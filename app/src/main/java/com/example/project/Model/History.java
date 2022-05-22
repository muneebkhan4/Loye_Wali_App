package com.example.project.Model;

import java.util.ArrayList;
import java.util.List;

public class History {
    private List<Order> listOfOrders;

    public History()
    {
        this.listOfOrders=new ArrayList<Order>();
    }

    public void addOrder(Order order)
    {
        this.listOfOrders.add(order);
    }

    public List<Order> getListOfOrders() {
        return listOfOrders;
    }

}
