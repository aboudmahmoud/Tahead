package com.example.taehaed.Model;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.taehaed.Constans;
import com.example.taehaed.Model.ListenersForRespone.AllJobLisnter;
import com.example.taehaed.Model.ListenersForRespone.CancelServes;
import com.example.taehaed.Model.ListenersForRespone.ConvertToAcceptedListner;
import com.example.taehaed.Model.ListenersForRespone.HomeRouteLisnter;
import com.example.taehaed.Model.ListenersForRespone.LoginStatus;
import com.example.taehaed.Model.ListenersForRespone.LogouStatus;
import com.example.taehaed.Model.ListenersForRespone.NoteStateListner;
import com.example.taehaed.Model.ListenersForRespone.OperationRequsetLisnet;
import com.example.taehaed.Model.ListenersForRespone.RequsetOberttionListner;
import com.example.taehaed.Model.ListenersForRespone.StatusApi;
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


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TaehaedVModel extends ViewModel {
    public MutableLiveData<IndexRoot> indexRootMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<HomeRoot> homeRouteMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<RequsetRoot> requsetRootMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<FormRoot> operationRequestRootMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<LoginRoot> UserData = new MutableLiveData<>();
    public MutableLiveData<NotesRoot> notesRootMutableLiveData = new MutableLiveData<>();

    private final Resportry resportry = new Resportry();

    public void InsertLogin(UserData userData, LoginStatus loginStatus) {
        resportry.InsertLogin(userData, loginStatus).enqueue(
                new Callback<LoginRoot>() {
                    @Override
                    public void onResponse(Call<LoginRoot> call, Response<LoginRoot> response) {
                        Log.d("Aboud", "onResponse:InsertLogin " + response.message());
                        Constans.Token = response.body().getToken();
                        UserData.setValue(response.body());
                        loginStatus.setStatus(response.isSuccessful(), response.body().getToken());


                    }

                    @Override
                    public void onFailure(Call<LoginRoot> call, Throwable t) {
                        Log.d("Aboud", "onFailure: InsertLogin " + t.getMessage());
                        loginStatus.setStatus(false, null);
                    }
                }
        );
    }


    public void getIndexes(AllJobLisnter lisnter) {
        resportry.geIndexRootCall().enqueue(new Callback<IndexRoot>() {
            @Override
            public void onResponse(Call<IndexRoot> call, Response<IndexRoot> response) {
                Log.d("Aboud", "onResponse: getIndexes "+response.message());
                indexRootMutableLiveData.setValue(response.body());
                lisnter.getJobs(true);

            }

            @Override
            public void onFailure(Call<IndexRoot> call, Throwable t) {
                lisnter.getJobs(false);

            }
        });
    }

    // In The Moment no use for this method
    public void getHomeRoute(HomeRouteLisnter homeRouteLisnter) {
        resportry.getHomeRute().enqueue(new Callback<HomeRoot>() {
            @Override
            public void onResponse(Call<HomeRoot> call, Response<HomeRoot> response) {
                homeRouteMutableLiveData.setValue(response.body());

                homeRouteLisnter.getThehomePage(true);
            }

            @Override
            public void onFailure(Call<HomeRoot> call, Throwable t) {

                homeRouteLisnter.getThehomePage(false);
            }
        });
    }

    // In The Moment no use for this method
    public void getNewIndex(AllJobLisnter lisnter) {
        resportry.getNewIndex().enqueue(new Callback<IndexRoot>() {
            @Override
            public void onResponse(Call<IndexRoot> call, Response<IndexRoot> response) {
                indexRootMutableLiveData.setValue(response.body());

                lisnter.getJobs(true);
            }

            @Override
            public void onFailure(Call<IndexRoot> call, Throwable t) {
                lisnter.getJobs(false);

            }
        });
    }

    // In The Moment no use for this method
    public void getCurrentIndes(AllJobLisnter lisnter) {
        resportry.getCurrentIndexs().enqueue(new Callback<IndexRoot>() {
            @Override
            public void onResponse(Call<IndexRoot> call, Response<IndexRoot> response) {
                indexRootMutableLiveData.setValue(response.body());
                lisnter.getJobs(true);
            }

            @Override
            public void onFailure(Call<IndexRoot> call, Throwable t) {
                lisnter.getJobs(false);

            }
        });
    }

    public void getRqusetOpertatiom(int clientId, RequsetOberttionListner listner) {
        resportry.getRequsetRoot(clientId).enqueue(new Callback<RequsetRoot>() {
            @Override
            public void onResponse(Call<RequsetRoot> call, Response<RequsetRoot> response) {
                Log.d("Aboud", "onResponse: getRqusetOpertatiom " + response.body().message);
                requsetRootMutableLiveData.setValue(response.body());

                listner.statusOperation(true);

            }

            @Override
            public void onFailure(Call<RequsetRoot> call, Throwable t) {
                Log.d("Aboud", "onFailure: " + t.getMessage());
                listner.statusOperation(false);

            }
        });
    }

    public void getOperationReuqest(int request_service_id, OperationRequsetLisnet operationRequsetLisnet) {
        resportry.getOperationRequsetRoot(request_service_id).enqueue(new Callback<FormRoot>() {
            @Override
            public void onResponse(Call<FormRoot> call, Response<FormRoot> response) {
                Log.d("Aboud", "onResponse: getOperationReuqest " + response.body().message);
                operationRequestRootMutableLiveData.setValue(response.body());
                operationRequsetLisnet.getStatus(true);

            }

            @Override
            public void onFailure(Call<FormRoot> call, Throwable t) {
                operationRequsetLisnet.getStatus(false);
                Log.d("Aboud", "onFailure:getOperationReuqest " + t.getMessage());
                Log.d("Aboud", "onFailure:getOperationReuqest " + t);
            }
        });
    }

    public void DeletUserToken(LogouStatus logouStatus) {
        resportry.DeletUserToken().enqueue(new Callback<StatusRoot>() {
            @Override
            public void onResponse(Call<StatusRoot> call, Response<StatusRoot> response) {
                logouStatus.getStatus(response.isSuccessful());
            }

            @Override
            public void onFailure(Call<StatusRoot> call, Throwable t) {
                Log.d("Aboud ", "onFailure DeletUserToken: " + t.getMessage());
                logouStatus.getStatus(false);
            }
        });
    }

    public void getNotes(int id, NoteStateListner noteStateListner)
    {
        resportry.getNotes(id).enqueue(new Callback<NotesRoot>() {
            @Override
            public void onResponse(Call<NotesRoot> call, Response<NotesRoot> response) {
                Log.d("aboud", "onResponse: getNotes"+response.body().getMessage());
                notesRootMutableLiveData.setValue(response.body());
                noteStateListner.getStatus(true);
            }

            @Override
            public void onFailure(Call<NotesRoot> call, Throwable t) {
                Log.d("Aboud", "onFailure: getNotes "+t.getMessage());
                noteStateListner.getStatus(false);
            }
        });
    }

    public void CancelRequstNote(NoteBodey noteBodey, CancelServes cancelServes)
    {
        resportry.CancelRequstNote(noteBodey).enqueue(new Callback<StatusRoot>() {
            @Override
            public void onResponse(Call<StatusRoot> call, Response<StatusRoot> response) {
                Log.d("Aboud", "onResponse: CancelRequstNote"+response.body().getMessage());
                cancelServes.canseStatus(true);
            }

            @Override
            public void onFailure(Call<StatusRoot> call, Throwable t) {
                Log.d("Aboud", "onFailure: CancelRequstNote "+ t.getMessage());
                cancelServes.canseStatus(false);
            }
        });
    }

    public void ConverNoteToAccept(int request_service_id, ConvertToAcceptedListner convertToAcceptedListner)
    {
        resportry.ConverNoteToAccept(request_service_id).enqueue(new Callback<StatusRoot>() {
            @Override
            public void onResponse(Call<StatusRoot> call, Response<StatusRoot> response) {
                Log.d("Aboud", "onResponse: ConverNoteToAccept"+response.body().getMessage());

                convertToAcceptedListner.Setstatus(true);
            }

            @Override
            public void onFailure(Call<StatusRoot> call, Throwable t) {
                Log.d("Aboud", "onFailure: ConverNoteToAccept "+ t.getMessage());
                convertToAcceptedListner.Setstatus(false);
            }
        });
    }

    public void ConverServiesToCanse(NoteBodey noteBodey, CancelServes cancelServes)
    {
        resportry.ConverServiersAceptToCanel(noteBodey).enqueue(new Callback<StatusRoot>() {
            @Override
            public void onResponse(Call<StatusRoot> call, Response<StatusRoot> response) {
                Log.d("Aboud", "onResponse:  ConverServiesToCanse "+response.body().getMessage());
                cancelServes.canseStatus(response.body().isSuccessful());
            }

            @Override
            public void onFailure(Call<StatusRoot> call, Throwable t) {
                Log.d("Aboud", "onFailure:  ConverServiesToCanse "+t.getMessage());
                cancelServes.canseStatus(false);
            }
        });
    }

    public void SendNote(NoteBodey noteBodey, CancelServes cancelServes)
    {
        resportry.setNotes(noteBodey).enqueue(new Callback<StatusRoot>() {
            @Override
            public void onResponse(Call<StatusRoot> call, Response<StatusRoot> response) {
                Log.d("Aboud", "onResponse:  SendNote "+response.body().getMessage());
                cancelServes.canseStatus(response.body().isSuccessful());
            }

            @Override
            public void onFailure(Call<StatusRoot> call, Throwable t) {
                Log.d("Aboud", "onFailure:  SendNote "+t.getMessage());
                cancelServes.canseStatus(false);
            }
        });
    }

    public void setDoneservies(FormData formData, StatusApi statusApi)
    {
        resportry.setDoneservies(formData).enqueue(new Callback<StatusRoot>() {
            @Override
            public void onResponse(Call<StatusRoot> call, Response<StatusRoot> response) {
                Log.d("Aboud ", "onResponse: setDoneservies " +response.body().getMessage());
                statusApi.SetStatus(response.body().isSuccessful());
            }

            @Override
            public void onFailure(Call<StatusRoot> call, Throwable t) {
                Log.d("Aboud", "onFailure: setDoneservies "+ t.getMessage());
                statusApi.SetStatus(false);
            }
        });
    }

    public void ConvertDoneToAccept(NoteBodey noteBodey,StatusApi statusApi)
    {
        resportry.ConvertDoneToAccept(noteBodey).enqueue(new Callback<StatusRoot>() {
            @Override
            public void onResponse(Call<StatusRoot> call, Response<StatusRoot> response) {
                Log.d("Aboud ", "onResponse: ConvertDoneToAccept " +response.body().getMessage());
                statusApi.SetStatus(response.body().isSuccessful());
            }

            @Override
            public void onFailure(Call<StatusRoot> call, Throwable t) {
                Log.d("Aboud", "onFailure: ConvertDoneToAccept "+ t.getMessage());
                statusApi.SetStatus(false);
            }
        });
    }

}
