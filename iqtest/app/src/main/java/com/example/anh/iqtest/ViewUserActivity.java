package com.example.anh.iqtest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.anh.constant.AppConstant;
import com.example.anh.model.UserModel;
import com.example.anh.listener.CustomListener;
import com.example.anh.task.GetUserDataTask;

public class ViewUserActivity extends CustomBarWithSearchActivity  {
    private Activity mActivity;
    private TableLayout listUserTable;
    private CustomListener.onAsynTaskSQLiteDatabase listener = new CustomListener.onAsynTaskSQLiteDatabase() {
        @Override
        public void onNotifyStatusCreate(int status) {
            //Nothing to implement
        }

        @Override
        public void onNotifyStatusSelect(int status, Object[] o) {
            if(status == AppConstant.LIST_USER_SUCCESS) {
                listUserTable.removeAllViewsInLayout();
                for(int i = 0 ; i < o.length ; i ++) {
                    UserModel user = (UserModel)o[i];
                    displayUserInfo(user,i);
                }
            }
        }
    };





    private View.OnClickListener userInfoClick = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(mActivity, InterviewerTestActivity.class);
            startActivity(intent);
            mActivity.finish();
        }
    };

    private void displayUserInfo(UserModel u, int index) {
        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View userRowView = inflater.inflate(R.layout.table_row_user,null);

        RelativeLayout userInfoLayout = (RelativeLayout)userRowView.findViewById(R.id.user_info);
        userInfoLayout.setOnClickListener(userInfoClick);

        TextView txtView = (TextView)userRowView.findViewById(R.id.text_view_user_name);
        txtView.setText(u.getFullName());

        TextView txtPhoneView = (TextView)userRowView.findViewById(R.id.text_view_phone);
        txtPhoneView.setText(u.getPhone());

        TextView txtCorrectView = (TextView)userRowView.findViewById(R.id.text_view_number_correct);
        txtCorrectView.setText(u.getNumsRightAnswers().toString());

        TextView txtNumberSentenceCompleted = (TextView)userRowView.findViewById(R.id.text_view_number_completed_sentence);
        txtNumberSentenceCompleted.setText(u.getTotalCompletedQuestions().toString() + "/" + u.getTotalQuestions().toString());

        listUserTable.addView(userRowView);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user);
        listUserTable = (TableLayout) findViewById(R.id.table_user);

        mActivity = this;
        onClickBackListener = new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, ManageActivity.class);
                startActivity(intent);
                mActivity.finish();
            }
        };

        onBehaviors = new CustomListener.onBehaviors() {
            @Override
            public void onSearchWithObjectFilter(Object filter) {
                String filterStr = filter.toString();
                GetUserDataTask listUserTask = new GetUserDataTask(mActivity,listener);
                String[] params = new String[] {filterStr};
                listUserTask.execute(params);
            }
        };

        bindEvents();

        //use on touch keyboard
        setupUI(findViewById(R.id.view_user_screen));
        //load data
        GetUserDataTask listUserTask = new GetUserDataTask(mActivity,listener);
        String[] params = new String[] {""};
        listUserTask.execute(params);

        //onSearch

    }
}
