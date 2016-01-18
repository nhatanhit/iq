package com.bsp.iqtest.task;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.bsp.iqtest.constant.AppConstant;
import com.bsp.iqtest.listener.CustomListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by anh on 12/29/15.
 */
public class UnzippingTask extends AsyncTask<Void,Integer,Integer> {

    private Context mContext;
    private CustomListener.onAsynTaskExtractZipProgress progressListener;
    private String _zipFile;
    private String _location;
    private int per = 0;
    @Override
    protected Integer doInBackground(Void... params) {
        try  {
            FileInputStream fin = new FileInputStream(_zipFile);
            ZipInputStream zin = new ZipInputStream(fin);
            ZipEntry ze = null;
            while ((ze = zin.getNextEntry()) != null) {
                Log.v("Decompress", "Unzipping " + ze.getName());

                if(ze.isDirectory()) {
                    _dirChecker(ze.getName());
                } else {
                    per++;
                    publishProgress(per);

                    FileOutputStream fout = new FileOutputStream(_location + ze.getName());
                    for (int c = zin.read(); c != -1; c = zin.read()) {
                        fout.write(c);
                    }

                    zin.closeEntry();
                    fout.close();
                }

            }
            zin.close();
            return AppConstant.UNZIPPING_SUCCESS_STATUS;
        } catch(Exception e) {
            Log.e("Decompress", "unzip", e);
            return AppConstant.UNZIPPING_FAILED_STATUS;

        }
    }

    public UnzippingTask(Context context,CustomListener.onAsynTaskExtractZipProgress l,String zipFile, String location) {
        this.mContext = context;
        this.progressListener = l;
        this._zipFile = zipFile;
        this._location = location;
        _dirChecker("");
    }

    @Override
    protected void onPostExecute(Integer integer) {
        progressListener.onFinish(integer);
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    private void _dirChecker(String dir) {
        File f = new File(_location + dir);

        if(!f.isDirectory()) {
            f.mkdirs();
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        progressListener.onProgressExtract(values[0]);
    }
}
