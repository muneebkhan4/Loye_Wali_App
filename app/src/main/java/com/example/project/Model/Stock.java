package com.example.project.Model;

import java.util.HashMap;

public class Stock {
    HashMap<String,Iron_Info> listOfRods;

    public Stock()
    {

    }


    public void addRod(Iron_Info newRodInfo)
    {
        if(listOfRods==null)
        {
            listOfRods=new HashMap<>();
        }
        Iron_Info rod=listOfRods.get(newRodInfo.getLength());
        if (rod != null) {
            String added = Help.add(rod.getQuantity(), newRodInfo.getQuantity());
            newRodInfo.setQuantity(added);
        }
        listOfRods.put(newRodInfo.getLength(),newRodInfo);

    }

    public void setListOfRods(HashMap<String,Iron_Info> listOfRods) {
        this.listOfRods = listOfRods;
    }

    public HashMap<String,Iron_Info> getListOfRods() {
        return listOfRods;
    }
}

