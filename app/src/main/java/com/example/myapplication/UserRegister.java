package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class UserRegister extends AppCompatActivity {

    EditText mEmail,mPassword,mReEnterPassword;
    Button mSignUpBtn;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    ProgressBar progressBar;
    private FirebaseAuth mAuth;
    Boolean valid = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);

        mEmail = findViewById(R.id.Email1);
        mPassword = findViewById(R.id.Password1);
        mReEnterPassword = findViewById(R.id.Rpassword);
        mSignUpBtn = findViewById(R.id.button10);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();


        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),userlayout.class));
            finish();
        }



        mSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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



                mSignUpBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
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
                        if(valid){
                            fAuth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {

                                    FirebaseUser User = fAuth.getCurrentUser();
                                    Toast.makeText(UserRegister.this, "Profile Created", Toast.LENGTH_SHORT).show();
                                    DocumentReference df = fStore.collection("Users").document(User.getUid());
                                    Map<String, Object> userInfo = new HashMap<>();
                                    userInfo.put("UserEmail", mEmail.getText().toString());
                                    // specify if the user is admin
                                    userInfo.put("isAdmin", "0");
                                    df.set(userInfo);
                                    startActivity(new Intent(getApplicationContext(), adminlayout.class));
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }
                            });
                        }
                    }
                });
            }
        });
    }
}