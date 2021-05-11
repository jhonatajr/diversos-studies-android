package com.example.mobile_schorgan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        SharedPreferences sharedPref = getSharedPreferences("Login", MODE_PRIVATE);
        final String login = sharedPref.getString("login", "");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (login.isEmpty()) {
                    startActivity(new Intent(getBaseContext(), LoginActivity.class));
                }
                else {
                    startActivity(new Intent(getBaseContext(), MenuNavActivity.class));
                }
                finish();
            }
        }, 3000);
    }

}