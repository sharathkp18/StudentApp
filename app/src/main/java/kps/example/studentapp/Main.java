package kps.example.studentapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class Main extends AppCompatActivity {

    LocationManager locationManager;
    final int LOCATION_REQUEST=1;
    private ImageButton multiMedia,userProfile,addStudent,viewStudent,mapView;
    @Override
    public void onBackPressed() {
        startActivity(new Intent(Main.this,SignIn.class));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        multiMedia=findViewById(R.id.multiMedia);
        userProfile=findViewById(R.id.profile);
        addStudent=findViewById(R.id.addStudent);
        viewStudent=findViewById(R.id.viewStudent);
        mapView=findViewById(R.id.mapView);

        multiMediaOptions();
        userProfile();
        addStudents();
        viewStudentsData();
        mapViews();
    }
    public void multiMediaOptions(){
        multiMedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Main.this, "Function not implemented", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void userProfile(){
        userProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Main.this, "Function not implemented", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void addStudents(){
        addStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main.this,AddData.class));
             }
        });
    }
    public void viewStudentsData(){
        viewStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main.this,ViewData.class));
            }
        });
    }
    public void mapViews(){
        mapView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                if (ActivityCompat.checkSelfPermission(
                        Main.this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        Main.this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Main.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST);
                } else {
                    Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if (locationGPS != null) {
                        startActivity(new Intent(Main.this, MapsActivity.class));
                    }
                }
            }
        });
    }
}