package com.example.project.Model;

public class RodInfo {
    private String length;
    private int quantity;

    public RodInfo(String length,int quantity)
    {
        this.length=length;
        this.quantity=quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getLength() {
        return length;
    }
}
