package com.example.lifter;

public class Exercise {
    //variable
    //image if needed
    private int img_ex;
    private string ex_name;

    public Exercise(int img, string name){
        this.img_ex = img;
        this.ex_name = name;
    }

    //getter and setters
    public int getImg_ex() {
        return img_ex;
    }

    public void setImg_ex(int img_ex) {
        this.img_ex = img_ex;
    }

    public string getEx_name() {
        return ex_name;
    }

    public void setEx_name(string ex_name) {
        this.ex_name = ex_name;
    }

    
}
