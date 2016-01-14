package com.bsp.iqtest.utils;

/**
 * Created by Dell on 13/01/2016.
 */
public class Utils {
    public static boolean checkDataUrl(String url) {
        String exp = "^((https?:\\/\\/)|(ftp:\\/\\/))?([\\da-z\\.-]+)\\.([a-z\\.]{2,6})([\\/\\w \\.-]*)*\\/?(.zip)$";
        if(url.matches(exp)) {
            return true;
        }
        else {
            return false;
        }
    }
}
