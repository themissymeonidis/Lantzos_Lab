package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
                    startActivity(new Intent(getApplicationContext(),AdminLogin.class));

                }
            });


            adminlogin.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(),AdminLogin.class));
                }
            });


            usersignup.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    setContentView(R.layout.activity_user_register);
                }
            });


            adminsignup.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(),AdminRegister.class));
                }
            });




        }
}