package com.example.anh.task;

import android.content.Context;
import android.os.AsyncTask;

import com.example.anh.constant.AppConstant;
import com.example.anh.database.DBAdapter;
import com.example.anh.model.UserModel;
import com.example.anh.listener.CustomListener;

/**
 * Created by anh on 1/5/16.
 */
public class CreateDataTask extends AsyncTask<UserModel, Void, Integer> {
    private Context mContext;
    private CustomListener.onAsynTaskSQLiteDatabase listener;



    public CreateDataTask(Context context,CustomListener.onAsynTaskSQLiteDatabase l) {
        this.mContext = context;
        this.listener = l;
    }
    @Override
    protected Integer doInBackground(UserModel... params) {
        DBAdapter dbAdapter;
        dbAdapter = DBAdapter.getInstance(this.mContext);
        dbAdapter.create(params[0]);
        return AppConstant.APPLICANT_CREATE_SUCCESS;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        listener.onNotifyStatusCreate(integer);

    }
}
