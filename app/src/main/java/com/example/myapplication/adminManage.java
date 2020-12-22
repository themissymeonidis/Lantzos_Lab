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
        EditText firetext = (EditText) findViewById(R.id.FireText);
        Button fire = findViewById(R.id.AdminFire);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        userlist.setText("");
        userlist.setMovementMethod(new ScrollingMovementMethod());

        db.collection("Users").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                if (e !=null)
                {

                }

                for (DocumentChange documentChange : documentSnapshots.getDocumentChanges())
                {
                    if (documentChange.getDocument().getData().get("isAdmin").toString().equals("0")) {

                        String names = documentChange.getDocument().getData().get("Name").toString();
                        String gender = documentChange.getDocument().getData().get("Gender").toString();
                        String address = documentChange.getDocument().getData().get("Address").toString();
                        userlist.append("\n \n" + names + "/" + gender + "/" + address);
                    }
                }
            }
        });
        fire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nametofire = firetext.getText().toString();
                db.collection("Users").addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                        if (e !=null)
                        {

                        }

                        for (DocumentChange documentChange : documentSnapshots.getDocumentChanges())
                        {
                            if (documentChange.getDocument().getData().get("Name").toString().equals(nametofire)) {

                                String emailtofire = documentChange.getDocument().getData().get("UserEmail").toString();

                                db.collection("Users").document(emailtofire)
                                        .delete()
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(adminManage.this, "Deleted User " + emailtofire, Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(adminManage.this, "An error occured while deleting " + nametofire, Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        }
                    }
                });




            }
        });
    }
}