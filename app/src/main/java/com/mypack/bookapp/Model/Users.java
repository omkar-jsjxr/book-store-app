package com.mypack.bookapp.Model;

public class Users {
    private String Phone_no,Password,Full_Name;

    public Users() {
    }

    public Users(String phone_no, String password, String full_name) {
        Phone_no = phone_no;
        Password = password;
        Full_Name = full_name;
    }

    public String getPhone_no() {
        return Phone_no;
    }

    public void setPhone_no(String phone_no) {
        Phone_no = phone_no;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getFull_name() {
        return Full_Name;
    }

    public void setFull_name(String full_name) {
        Full_Name = full_name;
    }
}
