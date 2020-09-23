package com.example.project;

public class Food {

    private String food_id;
    private  double  fd_price;
    private String  name;
    private  String description;

    Food(){

        food_id = "";
        fd_price = 0.0;
        name ="";
        description="";
    }

    public String getFood_id() {
        return food_id;
    }

    public void setFood_id(String food_id) {
        this.food_id = food_id;
    }

    public double getFd_price() {
        return fd_price;
    }

    public void setFd_price(double fd_price) {
        this.fd_price = fd_price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
