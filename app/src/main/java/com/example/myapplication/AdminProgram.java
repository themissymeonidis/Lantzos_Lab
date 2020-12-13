package com.example.myapplication;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Document;

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
        TextView day = findViewById(R.id.textViewday);
        TextView afternoon = findViewById(R.id.textViewafternoon);
        TextView night = findViewById(R.id.textViewnight);
        Intent intent = getIntent();
        String themis = intent.getExtras().getString("epuzzle");
        title.setText(themis);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        FirebaseUser user = fAuth.getCurrentUser();


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                DocumentReference cal = fStore.collection("Program").document(themis);
//                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
//                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        if (dataSnapshot != null) {
//                            Random random = new Random();
//                            int index = random.nextInt((int) dataSnapshot.getChildrenCount());
//                            int count = 0;
//                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                                if (count == index) {
//                                    User user = snapshot.getValue(User.class);
//                                    //na mpei kapou
//                                    return;
//                                }
//                                count++;
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });

                db.collection("Users").addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                        if (e !=null)
                        {

                        }

                        for (DocumentChange documentChange : documentSnapshots.getDocumentChanges())
                        {
                            if (documentChange.getDocument().getData().get("isAdmin").toString().equals("0")) {



                                    String isAttendance = documentChange.getDocument().getData().get("Name").toString();
                                    Log.d(TAG, "Users:  " + isAttendance);
                                    day.append("\n \n" + isAttendance);
                                    if (day.getText() != null) {
                                        afternoon.append("\n \n" + isAttendance);
                                    }



                            }
                        }
                    }
                });
                String end = themis.replace("/", ".");
                DocumentReference df = fStore.collection("Users").document("bradpitt@gmail.com");
                DocumentReference ypo = df.collection("Calendar").document(end);
                Map<String, Object> calendar = new HashMap<>();
                calendar.put("Shift", day);
                calendar.put("Hours", "8");
                //ypo.set(calendar);




            }
        });

    }



}
