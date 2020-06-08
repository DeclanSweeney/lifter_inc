package com.example.lifter;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lifter.database.mySQLiteDBHandler;

import java.util.ArrayList;

public class SuggestedEventActivity extends AppCompatActivity {

    private static final String TAG = "SuggestedEventActivity";
    CheckBox checkSun, checkMon, checkTue, checkWed, checkThu, checkFri, checkSat;

    ArrayList<String> list;
    Button buttonSubmit;
    private mySQLiteDBHandler dbHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggested_event);

        checkSun = findViewById(R.id.checkSun);
        checkMon = findViewById(R.id.checkMon);
        checkTue = findViewById(R.id.checkTue);
        checkWed = findViewById(R.id.checkWed);
        checkThu = findViewById(R.id.checkThu);
        checkFri = findViewById(R.id.checkFri);
        checkSat = findViewById(R.id.checkSat);
        buttonSubmit = findViewById(R.id.buttonSubmit);

        list = new ArrayList<>();

        //=================== Initialize Table ===================//
        try {
            dbHandler = new mySQLiteDBHandler(this);
        } catch (Exception e) {
            e.printStackTrace();
        }


        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InsertDatabase();
               // Log.e(TAG, "onClick: " + list.toString());
            }
        });

    }

    /// =============== Insert data on table  ============//
    public void InsertDatabase() {
        dbHandler.clearSuggestedList();
        dbHandler.insertSuggested(list.toString());
        finish();
    }

    //=================== check box click ======================//
    public void onCheckboxClicked(View view) {

        boolean checked = ((CheckBox) view).isChecked();

        switch (view.getId()) {
            case R.id.checkSun:
                if (checked) {
                    list.add(checkSun.getTag().toString());
                } else {
                    list.remove(checkSun.getTag().toString());
                }
                break;
            case R.id.checkMon:
                if (checked) {
                    list.add(checkMon.getTag().toString());
                } else {
                    list.remove(checkMon.getTag().toString());
                }
                break;

            case R.id.checkTue:
                if (checked) {
                    list.add(checkTue.getTag().toString());
                } else {
                    list.remove(checkTue.getTag().toString());
                }

                break;
            case R.id.checkWed:
                if (checked) {
                    list.add(checkWed.getTag().toString());
                } else {
                    list.remove(checkWed.getTag().toString());
                }
                break;
            case R.id.checkThu:
                if (checked) {
                    list.add(checkThu.getTag().toString());
                } else {
                    list.remove(checkThu.getTag().toString());
                }
                break;
            case R.id.checkFri:
                if (checked) {
                    list.add(checkFri.getTag().toString());
                } else {
                    list.remove(checkFri.getTag().toString());
                }
                break;
            case R.id.checkSat:
                if (checked) {
                    list.add(checkSat.getTag().toString());
                } else {
                    list.remove(checkSat.getTag().toString());
                }
                break;

        }

    }

}
