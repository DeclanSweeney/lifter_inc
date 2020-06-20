package com.example.lifter;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SurveyActivity extends AppCompatActivity {
    private SurveyQuestions mQuestionLibrary = new SurveyQuestions();

    //declares all variables
    private TextView mQuestionView;
    private Button mButtonChoiceA;
    private Button mButtonChoiceB;
    private Button mButtonChoiceC;
    private Button mButtonReset;
    private Button mButtonSubmit;
    private TextView mShowOutput;

    Integer mQuestionNumber = 0;

    //for now the 3 variables to store the users input
    String WorkoutExperience = "";
    String DaysAvailable = "";
    String Weight = "";

    //All action listeners
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_button);

        mQuestionView = (TextView) findViewById(R.id.Question);
        mButtonChoiceA = (Button) findViewById(R.id.b_option1);
        mButtonChoiceB = (Button) findViewById(R.id.b_option2);
        mButtonChoiceC = (Button) findViewById(R.id.b_option3);
        mButtonReset = (Button) findViewById(R.id.b_reset);
        mShowOutput = (TextView) findViewById(R.id.ShowOutput);

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
                    IntegerInputUI();
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
                    IntegerInputUI();
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
                    IntegerInputUI();
                }
            }
        });

        //action listener for the reset button
        mButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ResetUI();
                updateQuestion();
                Weight = "";
                DaysAvailable = "";
                WorkoutExperience = "";
            }
        });

        mButtonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    //Here is the code that will update the UI to the new question
    private void updateQuestion() {
        //only updates the ui questions if not all have been answered
        mQuestionView.setText(mQuestionLibrary.getQuestion(mQuestionNumber));
        mButtonChoiceA.setText(mQuestionLibrary.getChoice1(mQuestionNumber));
        mButtonChoiceB.setText(mQuestionLibrary.getChoice2(mQuestionNumber));
        mButtonChoiceC.setText(mQuestionLibrary.getChoice3(mQuestionNumber));
        mQuestionNumber++;
    }

    private void EndQuestions() {
        mQuestionView.setText("Questions answered successfully");
        mButtonChoiceA.setVisibility(View.GONE);
        mButtonChoiceB.setVisibility(View.GONE);
        mButtonChoiceC.setVisibility(View.GONE);
        mButtonSubmit.setVisibility(View.VISIBLE);
    }

    private void IntegerInputUI() {
        setContentView(R.layout.activity_survey_int);
        mButtonReset = (Button) findViewById(R.id.b_resetINT);
        mQuestionView = (TextView) findViewById(R.id.Question);
        mButtonSubmit = (Button) findViewById(R.id.b_Submit);

        mQuestionView.setText(mQuestionLibrary.getIntQ());
    }

    private void ResetUI()
    {
        setContentView(R.layout.activity_survey_button);
        mQuestionView = (TextView) findViewById(R.id.Question);
        mButtonChoiceA = (Button) findViewById(R.id.b_option1);
        mButtonChoiceB = (Button) findViewById(R.id.b_option2);
        mButtonChoiceC = (Button) findViewById(R.id.b_option3);
        mButtonReset = (Button) findViewById(R.id.b_reset);
        mShowOutput = (TextView) findViewById(R.id.ShowOutput);

        mQuestionNumber = 0;
        updateQuestion();
    }
}
