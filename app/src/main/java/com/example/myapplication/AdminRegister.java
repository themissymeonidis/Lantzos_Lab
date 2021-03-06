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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class  AdminRegister extends AppCompatActivity {

    EditText mEmail,mPassword,mReEnterPassword;
    Button mSignUpBtn;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    private FirebaseAuth mAuth;

    Boolean valid = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_register);

        mEmail = findViewById(R.id.Admin_Mail);
        mPassword = findViewById(R.id.Admin_Pass);
        mReEnterPassword = findViewById(R.id.Admin_RePass);
        mSignUpBtn = findViewById(R.id.button12);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
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
                if(valid){
                fAuth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {


                        FirebaseUser Admin = fAuth.getCurrentUser();
                        Toast.makeText(AdminRegister.this, "Profile Created", Toast.LENGTH_SHORT).show();
                        DocumentReference df = fStore.collection("Users").document(Admin.getEmail());
                        Map<String, Object> userInfo = new HashMap<>();
                        userInfo.put("UserEmail", mEmail.getText().toString());
                        // specify if the user is admin
                        userInfo.put("isAdmin", "1");
                        df.set(userInfo);
                        startActivity(new Intent(getApplicationContext(), AfterAdminRegister.class));
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
