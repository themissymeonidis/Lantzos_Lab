package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            Button userlogin = (Button) findViewById(R.id.user_login_btn);
            Button usersignup = (Button) findViewById(R.id.user_signup_btn);
            Button adminlogin = (Button) findViewById(R.id.admin_login_btn);
            Button adminsignup = (Button) findViewById(R.id.admin_signup_btn);


            userlogin.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    setContentView(R.layout.activity_user_login);
                }
            });


            adminlogin.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    setContentView(R.layout.activity_admin_login);
                }
            });


            usersignup.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    setContentView(R.layout.activity_user_register);
                }
            });


            adminsignup.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    setContentView(R.layout.activity_admin_register);
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
                    String StInput = yourEditText.getText().toString();
                    if (StInput != null && StInput.length() > 0) {
                        finalArLi.add(StInput);
                        ArAd.notifyDataSetChanged();
                        yourEditText.setText(Integer.parseInt(""));
                    } else {
                        //EditText is blank
                    }
                }
            });


        }
}