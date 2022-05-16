package com.example.project.Model;

public class Seller {
    String id;
    String name;
    String email;
    Stock stock;

    Seller(String id,String name,String email)
    {
        this.id=id;
        this.name=name;
        this.email=email;
    }

    public void addStock(Stock stock)
    {
        this.stock=stock;
    }

}
