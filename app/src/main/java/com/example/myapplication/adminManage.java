package com.example.myapplication;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class adminManage extends AppCompatActivity {

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_manage);
        TextView userlist = findViewById(R.id.AdminUserList);
        Button fire = findViewById(R.id.fire_btn);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        userlist.setText("");
        userlist.setMovementMethod(new ScrollingMovementMethod());

        db.collection("Users").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                if (e != null) {

                }

                for (DocumentChange documentChange : documentSnapshots.getDocumentChanges()) {
                    if (documentChange.getDocument().getData().get("isAdmin").toString().equals("0")) {

                        String names = documentChange.getDocument().getData().get("Name").toString();
                        String gender = documentChange.getDocument().getData().get("Gender").toString();
                        String address = documentChange.getDocument().getData().get("Address").toString();
                        userlist.append("\n \n" + names + "/" + gender + "/" + address);
                    }
                }
            }
        });


    }
}