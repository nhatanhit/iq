package com.example.anh.task;

import android.content.Context;
import android.os.AsyncTask;

import com.example.anh.constant.AppConstant;
import com.example.anh.database.DBAdapter;
import com.example.anh.listener.CustomListener;
import com.example.anh.model.UserModel;

import java.util.HashMap;

/**
 * Created by Dell on 12/01/2016.
 */
public class UpdateUserDataTask extends AsyncTask<UserModel,Void,Integer> {

    private Context mContext;
    private CustomListener.onAsynTaskSQLiteDatabase listener;
    private Integer userId;
    public UpdateUserDataTask(Context context, CustomListener.onAsynTaskSQLiteDatabase l,Integer uId) {
        this.mContext = context;
        this.listener = l;
        this.userId = uId;
    }

    @Override
    protected Integer doInBackground(UserModel... params) {
        DBAdapter dbAdapter;
        dbAdapter = DBAdapter.getInstance(this.mContext);
        Integer taskStatus =  dbAdapter.updateData(params[0], userId);
        return taskStatus;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        listener.onNotifyStatusUpdate(integer);

    }
}
