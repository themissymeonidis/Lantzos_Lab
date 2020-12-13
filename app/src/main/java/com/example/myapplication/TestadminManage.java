package com.example.myapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.safetynet.SafetyNetApi;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public abstract class adminManage extends AppCompatActivity  {

    Dialog myDialog;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    FirebaseUser firebaseUser;
    CheckBox checkBox;
    GoogleApiClient googleApiClient;
    Button  fire;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_manage);

        TextView userlist = findViewById(R.id.AdminUserList);
        Button managestaff = (Button) findViewById(R.id.manage_staff2);
        fire = (Button) findViewById(R.id.fire_btn);
        String SiteKey = "6LeHkgQaAAAAAHuIEOJfGbeWzoarPMQFQVemfGRd";
        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(SafetyNet.API)
                .addConnectionCallbacks((GoogleApiClient.ConnectionCallbacks) adminManage.this)
                .build();

        googleApiClient.connect();
        checkBox = findViewById(R.id.check_box);
        myDialog = new Dialog(this);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        userlist.setText("");
        userlist.setMovementMethod(new ScrollingMovementMethod());

        fire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(adminManage.this);
                dialog.setTitle("U good bro?");
                dialog.setMessage("Are u sure u want to delete this account, cause u never gonna get it back!");
                dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        firebaseUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    dialog.setTitle("R u really sure u want to do this?");
                                    dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            firebaseUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()){
                                                        checkBox.setOnClickListener(new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View view) {
                                                                if (checkBox.isChecked()){
                                                                    SafetyNet.SafetyNetApi.verifyWithRecaptcha(googleApiClient,SiteKey)
                                                                            .setResultCallback(new ResultCallback<SafetyNetApi.RecaptchaTokenResult>() {
                                                                                @Override
                                                                                public void onResult(@NonNull SafetyNetApi.RecaptchaTokenResult recaptchaTokenResult) {
                                                                                    Status status = recaptchaTokenResult.getStatus();
                                                                                    if ((status != null) && status.isSuccess()){
                                                                                        Toast.makeText(getApplicationContext(),"Account Deleted",Toast.LENGTH_SHORT).show();
                                                                                        checkBox.setTextColor(Color.GREEN);
                                                                                    }
                                                                                }
                                                                            });
                                                                }else{
                                                                    Toast.makeText(adminManage.this, task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                                                    checkBox.setTextColor(Color.BLACK);
                                                                }
                                                            }
                                                        });
                                                    }else{
                                                        Toast.makeText(adminManage.this, task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                        }
                                    });

                                    dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.dismiss();
                                        }
                                    });
                                }else{
                                    Toast.makeText(adminManage.this, task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });

                dialog.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
            }
        });

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
