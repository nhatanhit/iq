package com.example.anh.iqtest;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TimePicker;

import com.example.anh.constant.AppConstant;
import com.example.anh.fragment.TimePickerFragment;
import com.example.anh.listener.CustomListener;
import com.example.anh.task.CreateDataTask;
import com.example.anh.utils.KeyValueDb;
import com.example.anh.utils.NoticeDialog;

public class ManageActivity extends Activity implements CustomListener.TimePicker {
    ImageButton btnShowPicker;
    private Activity mActivity;
    Button navigateImportButton,navigateAddUserButton,navigateViewResult,navigateViewQuestion;

    View.OnClickListener showPickerlistener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String numberQuestions =  KeyValueDb.getValue(getApplicationContext(), "number_of_question");
            if(!numberQuestions.equals("0") && !numberQuestions.equals("")) {
                TimePickerFragment newFragment = new TimePickerFragment();
                newFragment.show(getFragmentManager(),"timePicker");
            }
            else {
                //show an alert dialog and required user input data
                NoticeDialog.showNoticeDialog(mActivity,"Messages", AppConstant.messages.get(AppConstant.DATA_NOT_IMPORT));
            }

        }
    };

    View.OnClickListener importListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(mActivity, ImportDataActivity.class);
            startActivity(intent);
            mActivity.finish();
        };
    };

    View.OnClickListener addUserListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(mActivity, AddUserActivity.class);
            startActivity(intent);
            mActivity.finish();
        }
    };

    View.OnClickListener viewResultListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(mActivity, ViewUserActivity.class);
            startActivity(intent);
            mActivity.finish();
        }
    };
    View.OnClickListener viewQuestionListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(mActivity, QuestionActivity.class);
            startActivity(intent);
            mActivity.finish();
        }
    };



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivity = this;
        setContentView(R.layout.activity_manage);

        btnShowPicker = (ImageButton)findViewById(R.id.btn_show_picker);
        navigateImportButton = (Button)findViewById(R.id.navigate_import_data);
        navigateAddUserButton = (Button)findViewById(R.id.navigate_add_user);
        navigateViewResult = (Button)findViewById(R.id.navigate_view_result);
        navigateViewQuestion = (Button)findViewById(R.id.navigate_view_question);

        btnShowPicker.setOnClickListener(showPickerlistener);
        navigateImportButton.setOnClickListener(importListener);
        navigateAddUserButton.setOnClickListener(addUserListener);
        navigateViewResult.setOnClickListener(viewResultListener);
        navigateViewQuestion.setOnClickListener(viewQuestionListener);
    }

    @Override
    public void onTimeSet(TimePicker view, Integer hourOfDay, Integer minute) {
        //set preferences
        KeyValueDb.setValue(getApplicationContext(),"allow_hour",hourOfDay.toString());
        KeyValueDb.setValue(getApplicationContext(),"allow_minute",minute.toString());
    }
}
