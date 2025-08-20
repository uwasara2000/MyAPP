package com.s23010541.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ChangePasswordActivity extends AppCompatActivity {

    private ImageButton backButton;
    private EditText oldPasswordInput, newPasswordInput, confirmPasswordInput;
    private Button changePasswordButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        // Initialize UI components
        backButton = findViewById(R.id.back_button);
        oldPasswordInput = findViewById(R.id.old_password_input);
        newPasswordInput = findViewById(R.id.new_password_input);
        confirmPasswordInput = findViewById(R.id.confirm_password_input);
        changePasswordButton = findViewById(R.id.change_password_button);

        // Back button → go back to SecurityActivity
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(ChangePasswordActivity.this, SecurityActivity.class);
            startActivity(intent);
            finish();
        });

        // Change Password Button
        changePasswordButton.setOnClickListener(v -> {
            String oldPass = oldPasswordInput.getText().toString().trim();
            String newPass = newPasswordInput.getText().toString().trim();
            String confirmPass = confirmPasswordInput.getText().toString().trim();

            if (oldPass.isEmpty() || newPass.isEmpty() || confirmPass.isEmpty()) {
                Toast.makeText(ChangePasswordActivity.this,
                        "Please fill all fields", Toast.LENGTH_SHORT).show();
            } else if (!newPass.equals(confirmPass)) {
                Toast.makeText(ChangePasswordActivity.this,
                        "New password and confirm password do not match", Toast.LENGTH_SHORT).show();
            } else {
                // TODO: Replace this with real DB / API update
                Toast.makeText(ChangePasswordActivity.this,
                        "Password changed successfully!", Toast.LENGTH_LONG).show();

                // Go back to Security Page
                Intent intent = new Intent(ChangePasswordActivity.this, SecurityActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }
        });
    }
}
