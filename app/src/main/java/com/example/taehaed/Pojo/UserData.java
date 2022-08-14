package com.example.taehaed.Pojo;

import com.google.gson.annotations.SerializedName;

public class UserData {
    @SerializedName("username")
    private String Email;
    @SerializedName("password")
    private String password;

    public UserData() {
    }

    public UserData(String email, String password) {
        Email = email;
        this.password = password;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getpassword() {
        return password;
    }

    public void setpassword(String password) {
        this.password = password;
    }
}
