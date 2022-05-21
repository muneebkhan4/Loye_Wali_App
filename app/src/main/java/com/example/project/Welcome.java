package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class Welcome extends AppCompatActivity {

    Button logout, addStock, viewStock, addOrder, viewHistory;
    FirebaseAuth mAuth;

    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth=FirebaseAuth.getInstance();

        setContentView(R.layout.activity_welcome);

        logout = findViewById(R.id.welcome_logout);

        addStock = findViewById(R.id.welcome_addStockBtn);
        viewStock = findViewById(R.id.welcome_viewStockBtn);
        addOrder = findViewById(R.id.welcome_addOrderBtn);
        viewHistory = findViewById(R.id.welcome_viewHistoryBtn);

        addStock.setOnClickListener((view) -> {
            startActivity(new Intent(Welcome.this, AddStock.class));
        });

        viewStock.setOnClickListener((view) -> {
            startActivity(new Intent(Welcome.this, ViewStock.class));
        });

        addOrder.setOnClickListener((view) -> {
            startActivity(new Intent(Welcome.this, AddOrder.class));
        });

        viewHistory.setOnClickListener((view) -> {
            startActivity(new Intent(Welcome.this, ViewHistory.class));
        });

        logout.setOnClickListener((view) -> {
            FirebaseUser user=mAuth.getCurrentUser();
            mAuth.signOut();
            Intent intent = new Intent(Welcome.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);              // remove all previous activities, efficient log out

            // load ad when the activity get created

            loadInterstitialAd();       // load interstitial ad

            if (mInterstitialAd != null) {
                mInterstitialAd.show(Welcome.this);       // show ad

                mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback(){
                    @Override
                    public void onAdDismissedFullScreenContent() {
                        // Called when fullscreen content is dismissed.
                        Log.d("TAG", "The ad was dismissed.");
                        FirebaseUser user=mAuth.getCurrentUser();
                        mAuth.signOut();
                        Intent intent = new Intent(Welcome.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);                               // remove all previous activities, efficient log out
                        startActivity(intent);
                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                        // Called when fullscreen content failed to show.
                        Log.d("TAG", "The ad failed to show.");
                    }

                    @Override
                    public void onAdShowedFullScreenContent() {
                        // Called when fullscreen content is shown.
                        // Make sure to set your reference to null so you don't
                        // show it a second time.
                        mInterstitialAd = null;
                        Log.d("TAG", "The ad was shown.");
                    }
                });



            } else {
                Log.d("TAG", "The interstitial ad wasn't ready yet.");
            }

            //startActivity(intent);
        });


    }

    // load interstitial ad
    void loadInterstitialAd() {
        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(this, getString(R.string.interstitial_ad_unit_id), adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                        Log.i("TAG", "onAdLoaded");
                    }
                });
    }



}