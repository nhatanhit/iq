package com.example.anh.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.anh.model.UserModel;

import java.sql.SQLException;

/**
 * Created by anh on 1/5/16.
 */
public class DBAdapter {
    private SQLiteDatabase database;
    private DatabaseHandler databaseHandler;

    private static DBAdapter instance = null;

    public DBAdapter(Context context) {
        databaseHandler = new DatabaseHandler(context);
    }

    public void open() throws SQLException {
        database = databaseHandler.getWritableDatabase();
    }

    public void close() {
        databaseHandler.close();
    }

    public static DBAdapter getInstance(Context context) {
        if (instance == null) {
            instance = new DBAdapter(context);
        }
        return instance;
    }

    public boolean create(UserModel myObj) {
        return databaseHandler.create(myObj);
    }
    public UserModel[] readFromId(String id) {
        return databaseHandler.readFromId(id);
    }

    public UserModel[] readAll(String filter) {
        return databaseHandler.selectAll(filter);
    }

    public void queryDataBase() {
        databaseHandler.queryAllColumnsName();
    }

}
