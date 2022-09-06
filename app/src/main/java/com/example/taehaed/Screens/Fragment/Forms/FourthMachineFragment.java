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
import androidx.fragment.app.FragmentManager;
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
import com.example.taehaed.Screens.Fragment.PopAttachmentFragment;
import com.example.taehaed.databinding.FragmentFourthMachineBinding;

import java.io.File;
import java.util.ArrayList;


public class FourthMachineFragment extends Fragment implements ImageTakeIt {
    private int servier_id,DoneStatus;
    private FormData formdata;
    private AlertDialog alertDialog;
    private TaehaedVModel taehaedVModel;
    private FragmentFourthMachineBinding binding;
    private PopAttachmentFragment popAttachmentFragment;
    private FragmentManager fragmentManager;
     private int AttachmentNumber;
    private ArrayList<ImageFileData> imageFileData, imageFileData2, imageFileData3,imageFileData4,imageFileData5;
    private ArrayList<ImageFileApabter> adApabters = new ArrayList<>();
    private   ArrayList<File> vehicle_result_attachments_driving_license,
            vehicle_result_attachments_national_id,vehicle_result_attachments_vehicle_license
            ,vehicle_result_attachments_purchase_contract,attachments;
    private File aboudfile;
    ActivityResultLauncher<String> activityResultLauncher;
    public FourthMachineFragment() {

    }


    public static FourthMachineFragment newInstance(int id) {
        FourthMachineFragment fragment = new FourthMachineFragment();

        Bundle args = new Bundle();
        args.putInt(Constans.IdkeyFrgment, id);

        fragment.setArguments(args);
        return fragment;
    }

    public static FourthMachineFragment newInstance(int id, FormData formData,int DoneStatus) {
        FourthMachineFragment fragment = new FourthMachineFragment();

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
            formdata = (FormData) getArguments().getSerializable(DESCRIBABLE_KEY);
            DoneStatus = getArguments().getInt(Constans.DoneStatus);
        }
        for(int i=0 ; i<5;i++)
        {
            adApabters.add(new ImageFileApabter());
        }
        vehicle_result_attachments_national_id = new ArrayList<>();
        vehicle_result_attachments_driving_license= new ArrayList<>();
        vehicle_result_attachments_vehicle_license= new ArrayList<>();
        vehicle_result_attachments_purchase_contract= new ArrayList<>();
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
        binding = FragmentFourthMachineBinding.inflate(getLayoutInflater());
        taehaedVModel = new ViewModelProvider(requireActivity()).get(TaehaedVModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //ظبط التاريخ
        setData();

        if(SetDataUI()) {
         /*   Constans.enableDisableViewGroup(binding.TopBoss,false);
            binding.DisplaImagefor.setEnabled(true);
            binding.DisplayPDf.setEnabled(true);
            binding.ViecalShow.setEnabled(true);
            binding.ArrgemtnShow.setEnabled(true);*/

            binding.ArrgemtnShow.setVisibility(View.VISIBLE);

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

          if(  getFromUiData()) return; ;
            alertDialog = setAlertMeaage(getString(R.string.getthedata), getContext());
            alertDialog.show();

            if(DoneStatus==1)
            {
                NoteBodey noteBodey = new NoteBodey();
                noteBodey.setRequest_service_id(servier_id);
                noteBodey.setReport("تم تعديل الاستعلام");
                taehaedVModel.ConvertDoneToAccept(noteBodey, (status,errorM) -> {
                    if(status)
                    {
                        DoneStatus=0;
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

        SetTheRadioButton();
    }

    private void setRcylerviews() {
        setRes(binding.RescView, adApabters.get(0),getContext());
        setRes(binding.RescView2,  adApabters.get(1),getContext());
        setRes(binding.RescView3,  adApabters.get(2),getContext());
        setRes(binding.RescView4,  adApabters.get(3),getContext());
        setRes(binding.RescView7,  adApabters.get(4),getContext());
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

    private void setNewFormData() {
        if (DoneStatus == 0) {
            formdata = new FormData();
        }
    }

    private void setDone() {
        taehaedVModel.setDoneserviesWithFielsFrom4(formdata, vehicle_result_attachments_driving_license,
                vehicle_result_attachments_national_id, vehicle_result_attachments_vehicle_license
                , vehicle_result_attachments_purchase_contract,attachments, (status, Message) -> {
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
                binding.attacmentImagetShoer.setVisibility(View.VISIBLE);


            } else {
                binding.attacmentImagetShoer.setVisibility(View.GONE);

            }
        });
        binding.radioFielsCH2.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                binding.attacmentLisensDriveShoer.setVisibility(View.VISIBLE);
            } else {

                binding.attacmentLisensDriveShoer.setVisibility(View.GONE);
            }
        });
        binding.radioFielsCH3.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                binding.attacmentLiesnesViecalShoer.setVisibility(View.VISIBLE);
            } else {
                binding.attacmentLiesnesViecalShoer.setVisibility(View.GONE);
            }
        });
        binding.radioFielsCH4.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                binding.attacmentAgrremtnShoer.setVisibility(View.VISIBLE);
            } else {
                binding.attacmentAgrremtnShoer.setVisibility(View.GONE);
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
                donlowdTheFile(formdata.vehicle_result_attachments_driving_license,getActivity(),getContext());
                donlowdTheFile(formdata.vehicle_result_attachments_national_id,getActivity(),getContext());
                donlowdTheFile(formdata.vehicle_result_attachments_vehicle_license,getActivity(),getContext());
                donlowdTheFile(formdata.vehicle_result_attachments_purchase_contract,getActivity(),getContext());
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
                            if (AttachmentNumber==1) {
                                vehicle_result_attachments_national_id.add(aboudfile);
                                setAdpater(result.get(i), aboudfile.getName(), imageFileData, adApabters.get(0));
                            }
                            else if (AttachmentNumber==2) {
                                vehicle_result_attachments_driving_license.add(aboudfile);
                                setAdpater(result.get(i), aboudfile.getName(), imageFileData2, adApabters.get(1));
                            }  else if (AttachmentNumber==3) {
                                vehicle_result_attachments_vehicle_license.add(aboudfile);
                                setAdpater(result.get(i), aboudfile.getName(), imageFileData3, adApabters.get(2));
                            }  else if (AttachmentNumber==4) {
                                vehicle_result_attachments_purchase_contract.add(aboudfile);
                                setAdpater(result.get(i), aboudfile.getName(), imageFileData4, adApabters.get(3));
                            }else if (AttachmentNumber==5) {
                                attachments.add(aboudfile);
                                setAdpater(result.get(i), aboudfile.getName(), imageFileData5, adApabters.get(4));
                            }



                        }


                    }
                });
    }


    private void SelectFiles() {
        activityResultLauncher.launch("*/*");
    }

    private void SelectImage() {
        activityResultLauncher.launch("image/*");

    }

    private void setData() {
        Constans.setDateForInputText(binding.PerathData, getContext());
        Constans.setDateForInputText(binding.DateOfDelivery, getContext());
        Constans.setDateForInputText(binding.EndingDate, getContext());
        Constans.setDateForInputText(binding.year, getContext());
        Constans.setDateForInputText(binding.yearOfFactory, getContext());
        Constans.setDateForInputText(binding.DateOfDeliveryMahine, getContext());
        Constans.setDateForInputText(binding.EndingDateMachine, getContext());

    }

    private boolean SetDataUI() {
        if (itsNotNull(formdata)) {
            binding.Sumbit.setVisibility(View.VISIBLE);

            //بيانات العميل
            SetAgentData();
            //بيانات رخصة القيادة
            SetLisenesData();
            //بيانات رخصة المركبة
            SetViesicalLisesData();
            // نتيجة الاستعلام
            SetAskingData();
            return true;
        }else{
            return false;
        }
    }

    private void SetAskingData() {

        if (itsNotNull(formdata.vehicle_result_driving_license)) {
            if (getValueOfboleaan(formdata.vehicle_result_driving_license) == 0) {
                binding.radioLieesnasCH.setChecked(true);

            } else if (getValueOfboleaan(formdata.vehicle_result_driving_license) == 1) {

                binding.radioLieesnasCH2.setChecked(true);
            }

        }
        if (itsNotNull(formdata.vehicle_result_license)) {
            if (getValueOfboleaan(formdata.vehicle_result_license) == 0) {
                binding.radioMachineLiesnseCH.setChecked(true);

            } else if (getValueOfboleaan(formdata.vehicle_result_license) == 1) {

                binding.radioMachineLiesnseCH2.setChecked(true);
            }

        }

        if (itsNotNull(formdata.vehicle_result_condition)) {
            if (getValueOfboleaan(formdata.vehicle_result_condition) == 1) {
                binding.radioRaptaionCH.setChecked(true);

            } else if (getValueOfboleaan(formdata.vehicle_result_condition) == 2) {
                binding.radioRaptaionCH2.setChecked(true);

            } else if (getValueOfboleaan(formdata.vehicle_result_condition) == 3) {
                binding.radioRaptaionCH3.setChecked(true);
            }
        }

        if (itsNotNull(formdata.vehicle_result_value)) {
            if (getValueOfboleaan(formdata.vehicle_result_value) == 0) {
                binding.radioValueCH.setChecked(true);

            } else if (getValueOfboleaan(formdata.vehicle_result_value) == 1) {
                binding.radioValueCH2.setChecked(true);

            }

        }
        //المرفقات
        if (itsNotNull(formdata.vehicle_result_attachments_driving_license)) {
            for (int i = 0; i < formdata.vehicle_result_attachments_driving_license.size(); i++) {
                String name = getValue(formdata.vehicle_result_attachments_driving_license.get(i));

                setAdpater(Uri.parse(name), Uri.parse(name).getLastPathSegment(), imageFileData, adApabters.get(0));

            }
            //
        }
        if (itsNotNull(formdata.vehicle_result_attachments_national_id)) {
            for (int i = 0; i < formdata. vehicle_result_attachments_national_id.size(); i++) {
                String name = getValue(formdata. vehicle_result_attachments_national_id.get(i));

                setAdpater(Uri.parse(name), Uri.parse(name).getLastPathSegment(), imageFileData2, adApabters.get(1));

            }
            //
        }
        if (itsNotNull(formdata.vehicle_result_attachments_vehicle_license)) {
            for (int i = 0; i < formdata.vehicle_result_attachments_vehicle_license.size(); i++) {
                String name = getValue(formdata.vehicle_result_attachments_vehicle_license.get(i));

                setAdpater(Uri.parse(name), Uri.parse(name).getLastPathSegment(), imageFileData3, adApabters.get(2));

            }
            //
        }
        if (itsNotNull(formdata.vehicle_result_attachments_purchase_contract)) {
            for (int i = 0; i < formdata.vehicle_result_attachments_purchase_contract.size(); i++) {
                String name = getValue(formdata.vehicle_result_attachments_purchase_contract.get(i));
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

    private void SetViesicalLisesData() {
        binding.TypeOfMachine.setText(getValue(formdata.vehicle_license_type));
        binding.Brand.setText(getValue(formdata.vehicle_license_brand));
        binding.year.setText(getValue(formdata.vehicle_license_model));
        binding.Enaing.setText(getValue(formdata.vehicle_license_engine_capacity));
        binding.yearOfFactory.setText(getValue(formdata.vehicle_license_manufacturing_year));
        binding.MaterInway.setText(getValue(formdata.vehicle_license_traveled_distance));
        binding.DateOfDeliveryMahine.setText(getValue(formdata.vehicle_license_form));
        binding.EndingDateMachine.setText(getValue(formdata.vehicle_license_to));
        binding.Worth.setText(getValue(formdata.vehicle_license_value_when_buying));
        binding.WorthNow.setText(getValue(formdata.vehicle_license_value_now));
        // الحالة عند الحيازة
        if (itsNotNull(formdata.vehicle_license_situation)) {
            if (getValueOfboleaan(formdata.vehicle_license_situation) == 1) {
                binding.rch.setChecked(true);
                binding.rch2.setChecked(false);
                binding.rch3.setChecked(false);
            } else if (getValueOfboleaan(formdata.vehicle_license_situation) == 2) {
                binding.rch.setChecked(false);
                binding.rch2.setChecked(true);
                binding.rch3.setChecked(false);
            } else if (getValueOfboleaan(formdata.vehicle_license_situation) == 3) {
                binding.rch.setChecked(false);
                binding.rch2.setChecked(false);
                binding.rch3.setChecked(true);
            }
        }
    }

    private void SetLisenesData() {
        binding.Lisenestype.setText(getValue(formdata.driving_license_type));
        binding.LiesnesNumber.setText(getValue(formdata.driving_license_number));
        binding.GovenmentUnit.setText(getValue(formdata.driving_license_traffic_center));
        binding.DateOfDelivery.setText(getValue(formdata.driving_license_form));
        binding.EndingDate.setText(getValue(formdata.driving_license_to));
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

    private void SetTheRadioButton() {

    }



    private boolean getFromUiData() {


        formdata.request_service_id = servier_id;
        setErrorNullForTextView();
        //بيانات العميل
        if(getAgentDataValdion()) return true;
        //بيانات رخصة القيادة
        if(getDerivng_LisenseValditon()) return true;
        //بيانات رخصة المركبة
        if(getVehicle_LicenseValdtion()) return true;

        // نتيجة الاستعلام
        return getAskingDataInfo();
    }

    private void setErrorNullForTextView() {
        binding.StatusWhecapTrue.setError(null);
        binding.LissornsTrue.setError(null);
        binding.VieclalTrue.setError(null);
        binding.StatusVieaclTrue.setError(null);
        binding.WorthetyTrue.setError(null);
    }

    private boolean getAskingDataInfo() {
        if (binding.radioLieesnasCH.isChecked()) {
            formdata.vehicle_result_driving_license = 0;
        } else if (binding.radioLieesnasCH2.isChecked()) {
            formdata.vehicle_result_driving_license = 1;
        } else {
            setErrorTextView(binding.LissornsTrue, getContext());
            return true;
        }
        if (binding.radioMachineLiesnseCH.isChecked()) {
            formdata.vehicle_result_license = 0;
        } else if (binding.radioMachineLiesnseCH2.isChecked()) {
            formdata.vehicle_result_license = 1;
        } else {
            setErrorTextView(binding.VieclalTrue, getContext());
            return true;
        }


        if (binding.radioRaptaionCH.isChecked()) {
            formdata.vehicle_result_condition = 1;
        } else if (binding.radioRaptaionCH2.isChecked()) {
            formdata.vehicle_result_condition = 2;
        } else if (binding.radioRaptaionCH3.isChecked()) {
            formdata.vehicle_result_condition = 3;
        } else {
            setErrorTextView(binding.StatusVieaclTrue, getContext());
            return true;
        }

        if (binding.radioValueCH.isChecked()) {
            formdata.vehicle_result_value = 0;
        } else if (binding.radioValueCH2.isChecked()) {
            formdata.vehicle_result_value = 1;
        } else {
            setErrorTextView(binding.WorthetyTrue, getContext());
            return true;

        }
        return false;
    }

    private boolean getVehicle_LicenseValdtion() {
        if (CheckInputfield(binding.TypeOfMachine, getContext())) return true;
        if (CheckInputfield(binding.Brand, getContext())) return true;
        if (CheckInputfield(binding.year, getContext())) return true;
        if (CheckInputfield(binding.Enaing, getContext())) return true;
        if (CheckInputfield(binding.yearOfFactory, getContext())) return true;
        if (CheckInputfield(binding.MaterInway, getContext())) return true;
        if (CheckInputfield(binding.DateOfDeliveryMahine, getContext())) return true;
        if (CheckInputfield(binding.EndingDateMachine, getContext())) return true;
        if (CheckInputfield(binding.Worth, getContext())) return true;
        if (CheckInputfield(binding.WorthNow, getContext())) return true;
        // الحالة عند الحيازة
        if (binding.rch.isChecked()) {
            formdata.vehicle_license_situation = 1;
        } else if (binding.rch2.isChecked()) {
            formdata.vehicle_license_situation = 2;
        } else if (binding.rch3.isChecked()) {
            formdata.vehicle_license_situation = 3;
        } else {
            setErrorTextView(binding.StatusWhecapTrue, getContext());
            return true;
        }
        getVehicle_License();
        return false;
    }

    private void getVehicle_License() {
        formdata.vehicle_license_type = getValue(binding.TypeOfMachine.getText());
        formdata.vehicle_license_brand = getValue(binding.Brand.getText());
        formdata.vehicle_license_model = getValue(binding.year.getText());
        formdata.vehicle_license_engine_capacity = getValue(binding.Enaing.getText());
        formdata.vehicle_license_manufacturing_year = getValue(binding.yearOfFactory.getText());
        formdata.vehicle_license_traveled_distance = getValue(binding.MaterInway.getText());
        formdata.vehicle_license_form = getValue(binding.DateOfDeliveryMahine.getText());
        formdata.vehicle_license_to = getValue(binding.EndingDateMachine.getText());
        formdata.vehicle_license_value_when_buying = getValue(binding.Worth.getText());
        formdata.vehicle_license_value_now = getValue(binding.WorthNow.getText());

    }

    private boolean getDerivng_LisenseValditon() {
        if (CheckInputfield(binding.Lisenestype, getContext())) return true;
        if (CheckInputfield(binding.LiesnesNumber, getContext())) return true;
        if (CheckInputfield(binding.GovenmentUnit, getContext())) return true;
        if (CheckInputfield(binding.DateOfDelivery, getContext())) return true;
        if (CheckInputfield(binding.EndingDate, getContext())) return true;
        getDerivng_Lisense();
        return false;
    }


    private void getDerivng_Lisense() {
        formdata.driving_license_type = getValue(binding.Lisenestype.getText());
        formdata.driving_license_number = getValue(binding.LiesnesNumber.getText());
        formdata.driving_license_traffic_center = getValue(binding.GovenmentUnit.getText());
        formdata.driving_license_form = getValue(binding.DateOfDelivery.getText());
        formdata.driving_license_to = getValue(binding.EndingDate.getText());
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


    @Override
    public void imageDone(Uri uri) {
        aboudfile = new File(FileUtil.getPath(uri, getContext()));
        if (AttachmentNumber == 1) {
            vehicle_result_attachments_driving_license.add(aboudfile);
            setAdpater(uri, aboudfile.getName(), imageFileData, adApabters.get(0));

        } else if (AttachmentNumber == 2) {
            vehicle_result_attachments_national_id.add(aboudfile);
            setAdpater(uri, aboudfile.getName(), imageFileData2, adApabters.get(1));

        } else if (AttachmentNumber == 3) {
            vehicle_result_attachments_vehicle_license.add(aboudfile);
            setAdpater(uri, aboudfile.getName(), imageFileData3, adApabters.get(2));

        } else if (AttachmentNumber == 4) {
            vehicle_result_attachments_purchase_contract.add(aboudfile);
            setAdpater(uri, aboudfile.getName(), imageFileData4, adApabters.get(3));

        }
        else if (AttachmentNumber == 5) {
            attachments.add(aboudfile);
            setAdpater(uri, aboudfile.getName(), imageFileData5, adApabters.get(4));

        }
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
}