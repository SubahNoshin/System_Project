package com.example.g_ecommerce.models;

public class UserModel {
    public void setName(String name) {
        this.name = name;
    }

    String name;

    public void setEmail(String email) {
        this.email = email;
    }

    String email;
    String password;
    String profileImg;

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    String phoneno;

    String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public UserModel(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getPhoneno() {
        return phoneno;
    }



    public UserModel() {
    }

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }

    public UserModel(String name, String email, String password, String phone) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneno = phone;
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
}