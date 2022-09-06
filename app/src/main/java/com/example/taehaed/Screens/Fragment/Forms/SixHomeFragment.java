package com.example.taehaed.Screens.Fragment.Forms;

import static com.example.taehaed.Constans.CheckInputfield;
import static com.example.taehaed.Constans.DESCRIBABLE_KEY;
import static com.example.taehaed.Constans.VadlditoForIdNumber;
import static com.example.taehaed.Constans.checkLocation;
import static com.example.taehaed.Constans.donlowdTheFile;
import static com.example.taehaed.Constans.getLoaction;
import static com.example.taehaed.Constans.getPermationForCamre;
import static com.example.taehaed.Constans.getPermationForFiles;
import static com.example.taehaed.Constans.getPermationForLocation;
import static com.example.taehaed.Constans.getValue;
import static com.example.taehaed.Constans.getValueOfboleaan;
import static com.example.taehaed.Constans.itsNotNull;
import static com.example.taehaed.Constans.setAdpater;
import static com.example.taehaed.Constans.setAlertMeaage;
import static com.example.taehaed.Constans.setErrorTextView;
import static com.example.taehaed.Constans.setPhoneNumberValdtion;
import static com.example.taehaed.Constans.setRes;


import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.taehaed.Adapters.ImageFileApabter;
import com.example.taehaed.Constans;
import com.example.taehaed.FileUtil;
import com.example.taehaed.Model.TaehaedVModel;
import com.example.taehaed.Pojo.FormReuest.FormData;
import com.example.taehaed.Pojo.ImageFileData;
import com.example.taehaed.Pojo.NoteBodey;
import com.example.taehaed.R;
import com.example.taehaed.Screens.CameraActivity;
import com.example.taehaed.Screens.Fragment.ImageTakeIt;
import com.example.taehaed.databinding.FragmentSixHomeBinding;

import java.io.File;
import java.util.ArrayList;


public class SixHomeFragment extends Fragment implements ImageTakeIt {


    private FragmentSixHomeBinding binding;
    private FormData formdata;
    private AlertDialog alertDialog;
    private TaehaedVModel taehaedVModel;
    private int servier_id,DoneStatus;

    private int AttachmentNumber;

    private ArrayList<ImageFileData> imageFileData, imageFileData2, imageFileData3,imageFileData4,imageFileData5;
    private ArrayList<ImageFileApabter> adApabters = new ArrayList<>();
    private ArrayList<File> home_activity_result_attachments_owner,
            home_activity_result_attachments_amenities_receipt,
            home_activity_result_attachments_supplier_invoices,
            home_activity_result_attachments_sales_statements,attachments;
    private File aboudfile;
    ActivityResultLauncher<String> activityResultLauncher;

    public SixHomeFragment() {

    }

    public static SixHomeFragment newInstance(int id) {
        SixHomeFragment fragment = new SixHomeFragment();
        Bundle args = new Bundle();
        args.putInt(Constans.IdkeyFrgment, id);
        fragment.setArguments(args);
        return fragment;
    }

    public static SixHomeFragment newInstance(int id, FormData formData,int DoneStatus) {
        SixHomeFragment fragment =  new SixHomeFragment();
        Bundle args = new Bundle();
        args.putInt(Constans.IdkeyFrgment, id);
        args.putInt(Constans.DoneStatus,DoneStatus);
        args.putSerializable(DESCRIBABLE_KEY, formData);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            servier_id = getArguments().getInt(Constans.IdkeyFrgment);
            formdata = (FormData) getArguments().getSerializable(DESCRIBABLE_KEY);
            DoneStatus = getArguments().getInt(Constans.DoneStatus);
        }

        for(int i=0 ; i<5;i++)
        {
            adApabters.add(new ImageFileApabter());
        }
        home_activity_result_attachments_owner = new ArrayList<>();
        home_activity_result_attachments_amenities_receipt= new ArrayList<>();
        home_activity_result_attachments_supplier_invoices= new ArrayList<>();
        home_activity_result_attachments_sales_statements= new ArrayList<>();

        attachments= new ArrayList<>();

        imageFileData = new ArrayList<>();
        imageFileData2 = new ArrayList<>();
        imageFileData3 = new ArrayList<>();
        imageFileData4 = new ArrayList<>();
        imageFileData5 = new ArrayList<>();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSixHomeBinding.inflate(inflater);
        taehaedVModel = new ViewModelProvider(this).get(TaehaedVModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        Constans.setDateForInputText(binding.PerathData,getContext());

        if(SetDataUI()){
         //   Constans.enableDisableViewGroup(binding.TopBoss,false);
            SetVispaltyAndEnadle();
        }else{
            setNewFormData();
        }

        //ظبط الملفات لرفع
        setTheUpload();

        //ظبط المرفقات
        AttachmentSet();
        setRcylerviews();



        binding.Location.setOnClickListener(view1 -> {
            setLoavtion();
        });
        binding.Sumbit.setOnClickListener(view1 -> {

            if(getDataUI()) return;
            alertDialog= setAlertMeaage(getString(R.string.getthedata),getContext());
            alertDialog.show();
            if(DoneStatus==1)
            {
                NoteBodey noteBodey = new NoteBodey();
                noteBodey.setRequest_service_id(servier_id);
                noteBodey.setReport("تم تعديل الاستعلام");
                taehaedVModel.ConvertDoneToAccept(noteBodey, (status,errorM) -> {
                    if(status)
                    {
                        setDone();

                    }else{
                        Toast.makeText(getContext(), getString(R.string.deletProblen)+" \n "+errorM, Toast.LENGTH_SHORT).show();
                        alertDialog.dismiss();
                    }
                });
            }else{

                setDone();
            }


        });

    }



    private void setNewFormData() {
        if(DoneStatus==0)
        {
            formdata = new FormData();
        }
    }

    private void setLoavtion() {

        if(checkLocation(getActivity(),getContext()))
        {
            if (!getPermationForLocation(getContext(),getActivity())) {

                Toast.makeText(getContext(),getString( R.string.hitagian), Toast.LENGTH_SHORT).show();
                binding.Sumbit.setVisibility(View.GONE);
            }
            else {
                alertDialog= setAlertMeaage("جاري تحديد المكان",getContext());
                alertDialog.show();
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        // write your code here
                        Location location  =getLoaction(getActivity(),getContext());
                        getActivity().runOnUiThread(() -> {
                            if(location==null)
                            {    Toast.makeText(getContext(), R.string.errorsd, Toast.LENGTH_SHORT).show();
                                alertDialog.dismiss();
                            }else{
                                formdata.longitude=location.getLongitude();
                                formdata.latitude=location.getLatitude();

                                binding.Sumbit.setVisibility(View.VISIBLE);

                                Toast.makeText(getContext(), getString(R.string.Loaction), Toast.LENGTH_SHORT).show();
                                alertDialog.dismiss();
                            }
                            // todo: update your ui / view in activity
                        });

                    }
                });

            }
        }
    }

    private void setDone() {
        taehaedVModel.setDoneserviesWithFielsFrom6(formdata, home_activity_result_attachments_owner,
                home_activity_result_attachments_amenities_receipt,
                home_activity_result_attachments_supplier_invoices,
                home_activity_result_attachments_sales_statements,attachments
               , (status, Message) -> {
                    if (status) {
                        alertDialog.dismiss();
                        Toast.makeText(getContext(), "يبدو ان هناك خطأ ما" + Message, Toast.LENGTH_SHORT).show();
                    } else {
                        alertDialog.dismiss();
                        Toast.makeText(getContext(), "تم", Toast.LENGTH_SHORT).show();
                        getActivity().onBackPressed();
                    }

                });
    }

    private void AttachmentSet() {
        // التشيوز بوكس بتاع المفرقات
        binding.radioFielsCH.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                binding.attacmentKingeShoer.setVisibility(View.VISIBLE);

            } else {
                binding.attacmentKingeShoer.setVisibility(View.GONE);

            }
        });
        binding.radioFielsCH2.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                binding.attacmentDoucmentShoer.setVisibility(View.VISIBLE);
            } else {
                binding.attacmentDoucmentShoer.setVisibility(View.GONE);
            }
        });
        binding.radioFielsCH3.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                binding.attacmentBuyerShoer.setVisibility(View.VISIBLE);
            } else {
                binding.attacmentBuyerShoer.setVisibility(View.GONE);
            }
        });
        binding.radioFielsCH4.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                binding.attacmentSellerShoer.setVisibility(View.VISIBLE);
            } else {
                binding.attacmentSellerShoer.setVisibility(View.GONE);
            }
        });
        binding.radioFielsCH5.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                binding.attacmentOtherShoer.setVisibility(View.VISIBLE);
            } else {
                binding.attacmentOtherShoer.setVisibility(View.GONE);
            }
        });

        //هنا التيسكت فيلد بيظهر فيه اسامي اول ملف بيترفع وبيتم اختيار الملفات عن طريقه برضو
        binding.fobutton.setOnClickListener(view1 -> {

            AttachmentNumber = 1;
            selectUploadType();

        });
        binding.fobutton2.setOnClickListener(view1 -> {
            AttachmentNumber = 2;
            selectUploadType();

        });
        binding.fobutton3.setOnClickListener(view1 -> {
            AttachmentNumber = 3;
            selectUploadType();

        });
        binding.fobutton4.setOnClickListener(view1 -> {
            AttachmentNumber = 4;
            selectUploadType();

        });
        binding.fobutton5.setOnClickListener(view1 -> {
            AttachmentNumber = 5;
            selectUploadType();

        });

        //هنا زار اظهار وتحميل الملفات

        binding.ArrgemtnShow.setOnClickListener(view1 -> {
            if (getPermationForFiles(getContext(), getActivity())) {
                donlowdTheFile(formdata.home_activity_result_attachments_owner,getActivity(),getContext());
                donlowdTheFile(formdata.home_activity_result_attachments_amenities_receipt,getActivity(),getContext());
                donlowdTheFile(formdata.home_activity_result_attachments_supplier_invoices,getActivity(),getContext());
                donlowdTheFile(formdata.home_activity_result_attachments_sales_statements,getActivity(),getContext());
                donlowdTheFile(formdata.attachments,getActivity(),getContext());
            }
        });

    }
    private void setRcylerviews() {
        setRes(binding.RescView, adApabters.get(0),getContext());
        setRes(binding.RescView2,  adApabters.get(1),getContext());
        setRes(binding.RescView3,  adApabters.get(2),getContext());
        setRes(binding.RescView4,  adApabters.get(3),getContext());
        setRes(binding.RescView7,  adApabters.get(4),getContext());

    }
    private void SelectFiles() {
        activityResultLauncher.launch("*/*");
    }

    private void SelectImage() {
        activityResultLauncher.launch("image/*");
    }

    private void setTheUpload() {
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.GetMultipleContents()
                , result -> {

                    if (result != null) {

                        for (int i = 0; i < result.size(); i++) {
                            try {
                                //   String extanton=getFileExtension(result.get(i),getContext());
                                aboudfile = new File(FileUtil.getPath(result.get(i), getContext()));
                            } catch (Exception ex) {
                                Log.d("Aboud", "on" +
                                        "ViewCreated: " + result.get(i) + " " + ex.getMessage());
                                Toast.makeText(getContext(), ex.getMessage() + "  " + result.get(i).getPath() + " " + getString(R.string.errorMeshae), Toast.LENGTH_SHORT).show();
                                break;
                            }
                            if (AttachmentNumber == 1) {
                                home_activity_result_attachments_owner.add(aboudfile);
                                setAdpater(result.get(i), aboudfile.getName(), imageFileData, adApabters.get(0));
                            } else if (AttachmentNumber == 2) {
                                home_activity_result_attachments_amenities_receipt.add(aboudfile);
                                setAdpater(result.get(i), aboudfile.getName(), imageFileData2, adApabters.get(1));
                            } else if (AttachmentNumber == 3) {
                                home_activity_result_attachments_supplier_invoices.add(aboudfile);
                                setAdpater(result.get(i), aboudfile.getName(), imageFileData3, adApabters.get(2));
                            } else if (AttachmentNumber == 4) {
                                home_activity_result_attachments_sales_statements.add(aboudfile);
                                setAdpater(result.get(i), aboudfile.getName(), imageFileData4, adApabters.get(3));
                            }else if (AttachmentNumber == 5) {
                                attachments.add(aboudfile);
                                setAdpater(result.get(i), aboudfile.getName(), imageFileData5, adApabters.get(4));
                            }



                        }


                    }
                });
    }



    private void SetVispaltyAndEnadle() {



        binding.ArrgemtnShow.setVisibility(View.VISIBLE);

    }

    private boolean getDataUI() {
        formdata.request_service_id = servier_id;

        setErrorNullForTextView();

        //بيانات العميل
        if(getAgentDataValdion()) return true;

        //بيانات النشاط
        if(getActvityDataValdtion()) return true;

        //بيانات المبيعات / المشتريات
        if(getSelasBuyData())return true;

        //نتيجة الاستعلام
        if(getAskingData())return true;
        return false;
    }

    private void setErrorNullForTextView() {
        binding.DoorTrue.setError(null);
        binding.ForntRoomTrue.setError(null);
        binding.FoundlePropureTrue.setError(null);
        binding.TheMangementTrue.setError(null);
        binding.PaymentTrue.setError(null);
        binding.PaymentHowTrue.setError(null);
        binding.AgentCoomentiTrue.setError(null);
        binding.SuplluerTypeTrue.setError(null);
        binding.SuplluerPaymentTrue.setError(null);
        binding.OwnerDataType.setError(null);
        binding.PlaceAddersTrue.setError(null);
        binding.MangemntPLaceTrue.setError(null);
        binding.RepatationTrue.setError(null);
        binding.SorursTrue.setError(null);
    }

    private boolean getAskingData() {
        if( binding.radioLieesnasCH.isChecked())
        {
            formdata.home_activity_result_owner=0;
        }
        else if (binding.radioLieesnasCH2.isChecked())
        {
            formdata.home_activity_result_owner=1;
        }
        else{
            setErrorTextView(binding.OwnerDataType,getContext());
            return true;
        }

        if( binding.radioMachineLiesnseCH.isChecked())
        {
            formdata.home_activity_result_headquarters=0;
        }
        else if( binding.radioMachineLiesnseCH2.isChecked())
        {
            formdata.home_activity_result_headquarters=1;
        }
        else{
            setErrorTextView(binding.PlaceAddersTrue,getContext());
            return true;
        }
        if( binding.radioOwnerCH.isChecked())
        {
            formdata.home_activity_result_manager=0;
        }
        else if( binding.radioOwnerCH2.isChecked())
        {
            formdata.home_activity_result_manager=1;
        }else{
            setErrorTextView(binding.MangemntPLaceTrue,getContext());
            return true;
        }

        if( binding.radioValueCH.isChecked())
        {
            formdata.home_activity_result_customer_heard=1;
        }
        else if( binding.radioValueCH2.isChecked())
        {
            formdata.home_activity_result_customer_heard=2;
        }
        else if( binding.radioValueCH3.isChecked())
        {
            formdata.home_activity_result_customer_heard=3;
        }
        else{
            setErrorTextView(binding.RepatationTrue,getContext());
            return true;
        }

        if( binding.radioSoueCH.isChecked())
        {
            formdata.home_activity_result_sources=1;
        }
        else if( binding.radioSouCH2.isChecked())
        {
            formdata.home_activity_result_sources=2;
        }
        else if( binding.radioSouCH3.isChecked())
        {
            formdata.home_activity_result_sources=3;
        }
        else if( binding.radioSouCH4.isChecked())
        {
            formdata.home_activity_result_sources=4;
        }
        else{
            setErrorTextView(binding.SorursTrue,getContext());
            return true;
        }

        return false;

    }

    private boolean getSelasBuyData() {
        if( binding.radioReviredCH.isChecked())
        {
            formdata.sales_data_manager=1;
        }
        else if( binding.radioReviredCH2.isChecked())
        {
            formdata.sales_data_manager=2;
        }

        else if (binding.radioReviredCH3.isChecked())
        {
            formdata.sales_data_manager=3;
        }
        else{
            setErrorTextView(binding.TheMangementTrue,getContext());
            return true;
        }

        if (binding.radioSellCH.isChecked()) {
            formdata.sales_data_how_sell = 1;
        }
        else if (binding.radiSellCH2.isChecked()) {
            formdata.sales_data_how_sell = 2;
        }
        else{
            setErrorTextView(binding.PaymentTrue,getContext());
            return true;
        }

        if(binding.radioBayCH.isChecked())
        {
            formdata.sales_data_customers_payment_method=1;
        }
        else  if(binding.radioBayCH2.isChecked())
        {
            formdata.sales_data_customers_payment_method=2;
        }
        else if(binding.radioBayCH3.isChecked())
        {
            formdata.sales_data_customers_payment_method=3;
        }
        else{
            setErrorTextView(binding.PaymentHowTrue,getContext());
            return true;
        }

        if(binding.radioAgentsCH.isChecked())
        {
            formdata.sales_data_customers_pay=1;
        }
        else if(binding.radioAgentsCH2.isChecked())
        {
            formdata.sales_data_customers_pay=2;
        }
        else if(binding.radioAgentsCH3.isChecked())
        {
            formdata.sales_data_customers_pay=3;
        }
        else{
            setErrorTextView(binding.AgentCoomentiTrue,getContext());
            return true;
        }

        if( binding.radioSuppliersTypeCH.isChecked())
        {
            formdata.sales_data_suppliers_type=1;
        }
        else if( binding.radioSuppliersTypeCH2.isChecked())
        {
            formdata.sales_data_suppliers_type=2;
        }
        else if (binding.radioSuppliersTypeCH3.isChecked())
        {
            formdata.sales_data_suppliers_type=2;
        }
        else{
            setErrorTextView(binding.SuplluerTypeTrue,getContext());
            return true;
        }
        if( binding.radioSuppliersBaymentCH.isChecked())
        {
            formdata.sales_data_suppliers_payment_method=1;
        }
        else if( binding.radioSuppliersBaymentCH2.isChecked())
        {
            formdata.sales_data_suppliers_payment_method=2;
        }
        else if( binding.radioSuppliersBaymentCH3.isChecked())
        {
            formdata.sales_data_suppliers_payment_method=3;
        }
        else{
            setErrorTextView(binding.SuplluerPaymentTrue,getContext());
            return true;
        }
        return false;
    }

    private boolean getActvityDataValdtion()
    {
        if (CheckInputfield(binding.ComarialType, getContext())) return true;
        if (CheckInputfield(binding.ActivitySpace, getContext())) return true;
        if (CheckInputfield(binding.ActivityFace, getContext())) return true;
        //لها باب منفصل
        if (binding.radiooDoorCH.isChecked()) {
            formdata.home_activity_separate_door = 0;
        } else if (binding.radiooDoorCH2.isChecked()) {
            formdata.home_activity_separate_door = 1;
        }else{
            setErrorTextView(binding.DoorTrue,getContext());
            return true;
        }

        //واجهة غرفة النشاط
        if (binding.radiooActivityDoorCH.isChecked()) {
            formdata.home_activity_front_room_2 = 1;
        }
        else if (binding.radiooActivityDoorCH2.isChecked()) {
            formdata.home_activity_front_room_2 = 2;
        }
        else if (binding.radiooActivityDoorCH3.isChecked()) {
            formdata.home_activity_front_room_2 = 3;
        }
        else{
            setErrorTextView(binding.ForntRoomTrue,getContext());
            return true;
        }

        //الغرض من التمويل
        if (binding.radioInvestmenttCH.isChecked()) {
            formdata.home_activity_purpose_financing = 1;
        }
        else if (binding.radioInvestmenttCH2.isChecked()) {
            formdata.home_activity_purpose_financing = 2;
        }
        else if (binding.radioInvestmentCH3.isChecked()) {
            formdata.home_activity_purpose_financing = 3;
        }
        else{
            setErrorTextView(binding.FoundlePropureTrue,getContext());
            return true;
        }

        if (CheckInputfield(binding.MonreySupported, getContext())) return true;
        getActvityData();
        return false;
    }

    private void getActvityData() {
        formdata.home_activity_type = getValue(binding.ComarialType.getText());
        formdata.home_activity_area = getValue(binding.ActivitySpace.getText());
        formdata.home_activity_front_room_1 = getValue(binding.ActivityFace.getText());

        formdata.home_activity_capital = getValue(binding.MonreySupported.getText());

    }

    private boolean getAgentDataValdion() {
        // بيانات العميل
        if (CheckInputfield(binding.FullName, getContext())) return true;
        if (VadlditoForIdNumber(binding.NainolIdNumber, getContext())) return true;
        if (CheckInputfield(binding.PerathData, getContext())) return true;
        if (setPhoneNumberValdtion(binding.PhonNumber, getContext())) return true;

        getAgentData();
        return false;
    }


    private void getAgentData() {
        formdata.client_name = getValue(binding.FullName.getText());
        formdata.client_nickname = getValue(binding.NakeName.getText());
        formdata.client_national_id = getValue(binding.NainolIdNumber.getText());
        formdata.client_birth_date = getValue(binding.PerathData.getText());
        formdata.client_mobile_number_1 = getValue(binding.PhonNumber.getText());
        formdata.client_mobile_number_2 = getValue(binding.PhonNumber2.getText());
        formdata.client_phone = getValue(binding.PhonNumber3.getText());
    }

    private boolean SetDataUI() {
        if (itsNotNull(formdata)) {
            binding.Sumbit.setVisibility(View.VISIBLE);


            //بيانات العميل
            SetAgentData();

            //بيانات النشاط
            SetActvityData();

            //بيانات المبيعات / المشتريات
            SetSelasBuyData();

            //نتيجة الاستعلام
            SetAskingData();
return true;
        }
        else{
            return false;
        }
    }

    private void SetAskingData() {

        if (itsNotNull(formdata.home_activity_result_owner)) {
            if (getValueOfboleaan(formdata.home_activity_result_owner) == 0) {
                binding.radioLieesnasCH.setChecked(true);
            } else if (getValueOfboleaan(formdata.home_activity_result_owner) == 1) {
                binding.radioLieesnasCH2.setChecked(true);
            }
        }
        if (itsNotNull(formdata.home_activity_result_headquarters)) {
            if (getValueOfboleaan(formdata.home_activity_result_headquarters) == 0) {
                binding.radioMachineLiesnseCH.setChecked(true);
            } else if (getValueOfboleaan(formdata.home_activity_result_headquarters) == 1) {
                binding.radioMachineLiesnseCH2.setChecked(true);
            }
        }
        if (itsNotNull(formdata.home_activity_result_manager)) {
            if (getValueOfboleaan(formdata.home_activity_result_manager) == 0) {
                binding.radioOwnerCH.setChecked(true);
            } else if (getValueOfboleaan(formdata.home_activity_result_manager) == 1) {
                binding.radioOwnerCH2.setChecked(true);
            }
        }
        if (itsNotNull(formdata.home_activity_result_customer_heard)) {
            if (getValueOfboleaan(formdata.home_activity_result_customer_heard) == 1) {
                binding.radioValueCH.setChecked(true);
            } else if (getValueOfboleaan(formdata.home_activity_result_customer_heard) == 2) {
                binding.radioValueCH2.setChecked(true);
            } else if (getValueOfboleaan(formdata.home_activity_result_customer_heard) == 3) {
                binding.radioValueCH3.setChecked(true);
            }
        }
        if (itsNotNull(formdata.home_activity_result_sources)) {
            if (getValueOfboleaan(formdata.home_activity_result_sources) == 1) {
                binding.radioSoueCH.setChecked(true);
            } else if (getValueOfboleaan(formdata.home_activity_result_sources) == 2) {
                binding.radioSouCH2.setChecked(true);
            } else if (getValueOfboleaan(formdata.home_activity_result_sources) == 3) {
                binding.radioSouCH3.setChecked(true);
            } else if (getValueOfboleaan(formdata.home_activity_result_sources) == 4) {
                binding.radioSouCH4.setChecked(true);
            }
        }

        //المرفقات
        if (itsNotNull(formdata.home_activity_result_attachments_owner)) {
            for (int i = 0; i < formdata.home_activity_result_attachments_owner.size(); i++) {
                String name = getValue(formdata.home_activity_result_attachments_owner.get(i));

                setAdpater(Uri.parse(name), Uri.parse(name).getLastPathSegment(), imageFileData, adApabters.get(0));

            }
            //
        }
        if (itsNotNull(formdata.home_activity_result_attachments_amenities_receipt)) {
            for (int i = 0; i < formdata. home_activity_result_attachments_amenities_receipt.size(); i++) {
                String name = getValue(formdata. home_activity_result_attachments_amenities_receipt.get(i));

                setAdpater(Uri.parse(name), Uri.parse(name).getLastPathSegment(), imageFileData2, adApabters.get(1));

            }
            //
        }
        if (itsNotNull(formdata.home_activity_result_attachments_supplier_invoices)) {
            for (int i = 0; i < formdata.home_activity_result_attachments_supplier_invoices.size(); i++) {
                String name = getValue(formdata.home_activity_result_attachments_supplier_invoices.get(i));

                setAdpater(Uri.parse(name), Uri.parse(name).getLastPathSegment(), imageFileData3, adApabters.get(2));

            }
            //
        }
        if (itsNotNull(formdata. home_activity_result_attachments_sales_statements)) {
            for (int i = 0; i < formdata.home_activity_result_attachments_sales_statements.size(); i++) {
                String name = getValue(formdata.home_activity_result_attachments_sales_statements.get(i));
                setAdpater(Uri.parse(name), Uri.parse(name).getLastPathSegment(), imageFileData4, adApabters.get(3));
            }
            //
        }

        if (itsNotNull(formdata.attachments)) {
            for (int i = 0; i < formdata.attachments.size(); i++) {
                String name = getValue(formdata.attachments.get(i));

                setAdpater(Uri.parse(name), Uri.parse(name).getLastPathSegment(), imageFileData5, adApabters.get(4));

            }
            //
        }


    }

    private void SetSelasBuyData() {
        if (itsNotNull(formdata.sales_data_manager)) {
            if (getValueOfboleaan(formdata.sales_data_manager) == 1) {
                binding.radioReviredCH.setChecked(true);
            } else if (getValueOfboleaan(formdata.sales_data_manager) == 2) {
                binding.radioReviredCH2.setChecked(true);
            } else if (getValueOfboleaan(formdata.sales_data_manager) == 3) {
                binding.radioReviredCH3.setChecked(true);
            }
        }
        if (itsNotNull(formdata.sales_data_how_sell)) {
            if (getValueOfboleaan(formdata.sales_data_how_sell) == 1) {
                binding.radioSellCH.setChecked(true);
            } else if (getValueOfboleaan(formdata.sales_data_how_sell) == 2) {
                binding.radiSellCH2.setChecked(true);
            }

        }
        if (itsNotNull(formdata.sales_data_customers_payment_method)) {
            if (getValueOfboleaan(formdata.sales_data_customers_payment_method) == 1) {
                binding.radioBayCH.setChecked(true);
            } else if (getValueOfboleaan(formdata.sales_data_customers_payment_method) == 2) {
                binding.radioBayCH2.setChecked(true);
            } else if (getValueOfboleaan(formdata.sales_data_customers_payment_method) == 3) {
                binding.radioBayCH3.setChecked(true);
            }

        }
        if (itsNotNull(formdata.sales_data_customers_pay)) {
            if (getValueOfboleaan(formdata.sales_data_customers_pay) == 1) {
                binding.radioAgentsCH.setChecked(true);
            } else if (getValueOfboleaan(formdata.sales_data_customers_pay) == 2) {
                binding.radioAgentsCH2.setChecked(true);
            } else if (getValueOfboleaan(formdata.sales_data_customers_pay) == 3) {
                binding.radioAgentsCH3.setChecked(true);
            }

        }
        if (itsNotNull(formdata.sales_data_suppliers_type)) {
            if (getValueOfboleaan(formdata.sales_data_suppliers_type) == 1) {
                binding.radioSuppliersTypeCH.setChecked(true);
            } else if (getValueOfboleaan(formdata.sales_data_suppliers_type) == 2) {
                binding.radioSuppliersTypeCH2.setChecked(true);
            } else if (getValueOfboleaan(formdata.sales_data_suppliers_type) == 3) {
                binding.radioSuppliersTypeCH3.setChecked(true);
            } else if (getValueOfboleaan(formdata.sales_data_suppliers_type) == 4) {
                binding.radioSuppliersTypeCH4.setChecked(true);
            }


        }
        if (itsNotNull(formdata.sales_data_suppliers_payment_method)) {
            if (getValueOfboleaan(formdata.sales_data_suppliers_payment_method) == 1) {
                binding.radioSuppliersBaymentCH.setChecked(true);
            } else if (getValueOfboleaan(formdata.sales_data_suppliers_payment_method) == 2) {
                binding.radioSuppliersBaymentCH2.setChecked(true);
            } else if (getValueOfboleaan(formdata.sales_data_suppliers_payment_method) == 3) {
                binding.radioSuppliersBaymentCH3.setChecked(true);
            }

        }
    }

    private void SetActvityData() {
        binding.ComarialType.setText(getValue(formdata.home_activity_type));
        binding.ActivitySpace.setText(getValue(formdata.home_activity_area));
        binding.ActivityFace.setText(getValue(formdata.home_activity_front_room_1));
        //لها باب منفصل
        if (itsNotNull(formdata.home_activity_separate_door)) {
            if (getValueOfboleaan(formdata.home_activity_separate_door) == 0) {
                binding.radiooDoorCH.setChecked(true);
            } else if (getValueOfboleaan(formdata.home_activity_separate_door) == 1) {
                binding.radiooDoorCH2.setChecked(true);
            }
        }
        //واجهة غرفة النشاط
        if (itsNotNull(formdata.home_activity_front_room_2)) {
            if (getValueOfboleaan(formdata.home_activity_front_room_2) == 1) {
                binding.radiooActivityDoorCH.setChecked(true);
            } else if (getValueOfboleaan(formdata.home_activity_front_room_2) == 2) {
                binding.radiooActivityDoorCH2.setChecked(true);
            } else if (getValueOfboleaan(formdata.home_activity_front_room_2) == 3) {
                binding.radiooActivityDoorCH3.setChecked(true);
            }
        }
        //الغرض من التمويل
        if (itsNotNull(formdata.home_activity_purpose_financing)) {
            if (getValueOfboleaan(formdata.home_activity_purpose_financing) == 1) {
                binding.radioInvestmenttCH.setChecked(true);
            } else if (getValueOfboleaan(formdata.home_activity_purpose_financing) == 2) {
                binding.radioInvestmenttCH2.setChecked(true);
            } else if (getValueOfboleaan(formdata.home_activity_purpose_financing) == 3) {
                binding.radioInvestmentCH3.setChecked(true);
            }
        }
        binding.MonreySupported.setText(getValue(formdata.home_activity_capital));

    }

    private void SetAgentData() {
        binding.FullName.setText(getValue(formdata.client_name));
        binding.NakeName.setText((getValue(formdata.client_nickname)));
        binding.NainolIdNumber.setText(getValue(formdata.client_national_id));
        binding.PerathData.setText(getValue(formdata.client_birth_date));
        binding.PhonNumber.setText(getValue(formdata.client_mobile_number_1));
        binding.PhonNumber2.setText(getValue(formdata.client_mobile_number_2));
        binding.PhonNumber3.setText(getValue(formdata.client_phone));
    }


    private void selectUploadType() {
        final CharSequence[] options = {getString(R.string.Camera), getString(R.string.Show),
                getString(R.string.file), getString(R.string.Cancle)};
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("اضف!");
        builder.setItems(options, (dialog, item) -> {
            if (options[item].equals(getString(R.string.Camera))) {
                if (getPermationForCamre(getContext(), getActivity())) {
                    Intent intent = new Intent(getContext(), CameraActivity.class);
                    CameraActivity.imgaetakeIt = this;
                    getContext().startActivity(intent);
                }
            } else if (options[item].equals(getString(R.string.Show))) {
                SelectImage();
            } else if (options[item].equals(getString(R.string.file))) {
                if (getPermationForFiles(getContext(), getActivity())) {
                    SelectFiles();
                }

            } else if (options[item].equals(getString(R.string.Cancle))) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    @Override
    public void imageDone(Uri uri) {
        aboudfile = new File(FileUtil.getPath(uri, getContext()));
        if (AttachmentNumber == 1) {
            home_activity_result_attachments_owner.add(aboudfile);
            setAdpater(uri, aboudfile.getName(), imageFileData, adApabters.get(0));

        } else if (AttachmentNumber == 2) {
            home_activity_result_attachments_amenities_receipt.add(aboudfile);
            setAdpater(uri, aboudfile.getName(), imageFileData2, adApabters.get(1));

        } else if (AttachmentNumber == 3) {
            home_activity_result_attachments_supplier_invoices.add(aboudfile);
            setAdpater(uri, aboudfile.getName(), imageFileData3, adApabters.get(2));

        } else if (AttachmentNumber == 4) {
            home_activity_result_attachments_sales_statements.add(aboudfile);
            setAdpater(uri, aboudfile.getName(), imageFileData4, adApabters.get(3));

        }
        else if (AttachmentNumber == 5) {
            attachments.add(aboudfile);
            setAdpater(uri, aboudfile.getName(), imageFileData5, adApabters.get(4));

        }
    }
}