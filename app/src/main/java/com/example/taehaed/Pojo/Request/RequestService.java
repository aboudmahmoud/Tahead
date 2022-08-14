package com.example.taehaed.Pojo.Request;

import java.io.Serializable;

public class RequestService implements Serializable {
    private int id;
    private String service;
    private int done;
    private int form;
    private int status;

    public int getForm() {
        return form;
    }

    public void setForm(int form) {
        this.form = form;
    }

    public RequestService() {
    }

    public RequestService(int id, String service, int done, int status) {
        this.id = id;
        this.service = service;
        this.done = done;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public int getDone() {
        return done;
    }

    public void setDone(int done) {
        this.done = done;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
