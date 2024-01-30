package com.example.g_ecommerce.models;

import java.io.Serializable;
import java.util.List;

public class BuyerModel implements Serializable {
    String name;
    String phone;
    List<MyCartModel> purchases;

    public BuyerModel(String name, String phone, List<MyCartModel> list) {
        this.name = name;
        this.purchases = list;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {return phone;}

    public List<MyCartModel> getPurchases() {
        return purchases;
    }
}
