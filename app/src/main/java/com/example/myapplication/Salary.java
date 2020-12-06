package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Salary extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.salary);

        TextView hours = findViewById(R.id.editTextNumberDecimal);
        TextView days = findViewById(R.id.editTextNumber);
        TextView overtime = findViewById(R.id.editTextNumber2);
        TextView income = findViewById(R.id.textView26);
        Button calculate = findViewById(R.id.user_login_btn);

        float a = Float.parseFloat(hours.getText().toString());
        float b = Float.parseFloat(days.getText().toString());
        float c = Float.parseFloat(overtime.getText().toString());
        float total = (a * (b * 8)) + (a * ((c * 25) / 100));


        calculate.setOnClickListener(view -> income.setText((int) total));


    }

}
