package com.example.taehaed.data;

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

import io.reactivex.rxjava3.core.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface TaaheidMathod {

    @POST("login")
    public Call<LoginRoot> InsetLogin(@Body UserData userData);
    @POST("logout")
    public Call<StatusRoot> logoutUser();
    @POST("request_services/assigned/cancel")
    public Call<StatusRoot> CancelRequstNote(@Body NoteBodey noteBodey);
    @POST("request_services/accept")
    public Call<StatusRoot> ConverNoteToAccept(@Query("request_service_id") int request_service_id);
    @POST("request_services/accept/cancel")
    public Call<StatusRoot> ConverServiersAceptToCanel(@Body NoteBodey noteBodey);

    @POST("request_services/notes/create")
    public Call<StatusRoot> setNotes(@Body NoteBodey noteBodey);




    @POST("request_services/done")
    public Call<StatusRoot> setDoneservies( @Body FormData formData);

    @Multipart
    @POST("request_services/done")
    public Call<StatusRoot> setDoneserviesWithFiels(@Part MultipartBody.Part[] file,@Part("json") RequestBody json);

    @POST("request_services/done/cancel")
    public Call<StatusRoot>  ConvertDoneToAccept(@Body NoteBodey noteBodey);


    @GET("requests")
    public Call<IndexRoot> getIndexs();
    @GET("requests?status=2")
    public Call<IndexRoot>getNewIndexs();
    @GET("requests?status=3")
    public Call<IndexRoot>getCurrentIndexs();
    @GET("home")
    public Call<HomeRoot> getHomeRute();
    @GET("requests/show?")
    public Call<RequsetRoot> getRequsetRoot(@Query("request_id") int clientId);

    @GET("request_services/show?")
    public Call<FormRoot> getOperationRequset(@Query("request_service_id") int request_service_id);
    @GET("request_services/notes?")
    public Call<NotesRoot> getNotes(@Query("request_service_id") int request_service_id);
    @GET("request_services/notes?")
    public Observable<NotesRoot> ObesrveNotes(@Query("request_service_id") int request_service_id);

}
