package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    @Override
    protected void onStart(){
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        super.onStart();
        FirebaseUser mfirebaseuser = fAuth.getCurrentUser();

        if(mfirebaseuser != null) {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            String userEmail = mfirebaseuser.getEmail();

            DocumentReference docRef = db.collection("Users").document(userEmail);
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        String a = (String) document.get("isAdmin");

                        if (document.exists()) {
                            if (a.equals("1")) {
                                startActivity(new Intent(getApplicationContext(),adminlayout.class));
                            }else {
                                startActivity(new Intent(getApplicationContext(),userlayout.class));
                            }


                        } else {
                            Toast.makeText(MainActivity.this, "No such Doc", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "Failed with " + task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

            }
        }



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
                    startActivity(new Intent(getApplicationContext(),UserLogin.class));

                }
            });


            adminlogin.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(),AdminLogin.class));
                }
            });


            usersignup.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), UserRegister.class)));


            adminsignup.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(),AdminRegister.class));
                }
            });




        }
}