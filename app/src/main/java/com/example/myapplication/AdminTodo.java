package com.example.myapplication;

import android.content.ClipData;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class AdminTodo extends AppCompatActivity {
    List<String> arrayList;
    EditText items;
    ListView itemlist;
    Button add, delete;
    ArrayAdapter<String> adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todolist);
        add = (Button) findViewById(R.id.add_btn);
        delete = (Button) findViewById(R.id.delete_btn);
        items = (EditText) findViewById(R.id.item_edit_text);
        itemlist = (ListView) findViewById(R.id.items_list);

//        itemlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                SparseBooleanArray sp = itemlist.getCheckedItemPositions();
//
//                StringBuilder sb= new StringBuilder();
//
//                for(int i=0;i<sp.size();i++){
//                    if(sp.valueAt(i)==true){
//                         String s = ((CheckedTextView) itemlist.getChildAt(i)).getText().toString();
//                        sb = sb.append(" "+s);
//                    }
//                }
//                Toast.makeText(AdminTodo.this, "Selected items are: "+sb.toString(), Toast.LENGTH_LONG).show();
//            }
//        });



        arrayList = new ArrayList<>();
        adapter = new ArrayAdapter<>(AdminTodo.this, android.R.layout.simple_list_item_checked, arrayList);
        itemlist.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        itemlist.setAdapter(adapter);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayList.add(items.getText().toString());
                adapter.notifyDataSetChanged();
                items.setText("");
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
