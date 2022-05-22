package com.example.project.Model;

public class Seller {
    String id;
    String name;
    String email;
    Stock stock;
    History history;

    Seller(String id,String name,String email)
    {
        this.id=id;
        this.name=name;
        this.email=email;
    }

    public void addOrderToHistory(Order order)
    {
        this.history.addOrder(order);
    }

    public void addStock(Stock stock)
    {
        this.stock=stock;
    }

//    public void subtractStock(Stock stock)
//    {
//
//    }

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
