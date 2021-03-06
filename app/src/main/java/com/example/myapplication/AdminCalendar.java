package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class AdminCalendar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_calendar);

        CalendarView calendarView=(CalendarView) findViewById(R.id.calendarView2);
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            String c = dayOfMonth +"/"+ month +"/"+ year;
            Intent i = new Intent(this, AdminProgram.class);
            i.putExtra("epuzzle", String.valueOf(c));
            startActivity(i);
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                setContentView(R.layout.aboutus);
                return true;
            case R.id.item2:
                setContentView(R.layout.help);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}