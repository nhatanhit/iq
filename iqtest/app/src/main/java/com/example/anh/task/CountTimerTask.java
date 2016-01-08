package com.example.anh.task;

import android.app.Activity;

import com.example.anh.listener.CustomListener;

import java.sql.Time;
import java.util.TimerTask;

/**
 * Created by Dell on 07/01/2016.
 */
public class CountTimerTask extends TimerTask {
    private Integer hour = 0;
    private Integer min = 0;
    private Integer allow_hour;
    private Integer allow_min;
    private CustomListener.TimeChange timeChangeListener;
    private Activity mActivity;
    public CountTimerTask(CustomListener.TimeChange l,Integer allowHour,Integer allowMin,Activity a) {
        this.hour = 0;
        this.min = 0;
        this.timeChangeListener = l;
        this.allow_hour = allowHour;
        this.allow_min = allowMin;
        this.mActivity = a;
    }
    public void run() {
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(min == 60) {
                    hour = hour + 1;
                    min = 0;
                }
                if(hour.equals(allow_hour) && min.equals(allow_min)) {
                    timeChangeListener.onNotifyDurationEnd();
                }
                timeChangeListener.onNotifyDurationChange(hour,min);
            }
        });
        min = min + 1;

    }
}
