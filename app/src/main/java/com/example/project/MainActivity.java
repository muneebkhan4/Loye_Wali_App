package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.project.Model.Rod_Info;
import com.example.project.Model.Stock;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
//
    private DatabaseReference myRef;
  //
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




        //-----------------------------------------------Testing

        myRef = FirebaseDatabase.getInstance().getReference().child("History");


        Rod_Info x1=new Rod_Info("144","6");
        Rod_Info x2=new Rod_Info("66","2");
        Rod_Info x3=new Rod_Info("35","7");

        Stock stock1=new Stock();
        stock1.addRod(x1);
        stock1.addRod(x2);

        Stock stock2=new Stock();
        stock2.addRod(x3);
        stock2.addRod(x2);


        myRef.push().setValue(stock1);
        myRef.push().setValue(stock2);


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int i=0;
                for(DataSnapshot ds:snapshot.getChildren())
                {
                    Stock stock;
//                    RodInfo rod=new RodInfo();
                    stock=ds.getValue(Stock.class);
                    i++;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

///////////////////

    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}