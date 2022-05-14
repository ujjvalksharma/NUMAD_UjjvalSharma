package com.example.numad_ujjvalsharma;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.numad_ujjvalsharma.CustomWidget.Ibutton;


public class MainActivity extends AppCompatActivity implements Ibutton {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onClick(View view) {

        Toast.makeText(this,"Name: Ujjval Sharma and Email: Ujjvalksharma@gmail.com",Toast.LENGTH_LONG).show();

    }
}