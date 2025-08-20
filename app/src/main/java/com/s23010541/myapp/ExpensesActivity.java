package com.s23010541.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;

public class ExpensesActivity extends AppCompatActivity {

    GridView iconGrid;
    ImageButton backButton;

    String[] labels = {
            "Shopping", "Food", "Bills", "Education", "Transportation", "Health",
            "Internet", "Repairs", "Pet", "Kids", "Gifts", "Home",
            "Travel", "Beauty", "Savings", "Gym", "Others"
    };

    int[] icons = {
            R.drawable.cart, R.drawable.food, R.drawable.bills,
            R.drawable.education, R.drawable.transport, R.drawable.health,
            R.drawable.internet, R.drawable.repairs, R.drawable.pet,
            R.drawable.kids, R.drawable.gift, R.drawable.home,
            R.drawable.travel, R.drawable.beauty, R.drawable.savings,
            R.drawable.gym, R.drawable.plus
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses);

        // Find GridView and ImageButton from layout
        iconGrid = findViewById(R.id.iconGrid);
        backButton = findViewById(R.id.backButton);

        // Attach adapter
        IconAdapter adapter = new IconAdapter(this, labels, icons);
        iconGrid.setAdapter(adapter);

        // Set click listener for the GridView items
        iconGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, android.view.View view, int position, long id) {
                // Create an intent to start the CalculatorActivity and add the origin extra
                Intent intent = new Intent(ExpensesActivity.this, CalculatorActivity.class);
                intent.putExtra(CalculatorActivity.ORIGIN_ACTIVITY_KEY, "Expenses");
                startActivity(intent);
            }
        });

        // Set click listener for the back button
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(ExpensesActivity.this, DashboardActivity.class);
            startActivity(intent);
            finish();
        });
    }
}