package com.example.anh.task;

import android.content.Context;
import android.os.AsyncTask;

import com.example.anh.constant.AppConstant;
import com.example.anh.database.DBAdapter;
import com.example.anh.model.UserModel;
import com.example.anh.listener.CustomListener;

/**
 * Created by Dell on 07/01/2016.
 */
public class GetUserDataTask extends AsyncTask<String,Void,UserModel[]> {
    private Integer taskStatus;
    private Context mContext;
    private CustomListener.onAsynTaskSQLiteDatabase listener;

    public GetUserDataTask(Context context,CustomListener.onAsynTaskSQLiteDatabase l) {
        this.listener = l;
        this.mContext = context;
    }
    @Override
    protected UserModel[] doInBackground(String... params) {
        DBAdapter dbAdapter;
        dbAdapter = DBAdapter.getInstance(this.mContext);
        UserModel[] result =  dbAdapter.readAll(params[0]);
        this.taskStatus = AppConstant.LIST_USER_SUCCESS;
        return result;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(UserModel[] userModels) {
        super.onPostExecute(userModels);
        listener.onNotifyStatusSelect(taskStatus,userModels);
    }
}
