package com.example.anh.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by anh on 12/29/15.
 */
public class KeyValueDb {
    private SharedPreferences sharedPreferences;
    private static String PREF_NAME = "prefs";
    public static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static String getValue(Context context,String keyName) {
        return getPrefs(context).getString(keyName, "");
    }

    public static void setValue(Context context,String key, String val) {
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.putString(key, val);
        editor.commit();
    }
}
