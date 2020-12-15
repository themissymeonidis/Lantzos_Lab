package com.example.myapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.safetynet.SafetyNetApi;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public  class adminManage extends AppCompatActivity {

    Dialog myDialog;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    CheckBox checkBox;
    Button fire;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_manage);

        TextView userlist = findViewById(R.id.AdminUserList);
        Button managestaff = (Button) findViewById(R.id.manage_staff2);
        fire = (Button) findViewById(R.id.fire_btn);


        checkBox = findViewById(R.id.check_box);
        myDialog = new Dialog(this);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        userlist.setText("");
        userlist.setMovementMethod(new ScrollingMovementMethod());



        db.collection("Users").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                if (e != null) {

                }

                for (DocumentChange documentChange : documentSnapshots.getDocumentChanges()) {
                    if (documentChange.getDocument().getData().get("isAdmin").toString().equals("0")) {

                        String names = documentChange.getDocument().getData().get("Name").toString();
                        String gender = documentChange.getDocument().getData().get("Gender").toString();
                        String address = documentChange.getDocument().getData().get("Address").toString();
                        userlist.append("\n \n" + names + "/" + gender + "/" + address);
                    }
                }
            }
        });


    }

    public void manage_staff(View v) {
        TextView close_popup;
        myDialog.setContentView(R.layout.popupmenu);
        close_popup = (TextView) myDialog.findViewById(R.id.close_popup);
        Button fire_btn = (Button) myDialog.findViewById(R.id.fire_btn);
        close_popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog.dismiss();
            }
        });
        myDialog.show();
    }

    public void next_user2(View v) {
        TextView close_popup;
        myDialog.setContentView(R.layout.popup2);
        close_popup = (TextView) myDialog.findViewById(R.id.close_popup);
        Button fire_btn = (Button) myDialog.findViewById(R.id.fire_btn);
        close_popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog.dismiss();
            }
        });
        myDialog.show();
    }

    public void previous_user1(View v) {
        TextView close_popup;
        myDialog.setContentView(R.layout.popupmenu);
        close_popup = (TextView) myDialog.findViewById(R.id.close_popup);
        Button fire_btn = (Button) myDialog.findViewById(R.id.fire_btn);
        close_popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog.dismiss();
            }
        });
        myDialog.show();
    }

    public void next_user3(View v) {
        TextView close_popup;
        myDialog.setContentView(R.layout.popup3);
        close_popup = (TextView) myDialog.findViewById(R.id.close_popup);
        Button fire_btn = (Button) myDialog.findViewById(R.id.fire_btn);
        close_popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog.dismiss();
            }
        });
        myDialog.show();
    }
    public void previous_user2(View v) {
        TextView close_popup;
        myDialog.setContentView(R.layout.popup2);
        close_popup = (TextView) myDialog.findViewById(R.id.close_popup);
        Button fire_btn = (Button) myDialog.findViewById(R.id.fire_btn);
        close_popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog.dismiss();
            }
        });
        myDialog.show();
    }


}

