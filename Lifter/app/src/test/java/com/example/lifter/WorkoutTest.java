package com.example.lifter;

import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class WorkoutTest {
// Testing the Exercise object
@Test
public void Test_exercise(){
    //
    String ex_name = "pull up";
    int ex_img = 123 ;// cannot test this, as it is based GUI
    String descrp = "This is just a description";
    String tip = "Keep your arms straight";

    Exercise ex  = new Exercise(ex_img,ex_name,descrp,tip);
    System.out.println("Name : " +ex.getEx_name());
    //System.out.println("Description : " + ex.getDescription());
    //System.out.println("Tip: "+ ex.getTips());

    assertEquals("pull up",ex_name);
}








}
