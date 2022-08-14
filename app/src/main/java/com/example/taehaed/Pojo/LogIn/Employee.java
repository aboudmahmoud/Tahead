package com.example.taehaed.Pojo.LogIn;

import java.io.Serializable;

public class Employee implements Serializable {
    private int id;
    private String username;
    private String email;
    private String birth_date;
    private String image;
    private Type type;
    private Gender gender;

    public Employee() {
    }

    public Employee(int id, String username, String email, String birth_date, String image, Type type, Gender gender) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.birth_date = birth_date;
        this.image = image;
        this.type = type;
        this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(String birth_date) {
        this.birth_date = birth_date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }
}
