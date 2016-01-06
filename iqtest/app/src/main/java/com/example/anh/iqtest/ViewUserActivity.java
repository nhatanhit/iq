package com.example.anh.iqtest;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.anh.listener.CustomListener;

public class ViewUserActivity extends CustomBarWithSearchActivity  {
    private Activity mActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user);
        mActivity = this;
        onClickBackListener = new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, ManageActivity.class);
                startActivity(intent);
                mActivity.finish();
            }
        };
        behaviors  = new CustomListener.onCustomBarBehaviors() {
            @Override
            public void onSearch() {
                //Not implement
            }
        };
        bindEvents();

        //use on touch keyboard
        setupUI(findViewById(R.id.view_user_screen));

    }
}
