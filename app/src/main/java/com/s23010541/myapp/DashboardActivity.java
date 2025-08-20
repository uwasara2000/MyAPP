package com.s23010541.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DashboardActivity extends AppCompatActivity {

    private ImageButton backButton;
    private TextView dashboardTitle;
    private ImageView profilePicture;
    private EditText searchBar;

    private Button expenseTrackerButton, incomeTrackerButton,
            financialEducationButton, AiHelperButton, settingsButton, LocationSensorButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Initialize views
        backButton = findViewById(R.id.backButton);
        dashboardTitle = findViewById(R.id.dashboardTitle);
        profilePicture = findViewById(R.id.profilePicture);
        searchBar = findViewById(R.id.searchBar);

        expenseTrackerButton = findViewById(R.id.expenseTrackerButton);
        incomeTrackerButton = findViewById(R.id.incomeTrackerButton);
        financialEducationButton = findViewById(R.id.financialEducationButton);
        AiHelperButton = findViewById(R.id.AiHelperButton);
        settingsButton = findViewById(R.id.settingsButton);
        LocationSensorButton = findViewById(R.id.LocationSensorButton);

        // Back button exits app
        backButton.setOnClickListener(v -> finishAffinity());

        // Expense Tracker
        expenseTrackerButton.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, ExpensesActivity.class);
            startActivity(intent);
        });

        // Income Tracker
        incomeTrackerButton.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, IncomeActivity.class);
            startActivity(intent);
        });


        // Financial Education
        financialEducationButton.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, FinancialEducationActivity.class);
            startActivity(intent);
        });

        // AI Helper → ChatActivity
        AiHelperButton.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, ChatActivity.class);
            startActivity(intent);
        });

        // Location & Sensor (Map)
        LocationSensorButton.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, MapActivity.class);
            startActivity(intent);
        });

        // Settings
        settingsButton.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, SettingsActivity.class);
            startActivity(intent);
        });
    }
}
