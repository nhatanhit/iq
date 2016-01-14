package com.bsp.iqtest.task;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.PowerManager;
import android.widget.Toast;


import com.bsp.iqtest.constant.AppConstant;
import com.bsp.iqtest.listener.CustomListener;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.io.IOException;

/**
 * Created by anh on 12/28/15.
 */
public class DownloadTask extends AsyncTask<String, Integer, Integer> {

    private Context context;
    private PowerManager.WakeLock mWakeLock;
    private CustomListener.onAsyntaskDownloadProgress listener;

    public static DownloadTask currentTask;
    public static DownloadTask getInstance(Context context,CustomListener.onAsyntaskDownloadProgress l) {
        if(DownloadTask.currentTask != null) {
            return currentTask;
        }
        else {
            currentTask = new DownloadTask(context,l);
            return currentTask;
        }
    }

    public DownloadTask(Context context,CustomListener.onAsyntaskDownloadProgress l) {
        this.context = context;
        this.listener = l;
    }

    @Override
    protected void onPreExecute() {
        listener.onProgressStart();
        PowerManager pm = (PowerManager)context.getSystemService(Context.POWER_SERVICE);
        mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                getClass().getName());
        mWakeLock.acquire();

        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Integer s) {
        mWakeLock.release();
        if(s == AppConstant.DOWNLOAD_FROM_URL_SUCCESS) {
            Toast.makeText(context,"File downloaded", Toast.LENGTH_SHORT).show();
            this.listener.onProgressFinish(AppConstant.DOWNLOAD_FROM_URL_SUCCESS);
        }
        else {
            this.listener.onProgressFinish(s);
        }


    }

    @Override
    protected Integer doInBackground(String... params) {
        InputStream input = null;
        OutputStream output = null;
        HttpURLConnection connection = null;
        try {
            URL url = new URL(params[0]);
            connection = (HttpURLConnection) url.openConnection();

            connection.connect();

            // expect HTTP 200 OK, so we don't mistakenly save error report
            // instead of the file

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return AppConstant.CONNECTION_ERROR;

            }

            // this will be useful to display download percentage
            // might be -1: server did not report the length
            int fileLength = connection.getContentLength();
            if(connection.getContentType().equals("application/zip")) {
                // download the file
                input = connection.getInputStream();


                String outputPath = Environment.getExternalStorageDirectory() + "/data.zip";

                output = new  java.io.FileOutputStream(outputPath);

                byte data[] = new byte[4096];
                long total = 0;
                int count;
                while ((count = input.read(data)) != -1) {
                    // allow canceling with back button
                    if (isCancelled()) {
                        input.close();
                        return null;
                    }
                    total += count;
                    // publishing the progress....
                    if (fileLength > 0) // only if total length is known
                        publishProgress((int) (total * 100 / fileLength));
                    output.write(data, 0, count);
                }
            }
            else {
                return AppConstant.DOWNLOAD_NOT_FOUND;
            }

        }
        catch (MalformedURLException exc) {
//            return AppConstant.messages.get(AppConstant.MALFORMED_URL);
            return AppConstant.MALFORMED_URL;
        }

        catch (Exception e) {
//            return AppConstant.messages.get(AppConstant.DOWNLOAD_ERROR);
            return AppConstant.DOWNLOAD_NOT_FOUND;
        } finally {
            try {
                if (output != null)
                    output.close();
                if (input != null)
                    input.close();
            } catch (IOException ignored) {
            }

            if (connection != null)
                connection.disconnect();
        }

        return AppConstant.DOWNLOAD_FROM_URL_SUCCESS;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        this.listener.onProgressUpdate(values[0]);

    }
}
