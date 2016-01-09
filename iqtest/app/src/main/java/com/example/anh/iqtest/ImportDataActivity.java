package com.example.anh.iqtest;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.os.Environment;

import com.example.anh.constant.AppConstant;
import com.example.anh.listener.CustomListener;
import com.example.anh.task.CheckDataTask;
import com.example.anh.task.DeleteFolderTask;
import com.example.anh.task.DownloadTask;
import com.example.anh.task.UnzippingTask;
import com.example.anh.utils.Decompress;
import com.example.anh.utils.FileMainpulation;
import com.example.anh.utils.Json;
import com.example.anh.utils.KeyValueDb;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

public class ImportDataActivity extends CustomBarWithHeaderActivity {

    private ProgressDialog mProgressDialog;
    private DownloadTask currentTask;
    private String zipFileLocation;
    private String unzipDirLocation;
    private Activity mActivity;
    private CustomListener.onAsynTaskExtractZipProgress unzipCallback = new CustomListener.onAsynTaskExtractZipProgress() {

        @Override
        public void onProgressExtract(int percentageCompleted) {
            mProgressDialog.setProgress(percentageCompleted);
        }

        @Override
        public void onFinish(int status) {
            if(status == AppConstant.UNZIPPING_SUCCESS_STATUS) {
                mProgressDialog.setMessage("Check Data Progress");
                mProgressDialog.setProgress(100);
                String zipFile = Environment.getExternalStorageDirectory() + "/data.zip";
                String unzipLocation = Environment.getExternalStorageDirectory() + "/unzipped";
                CheckDataTask checkDataTask = new CheckDataTask(getApplicationContext(),this);
                String[] paths = new String[]{zipFile,unzipLocation};
                checkDataTask.execute(paths);
            }

        }

        @Override
        public void onFinishCheckDataZip(int status,String extra) {
            if(status == AppConstant.CHECK_DOWNLOAD_DATA_SUCCES) {
                mProgressDialog.dismiss();
                //set number of questions
                if(extra.equals("")) {
                    extra = "0";
                }

                KeyValueDb.setValue(getApplicationContext(), "number_of_question", extra.trim());
                //show dialog
                AlertDialog alertDialog = new AlertDialog.Builder(ImportDataActivity.this).create();
                alertDialog.setTitle("Message");
                alertDialog.setMessage("Import Data Success");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
            else {
                mProgressDialog.setMessage("Failed when zip file, delete data...");
                //execute asyntask
                String[] paths = new String[]{zipFileLocation,unzipDirLocation};
                DeleteFolderTask deleteTask = new DeleteFolderTask(this,status);
                deleteTask.execute(paths);
            }

        }

        @Override
        public void onAfterDeleteFailedDirectory(int status) {
            mProgressDialog.dismiss();
            String message = AppConstant.messages.get(status);


            //show dialog
            AlertDialog alertDialog = new AlertDialog.Builder(ImportDataActivity.this).create();
            alertDialog.setTitle("Message");
            alertDialog.setMessage(message);
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();

        }
    };

    private CustomListener.onAsyntaskDownloadProgress downloadCallback = new CustomListener.onAsyntaskDownloadProgress() {
        @Override
        public void onProgressUpdate(Integer... progress) {
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setMax(100);
            mProgressDialog.setProgress(progress[0]);

        }

        @Override
        public void onProgressFinish(Integer status) {
            if(status == AppConstant.DOWNLOAD_FROM_URL_SUCCESS) {
                mProgressDialog.setMessage("Unzipping Progress");
                //open file, check extension, and then extract
                mProgressDialog.setProgress(0);

                String zipFile = Environment.getExternalStorageDirectory() + "/data.zip";
                String unzipLocation = Environment.getExternalStorageDirectory() + "/unzipped/";
                UnzippingTask unzipTask = new UnzippingTask(getApplicationContext(),unzipCallback,zipFile,unzipLocation);

                zipFileLocation = zipFile;
                unzipDirLocation = unzipLocation;

//                String[] paths = new String[]{zipFile,unzipLocation};
                unzipTask.execute();
            }
            else {
                mProgressDialog.dismiss();
                KeyValueDb.setValue(getApplicationContext(),"question_data_url","");
            }
        }

        @Override
        public void onProgressStart() {
            mProgressDialog.show();
        }
    };


    private View.OnClickListener importClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //execute asyntask
            String url = downloadText.getText().toString();
            if(!url.isEmpty()) {
                DownloadTask downloadTask = new DownloadTask(getApplicationContext(),downloadCallback);
                currentTask = downloadTask;
                String[] urls = new String[] {url};

                KeyValueDb.setValue(getApplicationContext(),"question_data_url",urls[0]);

                downloadTask.execute(urls);
            }

        }
    };

    private DialogInterface.OnCancelListener cancelDialogListener = new DialogInterface.OnCancelListener() {
        @Override
        public void onCancel(DialogInterface dialog) {

            currentTask.cancel(true);
        }
    };

    private EditText downloadText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mActivity = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_data);
        Button btnImportData = (Button)findViewById(R.id.btn_import_data);
        downloadText = (EditText)findViewById(R.id.download_link);



        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setMessage("Download Progress");
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setCancelable(true);
        mProgressDialog.setCanceledOnTouchOutside(false);

        KeyValueDb.getPrefs(getApplicationContext());
        String questionDataUrl =  KeyValueDb.getValue(getApplicationContext(),"question_data_url");
        if(!questionDataUrl.isEmpty()) {
            downloadText.setText(questionDataUrl);
        }

        btnImportData.setOnClickListener(importClick);

        onClickBackListener = new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, ManageActivity.class);
                startActivity(intent);
                mActivity.finish();
            }
        };
        bindEvents();
        onSetHeaderText("Import Data");
    }
}
