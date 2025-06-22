package com.s23010541.myapp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private EditText addressInput;
    private Button btnGoToSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        // View bindings
        addressInput = findViewById(R.id.addressInput);
        Button showLocationButton = findViewById(R.id.showLocationButton);
        btnGoToSensor = findViewById(R.id.btnGoToSensor);

        // 🔗 Go to SensorActivity
        btnGoToSensor.setOnClickListener(v -> {
            Intent intent = new Intent(MapActivity.this, SensorActivity.class);
            startActivity(intent);
        });

        // 🗺️ Initialize the map
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        } else {
            // This toast should ideally not be reached if the layout is correct
            Toast.makeText(this, "Error initializing map fragment", Toast.LENGTH_LONG).show();
            // Optionally, consider finishing the activity or handling the error gracefully
        }

        // 📍 Show location based on user input
        showLocationButton.setOnClickListener(view -> {
            String address = addressInput.getText().toString().trim();

            if (mMap == null) {
                Toast.makeText(this, "Map not ready yet. Please wait.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!TextUtils.isEmpty(address)) {
                locateAddress(address);
            } else {
                Toast.makeText(this, "Please enter an address to search.", Toast.LENGTH_SHORT).show();
            }
        });

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        }
    }

    private void locateAddress(String address) {
        new Thread(() -> {
            Geocoder geocoder = new Geocoder(MapActivity.this, Locale.getDefault());
            try {
                List<Address> addresses = geocoder.getFromLocationName(address, 1);
                if (addresses != null && !addresses.isEmpty()) {
                    Address location = addresses.get(0);
                    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

                    runOnUiThread(() -> {
                        mMap.clear(); // Clear existing markers
                        mMap.addMarker(new MarkerOptions().position(latLng).title(address));
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15)); // Use animateCamera for smoother transition
                        Toast.makeText(MapActivity.this,
                                "Location: " + String.format(Locale.getDefault(), "%.4f, %.4f", location.getLatitude(), location.getLongitude()),
                                Toast.LENGTH_LONG).show();
                    });
                } else {
                    runOnUiThread(() ->
                            Toast.makeText(MapActivity.this, "Location not found for: " + address, Toast.LENGTH_LONG).show()
                    );
                }
            } catch (IOException e) {
                runOnUiThread(() ->
                        Toast.makeText(MapActivity.this, "Geocoder service error: " + e.getMessage(), Toast.LENGTH_LONG).show()
                );
                e.printStackTrace(); // Log the exception for debugging
            }
        }).start();
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // Enable map UI tools
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true); // Enable "My Location" button

        // Enable location layer if permission is granted
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            // Permission not granted, explain to user or request again if necessary
            Toast.makeText(this, "Location permission not granted. My Location layer disabled.", Toast.LENGTH_SHORT).show();
        }

        // Default location: Colombo, Sri Lanka
        LatLng defaultLocation = new LatLng(6.9271, 79.8612);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 12));
    }

    // Optional: Handle permission request results (good practice)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) { // Our request code for location
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, re-enable my location if map is ready
                if (mMap != null) {
                    if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        mMap.setMyLocationEnabled(true);
                    }
                }
                Toast.makeText(this, "Location permission granted!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Location permission denied.", Toast.LENGTH_LONG).show();
            }
        }
    }
}