package com.example.project;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.Model.Iron_Info;
import com.example.project.Model.Order;
import com.example.project.Model.Rod_Info;
import com.example.project.RecyclerViews.OrderAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class ViewHistory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_history);

        List<Order> listOfOrders = new ArrayList<>();
        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.orderRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        OrderAdapter adapter = new OrderAdapter(this, listOfOrders);
        recyclerView.setAdapter(adapter);

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        assert firebaseUser != null;
        String userId = firebaseUser.getUid();
        DatabaseReference reference = mDatabase.child(userId).child("History").child("listOfOrders");

        reference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Order order = new Order();
                    order.setListOfRods(new HashMap<>());
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        String key = snapshot1.getKey();
                        switch (Objects.requireNonNull(key)) {
                            case "clientName":
                                String clientName = snapshot1.getValue(String.class);
                                order.setClientName(clientName);
                                break;
                            case "dateTime":
                                String dateTime = snapshot1.getValue(String.class);
                                order.setDateTime(dateTime);
                                break;
                            case "listOfRods":
                                HashMap<String, Iron_Info> listOfRods = new HashMap<>();
                                for (DataSnapshot snapshot2 : snapshot1.getChildren()) {
                                    Iron_Info iron = snapshot2.getValue(Rod_Info.class);
                                    assert iron != null;
                                    listOfRods.put(iron.getLength(), iron);
                                }
                                order.setListOfRods(listOfRods);
                                break;
                            case "price":
                                String price = snapshot1.getValue(String.class);
                                order.setPrice(price);
                                break;
                        }
                        //Toast.makeText(ViewHistory.this, key, Toast.LENGTH_LONG).show();
                    }
                    listOfOrders.add(order);
                }
                adapter.notifyDataSetChanged();
                // Toast.makeText(MainActivity.this, listOfOrders.size(), Toast.LENGTH_LONG).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}

