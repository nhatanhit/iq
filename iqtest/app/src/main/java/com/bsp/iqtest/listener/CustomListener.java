package com.bsp.iqtest.listener;

/**
 * Created by anh on 12/25/15.
 */

/**
 *
 */
public  class CustomListener {
    public interface TimePicker {
        void onTimeSet(android.widget.TimePicker view, Integer minute, Integer second);
    };

    public interface TimeChange {
        void onNotifyDurationChange(Integer min,Integer second);
        void onNotifyDurationEnd();
    }

    public interface onAsyntaskDownloadProgress {
        void onProgressUpdate(Integer... progress);
        void onProgressFinish(Integer status);
        void onProgressStart();
    };


    public interface onAsynTaskExtractZipProgress {
        void onProgressExtract(int percentageCompleted);
        void onFinish(int status);

        void onFinishCheckDataZip(int status,String extra);
        void onAfterDeleteFailedDirectory(int status);
    };

    public interface onAsynTaskSQLiteDatabase {
        void onNotifyStatusCreate(int status);
        void onNotifyStatusSelect(int status, Object[] o);
        void onNotifyStatusUpdate(int status);
    }
    //this interface for use in abstract class, parent class, use one layout as one part of children class layout
    //So it will implement a common task(algorithm)
    //For example a search util in search layout, find icon click to search and inject onBehaviors listener of client to common task
    //of abstract class
    public  interface onBehaviors {
        void onSearchWithObjectFilter(Object filter);
    }



}
