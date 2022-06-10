package com.example.numad_ujjvalsharma;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity5 extends AppCompatActivity {

    Runnable EndSearch;
    Runnable startSearch;
    TextView currentNumberTextView;
    TextView foundPrimeTextView;
    boolean isPrimeSearchOver=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        Button findPrimesBtn= (Button) findViewById(R.id.button4);
        Button endSearchBtn= (Button) findViewById(R.id.button5);
        currentNumberTextView= (TextView) findViewById(R.id.textView4);
        foundPrimeTextView= (TextView) findViewById(R.id.textView5);
        EndSearch = ()->{
            isPrimeSearchOver=true;
        };

         startSearch =()->{

            int num=2;
             isPrimeSearchOver=false;
            while(true){

                final int tempNum=num;
                if(isPrimeSearchOver){
                    break;
                }
                currentNumberTextView.post(()->{
                    currentNumberTextView.setText("Current Number Checked:"+tempNum);
                });
                if(isPrime(num)){
                    foundPrimeTextView.post(()->{
                        foundPrimeTextView.setText("Latest Prime Found:"+tempNum);
                    });

                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                num++;
            }

        };
        findPrimesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(startSearch).start();
            }
        });

        endSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(EndSearch).start();
            }
        });


    }

    private boolean isPrime(int num) {

        if(num==2){
            return true;
        }
        for(int i=2;i<num-1;i++){
            if(num%i==0){
                return false;
            }
        }

        return true;
    }
}