package com.example.taehaed.data;

import com.example.taehaed.Constans;
import com.example.taehaed.Pojo.FormReuest.FormData;
import com.example.taehaed.Pojo.FormReuest.FormRoot;
import com.example.taehaed.Pojo.Index.IndexRoot;
import com.example.taehaed.Pojo.LogIn.LoginRoot;
import com.example.taehaed.Pojo.LogoOut.StatusRoot;
import com.example.taehaed.Pojo.NoteBodey;
import com.example.taehaed.Pojo.NoteToShow.NotesRoot;
import com.example.taehaed.Pojo.Request.RequsetRoot;
import com.example.taehaed.Pojo.UserData;
import com.example.taehaed.Pojo.home.HomeRoot;

import java.util.Map;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class TaahiedImplements {

    private static final String baseUrl = "https://ms.taahied.com/api/employees/";
    TaaheidMathod taaheidMathod;
    //That is Signletion Pattern
    private static TaahiedImplements taahiedImplements;

    private TaahiedImplements() {
        Retrofit retrofit = new Retrofit.Builder().client(new OkHttpClient.Builder()
                        .addNetworkInterceptor(chain -> {
                            Request.Builder builder = chain.request().newBuilder();

                            builder.addHeader("Authorization", "Bearer " + Constans.Token);
                            builder.addHeader("Content-Type", "multipart/form-data");

                            Request request = builder.build();

                            Response response = chain.proceed(request);

                            return response;
                        })
                        .followRedirects(false).build()).baseUrl(baseUrl).
                addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create()).build();

        taaheidMathod = retrofit.create(TaaheidMathod.class);
    }

    public static TaahiedImplements getInstanse() {
        if (taahiedImplements == null) {
            taahiedImplements = new TaahiedImplements();

        }
        return taahiedImplements;
    }

    //end of Signeltion


    public Call<LoginRoot> InsertLogin(UserData userData) {
        return taaheidMathod.InsetLogin(userData);
    }

    public Call<IndexRoot> getIndex() {
        return taaheidMathod.getIndexs();
    }

    public Call<IndexRoot> getCurrentIndexs() {
        return taaheidMathod.getCurrentIndexs();
    }

    public Call<IndexRoot> getNewIndex() {
        return taaheidMathod.getNewIndexs();
    }

    public Call<HomeRoot> getHomeRute() {
        return taaheidMathod.getHomeRute();
    }

    public Call<RequsetRoot> getRequsetRoot(int clientId) {
        Call<RequsetRoot> call = taaheidMathod.getRequsetRoot(clientId);
        return call;
    }

    public Call<FormRoot> getOperationRequsetRoot(int request_service_id) {
        return taaheidMathod.getOperationRequset(request_service_id);
    }

    public Call<StatusRoot> DeletUserToken() {
        return taaheidMathod.logoutUser();
    }

    public Call<NotesRoot> getNotes(int request_service_id) {
        return taaheidMathod.getNotes(request_service_id);
    }

    public Call<StatusRoot> CancelRequstNote(NoteBodey noteBodey) {
        return taaheidMathod.CancelRequstNote(noteBodey);
    }

    public Call<StatusRoot> ConverNoteToAccept(int request_service_id) {
        return taaheidMathod.ConverNoteToAccept(request_service_id);
    }

    public Call<StatusRoot> ConverServiersAceptToCanel(NoteBodey noteBodey) {
        return taaheidMathod.ConverServiersAceptToCanel(noteBodey);
    }

    public Call<StatusRoot> setNotes(NoteBodey noteBodey) {
        return taaheidMathod.setNotes(noteBodey);
    }

    public Call<StatusRoot> setDoneservies(FormData formData) {
        return taaheidMathod.setDoneservies(formData);
    }

    public Call<StatusRoot> ConvertDoneToAccept(NoteBodey noteBodey) {
        return taaheidMathod.ConvertDoneToAccept(noteBodey);
    }

    public Observable<NotesRoot> ObesrveNotes(int request_service_id) {
        return taaheidMathod.ObesrveNotes(request_service_id);
    }

    public Call<StatusRoot> setDoneserviesWithFiels(MultipartBody.Part[] file, FormData formData) {
        return taaheidMathod.setDoneserviesWithFiels(  formData,file);
    }
    public Call<StatusRoot> setDoneserviesWithFiels2(RequestBody form){
        return taaheidMathod.setDoneserviesWithFiels2(form);
    }


    public Call<StatusRoot> setDoneserviesWithFiels3( Map<String, RequestBody> params){

        return taaheidMathod.setDoneserviesWithFiels3(params);
    }
}
