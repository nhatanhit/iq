package com.example.anh.task;

import android.app.Activity;

import com.example.anh.listener.CustomListener;

import java.sql.Time;
import java.util.TimerTask;

/**
 * Created by Dell on 07/01/2016.
 */
public class CountTimerTask extends TimerTask {
    private Integer min = 0;
    private Integer second = 0;
    private Integer allow_min;
    private Integer allow_second;
    private CustomListener.TimeChange timeChangeListener;
    private Activity mActivity;
    public CountTimerTask(CustomListener.TimeChange l,Integer allowMin,Integer allowSecond,Activity a) {
        this.second = 0;
        this.min = 0;
        this.timeChangeListener = l;
        this.allow_min = allowMin;
        this.allow_second = allowSecond;
        this.mActivity = a;
    }
    public void run() {
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(second == 60) {
                    min = min + 1;
                    second = 0;
                }
                if(min.equals(allow_min) && second.equals(allow_second)) {
                    timeChangeListener.onNotifyDurationEnd();
                }
                timeChangeListener.onNotifyDurationChange(min,second);
            }
        });
        second = second + 1;

    }
}
