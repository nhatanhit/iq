package com.bsp.iqtest.task;

import android.content.Context;
import android.os.AsyncTask;

import com.bsp.iqtest.constant.AppConstant;
import com.bsp.iqtest.database.DBAdapter;
import com.bsp.iqtest.model.UserModel;
import com.bsp.iqtest.listener.CustomListener;

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
