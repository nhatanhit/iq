package com.example.anh.model;

import java.net.Inet4Address;

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

    //constructor
    public UserModel() {
        this.totalQuestions = 0;
        this.numberCompletedQuestions = 0;
        this.numberRightAnswers = 0;
    }

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

    public Integer getTotalQuestions() {
        return totalQuestions;
    }
    public Integer setTotalQuestions(Integer total) {
        totalQuestions = total;
        return this.totalQuestions;
    }

    public Integer getTotalCompletedQuestions() {
        return numberCompletedQuestions;
    }
    public Integer setTotalCompletedQuestions(Integer count) {
        numberCompletedQuestions = count;
        return numberCompletedQuestions;
    }
    public Integer getNumsRightAnswers() {
        return numberRightAnswers;
    }
    public Integer setNumsRightAnswers(int numberRightAnswers) {
        this.numberRightAnswers = numberRightAnswers;
        return this.numberRightAnswers;
    }

}
