package com.example.anh.iqtest;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.anh.listener.CustomListener;

/**
 * Created by anh on 1/6/16.
 */
public abstract class CustomBarWithSearchActivity extends CommonActivity {
    protected ImageView iconBack;

    protected View.OnClickListener onClickBackListener;
    protected CustomListener.onCustomBarBehaviors behaviors;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }




    public  void onSearch() {
        //Nothing to do
        behaviors.onSearch();
    }

    public void bindEvents() {
        iconBack = (ImageView)findViewById(R.id.custom_with_search_back);
//        headerText = (TextView)findViewById(R.id.custom_with_header_header);
        iconBack.setOnClickListener(onClickBackListener);
        onSearch();
    }
}
