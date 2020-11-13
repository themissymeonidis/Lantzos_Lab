package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

        import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int a;
        int b;
        int c;
        c = (textView) findViewById(R.id.textView);
        Button btn0;
        btn0 = (Button) findViewById(R.id.btnone0) ;
        btn0.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                c = 0;
            }
        });
    }
}