package com.mypack.bookapp.Model;

public class Cart {
    private String Bid,Bookname,disscount,price,quantity;

    public Cart() {
    }

    public Cart(String bid, String bookname, String disscount, String price, String quantity) {
        Bid = bid;
        Bookname = bookname;
        this.disscount = disscount;
        this.price = price;
        this.quantity = quantity;
    }

    public String getBid() {
        return Bid;
    }

    public void setBid(String bid) {
        Bid = bid;
    }

    public String getBookname() {
        return Bookname;
    }

    public void setBookname(String bookname) {
        Bookname = bookname;
    }

    public String getDisscount() {
        return disscount;
    }

    public void setDisscount(String disscount) {
        this.disscount = disscount;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
