package com.example.mapsapplication;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.mapsapplication.data.DatabaseHelper;
import com.example.mapsapplication.model.Restaurant;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String[] pins;

    private List<Restaurant> restaurantList;
    private List<LatLng> pinLatLng;
    private List<String> nameList;

    int zoomAmount;

    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        pinLatLng = new ArrayList<LatLng>();
        nameList = new ArrayList<String>();




        Intent intent = getIntent();
        Boolean useDataBase = intent.getExtras().getBoolean("db");

        if(useDataBase == true)
        {
            db = new DatabaseHelper(MapsActivity.this);

            restaurantList = db.fetchAllRestaurants();

            for(Restaurant restaurant : restaurantList)
            {
                LatLng location = new LatLng(Double.parseDouble(restaurant.getRestaurantLat()), Double.parseDouble(restaurant.getRestaurantLong()));
                pinLatLng.add(location);
                nameList.add(restaurant.getRestaurantName());
            }
            zoomAmount = 11;
        }
        else
        {
            String locationName = intent.getStringExtra("locationName");
            Double locationLat = Double.parseDouble(intent.getExtras().getString("lat"));
            Double locationLng = Double.parseDouble(intent.getExtras().getString("lng"));

            LatLng location = new LatLng(locationLat, locationLng);

            pinLatLng.add(location);
            nameList.add(locationName);

            zoomAmount = 17;
        }



    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng deakin = new LatLng(-38.2116375,144.3139285);
//        mMap.addMarker(new MarkerOptions().position(deakin).title("Deakin University"));

        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));

        LatLng currentLocation = null;
        if(pinLatLng.size() <= 0)
        {

        }
        else
        {
            for (int i = 0 ; i < pinLatLng.size(); i++){

                currentLocation = pinLatLng.get(i);

                mMap.addMarker(new MarkerOptions().position(currentLocation).title(nameList.get(i)));

                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation,zoomAmount));
            }
        }



    }
}