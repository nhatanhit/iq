package com.example.anh.task;

import android.content.Context;
import android.os.AsyncTask;

import com.example.anh.constant.AppConstant;
import com.example.anh.database.DBAdapter;
import com.example.anh.database.UserModel;
import com.example.anh.listener.CustomListener;

/**
 * Created by anh on 1/5/16.
 */
public class SelectUserTask extends AsyncTask<Void,Void,Integer> {
    private CustomListener.onAsynTaskSQLiteDatabase listener;
    private Context mContext;
    private UserModel[] data;
    public SelectUserTask(Context context,CustomListener.onAsynTaskSQLiteDatabase l) {
        this.mContext = context;
        this.listener = l;
    }

    @Override
    protected Integer doInBackground(Void... params) {

        DBAdapter dbAdapter;
        dbAdapter = DBAdapter.getInstance(this.mContext);
        data = dbAdapter.readAll();
        return AppConstant.APPLICANT_CREATE_SUCCESS;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Integer integer) {
        listener.onNotifyStatusSelect(integer,data);
        super.onPostExecute(integer);
    }
}
