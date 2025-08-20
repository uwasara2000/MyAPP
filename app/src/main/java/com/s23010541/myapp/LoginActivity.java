package com.s23010541.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText; // Still needed for findViewById, but logic will ignore input
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail;
    private EditText etPassword;
    private Button btnLogin;
    private TextView tvForgetPassword;
    private TextView tvSignUp;
    // private DatabaseHelper databaseHelper; // No longer strictly needed if bypassing login logic

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize views (still good practice, even if not directly used for validation)
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvForgetPassword = findViewById(R.id.tvForgetPassword);
        tvSignUp = findViewById(R.id.tvSignUp);

        // databaseHelper = new DatabaseHelper(this); // You can comment this out or remove it if database operations are not needed at all here

        // Set OnClickListener for Login button
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {                // --- MODIFICATION START ---
                // Directly navigate to DashboardActivity, ignoring email and password fields.
                Toast.makeText(LoginActivity.this, "", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                startActivity(intent);
                finish(); // Finish LoginActivity so the user cannot go back to it
                // --- MODIFICATION END ---
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