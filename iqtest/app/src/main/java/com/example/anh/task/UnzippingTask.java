package com.example.anh.task;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Environment;

import com.example.anh.constant.AppConstant;
import com.example.anh.listener.CustomListener;
import com.example.anh.utils.Decompress;

/**
 * Created by anh on 12/29/15.
 */
public class UnzippingTask extends AsyncTask<String,Integer,Integer> {

    private Context mContext;
    private CustomListener.onAsynTaskExtractZipProgress progressListener;
    public UnzippingTask(Context context,CustomListener.onAsynTaskExtractZipProgress l) {
        this.mContext = context;
        this.progressListener = l;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        progressListener.onFinish(integer);
    }

    @Override
    protected Integer doInBackground(String... params) {
        String zipFile = params[0] ;
        String unzipLocation = params[1] ;
        Decompress d = new Decompress(zipFile, unzipLocation);
        return   d.unzip();

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
}
