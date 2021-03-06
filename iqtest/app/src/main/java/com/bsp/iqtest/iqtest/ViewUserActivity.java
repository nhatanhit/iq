package com.bsp.iqtest.iqtest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.bsp.iqtest.constant.AppConstant;
import com.bsp.iqtest.model.UserModel;
import com.bsp.iqtest.listener.CustomListener;
import com.bsp.iqtest.task.GetUserDataTask;
import com.bsp.iqtest.utils.KeyValueDb;
import com.bsp.iqtest.utils.NoticeDialog;

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

        @Override
        public void onNotifyStatusUpdate(int status) {

        }
    };





    private View.OnClickListener userInfoClick = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            try {
                String phone = v.getTag().toString();
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + phone));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);
            }
            catch(SecurityException ex) {
                NoticeDialog.showNoticeDialog(mActivity,"Notice",AppConstant.messages.get(AppConstant.CAN_NOT_CALL_PHONE));
            }
        }
    };

    private View.OnClickListener userScoreClick = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            if(!KeyValueDb.getValue(getApplicationContext(), "allow_minute").equals("") &&
                    !KeyValueDb.getValue(getApplicationContext(), "allow_second").equals("")) {
                Integer userId = Integer.parseInt(v.getTag().toString());
                Intent intent = new Intent(mActivity, InterviewerTestActivity.class);
                intent.putExtra("user_id",userId);
                startActivity(intent);
                mActivity.finish();
            }
            else {
                NoticeDialog.showNoticeDialog(mActivity,"Notice",AppConstant.messages.get(AppConstant.DURATION_NOT_SET));
            }
        }
    };

    private void displayUserInfo(UserModel u, int index) {
        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View userRowView = inflater.inflate(R.layout.table_row_user,null);

        RelativeLayout userInfoLayout = (RelativeLayout)userRowView.findViewById(R.id.user_info);

        userInfoLayout.setOnClickListener(userInfoClick);

        RelativeLayout userScoreLayout = (RelativeLayout)userRowView.findViewById(R.id.user_score);
        if(u.getIsCompleted() == 0) {
            userScoreLayout.setOnClickListener(userScoreClick);
        }



        userScoreLayout.setTag(u.getId());
        userInfoLayout.setTag(u.getPhone());
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

        //drop database
//        getApplicationContext().deleteDatabase(DatabaseHandler.DATABASE_NAME);

    }
}
