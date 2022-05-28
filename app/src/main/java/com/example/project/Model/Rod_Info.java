package com.example.project.Model;

public class Rod_Info implements Iron_Info{
    private String quantity;
    private String length;

    public Rod_Info()
    {

    }

    public Rod_Info(Rod_Info rod) {
        this.length = rod.length;
        this.quantity = rod.quantity;
    }

    public Rod_Info(String length, String quantity) {
        this.length = length;
        this.quantity = quantity;
    }


    @Override
    public String getLength() {
        return this.length;
    }

    @Override
    public String getQuantity() {
        return this.quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    @Override
    public String typeOfIron() {
        return "Rod_Info";
    }

    @Override
    public String toString() {
        return "Rod_Info{" +
                "length='" + length + '\'' +
                ", quantity='" + quantity + '\'' +
                '}';
    }

}