package com.example.storeqrscanner;

import androidx.lifecycle.ViewModel;

public class Item extends ViewModel {
    public String itemId, name, barcode;
    public String quantity;
    public String price, discount;
    public String startDiscount, endDiscount;


    public Item (String name, String barcode, String quantity, String price, String discount, String startDiscount, String endDiscount)
    {
        this.name = name;
        this.barcode = barcode;
        this.quantity = quantity;
        this.price = price;
        this.discount = discount;
        this.startDiscount = startDiscount;
        this.endDiscount = endDiscount;
    }

    public Item(){

    }
/*    public String getItemId() {
        return name;
    }
    public void setItemId(String name) {
        this.name = name;
    }*/

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String barcode() {
        return barcode;
    }
    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }
    public String getQuantity() {
        return quantity;
    }
    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
    public String getPrice() {
        return price;
    }
    public void setPrice(String price) {
        this.price = price;
    }
    public String getDiscount() { return discount; }
    public void setDiscount(String discount) {
        this.discount = discount;
    }
    public String getStartDiscount() {
        return startDiscount;
    }
    public void setStartDiscount(String startDiscount) {
        this.startDiscount = startDiscount;
    }
    public String getEndDiscount() {
        return endDiscount;
    }
    public void setEndDiscount(String endDiscount) {
        this.endDiscount = endDiscount;
    }
}

