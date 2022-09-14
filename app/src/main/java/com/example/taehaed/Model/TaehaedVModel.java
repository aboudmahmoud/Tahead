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
import com.example.taehaed.Model.ListenersForRespone.OberverTheError;
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

import java.io.File;
import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
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
                        if (response.isSuccessful()) {
                            if (response.body().getSuccesstate()) {

                                Constans.Token = response.body().getToken();
                                UserData.setValue(response.body());
                                loginStatus.setStatus(true, response.body().getToken(),null);
                            } else {
                                Log.d("Aboud", "onResponse:InsertLogin response body " + response.body().getMessage());
                                loginStatus.setStatus(false, null,response.body().getMessage());
                            }
                        } else {
                            Log.d("Aboud", "onResponse:InsertLogin response " + response.message());
                            loginStatus.setStatus(false, null, response.message());
                        }


                    }

                    @Override
                    public void onFailure(Call<LoginRoot> call, Throwable t) {
                        Log.d("Aboud", "onFailure: InsertLogin " + t.getMessage());
                        loginStatus.setStatus(false, null, t.getMessage());
                    }
                }
        );
    }


    public void getIndexes(AllJobLisnter lisnter) {
        resportry.geIndexRootCall().enqueue(new Callback<IndexRoot>() {
            @Override
            public void onResponse(Call<IndexRoot> call, Response<IndexRoot> response) {
                if (response.isSuccessful()) {

                    if(response.body().isSuccessful())
                    {
                        Log.d("Aboud", "onResponse: getIndexes " + response.body().getMessage());
                        indexRootMutableLiveData.setValue(response.body());
                        lisnter.getJobs(true,null);
                    }else{
                        Log.d("Aboud", "onResponse: Faield api" + response.body().getMessage());
                        lisnter.getJobs(false,response.body().getMessage());

                    }
                } else {
                    Log.d("Aboud", "onResponse: getIndexes  Faield link " + response.message());

                    lisnter.getJobs(false, response.message());
                }

            }

            @Override
            public void onFailure(Call<IndexRoot> call, Throwable t) {
                Log.d("Aboud", "onFailure: getIndexes " + t.getMessage());
                lisnter.getJobs(false, t.getMessage());

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

   /* // In The Moment no use for this method
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


                if (response.isSuccessful()) {
                    indexRootMutableLiveData.setValue(response.body());
                    lisnter.getJobs(response.body().isSuccessful());
                } else {
                    lisnter.getJobs(false);
                }
            }

            @Override
            public void onFailure(Call<IndexRoot> call, Throwable t) {
                lisnter.getJobs(false);

            }
        });
    }*/

    public void getRqusetOpertatiom(int clientId, RequsetOberttionListner listner) {
        resportry.getRequsetRoot(clientId).enqueue(new Callback<RequsetRoot>() {
            @Override
            public void onResponse(Call<RequsetRoot> call, Response<RequsetRoot> response) {
                if (response.isSuccessful()) {
                    if (response.body().successful) {
                        Log.d("Aboud", "onResponse: getRqusetOpertatiom " + response.body().message);
                        requsetRootMutableLiveData.setValue(response.body());
                        listner.statusOperation(true, null);
                    } else {
                        Log.d("Aboud", "onResponse: body faield " + response.body().message);
                        listner.statusOperation(false, response.body().message);
                    }


                } else {
                    Log.d("Aboud", "onResponse: faield " + response.message());
                    listner.statusOperation(false, response.message());
                }

            }

            @Override
            public void onFailure(Call<RequsetRoot> call, Throwable t) {
                Log.d("Aboud", "onFailure: " + t.getMessage());
                listner.statusOperation(false, t.getMessage());

            }
        });
    }

    public void getOperationReuqest(int request_service_id, OperationRequsetLisnet operationRequsetLisnet) {
        resportry.getOperationRequsetRoot(request_service_id).enqueue(new Callback<FormRoot>() {
            @Override
            public void onResponse(Call<FormRoot> call, Response<FormRoot> response) {
                if (response.isSuccessful()) {
                    operationRequestRootMutableLiveData.setValue(response.body());
                    operationRequsetLisnet.getStatus(response.body().successful);
                } else {
                    Log.d("Aboud", "onResponse: getOperationReuqest " + response.message());
                    operationRequsetLisnet.getStatus(false);
                }

            }

            @Override
            public void onFailure(Call<FormRoot> call, Throwable t) {
                operationRequsetLisnet.getStatus(false);
                Log.d("Aboud", "onFailure:getOperationReuqest " + t.getMessage());

            }
        });
    }

    public void DeletUserToken(LogouStatus logouStatus) {
        resportry.DeletUserToken().enqueue(new Callback<StatusRoot>() {
            @Override
            public void onResponse(Call<StatusRoot> call, Response<StatusRoot> response) {
                if (response.isSuccessful()) {
                    logouStatus.getStatus(response.body().isSuccessful());


                } else {
                    logouStatus.getStatus(false);
                }
            }

            @Override
            public void onFailure(Call<StatusRoot> call, Throwable t) {
                Log.d("Aboud ", "onFailure DeletUserToken: " + t.getMessage());
                logouStatus.getStatus(false);
            }
        });
    }

    public void getNotes(int id, NoteStateListner noteStateListner) {
        resportry.getNotes(id).enqueue(new Callback<NotesRoot>() {
            @Override
            public void onResponse(Call<NotesRoot> call, Response<NotesRoot> response) {


                if (response.isSuccessful()) {
                    Log.d("aboud", "onResponse: getNotes" + response.body().getMessage());
                    notesRootMutableLiveData.setValue(response.body());
                    noteStateListner.getStatus(response.body().isSuccessful());

                } else {
                    noteStateListner.getStatus(response.isSuccessful());
                    Log.d("aboud", "onResponse: getNotes" + response.message());
                }
            }

            @Override
            public void onFailure(Call<NotesRoot> call, Throwable t) {
                Log.d("Aboud", "onFailure: getNotes " + t.getMessage());
                noteStateListner.getStatus(false);
            }
        });
    }

    public void CancelRequstNote(NoteBodey noteBodey, CancelServes cancelServes) {
        resportry.CancelRequstNote(noteBodey).enqueue(new Callback<StatusRoot>() {
            @Override
            public void onResponse(Call<StatusRoot> call, Response<StatusRoot> response) {


                if (response.isSuccessful()) {
                    Log.d("Aboud", "onResponse: CancelRequstNote" + response.body().getMessage());
                    cancelServes.canseStatus(true);
                } else {
                    Log.d("Aboud", "onResponse: CancelRequstNote" + response.message());
                    cancelServes.canseStatus(false);
                }
            }

            @Override
            public void onFailure(Call<StatusRoot> call, Throwable t) {
                Log.d("Aboud", "onFailure: CancelRequstNote " + t.getMessage());
                cancelServes.canseStatus(false);
            }
        });
    }

    public void ConverNoteToAccept(int request_service_id, ConvertToAcceptedListner convertToAcceptedListner) {
        resportry.ConverNoteToAccept(request_service_id).enqueue(new Callback<StatusRoot>() {
            @Override
            public void onResponse(Call<StatusRoot> call, Response<StatusRoot> response) {


                if (response.isSuccessful()) {
                    Log.d("Aboud", "onResponse: ConverNoteToAccept" + response.body().getMessage());
                    convertToAcceptedListner.Setstatus(response.body().isSuccessful());


                } else {
                    Log.d("Aboud", "onResponse: ConverNoteToAccept" + response.message());
                    convertToAcceptedListner.Setstatus(false);
                }
            }

            @Override
            public void onFailure(Call<StatusRoot> call, Throwable t) {
                Log.d("Aboud", "onFailure: ConverNoteToAccept " + t.getMessage());
                convertToAcceptedListner.Setstatus(false);
            }
        });
    }

    public void ConverServiesToCanse(NoteBodey noteBodey, CancelServes cancelServes) {
        resportry.ConverServiersAceptToCanel(noteBodey).enqueue(new Callback<StatusRoot>() {
            @Override
            public void onResponse(Call<StatusRoot> call, Response<StatusRoot> response) {


                if (response.isSuccessful()) {
                    cancelServes.canseStatus(response.body().isSuccessful());
                    Log.d("Aboud", "onResponse:  ConverServiesToCanse " + response.body().getMessage());
                } else {
                    Log.d("Aboud", "onResponse:  ConverServiesToCanse " + response.message());
                    cancelServes.canseStatus(false);
                }
            }

            @Override
            public void onFailure(Call<StatusRoot> call, Throwable t) {
                Log.d("Aboud", "onFailure:  ConverServiesToCanse " + t.getMessage());
                cancelServes.canseStatus(false);
            }
        });
    }

    public void SendNote(NoteBodey noteBodey, StatusApi cancelServes) {
        resportry.setNotes(noteBodey).enqueue(new Callback<StatusRoot>() {
            @Override
            public void onResponse(Call<StatusRoot> call, Response<StatusRoot> response) {


                if (response.isSuccessful()) {
                    if(response.body().isSuccessful())
                    {
                        Log.d("Aboud", "onResponse:  SendNote " + response.body().getMessage());
                        cancelServes.SetStatus(true,null);
                    }else{
                        Log.d("Aboud", "onResponse:  FaliledApi " + response.body().getMessage());
                        cancelServes.SetStatus(false,response.body().getMessage());
                    }

                } else {
                    Log.d("Aboud", "onResponse:  Falied link  " + response.message());
                    cancelServes.SetStatus(false,response.message());
                }
            }

            @Override
            public void onFailure(Call<StatusRoot> call, Throwable t) {
                Log.d("Aboud", "onFailure:  SendNote " + t.getMessage());
                cancelServes.SetStatus(false,t.getMessage());
            }
        });
    }

    public void setDoneservies(FormData formData, StatusApi statusApi) {
        resportry.setDoneservies(formData).enqueue(new Callback<StatusRoot>() {
            @Override
            public void onResponse(Call<StatusRoot> call, Response<StatusRoot> response) {
                if (response.isSuccessful()) {
                    if (response.body().isSuccessful()) {
                        Log.d("Aboud ", "onResponse: setDoneservies Body Succes  " + response.body().getMessage());
                        statusApi.SetStatus(true,null);
                    } else {
                        Log.d("Aboud ", "onResponse: setDoneservies Body fail  " + response.body().getMessage());
                        statusApi.SetStatus(false,response.body().getMessage());
                    }


                } else {
                    Log.d("Aboud ", "onfalier: setDoneservies  " + response + " " + response.message() + " " + response.code() + " " + response.raw() + " msh " + response.headers());
                    statusApi.SetStatus(false,response.message());
                }

            }

            @Override
            public void onFailure(Call<StatusRoot> call, Throwable t) {
                Log.d("Aboud", "onFailure: setDoneservies " + t.getMessage());
                statusApi.SetStatus(false,t.getMessage());
            }
        });
    }

    public void ConvertDoneToAccept(NoteBodey noteBodey, StatusApi statusApi) {
        resportry.ConvertDoneToAccept(noteBodey).enqueue(new Callback<StatusRoot>() {
            @Override
            public void onResponse(Call<StatusRoot> call, Response<StatusRoot> response) {


                if (response.isSuccessful()) {


                    if(response.body().isSuccessful())
                    {
                        statusApi.SetStatus(true,null);
                    }else{
                        Log.d("Aboud ", "onResponse: ConvertDoneToAccept API Field" + response.body().getMessage());
                        statusApi.SetStatus(false, response.body().getMessage());
                    }
                } else {
                    Log.d("Aboud ", "onResponse: ConvertDoneToAccept Link Field" + response.message());
                    statusApi.SetStatus(false,response.message());
                }
            }

            @Override
            public void onFailure(Call<StatusRoot> call, Throwable t) {
                Log.d("Aboud", "onFailure: ConvertDoneToAccept onFailure " + t.getMessage());
                statusApi.SetStatus(false,t.getMessage());
            }
        });
    }

    public void ObesverNotes(int request_service_id, OberverTheError oberverTheError) {
        Observable<NotesRoot> notesRootObservable = resportry.ObesrveNotes(request_service_id)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());

        notesRootObservable.subscribe(o -> {
                    notesRootMutableLiveData.setValue(o);
                    if (!o.isSuccessful()) {
                        oberverTheError.errormessage(true, o.getMessage());
                    } else {
                        oberverTheError.errormessage(false, null);
                    }
                }
                , error -> {
                    oberverTheError.errormessage(true, error.getMessage());

                });

    }


  /*
  الموضوع يطول شرحه بس باختصار
  دلوقتي عندنا 10 فورمات
  كل فورم فيه ملف بيتبعت (كان في الاول مفيش ملفات بتبعتب )
  فكان المشكلة عشان اعرف ابعت ملف ل سيرفر محتاج افصل الدتا كلها وابعت كل واحد لوحدها
  في الحل كان اني اعمل كونفرتور بيسيط في الريبوزرتي
  الريبوزتي حولته لملف كولتن عشان الاسباب الاتاتية
  البرامتير الاختياريه (اذا ماعرفتها ابحث عن choseoable pramiter )
  و ميثود apply
  المهم في ملف الكولتن هتلاقي ميثود واحده فقط بتبعت الملفات  هي باختصار بتاخد الملفات من اي ميثود هنا
   choseoable pramiter الحل الامثل كان اني استخدم كولتن من البداية بدل مانا مكرر ميثود لكل فورم .. واللي يجي بنل اتجاهله .. ازاي ؟ ارجع ابحث علي
   المهم ان دلوقتي الفايلات بتبعت  .. لو في المستقبل فيه نوع جديد لازم يتضاف .. هتضطر تعمل عملية الاضافة دي في صفحة Resporty
   */
    public void setDoneserviesWithFielsFroms(FormData form, ArrayList<File> attachments, OberverTheError oberverTheError) {
        resportry.setDoneserviesWithFiels(form, null, null,
                        null,
                        null, null
                        , null,
                        null,
                        null,
                        null, null,
                        null, null, null, null, null, null,
                        null, null,  attachments)
                .enqueue(new Callback<StatusRoot>() {
                    @Override
                    public void onResponse(Call<StatusRoot> call, Response<StatusRoot> response) {
                        if (response.isSuccessful()) {
                            if (response.body().isSuccessful()) {
                                ;
                                oberverTheError.errormessage(false, null);
                            } else {
                                Log.d("Aboud", "onResponse setDoneserviesWithFiels2 response api Failed: " + response.body().getMessage());
                                oberverTheError.errormessage(true, "response api Failed " + response.body().getMessage());

                            }
                        } else {
                            Log.d("Aboud", "onResponse setDoneserviesWithFiels2 response link Failed: " + response.message());
                            oberverTheError.errormessage(true, "response link Failed " + response.message());

                        }

                    }

                    @Override
                    public void onFailure(Call<StatusRoot> call, Throwable t) {
                        oberverTheError.errormessage(true, "onFailure " + t.getMessage());
                        Log.d("Aboud", "onFailure setDoneserviesWithFiels2: " + t.getMessage());
                    }
                });
    }
    public void setDoneserviesWithFielsFrom2(FormData form, ArrayList<File> result_attached_utilities_receipt,
                                             ArrayList<File> result_attached_husband_national_id,ArrayList<File> attachments, OberverTheError oberverTheError) {
        resportry.setDoneserviesWithFiels(form, result_attached_utilities_receipt, result_attached_husband_national_id,
                        null,
                        null, null
                        , null,
                        null,
                        null,
                        null, null,
                        null, null, null, null, null, null,
                        null, null,  attachments)
                .enqueue(new Callback<StatusRoot>() {
                    @Override
                    public void onResponse(Call<StatusRoot> call, Response<StatusRoot> response) {
                        if (response.isSuccessful()) {
                            if (response.body().isSuccessful()) {
                             ;
                                oberverTheError.errormessage(false, null);
                            } else {
                                Log.d("Aboud", "onResponse setDoneserviesWithFiels2 response api Failed: " + response.body().getMessage());
                                oberverTheError.errormessage(true, "response api Failed " + response.body().getMessage());

                            }
                        } else {
                            Log.d("Aboud", "onResponse setDoneserviesWithFiels2 response link Failed: " + response.message());
                            oberverTheError.errormessage(true, "response link Failed " + response.message());

                        }

                    }

                    @Override
                    public void onFailure(Call<StatusRoot> call, Throwable t) {
                        oberverTheError.errormessage(true, "onFailure " + t.getMessage());
                        Log.d("Aboud", "onFailure setDoneserviesWithFiels2: " + t.getMessage());
                    }
                });
    }

    public void setDoneserviesWithFielsFrom4(FormData form, ArrayList<File> vehicle_result_attachments_driving_license,
                                             ArrayList<File> vehicle_result_attachments_national_id,
                                             ArrayList<File> vehicle_result_attachments_vehicle_license,
                                             ArrayList<File> vehicle_result_attachments_purchase_contract,ArrayList<File> attachments, OberverTheError oberverTheError) {
        resportry.setDoneserviesWithFiels(form, null, null,
                        vehicle_result_attachments_national_id,
                        vehicle_result_attachments_driving_license,
                        vehicle_result_attachments_vehicle_license,
                        vehicle_result_attachments_purchase_contract,
                        null,
                        null,
                        null, null,
                        null, null,
                        null,
                        null, null,
                        null,
                        null, null, attachments)
                .enqueue(new Callback<StatusRoot>() {
                    @Override
                    public void onResponse(Call<StatusRoot> call, Response<StatusRoot> response) {
                        if (response.isSuccessful()) {
                            if (response.body().isSuccessful()) {
                                Log.d("Aboud", "onResponse setDoneserviesWithFiels4 response api Successfu: " + response.body().getMessage());
                                oberverTheError.errormessage(false, null);
                            } else {
                                Log.d("Aboud", "onResponse setDoneserviesWithFiel4 response api Failed: " + response.body().getMessage());
                                oberverTheError.errormessage(true, response.body().getMessage());

                            }
                        } else {
                            Log.d("Aboud", "onResponse setDoneserviesWithFiels4 response link Failed: " + response.message());
                            oberverTheError.errormessage(true,  response.message());

                        }

                    }

                    @Override
                    public void onFailure(Call<StatusRoot> call, Throwable t) {
                        oberverTheError.errormessage(true, "onFailure " + t.getMessage());
                        Log.d("Aboud", "onFailure setDoneserviesWithFiels2: " + t.getMessage());
                    }
                });
    }

    public void setDoneserviesWithFielsFrom5(FormData form, ArrayList<File> business_result_attachment_owner,
                                             ArrayList<File> business_result_attachment_amenities_receipt,
                                             ArrayList<File> business_result_attachment_commercial_record,
                                             ArrayList<File> business_result_attachment_tax_card,
                                             ArrayList<File> business_result_attachment_activity_license,
                                             ArrayList<File> business_result_attachment_partner_national_id,ArrayList<File> attachments
            , OberverTheError oberverTheError) {
        resportry.setDoneserviesWithFiels(form, null, null,
                        null,
                        null,
                        null,
                        null,
                        business_result_attachment_owner,
                        business_result_attachment_amenities_receipt,
                        business_result_attachment_commercial_record,
                        business_result_attachment_tax_card,
                        business_result_attachment_activity_license,
                        business_result_attachment_partner_national_id,
                        null,
                        null, null,
                        null,
                        null, null,attachments)
                .enqueue(new Callback<StatusRoot>() {
                    @Override
                    public void onResponse(Call<StatusRoot> call, Response<StatusRoot> response) {
                        if (response.isSuccessful()) {
                            if (response.body().isSuccessful()) {
                                Log.d("Aboud", "onResponse setDoneserviesWithFiels2 response api Failed: " + response.body().getMessage());
                                oberverTheError.errormessage(false, null);
                            } else {
                                Log.d("Aboud", "onResponse setDoneserviesWithFiels2 response api Failed: " + response.body().getMessage());
                                oberverTheError.errormessage(true, "response api Failed " + response.body().getMessage());

                            }
                        } else {
                            Log.d("Aboud", "onResponse setDoneserviesWithFiels2 response link Failed: " + response.message());
                            oberverTheError.errormessage(true, "response link Failed " + response.message());

                        }

                    }

                    @Override
                    public void onFailure(Call<StatusRoot> call, Throwable t) {
                        oberverTheError.errormessage(true, "onFailure " + t.getMessage());
                        Log.d("Aboud", "onFailure setDoneserviesWithFiels2: " + t.getMessage());
                    }
                });
    }

    public void setDoneserviesWithFielsFrom6(FormData form, ArrayList<File> home_activity_result_attachments_owner,
                                             ArrayList<File> home_activity_result_attachments_amenities_receipt,
                                             ArrayList<File> home_activity_result_attachments_supplier_invoices,
                                             ArrayList<File> home_activity_result_attachments_sales_statements,ArrayList<File> attachments

            , OberverTheError oberverTheError) {
        resportry.setDoneserviesWithFiels(form, null, null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        home_activity_result_attachments_owner,
                        home_activity_result_attachments_amenities_receipt
                        , home_activity_result_attachments_supplier_invoices,
                        home_activity_result_attachments_sales_statements,
                         null,
                        null,attachments)
                .enqueue(new Callback<StatusRoot>() {
                    @Override
                    public void onResponse(Call<StatusRoot> call, Response<StatusRoot> response) {
                        if (response.isSuccessful()) {
                            if (response.body().isSuccessful()) {
                                Log.d("Aboud", "onResponse setDoneserviesWithFiels2 response api succes: " + response.body().getMessage());
                                oberverTheError.errormessage(false, null);
                            } else {
                                Log.d("Aboud", "onResponse setDoneserviesWithFiels2 response api Failed: " + response.body().getMessage());
                                oberverTheError.errormessage(true, "response api Failed " + response.body().getMessage());

                            }
                        } else {
                            Log.d("Aboud", "onResponse setDoneserviesWithFiels2 response link Failed: " + response.message());
                            oberverTheError.errormessage(true, "response link Failed " + response.message());

                        }

                    }

                    @Override
                    public void onFailure(Call<StatusRoot> call, Throwable t) {
                        oberverTheError.errormessage(true, "onFailure " + t.getMessage());
                        Log.d("Aboud", "onFailure setDoneserviesWithFiels2: " + t.getMessage());
                    }
                });
    }

    public void setDoneserviesWithFielsFrom7(FormData form, ArrayList<File> service_activity_result_attachments_owner,
                                             ArrayList<File> service_activity_result_attachments_amenities_receipt,     ArrayList<File> attachments,
              OberverTheError oberverTheError) {
        resportry.setDoneserviesWithFiels(form, null, null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        service_activity_result_attachments_owner,
                        service_activity_result_attachments_amenities_receipt,attachments)
                .enqueue(new Callback<StatusRoot>() {
                    @Override
                    public void onResponse(Call<StatusRoot> call, Response<StatusRoot> response) {
                        if (response.isSuccessful()) {
                            if (response.body().isSuccessful()) {
                                Log.d("Aboud", "onResponse setDoneserviesWithFiels2 response api Failed: " + response.body().getMessage());
                                oberverTheError.errormessage(false, null);
                            } else {
                                Log.d("Aboud", "onResponse setDoneserviesWithFiels2 response api Failed: " + response.body().getMessage());
                                oberverTheError.errormessage(true, "response api Failed " + response.body().getMessage());

                            }
                        } else {
                            Log.d("Aboud", "onResponse setDoneserviesWithFiels2 response link Failed: " + response.message());
                            oberverTheError.errormessage(true, "response link Failed " + response.message());

                        }

                    }

                    @Override
                    public void onFailure(Call<StatusRoot> call, Throwable t) {
                        oberverTheError.errormessage(true, "onFailure " + t.getMessage());
                        Log.d("Aboud", "onFailure setDoneserviesWithFiels2: " + t.getMessage());
                    }
                });
    }

}
