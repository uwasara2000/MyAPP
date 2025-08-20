package com.s23010541.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class CalculatorActivity extends AppCompatActivity {

    private TextView displayTextView;
    private StringBuilder currentNumber = new StringBuilder();
    private String operator = "";
    private double firstOperand = 0;
    private boolean isNewCalculation = true;

    // Define a key for the Intent extra
    public static final String ORIGIN_ACTIVITY_KEY = "ORIGIN_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        displayTextView = findViewById(R.id.displayTextView);
        ImageButton backButton = findViewById(R.id.backButton);

        // Get the origin activity from the Intent
        String originActivity = getIntent().getStringExtra(ORIGIN_ACTIVITY_KEY);

        // Set the back button listener
        backButton.setOnClickListener(v -> {
            Intent intent;
            // Check the origin and create the appropriate Intent
            if ("Expenses".equals(originActivity)) {
                intent = new Intent(CalculatorActivity.this, ExpensesActivity.class);
            } else if ("Income".equals(originActivity)) {
                intent = new Intent(CalculatorActivity.this, IncomeActivity.class);
            } else {
                // Fallback to a default activity if the origin is unknown
                intent = new Intent(CalculatorActivity.this, DashboardActivity.class);
            }
            startActivity(intent);
            finish();
        });

        // Set click listeners for number buttons
        findViewById(R.id.button0).setOnClickListener(v -> onNumberClick("0"));
        findViewById(R.id.button1).setOnClickListener(v -> onNumberClick("1"));
        findViewById(R.id.button2).setOnClickListener(v -> onNumberClick("2"));
        findViewById(R.id.button3).setOnClickListener(v -> onNumberClick("3"));
        findViewById(R.id.button4).setOnClickListener(v -> onNumberClick("4"));
        findViewById(R.id.button5).setOnClickListener(v -> onNumberClick("5"));
        findViewById(R.id.button6).setOnClickListener(v -> onNumberClick("6"));
        findViewById(R.id.button7).setOnClickListener(v -> onNumberClick("7"));
        findViewById(R.id.button8).setOnClickListener(v -> onNumberClick("8"));
        findViewById(R.id.button9).setOnClickListener(v -> onNumberClick("9"));
        findViewById(R.id.buttonDot).setOnClickListener(v -> onNumberClick("."));

        // Set click listeners for operator buttons
        findViewById(R.id.buttonAdd).setOnClickListener(v -> onOperatorClick("+"));
        findViewById(R.id.buttonSubtract).setOnClickListener(v -> onOperatorClick("-"));
        findViewById(R.id.buttonMultiply).setOnClickListener(v -> onOperatorClick("*"));
        findViewById(R.id.buttonDivide).setOnClickListener(v -> onOperatorClick("/"));

        // Set click listeners for clear and equals buttons
        findViewById(R.id.buttonClear).setOnClickListener(v -> onClearClick());
        findViewById(R.id.buttonEquals).setOnClickListener(v -> onEqualsClick());

        // Updated code for the "Go" button
        findViewById(R.id.buttonGo).setOnClickListener(v -> {
            Intent intent = new Intent(CalculatorActivity.this, AddActivity.class);
            startActivity(intent);
            // Optionally, call finish() if you don't want the user to go back to the calculator
            // finish();
        });
    }

    private void onNumberClick(String number) {
        if (isNewCalculation) {
            currentNumber.setLength(0);
            isNewCalculation = false;
        }

        if (number.equals(".") && currentNumber.toString().contains(".")) {
            return;
        }

        currentNumber.append(number);
        displayTextView.setText(currentNumber.toString());
    }

    private void onOperatorClick(String op) {
        if (currentNumber.length() > 0) {
            firstOperand = Double.parseDouble(currentNumber.toString());
            operator = op;
            isNewCalculation = true;
        }
    }

    private void onEqualsClick() {
        if (operator.isEmpty() || currentNumber.length() == 0) {
            return;
        }

        double secondOperand = Double.parseDouble(currentNumber.toString());
        double result = 0;

        switch (operator) {
            case "+":
                result = firstOperand + secondOperand;
                break;
            case "-":
                result = firstOperand - secondOperand;
                break;
            case "*":
                result = firstOperand * secondOperand;
                break;
            case "/":
                if (secondOperand != 0) {
                    result = firstOperand / secondOperand;
                } else {
                    displayTextView.setText("Error");
                    return;
                }
                break;
        }

        displayTextView.setText(String.valueOf(result));
        currentNumber.setLength(0);
        currentNumber.append(result);
        isNewCalculation = true;
    }

    private void onClearClick() {
        currentNumber.setLength(0);
        firstOperand = 0;
        operator = "";
        isNewCalculation = true;
        displayTextView.setText("0");
    }
}