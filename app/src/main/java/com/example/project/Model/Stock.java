package com.example.project.Model;

import java.util.ArrayList;
import java.util.List;

//Keeps the Reference of Rods.
public class Stock {
    List<RodInfo> listOfRods;

    public Stock()
    {
        this.listOfRods=new ArrayList<>();
    }

    public void addRod(RodInfo rodInfo)
    {
        this.listOfRods.add(rodInfo);
    }

    public List<RodInfo> getListOfRods() {
        return listOfRods;
    }
}
