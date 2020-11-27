package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
        @Override
        public void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

        Button userlogin = (Button) findViewById(R.id.button7);

                  userlogin.setOnClickListener(new View.OnClickListener() {
              public void onClick(View v) {
                  setContentView(R.layout.user_signin);
                }
                  });

              Button adminlogin = (Button) findViewById(R.id.button14);

               adminlogin.setOnClickListener(new View.OnClickListener() {
                  public void onClick(View v) {
                      setContentView(R.layout.admin_signin);
                   }
               });

            //    Button usersignup = (Button) findViewById(R.id.button4);
//
            //    usersignup.setOnClickListener(new View.OnClickListener() {
            //       public void onClick(View v) {
                //          setContentView(R.layout.user_signup);
            //      }
            //     });

            //     Button adminsignup = (Button) findViewById(R.id.button6);

            //     adminsignup.setOnClickListener(new View.OnClickListener() {
            //    public void onClick(View v) {
            //        setContentView(R.layout.admin_signup);
            //      }
            //   });
            //
    }
}