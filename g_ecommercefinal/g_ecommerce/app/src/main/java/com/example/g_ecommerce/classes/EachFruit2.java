package com.example.g_ecommerce.classes;

import java.io.Serializable;

public class EachFruit2 implements Serializable {
    String name;
    String description;
    String rating;
    String img_url;
    String fruit_id;


    public EachFruit2() {
    }

    public EachFruit2(EachFruit2 eachFruit2) {
        this.fruit_id = eachFruit2.fruit_id;
        this.img_url = eachFruit2.img_url;
        this.name = eachFruit2.name;
        this.description = eachFruit2.description;
        this.rating = eachFruit2.rating;


    }

    public EachFruit2(String name, String description, String rating, String img_url, String fruit_id) {
        this.name = name;
        this.description = description;
        this.rating = rating;
        this.img_url = img_url;
        this.fruit_id = fruit_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getFruit_id() {
        return fruit_id;
    }

    public void setFruit_id(String fruit_id) {
        this.fruit_id = fruit_id;
    }
}


