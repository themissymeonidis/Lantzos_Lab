package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import static com.example.myapplication.R.id.parent;
import static java.lang.String.valueOf;
import static com.example.myapplication.R.id.after_program;

public class AdminProgram extends AppCompatActivity {
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    private static final String TAG = "MainActivity";
    int i, j, k, NumOfShifts, NumOfDays, workersEachShift, counter, CurrentDay;
    public int day, month, year;
    private PopupWindow popupWindow;
    private LayoutInflater layoutInflater;
    public StringBuilder lista;
    ArrayAdapter<String> adapter;
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
    ArrayList<String> after_program_list = new ArrayList<>();
    Dialog MyDialog;

    private PopupWindow popupWindow2;
    private LayoutInflater layoutInflater2;
    ListView after_program_dil;
    String[] local = {""};

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.program_admin);
        MyDialog = new Dialog(this);

        WorkSaturday = findViewById(R.id.saturday_Checkbox);
        WorkSunday = findViewById(R.id.Sunday_Checkbox);
        title = findViewById(R.id.editTextDate);
        numofdaystext = findViewById(R.id.NumOfDays);
        numofshiftstext = findViewById(R.id.NumOfShifts);
        numofworkerstext = findViewById(R.id.NumOfWorkers);
        tryouts = findViewById(R.id.button6);
        Intent intent = getIntent();
        String themis = intent.getExtras().getString("epuzzle");


        lista = new StringBuilder();
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


        tryouts.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ServiceCast")
            @Override
            public void onClick(View view) {
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
                layoutInflater = (LayoutInflater) getApplication().getSystemService(LAYOUT_INFLATER_SERVICE);
                ViewGroup container = (ViewGroup) layoutInflater.inflate(R.layout.admin_program_popup, null);

                popupWindow = new PopupWindow(container, 850, 1400, true);
                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
                after_program_dil = container.findViewById(after_program);
                Collections.addAll(after_program_list,local);
                after_program_list.remove(after_program_list.get(0));
                adapter = new ArrayAdapter<>(AdminProgram.this, android.R.layout.simple_list_item_1, after_program_list);

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
                            lista.append(date + " ---> Argia");
                            dates1.set(ShiftsMap);
                            break;

                        case 3:
                            GetCurrentDay();
                            if (CurrentDay == 1) {
                                dates1 = fStore.collection("Program").document(date);
                                ShiftsMap.put("Saturday", "Off");
                                lista.append(date + " ---> Saturday Off\n");
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
                                lista.append(date + " ---> Sunday Off \n");
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
                                lista.append(date + " ---> Sunday Off\n");
                            } else if (CurrentDay == 1) {
                                dates1 = fStore.collection("Program").document(date);
                                ShiftsMap.put("Saturday", "Off");
                                lista.append(date + " ---> Saturday Off\n");
                            } else {
                                GiveMap();
                                System.out.println(ShiftsMap);
                                dates1 = fStore.collection("Program").document(date);
                            }
                            dates1.set(ShiftsMap);
                            break;
                    }
                    NumOfShifts = Integer.parseInt(temp2);
                    day = day + 1;
                }
                System.out.println(lista);
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

                after_program_dil.setAdapter(adapter);
                popupWindow.showAsDropDown(tryouts);
                container.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        ((PopupWindow) popupWindow).dismiss();
                        startActivity(new Intent(getApplicationContext(),AdminCalendar.class));
                        return false;
                    }
                });
            }});
    }




    public void GiveMap() {
        StringBuilder abc = new StringBuilder();
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
            abc.append("{ Shift " + map + " : " + finalNames + " } \n");
        }
        lista.append(date + "\n" + abc + "\n");
        after_program_list.add(date + "\n" + abc + "\n");
        adapter.notifyDataSetChanged();

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
}
