package com.mypack.bookapp.Model;

public class books1 {
    private  String description,price,Bookname,category,Bid,date,time,image,bookstate;

    public books1() {
    }

    public books1(String description, String price, String bookname, String category, String bid, String date, String time, String image, String bookstate) {
        this.description = description;
        this.price = price;
        Bookname = bookname;
        this.category = category;
        Bid = bid;
        this.date = date;
        this.time = time;
        this.image = image;
        this.bookstate = bookstate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getBookname() {
        return Bookname;
    }

    public void setBookname(String bookname) {
        Bookname = bookname;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBid() {
        return Bid;
    }

    public void setBid(String bid) {
        Bid = bid;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBookstate() {
        return bookstate;
    }

    public void setBookstate(String bookstate) {
        this.bookstate = bookstate;
    }
}
