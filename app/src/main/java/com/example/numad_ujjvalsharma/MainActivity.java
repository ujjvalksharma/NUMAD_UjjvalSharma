package com.example.numad_ujjvalsharma;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.numad_ujjvalsharma.CustomWidget.Ibutton;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button aboutMe= (Button) findViewById(R.id.button);
        Button clickyClicky= (Button) findViewById(R.id.button2);
        Button linkCollector=(Button) findViewById(R.id.linkCollector);
        aboutMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayToast();
            }
        });


        clickyClicky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MainActivity2.class);
                view.getContext().startActivity(intent);
            }
        });

        linkCollector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MainActivity3.class);
                view.getContext().startActivity(intent);
            }
        });

    }

    public void displayToast(){
        Toast.makeText(this,"Name: Ujjval Sharma and Email: Ujjvalksharma@gmail.com",Toast.LENGTH_LONG).show();
    }
}