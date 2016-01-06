package com.example.anh.database;

/**
 * Created by anh on 1/5/16.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "users";
    public static final String TAG = "DatabaseHandler.java";

    public String tableName = "user_info";
    public String fieldObjectId = "id";
    public String fieldObjectFullName = "full_name";
    public String fieldObjectPhone = "phone";
    public String fieldTotalQuestions = "total_questions";
    public String fieldNumberCompletedQuestion = "number_completed_questions";
    public String fieldNumberRightAnswers = "number_right_answers";

    private static final int DATABASE_VERSION = 5;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        String sql = "";

        sql += "CREATE TABLE " + tableName;
        sql += " ( ";
        sql += fieldObjectId + " INTEGER PRIMARY KEY AUTOINCREMENT, ";
        sql += fieldObjectFullName + " TEXT, ";
        sql += fieldObjectPhone + " TEXT, ";
        sql += fieldTotalQuestions + " INTEGER, ";
        sql += fieldNumberCompletedQuestion + " INTEGER, " ;
        sql += fieldNumberRightAnswers + " INTEGER " ;
        sql += " ) ";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        String sql = "DROP TABLE IF EXISTS " + tableName;
        db.execSQL(sql);

        onCreate(db);
    }

    public UserModel[] selectAll() {
        String sql = "";
        sql += "SELECT * FROM " + tableName;


        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        int recCount = cursor.getCount();
        UserModel[] objectItemData = new UserModel[recCount];
        int x = 0;
        if (cursor.moveToFirst()) {
            do {
                UserModel user = new UserModel();
                user.setFullName(cursor.getString(cursor.getColumnIndex(fieldObjectFullName)));
                user.setPhone(cursor.getString(cursor.getColumnIndex(fieldObjectPhone)));
                user.setTotalQuestions(Integer.getInteger(cursor.getString(cursor.getColumnIndex(fieldTotalQuestions))));
                user.setTotalCompletedQuestions(Integer.getInteger(cursor.getString(cursor.getColumnIndex(fieldNumberCompletedQuestion))));
                user.setNumsRightAnswers(Integer.getInteger(cursor.getString(cursor.getColumnIndex(fieldNumberRightAnswers))));
                objectItemData[x] = user;
                x++;
            } while (cursor.moveToNext());
        }
        return objectItemData;
    }

    public UserModel[] readFromId(String id) {

        String sql = "";
        sql += "SELECT * FROM " + tableName;
        sql += " wHERE " + fieldObjectId + "=" + id;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        int recCount = cursor.getCount();
        UserModel[] objectItemData = new UserModel[recCount];
        int x = 0;
        if (cursor.moveToFirst()) {
            do {
                UserModel user = new UserModel();
                user.setFullName(cursor.getString(cursor.getColumnIndex(fieldObjectFullName)));
                user.setPhone(cursor.getString(cursor.getColumnIndex(fieldObjectPhone)));
                user.setTotalQuestions(Integer.getInteger(cursor.getString(cursor.getColumnIndex(fieldTotalQuestions))));
                user.setTotalCompletedQuestions(Integer.getInteger(cursor.getString(cursor.getColumnIndex(fieldNumberCompletedQuestion))));
                user.setNumsRightAnswers(Integer.getInteger(cursor.getString(cursor.getColumnIndex(fieldNumberRightAnswers))));
                objectItemData[x] = user;
                x++;
            } while (cursor.moveToNext());
        }
        return objectItemData;
    }

    public boolean checkIfExists(UserModel user) {

        boolean recordExists = false;

        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "";
        sql += "SELECT " + fieldObjectId + " FROM " + tableName  ;
        sql += " WHERE full_name LIKE '%" + user.getFullName() + "%'";
        sql += " AND phone = '" + user.getPhone() + "'";

        Cursor cursor = db.rawQuery(sql, null);

        if (cursor != null) {

            if (cursor.getCount() > 0) {
                recordExists = true;
            }
        }

        cursor.close();
        db.close();

        return recordExists;
    }

    public boolean create(UserModel user) {

        boolean createSuccessful = false;

        if (!checkIfExists(user)) {

            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(fieldObjectFullName,user.getFullName());
            values.put(fieldObjectPhone,user.getPhone());
            values.put(fieldTotalQuestions,user.getTotalQuestions());
            values.put(fieldNumberRightAnswers,user.getNumsRightAnswers());
            values.put(fieldNumberCompletedQuestion, user.getTotalCompletedQuestions());
            createSuccessful = db.insert(tableName, null, values) > 0;

            if (createSuccessful) {
                Log.v(TAG, user.getFullName() + " created.");
            }

            db.close();

        }

        return createSuccessful;
    }

    public void queryAllColumnsName() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor dbCursor = db.query(tableName, null, null, null, null, null, null);
        String[] columnNames = dbCursor.getColumnNames();

    }


}
