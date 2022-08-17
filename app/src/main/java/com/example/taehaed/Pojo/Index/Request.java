package com.example.taehaed.Pojo.Index;

import java.io.Serializable;

public class Request implements Serializable {
    private int id;
    private String actual_address;
    private String card_address;
    private String national_ID;
    private String fullname;
    private Object card_name;
    private String nick_name;
    private String mobile_number_1;
    private String mobile_number_2;
    private String phone;
    private Object actual_job;
    private Object card_job;
    private String gender;
    private String identity_card_photo_front;
    private String identity_card_photo_back;
    private String created_at;
    private int percentage;
    private Organization organization;
private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Request() {
    }

    public String getNational_ID() {
        return national_ID;
    }

    public void setNational_ID(String national_ID) {
        this.national_ID = national_ID;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public Object getCard_name() {
        return card_name;
    }

    public void setCard_name(Object card_name) {
        this.card_name = card_name;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getMobile_number_1() {
        return mobile_number_1;
    }

    public void setMobile_number_1(String mobile_number_1) {
        this.mobile_number_1 = mobile_number_1;
    }

    public String getMobile_number_2() {
        return mobile_number_2;
    }

    public void setMobile_number_2(String mobile_number_2) {
        this.mobile_number_2 = mobile_number_2;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Object getActual_job() {
        return actual_job;
    }

    public void setActual_job(Object actual_job) {
        this.actual_job = actual_job;
    }

    public Object getCard_job() {
        return card_job;
    }

    public void setCard_job(Object card_job) {
        this.card_job = card_job;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getActual_address() {
        return actual_address;
    }

    public void setActual_address(String actual_address) {
        this.actual_address = actual_address;
    }

    public String getCard_address() {
        return card_address;
    }

    public void setCard_address(String card_address) {
        this.card_address = card_address;
    }

    public String getIdentity_card_photo_front() {
        return identity_card_photo_front;
    }

    public void setIdentity_card_photo_front(String identity_card_photo_front) {
        this.identity_card_photo_front = identity_card_photo_front;
    }

    public String getIdentity_card_photo_back() {
        return identity_card_photo_back;
    }

    public void setIdentity_card_photo_back(String identity_card_photo_back) {
        this.identity_card_photo_back = identity_card_photo_back;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }
}
