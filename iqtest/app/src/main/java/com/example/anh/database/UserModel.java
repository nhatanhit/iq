package com.example.anh.database;

/**
 * Created by anh on 1/5/16.
 */
public class UserModel {
    private Integer id;
    private String fullName;
    private String phone;
    private Integer totalQuestions = 0;
    private Integer numberCompletedQuestions = 0;
    private Integer numberRightAnswers = 0;


    public String getFullName() {
        return fullName;
    }
    public String setFullName(String name) {
        this.fullName = name;
        return this.fullName;
    }
    public String getPhone() {
        return phone;
    }
    public String setPhone(String phone) {
        this.phone = phone;
        return this.phone;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }
    public int setTotalQuestions(Integer total) {
        totalQuestions = total;
        return this.totalQuestions;
    }

    public int getTotalCompletedQuestions() {
        return numberCompletedQuestions;
    }
    public int setTotalCompletedQuestions(Integer count) {
        numberCompletedQuestions = count;
        return numberCompletedQuestions;
    }
    public int getNumsRightAnswers() {
        return numberRightAnswers;
    }
    public int setNumsRightAnswers(int numberRightAnswers) {
        this.numberRightAnswers = numberRightAnswers;
        return this.numberRightAnswers;
    }

}
