package com.example.anh.utils;

/**
 * Created by anh on 12/29/15.
 */
import java.io.File;
public class FileMainpulation {
    public static void DeleteRecursive(String fileOrDirectory) {
        File dir = new File(fileOrDirectory);
        if (dir.isDirectory())
            for (File child : dir.listFiles())
                DeleteRecursive(child.getAbsolutePath());

        dir.delete();
    }


}
