package com.example.myapplication;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;

import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;


public class UserProfile extends AppCompatActivity {
    FirebaseAuth fAuth;
    TextView getname, getaddress, getphone, getemail, getgender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userprofile);

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


        }
}








