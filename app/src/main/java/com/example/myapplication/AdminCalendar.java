package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AdminCalendar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_calendar);

        CalendarView calendarView=(CalendarView) findViewById(R.id.calendarView2);
        TextView textView = (TextView) findViewById(R.id.editTextDate);
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            //textView.setText("");
            String c = dayOfMonth +"/"+ month +"/"+ year;
            //textView.append(c);
            startActivity(new Intent(getApplicationContext(),AdminProgram.class));
        });

    }
}