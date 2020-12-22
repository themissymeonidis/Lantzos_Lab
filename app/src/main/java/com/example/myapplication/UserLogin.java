package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserLogin extends AppCompatActivity {

    EditText mEmail,mPassword;
    Button mLoginBtn;
    ProgressBar progressBar;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        mEmail = findViewById(R.id.Email1);
        mPassword = findViewById(R.id.Password1);

        fAuth = FirebaseAuth.getInstance();
        mLoginBtn = findViewById(R.id.button20);

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
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




                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()) {
                            fAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {

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

                                                    if (foo == 0) {
                                                        startActivity(new Intent(getApplicationContext(),userlayout.class));
                                                        Toast.makeText(UserLogin.this, "Succesfull LogIn", Toast.LENGTH_SHORT).show();
                                                    }else {
                                                        FirebaseAuth.getInstance().signOut();
                                                        Toast.makeText(UserLogin.this, "This is not a User Account", Toast.LENGTH_SHORT).show();
                                                    }

                                                } else {
                                                    Toast.makeText(UserLogin.this, "No such Doc", Toast.LENGTH_SHORT).show();
                                                }

                                            } else {
                                                Toast.makeText(UserLogin.this, "Failed with " + task.getException(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                            }).addOnFailureListener(new OnFailureListener(){
                                @Override
                                public void onFailure(@NonNull Exception e){
                                    Toast.makeText(UserLogin.this, "Error Incorrect Email or Password " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }

                            });
                        }else{
                            Toast.makeText(UserLogin.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }
        });

    }

    private void checkUserAccessLevel(String uid) {
        DocumentReference df= fStore.collection("Users").document(uid);
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.d( "TAG", "onSuccess" + documentSnapshot.getData());
                if(documentSnapshot.getString("isAdmin") == null){
                    startActivity(new Intent(getApplicationContext(), userlayout.class));

                }else if (documentSnapshot.getString("isAdmin") != null){
                    Toast.makeText(UserLogin.this, "Error This is not a User! ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}