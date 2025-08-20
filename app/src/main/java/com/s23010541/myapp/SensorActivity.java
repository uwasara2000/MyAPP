package com.s23010541.myapp;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View; // Make sure this is imported if you use View.OnClickListener
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class SensorActivity extends AppCompatActivity implements SensorEventListener {

    private TextView sensorStatusTextView;
    // Removed: private TextView alertMessageTextView; // This is no longer in the layout
    private ImageButton backButton;

    private SensorManager sensorManager;
    private Sensor temperatureSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);

        sensorStatusTextView = findViewById(R.id.sensorStatusTextView);
        // Removed: alertMessageTextView = findViewById(R.id.alertMessageTextView);
        backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(SensorActivity.this, DashboardActivity.class);
            // These flags are good for navigating back to a "main" activity and clearing the stack
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish(); // Finish current activity so pressing back won't return to it
        });

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        if (sensorManager != null) {
            temperatureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
            if (temperatureSensor == null) {
                // Fallback for older devices if TYPE_AMBIENT_TEMPERATURE isn't available
                temperatureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_TEMPERATURE);
            }
        }

        if (temperatureSensor == null) {
            sensorStatusTextView.setText("Temperature sensor not available on this device.");
            Toast.makeText(this, "Temperature sensor not found.", Toast.LENGTH_LONG).show();
            // Removed: alertMessageTextView.setVisibility(View.GONE);
        } else {
            sensorStatusTextView.setText("Temperature: -- °C");
            // Removed: alertMessageTextView.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (temperatureSensor != null) {
            // Register the listener to receive sensor updates
            sensorManager.registerListener(this, temperatureSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (sensorManager != null) {
            // Unregister the listener to save battery when the activity is not in foreground
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // Check if the event is from the temperature sensor
        if (event.sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE ||
                event.sensor.getType() == Sensor.TYPE_TEMPERATURE) {

            float temperature = event.values[0];
            // Update the TextView with the current temperature
            sensorStatusTextView.setText(String.format(Locale.getDefault(), "Temperature: %.1f°C", temperature));

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // This method is called when the accuracy of a sensor changes.
        // For simple data display, often no specific action is needed.
    }
}