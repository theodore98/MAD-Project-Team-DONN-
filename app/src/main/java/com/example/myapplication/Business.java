package com.example.myapplication;

public class Business {
    private String accountNumber;
    private String address;
    private String bank;
    private String email;
    private String mobileNo;
    private String name;
    private String owner;
    private String password;


    Business(){

    }
    public Business(String accountNumber, String address, String bank, String email, String mobileNo, String name, String owner, String password) {
        this.accountNumber = accountNumber;
        this.address = address;
        this.bank = bank;
        this.email = email;
        this.mobileNo = mobileNo;
        this.name = name;
        this.owner = owner;
        this.password = password;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getPasswordS() {
        return password;
    }

    public void setPasswordS(String passwordS) {
        this.password = password;
    }
}