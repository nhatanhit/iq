package com.bsp.iqtest.iqtest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bsp.iqtest.constant.AppConstant;
import com.bsp.iqtest.utils.KeyValueDb;
import com.bsp.iqtest.utils.NoticeDialog;

public class LoginActivity extends CommonActivity {

    private Button btnLogin ;
    private EditText textUsername;
    private EditText textPassword;


    private View.OnClickListener loginListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            String username =  KeyValueDb.getValue(getApplicationContext(),"username");
            String password =  KeyValueDb.getValue(getApplicationContext(),"password");
            if(textUsername.getText().toString().equals(username) && textPassword.getText().toString().equals(password)) {
                Intent intent = new Intent(mActivity, ManageActivity.class);
                startActivity(intent);
                mActivity.finish();
            }
            else {
                String message =  AppConstant.messages.get(AppConstant.PASSWORD_IS_INCORRECT);
                NoticeDialog.showNoticeDialog(mActivity, "Message", message);

            }
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //get shared preference
        textUsername = (EditText)findViewById(R.id.login_username_text);
        textPassword = (EditText)findViewById(R.id.login_password_text);

        String company_name =  KeyValueDb.getValue(getApplicationContext(),"company_name");
        TextView textViewCompany = (TextView)findViewById(R.id.text_view_company);
        textViewCompany.setText(company_name);

        btnLogin = (Button)findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(loginListener);

        setupUI(findViewById(R.id.login_screen));
    }
}
