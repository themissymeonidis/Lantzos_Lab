package com.example.myapplication;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import com.google.android.gms.tasks.OnCompleteListener;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import static java.lang.String.valueOf;

public class AdminProgram extends AppCompatActivity {
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    private static final String TAG = "MainActivity";
    int i, j, k;
    public int day, month, year;

    private static ArrayList<Type> mArrayList = new ArrayList<>();;
    String[] listes = {""};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.program_admin);

        final CheckBox checkBox = (CheckBox) findViewById(R.id.checkBox);
        EditText title = (EditText) findViewById(R.id.editTextDate);
        EditText numofdaystext = (EditText) findViewById(R.id.NumOfDays);
        EditText numofshiftstext = (EditText) findViewById(R.id.NumOfShifts);
        Button tryouts = (Button) findViewById(R.id.button6);
        Intent intent = getIntent();
        String themis = intent.getExtras().getString("epuzzle");

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        Calendar startDate = Calendar.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        ArrayList<String> Names = new ArrayList<>();
        ArrayList<String> Hours = new ArrayList<>();
        ArrayList<String> Emails = new ArrayList<>();
        ArrayList<Integer> Keys = new ArrayList<>();
        ArrayList<String> ArgiesMonth = new ArrayList<>();
        ArrayList<String> ArgiesDays = new ArrayList<>();

        String end = themis.replace("/", ".");
        String[] dates  = end.split("\\.");
        day = Integer.parseInt(dates[0]);
        month = Integer.parseInt(dates[1]);
        year = Integer.parseInt(dates[2]);
        month = month + 1;
        dates[1] = dates[1] + 1;

        db.collection("Argies")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                    String Argies = document.getId();
                                    String[] ArgiesSplit  = Argies.split("\\.");
                                    String ArgDay = ArgiesSplit[0];
                                    String ArgMonth = ArgiesSplit[1];
                                    ArgiesDays.add(ArgDay);
                                    ArgiesMonth.add(ArgMonth);

                            }
                            System.out.println("After Documents: " + ArgiesDays + "/" + ArgiesMonth);
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });

        db.collection("Users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (document.getData().get("isAdmin").toString().equals("0"))
                                {
                                    String email = document.getId();
                                    String name = document.getData().get("Name").toString();
                                    String hours = document.getData().get("HoursWorked").toString();
                                    int key = 0;
                                    Emails.add(email);
                                    Hours.add(hours);
                                    Keys.add(key);
                                    Names.add(name);
                                }
                            }
                            System.out.println("After Documents: " + ArgiesDays + "/" + ArgiesMonth);
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });




            tryouts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String temp1 = numofdaystext.getText().toString();
                String temp2 = numofshiftstext.getText().toString();
                int NumOfDays = Integer.parseInt(temp1);
                int NumOfShifts = Integer.parseInt(temp2);
                for (k=0;k<NumOfDays;k++) {
                    dates[0] = valueOf(day);
                    dates[1] = valueOf(month);
                    String date = dates[0]+"."+dates[1]+"."+dates[2];
                    int x = Integer.parseInt(dates[0]);
                    x = x + 1;
                    String yi = valueOf(x);
                    dates[0] = yi;

                    if (month == 2 && day == 28) {
                        day = 1;
                        month = month + 1;
                    }
                    if ((month == 1) || (month == 3) || (month == 5) || (month == 7) || (month == 8) || (month == 9) || (month == 11) || (month == 12)) {
                        if (day > 31) {
                            day = 1;
                            month = month + 1;
                        }
                    }
                    if ((month == 4) || (month == 6) || (month == 10)) {
                        if (day > 30) {
                            day = 1;
                            month = month + 1;
                        }
                    }
                    if (month > 12) {
                        month = 1;
                    }

                    System.out.println(day + "     =>     " + month);
                            boolean flag;
                            flag = false;
                            for (int j=0;j<ArgiesDays.size();j++) {
                                int temporalDays = Integer.parseInt(ArgiesDays.get(j));
                                int temporalMonths = Integer.parseInt(ArgiesMonth.get(j));
                                if (temporalDays == day && temporalMonths == month) {
                                    flag = true;
                                }
                            }

                            System.out.println("FLAG  ===>   " + flag);
                            if (!flag) {
                                System.out.println("Before Shorting: " + Names);
                                System.out.println("Before Shorting: " + Emails);
                                System.out.println("Before Shorting: " + Hours);
                                System.out.println("Before Shorting: " + Keys);

                                for (j=0;j<Hours.size();j++) {
                                    for (int y=j+1;y<Hours.size();y++) {
                                        if (Keys.get(j) > Keys.get(y)) {
                                            String temphours = Hours.get(j);
                                            String tempname = Names.get(j);
                                            String tempemail = Emails.get(j);
                                            int tempkeys = Keys.get(j);

                                            Hours.set(j, Hours.get(y));
                                            Hours.set(y, temphours);
                                            Keys.set(j, Keys.get(y));
                                            Keys.set(y, tempkeys);
                                            Names.set(j, Names.get(y));
                                            Names.set(y, tempname);
                                            Emails.set(j, Emails.get(y));
                                            Emails.set(y, tempemail);
                                        }
                                    }
                                }
                                int megethosAtomon = Names.size();
                                int pyliko = megethosAtomon / NumOfShifts;
                                int remainder = megethosAtomon % NumOfShifts;
                                int counter = 0;
                                i = 0;
                                while (megethosAtomon > i) {

                                        // shift(i) = atomo(i)
                                    if(megethosAtomon - pyliko < counter)
                                        // shift(i) = atomo (i)
                                        // FlagAtomo(i) = true

                                    counter++;
                                    i++;
                                }

                                System.out.println("After Shorting: " + Names);
                                System.out.println("After Shorting: " + Emails);
                                System.out.println("After Shorting: " + Hours);
                                System.out.println("After Shorting: " + Keys);
                                Map<String, Object> ShiftsMap = new HashMap<>();

                                for (i=0;i<NumOfShifts;i++){
                                    DocumentReference UserHours = fStore.collection("Users").document(Emails.get(i));
                                    int prosthesi = Integer.parseInt(Hours.get(i));
                                    Map<String, Object> UserHoursMap = new HashMap<>();
                                    prosthesi = prosthesi + 8;
                                    String prosth = valueOf(prosthesi);
                                    UserHoursMap.put("HoursWorked", prosth);
                                    Hours.set(i, prosth);
                                    Keys.set(i, Keys.get(i) + 1);
                                    System.out.println("Shift =>  " + i + " =>  Tha doyleppsei =>  " + Names.get(i) + " Stis =>  " + date);
                                    int map = i+1;
                                    ShiftsMap.put("Shift"+ map, Names.get(i));
                                    System.out.println("ShiftsMap:  =>    " + ShiftsMap);
                                    UserHours.update(UserHoursMap);
                                }
                                DocumentReference dates = fStore.collection("Program").document(date);
                                dates.set(ShiftsMap);
                            }else{
                                Map<String, Object> ShiftsMap = new HashMap<>();
                                DocumentReference dates = fStore.collection("Program").document(date);
                                ShiftsMap.put("H Zwh", "Einai wrea REPO!");
                                dates.set(ShiftsMap);
                            }
                    day = day + 1;
                }
                System.out.println("After Placing Shifts: " + Names);
                System.out.println("After Placing Shifts: " + Emails);
                System.out.println("After Placing Shifts: " + Hours);
                System.out.println("After Placing Shifts: " + Keys);
            }
        });
    }
}

