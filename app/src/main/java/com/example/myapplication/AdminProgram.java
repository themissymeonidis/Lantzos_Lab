package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import static java.lang.String.valueOf;

public class AdminProgram extends AppCompatActivity {
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    private static final String TAG = "MainActivity";
    int i, j, k, NumOfShifts, NumOfDays, workersEachShift, counter, CurrentDay;
    public int day, month, year;
    String[] dates;
    String date;
    Map<String, Object> ShiftsMap = new HashMap<>();
    EditText title,numofdaystext,numofshiftstext, numofworkerstext;
    Button tryouts;
    CheckBox WorkSaturday, WorkSunday;
    Boolean valid = true;
    ArrayList<String> Names = new ArrayList<>();
    ArrayList<Integer> Hours = new ArrayList<>();
    ArrayList<String> Emails = new ArrayList<>();
    ArrayList<Integer> Keys = new ArrayList<>();
    ArrayList<String> ArgiesMonth = new ArrayList<>();
    ArrayList<String> ArgiesDays = new ArrayList<>();

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.program_admin);

        WorkSaturday = findViewById(R.id.saturday_Checkbox);
        WorkSunday = findViewById(R.id.Sunday_Checkbox);
        title = findViewById(R.id.editTextDate);
        numofdaystext = findViewById(R.id.NumOfDays);
        numofshiftstext = findViewById(R.id.NumOfShifts);
        numofworkerstext = findViewById(R.id.NumOfWorkers);
        tryouts = findViewById(R.id.button6);
        Intent intent = getIntent();
        String themis = intent.getExtras().getString("epuzzle");


        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        counter = 0;


        String end = themis.replace("/", ".");
        dates  = end.split("\\.");
        day = Integer.parseInt(dates[0]);
        month = Integer.parseInt(dates[1]);
        year = Integer.parseInt(dates[2]);
        month = month + 1;
        title.setText(day + "/" + month + "/" + year);
        CurrentDay = 0;

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
                if(TextUtils.isEmpty(numofdaystext.getText().toString())){
                    numofdaystext.setError("Email is Required");
                    return;
                }
                if(TextUtils.isEmpty(numofshiftstext.getText().toString())){
                    numofshiftstext.setError("Password is Required");
                    return;
                }
                if(TextUtils.isEmpty(numofworkerstext.getText().toString())){
                    numofworkerstext.setError("Password is Required");
                    return;
                }
                String temp1 = numofdaystext.getText().toString();
                String temp2 = numofshiftstext.getText().toString();
                String temp3 = numofworkerstext.getText().toString();
                NumOfDays = Integer.parseInt(temp1);
                NumOfShifts = Integer.parseInt(temp2);
                workersEachShift = Integer.parseInt(temp3);

                if (NumOfShifts * workersEachShift > Names.size()) {
                    Toast.makeText(AdminProgram.this, "Not Enough Staff To Fill The Shifts", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (NumOfShifts * workersEachShift == Names.size()) {
                    Toast.makeText(AdminProgram.this, "Everyone Will Work Each Day", Toast.LENGTH_SHORT).show();
                    return;
                }
                for (k=0;k<NumOfDays;k++) {
                    int switcher = 0;
                    SetDate();

                    System.out.println(day + "     =>     " + month);
                    if (!WorkSaturday.isChecked()) {
                        switcher = 3;
                    }
                    if (!WorkSunday.isChecked()) {
                        switcher = 4;
                    }
                    if (!WorkSunday.isChecked() && !WorkSaturday.isChecked()) {
                        switcher = 5;
                    }
                    for (int j=0;j<ArgiesDays.size();j++) {
                        int temporalDays = Integer.parseInt(ArgiesDays.get(j));
                        int temporalMonths = Integer.parseInt(ArgiesMonth.get(j));
                        if (temporalDays == day && temporalMonths == month) {
                            switcher = 2;
                        }
                    }
                    System.out.println("After Shorting: " + Names);
                    System.out.println("After Shorting: " + Emails);
                    System.out.println("After Shorting: " + Hours);
                    System.out.println("After Shorting: " + Keys);
                    System.out.println("Switcher =>     " + switcher);
                    ShiftsMap = new HashMap<>();
                    DocumentReference dates1;
                    switch (switcher) {
                        case 0:
                            GiveMap();
                            System.out.println(ShiftsMap);
                            dates1 = fStore.collection("Program").document(date);
                            dates1.set(ShiftsMap);
                                break;
                        case 2:
                            dates1 = fStore.collection("Program").document(date);
                            ShiftsMap.put("Argia", "Den doulevei kaneis");
                            dates1.set(ShiftsMap);
                            break;

                        case 3:
                            GetCurrentDay();
                            if (CurrentDay == 1) {
                                dates1 = fStore.collection("Program").document(date);
                                ShiftsMap.put("Saturday", "Off");
                            } else {
                                GiveMap();
                                System.out.println(ShiftsMap);
                                dates1 = fStore.collection("Program").document(date);
                            }
                            dates1.set(ShiftsMap);
                            break;
                        case 4:
                            GetCurrentDay();
                            if (CurrentDay == 2) {
                                dates1 = fStore.collection("Program").document(date);
                                ShiftsMap.put("Sunday", "Off");
                            } else {
                                GiveMap();
                                System.out.println(ShiftsMap);
                                dates1 = fStore.collection("Program").document(date);
                            }
                            dates1.set(ShiftsMap);
                            break;
                        case 5:
                            GetCurrentDay();
                            if (CurrentDay == 2) {
                                dates1 = fStore.collection("Program").document(date);
                                ShiftsMap.put("Sunday", "Off");
                            } else if (CurrentDay == 1) {
                                dates1 = fStore.collection("Program").document(date);
                                ShiftsMap.put("Saturday", "Off");
                            } else {
                                GiveMap();
                                System.out.println(ShiftsMap);
                                dates1 = fStore.collection("Program").document(date);
                            }
                            dates1.set(ShiftsMap);
                            break;
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
    public void GiveMap() {
        for (i=0 ; i<NumOfShifts; i++) {
            StringBuilder NamesForShifts = new StringBuilder();
            for(j=0;j<workersEachShift;j++) {
                if (counter == Names.size()) {
                    counter = 0;
                }
                if (j < workersEachShift) {
                    NamesForShifts.append(Names.get(counter)).append(", ");
                } else {
                    NamesForShifts.append(Names.get(counter));
                }
                Hours.set(counter, Hours.get(counter) + 8);
                Keys.set(counter, Keys.get(counter) + 1);
                counter = counter + 1;

            }
            int map = i+1;
            String finalNames = NamesForShifts.toString();
            ShiftsMap.put("Shift"+ map, finalNames);
        }
    }
    public void SetDate() {
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
            year = year + 1;
        }

        dates[0] = valueOf(day);
        dates[1] = valueOf(month);
        date = dates[0]+"."+dates[1]+"."+dates[2];

    }
    public void GetCurrentDay() {

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.MONTH, month-1);
        cal.set(Calendar.YEAR, year);


            int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
            System.out.println(dayOfWeek);
            if (dayOfWeek == Calendar.SATURDAY) {
                CurrentDay = 1;
            } else if (dayOfWeek == Calendar.SUNDAY) {
                CurrentDay = 2;
            } else {
                CurrentDay = 0;
            }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                setContentView(R.layout.aboutus);
                return true;
            case R.id.item2:
                setContentView(R.layout.help);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

