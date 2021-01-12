package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class AdminLogin extends AppCompatActivity {

    EditText mEmail,mPassword;
    Button mLoginBtn;
    FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_login);

        mEmail = findViewById(R.id.Email2);
        mPassword = findViewById(R.id.Password2);
        fAuth = FirebaseAuth.getInstance();
        mLoginBtn = findViewById(R.id.button11);
        FirebaseFirestore db = FirebaseFirestore.getInstance();


        mLoginBtn.setOnClickListener(view -> {
            String email = mEmail.getText().toString().trim();
            String password = mPassword.getText().toString().trim();

            if(TextUtils.isEmpty(email)){
                mEmail.setError("Email is Required");
                return;
            }

            if(TextUtils.isEmpty(password)){
                mPassword.setError("Password is Required");
                return;
            }

            if(password.length() < 8){
                mPassword.setError("password must be more than 8 characters");
                return;
            }



            fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override

                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(task.isSuccessful()) {
                        FirebaseUser mfirebaseuser = fAuth.getCurrentUser();
                        String Uemail = mfirebaseuser.getEmail();
                        DocumentReference docref = db.collection("Users").document(Uemail);
                        docref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    String a = (String) document.get("isAdmin");
                                    int foo = Integer.parseInt(a);
                                    if (document.exists()) {

                                        if (foo == 1) {
                                            startActivity(new Intent(getApplicationContext(),adminlayout.class));
                                            Toast.makeText(AdminLogin.this, "Succesfull LogIn", Toast.LENGTH_SHORT).show();
                                        }else {
                                            FirebaseAuth.getInstance().signOut();
                                            Toast.makeText(AdminLogin.this, "This is not an Admin Account", Toast.LENGTH_SHORT).show();
                                        }

                                    } else {
                                        Toast.makeText(AdminLogin.this, "No such Doc", Toast.LENGTH_SHORT).show();
                                    }

                                } else {
                                    Toast.makeText(AdminLogin.this, "Failed with " + task.getException(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }else{
                        Toast.makeText(AdminLogin.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        });
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