package com.example.anh.iqtest;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.anh.listener.CustomListener;
import com.example.anh.task.CountTimerTask;

import java.util.Timer;

public class InterviewerTestActivity extends CustomBarWithHeaderActivity {
    private Activity mActivity;

    private Timer timer;
    private CountTimerTask timerTask;
    final Handler handler = new android.os.Handler();
    private TextView timeDurationTextView;

    public void initializeTimerTask() {
        timerTask = new CountTimerTask(timeChangeListener,0,5,mActivity);
    };


    private CustomListener.TimeChange timeChangeListener = new CustomListener.TimeChange() {
        @Override
        public void onNotifyDurationChange(Integer hour, Integer minute) {
            String durationStamp = hour.toString()+ ":" + minute.toString();
            timeDurationTextView.setText(durationStamp);
        }

        @Override
        public void onNotifyDurationEnd() {
            stopTimer();
            //forward to Login Activity
            Intent intent = new Intent(mActivity, LoginActivity.class);
            startActivity(intent);
            mActivity.finish();

        }
    };
    public void startTimer() {
        timer = new Timer();
        initializeTimerTask();
        timer.schedule(timerTask,2000,5000);
    }
    public void stopTimer() {
        if(timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interviewer_test);
        mActivity = this;
        bindEvents();
        onSetHeaderText("Interview IQ Test");
        timeDurationTextView = (TextView)findViewById(R.id.timer);
        startTimer();
    }
}
