package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

import androidx.appcompat.app.AppCompatActivity;

public class userlayout extends AppCompatActivity {

    EditText mEmail,mPassword,mReEnterPassword;
    Button mSignUpBtn;
    FirebaseAuth fAuth;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_layout);


    }
}