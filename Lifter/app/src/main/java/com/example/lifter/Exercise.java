package com.example.lifter;
//TODO: double base weight, target weight ,Enumeration Difficulty
// Enumeration area workout

import java.util.ArrayList;
import java.util.Arrays;

public class Exercise {
    //variable
    //image if needed
    private int img_ex;
    private String ex_name,description,tips;

    //for both the user and exercise
    private double baseweigth,targetweight,bodyWeight;

    //for different type of lists
    //need to store into the database
    private ArrayList<String> Diffculty = new ArrayList<>(Arrays.asList("BEGINNER","MEDIUM","HARD"));
    private ArrayList<String> exType = new ArrayList<>(Arrays.asList("CHEST","BACK","BICEP","TRICEP","LEGS"));


    //place holder for now
    private String diffcuity;
    private String ExType;


    public Exercise(int img, String name, String desc, String tip, String diff, String ExType,Double bodyWeight){
        this.img_ex = img;
        this.ex_name = name;
        this.description = desc;
        this.tips = tip;
        this.diffcuity = diff;
        this.ExType = ExType;
        this.bodyWeight = bodyWeight;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public double getBaseweigth() {
        return baseweigth;
    }


    public void setBaseweigth(double baseweigth) {
        this.baseweigth = baseweigth;
    }

    public double getTargetweight() {
        return targetweight;
    }

    public void setTargetweight(double targetweight) {
        this.targetweight = targetweight;
    }

    //getter and setters
    public int getImg_ex() {
        return img_ex;
    }

    public void setImg_ex(int img_ex) {
        this.img_ex = img_ex;
    }

    public String getEx_name() {
        return ex_name;
    }

    public void setEx_name(String ex_name) {
        this.ex_name = ex_name;
    }


}
