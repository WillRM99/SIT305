package com.example.mapsapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.util.Arrays;

public class PlaceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);

        // Initialize the SDK
        Places.initialize(getApplicationContext(), getString(R.string.api_key));

        // Create a new PlacesClient instance
        PlacesClient placesClient = Places.createClient(this);


        // Initialize the AutocompleteSupportFragment.
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG));

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                // TODO: Get info about the selected place.
                Toast.makeText(PlaceActivity.this, place.getName() + ", " + place.getId(), Toast.LENGTH_SHORT).show();

                Intent placeIntent = new Intent(PlaceActivity.this, AddLocationActivity.class);

                double latitude = place.getLatLng().latitude;
                double longitude = place.getLatLng().longitude;

                placeIntent.putExtra("name", place.getName());

                placeIntent.putExtra("lat", Double.toString(latitude));
                placeIntent.putExtra("long", Double.toString(longitude));
//                placeIntent.putExtra("latlng", place.getLatLng());


                finish();
                startActivity(placeIntent);
            }


            @Override
            public void onError(@NonNull Status status) {
                // TODO: Handle the error.
                Toast.makeText(PlaceActivity.this, "An Error Has Occurred", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}