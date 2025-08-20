package com.s23010541.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Switch;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class SecurityActivity extends AppCompatActivity {

    private ImageButton backButton;
    private Switch locationSwitch;
    private LinearLayout deleteAccountSection, changePasswordLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security);

        // Initialize views
        backButton = findViewById(R.id.backButton);
        locationSwitch = findViewById(R.id.locationSwitch);
        deleteAccountSection = findViewById(R.id.deleteLayout);
        changePasswordLayout = findViewById(R.id.changepasswordLayout);

        // Back button → go back to Settings
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(SecurityActivity.this, SettingsActivity.class);
            startActivity(intent);
            finish();
        });

        // Delete account → open ManageAccountActivity
        deleteAccountSection.setOnClickListener(v -> showDeleteConfirmationDialog());

        // Location switch listener
        locationSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Toast.makeText(SecurityActivity.this, "Location Tracking ON", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(SecurityActivity.this, "Location Tracking OFF", Toast.LENGTH_SHORT).show();
            }
        });

        // Change password → open ChangePasswordActivity
        changePasswordLayout.setOnClickListener(v -> {
            Intent intent = new Intent(SecurityActivity.this, ChangePasswordActivity.class);
            startActivity(intent);
        });
    }

    // Confirmation dialog for delete account
    private void showDeleteConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Delete Account")
                .setMessage("Are you sure you want to delete your account?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    Intent intent = new Intent(SecurityActivity.this, ManageAccountActivity.class);
                    startActivity(intent);
                })
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                .show();
    }
}
