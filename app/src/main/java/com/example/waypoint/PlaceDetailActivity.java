package com.example.waypoint;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class PlaceDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_detail);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Unpack extras
        String placeName = getIntent().getStringExtra("placeName");
        String city = getIntent().getStringExtra("city");
        String note = getIntent().getStringExtra("note");
        String placeType = getIntent().getStringExtra("placeType");
        String mood = getIntent().getStringExtra("mood");
        String dateAdded = getIntent().getStringExtra("dateAdded");

        // Bind Views
        View moodHeader = findViewById(R.id.moodHeader);
        ImageView ivPlaceType = findViewById(R.id.ivPlaceType);
        TextView tvPlaceName = findViewById(R.id.tvPlaceName);
        TextView tvMoodChip = findViewById(R.id.tvMoodChip);
        TextView tvCity = findViewById(R.id.tvCity);
        TextView tvDate = findViewById(R.id.tvDate);
        TextView tvNote = findViewById(R.id.tvNote);
        SwitchMaterial switchVisited = findViewById(R.id.switchVisited);

        // Set data
        tvPlaceName.setText(placeName);
        tvCity.setText(city);
        tvNote.setText(note);
        tvDate.setText("Discovered on " + dateAdded);
        tvMoodChip.setText(mood);

        // Mood colors
        int moodColor;
        switch (mood != null ? mood : "") {
            case "Exciting":
                moodColor = ContextCompat.getColor(this, R.color.mood_exciting);
                break;
            case "Nostalgic":
                moodColor = ContextCompat.getColor(this, R.color.mood_nostalgic);
                break;
            case "Dreamy":
                moodColor = ContextCompat.getColor(this, R.color.mood_dreamy);
                break;
            case "Chaotic":
                moodColor = ContextCompat.getColor(this, R.color.mood_chaotic);
                break;
            case "Peaceful":
            default:
                moodColor = ContextCompat.getColor(this, R.color.mood_peaceful);
                break;
        }

        moodHeader.setBackgroundColor(moodColor);
        tvMoodChip.setTextColor(moodColor);

        // Place Type Icons
        int iconRes;
        switch (placeType != null ? placeType : "") {
            case "City":
                iconRes = R.drawable.ic_city;
                break;
            case "Nature":
                iconRes = R.drawable.ic_nature;
                break;
            case "Cafe":
                iconRes = R.drawable.ic_cafe;
                break;
            case "Culture":
                iconRes = R.drawable.ic_culture;
                break;
            case "Forest":
                iconRes = R.drawable.ic_forest;
                break;
            case "Beach":
            default:
                iconRes = R.drawable.ic_beach;
                break;
        }
        ivPlaceType.setImageResource(iconRes);

        // Visited Toggle
        SharedPreferences prefs = getSharedPreferences("waypoint_prefs", MODE_PRIVATE);
        String visitedKey = "visited_" + placeName;
        switchVisited.setChecked(prefs.getBoolean(visitedKey, false));
        switchVisited.setOnCheckedChangeListener((buttonView, isChecked) -> {
            prefs.edit().putBoolean(visitedKey, isChecked).apply();
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}