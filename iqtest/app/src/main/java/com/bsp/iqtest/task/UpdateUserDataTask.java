package com.bsp.iqtest.task;

import android.content.Context;
import android.os.AsyncTask;

import com.bsp.iqtest.database.DBAdapter;
import com.bsp.iqtest.listener.CustomListener;
import com.bsp.iqtest.model.UserModel;

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
