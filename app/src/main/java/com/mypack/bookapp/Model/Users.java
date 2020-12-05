package com.mypack.bookapp.Model;

public class Users {
    private String Phone,Password,Name,image,address;

    public Users() {
    }

    public Users(String phone, String password, String name, String image, String address) {
        Phone = phone;
        Password = password;
        Name = name;
        this.image = image;
        this.address = address;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}