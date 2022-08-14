package com.example.taehaed.Model.ListenersForRespone;
 /*
 this listner to when the new and current data are recved
 We Use it in Class TaehedVModel and  Class MainPage in 62 line
 if data is Colactect form api than getJobs(status=true);
   */
public interface AllJobLisnter {
    public void getJobs(boolean status);
}
