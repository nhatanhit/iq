package com.bsp.iqtest.task;

import android.content.Context;
import android.os.AsyncTask;

import com.bsp.iqtest.constant.AppConstant;
import com.bsp.iqtest.database.DBAdapter;
import com.bsp.iqtest.model.UserModel;
import com.bsp.iqtest.listener.CustomListener;

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
