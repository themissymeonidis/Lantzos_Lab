package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class UserLogin extends AppCompatActivity {

    EditText mEmail,mPassword;
    Button mLoginBtn;
    ProgressBar progressBar;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        mEmail = findViewById(R.id.Email1);
        mPassword = findViewById(R.id.Password1);

        fAuth = FirebaseAuth.getInstance();
        mLoginBtn = findViewById(R.id.user_login_btn);

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Email is Required");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    mPassword.setError("Password is Required");
                    return;
                }

                if(password.length() < 8){
                    mPassword.setError("password must be more than 8 characters");
                    return;
                }



                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()) {
                            Toast.makeText(UserLogin.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));

                        }else{
                            Toast.makeText(UserLogin.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }
        });

    }
}