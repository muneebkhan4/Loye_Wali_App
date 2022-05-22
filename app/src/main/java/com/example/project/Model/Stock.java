package com.example.project.Model;

import java.util.ArrayList;
import java.util.List;

//Keeps the Reference of Rods.
public class Stock {
    List<Rod_Info> listOfRods;

    public Stock()
    {
        this.listOfRods=new ArrayList<>();
    }

    public void addRod(Rod_Info rodInfo)
    {
        this.listOfRods.add(rodInfo);
    }

    public void setListOfRods(List<Rod_Info> listOfRods) {
        this.listOfRods = listOfRods;
    }

    public List<Rod_Info> getListOfRods() {
        return listOfRods;
    }
}
