package com.example.waypoint;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "waypoint_prefs";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    private static final String KEY_USERNAME = "username";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        if (prefs.getBoolean(KEY_IS_LOGGED_IN, false)) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextInputEditText etUsername = findViewById(R.id.et_username);
        TextInputEditText etPassword = findViewById(R.id.et_password);
        MaterialButton btnLogin = findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(v -> {
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
            } else {
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean(KEY_IS_LOGGED_IN, true);
                editor.putString(KEY_USERNAME, username);
                editor.apply();

                startActivity(new Intent(this, MainActivity.class));
                finish();
            }
        });
    }
}