package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserRegister extends AppCompatActivity {

    private static final String TAG = "";
    EditText mEmail,mPassword,mReEnterPassword;
    Button mSignUpBtn;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    Boolean valid = true;
    int nextId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_register);

        mEmail = findViewById(R.id.User_Signup_Email);
        mPassword = findViewById(R.id.User_Signup_pass);
        mReEnterPassword = findViewById(R.id.User_Signup_Repass);
        mSignUpBtn = findViewById(R.id.btn_User_Registr);
        ArrayList<Integer> Index = new ArrayList<>();
        ArrayList<String> Email = new ArrayList<>();
        nextId = 1;

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();


        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),userlayout.class));
            finish();
        }
        db.collection("Users")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            if (document.getData().get("isAdmin").toString().equals("0"))
                            {
                                String tempid = document.getData().get("Id").toString();
                                String tempEmail = document.getData().get("UserEmail").toString();
                                int temp = Integer.parseInt(tempid);
                                Index.add(temp);
                                Email.add(tempEmail);
                            }
                        }
                    } else {
                        Log.w(UserRegister.TAG, "Error getting documents.", task.getException());
                    }
                });
                for (int i = 0 ; i < Index.size() ; i++) {
                if (nextId < Index.get(i)) {
                    nextId = Index.get(i);
                    }
                }
        nextId = nextId + 1;


                mSignUpBtn.setOnClickListener(view -> {
                    String email = mEmail.getText().toString().trim();
                    String password = mPassword.getText().toString().trim();

                    if(TextUtils.isEmpty(email)){
                        mEmail.setError("Email is Required");
                        return;
                    }

                    for (int i = 0 ; i < Email.size() ; i++) {
                        if (email.equals(Email.get(i))) {
                            return;
                        }
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
                        fAuth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(authResult -> {

                            FirebaseUser User = fAuth.getCurrentUser();
                            Toast.makeText(UserRegister.this, "Profile Created", Toast.LENGTH_SHORT).show();
                            DocumentReference df = fStore.collection("Users").document(User.getEmail());
                            Map<String, Object> userInfo = new HashMap<>();
                            userInfo.put("UserEmail", mEmail.getText().toString());
                            // specify if the user is admin
                            userInfo.put("isAdmin", "0");
                            userInfo.put("HoursWorked", "0");
                            userInfo.put("Id", nextId);

                            df.set(userInfo);
                            startActivity(new Intent(getApplicationContext(), AfterUserRegister.class));
                        }).addOnFailureListener(e -> System.out.println("error"));
                    }
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