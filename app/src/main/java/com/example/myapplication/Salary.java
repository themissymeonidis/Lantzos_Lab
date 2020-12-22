package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Salary extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.salary);

        EditText hours = findViewById(R.id.editTextNumberDecimal);
        EditText days = findViewById(R.id.editTextNumber);
        EditText overtime = findViewById(R.id.editTextNumber2);
        TextView income = findViewById(R.id.textView26);
        Button calculate = findViewById(R.id.user_login_btn);

        hours.setText("0");
        days.setText("2");
        overtime.setText("3");



        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String sa = hours.getText().toString();
                String sb = days.getText().toString();
                String sc = overtime.getText().toString();
                float a = Float.parseFloat(sa);
                float b = Float.parseFloat(sb);
                float c = Float.parseFloat(sc);
                float total = (a * (b * 8)) + (a * ((c * 25) / 100));
                income.setText(String.valueOf(total));

            }



        });

    }

}
