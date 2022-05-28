package com.example.project.Model;

import java.util.ArrayList;
import java.util.List;

public class History {
    private List<Order> listOfOrders=new ArrayList<>();

    public History()
    {

    }


    public void addOrder(Order order)
    {
        this.listOfOrders.add(order);
    }

    public List<Order> getListOfOrders() {
        return listOfOrders;
    }

}
