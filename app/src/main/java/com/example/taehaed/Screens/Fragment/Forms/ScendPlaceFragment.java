package com.example.taehaed.Screens.Fragment.Forms;

import static com.example.taehaed.Constans.CheckInputfield;
import static com.example.taehaed.Constans.DESCRIBABLE_KEY;
import static com.example.taehaed.Constans.ErrorMessageValdition;
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
import static com.example.taehaed.Constans.setPhoneNumberValdtion;

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
import androidx.recyclerview.widget.LinearLayoutManager;

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
import com.example.taehaed.databinding.FragmentPlaceBinding;

import java.io.File;
import java.util.ArrayList;


public class ScendPlaceFragment extends Fragment implements ImageTakeIt {
    //FormData   دي خاصة بال 10 فورمات اللي معانا ودي اللي هنخزن فيها البياناتا
    private FormData formdata;
    private FragmentPlaceBinding binding;

    private AlertDialog alertDialog;
    private TaehaedVModel taehaedVModel;
    //servier_id دي رقم الخدمة ودي لازم اكون عرف عشان ببعت الفورم علي الرقم ده
    // DoneStatus دي بعرف منها الفورم كان فيها بيانات ولا لا
    private int servier_id, DoneStatus;
    //دي بعرف منها انهي فايل هو اختار من الشيك بوكس
    boolean result_attached_utilities_receiptSelected = false;
    boolean result_attached_husband_national_idSelected = false;
    boolean attachments_idSelected = false;

    ImageFileApabter adapter = new ImageFileApabter(), adapter2 = new ImageFileApabter(), adapter3 = new ImageFileApabter();
    private ArrayList<ImageFileData> imageFileData1, imageFileData2, imageFileData3;
    // مش هعرف استخدام المتغيرات اللي بنفس الاسام في Formdata لان نوعهم ابوجيكت فكان الحل اني افصلهم
    ArrayList<File> result_attached_utilities_receipt, result_attached_husband_national_id, attachments;
    File aboudfile;
    ActivityResultLauncher<String> activityResultLauncher;

    public ScendPlaceFragment() {

    }


    public static ScendPlaceFragment getInstance(int id) {
        ScendPlaceFragment fragment = new ScendPlaceFragment();
        Bundle args = new Bundle();
        args.putInt(Constans.IdkeyFrgment, id);
        fragment.setArguments(args);
        return fragment;
    }

    public static ScendPlaceFragment getInstance(int id, FormData formData, int DoneStatus) {
        ScendPlaceFragment fragment = new ScendPlaceFragment();
        Bundle args = new Bundle();
        args.putInt(Constans.IdkeyFrgment, id);
        args.putInt(Constans.DoneStatus, DoneStatus);
        args.putSerializable(DESCRIBABLE_KEY, formData);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            servier_id = getArguments().getInt(Constans.IdkeyFrgment);
            DoneStatus = getArguments().getInt(Constans.DoneStatus);
            formdata = (FormData) getArguments().getSerializable(DESCRIBABLE_KEY);
        }
        result_attached_husband_national_id = new ArrayList<>();
        result_attached_utilities_receipt = new ArrayList<>();
        attachments = new ArrayList<>();
        imageFileData1 = new ArrayList<>();
        imageFileData2 = new ArrayList<>();
        imageFileData3 = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPlaceBinding.inflate(getLayoutInflater());
        taehaedVModel = new ViewModelProvider(this).get(TaehaedVModel.class);


        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        SetDate();
        //Here We set the Data if From data is send it
        if (SetDataUi()) {
            //هنا بنتاكد الاول هل هي كانت فيه دتا او لا
            //لو فيه يبقا مش هيعمل حاجة
            //لو مش فيه بيعمل اوبجيكت جديد
            setNewFormData();

        }  //    Constans.enableDisableViewGroup(binding.TopBoss, false);
        // binding.DisplaImagefor.setEnabled(true);
        //binding.DisplayPDf.setEnabled(true);


        setTheUpload();

        setTheAttachemnt();
        binding.Location.setOnClickListener(view1 -> setLoavtion());

        binding.RescView.setAdapter(adapter);
        binding.RescView.setLayoutManager(new LinearLayoutManager(binding.getRoot().getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.RescView2.setAdapter(adapter2);
        binding.RescView2.setLayoutManager(new LinearLayoutManager(binding.getRoot().getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.RescView3.setAdapter(adapter3);
        binding.RescView3.setLayoutManager(new LinearLayoutManager(binding.getRoot().getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.Sumbit.setOnClickListener(view1 -> {

            if (getFromUiData())  return;
            alertDialog = setAlertMeaage(getString(R.string.current), getContext());
            alertDialog.show();
            if (DoneStatus == 1) {
                NoteBodey noteBodey = new NoteBodey();
                noteBodey.setRequest_service_id(servier_id);
                noteBodey.setReport("تم تعديل الاستعلام");
                taehaedVModel.ConvertDoneToAccept(noteBodey, (status, ErrorMessage) -> {
                    if (status) {
                        DoneStatus = 0;
                        setDone();
                    } else {
                        Toast.makeText(getContext(), getString(R.string.deletProblen) + " \n " + ErrorMessage, Toast.LENGTH_SHORT).show();
                        alertDialog.dismiss();
                    }
                });
            } else {
                setDone();
            }


        });
        setrRadoisGroubs();

    }

    private void setNewFormData() {
        if (DoneStatus == 0) {
            formdata = new FormData();
        }
    }

    private void setDone() {
        taehaedVModel.setDoneserviesWithFielsFrom2(formdata, result_attached_utilities_receipt, result_attached_husband_national_id, attachments, (status, Message) -> {
            if (status) {
                alertDialog.dismiss();
                Toast.makeText(getContext(), "يبدو ان هناك خطأ ما" + " \n " + Message, Toast.LENGTH_SHORT).show();
            } else {
                alertDialog.dismiss();
                Toast.makeText(getContext(), "تم", Toast.LENGTH_SHORT).show();
                getActivity().onBackPressed();
            }
        });
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
                new Handler(Looper.getMainLooper()).post(() -> {
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

                });

            }
        }
    }

    private void setTheAttachemnt() {


        binding.radioFileCH.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                binding.attacmentRecitaeShoer.setVisibility(View.VISIBLE);
            } else {
                binding.attacmentRecitaeShoer.setVisibility(View.GONE);
            }
        });
        binding.radioFileCH2.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                binding.attacmentImageShoer.setVisibility(View.VISIBLE);
            } else {
                binding.attacmentImageShoer.setVisibility(View.GONE);
            }
        });
        binding.other.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                binding.attacmentOtherShoer.setVisibility(View.VISIBLE);
            } else {
                binding.attacmentOtherShoer.setVisibility(View.GONE);
            }
        });
        binding.fobutton.setOnClickListener(view1 -> {
            attachments_idSelected = false;
            result_attached_husband_national_idSelected = true;
            result_attached_utilities_receiptSelected = false;
            selectUploadType();


        });
        binding.fobutton2.setOnClickListener(view1 -> {
            result_attached_utilities_receiptSelected = true;
            result_attached_husband_national_idSelected = false;
            attachments_idSelected = false;
            selectUploadType();
        });
        binding.fobutton3.setOnClickListener(view1 -> {
            attachments_idSelected = true;
            result_attached_utilities_receiptSelected = false;
            result_attached_husband_national_idSelected = false;
            selectUploadType();
        });

        binding.DisplayPDf.setOnClickListener(view1 -> {
            if (getPermationForFiles(getContext(), getActivity())) {
                donlowdTheFile(formdata.result_attached_utilities_receipt,getActivity(),getContext());
                donlowdTheFile(formdata.result_attached_husband_national_id,getActivity(),getContext());
                donlowdTheFile(formdata.attachments,getActivity(),getContext());
            }
        });
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
                                Toast.makeText(getContext(), ex.getMessage() + "  " + result.get(i).getPath() + " " + "عفوا يبدو ان هناك مشكلة في ملف الذي تم اختيار باسم", Toast.LENGTH_SHORT).show();
                                break;
                            }

                            if (result_attached_utilities_receiptSelected) {
                                result_attached_utilities_receipt.add(aboudfile);
                                setAdpater(result.get(i), aboudfile.getName(), imageFileData2, adapter2);
                            }
                            if (result_attached_husband_national_idSelected) {
                                result_attached_husband_national_id.add(aboudfile);
                                setAdpater(result.get(i), aboudfile.getName(), imageFileData1, adapter);
                            }

                            if (attachments_idSelected) {
                                attachments.add(aboudfile);
                                setAdpater(result.get(i), aboudfile.getName(), imageFileData3, adapter3);
                            }
                        }


                    }
                });
    }


    private boolean SetDataUi() {
        if (formdata != null) {
            binding.Sumbit.setVisibility(View.VISIBLE);

            binding.DisplayPDf.setVisibility(View.VISIBLE);

            //بيانات العميل
            setAgentData();
            //بيانات محل الإقامة
            SetReadinesInfo();
            //بيانات ايصال المرافق
            SetRacietAttach();
            //بيانات الزوج او الزوجة
            SetWHData();
            // الاستعلامات
            SetAskingInfoData();

            return false;

        } else {
            return true;
        }

    }


    private void SetAskingInfoData() {
        if (itsNotNull(formdata.result_personal_data)) {
            if (getValueOfboleaan(formdata.result_personal_data) == 0) {
                binding.radioDataCoustmerCH.setChecked(true);

            } else if (getValueOfboleaan(formdata.result_personal_data) == 1) {

                binding.radioDataCoustmerCH2.setChecked(true);
            }
        }
        if (itsNotNull(formdata.result_residence_data)) {
            if (getValueOfboleaan(formdata.result_residence_data) == 0) {
                binding.radioStayingDataCH.setChecked(true);

            } else if (getValueOfboleaan(formdata.result_residence_data) == 1) {

                binding.radioStayingDataCH2.setChecked(true);
            }
        }
        if (itsNotNull(formdata.result_client_with_same_address)) {
            if (getValueOfboleaan(formdata.result_client_with_same_address) == 0) {
                binding.radioAddersCH.setChecked(true);

            } else if (getValueOfboleaan(formdata.result_client_with_same_address) == 1) {

                binding.radioAddersCH2.setChecked(true);
            }
        }
        if (itsNotNull(formdata.result_attachment_data)) {
            if (getValueOfboleaan(formdata.result_attachment_data) == 0) {
                binding.radioBillCH.setChecked(true);

            } else if (getValueOfboleaan(formdata.result_attachment_data) == 1) {

                binding.radioBillCH2.setChecked(true);
            }
        }
        if (itsNotNull(formdata.result_husband_data)) {
            if (getValueOfboleaan(formdata.result_husband_data) == 0) {
                binding.radioWifeCH.setChecked(true);

            } else if (getValueOfboleaan(formdata.result_husband_data) == 1) {

                binding.radioWifeCH2.setChecked(true);
            }
        }

        if (itsNotNull(formdata.result_family_data)) {
            if (getValueOfboleaan(formdata.result_family_data) == 0) {
                binding.radioFamilyCH.setChecked(true);

            } else if (getValueOfboleaan(formdata.result_family_data) == 1) {

                binding.radioFamilyCH2.setChecked(true);
            }
        }

        if (itsNotNull(formdata.result_client_reputation)) {
            if (getValueOfboleaan(formdata.result_client_reputation) == 1) {
                binding.radioRaptaionCH.setChecked(true);

            } else if (getValueOfboleaan(formdata.result_client_reputation) == 2) {

                binding.radioRaptaionCH2.setChecked(true);


            } else if (getValueOfboleaan(formdata.result_client_reputation) == 3) {

                binding.radioRaptaionCH3.setChecked(true);
            }
        }

        if (itsNotNull(formdata.result_sources)) {
            if (getValueOfboleaan(formdata.result_sources) == 1) {
                binding.radioSourseCH.setChecked(true);

            } else if (getValueOfboleaan(formdata.result_sources) == 2) {

                binding.radioSourseCH2.setChecked(true);


            } else if (getValueOfboleaan(formdata.result_sources) == 3) {

                binding.radioSourseCH3.setChecked(true);
            } else if (getValueOfboleaan(formdata.result_sources) == 4) {

                binding.radioSourseCH4.setChecked(true);
            }

        }
        if (itsNotNull(formdata.result_attached_husband_national_id)) {
            for (int i = 0; i < formdata.result_attached_husband_national_id.size(); i++) {
                String name = getValue(formdata.result_attached_husband_national_id.get(i));

                setAdpater(Uri.parse(name), Uri.parse(name).getLastPathSegment(), imageFileData1, adapter);

            }
            //
        }
        if (itsNotNull(formdata.result_attached_utilities_receipt)) {
            for (int i = 0; i < formdata.result_attached_utilities_receipt.size(); i++) {
                String name = getValue(formdata.result_attached_utilities_receipt.get(i));

                setAdpater(Uri.parse(name), Uri.parse(name).getLastPathSegment(), imageFileData2, adapter2);

            }
        }
        if (itsNotNull(formdata.attachments)) {
            for (int i = 0; i < formdata.attachments.size(); i++) {
                String name = getValue(formdata.attachments.get(i));

                setAdpater(Uri.parse(name), Uri.parse(name).getLastPathSegment(), imageFileData3, adapter3);

            }
        }
    }


    private void SetWHData() {
        binding.WHFullName.setText(getValue(formdata.husband_name));
        binding.WHNakeName.setText(getValue(formdata.husband_nickname));
        binding.WHNainolIdNumber.setText(getValue(formdata.husband_national_id));
        binding.WHPerathData.setText(getValue(formdata.husband_birth_date));
        binding.WHPhonNumber.setText(getValue(formdata.husband_mobile_number_1));
        binding.WHPhonNumber2.setText(getValue(formdata.husband_mobile_number_2));
        binding.WHPhonNumber3.setText(getValue(formdata.husband_phone));
        binding.WHFamilyMammaber.setText(getValue(formdata.husband_family_number));
    }

    private void SetRacietAttach() {
        binding.CounterNumber.setText(getValue(formdata.attached_counter_number));
        binding.BoardNumber.setText(getValue(formdata.attached_plate_number));
        binding.Monehtlcost.setText(getValue(formdata.attached_average_monthly_consumption));

        //نوع ايصال المرفقب
        if (itsNotNull(formdata.attached_type)) {
            if (getValueOfboleaan(formdata.attached_type) == 1) {
                binding.radio1.setChecked(true);

            } else if (getValueOfboleaan(formdata.attached_type) == 2) {

                binding.radio2.setChecked(true);


            } else if (getValueOfboleaan(formdata.attached_type) == 3) {

                binding.radio3.setChecked(true);
            }
        }
    }

    private void SetReadinesInfo() {
        binding.RentLogic.setText(getValue(formdata.residence_rent_duration));
        binding.RentCost.setText(getValue(formdata.residence_rent_value));
        binding.DataeOfending.setText(getValue(formdata.residence_rent_to));
        binding.Phonehome.setText(getValue(formdata.residence_building_number));
        binding.StreatName.setText(getValue(formdata.residence_street_name));
        binding.Nieporehod.setText(getValue(formdata.residence_neighborhood));
        binding.Ciety.setText(getValue(formdata.residence_city));
        binding.governorate.setText(getValue(formdata.residence_governorate));
        binding.SpeiclSine.setText(getValue(formdata.residence_special_marque));
        binding.TimeOfStaing.setText(getValue(formdata.residence_stay_duration));
        // نوع السكن
        if (itsNotNull(formdata.residence_accommodation_type)) {
            if (getValueOfboleaan(formdata.residence_accommodation_type) == 1) {
                binding.radioButton.setChecked(true);

            } else if (getValueOfboleaan(formdata.residence_accommodation_type) == 2) {

                binding.radioButton2.setChecked(true);

            } else if (getValueOfboleaan(formdata.residence_accommodation_type) == 3) {

                binding.radioButton3.setChecked(true);

            } else if (getValueOfboleaan(formdata.residence_accommodation_type) == 4) {

                binding.radioButton4.setChecked(true);

            } else if (getValueOfboleaan(formdata.residence_accommodation_type) == 5) {

                binding.radioButton5.setChecked(true);
            }
        }
    }

    private void setAgentData() {
        binding.FullName.setText(getValue(formdata.client_name));
        binding.NakeName.setText((getValue(formdata.client_nickname)));
        binding.NainolIdNumber.setText(formdata.client_national_id);
        binding.PerathData.setText(formdata.client_birth_date);
        binding.PhonNumber.setText(formdata.client_mobile_number_1);
        binding.PhonNumber2.setText(formdata.client_mobile_number_2);
        binding.PhonNumber3.setText(formdata.client_phone);
    }


    private boolean getFromUiData() {

        formdata.request_service_id = servier_id;

        setErrorNullForTextView();
//بيانات العميل
        if (getAgentDataValdion()) {
            return true;
        }
        //بيانات محل الإقامة
        if (getResanetDataValditio()) {
            return true;
        }
        //بيانات ايصال المرافق
        if (getReacietAttachValdition()) {
            return true;
        }
        //بيانات الزوج او الزوجة
        if (getWHDataValditon()) {
            return true;
        }

        //الاستعلام
        return getAskingData();
    }

    private void setErrorNullForTextView() {
        binding.HomeType.setError(null);
        binding.AttachmentRecietType.setError(null);
        binding.AgenetDataIsTrue.setError(null);
        binding.AgentHomeTrue.setError(null);
        binding.AgentStaingTrue.setError(null);
        binding.AttacemtenerTrue.setError(null);
        binding.WHTRue.setError(null);
        binding.FamilyTrue.setError(null);
        binding.Repation.setError(null);
        binding.SoruseTrue.setError(null);
    }

    private boolean getAskingData() {
        if (binding.radioDataCoustmer.getCheckedRadioButtonId() == binding.radioDataCoustmerCH.getId()) {
            formdata.result_personal_data = 0;
        } else if (binding.radioDataCoustmer.getCheckedRadioButtonId() == binding.radioDataCoustmerCH2.getId()) {
            formdata.result_personal_data = 1;
        } else {
            binding.AgenetDataIsTrue.setError(ErrorMessageValdition);
            Toast.makeText(getContext(), ErrorMessageValdition, Toast.LENGTH_SHORT).show();
            return true;
        }
        if (binding.radioStayingData.getCheckedRadioButtonId() == binding.radioStayingDataCH.getId()) {
            formdata.result_residence_data = 0;
        } else if (binding.radioStayingData.getCheckedRadioButtonId() == binding.radioStayingDataCH2.getId()) {
            formdata.result_residence_data = 1;
        } else {
            binding.AgentHomeTrue.setError(ErrorMessageValdition);
            Toast.makeText(getContext(), ErrorMessageValdition, Toast.LENGTH_SHORT).show();
            return true;
        }
        if (binding.radioAdders.getCheckedRadioButtonId() == binding.radioAddersCH.getId()) {
            formdata.result_client_with_same_address = 0;
        } else if (binding.radioAdders.getCheckedRadioButtonId() == binding.radioAddersCH2.getId()) {
            formdata.result_client_with_same_address = 1;
        } else {
            binding.AgentStaingTrue.setError(ErrorMessageValdition);
            Toast.makeText(getContext(), ErrorMessageValdition, Toast.LENGTH_SHORT).show();
            return true;
        }
        if (binding.radioBill.getCheckedRadioButtonId() == binding.radioBillCH.getId()) {
            formdata.result_attachment_data = 0;
        } else if (binding.radioBill.getCheckedRadioButtonId() == binding.radioBillCH2.getId()) {
            formdata.result_attachment_data = 1;
        } else {
            binding.AttacemtenerTrue.setError(ErrorMessageValdition);
            Toast.makeText(getContext(), ErrorMessageValdition, Toast.LENGTH_SHORT).show();
            return true;
        }

        if (binding.radioWife.getCheckedRadioButtonId() == binding.radioWifeCH.getId()) {
            formdata.result_husband_data = 0;
        } else if (binding.radioWife.getCheckedRadioButtonId() == binding.radioWifeCH2.getId()) {
            formdata.result_husband_data = 1;
        } else {
            binding.WHTRue.setError(ErrorMessageValdition);
            Toast.makeText(getContext(), ErrorMessageValdition, Toast.LENGTH_SHORT).show();
            return true;
        }
        if (binding.radioFamily.getCheckedRadioButtonId() == binding.radioFamilyCH.getId()) {
            formdata.result_family_data = 0;
        } else if (binding.radioFamily.getCheckedRadioButtonId() == binding.radioFamilyCH2.getId()) {
            formdata.result_family_data = 1;
        } else {
            binding.FamilyTrue.setError(ErrorMessageValdition);
            Toast.makeText(getContext(), ErrorMessageValdition, Toast.LENGTH_SHORT).show();
            return true;
        }

        if (binding.radioRaptaion.getCheckedRadioButtonId() == binding.radioRaptaionCH.getId()) {
            formdata.result_client_reputation = 1;
        } else if (binding.radioRaptaion.getCheckedRadioButtonId() == binding.radioRaptaionCH2.getId()) {
            formdata.result_client_reputation = 2;
        } else if (binding.radioRaptaion.getCheckedRadioButtonId() == binding.radioRaptaionCH3.getId()) {
            formdata.result_client_reputation = 3;
        } else {
            binding.Repation.setError(ErrorMessageValdition);
            Toast.makeText(getContext(), ErrorMessageValdition, Toast.LENGTH_SHORT).show();
            return true;
        }


        if (binding.radioSourse.getCheckedRadioButtonId() == binding.radioSourseCH.getId()) {
            formdata.result_sources = 1;
        } else if (binding.radioSourse.getCheckedRadioButtonId() == binding.radioSourseCH2.getId()) {
            formdata.result_sources = 2;
        } else if (binding.radioSourse.getCheckedRadioButtonId() == binding.radioSourseCH3.getId()) {
            formdata.result_sources = 3;
        } else if (binding.radioSourse.getCheckedRadioButtonId() == binding.radioSourseCH4.getId()) {
            formdata.result_sources = 4;
        } else {
            binding.SoruseTrue.setError(ErrorMessageValdition);
            Toast.makeText(getContext(), ErrorMessageValdition, Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    private boolean getReacietAttachValdition() {
        //نوع ايصال المرافق
        if (binding.radio1.isChecked()) {
            formdata.attached_type = 1;
        } else if (binding.radio2.isChecked()) {
            formdata.attached_type = 2;
        } else if (binding.radio3.isChecked()) {
            formdata.attached_type = 3;
        } else {
            binding.AttachmentRecietType.setError(ErrorMessageValdition);
            Toast.makeText(getContext(), ErrorMessageValdition, Toast.LENGTH_SHORT).show();
            return true;
        }
        if (CheckInputfield(binding.CounterNumber, getContext())) return true;
        if (CheckInputfield(binding.BoardNumber, getContext())) return true;
        if (CheckInputfield(binding.Monehtlcost, getContext())) return true;

        getReacietAttach();
        return false;
    }

    private void getReacietAttach() {
        formdata.attached_counter_number = getValue(binding.CounterNumber.getText());
        formdata.attached_plate_number = getValue(binding.BoardNumber.getText());
        formdata.attached_average_monthly_consumption = getValue(binding.Monehtlcost.getText());

    }

    private boolean getWHDataValditon() {
        if (CheckInputfield(binding.WHFullName, getContext())) return true;
        if (CheckInputfield(binding.WHNainolIdNumber, getContext())) return true;
        if (VadlditoForIdNumber(binding.WHNainolIdNumber, getContext())) return true;
        if (CheckInputfield(binding.WHPerathData, getContext())) return true;

        if (CheckInputfield(binding.WHPhonNumber, getContext())) return true;
        if (setPhoneNumberValdtion(binding.WHPhonNumber, getContext())) return true;
        if (CheckInputfield(binding.WHFamilyMammaber, getContext())) return true;
        getWHData();
        return false;
    }


    private void getWHData() {
        formdata.husband_name = getValue(binding.WHFullName.getText());
        formdata.husband_nickname = getValue(binding.WHNakeName.getText());
        formdata.husband_national_id = getValue(binding.WHNainolIdNumber.getText());
        formdata.husband_birth_date = getValue(binding.WHPerathData.getText());
        formdata.husband_mobile_number_1 = getValue(binding.WHPhonNumber.getText());
        formdata.husband_mobile_number_2 = getValue(binding.WHPhonNumber2.getText());
        formdata.husband_phone = getValue(binding.WHPhonNumber3.getText());
        formdata.husband_family_number = getValue(binding.WHFamilyMammaber.getText());
    }

    private boolean getResanetDataValditio() {

        //بيانات محل الإقامة
        boolean homeType;
        if (binding.radioButton.isChecked()) {
            homeType = false;
            formdata.residence_accommodation_type = 1;
        } else if (binding.radioButton2.isChecked()) {
            homeType = false;
            formdata.residence_accommodation_type = 2;
        } else if (binding.radioButton3.isChecked()) {
            homeType = true;
            formdata.residence_accommodation_type = 3;
        } else if (binding.radioButton4.isChecked()) {
            homeType = true;
            formdata.residence_accommodation_type = 4;
        } else if (binding.radioButton5.isChecked()) {
            homeType = true;
            formdata.residence_accommodation_type = 5;
        } else {
            binding.HomeType.setError(ErrorMessageValdition);
            Toast.makeText(getContext(), ErrorMessageValdition, Toast.LENGTH_SHORT).show();
            return true;
        }
        if (homeType) {


            if (CheckInputfield(binding.RentLogic, getContext())) return true;
            if (CheckInputfield(binding.RentCost, getContext())) return true;
            if (CheckInputfield(binding.DataeOfending, getContext())) return true;

        }

        if (CheckInputfield(binding.Phonehome, getContext())) return true;
        if (CheckInputfield(binding.StreatName, getContext())) return true;
        if (CheckInputfield(binding.Nieporehod, getContext())) return true;
        if (CheckInputfield(binding.Ciety, getContext())) return true;
        if (CheckInputfield(binding.governorate, getContext())) return true;
        if (CheckInputfield(binding.SpeiclSine, getContext())) return true;
        if (CheckInputfield(binding.TimeOfStaing, getContext())) return true;

        getResanetData();
        return false;
    }


    private void getResanetData() {
        formdata.residence_rent_duration = getValue(binding.RentLogic.getText());
        formdata.residence_rent_value = getValue(binding.RentCost.getText());
        formdata.residence_rent_to = getValue(binding.DataeOfending.getText());
        formdata.residence_building_number = getValue(binding.Phonehome.getText());
        formdata.residence_street_name = getValue(binding.StreatName.getText());
        formdata.residence_neighborhood = getValue(binding.Nieporehod.getText());
        formdata.residence_city = getValue(binding.Ciety.getText());
        formdata.residence_governorate = getValue(binding.governorate.getText());
        formdata.residence_special_marque = getValue(binding.SpeiclSine.getText());
        formdata.residence_stay_duration = getValue(binding.TimeOfStaing.getText());


    }

    private boolean getAgentDataValdion() {
        // بيانات العميل
        if (CheckInputfield(binding.FullName, getContext())) return true;
        if (CheckInputfield(binding.NainolIdNumber, getContext())) return true;
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


    private void setrRadoisGroubs() {

        // All this Code to make shoure whant Radio Button is selected The other is Off
        binding.radioButton.setOnCheckedChangeListener((compoundButton, b) -> {

            if (binding.radioButton.isChecked()) {
                binding.RentColaet.setVisibility(View.GONE);
                if (formdata != null) {
                    formdata.residence_accommodation_type = 1;
                }
                if (binding.radioButton2.isChecked() || binding.radioButton3.isChecked() || binding.radioButton4.isChecked()
                        || binding.radioButton5.isChecked()) {

                    binding.radioButton2.setChecked(false);
                    binding.radioButton3.setChecked(false);
                    binding.radioButton4.setChecked(false);
                    binding.radioButton5.setChecked(false);
                }
                //    binding.CheckBox1.setChecked(true);
            }

        });
        binding.radioButton2.setOnCheckedChangeListener((compoundButton, b) -> {
            if (binding.radioButton2.isChecked()) {
                binding.RentColaet.setVisibility(View.GONE);
                if (formdata != null) {
                    formdata.residence_accommodation_type = 2;
                }
                if (binding.radioButton.isChecked() || binding.radioButton3.isChecked() || binding.radioButton4.isChecked()
                        || binding.radioButton5.isChecked()) {

                    binding.radioButton.setChecked(false);
                    binding.radioButton3.setChecked(false);
                    binding.radioButton4.setChecked(false);
                    binding.radioButton5.setChecked(false);
                }
                // binding.CheckBox2.setChecked(true);
            }

        });
        binding.radioButton3.setOnCheckedChangeListener((compoundButton, b) -> {
            if (binding.radioButton3.isChecked()) {
                binding.RentColaet.setVisibility(View.VISIBLE);
                if (formdata != null) {
                    formdata.residence_accommodation_type = 3;
                }
                if (binding.radioButton.isChecked() || binding.radioButton2.isChecked() || binding.radioButton4.isChecked()
                        || binding.radioButton5.isChecked()) {
                    binding.radioButton.setChecked(false);
                    binding.radioButton2.setChecked(false);
                    binding.radioButton4.setChecked(false);
                    binding.radioButton5.setChecked(false);
                }
                //binding.CheckBox3.setChecked(true);
            }
        });
        binding.radioButton4.setOnCheckedChangeListener((compoundButton, b) -> {
            if (binding.radioButton4.isChecked()) {
                binding.RentColaet.setVisibility(View.VISIBLE);
                if (formdata != null) {
                    formdata.residence_accommodation_type = 4;
                }
                if (binding.radioButton.isChecked() || binding.radioButton2.isChecked() || binding.radioButton3.isChecked()
                        || binding.radioButton5.isChecked()) {
                    binding.radioButton.setChecked(false);
                    binding.radioButton2.setChecked(false);
                    binding.radioButton3.setChecked(false);
                    binding.radioButton5.setChecked(false);
                }
                //   binding.CheckBox4.setChecked(true);
            }
        });
        binding.radioButton5.setOnCheckedChangeListener((compoundButton, b) -> {
            if (binding.radioButton5.isChecked()) {
                binding.RentColaet.setVisibility(View.VISIBLE);
                if (formdata != null) {
                    formdata.residence_accommodation_type = 5;
                }
                if (binding.radioButton.isChecked() || binding.radioButton2.isChecked() || binding.radioButton4.isChecked()
                        || binding.radioButton3.isChecked()) {
                    binding.radioButton.setChecked(false);
                    binding.radioButton2.setChecked(false);
                    binding.radioButton4.setChecked(false);
                    binding.radioButton3.setChecked(false);
                }
                //   binding.radioButton5.setChecked(true);
            }

        });

        // All this Code to make shoure whant Radio Button is selected The other is Off
        binding.radio1.setOnCheckedChangeListener((compoundButton, b) -> {

            if (binding.radio1.isChecked()) {
                if (formdata != null) {
                    formdata.attached_type = 1;
                }
                if (binding.radio2.isChecked() || binding.radio3.isChecked()) {
                    binding.radio2.setChecked(false);
                    binding.radio3.setChecked(false);
                }
                //    binding.CheckBox1.setChecked(true);
            }

        });
        binding.radio2.setOnCheckedChangeListener((compoundButton, b) -> {
            if (binding.radio2.isChecked()) {
                if (formdata != null) {
                    formdata.attached_type = 2;
                }
                if (binding.radio1.isChecked() || binding.radio3.isChecked()) {
                    binding.radio1.setChecked(false);
                    binding.radio3.setChecked(false);
                }
                // binding.CheckBox2.setChecked(true);
            }

        });
        binding.radio3.setOnCheckedChangeListener((compoundButton, b) -> {
            if (binding.radio3.isChecked()) {
                if (formdata != null) {
                    formdata.attached_type = 3;
                }
                if (binding.radio1.isChecked() || binding.radio2.isChecked()) {
                    binding.radio1.setChecked(false);
                    binding.radio2.setChecked(false);
                }
                //binding.CheckBox3.setChecked(true);
            }
        });
    }

    private void SetDate() {
        Constans.setDateForInputText(binding.PerathData, getContext());
        Constans.setDateForInputText(binding.WHPerathData, getContext());
        Constans.setDateForInputText(binding.DataeOfending, getContext());
    }

    private void SelectImage() {

        activityResultLauncher.launch("image/*");

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

    private void SelectFiles() {
        activityResultLauncher.launch("*/*");

    }


    @Override
    public void imageDone(Uri uri) {

        //   String extanton=getFileExtension(result.get(i),getContext());
        aboudfile = new File(FileUtil.getPath(uri, getContext()));
        if (result_attached_utilities_receiptSelected) {
            result_attached_utilities_receipt.add(aboudfile);
            setAdpater(uri, aboudfile.getName(), imageFileData2, adapter2);
        }
        if (result_attached_husband_national_idSelected) {
            result_attached_husband_national_id.add(aboudfile);
            setAdpater(uri, aboudfile.getName(), imageFileData1, adapter);
        }
        if (attachments_idSelected) {
            attachments.add(aboudfile);
            setAdpater(uri, aboudfile.getName(), imageFileData3, adapter3);
        }


    }


}



