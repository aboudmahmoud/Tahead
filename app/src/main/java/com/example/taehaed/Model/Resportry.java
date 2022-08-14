package com.example.taehaed.Model;

import com.example.taehaed.Model.ListenersForRespone.LoginStatus;
import com.example.taehaed.Pojo.FormReuest.FormData;
import com.example.taehaed.Pojo.FormReuest.FormRoot;
import com.example.taehaed.Pojo.LogoOut.StatusRoot;
import com.example.taehaed.Pojo.NoteBodey;
import com.example.taehaed.Pojo.NoteToShow.NotesRoot;
import com.example.taehaed.data.TaahiedImplements;
import com.example.taehaed.Pojo.Index.IndexRoot;
import com.example.taehaed.Pojo.LogIn.LoginRoot;
import com.example.taehaed.Pojo.Request.RequsetRoot;
import com.example.taehaed.Pojo.UserData;
import com.example.taehaed.Pojo.home.HomeRoot;


import retrofit2.Call;


public class Resportry {

    //This listner to use On logoin and implemantion on Login page

    /*+
    .enqueue(new Callback<LoginRoot>() {
            @Override
            public void onResponse(Call<LoginRoot> call, Response<LoginRoot> response) {
                Log.d("Aboud", "onResponse:InsertLogin "+response.message());
                Constans.Token = response.body().getToken();
                loginStatus.setStatus(response.body().getSuccesstate(),response.body().getToken());


            }

            @Override
            public void onFailure(Call<LoginRoot> call, Throwable t) {
                Log.d("Aboud", "onFailure: InsertLogin "+t.getMessage());
                loginStatus.setStatus(false,null);
            }
        });
     */
    public  Call<LoginRoot>  InsertLogin(UserData userData, LoginStatus loginStatus) {

       return TaahiedImplements.getInstanse().InsertLogin(userData);


    }

    public Call<IndexRoot> geIndexRootCall() {
        return TaahiedImplements.getInstanse().getIndex();
    }

    public Call<HomeRoot> getHomeRute() {
        return TaahiedImplements.getInstanse().getHomeRute();
    }


    public Call<IndexRoot> getCurrentIndexs() {
        return TaahiedImplements.getInstanse().getCurrentIndexs();
    }

    public Call<IndexRoot> getNewIndex() {
        return TaahiedImplements.getInstanse().getNewIndex();
    }

    public Call<RequsetRoot> getRequsetRoot(int clientId){
        return TaahiedImplements.getInstanse().getRequsetRoot(clientId);
    }
    public Call<FormRoot> getOperationRequsetRoot(int request_service_id){
        return TaahiedImplements.getInstanse().getOperationRequsetRoot(request_service_id);}

    public Call<StatusRoot> DeletUserToken(){
        return TaahiedImplements.getInstanse().DeletUserToken();
    }
    public Call<NotesRoot> getNotes(int request_service_id)
    {
        return TaahiedImplements.getInstanse().getNotes(request_service_id);
    }
    public Call<StatusRoot> CancelRequstNote(NoteBodey noteBodey){
        return TaahiedImplements.getInstanse().CancelRequstNote(noteBodey);
    }

    public Call<StatusRoot> ConverNoteToAccept( int request_service_id)
    {
        return TaahiedImplements.getInstanse().ConverNoteToAccept(request_service_id);
    }
    public Call<StatusRoot> ConverServiersAceptToCanel( NoteBodey noteBodey)
    {
        return TaahiedImplements.getInstanse().ConverServiersAceptToCanel(noteBodey);
    }
    public Call<StatusRoot> setNotes( NoteBodey noteBodey){
        return TaahiedImplements.getInstanse().setNotes(noteBodey);
    }

    public Call<StatusRoot> setDoneservies( FormData formData)
    {
        return TaahiedImplements.getInstanse().setDoneservies(formData);
    }


    public Call<StatusRoot>  ConvertDoneToAccept( NoteBodey noteBodey){
        return TaahiedImplements.getInstanse().ConvertDoneToAccept(noteBodey);
    }
}
