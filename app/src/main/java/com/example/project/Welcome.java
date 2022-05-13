package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Welcome extends AppCompatActivity {

    Button logout;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        mAuth=FirebaseAuth.getInstance();

        logout = findViewById(R.id.welcome_logout);

        logout.setOnClickListener((view) -> {
            FirebaseUser user=mAuth.getCurrentUser();
            mAuth.signOut();
            startActivity(new Intent(Welcome.this, MainActivity.class));
        });
    }
}