package com.example.taehaed.Pojo.Index;

import java.io.Serializable;
import java.util.ArrayList;

public class IndexRoot implements Serializable{
    private boolean successful;
    private String message;
    private ArrayList<Request> requests;

    public IndexRoot() {
    }

    public IndexRoot(boolean successful, String message, ArrayList<Request> requests) {
        this.successful = successful;
        this.message = message;
        this.requests = requests;
    }

    public boolean isSuccessful() {
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

    public ArrayList<Request> getRequests() {
        return requests;
    }

    public void setRequests(ArrayList<Request> requests) {
        this.requests = requests;
    }
}
