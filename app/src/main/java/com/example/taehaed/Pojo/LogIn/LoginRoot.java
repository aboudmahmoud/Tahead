package com.example.taehaed.Pojo.LogIn;

import java.io.Serializable;

public class LoginRoot implements Serializable {
    private boolean successful;
    private String message;
    private Employee employee;
    private String token;

    public LoginRoot() {
    }

    public LoginRoot(boolean successful, String message, Employee employee, String token) {
        this.successful = successful;
        this.message = message;
        this.employee = employee;
        this.token = token;
    }

    public boolean getSuccesstate() {
        return successful;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
