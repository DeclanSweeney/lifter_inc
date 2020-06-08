package com.example.lifter.progress;
import android.content.Context;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;

import com.example.lifter.R;
import com.example.lifter.database.Note;
import com.example.lifter.database.mySQLiteDBHandler;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;

import java.util.Date;
import java.util.Dictionary;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class WorkoutGraphFragment extends Fragment {
    private mySQLiteDBHandler dbHandler;
    private Context mContext;
    private LineChart mpLineChart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_workout_graph, container, false);
        mpLineChart = view.findViewById(R.id.work_out_graph);
        dbHandler = new mySQLiteDBHandler(mContext);

        mpLineChart.getAxisRight().setDrawLabels(false);
        // Get the graph data convert it into Entry object which will be used by the graph.
        final ArrayList<LineGraphLabel> lineGraphLabels = getGraphPoints();
        ArrayList<Entry> entries = new ArrayList<>();

        // Fill Entries list.
        for (LineGraphLabel label: lineGraphLabels) {
            entries.add(new Entry(label.getX(),label.getY()));
        }

        // Now add these labels to the graph
        XAxis xAxis = mpLineChart.getXAxis();
        // Set x axis to appear at the bottom by default they appear at the top.
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);

        LineDataSet dataSet = new LineDataSet(entries,"");
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(dataSet);
        LineData data = new LineData(dataSets);

        mpLineChart.getXAxis().setValueFormatter(new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {

                LineGraphLabel label = getLabel(lineGraphLabels, (int) value);
                return label.getLabel();
            }
        });

        mpLineChart.getAxisLeft().setValueFormatter(new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                return "" +((int) value);
            }
        });

        mpLineChart.getAxisRight().setValueFormatter(new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                return "" +((int) value);
            }
        });

        mpLineChart.getAxisLeft().setGranularity(1f);
        mpLineChart.getXAxis().setDrawGridLines(false);
        mpLineChart.getAxisLeft().setDrawGridLines(false);
        mpLineChart.getAxisRight().setDrawGridLines(false);
        mpLineChart.setData(data);
        mpLineChart.invalidate();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;

    }

    private LineGraphLabel getLabel(ArrayList<LineGraphLabel> labels, int index){
        for (LineGraphLabel l : labels) {
            if(l.getX() == index)
                return l ;
        }
        return null;
    }

    /**
     * Returns all the points we have to show on the graph.
     */
    private ArrayList<LineGraphLabel> getGraphPoints(){
        ArrayList<Note> workouts = dbHandler.getAllNote();

        // Add all the unique dates into the list.
        ArrayList<String> uniqueDates=  new ArrayList<>();

        // Loop through the list.
        for (Note workout: workouts) {
            if(!uniqueDates.contains(workout.getDate())){
                uniqueDates.add(workout.getDate());
            }
        }

        // Create a list to store all the labels.
        ArrayList<LineGraphLabel> labels = new ArrayList<>();
        // Work out the number of work outs done for each date.
        int x = 0;
        for (String date : uniqueDates) {
            int workoutCount = getWorkOutCountByDate(workouts,date);
            labels.add(new LineGraphLabel(x,workoutCount,date));
            x++;
        }

        return labels;
    }

    private int getWorkOutCountByDate(ArrayList<Note> notes, String workoutDate){
        int count = 0;
        for (Note workout: notes) {
            if(workout.getDate().equals(workoutDate) && workout.getStatus().equals("1")){
                count ++;
            }
        }
        return count;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
    }
}
