package com.s23010541.myapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class FinancialEducationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_financial_education);

        // Back button
        ImageButton backBtn = findViewById(R.id.backButton);
        backBtn.setOnClickListener(v -> finish());



        // Set up links
        setLink(R.id.link1, "https://www.investopedia.com/terms/f/financial_plan.asp");
        setLink(R.id.link2, "https://smartasset.com/financial-advisor/financial-planning-explained");
        setLink(R.id.link3, "https://www.nerdwallet.com/article/investing/what-is-a-financial-plan");
        setLink(R.id.link4, "https://www.investor.gov/free-financial-planning-tools");

        setLink(R.id.video1, "https://youtu.be/MabD5R8kRak?si=kWGV2Age4_vRqUt6");
        setLink(R.id.video2, "https://youtu.be/MabD5R8kRak?si=kWGV2Age4_vRqUt6");
        setLink(R.id.video3, "https://youtu.be/ouvbeb2wSGA?si=1LoqrRP8wMI6ZnXn");
    }

    private void setLink(int textViewId, String url) {
        TextView textView = findViewById(textViewId);
        textView.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(browserIntent);
        });
    }
}
