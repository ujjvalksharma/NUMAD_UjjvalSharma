package com.example.numad_ujjvalsharma;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Button A= (Button) findViewById(R.id.A);
        Button B= (Button) findViewById(R.id.B);
        Button C= (Button) findViewById(R.id.C);
        Button D= (Button) findViewById(R.id.D);
        Button E= (Button) findViewById(R.id.E);
        Button F= (Button) findViewById(R.id.F);
        TextView myAwesomeTextView = (TextView)findViewById(R.id.textView2);
        A.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myAwesomeTextView.setText("Pressed: A");
            }
        });


        B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myAwesomeTextView.setText("Pressed: B");
            }
        });

        C.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myAwesomeTextView.setText("Pressed: C");
            }
        });


        D.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myAwesomeTextView.setText("Pressed: D");
            }
        });

        E.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myAwesomeTextView.setText("Pressed: E");
            }
        });

        F.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myAwesomeTextView.setText("Pressed: F");
            }
        });

        if(getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE){
            myAwesomeTextView.setText("Pressed: -");
        }else if(getResources().getConfiguration().orientation== Configuration.ORIENTATION_PORTRAIT){
            myAwesomeTextView.setText("Pressed: -");
        }

    }
}