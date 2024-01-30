package com.example.g_ecommerce.classes;

import java.io.Serializable;

public class Derivery implements Serializable {
    private String name, distance, location,id;


    public Derivery() {

    }

    public Derivery(Derivery derivery) {
        this.name = derivery.name;
        this.distance = derivery.distance;
        this.location = derivery.location;
        this.id = derivery.id;

    }

    public Derivery(String distance, String location, String name,String id) {
        this.name = name;
        this.distance = distance;
        this.location = location;
        this.id = id;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
