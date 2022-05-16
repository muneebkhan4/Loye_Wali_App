package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Welcome extends AppCompatActivity {

    Button logout, addStock, viewStock, addOrder, viewHistory;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        mAuth=FirebaseAuth.getInstance();

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
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);                               // remove all previous activities, efficient log out
            startActivity(intent);
        });
    }
}