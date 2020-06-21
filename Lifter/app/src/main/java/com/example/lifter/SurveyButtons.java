package com.example.lifter;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SurveyButtons  extends AppCompatActivity
{
    private SurveyQuestions mQuestionLibrary = new SurveyQuestions();

    private TextView mQuestionView;
    private Button mButtonChoiceA;
    private Button mButtonChoiceB;
    private Button mButtonChoiceC;
    private Button mButtonReset;
    //private Button mButtonSubmit;
    //private TextView mShowOutput;

    Integer mQuestionNumber = 0;

    //for now the 3 variables to store the users input
    String WorkoutExperience = "";
    String DaysAvailable = "";

    //All action listeners
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_survey);

        mQuestionView = (TextView) findViewById(R.id.Question);
        mButtonChoiceA = (Button) findViewById(R.id.b_option1);
        mButtonChoiceB = (Button) findViewById(R.id.b_option2);
        mButtonChoiceC = (Button) findViewById(R.id.b_option3);
        mButtonReset = (Button) findViewById(R.id.b_reset);

        updateQuestion();

        //action listener for button a
        mButtonChoiceA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mQuestionNumber == 0) {
                    updateQuestion();
                } else if (mQuestionNumber == 1) {
                    WorkoutExperience = (String) mButtonChoiceA.getText();
                    updateQuestion();
                } else if (mQuestionNumber == 2) {
                    DaysAvailable = (String) mButtonChoiceA.getText();
                }
            }
        });

        //Action listener for button b
        mButtonChoiceB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mQuestionNumber == 0) {
                    updateQuestion();
                } else if (mQuestionNumber == 1) {
                    WorkoutExperience = (String) mButtonChoiceB.getText();
                    updateQuestion();
                } else if (mQuestionNumber == 2) {
                    DaysAvailable = (String) mButtonChoiceB.getText();
                }
            }
        });

        //Action listener for button c
        mButtonChoiceC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mQuestionNumber == 0) {
                    updateQuestion();
                } else if (mQuestionNumber == 1) {
                    WorkoutExperience = (String) mButtonChoiceC.getText();
                    updateQuestion();
                } else if (mQuestionNumber == 2) {
                    DaysAvailable = (String) mButtonChoiceC.getText();
                }
            }
        });

        //action listener for the reset button
        mButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mQuestionNumber = 0;
                updateQuestion();
                DaysAvailable = "";
                WorkoutExperience = "";
            }
        });
    }
    private void updateQuestion() {
        //only updates the ui questions if not all have been answered
        mQuestionView.setText(mQuestionLibrary.getQuestion(mQuestionNumber));
        mButtonChoiceA.setText(mQuestionLibrary.getChoice1(mQuestionNumber));
        mButtonChoiceB.setText(mQuestionLibrary.getChoice2(mQuestionNumber));
        mButtonChoiceC.setText(mQuestionLibrary.getChoice3(mQuestionNumber));
        mQuestionNumber++;
    }
}
