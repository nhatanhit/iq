package com.bsp.iqtest.task;

import android.content.Context;
import android.os.AsyncTask;

import com.bsp.iqtest.constant.AppConstant;
import com.bsp.iqtest.listener.CustomListener;
import com.bsp.iqtest.utils.Json;
import com.bsp.iqtest.utils.KeyValueDb;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by anh on 12/29/15.
 */
public class CheckDataTask extends AsyncTask<String,Integer,Integer> {

    private Context mContext;
    private CustomListener.onAsynTaskExtractZipProgress progressListener;
    private Integer numberOfQuestions;
    private Integer per = 0;
    public CheckDataTask(Context context,CustomListener.onAsynTaskExtractZipProgress p) {
        this.mContext = context;
        this.progressListener = p;
        this.numberOfQuestions = 0;
    }

    //Return number of questions


    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Integer doInBackground(String... params) {
        //read file json
        String jsonPath = params[1] + "/data/data.json";
        String imageDirectoryPath = params[1] + "/data/image/";
        File f = new File(jsonPath);
        if(f.exists() && !f.isDirectory()) {
            // do something
            try {
                String jsonContent = Json.loadJsonFromAbsolutePath(jsonPath);
                JSONObject mainNode = new JSONObject(jsonContent);
                return checkData(mainNode,imageDirectoryPath);

            }
            catch(JSONException exc)  {
                return AppConstant.JSON_PARSE_DATA_ERROR;
            }
            catch(NullPointerException exc) {
                return AppConstant.JSON_PARSE_DATA_ERROR;
            }

        }
        else {
            return AppConstant.JSON_QUESTION_DATA_NOT_FOUND;
        }

    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        if(integer != AppConstant.JSON_QUESTION_DATA_NOT_FOUND) {
            progressListener.onFinishCheckDataZip(integer,numberOfQuestions.toString());
        }


    }

    private int checkData(JSONObject jObj,String imageDirectoryPath) {
        try {
            int numQuestions = 0;
            String sNumQuestions = jObj.getString("num_questions");
            if(sNumQuestions.equals("")) {
                return AppConstant.MISSING_FIELD_IN_FORMAT;
            }
            else {
                numQuestions =  Integer.parseInt(sNumQuestions,10);

            }

            this.numberOfQuestions = numQuestions;


            ArrayList<String> questionsPath = new ArrayList<String>();
            ArrayList<String> answersPath = new ArrayList<String>();

            int totalFile = numQuestions * 2;
            File imageDirectory = new File(imageDirectoryPath);
            if(imageDirectory.listFiles().length  == totalFile) {
                JSONArray listQuestions = jObj.getJSONArray("questions");
                for(int i = 0 ; i < listQuestions.length();i++) {
                    JSONObject questionObject = listQuestions.getJSONObject(i);
                    String questionPath = questionObject.getString("question_data");
                    if(questionPath.equals("")) {
                        return AppConstant.MISSING_FIELD_IN_FORMAT;
                    }
                    else {
                        questionsPath.add(questionPath);
                    }


                    String answerPath = questionObject.getString("answers");
                    if(answerPath.equals("")) {
                        return AppConstant.MISSING_FIELD_IN_FORMAT;
                    }
                    else {
                        answersPath.add(answerPath);
                    }


                    String sNumberAnswers = questionObject.getString("num_answers_per_question");
                    String sRightChoice = questionObject.getString("right_choice");

                    if(sNumberAnswers.equals("") || sRightChoice.equals("")) {
                        return AppConstant.MISSING_FIELD_IN_FORMAT;
                    }
                    else {
                        Integer numsAnswers = Integer.parseInt(sNumberAnswers);
                        Integer rightChoice = Integer.parseInt(sRightChoice);
                        if(rightChoice > numsAnswers) {
                            return AppConstant.RIGHT_CHOICE_SETTING_WRONG;
                        }
                    }


                }

                for(int i = 0 ; i < questionsPath.size();i++) {
                    String path = imageDirectoryPath + questionsPath.get(i);
                    File imageFile = new File(path);
                    if(!imageFile.isFile() || !imageFile.exists()) {
                        return AppConstant.DOWNLOAD_DATA_IMAGE_NOT_FOUND;
                    }
                }

                for(int i = 0 ; i < answersPath.size();i++) {
                    String path = imageDirectoryPath + answersPath.get(i);
                    File imageFile = new File(path);
                    if(!imageFile.isFile() || !imageFile.exists()) {
                        return AppConstant.DOWNLOAD_DATA_IMAGE_NOT_FOUND;
                    }
                }
            }
            else {
                return AppConstant.DOWNLOAD_DATA_ERROR_FAILED_CHECKSUM;
            }

        }
        catch(JSONException exception) {
            return AppConstant.MISSING_FIELD_IN_FORMAT;
        }
        catch(NumberFormatException formatException) {
            return AppConstant.JSON_PARSE_DATA_ERROR;
        }

        //save to shared preferences , content of json file
        KeyValueDb.setValue(mContext.getApplicationContext(),"question_data",jObj.toString());
        return AppConstant.CHECK_DOWNLOAD_DATA_SUCCES;
    }
}
