package com.example.project;

public class upload {

    private String name;
    private String Imageuri;

    public upload(String name, String imageuri) {
        this.name = name;
        Imageuri = imageuri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageuri() {
        return Imageuri;
    }

    public void setImageuri(String imageuri) {
        Imageuri = imageuri;
    }


}
