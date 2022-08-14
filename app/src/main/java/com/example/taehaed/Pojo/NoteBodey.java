package com.example.taehaed.Pojo;

import com.google.gson.annotations.SerializedName;

public class NoteBodey {
    @SerializedName("request_service_id")
    private int request_service_id ;
    @SerializedName("report")
    private String report;
    @SerializedName("note")
    private String note;

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getRequest_service_id() {
        return request_service_id;
    }

    public void setRequest_service_id(int request_service_id) {
        this.request_service_id = request_service_id;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }
}
