package com.example.mapsapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mapsapplication.data.DatabaseHelper;
import com.example.mapsapplication.model.Restaurant;

public class AddLocationActivity extends AppCompatActivity {
    LocationManager locationManager;
    LocationListener locationListener;
    String locationName;
    String userLat;
    String userLng;
    String locationLat;
    String locationLng;


    EditText nameEditText;
    EditText locationEditText;

    DatabaseHelper db;
    String currentLoaction;
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {
            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_loaction);

        nameEditText = findViewById(R.id.nameEditText);
        locationEditText = findViewById(R.id.locationEditText);
        db = new DatabaseHelper(AddLocationActivity.this);

        Button getCurrentLocationButton = findViewById(R.id.getCurrentLoactionButton);
        Button showMapButton = findViewById(R.id.showMapButton);
        Button saveButton = findViewById(R.id.saveButton);




        Intent intent = getIntent();
        if(intent.getStringExtra("name") != null)
        {
            nameEditText.setText(intent.getStringExtra("name"));
        }

        if(intent.getStringExtra("lat") != null && intent.getStringExtra("long") != null)
        {

            locationLat = getIntent().getStringExtra("lat");
            locationLng = getIntent().getStringExtra("long");

            locationEditText.setText(locationLat + ", " + locationLng);
        }






        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                userLat = Double.toString(location.getLatitude());
                userLng = Double.toString(location.getLongitude());
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        else
        {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }


        locationEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AddLocationActivity.this, "location edit text clicked", Toast.LENGTH_SHORT).show();

                Intent placeIntent = new Intent(AddLocationActivity.this, PlaceActivity.class);
                finish();
                startActivity(placeIntent);
            }
        });


        getCurrentLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locationLat = userLat;
                locationLng = userLng;

                locationEditText.setText(locationLat + ", " + locationLng);
            }
        });

        //shows restaurant on map
        showMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddLocationActivity.this, MapsActivity.class);

                locationName = nameEditText.getText().toString();

                intent.putExtra("locationName", locationName);
                intent.putExtra("lat", locationLat);
                intent.putExtra("lng", locationLng);
                intent.putExtra("db", false);

                startActivity(intent);
            }
        });

        //saves restaurant to database
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(nameEditText.getText().toString().equals(""))
                {
                    Toast.makeText(AddLocationActivity.this, "Error! Add text to restaurant Name", Toast.LENGTH_SHORT).show();
                }
                else if(locationEditText.getText().toString().equals(""))
                {
                    Toast.makeText(AddLocationActivity.this, "Error! Add Location", Toast.LENGTH_SHORT).show();
                }
                else
                {

                    locationName = nameEditText.getText().toString();

                    Restaurant restaurant = new Restaurant(locationName, locationLat, locationLng);

                    long result = db.insetRestaurant(restaurant);
                    if(result > 0)
                    {
                        Toast.makeText(AddLocationActivity.this, "Restaurant Saved Successfully", Toast.LENGTH_SHORT).show();

                        Intent newNoteIntent = new Intent(AddLocationActivity.this, MainActivity.class);
                        finish();
                        startActivity(newNoteIntent);
                    }
                    else
                    {
                        Toast.makeText(AddLocationActivity.this, "Error! Restaurant NOT Saved", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

    }
}