package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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
        FirebaseUser user=mAuth.getCurrentUser();
        if(user==null)
        {
            startActivity(new Intent(this,Login.class));
        }
    }
}