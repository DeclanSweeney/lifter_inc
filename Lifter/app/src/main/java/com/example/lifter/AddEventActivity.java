package com.example.lifter;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.lifter.database.mySQLiteDBHandler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddEventActivity extends AppCompatActivity {

    private static final String TAG = "AddEventActivity";
    EditText textInputEditText, editDesc;
    Button buttonSave;
    static TextView buttonDate;
    private static String selectedDate;
    private mySQLiteDBHandler dbHandler;
    CheckBox checkLeg, checkChest, checkBack, checkCore, checkArms;

    ArrayList<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        textInputEditText = findViewById(R.id.editText);
        editDesc = findViewById(R.id.editDesc);
        buttonSave = findViewById(R.id.buttonSave);
        buttonDate = findViewById(R.id.buttonDate);

        checkLeg = findViewById(R.id.checkLeg);
        checkChest = findViewById(R.id.checkChest);
        checkBack = findViewById(R.id.checkBack);
        checkCore = findViewById(R.id.checkCore);
        checkArms = findViewById(R.id.checkArms);

        list = new ArrayList<>();

        try {
            dbHandler = new mySQLiteDBHandler(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 InsertDatabase();
              //  Log.e(TAG, "onClick: "+list.toString() );

            }
        });

        buttonDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });

        String date = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(new Date());
        buttonDate.setText(date);


    }

    public void InsertDatabase() {
        dbHandler.insertNote(selectedDate, textInputEditText.getText().toString(), editDesc.getText().toString(),list.toString());
        finish();
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog = new DatePickerDialog(getActivity(), this, year, month, day);
            //  dialog.getDatePicker().setMaxDate(c.getTimeInMillis());
            return dialog;
        }

        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            selectedDate = Integer.toString(year) + Integer.toString(monthOfYear) + Integer.toString(dayOfMonth);
            // buttonDate.setText(selectedDate);

            String date = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;

            SimpleDateFormat spf = new SimpleDateFormat("dd/MM/yyyy");
            Date newDate = null;
            try {
                newDate = spf.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            spf = new SimpleDateFormat("dd MMM yyyy");
            date = spf.format(newDate);
            buttonDate.setText(date);
            // buttonDate.setText(ConverterDate.ConvertDate(year, monthOfYear + 1, dayOfMonth));
        }
    }

    public void onCheckboxClicked(View view) {

        boolean checked = ((CheckBox) view).isChecked();

        switch (view.getId()) {
            case R.id.checkLeg:
                if(checked){
                    list.add(checkLeg.getTag().toString());
                }else {
                    list.remove(checkLeg.getTag().toString());
                }
                break;
            case R.id.checkChest:
                if(checked){
                    list.add(checkChest.getTag().toString());
                }else {
                    list.remove(checkChest.getTag().toString());
                }
                break;

            case R.id.checkBack:
                if(checked){
                    list.add(checkBack.getTag().toString());
                }else {
                    list.remove(checkBack.getTag().toString());
                }

                break;
            case R.id.checkCore:
                if(checked){
                    list.add(checkCore.getTag().toString());
                }else {
                    list.remove(checkCore.getTag().toString());
                }
                break;
            case R.id.checkArms:
                if(checked){
                    list.add(checkArms.getTag().toString());
                }else {
                    list.remove(checkArms.getTag().toString());
                }
                break;

        }

    }
}
