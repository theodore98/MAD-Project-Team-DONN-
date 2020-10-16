package com.example.rmsapp;

public class Orders {

    private String oderid;
    private String Food_id;
    private String fdname;
    private double price;

    public Orders() {

    }

    public String getOderid() {
        return oderid;
    }

    public void setOderid(String oderid) {
        this.oderid = oderid;
    }

    public String getFood_id() {
        return Food_id;
    }

    public void setFood_id(String food_id) {
        Food_id = food_id;
    }

    public String getFdname() {
        return fdname;
    }

    public void setFdname(String fdname) {
        this.fdname = fdname;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }


}
