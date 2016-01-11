package com.example.anh.iqtest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import java.io.File;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.anh.constant.AppConstant;
import com.example.anh.listener.CustomListener;
import com.example.anh.model.QuestionModel;
import com.example.anh.task.CountTimerTask;
import com.example.anh.utils.KeyValueDb;
import com.example.anh.utils.NoticeDialog;

import org.json.JSONArray;
import org.json.JSONObject;

public class QuestionActivity extends CustomBarWithHeaderActivity {
    private Activity mActivity;
    private ArrayList<QuestionModel> listQuestion = new ArrayList<QuestionModel>();
    private Integer currentQuestion;
    private ImageView currentQuestionView,currentAnswerView;
    private TableLayout tableAnswer;


    String unzippedPath;
    View.OnClickListener nextQuestionClick = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            currentQuestion = currentQuestion + 1;
            if(currentQuestion >= listQuestion.size()) {
                currentQuestion = 0;

            }
            switchImage(currentQuestion);

        }
    };

    View.OnClickListener prevQuestionClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            currentQuestion = currentQuestion - 1;
            if(currentQuestion < 0) {
                currentQuestion = 0;
            }
            switchImage(currentQuestion);
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        currentQuestionView = (ImageView)findViewById(R.id.view_question_image);
        currentAnswerView = (ImageView)findViewById(R.id.view_answer_image);
        tableAnswer = (TableLayout)findViewById(R.id.view_table_answer);



        unzippedPath = Environment.getExternalStorageDirectory() + "/unzipped/data/image/";
        mActivity = this;
        currentQuestion = 0;
        onClickBackListener = new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, ManageActivity.class);
                startActivity(intent);
                mActivity.finish();
            }
        };
        bindEvents();
        onSetHeaderText("View Question/Answers");

        parseData();

        ImageView viewPreviousQuestions = (ImageView)findViewById(R.id.view_previous_question);
        ImageView viewNextQuestion = (ImageView)findViewById(R.id.view_next_question);
        if(listQuestion.size() > 0) {
            switchImage(currentQuestion);
        }

        viewNextQuestion.setOnClickListener(nextQuestionClick);
        viewPreviousQuestions.setOnClickListener(prevQuestionClick);


    }

    /**
     * This function for parsing json data saved in Share Preferences
     */
    private void parseData() {
        String questionData =  KeyValueDb.getValue(getApplicationContext(), "question_data");
        try {
            JSONObject jObj = new JSONObject(questionData);
            JSONArray listQuestions = jObj.getJSONArray("questions");
            for(int i = 0 ; i < listQuestions.length();i++) {
                JSONObject questionObject = listQuestions.getJSONObject(i);
                String questionPath = questionObject.getString("question_data");
                String answerPath = questionObject.getString("answers");
                Integer numAnswers = Integer.parseInt(questionObject.getString("num_answers_per_question"));
                Integer rightChoice = Integer.parseInt(questionObject.getString("right_choice"));
                QuestionModel question = new QuestionModel();
                question.setAnswerImagePath(answerPath);
                question.setQuestionImagePath(questionPath);
                question.setNumberAnswers(numAnswers);
                question.setRightChoice(rightChoice);

                listQuestion.add(question);

            }
        }
        catch (Exception exc) {

        }



    }

    private void switchImage(Integer currentQuestion) {
        String imagePath = unzippedPath + listQuestion.get(currentQuestion).getQuestionImagePath();
        File imageFile = new File(imagePath);
        if(imageFile.exists()) {
            try {
                Bitmap myBitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
                currentQuestionView.setImageBitmap(myBitmap);
            }
            catch(Exception exc) {
                NoticeDialog.showNoticeDialog(mActivity,"Notice", AppConstant.messages.get(AppConstant.READ_DATA_ERROR));

            }

        }

        imagePath = unzippedPath + listQuestion.get(currentQuestion).getAnswerImagePath();
        imageFile = new File(imagePath);
        if(imageFile.exists()) {
            try {
                Bitmap myBitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
                currentAnswerView.setImageBitmap(myBitmap);
            }
            catch(Exception exc) {
                NoticeDialog.showNoticeDialog(mActivity,"Notice", AppConstant.messages.get(AppConstant.READ_DATA_ERROR));

            }

        }
        QuestionModel questionModel = listQuestion.get(currentQuestion);
        makeAnswerGui(questionModel.getNumberAnswers(),questionModel.getRightChoice());


    }

    private void makeAnswerGui(int numberAnswer,int rightChoice) {
        this.tableAnswer.removeAllViewsInLayout();
        int row = 0;

        int col = (numberAnswer % 4);
        if(col != 0) {
            row = (numberAnswer / 4) + 1;
        }
        else {
            row = numberAnswer / 4;
        }

        for(int i = 0; i < row ; i++) {
            TableRow tableRow =  addAnswerTableRow(4);
            for(int j = 0; j < 4; j++) {
                if(((i  * 4) + j + 1 <= numberAnswer)) {
                    if((i * 4) + j + 1 == rightChoice) {
                        makeTableCell(tableRow,true,(i * 4) + j+1);
                    }
                    else {
                        makeTableCell(tableRow,false,(i * 4) + j+1);
                    }

                }
            }
        }


    }

    private TableRow addAnswerTableRow(int weightSum) {
        TableRow tableRow = new TableRow(mActivity);
        tableRow.setWeightSum((float) weightSum);
        if(android.os.Build.VERSION.SDK_INT <  android.os.Build.VERSION_CODES.JELLY_BEAN ) {
            tableRow.setBackgroundDrawable(getResources().getDrawable(R.drawable.table_row_answer));
        }
        else {
            tableRow.setBackground(getResources().getDrawable(R.drawable.table_row_answer));
        }

        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,TableLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 10, 0, 0);
//        tableRow.setPadding(0,5,0,5);
        tableRow.setLayoutParams(layoutParams);
        tableAnswer.addView(tableRow);
        //Add view to table
        return tableRow;
    }

    private void makeTableCell(TableRow tableRow,boolean isRightAnswer,Integer label) {
        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout answerCell = (LinearLayout)inflater.inflate(R.layout.anwer_table_cell, tableRow,false);

        TextView labelAnswer = (TextView)answerCell.findViewById(R.id.label_answer);
        labelAnswer.setText(label.toString());
        if(isRightAnswer) {
            if(android.os.Build.VERSION.SDK_INT <  android.os.Build.VERSION_CODES.JELLY_BEAN ) {
                labelAnswer.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_circle));
            }
            else {
                labelAnswer.setBackground(getResources().getDrawable(R.drawable.selected_circle));
            }
        }
        else {
            if(android.os.Build.VERSION.SDK_INT <  android.os.Build.VERSION_CODES.JELLY_BEAN ) {
                labelAnswer.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle));
            }
            else {
                labelAnswer.setBackground(getResources().getDrawable(R.drawable.circle));
            }
        }

        tableRow.addView(answerCell);
    }

}
