package com.example.myapplication;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserLogin extends AppCompatActivity {

    EditText mEmail,mPassword;
    Button mLoginBtn;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_login);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        mEmail = findViewById(R.id.Email1);
        mPassword = findViewById(R.id.Password1);

        fAuth = FirebaseAuth.getInstance();
        mLoginBtn = findViewById(R.id.button20);

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




            fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(task -> {

                if(task.isSuccessful()) {
                    fAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener(authResult -> {

                        FirebaseUser mfirebaseuser = fAuth.getCurrentUser();
                        String Uemail = mfirebaseuser.getEmail();
                        DocumentReference docref = db.collection("Users").document(Uemail);
                        docref.get().addOnCompleteListener(task1 -> {
                            if (task1.isSuccessful()) {
                                DocumentSnapshot document = task1.getResult();
                                String a = (String) document.get("isAdmin");
                                int foo = Integer.parseInt(a);
                                if (document.exists()) {

                                    if (foo == 0) {
                                        startActivity(new Intent(getApplicationContext(),userlayout.class));
                                        Toast.makeText(UserLogin.this, "Succesfull Log In", Toast.LENGTH_SHORT).show();
                                    }else {
                                        FirebaseAuth.getInstance().signOut();
                                        Toast.makeText(UserLogin.this, "This is not a User Account", Toast.LENGTH_SHORT).show();
                                    }

                                } else {
                                    Toast.makeText(UserLogin.this, "No such Doc", Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                Toast.makeText(UserLogin.this, "Failed with " + task1.getException(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }).addOnFailureListener(e -> Toast.makeText(UserLogin.this, "Error Incorrect Email or Password " + task.getException().getMessage(), Toast.LENGTH_SHORT).show());
                }else{
                    Toast.makeText(UserLogin.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}