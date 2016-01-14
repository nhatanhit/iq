package com.bsp.iqtest.iqtest;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by anh on 1/6/16.
 */
public abstract class CustomBarWithHeaderActivity extends CommonActivity {
    protected ImageView iconBack;
    protected TextView headerText;
    protected View.OnClickListener onClickBackListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    public void onSetHeaderText(String text) {
        headerText.setText(text);
    }


    public  void onSearch() {
        //Nothing to do
    }

    public void bindEvents() {
        iconBack = (ImageView)findViewById(R.id.custom_with_header_back);
        headerText = (TextView)findViewById(R.id.custom_with_header_header);
        iconBack.setOnClickListener(onClickBackListener);
    }
}
