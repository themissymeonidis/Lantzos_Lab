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
    List<String> arrayList;
    List<String> arrayListArgiesMonth;
    List<String> arrayListArgiesDays;
    List<String> arrayListhours;
    List<String> arrayListemails;
    List<Integer> arrayListKeys;
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

        arrayList = new ArrayList<>();
        arrayListhours = new ArrayList<>();
        arrayListemails = new ArrayList<>();
        arrayListKeys = new ArrayList<>();
        arrayListArgiesMonth = new ArrayList<>();
        arrayListArgiesDays = new ArrayList<>();

        Collections.addAll(arrayList,listes);
        Collections.addAll(arrayListhours,listes);
        Collections.addAll(arrayListemails,listes);
        Collections.addAll(arrayListArgiesMonth,listes);
        Collections.addAll(arrayListArgiesDays,listes);

        arrayList.remove(arrayList.get(0));
        arrayListArgiesMonth.remove(arrayListArgiesMonth.get(0));
        arrayListhours.remove(arrayListhours.get(0));
        arrayListemails.remove(arrayListemails.get(0));
        arrayListArgiesDays.remove(arrayListArgiesDays.get(0));

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
                                    arrayListArgiesDays.add(ArgDay);
                                    arrayListArgiesMonth.add(ArgMonth);

                            }
                            System.out.println("After Documents: " + arrayListArgiesDays + "/" + arrayListArgiesMonth);
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
                                    arrayListemails.add(email);
                                    arrayListhours.add(hours);
                                    arrayListKeys.add(key);
                                    arrayList.add(name);
                                }
                            }
                            System.out.println("After Documents: " + arrayListArgiesDays + "/" + arrayListArgiesMonth);
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

                    if (month == 2 && day == 29) {
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
                            for (int j=0;j<arrayListArgiesDays.size();j++) {
                                int temporalDays = Integer.parseInt(arrayListArgiesDays.get(j));
                                int temporalMonths = Integer.parseInt(arrayListArgiesMonth.get(j));
                                if (temporalDays == day && temporalMonths == month) {
                                    flag = true;
                                }
                            }

                            System.out.println("FLAG  ===>   " + flag);
                            if (!flag) {
                                System.out.println("Before Shorting: " + arrayList);
                                System.out.println("Before Shorting: " + arrayListemails);
                                System.out.println("Before Shorting: " + arrayListhours);
                                System.out.println("Before Shorting: " + arrayListKeys);

                                for (j=0;j<arrayListhours.size();j++) {
                                    for (int y=j+1;y<arrayListhours.size();y++) {
                                        if (arrayListKeys.get(j) > arrayListKeys.get(y)) {
                                            String temphours = arrayListhours.get(j);
                                            String tempname = arrayList.get(j);
                                            String tempemail = arrayListemails.get(j);
                                            int tempkeys = arrayListKeys.get(j);

                                            arrayListhours.set(j, arrayListhours.get(y));
                                            arrayListhours.set(y, temphours);
                                            arrayListKeys.set(j, arrayListKeys.get(y));
                                            arrayListKeys.set(y, tempkeys);
                                            arrayList.set(j, arrayList.get(y));
                                            arrayList.set(y, tempname);
                                            arrayListemails.set(j, arrayListemails.get(y));
                                            arrayListemails.set(y, tempemail);
                                        }
                                    }
                                }
                                System.out.println("After Shorting: " + arrayList);
                                System.out.println("After Shorting: " + arrayListemails);
                                System.out.println("After Shorting: " + arrayListhours);
                                System.out.println("After Shorting: " + arrayListKeys);
                                Map<String, Object> ShiftsMap = new HashMap<>();

                                for (i=0;i<NumOfShifts;i++){
                                    DocumentReference UserHours = fStore.collection("Users").document(arrayListemails.get(i));
                                    int prosthesi = Integer.parseInt(arrayListhours.get(i));
                                    Map<String, Object> UserHoursMap = new HashMap<>();
                                    prosthesi = prosthesi + 8;
                                    String prosth = valueOf(prosthesi);
                                    UserHoursMap.put("HoursWorked", prosth);
                                    arrayListhours.set(i, prosth);
                                    arrayListKeys.set(i, arrayListKeys.get(i) + 1);
                                    System.out.println("Shift =>  " + i + " =>  Tha doyleppsei =>  " + arrayList.get(i) + " Stis =>  " + date);
                                    int map = i+1;
                                    ShiftsMap.put("Shift"+ map, arrayList.get(i));
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
                System.out.println("After Placing Shifts: " + arrayList);
                System.out.println("After Placing Shifts: " + arrayListemails);
                System.out.println("After Placing Shifts: " + arrayListhours);
                System.out.println("After Placing Shifts: " + arrayListKeys);
            }
        });
    }
}

