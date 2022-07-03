package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.example.project.Model.Iron_Info;
import com.example.project.Model.Rod_Info;
import com.example.project.RecyclerViews.IronAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewStock extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_stock);

        // set up the RecyclerView
        List<Iron_Info> listOfIron=new ArrayList<>();
        RecyclerView recyclerView = findViewById(R.id.IronRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        IronAdapter adapter = new IronAdapter(this, listOfIron);
        recyclerView.setAdapter(adapter);

        //Getting userId
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser= mAuth.getCurrentUser();
        assert firebaseUser != null;
        String userId=firebaseUser.getUid();

        //Writing Objects on Firebase

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference reference = mDatabase.child(userId).child("Stock").child("listOfRods");

        reference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    Iron_Info iron=snapshot.getValue(Rod_Info.class);
                    listOfIron.add(iron);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}

