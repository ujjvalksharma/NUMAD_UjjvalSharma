package com.example.numad_ujjvalsharma;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity3 extends AppCompatActivity implements MyActivity4RecycleViewAdaptor.NameClickListener {

    MyActivity4RecycleViewAdaptor adapter;
    ArrayList<String> names = new ArrayList<>();
    ArrayList<String> urls = new ArrayList<>();
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            names=savedInstanceState.getStringArrayList("names");
            urls=savedInstanceState.getStringArrayList("urls");
        }
        setContentView(R.layout.activity_main3);
        recyclerView = findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyActivity4RecycleViewAdaptor(this, names,urls);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        FloatingActionButton floatingActionButton= (FloatingActionButton) findViewById(R.id.floatingActionButton2);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText nameEditText= (EditText) findViewById(R.id.edit_name);
                EditText urlEditText= (EditText) findViewById(R.id.edit_url);
                floatingButtonOnClickHelper(nameEditText.getText().toString(),urlEditText.getText().toString());

            }
        });

    }


    public void floatingButtonOnClickHelper(String name, String url){

        if( URLUtil.isValidUrl(url)){
            names.add(name);
            urls.add(url);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            adapter = new MyActivity4RecycleViewAdaptor(this, names,urls);
            adapter.setClickListener(this);
            recyclerView.setAdapter(adapter);
            Snackbar.make(findViewById(android.R.id.content),"Link was successfully created",Snackbar.LENGTH_SHORT).show();
        }else{
            Snackbar.make(findViewById(android.R.id.content),"Link was not created as url is invalid",Snackbar.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onClickName(View view, int position) {

        String url = urls.get(position);
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList("names",names);
        outState.putStringArrayList("urls",urls);
    }

}