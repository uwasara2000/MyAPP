package com.s23010541.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SecurityActivity extends AppCompatActivity {

    private ImageButton backButton;
    private Switch locationSwitch; // Assuming you have a location switch in your security settings

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security); // CORRECTED: Use activity_security.xml

        // Initialize views
        backButton = findViewById(R.id.backButton); // Assuming a new ID for back button in security layout
        locationSwitch = findViewById(R.id.locationSwitch); // Assuming a Switch with this ID in activity_security.xml

        // Set OnClickListener for the back button
        backButton.setOnClickListener(v -> {
            // Navigate back to SettingsActivity
            Intent intent = new Intent(SecurityActivity.this, SettingsActivity.class);
            startActivity(intent);
            finish(); // Finish SecurityActivity to remove it from the stack
            // Alternatively, just finish(): finish();
        });

        // Set listener for the location switch
        locationSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Toast.makeText(SecurityActivity.this, "Location Tracking ON", Toast.LENGTH_SHORT).show();
                // TODO: Implement logic to enable location tracking
            } else {
                Toast.makeText(SecurityActivity.this, "Location Tracking OFF", Toast.LENGTH_SHORT).show();
                // TODO: Implement logic to disable location tracking
            }
        });

        // Add listeners for any other specific security settings you add to activity_security.xml
        // Example: Change Password button
        // findViewById(R.id.changePasswordButton).setOnClickListener(v -> {
        //     Toast.makeText(SecurityActivity.this, "Change Password clicked", Toast.LENGTH_SHORT).show();
        //     // TODO: Implement navigation to a change password screen
        // });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}