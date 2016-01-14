package com.bsp.iqtest.model;

/**
 * Created by Dell on 08/01/2016.
 */
public class QuestionModel {
    private String questionImagePath;
    private String answerImagePath;
    private Integer numberAnswers;
    private Integer rightChoice;

    public String getQuestionImagePath() {
        return questionImagePath;
    }
    public String setQuestionImagePath(String path) {
        questionImagePath = path;
        return questionImagePath;
    }

    public String getAnswerImagePath() {
        return answerImagePath;
    }
    public String setAnswerImagePath(String path) {
        answerImagePath = path;
        return questionImagePath;
    }

    public Integer getNumberAnswers() {
        return  numberAnswers;
    }
    public Integer setNumberAnswers(Integer nums) {
        numberAnswers = nums;
        return numberAnswers;
    }

    public Integer getRightChoice() {
        return rightChoice;
    }
    public Integer setRightChoice(Integer r) {
        rightChoice = r;
        return rightChoice;
    }
}
