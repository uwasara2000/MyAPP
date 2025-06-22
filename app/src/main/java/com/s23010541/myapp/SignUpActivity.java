package com.s23010541.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {

    private EditText etUsername, etEmail, etPassword;
    private Button btnCreateAccount;
    private ImageView ivFacebook, ivGoogle, ivInstagram;
    private TextView tvSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up); // Make sure this XML layout exists

        // Initialize views
        etUsername = findViewById(R.id.etUsername);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnCreateAccount = findViewById(R.id.btnCreateAccount);
        ivFacebook = findViewById(R.id.ivFacebook);
        ivGoogle = findViewById(R.id.ivGoogle);
        ivInstagram = findViewById(R.id.ivInstagram);
        tvSignUp = findViewById(R.id.tvSignUp);

        // Create Account button logic
        btnCreateAccount.setOnClickListener(v -> {
            String username = etUsername.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(SignUpActivity.this, "Please fill all fields.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(SignUpActivity.this, "Account created for: " + username, Toast.LENGTH_SHORT).show();
                // After successful signup, navigate to login or dashboard
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Social media buttons logic
        ivFacebook.setOnClickListener(v ->
                Toast.makeText(SignUpActivity.this, "Sign up with Facebook clicked!", Toast.LENGTH_SHORT).show());

        ivGoogle.setOnClickListener(v ->
                Toast.makeText(SignUpActivity.this, "Sign up with Google clicked!", Toast.LENGTH_SHORT).show());

        ivInstagram.setOnClickListener(v ->
                Toast.makeText(SignUpActivity.this, "Sign up with Instagram clicked!", Toast.LENGTH_SHORT).show());

        // TextView navigation (e.g., already have an account? Sign In)
        tvSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
