package com.s23010541.myapp;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LanguageFontActivity extends AppCompatActivity {

    private ImageButton backButton;
    private RadioGroup fontRadioGroup;
    private RadioButton radioSmall, radioNormal, radioLarge;

    private LinearLayout languageLayout;
    private TextView selectLanguageText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_font);

        // Top bar back button
        backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> finish());

        // Font size RadioGroup and RadioButtons
        fontRadioGroup = findViewById(R.id.fontRadioGroup);
        radioSmall = findViewById(R.id.radioSmall);
        radioNormal = findViewById(R.id.radioNormal);
        radioLarge = findViewById(R.id.radioLarge);

        fontRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            String font = "Normal";
            if (checkedId == R.id.radioSmall) font = "Small";
            else if (checkedId == R.id.radioNormal) font = "Normal";
            else if (checkedId == R.id.radioLarge) font = "Large";

            Toast.makeText(LanguageFontActivity.this,
                    "Font size set to " + font, Toast.LENGTH_SHORT).show();
        });

        // Language selection layout
        languageLayout = findViewById(R.id.languageLayout);
        selectLanguageText = findViewById(R.id.selectLanguageText);

        languageLayout.setOnClickListener(v -> showLanguageDialog());
    }

    private void showLanguageDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_language_list, null);
        builder.setView(dialogView);

        EditText searchEditText = dialogView.findViewById(R.id.searchEditText);
        RecyclerView recyclerView = dialogView.findViewById(R.id.languageRecyclerView);

        // Large language list
        List<String> languageList = new ArrayList<>();
        languageList.addAll(Arrays.asList(
                "English", "Sinhala", "Tamil", "Spanish", "French", "German",
                "Italian", "Portuguese", "Chinese", "Japanese", "Korean", "Arabic",
                "Russian", "Hindi", "Urdu", "Bengali", "Turkish", "Vietnamese",
                "Thai", "Malay", "Swahili", "Greek", "Dutch", "Hebrew", "Persian"
        ));

        LanguageAdapter adapter = new LanguageAdapter(languageList, language -> {
            selectLanguageText.setText(language);
            Toast.makeText(LanguageFontActivity.this, "Language set to " + language, Toast.LENGTH_SHORT).show();
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        AlertDialog dialog = builder.create();
        dialog.show();

        // Filter languages while typing
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                List<String> filtered = new ArrayList<>();
                for (String lang : languageList) {
                    if (lang.toLowerCase().contains(s.toString().toLowerCase())) {
                        filtered.add(lang);
                    }
                }
                recyclerView.setAdapter(new LanguageAdapter(filtered, language -> {
                    selectLanguageText.setText(language);
                    Toast.makeText(LanguageFontActivity.this, "Language set to " + language, Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }));
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });
    }
}
