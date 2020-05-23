package com.example.lifter;

public class SurveyQuestions
{
    //creates string array for the survey questions
    private String mQuestions [] = {
            "What is your current workout experience?",
            "How many days are you available to workout?",
            "What is your current weight?"
    };

    //creates 3d array containing multi choice inputs
    private String mChoices [][] = {
            {"Beginner", "Medium", "Hard"},
            {"1 day", "3 days", "5 days"},
            {"50 - 60 kg", "61 - 80 kg", "81 - 100 kg"}
    };

    //used for
    public String getQuestion(int a) {
        String question = mQuestions[a];
        return question;
    }


    public String getChoice1(int a) {
        String choice0 = mChoices[a][0];
        return choice0;
    }


    public String getChoice2(int a) {
        String choice1 = mChoices[a][1];
        return choice1;
    }

    public String getChoice3(int a) {
        String choice2 = mChoices[a][2];
        return choice2;
    }
}
