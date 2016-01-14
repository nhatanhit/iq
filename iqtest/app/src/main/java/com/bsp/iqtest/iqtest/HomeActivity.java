package com.bsp.iqtest.iqtest;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.content.Intent;
import android.content.Context;

import com.bsp.iqtest.constant.AppConstant;

public class HomeActivity extends Activity {

    private Handler mHandler;
    private Activity mActivity;
    private Context mContext ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mActivity = this;


        mHandler = new Handler();
        autoForward();
    }

    private void autoForward () {
        mHandler.postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent intent = new Intent(mActivity, AddUserActivity.class);
                startActivity(intent);
                mActivity.finish();
            }
        }, AppConstant.timeLandingPage);
    }
}

