package com.bsp.iqtest.utils;

import android.app.Activity;
import android.content.Context;

import java.io.*;

/**
 * Created by anh on 12/29/15.
 */
public class Json {

    public static String loadJsonFromAbsolutePath(String path) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            java.io.File f = new java.io.File(path);
            if(f.exists() && !f.isDirectory() && f.canRead()) {
                InputStream is = new FileInputStream(path);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));

                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }

                bufferedReader.close();
                return stringBuilder.toString();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String loadJSONFromAsset(Activity activity) {
        String json = null;
        try {
            InputStream is = activity.getAssets().open("data.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        }
        catch (IOException ex) {
            return null;
        }
        return json;
    }

}
