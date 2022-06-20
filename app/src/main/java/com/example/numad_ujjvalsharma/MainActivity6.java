package com.example.numad_ujjvalsharma;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;

public class MainActivity6 extends AppCompatActivity {

    private LocationRequest locationRequest;
TextView longitudeTextView=null;
TextView latitudeTextView=null;
TextView totalDistanceTextView=null;
double prevLatitue;
double prevLongitude;
boolean isPrevLatAndLongSet=false;
float totalDistance=0;
Button resetDistanceBtn;
    Handler handler =new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);
        longitudeTextView=(TextView) findViewById(R.id.textView6);
        latitudeTextView=(TextView) findViewById(R.id.textView7);
        totalDistanceTextView=(TextView) findViewById(R.id.textView8);
        resetDistanceBtn=(Button) findViewById(R.id.button7);
        if (savedInstanceState != null) {
            totalDistance=savedInstanceState.getFloat("totalDistance");
        }
        Spinner precisionDropDown = (Spinner) findViewById(R.id.spinner);
        String[] items = new String[]{"High Precision", "Low Precision"};
        ArrayAdapter<String> precisionAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        precisionDropDown.setAdapter(precisionAdapter);

        precisionDropDown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String selectedPrecision=parent.getItemAtPosition(position).toString();
                if("High Precision".equals(selectedPrecision)){
                  //high precision high battery consumption
                    startLocationUpdates(100);
                }else{
                    //low battery consumption
                    startLocationUpdates(104);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            //nothing here
            }
        });


        resetDistanceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                totalDistance=0;
                isPrevLatAndLongSet=false;
                prevLatitue=0;
                prevLongitude=0;
                totalDistanceTextView.setText("Total Distance:"+totalDistance+"m");
            }
        });
        ActivityResultLauncher<String[]> locationPermissionRequest =
                registerForActivityResult(new ActivityResultContracts
                                .RequestMultiplePermissions(), result -> {
                            Boolean fineLocationGranted = result.getOrDefault(
                                    Manifest.permission.ACCESS_FINE_LOCATION, false);
                            Boolean coarseLocationGranted = result.getOrDefault(
                                    Manifest.permission.ACCESS_COARSE_LOCATION,false);
                            if (fineLocationGranted != null && fineLocationGranted) {
                                // Precise location access granted.
                            } else if (coarseLocationGranted != null && coarseLocationGranted) {
                                // Only approximate location access granted.
                            } else {
                                // No location access granted.
                            }
                        }
                );

        locationPermissionRequest.launch(new String[] {
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        });

        //high precision high battery consumption
        startLocationUpdates(100);
    }

    protected void startLocationUpdates(int priority) {
        locationRequest = new LocationRequest();
        locationRequest.setPriority(priority);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(2000);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(locationRequest);
        LocationSettingsRequest locationSettingsRequest = builder.build();
        SettingsClient settingsClient = LocationServices.getSettingsClient(this);
        settingsClient.checkLocationSettings(locationSettingsRequest);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.getFusedLocationProviderClient(this).requestLocationUpdates(locationRequest, new LocationCallback() {
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        onLocationChanged(locationResult.getLastLocation());
                    }
                },
                Looper.myLooper());
    }

    public void onLocationChanged(Location location) {

        if(location.getAccuracy()>=10){
            longitudeTextView.setText("longitude:"+location.getLongitude());
            latitudeTextView.setText("longitude:"+location.getLatitude());


            if(isPrevLatAndLongSet){
                float[] results = new float[1];
                android.location.Location.distanceBetween(prevLatitue, prevLongitude,
                        location.getLatitude(), location.getLongitude(), results);
                float distance = results[0];
                totalDistance+=distance;

                totalDistanceTextView.setText("Total Distance:"+totalDistance+"m");
            }else{
                prevLatitue=location.getLatitude();
                prevLongitude=location.getLongitude();
                isPrevLatAndLongSet=true;
                totalDistanceTextView.setText("Total Distance:"+totalDistance+"m");
            }
        }
    }


    @Override
    public void onBackPressed() {

        Runnable dialogBoxRunnable=()->{
            AlertDialog.Builder builder
                    = new AlertDialog
                    .Builder(MainActivity6.this);

            builder.setMessage("Do you want to go back? You will lose total distance");

            builder.setTitle("Warning!");
            builder.setCancelable(false);

            builder.setPositiveButton(
                    "Yes",
                    new DialogInterface
                            .OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog,
                                            int which) {
                            totalDistance=0;
                            isPrevLatAndLongSet=false;
                            finish();
                            dialog.cancel();
                        }
                    });

            builder.setNegativeButton(
                    "No",
                    new DialogInterface
                            .OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog,
                                            int which) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        };
        handler.post(dialogBoxRunnable);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putFloat("totalDistance",totalDistance);
    }

}