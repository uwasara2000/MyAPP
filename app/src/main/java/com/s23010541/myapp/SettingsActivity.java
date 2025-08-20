package com.s23010541.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    private ImageButton backButton;
    private Switch notificationsSwitch;
    private TextView logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Initialize views
        backButton = findViewById(R.id.backButton);
        notificationsSwitch = findViewById(R.id.notificationsSwitch);
        logoutButton = findViewById(R.id.logoutButton);

        // Back button: go to DashboardActivity
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(SettingsActivity.this, DashboardActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });

        // Notifications switch
        notificationsSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                showToast("Notifications ON");
                // TODO: Enable notifications
            } else {
                showToast("Notifications OFF");
                // TODO: Disable notifications
            }
        });

        // Logout button
        logoutButton.setOnClickListener(v -> {
            showToast("Logging out...");
            Intent intent = new Intent(SettingsActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });

        // Edit Profile
        findViewById(R.id.editProfileLayout).setOnClickListener(v -> {
            startActivity(new Intent(SettingsActivity.this, YourProfileActivity.class));
        });

        // Security
        findViewById(R.id.securityLayout).setOnClickListener(v -> {
            startActivity(new Intent(SettingsActivity.this, SecurityActivity.class));
        });

        // Language settings → LanguageFontActivity
        findViewById(R.id.languageLayout).setOnClickListener(v -> {
            Intent intent = new Intent(SettingsActivity.this, LanguageFontActivity.class);
            startActivity(intent);
        });

        // Font size settings → LanguageFontActivity
        findViewById(R.id.fontSizeLayout).setOnClickListener(v -> {
            Intent intent = new Intent(SettingsActivity.this, LanguageFontActivity.class);
            startActivity(intent);
        });

        // Help and Support
        findViewById(R.id.helpSupportLayout).setOnClickListener(v -> showToast("Help and Support clicked"));
    }

    // Utility method to show a Toast
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
