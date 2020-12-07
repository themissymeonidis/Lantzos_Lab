package com.example.myapplication;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;

public class AdminProgram extends AppCompatActivity {
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.program_admin);
        EditText title = (EditText) findViewById(R.id.editTextDate);
        Button button = (Button) findViewById(R.id.button);
        TextView lista = (TextView) findViewById(R.id.List);
        Spinner dropdown = findViewById(R.id.spinner);
        Intent intent = getIntent();
        String themis = intent.getExtras().getString("epuzzle");
        title.setText(themis);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        FirebaseUser user = fAuth.getCurrentUser();


        String bardia = "prwi";
        lista.setText("Users: ");


        db.collection("Users").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                if (e !=null)
                {

                }

                for (DocumentChange documentChange : documentSnapshots.getDocumentChanges())
                {
                    if (documentChange.getDocument().getData().get("isAdmin").toString().equals("0")) {

                        String isAttendance = documentChange.getDocument().getData().get("UserEmail").toString();
                        Log.d(TAG, "Users:  " + isAttendance);
                        lista.append("\n \n" + isAttendance);
                    }
                }
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String telos = themis.replace("/", ".");
                DocumentReference df = fStore.collection("Users").document("user@gmail.com");
                DocumentReference ypo = df.collection("Calendar").document(telos);
                Map<String, Object> calendar = new HashMap<>();
                calendar.put("Bardia", bardia);
                calendar.put("Wres", "24");
                ypo.set(calendar);




            }
        });

    }



}
