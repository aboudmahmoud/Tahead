package com.example.taehaed.Model.ListenersForRespone;
/*
   this Intrface is Listner For Login user
   We use this in class Resportry and class LoginPage
   if userEmail and pass correct that setStatus( status = true , Token=usertokenLogin)
 */
public interface LoginStatus {

    public void setStatus(boolean status, String Token);
}
