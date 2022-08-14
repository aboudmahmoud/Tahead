package com.example.taehaed.Pojo.LogIn;

import java.io.Serializable;

public class Type implements Serializable {
    private String string;

    public Type() {
    }

    public Type(String string) {
        this.string = string;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }
}
