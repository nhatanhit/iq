package com.example.anh.constant;

import android.support.annotation.NonNull;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by anh on 12/25/15.
 */
public class AppConstant {
    public static final int timeLandingPage = 10000;
    public static final int UNZIPPING_FAILED_STATUS = 0;
    public static final int UNZIPPING_SUCCESS_STATUS = 1;
    public static final int JSON_QUESTION_DATA_NOT_FOUND = 2;
    public static final int JSON_PARSE_DATA_ERROR = 3;
    public static final int DOWNLOAD_DATA_ERROR_FAILED_CHECKSUM = 4;
    public static final int DOWNLOAD_DATA_IMAGE_NOT_FOUND = 5;
    public static final int CHECK_DOWNLOAD_DATA_SUCCES = 6;
    public static final int DELETE_FAILED_FOLDER_SUCCESS = 7;
    public static final int COMPANY_NAME_IS_REQUIRED = 8;
    public static final int USER_NAME_IS_REQUIRED = 9;
    public static final int PASSWORD_IS_REQUIRED = 10;
    public static final int PASSWORD_IS_INCORRECT = 11;
    public static final int DATA_NOT_IMPORT = 12;
    public static final int APPLICANT_INFO_NOT_FILL = 13;
    public static final int APPLICANT_CREATE_SUCCESS = 14;
    public static final int LIST_USER_SUCCESS = 15;
    public static final int MALFORMED_URL  = 16;
    public static final int DOWNLOAD_ERROR = 17;
    public static final int DOWNLOAD_FROM_URL_SUCCESS = 18;
    public static final int READ_DATA_ERROR = 19;

    public static final Integer ANSWER_TAG = 20;

    public static final Map<Integer, String> messages = new HashMap<Integer,String>(){{
        put(AppConstant.CHECK_DOWNLOAD_DATA_SUCCES, "Download Data Success");
        put(AppConstant.DOWNLOAD_DATA_ERROR_FAILED_CHECKSUM, "Downloaded Data Failed Checksum");
        put(AppConstant.DOWNLOAD_DATA_IMAGE_NOT_FOUND, "Downloaded Data Image Not Found");
        put(AppConstant.JSON_PARSE_DATA_ERROR, "Json Parse Data Error");
        put(AppConstant.JSON_QUESTION_DATA_NOT_FOUND, "Json question data not found");
        put(AppConstant.UNZIPPING_FAILED_STATUS, "Unzipping Failed");
        put(AppConstant.UNZIPPING_SUCCESS_STATUS, "Unzipping Data Success");
        put(AppConstant.COMPANY_NAME_IS_REQUIRED, "Company Name Is Required");
        put(AppConstant.USER_NAME_IS_REQUIRED, "Username Is Required");
        put(AppConstant.PASSWORD_IS_REQUIRED, "Password Is Required");
        put(AppConstant.PASSWORD_IS_INCORRECT,"Password Is Incorrect");
        put(AppConstant.DATA_NOT_IMPORT,"You need to import data");
        put(AppConstant.APPLICANT_INFO_NOT_FILL,"Please insert full name, phone");
        put(AppConstant.APPLICANT_CREATE_SUCCESS,"Add User Success");
        put(AppConstant.MALFORMED_URL,"URL Not found");
        put(AppConstant.DOWNLOAD_ERROR,"Download Error");
        put(AppConstant.READ_DATA_ERROR,"Read Data Error");
    }};


}
