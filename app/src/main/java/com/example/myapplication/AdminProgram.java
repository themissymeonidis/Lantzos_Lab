package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;

import static java.lang.String.valueOf;

public class AdminProgram extends AppCompatActivity {
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    private static final String TAG = "MainActivity";
    int i, j, k;
    public int day, month, year;
    EditText title,numofdaystext,numofshiftstext;
    Button tryouts;
    CheckBox checkBox;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.program_admin);

        checkBox = findViewById(R.id.checkBox);
        title = findViewById(R.id.editTextDate);
        numofdaystext = findViewById(R.id.NumOfDays);
        numofshiftstext = findViewById(R.id.NumOfShifts);
        tryouts = findViewById(R.id.button6);
        Intent intent = getIntent();
        String themis = intent.getExtras().getString("epuzzle");


        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        ArrayList<String> Names = new ArrayList<>();
        ArrayList<Integer> Hours = new ArrayList<>();
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
        title.setText(day + "/" + month + "/" + year);

        db.collection("Argies")
                .get()
                .addOnCompleteListener(task -> {
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
                });

        db.collection("Users")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            if (document.getData().get("isAdmin").toString().equals("0"))
                            {
                                String email = document.getId();
                                String name = document.getData().get("Name").toString();
                                String hours = document.getData().get("HoursWorked").toString();
                                int temphours = Integer.parseInt(hours);
                                int key = 0;
                                Emails.add(email);
                                Hours.add(temphours);
                                Keys.add(key);
                                Names.add(name);
                            }
                        }
                        System.out.println("After Documents: " + ArgiesDays + "/" + ArgiesMonth);
                    } else {
                        Log.w(TAG, "Error getting documents.", task.getException());
                    }
                });

        for (j=0;j<Hours.size();j++) {
                                    for (int y=j+1;y<Hours.size();y++) {
                                        if (Keys.get(j) > Keys.get(y)) {
                                            int temphours = Hours.get(j);
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



            tryouts.setOnClickListener(view -> {
                String temp1 = numofdaystext.getText().toString();
                String temp2 = numofshiftstext.getText().toString();
                int NumOfDays = Integer.parseInt(temp1);
                int NumOfShifts = Integer.parseInt(temp2);
                int workersEachShift = 2;
                int ArrayLenght = Names.size();
                for (k=0;k<NumOfDays;k++) {
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

                    dates[0] = valueOf(day);
                    dates[1] = valueOf(month);
                    String date = dates[0]+"."+dates[1]+"."+dates[2];

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
                                /// kane Case Switch baze mesa sta case ta HashMaps kai ta Documents meta thn case

//                                for (j=0;j<Hours.size();j++) {
//                                    for (int y=j+1;y<Hours.size();y++) {
//                                        if (Keys.get(j) > Keys.get(y)) {
//                                            int temphours = Hours.get(j);
//                                            String tempname = Names.get(j);
//                                            String tempemail = Emails.get(j);
//                                            int tempkeys = Keys.get(j);
//
//                                            Hours.set(j, Hours.get(y));
//                                            Hours.set(y, temphours);
//                                            Keys.set(j, Keys.get(y));
//                                            Keys.set(y, tempkeys);
//                                            Names.set(j, Names.get(y));
//                                            Names.set(y, tempname);
//                                            Emails.set(j, Emails.get(y));
//                                            Emails.set(y, tempemail);
//                                        }
//                                    }
//                                }
//                                int megethosAtomon = Names.size();
//                                int pyliko = megethosAtomon / NumOfShifts;
//                                int remainder = megethosAtomon % NumOfShifts;
//                                int counter = 0;
//                                i = 0;
//                                while (megethosAtomon > i) {
//
//                                        // shift(i) = atomo(i)
//                                    if(megethosAtomon - pyliko < counter)
//                                        // shift(i) = atomo (i)
//                                        // FlagAtomo(i) = true
//
//                                    counter++;
//                                    i++;
//                                }

                                System.out.println("After Shorting: " + Names);
                                System.out.println("After Shorting: " + Emails);
                                System.out.println("After Shorting: " + Hours);
                                System.out.println("After Shorting: " + Keys);
                                Map<String, Object> ShiftsMap = new HashMap<>();

                                int counter = 0;
                                int anticounter = Names.size() - 1;
                                for (i = 0 ;i < NumOfShifts ; i++){

                                    StringBuilder NamesForShifts = new StringBuilder();

                                    for (j = 0 ; j < workersEachShift ; j++) {
                                        if (j < workersEachShift) {
                                            NamesForShifts.append(Names.get(counter)).append(", ");
                                        } else {
                                            NamesForShifts.append(Names.get(counter));
                                        }

                                        Hours.set(counter, Hours.get(counter) + 8);
                                        Keys.set(counter, Keys.get(counter) + 1);
                                        System.out.println("Shift =>  " + i + " =>  Tha doyleppsei =>  " + Names.get(counter) + " Stis =>  " + date);

                                        int temphours = Hours.get(counter);
                                        String tempname = Names.get(counter);
                                        String tempemail = Emails.get(counter);
                                        int tempkeys = Keys.get(counter);

                                        Hours.set(counter, Hours.get(anticounter));
                                        Hours.set(anticounter, temphours);
                                        Keys.set(counter, Keys.get(anticounter));
                                        Keys.set(anticounter, tempkeys);
                                        Names.set(counter, Names.get(anticounter));
                                        Names.set(anticounter, tempname);
                                        Emails.set(counter, Emails.get(anticounter));
                                        Emails.set(anticounter, tempemail);
                                        counter = counter + 1;
                                        anticounter = anticounter - 1;

                                    }
                                    int map = i+1;
                                    String finalNames = NamesForShifts.toString();
                                    ShiftsMap.put("Shift"+ map, finalNames);
                                }
                                DocumentReference dates1 = fStore.collection("Program").document(date);
                                dates1.set(ShiftsMap);
                            }else{
                                Map<String, Object> ShiftsMap = new HashMap<>();
                                DocumentReference dates1 = fStore.collection("Program").document(date);
                                ShiftsMap.put("Argia", "Den doulevei kaneis");
                                dates1.set(ShiftsMap);
                            }
                    day = day + 1;
                }
                for (i=0;i<Names.size();i++) {
                    DocumentReference UserHours = fStore.collection("Users").document(Emails.get(i));
                    Map<String, Object> UserHoursMap = new HashMap<>();
                    UserHoursMap.put("HoursWorked", Hours.get(i));
                    UserHours.update(UserHoursMap);
                    System.out.println("Maps =>   " + UserHoursMap);
                }
                System.out.println("After Placing Shifts: " + Names);
                System.out.println("After Placing Shifts: " + Emails);
                System.out.println("After Placing Shifts: " + Hours);
                System.out.println("After Placing Shifts: " + Keys);
            });

    }
}

