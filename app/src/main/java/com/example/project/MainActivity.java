package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    Button login;
    Button signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth=FirebaseAuth.getInstance();

        login = findViewById(R.id.main_login);
        signup = findViewById(R.id.main_signup);

        login.setOnClickListener((view) -> {
            startActivity(new Intent(MainActivity.this, Login.class));
        });

        signup.setOnClickListener((view) -> {
            startActivity(new Intent(MainActivity.this, Signup.class));
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}