package com.example.anh.task;

import android.content.Context;
import android.os.AsyncTask;

import com.example.anh.constant.AppConstant;
import com.example.anh.listener.CustomListener;
import com.example.anh.utils.FileMainpulation;

import java.io.File;

/**
 * Created by anh on 1/5/16.
 */
public class DeleteFolderTask extends AsyncTask<String,Void,Integer> {
    private CustomListener.onAsynTaskExtractZipProgress progressListener;
    private int checkFileStatus;
    public DeleteFolderTask(CustomListener.onAsynTaskExtractZipProgress p,int checkFileStatus) {
        this.progressListener = p;
        this.checkFileStatus = checkFileStatus;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        progressListener.onAfterDeleteFailedDirectory(this.checkFileStatus);
    }

    @Override
    protected Integer doInBackground(String... params) {
        String unzipDirLocation = params[1];
        String zipFileLocation = params[0];

        FileMainpulation.DeleteRecursive(unzipDirLocation);
        File zipFile = new File(zipFileLocation);
        zipFile.delete();
        return AppConstant.DELETE_FAILED_FOLDER_SUCCESS;
    }
}
