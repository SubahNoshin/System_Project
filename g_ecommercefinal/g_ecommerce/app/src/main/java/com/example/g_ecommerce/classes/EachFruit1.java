package com.example.g_ecommerce.classes;

import java.io.Serializable;

public class EachFruit1 implements Serializable {
    private String img_url, name, type, description, rating,discount, fruit_id;



    public EachFruit1() {

    }

    public EachFruit1(EachFruit1 eachFruit1) {
        this.fruit_id = eachFruit1.fruit_id;
        this.img_url = eachFruit1.img_url;
        this.name = eachFruit1.name;
        this.type = eachFruit1.type;
        this.description = eachFruit1.description;
        this.rating = eachFruit1.rating;
        this.discount = eachFruit1.discount;
    }

    public EachFruit1(String img_url, String name, String type, String description, String rating, String discount, String fruit_id) {
        this.img_url = img_url;
        this.name = name;
        this.type = type;
        this.description = description;
        this.rating = rating;
        this.fruit_id = fruit_id;
        this.discount = discount;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getFruit_id() {
        return fruit_id;
    }

    public void setFruit_id(String fruit_id) {
        this.fruit_id = fruit_id;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }
}