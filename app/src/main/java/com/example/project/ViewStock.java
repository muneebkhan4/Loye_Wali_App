package com.example.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.project.Model.Iron_Info;
import com.example.project.Model.Rod_Info;
import com.example.project.RecyclerViews.IronAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewStock extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_stock);

        // data to populate the RecyclerView with
        List<Iron_Info> animalNames = new ArrayList<>();
        animalNames.add(new Rod_Info("100","5"));
        animalNames.add(new Rod_Info("50","3"));
        animalNames.add(new Rod_Info("80","9"));
        animalNames.add(new Rod_Info("60","10"));
        animalNames.add(new Rod_Info("200","7"));

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.IronRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        IronAdapter adapter = new IronAdapter(this, animalNames);
        recyclerView.setAdapter(adapter);

    }

}

