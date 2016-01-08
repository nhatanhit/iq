package com.example.anh.iqtest;

import android.app.Activity;
import android.media.Image;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.anh.listener.CustomListener;

/**
 * Created by anh on 1/6/16.
 */
public abstract class CustomBarWithSearchActivity extends CommonActivity {
    protected ImageView iconBack;
    protected EditText txtSearch;
    protected ImageView iconSearch;
    protected View.OnClickListener onClickBackListener;
    protected CustomListener.onBehaviors onBehaviors;

    View.OnClickListener iconSearchClick  = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            String text = txtSearch.getText().toString();
            if(!text.equals("")) {
                onBehaviors.onSearchWithObjectFilter(text);
            }

        }
    };
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }



    public void bindEvents() {
        iconBack = (ImageView)findViewById(R.id.custom_with_search_back);
        txtSearch = (EditText)findViewById(R.id.text_search);
        iconSearch = (ImageView)findViewById(R.id.btn_search);
//        headerText = (TextView)findViewById(R.id.custom_with_header_header);
        iconBack.setOnClickListener(onClickBackListener);
        iconSearch.setOnClickListener(iconSearchClick);
    }
}
