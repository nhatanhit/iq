package com.bsp.iqtest.iqtest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.bsp.iqtest.constant.AppConstant;
import com.bsp.iqtest.database.DBAdapter;
import com.bsp.iqtest.model.UserModel;
import com.bsp.iqtest.listener.CustomListener;
import com.bsp.iqtest.task.CreateDataTask;
import com.bsp.iqtest.utils.NoticeDialog;


import java.sql.SQLException;

public class AddUserActivity extends CustomBarWithHeaderActivity {

    private Button btnAddUser;
    private Activity mActivity;

    private CustomListener.onAsynTaskSQLiteDatabase listener = new CustomListener.onAsynTaskSQLiteDatabase() {
        @Override
        public void onNotifyStatusCreate(int status) {
            if(status == AppConstant.APPLICANT_CREATE_SUCCESS) {
                //NoticeDialog.showNoticeDialog(mActivity,"Messages",AppConstant.messages.get(AppConstant.APPLICANT_CREATE_SUCCESS));
                //go to screen user list
                Intent intent = new Intent(mActivity, ViewUserActivity.class);
                startActivity(intent);
                mActivity.finish();
            }
        };

        @Override
        public void onNotifyStatusSelect(int status,Object[] o) {
            //Nothing to implement
        }

        @Override
        public void onNotifyStatusUpdate(int status) {

        }
    };



    View.OnClickListener addUserListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            EditText fullNameTextView = (EditText)findViewById(R.id.add_user_full_name);
            EditText phoneTextView = (EditText)findViewById(R.id.add_user_phone);
            if(fullNameTextView.getText().toString().equals("") || phoneTextView.getText().toString().equals("")) {
                NoticeDialog.showNoticeDialog(mActivity,"Notice", AppConstant.messages.get(AppConstant.APPLICANT_INFO_NOT_FILL));

            }
            else {
//                getApplicationContext().deleteDatabase(DatabaseHandler.DATABASE_NAME);
                UserModel newUser = new UserModel();
                DBAdapter adapter = DBAdapter.getInstance(mActivity);
                try {
                    adapter.open();
                    newUser.setFullName(fullNameTextView.getText().toString());
                    newUser.setPhone(phoneTextView.getText().toString());
                    CreateDataTask task = new CreateDataTask(mActivity,listener);
                    UserModel[] data = new UserModel[]{newUser};
                    task.execute(data);
                }
                catch(SQLException exception) {

                }

            }
        }
    };
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        mActivity = this;
        btnAddUser = (Button)findViewById(R.id.btn_save_user);
        btnAddUser.setOnClickListener(addUserListener);


        onClickBackListener = new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, ManageActivity.class);
                startActivity(intent);
                mActivity.finish();
            }
        };
        bindEvents();
        onSetHeaderText("Add User");

    }
}
