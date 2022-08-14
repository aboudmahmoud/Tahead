package com.example.taehaed.Pojo.NoteToShow;

import java.io.Serializable;

public class Note implements Serializable {
    private int id;
    private int employee_id;
    private int request_service_id;
    private String note;
    private String created_at;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }

    public int getRequest_service_id() {
        return request_service_id;
    }

    public void setRequest_service_id(int request_service_id) {
        this.request_service_id = request_service_id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
