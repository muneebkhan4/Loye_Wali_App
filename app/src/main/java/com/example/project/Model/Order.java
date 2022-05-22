package com.example.project.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Order {
    private HashMap<String,Iron_Info> listOfRods;
    private String dateTime;
    private String price;
    private String clientName;

    public Order(String dateTime,String clientName,Iron_Info iron)
    {
        this.listOfRods=new HashMap<>();
        if(iron!=null)
        {
            listOfRods.put(iron.getLength(),iron);
        }
        this.dateTime=dateTime;
        this.price="0";
        this.clientName=clientName;
    }

    public void addRod(Iron_Info newRodInfo)
    {
        Iron_Info rod=listOfRods.get(newRodInfo.getLength());
        if(rod==null) //doesnot exist
        {
            listOfRods.put(newRodInfo.getLength(),newRodInfo);
        }
        else
        {
            String added=Help.add(rod.getQuantity(),newRodInfo.getQuantity() );
            newRodInfo.setQuantity(added);
            listOfRods.put(newRodInfo.getLength(),newRodInfo);
        }
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public HashMap<String,Iron_Info> getListOfRods() {
        return listOfRods;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getPrice() {
        return price;
    }

    public String getClientName() {
        return clientName;
    }
}
