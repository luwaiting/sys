package com.example.systemdesignproject.Model;

public class Goods {
    public String name;
    public String expiration;
    public String price;
    public String amount;
    public Goods(String name,String expiration,String price){
        this.expiration=expiration;
        this.name=name;
        this.price=price;
    }
    public void addAmount(String i){
        amount=i;
    }
    public String getAmount(){
        return amount;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
