package com.s23010541.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.TextView; // Import TextView for list items

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    private ImageButton backButton;
    private Switch notificationsSwitch;
    private TextView logoutButton; // Using TextView for the logout button style

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Initialize views
        backButton = findViewById(R.id.backButton);
        notificationsSwitch = findViewById(R.id.notificationsSwitch);
        logoutButton = findViewById(R.id.logoutButton);

        // Set OnClickListener for the back button
        backButton.setOnClickListener(v -> {
            // Navigate to DashboardActivity
            Intent intent = new Intent(SettingsActivity.this, DashboardActivity.class);
            // These flags ensure that if DashboardActivity is already in the stack,
            // it's brought to the front and all activities on top of it (including SettingsActivity) are cleared.
            // If DashboardActivity isn't in the stack, a new instance is created.
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish(); // Finish SettingsActivity so it's removed from the back stack
        });

        // Set listener for the notifications switch
        notificationsSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Toast.makeText(SettingsActivity.this, "Notifications ON", Toast.LENGTH_SHORT).show();
                // TODO: Implement logic to enable notifications
            } else {
                Toast.makeText(SettingsActivity.this, "Notifications OFF", Toast.LENGTH_SHORT).show();
                // TODO: Implement logic to disable notifications
            }
        });

        // Set listener for the Logout button
        logoutButton.setOnClickListener(v -> {
            Toast.makeText(SettingsActivity.this, "Logging out...", Toast.LENGTH_SHORT).show();
            // TODO: Implement logout logic (e.g., clear session, navigate to LoginActivity)
            Intent intent = new Intent(SettingsActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });

        // Set listeners for other setting items
        findViewById(R.id.editProfileLayout).setOnClickListener(v -> {
            Intent intent = new Intent(SettingsActivity.this, YourProfileActivity.class);
            startActivity(intent);
            // No finish() here, allowing the user to return to SettingsActivity from YourProfileActivity
        });

        // MODIFICATION START: Navigate to SecurityActivity
        findViewById(R.id.securityLayout).setOnClickListener(v -> {
            Intent intent = new Intent(SettingsActivity.this, SecurityActivity.class);
            startActivity(intent);
            // No finish() here, allowing the user to return to SettingsActivity from SecurityActivity
        });
        // MODIFICATION END

        findViewById(R.id.languageLayout).setOnClickListener(v -> showToast("Language clicked"));
        findViewById(R.id.fontSizeLayout).setOnClickListener(v -> showToast("Font size clicked"));
        findViewById(R.id.helpSupportLayout).setOnClickListener(v -> showToast("Help and Support clicked"));

        // TODO: Implement actual navigation or dialogs for each setting item
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}