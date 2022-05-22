package com.example.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.project.Model.History;
import com.example.project.Model.Order;
import com.example.project.Model.Rod_Info;
import com.example.project.RecyclerViews.IronAdapter;
import com.example.project.RecyclerViews.OrderAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewHistory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_history);

        Order order1=new Order("09 11 2019","Usman K",new Rod_Info("100","3"));
        order1.addRod(new Rod_Info("90","2"));

        Order order2=new Order("5 10 2010","Asim K",new Rod_Info("66","11"));
        order2.addRod(new Rod_Info("10","2"));

        List<Order> animalNames=new ArrayList<>();
        animalNames.add(order1);
        animalNames.add(order2);


        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.orderRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        OrderAdapter adapter = new OrderAdapter(this, animalNames);
        recyclerView.setAdapter(adapter);


    }
}