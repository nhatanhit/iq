package com.example.anh.task;

import android.content.Context;
import android.os.AsyncTask;

import com.example.anh.constant.AppConstant;
import com.example.anh.listener.CustomListener;
import com.example.anh.utils.Json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by anh on 12/29/15.
 */
public class CheckDataTask extends AsyncTask<String,Void,Integer> {

    private Context mContext;
    private CustomListener.onAsynTaskExtractZipProgress progressListener;
    private Integer numberOfQuestions;

    public CheckDataTask(Context context,CustomListener.onAsynTaskExtractZipProgress p) {
        this.mContext = context;
        this.progressListener = p;
    }

    //Return number of questions


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
        progressListener.onFinishCheckDataZip(integer,numberOfQuestions.toString());
    }

    private int checkData(JSONObject jObj,String imageDirectoryPath) {
        try {
            int numQuestions =  Integer.parseInt(jObj.getString("num_questions"),10);
            this.numberOfQuestions = numQuestions;

            int numAnswersPerQuestions = Integer.parseInt(jObj.getString("num_answers_per_question"),10);

            ArrayList<String> questionsPath = new ArrayList<String>();
            ArrayList<String> answersPath = new ArrayList<String>();

            int totalFile = numQuestions * 2;
            File imageDirectory = new File(imageDirectoryPath);
            if(imageDirectory.listFiles().length  == totalFile) {
                JSONArray listQuestions = jObj.getJSONArray("questions");
                for(int i = 0 ; i < listQuestions.length();i++) {
                    JSONObject questionObject = listQuestions.getJSONObject(i);
                    String questionPath = questionObject.getString("question_data");
                    questionsPath.add(questionPath);

                    String answerPath = questionObject.getString("answers");
                    answersPath.add(answerPath);

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
            return AppConstant.JSON_PARSE_DATA_ERROR;
        }
        catch(NumberFormatException formatException) {
            return AppConstant.JSON_PARSE_DATA_ERROR;
        }


        return AppConstant.CHECK_DOWNLOAD_DATA_SUCCES;
    }
}
