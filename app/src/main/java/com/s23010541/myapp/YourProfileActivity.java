package com.s23010541.myapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class YourProfileActivity extends AppCompatActivity {

    private EditText etFirstName, etSecondName, etEmail, etPhoneNumber, etPassword;
    private Button btnSave;
    private ImageView backButton;

    // SharedPreferences key for storing profile data
    private static final String PREF_NAME = "UserProfile";
    private static final String KEY_FIRST_NAME = "firstName";
    private static final String KEY_SECOND_NAME = "secondName";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PHONE_NUMBER = "phoneNumber";
    private static final String KEY_PASSWORD = "password"; // Be careful storing passwords in SharedPreferences

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_profile);

        // Initialize UI elements
        etFirstName = findViewById(R.id.etFirstName);
        etSecondName = findViewById(R.id.etSecondName);
        etEmail = findViewById(R.id.etEmail);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        etPassword = findViewById(R.id.etPassword);
        btnSave = findViewById(R.id.btnSave);
        backButton = findViewById(R.id.backButton);

        // Load existing profile data
        loadProfileData();

        // Set up Save button click listener
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProfileData();
            }
        });

        // Set up Back button click listener
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start SettingsActivity
                Intent intent = new Intent(YourProfileActivity.this, SettingsActivity.class);
                // Optional: Add flags to clear the activity stack if you don't want to come back here
                // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish(); // Finish YourProfileActivity so it's removed from the back stack
            }
        });
    }

    private void loadProfileData() {
        SharedPreferences preferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        etFirstName.setText(preferences.getString(KEY_FIRST_NAME, ""));
        etSecondName.setText(preferences.getString(KEY_SECOND_NAME, ""));
        etEmail.setText(preferences.getString(KEY_EMAIL, ""));
        etPhoneNumber.setText(preferences.getString(KEY_PHONE_NUMBER, ""));
        // For password, it's generally not recommended to load it directly into an EditText
        // For security, you should only allow setting a new password, not displaying the old one.
        // etPassword.setText(preferences.getString(KEY_PASSWORD, "")); // Do NOT do this for actual passwords
    }

    private void saveProfileData() {
        String firstName = etFirstName.getText().toString().trim();
        String secondName = etSecondName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String phoneNumber = etPhoneNumber.getText().toString().trim();
        String password = etPassword.getText().toString().trim(); // This will be the new password

        // Basic validation
        if (TextUtils.isEmpty(firstName) || TextUtils.isEmpty(email) || TextUtils.isEmpty(phoneNumber)) {
            Toast.makeText(this, "Please fill in all required fields (First Name, Email, Phone Number).", Toast.LENGTH_SHORT).show();
            return;
        }

        // Email format validation (simple)
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Please enter a valid email address.", Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences preferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString(KEY_FIRST_NAME, firstName);
        editor.putString(KEY_SECOND_NAME, secondName);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_PHONE_NUMBER, phoneNumber);

        // Only save password if it's not empty, assuming user wants to change it
        if (!TextUtils.isEmpty(password)) {
            // In a real application, you would hash this password before storing it,
            // and never store it in plain text in SharedPreferences.
            // For a robust solution, consider a secure way to handle passwords (e.g., API calls to a backend).
            editor.putString(KEY_PASSWORD, password);
        }

        editor.apply(); // Apply changes asynchronously

        Toast.makeText(this, "Profile Saved!", Toast.LENGTH_SHORT).show();

        // Navigate back to SettingsActivity after saving
        Intent intent = new Intent(YourProfileActivity.this, SettingsActivity.class);
        startActivity(intent);
        finish(); // Finish YourProfileActivity so it's removed from the back stack
    }
}