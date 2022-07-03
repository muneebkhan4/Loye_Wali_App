package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.firebase.auth.FirebaseAuth;

import kotlin.jvm.JvmField;


public class MainActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    Button login;
    Button signup;

    Button start;
    Button stop;

    AdView mAdView;
    static boolean flag = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth=FirebaseAuth.getInstance();

        login = findViewById(R.id.main_login);
        signup = findViewById(R.id.main_signup);
        start = findViewById(R.id.start);
        stop = findViewById(R.id.stop);

        login.setOnClickListener((view) -> startActivity(new Intent(MainActivity.this, Login.class)));

        signup.setOnClickListener((view) -> startActivity(new Intent(MainActivity.this, Signup.class)));

        start.setOnClickListener( (view)->{
            if (flag)
            {
                flag =false;
                startService(new Intent(this, MyService.class));
                showToast("Background Service Started...");
            }
            else{
                showToast("Background Service Already Started...");
            }
        });

        // music stop click listener
        stop.setOnClickListener( (view)->{
            flag =true;
            stopService(new Intent(this, MyService.class));
            showToast("Background Service Stopped...");
        });


        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {
                // not essential for now
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}