package com.example.anh.iqtest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.Visibility;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
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
import com.example.anh.utils.Screen;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;

public class InterviewerTestActivity extends CustomBarWithHeaderActivity {
    private Activity mActivity;

    private Timer timer;
    private CountTimerTask timerTask;
    final Handler handler = new android.os.Handler();
    private TextView timeDurationTextView;

    private TableLayout tableAnswer;
    private ImageView testQuestionImage,testAnswerImage;
    private ArrayList<QuestionModel> listQuestion = new ArrayList<QuestionModel>();
    private Integer currentQuestion;
    String unzippedPath;
    private TextView currentQuestionTextView;
    private HashMap<Integer,String> userAnswers;
    private Integer userId;


    public void initializeTimerTask() {
        timerTask = new CountTimerTask(timeChangeListener,0,3,mActivity);
    };

    private View.OnClickListener onSwitchNextQuestion = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            currentQuestion = currentQuestion + 1;
            if(currentQuestion >= listQuestion.size()) {
                currentQuestion = 0;
            }
            Integer iLabel = (currentQuestion + 1);
            Integer iTotalLabel = listQuestion.size();
            currentQuestionTextView.setText(iLabel.toString() + "/" + iTotalLabel.toString());
            switchImage(currentQuestion);

            if(!userAnswers.get(currentQuestion + 1).toString().equals("")) {
                Integer iAnswer = Integer.parseInt(userAnswers.get(currentQuestion + 1).toString());
                ArrayList<LinearLayout> tableCells = Screen.getAllTableCell(tableAnswer);
                markSelectedAnswer(tableCells,iAnswer);
            }
        }
    };

    private View.OnClickListener onSwitchPrevQuestion = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            currentQuestion = currentQuestion - 1;
            if(currentQuestion < 0) {
                currentQuestion = 0;
            }
            Integer iLabel = (currentQuestion + 1);
            Integer iTotalLabel = listQuestion.size();
            currentQuestionTextView.setText(iLabel.toString() + "/" + iTotalLabel.toString());
            switchImage(currentQuestion);

            if(!userAnswers.get(currentQuestion + 1).toString().equals("")) {
                Integer iAnswer = Integer.parseInt(userAnswers.get(currentQuestion + 1).toString());
                ArrayList<LinearLayout> tableCells = Screen.getAllTableCell(tableAnswer);
                markSelectedAnswer(tableCells,iAnswer);
            }
        }
    };

    private void clearAllAnswer() {
        ArrayList<LinearLayout> tableCellLayout = Screen.getAllTableCell(tableAnswer);
        TextView labelAnswer;
        for(int i = 0 ; i < tableCellLayout.size();i++) {
            labelAnswer = (TextView)tableCellLayout.get(i).findViewById(R.id.label_answer);
            if(android.os.Build.VERSION.SDK_INT <  android.os.Build.VERSION_CODES.JELLY_BEAN ) {
                labelAnswer.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle));
            }
            else {
                labelAnswer.setBackground(getResources().getDrawable(R.drawable.circle));
            }
        }
    }


    private View.OnClickListener answerCellListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            //set selected
            clearAllAnswer();
            TextView labelAnswer = (TextView)v.findViewById(R.id.label_answer);
            if(android.os.Build.VERSION.SDK_INT <  android.os.Build.VERSION_CODES.JELLY_BEAN ) {
                labelAnswer.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_circle));
            }
            else {
                labelAnswer.setBackground(getResources().getDrawable(R.drawable.selected_circle));
            }
            String answer = v.getTag().toString();
            userAnswers.put(currentQuestion + 1, answer);
        }
    };


    private CustomListener.TimeChange timeChangeListener = new CustomListener.TimeChange() {
        @Override
        public void onNotifyDurationChange(Integer hour, Integer minute) {
            String durationStamp = hour.toString()+ ":" + minute.toString();
            timeDurationTextView.setText(durationStamp);
        }

        @Override
        public void onNotifyDurationEnd() {
            stopTimer();

            submitTest();
            //forward to Login Activity
            Intent intent = new Intent(mActivity, LoginActivity.class);
            startActivity(intent);
            mActivity.finish();

        }
    };
    public void startTimer() {
        timer = new Timer();
        initializeTimerTask();
        timer.schedule(timerTask,2000,60000);
    }
    public void stopTimer() {
        if(timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interviewer_test);
        mActivity = this;
        bindEvents();
        onSetHeaderText("Interview IQ Test");
        timeDurationTextView = (TextView)findViewById(R.id.timer);
        startTimer();



        ImageView switchNextQuestion = (ImageView)findViewById(R.id.switch_next_question);
        ImageView switchPrevQuestion = (ImageView)findViewById(R.id.switch_prev_question);
        currentQuestionTextView = (TextView)findViewById(R.id.current_question);
        testQuestionImage = (ImageView)findViewById(R.id.test_question_image);
        testAnswerImage = (ImageView)findViewById(R.id.test_question_answers);
        tableAnswer = (TableLayout)findViewById(R.id.table_answer);
        unzippedPath = Environment.getExternalStorageDirectory() + "/unzipped/data/image/";

        bindEvents();
        this.iconBack.setVisibility(View.INVISIBLE);
        onSetHeaderText("Test");

        switchNextQuestion.setOnClickListener(onSwitchNextQuestion);
        switchPrevQuestion.setOnClickListener(onSwitchPrevQuestion);

        parseData();
        currentQuestion = 0;
        userAnswers = new HashMap<>(listQuestion.size());
        if(listQuestion.size() > 0) {
            for(int i = 0 ; i < listQuestion.size();i++) {
                userAnswers.put(i+1,"");
            }
            switchImage(currentQuestion);
        }

    }
    private void markSelectedAnswer(ArrayList<LinearLayout> tableCells ,int answerNumber) {
        for(int i = 0; i < tableCells.size();i++) {
            if(i == answerNumber - 1) {
                LinearLayout answerCell  = tableCells.get(i);
                TextView labelAnswer = (TextView)answerCell.findViewById(R.id.label_answer);
                if(android.os.Build.VERSION.SDK_INT <  android.os.Build.VERSION_CODES.JELLY_BEAN ) {
                    labelAnswer.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_circle));
                }
                else {
                    labelAnswer.setBackground(getResources().getDrawable(R.drawable.selected_circle));
                }
            }
        }
    }

    private void switchImage(Integer currentQuestion) {
        String imagePath = unzippedPath + listQuestion.get(currentQuestion).getQuestionImagePath();
        File imageFile = new File(imagePath);
        if(imageFile.exists()) {
            try {
                Bitmap myBitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
                testQuestionImage.setImageBitmap(myBitmap);
            }
            catch(Exception exc) {
                NoticeDialog.showNoticeDialog(mActivity, "Notice", AppConstant.messages.get(AppConstant.READ_DATA_ERROR));

            }

        }

        imagePath = unzippedPath + listQuestion.get(currentQuestion).getAnswerImagePath();
        imageFile = new File(imagePath);
        if(imageFile.exists()) {
            try {
                Bitmap myBitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
                testAnswerImage.setImageBitmap(myBitmap);
            }
            catch(Exception exc) {
                NoticeDialog.showNoticeDialog(mActivity,"Notice", AppConstant.messages.get(AppConstant.READ_DATA_ERROR));

            }

        }
        QuestionModel questionModel = listQuestion.get(currentQuestion);
        makeAnswerGui(questionModel.getNumberAnswers());

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

        tableRow.setLayoutParams(layoutParams);
        tableAnswer.addView(tableRow);
        //Add view to table
        return tableRow;
    }

    private void makeAnswerGui(int numberAnswer) {
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
                    makeTableCell(tableRow,(i * 4) + j+1);
                }
            }
        }
    }


    private void makeTableCell(TableRow tableRow,Integer label) {
        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout answerCell = (LinearLayout)inflater.inflate(R.layout.anwer_table_cell, tableRow,false);

        answerCell.setOnClickListener(answerCellListener);
        answerCell.setTag(label.toString());


        TextView labelAnswer = (TextView)answerCell.findViewById(R.id.label_answer);
        labelAnswer.setText(label.toString());

       if(android.os.Build.VERSION.SDK_INT <  android.os.Build.VERSION_CODES.JELLY_BEAN ) {
                labelAnswer.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle));
            }
            else {
                labelAnswer.setBackground(getResources().getDrawable(R.drawable.circle));
            }
       tableRow.addView(answerCell);
    }

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

    private void submitTest() {
        Integer totalQuestion = listQuestion.size();
        Integer totalCompletedAnswer = totalQuestion;
        Integer totalRightAnswer = 0;
        for(int i = 0; i < totalQuestion;i++) {
            String userChoice = userAnswers.get(i+1).toString();
            String correctAnswers = listQuestion.get(i).getRightChoice().toString();
            if(userChoice.equals("")) {
                totalCompletedAnswer = totalCompletedAnswer - 1;
            }
            if(userChoice.equals(correctAnswers)) {
                totalRightAnswer = totalRightAnswer + 1;
            }
        }

        //Update to database

    }
}
