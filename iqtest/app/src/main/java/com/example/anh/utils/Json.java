package com.example.anh.utils;

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

    public static String loadJsonFromAssets(String path,Context context) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            java.io.File f = new java.io.File(path);
            if(f.exists() && !f.isDirectory() && f.canRead()) {
                InputStream is = context.getAssets().open(path);
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
}
