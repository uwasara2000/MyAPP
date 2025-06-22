package com.s23010541.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class DashboardActivity extends AppCompatActivity {

    private ImageButton backButton;
    private TextView dashboardTitle;
    private ImageView profilePicture;
    private EditText searchBar;

    private Button expenseTrackerButton, incomeTrackerButton, budgetPlannerButton,
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
        budgetPlannerButton = findViewById(R.id.budgetPlannerButton);
        financialEducationButton = findViewById(R.id.financialEducationButton);
        AiHelperButton = findViewById(R.id.AiHelperButton);
        settingsButton = findViewById(R.id.settingsButton);
        LocationSensorButton = findViewById(R.id.LocationSensorButton);

        // Click listeners
        backButton.setOnClickListener(v -> {
            finishAffinity(); // This will exit the app completely
        });

        LocationSensorButton.setOnClickListener(v -> {
            boolean inserted = true; // Replace with actual insert logic if needed

            if (inserted) {
                Intent intent = new Intent(DashboardActivity.this, MapActivity.class);
                startActivity(intent);
                // Removed finish() here if you want to be able to come back to Dashboard
                // You might want to remove finish() from here and other button clicks if you want a back stack
            } else {
                Toast.makeText(DashboardActivity.this, "Insert Failed", Toast.LENGTH_SHORT).show();
            }

        });
        settingsButton.setOnClickListener(v -> {
            boolean inserted = true; // Replace with actual insert logic if needed

            if (inserted) {
                // You previously set this to MainActivity, but typically Settings button would go to SettingsActivity
                // Assuming you meant SettingsActivity based on previous conversations.
                Intent intent = new Intent(DashboardActivity.this, SettingsActivity.class); // Changed from MainActivity to SettingsActivity
                startActivity(intent);
                // Removed finish() here for consistent navigation flow,
                // allowing users to return to Dashboard from Settings.
            } else {
                Toast.makeText(DashboardActivity.this, "Insert Failed", Toast.LENGTH_SHORT).show();
            }

        });
    }
}