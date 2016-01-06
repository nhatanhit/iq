package com.example.anh.listener;

import android.view.View;
import android.widget.TimePicker;

import java.util.Objects;

/**
 * Created by anh on 12/25/15.
 */
public  class CustomListener {
    public static interface TimePicker {
        void onTimeSet(android.widget.TimePicker view, Integer hourOfDay, Integer minute);
    };

    public static interface onAsyntaskDownloadProgress {
        void onProgressUpdate(Integer... progress);
        void onProgressFinish();
        void onProgressStart();
    };

    public static interface onAsynTaskExtractZipProgress {
        void onFinish(int status);
        void onFinishCheckDataZip(int status,String extra);
        void onAfterDeleteFailedDirectory(int status);

    };

    public static interface onAsynTaskSQLiteDatabase {
        void onNotifyStatusCreate(int status);
        void onNotifyStatusSelect(int status, Object[] o);
    }
    public static interface onCustomBarBehaviors {
        void onSearch();
    }



}
