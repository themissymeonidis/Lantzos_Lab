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
import java.util.Collections;
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
    int i, h, r1, i1;
    List<String> arrayList;
    List<String> arrayListhours;
    String[] listes = {""};
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
        h = 0;
        FirebaseUser user = fAuth.getCurrentUser();
        arrayList = new ArrayList<>();
        Collections.addAll(arrayList,listes);
        Collections.addAll(arrayListhours,listes);
        arrayListhours.remove(arrayListhours.get(0));
        arrayList.remove(arrayList.get(0));
        String end = themis.replace("/", ".");
        String[] dates  = end.split("\\.");
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                for(i=1;i<=7;i++) {
                    String date = dates[0]+"."+dates[1]+"."+dates[2];
                    DocumentReference df = fStore.collection("Program").document(date);
                    int x = Integer.parseInt(dates[0]);
                    x = x + 1;
                    String y = String.valueOf(x);
                    dates[0] = y;


                    db.collection("Users").addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                            if (e !=null)
                            {

                            }
                            i1 = 0;
                            for (DocumentChange documentChange : documentSnapshots.getDocumentChanges())
                            {
                                if (documentChange.getDocument().getData().get("isAdmin").toString().equals("0")) {

                                    String isAttendance = documentChange.getDocument().getData().get("Name").toString();
                                    String ishours = documentChange.getDocument().getData().get("HoursWorked").toString();
                                    arrayList.add(isAttendance);
                                    arrayListhours.add(ishours);
                                    i1++;

                                }
                            }

                            Map<String, Object> userInfo = new HashMap<>();
                            Random random = new Random();
                            int h = random.nextInt(4 - 0) + 0;

                            String putin = arrayList.get(h);
                            userInfo.put("morning" , putin);
                            int c = random.nextInt(4 - 0) + 0;
                            while (h == c){
                                c = random.nextInt(4 - 0) + 0;
                            }
                            putin = arrayList.get(c);
                            userInfo.put("evening" , putin);
                            df.set(userInfo);


                        }
                    });


                }
            }
        });

    }



}
