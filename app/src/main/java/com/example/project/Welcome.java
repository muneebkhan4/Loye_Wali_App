package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;


public class Welcome extends AppCompatActivity {

    Button logout, addStock, viewStock, addOrder, viewHistory;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();

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
            mAuth.signOut();
            Intent intent = new Intent(Welcome.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);              // remove all previous activities, efficient log out

            // load ad when the activity get created

        });
    }


}