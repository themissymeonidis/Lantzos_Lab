package com.example.myapplication;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AdminTodo extends AppCompatActivity {
    List<String> arrayList;
    EditText items;
    ListView itemlist;
    Button add, delete, back;
    ArrayAdapter<String> adapter;
    FirebaseFirestore fStore;

    Calendar startDate = Calendar.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_todo);
        back = (Button) findViewById(R.id.button10);
        add = (Button) findViewById(R.id.add_btn);
        delete = (Button) findViewById(R.id.delete_btn);
        items = (EditText) findViewById(R.id.item_edit_text);
        itemlist = (ListView) findViewById(R.id.items_list);

        ArrayList<String> Names = new ArrayList<>();
        ArrayList<String> Emails = new ArrayList<>();

        fStore = FirebaseFirestore.getInstance();


        back.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Log.d("MyApp",items.getText().toString());

                                    }
                                });

        //////// Diabazei Basi Arxi ///////

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
                                    Names.add(name);
                                    Emails.add(email);
                                }
                            }
                        } else {

                        }
                    }
                });

        //////// Diabazei Basi Telos ///////



        arrayList = new ArrayList<>();
        adapter = new ArrayAdapter<>(AdminTodo.this, android.R.layout.simple_list_item_checked, arrayList);
        itemlist.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        itemlist.setAdapter(adapter);

        String s = items.getText().toString();
        if(s != "") {
            add.setVisibility(View.VISIBLE);
        }

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayList.add(items.getText().toString());
                adapter.notifyDataSetChanged();

                //////// Grafei Basi Arxi ///////

                Map<String, Object> UserTodo = new HashMap<>();

                UserTodo.put("ToDo", items.getText().toString());
                for(int i=0;i < Emails.size();i++) {
                    DocumentReference users = fStore.collection("Users").document(Emails.get(i));
                    users.update(UserTodo);
                    System.out.println(Emails.get(i));
                }
                items.setText("");
                //////// Grafei Basi Telos ///////
            }
        });



        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                itemlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        SparseBooleanArray oi = itemlist.getCheckedItemPositions();
                        final int position1 = position;
                        arrayList.remove(position1);
                        adapter.notifyDataSetChanged();
                    }
                });


            }

        });



    }
}
