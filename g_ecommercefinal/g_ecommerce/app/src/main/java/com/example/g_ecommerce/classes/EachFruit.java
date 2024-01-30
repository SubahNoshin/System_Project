package com.example.g_ecommerce.classes;

import java.io.Serializable;

public class EachFruit implements Serializable {
    private String img_url,name, type, description,rating,fruit_id;
    int price;

    

    public EachFruit() {

    }
    public EachFruit(EachFruit eachFruit){
        this.fruit_id = eachFruit.fruit_id;
        this.img_url = eachFruit.img_url;
        this.name = eachFruit.name;
        this.type = eachFruit.type;
        this.description = eachFruit.description;
        this.rating = eachFruit.rating;
        this.price = eachFruit.price;
    }

    public EachFruit(String img_url, String name, String type, String description, int price, String rating, String fruit_id) {
        this.img_url = img_url;
        this.name = name;
        this.type = type;
        this.description = description;
        this.price = price;
        this.rating = rating;
        this.fruit_id = fruit_id;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
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
}