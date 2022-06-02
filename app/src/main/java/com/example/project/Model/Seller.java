package com.example.project.Model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Seller {
    String id;
    String name;
    String email;
    Stock stock;
    History history;

    public Seller(Stock stock)
    {
        this.stock=stock;
    }

    public Seller(String id, String name, String email, Stock stock) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.stock = stock;
    }

    public void addOrderToHistory(Order order) {
        this.history.addOrder(order);
    }

    public void addRodToStock(Iron_Info rod) {
        stock.addRod(rod);
    }

    // Creating TempList Of Stock to hold "newRods". if success update else noUpdate on Stock
    @RequiresApi(api = Build.VERSION_CODES.N)
    public boolean computeOrder(Order order) {
        HashMap<String, Iron_Info> stockRods = stock.getListOfRods();
        HashMap<String, Iron_Info> orderRods = order.getListOfRods();
        if(stockRods==null)
            return false;

        HashMap<String, Iron_Info> newStockRods = new HashMap<>(stockRods);


        Iterator<Map.Entry<String,Iron_Info>> stockIterator = newStockRods.entrySet().iterator();

        while (stockIterator.hasNext() && orderRods.size() > 0) {


            Iron_Info stockRod=stockIterator.next().getValue();


            Iterator<Map.Entry<String,Iron_Info>> orderIterator = orderRods.entrySet().iterator();

            while ( orderIterator.hasNext() && newStockRods.size()>0) {

                Iron_Info orderRod= orderIterator.next().getValue();

                HashMap<String, Iron_Info> newRods = Iron_Info.cutStockRod(stockRod, orderRod);

                if (newRods != null) {
                    orderRods.remove(orderRod.getLength());
                    orderIterator=orderRods.entrySet().iterator();

                    newStockRods.remove(stockRod.getLength());

                    newRods.forEach((key,value)->newStockRods.merge(key, value, (v1,v2)->new Rod_Info(key,Help.add(v1.getQuantity(),v2.getQuantity()) )));

                    stockIterator=newStockRods.entrySet().iterator();
                    if(stockIterator.hasNext())
                    {
                        stockRod=stockIterator.next().getValue();
                    }
                }
            }
        }

        if (orderRods.size() == 0) // successful
        {
            stock.setListOfRods( newStockRods);
            return true;
        }
        return false;

    }

    public boolean needStockNotification()
    {
        if(stock!=null && stock.getListOfRods()!=null)
        {
            HashMap<String,Iron_Info> listOfRods=stock.getListOfRods();
            Iterator<Map.Entry<String,Iron_Info>> iterator = listOfRods.entrySet().iterator();
            while(iterator.hasNext())
            {
                Iron_Info iron=iterator.next().getValue();
                if(Help.Integer( iron.getQuantity())<2) //Stock is about to finish
                {
                    return true;
                }
            }
            return false;

        }
        return false;
    }

    public Stock getStock() {
        return stock;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getId() {
        return id;
    }
}
