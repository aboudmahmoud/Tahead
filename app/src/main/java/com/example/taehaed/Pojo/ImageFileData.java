package com.example.taehaed.Pojo;

import android.net.Uri;

public class ImageFileData {
    private  Uri imageLink;
    private String fileName;
    private int status;
    public ImageFileData() {
    }

    public ImageFileData(Uri imageLink, String fileName) {
        this.imageLink = imageLink;
        this.fileName = fileName;
    }

    public ImageFileData(Uri imageLink, String fileName, int status) {
        this.imageLink = imageLink;
        this.fileName = fileName;
        this.status = status;
    }

    public Uri getImageLink() {
        return imageLink;
    }

    public void setImageLink(Uri imageLink) {
        this.imageLink = imageLink;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
