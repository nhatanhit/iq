package com.bsp.iqtest.constant;

import java.util.HashMap;
import java.util.Map;

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
    public static final int UPDATE_DATA_SUCCESS = 20;
    public static final int UPDATE_DATA_FAILED = 21;
    public static final int SAME_OLD_URL = 22;
    public static final int WRONG_FORMAT_DATA_URL = 23;
    public static final int CREATE_DIRECTORY_SUCCESS = 24;
    public static final int CREATE_DIRECTORY_FAILED = 25;
    public static final int DIRECTORY_IS_EXISTED = 26;
    public static final int DURATION_NOT_SET = 27;
    public static final int CONNECTION_ERROR = 29;
    public static final int DOWNLOAD_NOT_FOUND = 30;
    public static final int CAN_NOT_CALL_PHONE = 31;
    public static final int RIGHT_CHOICE_SETTING_WRONG = 32;
    public static final int MISSING_FIELD_IN_FORMAT = 33;

    public static final Integer DEFAULT_NUMBER_QUESTION = 10;

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
        put(AppConstant.SAME_OLD_URL,"Data in this URL has been downloaded");
        put(AppConstant.WRONG_FORMAT_DATA_URL,"Wrong Format Data URL");
        put(AppConstant.DURATION_NOT_SET,"Duration Not Set");
        put(AppConstant.CONNECTION_ERROR,"Connection Error");
        put(AppConstant.DOWNLOAD_NOT_FOUND,"Data Download Not Found");
        put(AppConstant.CAN_NOT_CALL_PHONE,"Can not Call Phone");
        put(AppConstant.RIGHT_CHOICE_SETTING_WRONG,"Right Choice setting wrong");
        put(AppConstant.MISSING_FIELD_IN_FORMAT,"Missing or empty field in json file format.");
    }};


}
