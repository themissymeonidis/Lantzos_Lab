package com.example.myapplication;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


public class AfterAdminRegister extends AppCompatActivity {
    FirebaseAuth fAuth;
    EditText AName, Aaddress, phone;
    CheckBox AMale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_after_register);
        final CheckBox checkBox = (CheckBox) findViewById(R.id.Admin_GMale);
        fAuth = FirebaseAuth.getInstance();
        AName = findViewById(R.id.Admin_Name);
        Aaddress = findViewById(R.id.Admin_Address);
        phone = findViewById(R.id.Admin_Phone);
        Button completeinfo = (Button) findViewById(R.id.btn_Admin_Info);

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    FirebaseUser user = fAuth.getCurrentUser();
    String userEmail = user.getEmail();

    DocumentReference docref = db.collection("Users").document(userEmail);
        completeinfo.setOnClickListener(new View.OnClickListener() {
        public void onClick(View view){

            String gender = "male";
            if (checkBox.isChecked()) {
                gender = "male";
            }

            String name = AName.getText().toString();
            String address = Aaddress.getText().toString();
            String Sphone = phone.getText().toString();


            Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("Name", name);
            userInfo.put("Address", address);
            userInfo.put("Phone", Sphone);
            userInfo.put("Gender", gender);
            docref.update(userInfo);


            startActivity(new Intent(getApplicationContext(), adminlayout.class));
        }
        });




        }
    }


