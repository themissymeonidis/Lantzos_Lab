package com.example.myapplication;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
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


public class AfterUserRegister extends AppCompatActivity {
    FirebaseAuth fAuth;
    EditText AName, Aaddress, phone;
    CheckBox AMale, AFemale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_after_register);
        final CheckBox checkBox = (CheckBox) findViewById(R.id.User_GMale);
        final CheckBox checkBox2 = (CheckBox) findViewById(R.id.User_FMale);
        fAuth = FirebaseAuth.getInstance();
        AName = findViewById(R.id.User_Name);
        Aaddress = findViewById(R.id.User_Address);
        phone = findViewById(R.id.User_Phone);
        Button completeinfo = (Button) findViewById(R.id.btn_User_Info);

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    FirebaseUser user = fAuth.getCurrentUser();
    String userEmail = user.getEmail();

    DocumentReference docref = db.collection("Users").document(userEmail);
        completeinfo.setOnClickListener(new View.OnClickListener() {
        public void onClick(View view){

            String gender;
            if (checkBox.isChecked()) {
                gender = "Male";
            } else
                {
                    gender = "Female";
                }
            if (checkBox2.isChecked()){
                 gender = "Female";
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                setContentView(R.layout.aboutus);
                return true;
            case R.id.item2:
                setContentView(R.layout.help);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    }


