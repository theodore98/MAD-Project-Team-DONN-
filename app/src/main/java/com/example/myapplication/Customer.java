package com.example.myapplication;

public class Customer {
    private String email;
    private String username;
    private String password;
    private String contact;

    public Customer() {
    }

    public Customer(String email, String username, String password, String contact) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}