package com.example.taehaed.Pojo.Request;

import com.example.taehaed.Pojo.Index.Request;

import java.util.ArrayList;

public class RequestOp extends Request {

    private ArrayList<RequestService> request_services;

    public ArrayList<RequestService> getRequest_services() {
        return request_services;
    }

    public void setRequest_services(ArrayList<RequestService> request_services) {
        this.request_services = request_services;
    }
}
