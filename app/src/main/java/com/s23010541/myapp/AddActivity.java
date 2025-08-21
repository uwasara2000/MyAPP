package com.s23010541.myapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class AddActivity extends AppCompatActivity {

    private ImageButton backButton;
    private EditText expensesEditText, incomeEditText, balanceEditText;
    private Button clearButton;

    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        backButton = findViewById(R.id.backButton);
        expensesEditText = findViewById(R.id.expenses_edittext);
        incomeEditText = findViewById(R.id.income_edittext);
        balanceEditText = findViewById(R.id.balance_edittext);
        clearButton = findViewById(R.id.clearButton);

        // Make balance read-only (user cannot edit)
        balanceEditText.setKeyListener(null);

        prefs = getSharedPreferences("AddActivityPrefs", MODE_PRIVATE);

        // Restore saved values
        expensesEditText.setText(prefs.getString("expenses", ""));
        incomeEditText.setText(prefs.getString("income", ""));
        balanceEditText.setText(prefs.getString("balance", ""));

        // Get data from CalculatorActivity
        String amount = getIntent().getStringExtra("CALCULATED_AMOUNT");
        String origin = getIntent().getStringExtra("ORIGIN_ACTIVITY");

        if (amount != null) {
            if ("Expenses".equals(origin)) {
                expensesEditText.setText(amount);
            } else if ("Income".equals(origin)) {
                incomeEditText.setText(amount);
            }
        }

        // Auto-calculate balance whenever expenses/income changes
        TextWatcher balanceWatcher = new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                calculateBalance();
                saveValues();
            }
            @Override public void afterTextChanged(Editable s) {}
        };

        expensesEditText.addTextChangedListener(balanceWatcher);
        incomeEditText.addTextChangedListener(balanceWatcher);

        // Back button logic
        backButton.setOnClickListener(v -> {
            String expenses = expensesEditText.getText().toString().trim();
            String income = incomeEditText.getText().toString().trim();

            saveValues(); // Save before leaving

            if (expenses.isEmpty()) {
                Intent intent = new Intent(AddActivity.this, ExpensesActivity.class);
                startActivity(intent);
                finish();
            } else if (income.isEmpty()) {
                Intent intent = new Intent(AddActivity.this, IncomeActivity.class);
                startActivity(intent);
                finish();
            } else {
                finish();
            }
        });

        // Clear button → also clear SharedPreferences
        clearButton.setOnClickListener(v -> {
            expensesEditText.setText("");
            incomeEditText.setText("");
            balanceEditText.setText("");
            prefs.edit().clear().apply();
        });

        // Ensure balance is calculated on start
        calculateBalance();
    }

    // Function to calculate balance
    private void calculateBalance() {
        String incomeStr = incomeEditText.getText().toString();
        String expensesStr = expensesEditText.getText().toString();

        double income = incomeStr.isEmpty() ? 0 : Double.parseDouble(incomeStr);
        double expenses = expensesStr.isEmpty() ? 0 : Double.parseDouble(expensesStr);

        double balance = income - expenses;
        balanceEditText.setText(String.valueOf(balance));
    }

    // Save values to SharedPreferences
    private void saveValues() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("expenses", expensesEditText.getText().toString());
        editor.putString("income", incomeEditText.getText().toString());
        editor.putString("balance", balanceEditText.getText().toString());
        editor.apply();
    }
}
