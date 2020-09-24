package com.example.myapplication;

public class Admin {


    public String email;
    public String fname;
    public String lname;
    public String password;
    public String phoneno;

    public Admin() {

    }

    public Admin(String email, String fname, String lname, String password) {
        this.email = email;
        this.fname = fname;
        this.lname = lname;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }
}
