package com.example.taehaed.Pojo.LogIn;

import java.io.Serializable;

public class Gender implements Serializable {
    private int myboolean;
    private String string;

    public Gender(int myboolean, String string) {
        this.myboolean = myboolean;
        this.string = string;
    }

    public Gender() {
    }

    public int getMyboolean() {
        return myboolean;
    }

    public void setMyboolean(int myboolean) {
        this.myboolean = myboolean;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }
}
