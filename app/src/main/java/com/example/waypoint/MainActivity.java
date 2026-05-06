package com.example.waypoint;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private MaterialToolbar toolbar;
    private MaterialButton btnMySpots, btnAddSpot;
    private static final String PREFS_NAME = "waypoint_prefs";

    public ArrayList<Spot> spots = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnMySpots = findViewById(R.id.btnMySpots);
        btnAddSpot = findViewById(R.id.btnAddSpot);

        updateSubtitle();

        if (savedInstanceState == null) {
            loadFragment(new SpotsFragment());
            setActiveButton(btnMySpots);
        }

        btnMySpots.setOnClickListener(v -> {
            loadFragment(new SpotsFragment());
            setActiveButton(btnMySpots);
        });

        btnAddSpot.setOnClickListener(v -> {
            loadFragment(new AddSpotFragment());
            setActiveButton(btnAddSpot);
        });
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }

    private void setActiveButton(MaterialButton activeBtn) {
        MaterialButton inactiveBtn = (activeBtn == btnMySpots) ? btnAddSpot : btnMySpots;

        // Active style: amber fill and navy text
        activeBtn.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.amber)));
        activeBtn.setTextColor(ContextCompat.getColor(this, R.color.navy));
        activeBtn.setStrokeWidth(0);

        // Inactive style: outlined, amber stroke and amber text
        inactiveBtn.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, android.R.color.transparent)));
        inactiveBtn.setTextColor(ContextCompat.getColor(this, R.color.amber));
        inactiveBtn.setStrokeColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.amber)));
        inactiveBtn.setStrokeWidth(2); // standard stroke width
    }

    private void updateSubtitle() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        int level = prefs.getInt("explorerLevel", 0);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setSubtitle("Explorer Level " + level + " 🗺️");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateSubtitle();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {
            SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
            editor.clear();
            editor.apply();

            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public ArrayList<Spot> getSpots() {
        return spots;
    }
}