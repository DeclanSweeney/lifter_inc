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
    CheckBox checkChest, checkBack, checkBicep, checkTricep,checkLeg;

    ArrayList<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        textInputEditText = findViewById(R.id.editText);
        editDesc = findViewById(R.id.editDesc);
        buttonSave = findViewById(R.id.buttonSave);
        buttonDate = findViewById(R.id.buttonDate);

        checkChest = findViewById(R.id.checkChest);
        checkBack = findViewById(R.id.checkBack);
        checkBicep = findViewById(R.id.checkBicep);
        checkTricep = findViewById(R.id.checkTricep);
        checkLeg = findViewById(R.id.checkLeg);

        list = new ArrayList<>();

        //=================== Initialize Table ===================//
        try {
            dbHandler = new mySQLiteDBHandler(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String date = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(new Date());
        buttonDate.setText(date);

        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        //================== Today date ======================//
        selectedDate = Integer.toString(year) + Integer.toString(month) + Integer.toString(day);

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


    }


    /// =============== Insert data on table  ============//
    public void InsertDatabase() {
        dbHandler.insertNote(selectedDate, textInputEditText.getText().toString(), editDesc.getText().toString(),list.toString());
        finish();
    }


    //================= Date picker class ================//
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

        //================= On date click ================//
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

    //=================== check box click ======================//
    public void onCheckboxClicked(View view) {

        boolean checked = ((CheckBox) view).isChecked();

        switch (view.getId()) {
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
            case R.id.checkBicep:
                if(checked){
                    list.add(checkBicep.getTag().toString());
                }else {
                    list.remove(checkBicep.getTag().toString());
                }
                break;
            case R.id.checkTricep:
                if(checked){
                    list.add(checkTricep.getTag().toString());
                }else {
                    list.remove(checkTricep.getTag().toString());
                }
                break;
            case R.id.checkLeg:
                if(checked){
                    list.add(checkLeg.getTag().toString());
                }else {
                    list.remove(checkLeg.getTag().toString());
                }
                break;
        }

    }
}
