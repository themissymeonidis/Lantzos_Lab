package com.example.myapplication;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;


public class UserProfile extends AppCompatActivity {
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    EditText getname, getaddress, getphone, getemail, getgender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.userprofile);


        Button info_update = (Button) findViewById(R.id.button5);

        info_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),UserProfileEdit.class));
            }
        });


        getname = findViewById(R.id.get_name);
        getaddress = findViewById(R.id.get_address);
        getphone = findViewById(R.id.get_phone);
        getemail = findViewById(R.id.get_email);
        getgender = findViewById(R.id.get_gender);

        fAuth = FirebaseAuth.getInstance();


        FirebaseFirestore db = FirebaseFirestore.getInstance();

        FirebaseUser user = fAuth.getCurrentUser();
        String userEmail = user.getEmail();




        db.collection("Users").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                if (e !=null)
                {

                }

                for (DocumentChange documentChange : documentSnapshots.getDocumentChanges())
                {
                    if (documentChange.getDocument().getData().get("UserEmail").toString().equals(userEmail)) {

                        String name = documentChange.getDocument().getData().get("Name").toString();
                        String gender = documentChange.getDocument().getData().get("Gender").toString();
                        String phone = documentChange.getDocument().getData().get("Phone").toString();
                        String address = documentChange.getDocument().getData().get("Address").toString();

                        getname.setText(name);
                        getaddress.setText(address);
                        getphone.setText(phone);
                        getgender.setText(gender);
                        getemail.setText(userEmail);
                    }
                }
            }
        });

        Button edit = (Button) findViewById(R.id.button3);
        Button back = (Button) findViewById(R.id.button2);
        Button confirm = (Button) findViewById(R.id.button5);
        edit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                edit.setVisibility(View.GONE);
                back.setVisibility(View.GONE);
                confirm.setVisibility(View.VISIBLE);
                getname.setEnabled(true);
                getaddress.setEnabled(true);
                getphone.setEnabled(true);
                getgender.setEnabled(true);
                getemail.setEnabled(true);
            }
        });

        DocumentReference docref = db.collection("Users").document(userEmail);
        confirm.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                edit.setVisibility(View.VISIBLE);
                back.setVisibility(View.VISIBLE);
                confirm.setVisibility(View.GONE);
                getname.setEnabled(false);
                getaddress.setEnabled(false);
                getphone.setEnabled(false);
                getgender.setEnabled(false);
                getemail.setEnabled(false);
                String name = getname.getText().toString().trim();
                String address = getaddress.getText().toString().trim();
                String phone = getphone.getText().toString().trim();
                String gender = getgender.getText().toString().trim();
                String email = getemail.getText().toString().trim();
                Map<String, Object> userInfo = new HashMap<>();

                userInfo.put("UserEmail", email);
                userInfo.put("Name", name);
                userInfo.put("Address", address);
                userInfo.put("Phone", phone);
                userInfo.put("Gender", gender);

                docref.update(userInfo);

            }
        });



    }
}







