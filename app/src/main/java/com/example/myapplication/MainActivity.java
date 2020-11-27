package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            Button userlogin = (Button) findViewById(R.id.button);

            userlogin.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    setContentView(R.layout.user_signin);
                }
            });

            Button adminlogin = (Button) findViewById(R.id.button3);

            adminlogin.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    setContentView(R.layout.admin_signin);
                }
            });

            Button usersignup = (Button) findViewById(R.id.button4);

            usersignup.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    setContentView(R.layout.user_signup);
                }
            });

            Button adminsignup = (Button) findViewById(R.id.button6);

            adminsignup.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    setContentView(R.layout.admin_signup);
                }
            });

            Button BACK = (Button) findViewById(R.id.button9);

            BACK.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    onBackPressed();
                }
            });

            Button BACK1 = (Button) findViewById(R.id.button10);

            BACK.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    onBackPressed();
                }
            });

            Button BACK2 = (Button) findViewById(R.id.button10);

            BACK.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    onBackPressed();
                }
            });

            EditText yourEditText = (EditText) findViewById(R.id.item_edit_text);
            yourEditText.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(yourEditText, InputMethodManager.SHOW_IMPLICIT);



            ArrayList<String> ArLi = null;
            ArrayAdapter<String> ArAd = new ArrayAdapter<String>(this, R.layout.todolist, ArLi);
            ArLi = new ArrayList<String>();
            Button Add = (Button) findViewById(R.id.add_btn);

            ArrayList<String> finalArLi = ArLi;
            Add.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    String StInput = EditText.getText().toString();
                    if (StInput != null && StInput.length() > 0) {
                        finalArLi.add(StInput);
                        ArAd.notifyDataSetChanged();
                        EditText.setText(Integer.parseInt(""));
                    } else {
                        //EditText is blank
                    }
                }
            });
        }
}