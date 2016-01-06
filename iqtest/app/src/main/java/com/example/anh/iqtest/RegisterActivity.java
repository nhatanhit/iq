package com.example.anh.iqtest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.anh.constant.AppConstant;
import com.example.anh.utils.KeyValueDb;

/**
 * Created by anh on 12/23/15.
 */
public class RegisterActivity extends Activity {
    Button btnRegister;
    private Activity mActivity;

    private View.OnClickListener registerClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            EditText userNameText = (EditText)findViewById(R.id.user_name_text);
            EditText passwordText = (EditText)findViewById(R.id.password_text);
            EditText companyText = (EditText)findViewById(R.id.company_name_text);

            if(!userNameText.getText().toString().trim().equals("") &&
                    !passwordText.getText().toString().trim().equals("") &&
                    !companyText.getText().toString().trim().equals("")) {

                //write to share preferences
                KeyValueDb.setValue(getApplicationContext(), "username", userNameText.getText().toString().trim());
                KeyValueDb.setValue(getApplicationContext(), "password", passwordText.getText().toString().trim());
                KeyValueDb.setValue(getApplicationContext(), "company_name", companyText.getText().toString().trim());

                Intent intent = new Intent(mActivity, ManageActivity.class);
                startActivity(intent);
                mActivity.finish();


            }
            else {
                if( userNameText.getText().toString().trim().equals("")){
                    userNameText.setError(AppConstant.messages.get(AppConstant.USER_NAME_IS_REQUIRED));
                }
                if( companyText.getText().toString().trim().equals("")){
                    companyText.setError(AppConstant.messages.get(AppConstant.COMPANY_NAME_IS_REQUIRED));
                }
                if( passwordText.getText().toString().trim().equals("")){
                    passwordText.setError(AppConstant.messages.get(AppConstant.PASSWORD_IS_REQUIRED));
                }
            }

        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        this.mActivity = this;

        //redirect to login page if it has username and password
        String username =  KeyValueDb.getValue(getApplicationContext(),"username");
        String password =  KeyValueDb.getValue(getApplicationContext(),"password");
        String company_name =  KeyValueDb.getValue(getApplicationContext(),"company_name");
        if(!username.equals("") && !password.equals("") && !company_name.equals("")) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            this.finish();
        }


       btnRegister = (Button)findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(registerClickListener);
    }
}
