package com.example.lifter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;

public class SurveyActivity extends AppCompatActivity {

    //initialise the survey questions arrays
    private SurveyQuestions mQuestionLibrary = new SurveyQuestions();

    //create a hashmap for storing users inputs
    HashMap<String, Integer> UserAnswers = new HashMap<String, Integer>();

    //private SurveyButtons mButtonUI = new SurveyButtons();

    //declares all variables
    private TextView mQuestionView;
    private Button mButtonChoiceA;
    private Button mButtonChoiceB;
    private Button mButtonChoiceC;
    private Button mButtonReset;
    private Button mButtonSubmit;
    private EditText mIntegerInput;


    Integer mQuestionNumber = 0;

    //for now the 3 variables to store the users input
    String WorkoutExperience = "";
    String DaysAvailable = "";
    Integer Weight = 45;

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
        mButtonSubmit = (Button) findViewById(R.id.b_Submit);
        mIntegerInput = (EditText) findViewById(R.id.IntInput);

        mButtonSubmit.setVisibility(View.GONE);
        mIntegerInput.setVisibility(View.GONE);

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
                    updateUI();
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
                    updateUI();
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
                    updateUI();
                }
            }
        });

        //action listener for the reset button
        mButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetUI();
            }
        });

        //action listener for selecting the submit button at the end of the questionaire
        mButtonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Weight = Integer.parseInt(String.valueOf(mIntegerInput.getText()));
                SubmitAnswer();
            }
        });
    }

    //Here is the code that will update the UI to the new question
    private void updateQuestion()
    {
        //only updates the ui questions if not all have been answered
        mQuestionView.setText(mQuestionLibrary.getQuestion(mQuestionNumber));
        mButtonChoiceA.setText(mQuestionLibrary.getChoice1(mQuestionNumber));
        mButtonChoiceB.setText(mQuestionLibrary.getChoice2(mQuestionNumber));
        mButtonChoiceC.setText(mQuestionLibrary.getChoice3(mQuestionNumber));
        mQuestionNumber++;
    }

    private void updateUI()
    {
       //the updated UI is supposed to start with the 3rd q in the array
        mQuestionView.setText(mQuestionLibrary.getQuestion(2));

        //buttons get in way of text input
        mButtonChoiceA.setVisibility(View.GONE);
        mButtonChoiceB.setVisibility(View.GONE);
        mButtonChoiceC.setVisibility(View.GONE);

        //makes the integer field and submit buttons visible
        mIntegerInput.setVisibility(View.VISIBLE);
        mButtonSubmit.setVisibility(View.VISIBLE);
    }

    //upon hitting the reset button the UI should be updated to be as if nothing as been selected yet
    private void resetUI()
    {
        //makes the 3 option buttons visible
        mButtonChoiceA.setVisibility(View.VISIBLE);
        mButtonChoiceB.setVisibility(View.VISIBLE);
        mButtonChoiceC.setVisibility(View.VISIBLE);

        //hides the submit button and integer inputs
        mIntegerInput.setVisibility(View.GONE);
        mButtonSubmit.setVisibility(View.GONE);

        //resets all values to their base form
        mQuestionNumber = 0;
        updateQuestion();
        DaysAvailable = "";
        WorkoutExperience = "";
        Weight = 45;
    }

    private void SubmitAnswer()
    {
        //hides all buttons as input is no longer needed
        mQuestionView.setText("Questionnaire inputs successful");
        mIntegerInput.setVisibility(View.GONE);
        mButtonSubmit.setVisibility(View.GONE);
        mButtonReset.setVisibility(View.GONE);
        mButtonChoiceA.setVisibility(View.GONE);
        mButtonChoiceB.setVisibility(View.GONE);
        mButtonChoiceC.setVisibility(View.GONE);

        switch(DaysAvailable)
        {
            case "1 Day":
                UserAnswers.put("Days",1);
                break;
            case "3 days":
                UserAnswers.put("Days",3);
                break;
            case "5 days":
                UserAnswers.put("Days",5);
                break;
        }
        switch(WorkoutExperience)
        {
            case "Beginner":
                UserAnswers.put("experience",1);
                break;
            case "Medium":
                UserAnswers.put("experience",2);
                break;
            case "Hard":
                UserAnswers.put("experience",3);
                break;
        }

        UserAnswers.put("Weight", Weight);

    }



}
