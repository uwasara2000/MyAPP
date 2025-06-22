package com.s23010541.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail;
    private EditText etPassword;
    private Button btnLogin;
    private TextView tvForgetPassword;
    private TextView tvSignUp;
    private DatabaseHelper databaseHelper; // Declare an instance of your DatabaseHelper

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize views
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvForgetPassword = findViewById(R.id.tvForgetPassword);
        tvSignUp = findViewById(R.id.tvSignUp);

        // Initialize DatabaseHelper
        databaseHelper = new DatabaseHelper(this); // Initialize your DatabaseHelper here

        // Set OnClickListener for Login button
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please enter both email and password.", Toast.LENGTH_SHORT).show();
                } else {
                    // Authenticate user against the database
                    // This assumes 'checkUser' verifies existing data, not inserts new data
                    boolean isValidUser = databaseHelper.checkUser(email, password);

                    if (isValidUser) {
                        Toast.makeText(LoginActivity.this, "Login successful! Data checked from dataset.", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(LoginActivity.this, DashboardActivity.class); // Assuming you have a DashboardActivity
                        startActivity(intent);
                        finish();


                    } else {
                        Toast.makeText(LoginActivity.this, "Invalid email or password.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // Set OnClickListener for Forget Password text
        tvForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "Forget Password clicked!", Toast.LENGTH_SHORT).show();
                // Navigate to Forget Password activity (if you implement one)
                // Intent intent = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                // startActivity(intent);
            }
        });

        // Set OnClickListener for Sign Up text
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "Sign Up clicked!", Toast.LENGTH_SHORT).show();
                // Navigate to Sign Up activity
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}