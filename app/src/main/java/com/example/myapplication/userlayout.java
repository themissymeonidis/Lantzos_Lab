package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

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

        Button logout= (Button) findViewById(R.id.btn_User_Lout);
        RelativeLayout relativeclick1 =(RelativeLayout)findViewById(R.id.relative8);
        RelativeLayout userinfo = (RelativeLayout) findViewById(R.id.relative6);
        relativeclick1.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){
                startActivity(new Intent(getApplicationContext(),Salary.class));
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(),MainActivity.class)); //Go back to home page
                finish();
            }
        });

        userinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),UserProfile.class));
            }
        });

    }
}