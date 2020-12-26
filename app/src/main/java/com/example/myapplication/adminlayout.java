package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.google.firebase.auth.FirebaseAuth;

import androidx.appcompat.app.AppCompatActivity;

public class adminlayout extends AppCompatActivity {


    EditText mEmail,mPassword,mReEnterPassword;
    Button mSignUpBtn;
    FirebaseAuth fAuth;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_layout);
        RelativeLayout relativeclick1 =(RelativeLayout)findViewById(R.id.calendar_admin);
        RelativeLayout gotomanage =(RelativeLayout)findViewById(R.id.Admin_RelativeManage);
        RelativeLayout todo =(RelativeLayout)findViewById(R.id.relativeTodo);



        Button logout = (Button) findViewById(R.id.Alogout_btn);
        fAuth = FirebaseAuth.getInstance();

        logout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(getApplicationContext(),MainActivity.class)); //Go back to home page
                    finish();

            }
        });

        relativeclick1.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){
                startActivity(new Intent(getApplicationContext(),AdminCalendar.class));
            }
        });
        gotomanage.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){
                startActivity(new Intent(getApplicationContext(),adminManage.class));
            }
        });

        todo.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){
                startActivity(new Intent(getApplicationContext(),AdminTodo.class));
            }
        });


    }
}