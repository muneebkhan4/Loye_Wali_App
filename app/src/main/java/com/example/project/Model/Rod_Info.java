package com.example.project.Model;

public class Rod_Info implements Iron_Info {
    private String length;
    private String quantity;

    public Rod_Info()
    {

    }
    public Rod_Info(String length, String quantity)
    {
        this.length=length;
        this.quantity=quantity;
    }


    public void setLength(String length) {
        this.length = length;
    }
    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    @Override
    public String getLength() {
        return length;
    }

    @Override
    public String getQuantity() {
        return quantity;
    }

    @Override
    public String getName() {
        return "Rod_Info";
    }
}
