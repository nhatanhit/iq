package com.example.anh.utils;
import android.util.Log;

import  org.apache.commons.io.FileUtils;
/**
 * Created by anh on 12/29/15.
 */
import com.example.anh.constant.AppConstant;

import java.io.File;
import java.io.IOException;

public class FileMainpulation {
    public static final String TAG = "FileMainpulation";
    public static void DeleteRecursive(String fileOrDirectory) {
        File dir = new File(fileOrDirectory);
        if (dir.isDirectory())
            for (File child : dir.listFiles())
                DeleteRecursive(child.getAbsolutePath());

        dir.delete();
    }

    public static Integer createDirectory(String newDirectory) {
        File file = new File(newDirectory);
        if(!file.exists()) {
            if(file.mkdir()) {
                return AppConstant.CREATE_DIRECTORY_SUCCESS;
            }
            else {
                return AppConstant.CREATE_DIRECTORY_FAILED;
            }
        }
        else {
            return AppConstant.DIRECTORY_IS_EXISTED;
        }

    }

    public static void moveData(String srcDir,String desDir) {
        File source = new File(srcDir);
        File des = new File(desDir);

        try {
            FileUtils.moveDirectory(source,des);
        }
        catch(IOException exc) {
            Log.e(FileMainpulation.TAG,exc.getMessage());
        }
    }

}
