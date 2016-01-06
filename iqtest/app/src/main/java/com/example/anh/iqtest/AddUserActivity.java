package com.example.anh.iqtest;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.anh.constant.AppConstant;
import com.example.anh.database.DBAdapter;
import com.example.anh.database.DatabaseHandler;
import com.example.anh.database.UserModel;
import com.example.anh.listener.CustomListener;
import com.example.anh.task.CreateDataTask;
import com.example.anh.utils.NoticeDialog;

import java.sql.SQLException;
import java.util.Objects;

public class AddUserActivity extends CustomBarWithHeaderActivity {

    private Button btnAddUser;
    private Activity mActivity;

    private CustomListener.onAsynTaskSQLiteDatabase listener = new CustomListener.onAsynTaskSQLiteDatabase() {
        @Override
        public void onNotifyStatusCreate(int status) {
            if(status == AppConstant.APPLICANT_CREATE_SUCCESS) {
                NoticeDialog.showNoticeDialog(mActivity,"Messages",AppConstant.messages.get(AppConstant.APPLICANT_CREATE_SUCCESS));
            }
        };

        @Override
        public void onNotifyStatusSelect(int status,Object[] o) {
            //Nothing to implement
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
