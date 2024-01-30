package com.example.g_ecommerce.classes;

public class DataHolder {
    private static String name;
    private static String location;
    private static String distance;

    public static String getName() {
        return name;
    }

    public static String getLocation() {
        return location;
    }

    public static String getDistance() {
        return distance;
    }

    public static void setData(String newName, String newLocation, String newDistance) {
        name = newName;
        location = newLocation;
        distance = newDistance;
    }
}