package com.example.storeqrscanner.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.example.storeqrscanner.Home2;
import com.example.storeqrscanner.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (user==null)
                {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();

                }
                else {
                    SharedPreferences preferences = getSharedPreferences("userInfo", MODE_PRIVATE);
                    boolean currentLoginState = preferences.getBoolean("saveLogin",false);
                   if (currentLoginState){
                       startActivity(new Intent(SplashActivity.this, Home2.class));
                       finish();
                   }
                   else {
                       startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                       finish();
                   }
                }
            }
        },2000);
    }
}