package com.example.food2u;

public class Business {
    private String accountNo;
    private String address;
    private String bank;
    private String email;
    private String mobileNo;
    private String name;
    private String owner;
    private String password;

    public Business() {
    }

    public Business(String accountNo, String address, String bank, String email, String mobileNo, String name, String owner, String password) {
        this.accountNo = accountNo;
        this.address = address;
        this.bank = bank;
        this.email = email;
        this.mobileNo = mobileNo;
        this.name = name;
        this.owner = owner;
        this.password = password;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}