package com.s23010541.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;

public class IncomeActivity extends AppCompatActivity {

    GridView iconGrid;
    ImageButton backButton;

    String[] labels = {
            "Salary", "Bonus", "Part Time", "Investment", "Others"
    };

    int[] icons = {
            R.drawable.salary, R.drawable.bonus, R.drawable.parttime,
            R.drawable.investment, R.drawable.plus
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income);

        iconGrid = findViewById(R.id.iconGrid);
        backButton = findViewById(R.id.backButton);

        IconAdapter2 adapter = new IconAdapter2(this, labels, icons);
        iconGrid.setAdapter(adapter);

        iconGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Create an intent to start the CalculatorActivity and add the origin extra
                Intent intent = new Intent(IncomeActivity.this, CalculatorActivity.class);
                intent.putExtra(CalculatorActivity.ORIGIN_ACTIVITY_KEY, "Income");
                startActivity(intent);
            }
        });

        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(IncomeActivity.this, DashboardActivity.class);
            startActivity(intent);
            finish();
        });
    }
}