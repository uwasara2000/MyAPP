package com.s23010541.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ManageAccountActivity extends AppCompatActivity {

    private ImageButton backButton;
    private EditText emailInput;
    private EditText passwordInput;
    private Button deleteAccountButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_account);

        backButton = findViewById(R.id.back_button);
        emailInput = findViewById(R.id.email_input);
        passwordInput = findViewById(R.id.password_input);
        deleteAccountButton = findViewById(R.id.delete_account_button);

        // Back button → go back
        backButton.setOnClickListener(v -> finish());

        // Delete account button
        deleteAccountButton.setOnClickListener(v -> {
            String email = emailInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(ManageAccountActivity.this,
                        "Please enter your email and password",
                        Toast.LENGTH_SHORT).show();
            } else {
                performAccountDeletion(email, password);
            }
        });
    }

    private void performAccountDeletion(String email, String password) {
        // ✅ Show success message
        Toast.makeText(this,
                "Your account is deleted successfully",
                Toast.LENGTH_LONG).show();

        // ✅ Go to LoginActivity instead of SecurityActivity
        Intent intent = new Intent(ManageAccountActivity.this, LoginActivity.class);
        // Clear all previous activities from back stack
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

        // ✅ Close ManageAccountActivity
        finish();
    }
}
