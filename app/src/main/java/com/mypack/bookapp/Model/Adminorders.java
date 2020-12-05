package com.mypack.bookapp.Model;

public class Adminorders {
    private String TotalAmount,Name,Phone,address,city,date,time,state;

    public Adminorders() {
    }

    public Adminorders(String totalAmount, String name, String phone, String address, String city, String date, String time, String state) {
        TotalAmount = totalAmount;
        Name = name;
        Phone = phone;
        this.address = address;
        this.city = city;
        this.date = date;
        this.time = time;
        this.state = state;
    }

    public String getTotalAmount() {
        return TotalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        TotalAmount = totalAmount;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
