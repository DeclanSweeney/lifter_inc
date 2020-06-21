package com.example.lifter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SurveyInteger extends AppCompatActivity {
    private SurveyQuestions mQuestionLibrary = new SurveyQuestions();

    private TextView mQuestionView;
    private Button mButtonReset;
    private Button mButtonSubmit;
    private EditText mIntegerInput;

    Integer Weight;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_survey_int);

        updateQuestion();

        mQuestionView = (TextView) findViewById(R.id.Question);
        mButtonReset = (Button) findViewById(R.id.b_reset);
        mButtonSubmit = (Button) findViewById(R.id.b_Submit);
        mIntegerInput = (EditText) findViewById(R.id.IntegerField);


        //creates action listeners for the
        mButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateUI();
            }
        });
        mButtonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Saveinput();
            }
        });
    }

    private void Saveinput()
    {
        Weight = Integer.parseInt(String.valueOf(mIntegerInput.getText()));
    }

    private void UpdateUI()
    {
        Intent intent = new Intent(SurveyInteger.this, SurveyActivity.class);
        startActivity(intent);
    }

    private void updateQuestion() {
        //only updates the ui questions if not all have been answered
        mQuestionView.setText(mQuestionLibrary.getQuestion(2));

    }

}
