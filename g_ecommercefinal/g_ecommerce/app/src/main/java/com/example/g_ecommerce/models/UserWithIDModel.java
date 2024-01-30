package com.example.g_ecommerce.models;

public class UserWithIDModel {
    String name;
    String email;
    String password;
    String phone;
    String id;

    public UserWithIDModel() {

    }

    public UserWithIDModel(String name, String email, String password, String phone, String id) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {return phone;}

    public String getId() {return id;}
}
