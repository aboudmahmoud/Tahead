package com.example.taehaed.Screens.Fragment;

import static com.example.taehaed.Constans.CheckInputfield;
import static com.example.taehaed.Constans.DESCRIBABLE_KEY;
import static com.example.taehaed.Constans.getValue;
import static com.example.taehaed.Constans.getValueOfboleaan;
import static com.example.taehaed.Constans.itsNotNull;
import static com.example.taehaed.Constans.setAlertMeaage;
import static com.example.taehaed.Constans.setErrorTextView;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.taehaed.Constans;
import com.example.taehaed.Model.TaehaedVModel;
import com.example.taehaed.Pojo.FormReuest.FormData;
import com.example.taehaed.Pojo.NoteBodey;
import com.example.taehaed.R;
import com.example.taehaed.databinding.FragmentFifithEnquiryComarialBinding;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class FifithEnquiryComarialFragment extends Fragment {

    private FragmentFifithEnquiryComarialBinding binding;
    private FormData formdata;
    private AlertDialog alertDialog;
    private TaehaedVModel taehaedVModel;
    private int servier_id;

    public FifithEnquiryComarialFragment() {

    }

    public static FifithEnquiryComarialFragment getInstance(int id) {
        FifithEnquiryComarialFragment fragment = new FifithEnquiryComarialFragment();
        Bundle args = new Bundle();
        args.putInt(Constans.IdkeyFrgment, id);
        fragment.setArguments(args);
        return fragment;
    }

    public static FifithEnquiryComarialFragment getInstance(int id, FormData formData) {
        FifithEnquiryComarialFragment fragment = new FifithEnquiryComarialFragment();
        Bundle args = new Bundle();
        args.putInt(Constans.IdkeyFrgment, id);
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
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFifithEnquiryComarialBinding.inflate(inflater);
        taehaedVModel = new ViewModelProvider(this).get(TaehaedVModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.goBack.setOnClickListener(view1 -> getActivity().onBackPressed());
        SetVisbilty();
        SetRadiobutttions();

        SetDate();


    /*   binding.radioButton.setOnClickListener(view1 -> {
          //binding.infoDataColoect.scrollTo(((int) binding.radioPartnerCH2.getX()),((int) binding.radioPartnerCH2.getY()));
           binding.Pradiao.setError("عفوا يجب ان تختار");
       });*/



        SetDataUI();
        binding.Sumbit.setOnClickListener(view1 -> {
            formdata = new FormData();
            if(getDataUI()) return;
            alertDialog= setAlertMeaage("جاري ارسال البيانات",getContext());
            alertDialog.show();
            taehaedVModel.setDoneservies(formdata, status -> {
                if (status) {
                    alertDialog.dismiss();
                    Toast.makeText(getContext(), "تم", Toast.LENGTH_SHORT).show();
                    getActivity().onBackPressed();
                } else {
                    alertDialog.dismiss();
                    Toast.makeText(getContext(), "يبدو ان هناك خطأ ما", Toast.LENGTH_SHORT).show();
                }
            });


        });
        binding.DeletSumbit.setOnClickListener(view1 -> {
            alertDialog= setAlertMeaage("جاري حذف الاستعلام",getContext());
            alertDialog.show();
            NoteBodey noteBodey = new NoteBodey();
            noteBodey.setRequest_service_id(servier_id);
            noteBodey.setReport("تم حذف الاستعلام لوقت لاحق");
            taehaedVModel.ConvertDoneToAccept(noteBodey, status -> {
                if (status) {
                    alertDialog.dismiss();
                    getActivity().onBackPressed();
//                        getActivity().recreate();
                } else {
                    alertDialog.dismiss();
                    Toast.makeText(getContext(), "عفوا يبدو ان هناك خطأ ما", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private void SetDate() {
        Constans.setDateForInputText(binding.PerathData,getContext());
        Constans.setDateForInputText(binding.ComarialDate,getContext());
        Constans.setDateForInputText(binding.ReleaseDateComarial,getContext());
        Constans.setDateForInputText(binding.ExpiryDate,getContext());
    }



    private boolean getDataUI() {
        formdata.request_service_id = servier_id;
        SetErrorNullForTextView();
        //بيانات العميل
      if(getAgentDataValdion()) return true;

        //بيانات النشاط
        if(getActvityValdation()) return true;


        //بيانات تجارية
        if(getCommerialDataValdation()) return true;


        //بيانات مقر النشاط
        if(getActvityPlaceValdation()) return true;


        //بيانات مرافق النشاط
        if( getRacietAttachValdation()) return true;


        //بيانات مالك النشاط
        if(getOwnerDataValdation()) return true;


        //بيانات القائم بإدارة النشاط
        if(getactivityManagerValdation()) return true;

        //نتيجة الاستعلام
        if(getAskingData()) return true;

return false;
    }

    private void SetErrorNullForTextView() {
        binding.Pradiao.setError(null);
        binding.MagmegmentPayedTrue.setError(null);
        binding.ResonOfMoneeyTrue.setError(null);
        binding.PostionTypeTrue.setError(null);
        binding.ResietTrue.setError(null);
        binding.NameResietTrue.setError(null);
        binding.JobAndotheTrue.setError(null);
        binding.JobTypeTrue.setError(null);
        binding.AnotherJobType.setError(null);
        binding.radioJobTypeMTrue.setError(null);
        binding.AgentTrue.setError(null);
        binding.AddersTrue.setError(null);
        binding.tiemCycleTrue.setError(null);
        binding.OwnerTrue.setError(null);
        binding.MangamentTrue.setError(null);
        binding.RepartionTrue.setError(null);
        binding.adderLoactionTrue.setError(null);
        binding.SorursTrue.setError(null);
    }

    private boolean getAskingData() {
        if (binding.radioLieesnasCH.isChecked()) {
            formdata.business_result_activity = 0;
        } else if (binding.radioLieesnasCH.isChecked()) {
            formdata.business_result_activity = 1;
        }else{
            setErrorTextView(binding.AgentTrue,getContext());
            return true;
        }

        if (binding.radioMachineLiesnseCH.isChecked()) {
            formdata.business_result_headquarters = 0;
        } else if (binding.radioMachineLiesnseCH2.isChecked()) {
            formdata.business_result_headquarters = 1;
        }else{
            setErrorTextView(binding.AddersTrue,getContext());
            return true;
        }

        if (binding.radioValueCH.isChecked()) {
            formdata.business_result_keeping_appointment = 1;
        } else if (binding.radioValueCH2.isChecked()) {
            formdata.business_result_keeping_appointment = 2;
        } else if (binding.radioValueCH3.isChecked()) {
            formdata.business_result_keeping_appointment = 3;
        }else{
            setErrorTextView(binding.tiemCycleTrue,getContext());
            return true;
        }

        if (binding.radioOwnerCH.isChecked()) {
            formdata.business_result_owner_data = 0;
        } else if (binding.radioOwnerCH2.isChecked()) {
            formdata.business_result_owner_data = 1;
        }else{
            setErrorTextView(binding.OwnerTrue,getContext());
            return true;
        }
        if (binding.radioRaptaionCH.isChecked()) {
            formdata.business_result_administrator_data = 0;
        } else if (binding.radioRaptaionCH2.isChecked()) {
            formdata.business_result_administrator_data = 1;
        }else{
            setErrorTextView(binding.MangamentTrue,getContext());
            return true;
        }

        if (binding.radioReputationCH.isChecked()) {
            formdata.business_result_ustomer_heard = 1;
        } else if (binding.radioReputationCH2.isChecked()) {
            formdata.business_result_ustomer_heard = 2;
        } else if (binding.radioReputationCH3.isChecked()) {
            formdata.business_result_ustomer_heard = 3;
        }
        else{
            setErrorTextView(binding.RepartionTrue,getContext());
            return true;
        }


        if (binding.radioEnterpriseCH.isChecked()) {
            formdata.business_result_origin_reputation = 1;
        } else if (binding.radioEnterpriseCH2.isChecked()) {
            formdata.business_result_origin_reputation = 2;
        } else if (binding.radioEnterpriseCH3.isChecked()) {
            formdata.business_result_origin_reputation = 3;
        }else{
            setErrorTextView(binding.adderLoactionTrue,getContext());
            return true;
        }


        if (binding.radioSourseCH.isChecked()) {
            formdata.business_result_sources = 1;
        } else if (binding.radioSourseCH2.isChecked()) {
            formdata.business_result_sources = 2;
        } else if (binding.radioSourseCH3.isChecked()) {
            formdata.business_result_sources = 3;
        }
        else if (binding.radioSourseCH4.isChecked()) {
            formdata.business_result_sources = 4;
        }
        else{
            setErrorTextView(binding.SorursTrue,getContext());
            return true;
        }
      return false;
    }

    private boolean getactivityManagerValdation(){
        if (CheckInputfield(binding.fullNameForOwnerM, getContext())) return true;
        if (CheckInputfield(binding.NakeNameOwnerM, getContext())) return true;
        if (CheckInputfield(binding.NationalIDM, getContext())) return true;
        //عمل أخر
        if (binding.radioJobAntoherMCH.isChecked()) {
            formdata.activity_manager_another_job = 0;
        } else if (binding.radioJobAntoherMCH.isChecked()) {
            formdata.activity_manager_another_job = 1;
        }else{
            setErrorTextView(binding.AnotherJobType,getContext());
            return true;
        }

        //نوع العمل
        if (binding.radioJobTypeMCH.isChecked()) {
            formdata.activity_manager_employment_type = 1;
        } else if (binding.radioJobTypeMCH2.isChecked()) {
            formdata.activity_manager_employment_type = 2;
        }else{
            setErrorTextView(binding.radioJobTypeMTrue,getContext());
            return true;
        }
        if (CheckInputfield(binding.SalaryAverageM, getContext())) return true;
        getactivityManager();
        return false;
    }

    private void getactivityManager() {
        formdata.activity_manager_name = getValue(binding.fullNameForOwnerM.getText());
        formdata.activity_manager_nickname = getValue(binding.NakeNameOwnerM.getText());
        formdata.activity_manager_national_ID = getValue(binding.NationalIDM.getText());





        formdata.activity_manager_average_income = getValue(binding.SalaryAverageM.getText());

    }

    private boolean getOwnerDataValdation(){

        if (CheckInputfield(binding.fullNameForOwner, getContext())) return true;
        if (CheckInputfield(binding.NakeNameOwner, getContext())) return true;
        if (CheckInputfield(binding.NationalID, getContext())) return true;

        //عمل أخر
        if (binding.radioJobAntoherCH.isChecked()) {
            formdata.activity_owner_another_job = 0;
        } else if (binding.radioJobAntoherCH2.isChecked()) {
            formdata.activity_owner_another_job = 1;
        }else{
            setErrorTextView(binding.JobAndotheTrue,getContext());
            return true;
        }

        //نوع العمل
        if (binding.radioJobTypeCH.isChecked()) {
            formdata.activity_owner_employment_type = 0;
        } else if (binding.radioJobTypeCH2.isChecked()) {
            formdata.activity_owner_employment_type = 1;
        }else{
            setErrorTextView(binding.JobTypeTrue,getContext());
            return true;
        }

        if (CheckInputfield(binding.SalaryAverage, getContext())) return true;
        if (CheckInputfield(binding.FamilyMammber, getContext())) return true;
        getOwnerData();
        return false;
    }

    private void getOwnerData() {
        formdata.activity_owner_name = getValue(binding.fullNameForOwner.getText());
        formdata.activity_owner_nickname = getValue(binding.NakeNameOwner.getText());
        formdata.activity_owner_national_ID = getValue(binding.NationalID.getText());




        formdata.activity_owner_average_income = getValue(binding.SalaryAverage.getText());
        formdata.activity_owner_family_number = getValue(binding.FamilyMammber.getText());

    }

    private boolean getRacietAttachValdation(){
        //نوع إيصال المرافق
        if (binding.radioUtilityReceiptCH.isChecked()) {
            formdata.attached_type = 1;
        } else if (binding.radioUtilityReceiptCH2.isChecked()) {
            formdata.attached_type = 2;
        } else if (binding.radioUtilityReceiptCH3.isChecked()) {
            formdata.attached_type = 3;
        }else{
            setErrorTextView(binding.ResietTrue,getContext());
            return true;
        }

        if (CheckInputfield(binding.counterNumber, getContext())) return true;
        if (CheckInputfield(binding.PlateNumber, getContext())) return true;
        if (CheckInputfield(binding.AverageMonthly, getContext())) return true;
        //اسم المستفيد بالإيصال
        if (binding.radioNameOfRecipientCH.isChecked()) {
            formdata.attached_average_beneficiary_name = 1;
        } else if (binding.radioNameOfRecipientCH2.isChecked()) {
            formdata.attached_average_beneficiary_name = 2;
        } else if (binding.radioNameOfRecipientCH3.isChecked()) {
            formdata.attached_average_beneficiary_name = 3;
        }else{
            setErrorTextView(binding.NameResietTrue,getContext());
            return true;
        }

        getRacietAttach();
        return false;
    }

    private void getRacietAttach() {


        formdata.attached_counter_number = getValue(binding.counterNumber.getText());
        formdata.attached_plate_number = getValue(binding.PlateNumber.getText());
        formdata.attached_average_monthly_consumption = getValue(binding.AverageMonthly.getText());


    }

    private boolean getActvityPlaceValdation(){
        boolean RenatStatus=false;
        //نوع الحيازة
        if (binding.radioButton.isChecked()) {
            formdata.headquarters_possession_type = 1;
            RenatStatus=false;

        } else if (binding.radioButton2.isChecked()) {
            formdata.headquarters_possession_type = 2;
            RenatStatus=false;

        } else if (binding.radioButton3.isChecked()) {
            formdata.headquarters_possession_type = 3;
            RenatStatus=true;

        } else if (binding.radioButton4.isChecked()) {
            formdata.headquarters_possession_type = 4;
            RenatStatus=true;

        } else if (binding.radioButton5.isChecked()) {
            RenatStatus=true;
            formdata.headquarters_possession_type = 5;

        }else{
            Constans.setErrorTextView(binding.PostionTypeTrue,getContext());
            return true;
        }

        if(RenatStatus)
        {
            if (CheckInputfield(binding.OwnerName, getContext())) return true;
            if (CheckInputfield(binding.RelationshipClient, getContext())) return true;
            if (CheckInputfield(binding.DataeOfending, getContext())) return true;
        }

        if (CheckInputfield(binding.buildingnumber, getContext())) return true;
        if (CheckInputfield(binding.StreatName, getContext())) return true;
        if (CheckInputfield(binding.Nieporehod, getContext())) return true;
        if (CheckInputfield(binding.Ciety, getContext())) return true;
        if (CheckInputfield(binding.Twon, getContext())) return true;
        if (CheckInputfield(binding.SpeiclSine, getContext())) return true;
        if (CheckInputfield(binding.HeadquartersArea, getContext())) return true;
        getActvityPlace();
        return false;
    }

    private void getActvityPlace() {


        //في حالة الايجار
        formdata.headquarters_owner_name = getValue(binding.OwnerName.getText());
        formdata.headquarters_client_relationship = getValue(binding.RelationshipClient.getText());
        formdata.headquarters_to = getValue(binding.DataeOfending.getText());

        //عنوان مقر النشاط
        formdata.headquarters_building_number = getValue(binding.buildingnumber.getText());
        formdata.headquarters_street_name = getValue(binding.StreatName.getText());
        formdata.headquarters_neighborhood = getValue(binding.Nieporehod.getText());
        formdata.headquarters_city = getValue(binding.Ciety.getText());
        formdata.headquarters_governorate = getValue(binding.Twon.getText());
        formdata.headquarters_special_marque = getValue(binding.SpeiclSine.getText());
        formdata.headquarters_area = getValue(binding.HeadquartersArea.getText());

    }

    private boolean getCommerialDataValdation()
    {
        if (CheckInputfield(binding.CommercialRegistrationNo, getContext())) return true;
        if (CheckInputfield(binding.taxCardNumber, getContext())) return true;
        if (CheckInputfield(binding.TaxesErrand, getContext())) return true;
        if (CheckInputfield(binding.ReleaseDateComarial, getContext())) return true;
        if (CheckInputfield(binding.ExpiryDate, getContext())) return true;
        getCommerialData();
        return false;
    }

    private void getCommerialData() {
        formdata.commercial_registration_No = getValue(binding.CommercialRegistrationNo.getText());
        formdata.commercial_tax_card_number = getValue(binding.taxCardNumber.getText());
        formdata.commercial_Taxes_errand = getValue(binding.TaxesErrand.getText());
        formdata.commercial_form_date = getValue(binding.ReleaseDateComarial.getText());
        formdata.commercial_to_date = getValue(binding.ExpiryDate.getText());
    }

    private boolean getActvityValdation()
    {
        if (CheckInputfield(binding.ComarialName, getContext())) return true;
        if (CheckInputfield(binding.ComaanName, getContext())) return true;
        if (CheckInputfield(binding.ComarialDate, getContext())) return true;
        if (CheckInputfield(binding.ComarialType, getContext())) return true;
        if (CheckInputfield(binding.NumberOfBransh, getContext())) return true;
        if (CheckInputfield(binding.NumberOfEmployers, getContext())) return true;
        boolean RadioStatus=false;
        //يوجد شركاء بالنشاط
        if (binding.radioPartnerCH.isChecked()) {
            formdata.business_partner = 0;
            RadioStatus=false;
        } else if (binding.radioPartnerCH2.isChecked()) {
            formdata.business_partner = 1;
            RadioStatus=true;
        }
        else{
            setErrorTextView(binding.Pradiao,getContext());
            return true;
        }
        if(RadioStatus){
            if (CheckInputfield(binding.NumberOfPartner, getContext())) return true;

            if (CheckInputfield(binding.PartnerPersange, getContext())) return true;

            //يتقاضي مقابل للإدارة
            if (binding.radioPartnerMangmentCH.isChecked()) {
                formdata.business_gets_paid = 0;
            } else if (binding.radioPartnerMangmentCH2.isChecked()) {
                formdata.business_gets_paid = 1;
            }
            else {
                setErrorTextView(binding.MagmegmentPayedTrue,getContext());
                return true;
            }
        }



        //الغرض من التمويل
        if (binding.radioInvestmenttCH.isChecked()) {
            formdata.business_purpose_funding = 1;
        } else if (binding.radioInvestmenttCH2.isChecked()) {
            formdata.business_purpose_funding = 2;
        } else if (binding.radioInvestmentCH3.isChecked()) {
            formdata.business_purpose_funding = 3;
        }
        else{
            setErrorTextView(binding.ResonOfMoneeyTrue,getContext());
            return true;
        }
        getActvityData();
        return false;
    }

    private void getActvityData() {
        formdata.business_name = getValue(binding.ComarialName.getText());
        formdata.business_common_name = getValue(binding.ComaanName.getText());
        formdata.business_start_date = getValue(binding.ComarialDate.getText());
        formdata.business_type = getValue(binding.ComarialType.getText());
        formdata.business_branches_number = getValue(binding.NumberOfBransh.getText());
        formdata.business_workers_number = getValue(binding.NumberOfEmployers.getText());
        formdata.business_partner_number = getValue(binding.NumberOfPartner.getText());
        formdata.business_customer_share = getValue(binding.PartnerPersange.getText());


    }

    private boolean getAgentDataValdion() {
        // بيانات العميل
        if (CheckInputfield(binding.FullName, getContext())) return true;
        if (CheckInputfield(binding.NainolIdNumber, getContext())) return true;
        if (CheckInputfield(binding.PerathData, getContext())) return true;
        if (CheckInputfield(binding.PhonNumber, getContext())) return true;

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

    private void SetDataUI() {
        if (itsNotNull(formdata)) {
            binding.Sumbit.setVisibility(View.GONE);
            binding.DeletSumbit.setVisibility(View.VISIBLE);

            //بيانات العميل
            SetAgentData();

            //بيانات النشاط
            SetActvityData();

            //بيانات تجارية
            SetCommerialData();

            //بيانات مقر النشاط
            SetActvityPlace();

            //بيانات مرافق النشاط
            SetRacietAttach();

            //بيانات مالك النشاط
            setOwnerData();

            //بيانات القائم بإدارة النشاط
            SetactivityManager();

            //نتيجة الاستعلام
            SetAskingData();
        }
    }

    private void SetAskingData() {
        if (itsNotNull(formdata.business_result_activity)) {
            if (getValueOfboleaan(formdata.business_result_activity) == 0) {
                binding.radioLieesnasCH.setChecked(true);
            } else if (getValueOfboleaan(formdata.business_result_activity) == 1) {
                binding.radioLieesnasCH2.setChecked(true);
            }
        }

        if (itsNotNull(formdata.business_result_headquarters)) {
            if (getValueOfboleaan(formdata.business_result_headquarters) == 0) {
                binding.radioMachineLiesnseCH.setChecked(true);
            } else if (getValueOfboleaan(formdata.business_result_headquarters) == 1) {
                binding.radioMachineLiesnseCH2.setChecked(true);
            }
        }

        if (itsNotNull(formdata.business_result_keeping_appointment)) {
            if (getValueOfboleaan(formdata.business_result_keeping_appointment) == 1) {
                binding.radioValueCH.setChecked(true);
            } else if (getValueOfboleaan(formdata.business_result_keeping_appointment) == 2) {
                binding.radioValueCH2.setChecked(true);
            } else if (getValueOfboleaan(formdata.business_result_keeping_appointment) == 3) {
                binding.radioValueCH3.setChecked(true);
            }
        }

        if (itsNotNull(formdata.business_result_owner_data)) {
            if (getValueOfboleaan(formdata.business_result_owner_data) == 0) {
                binding.radioOwnerCH.setChecked(true);
            } else if (getValueOfboleaan(formdata.business_result_owner_data) == 1) {
                binding.radioOwnerCH2.setChecked(true);
            }
        }

        if (itsNotNull(formdata.business_result_administrator_data)) {
            if (getValueOfboleaan(formdata.business_result_administrator_data) == 0) {
                binding.radioRaptaionCH.setChecked(true);
            } else if (getValueOfboleaan(formdata.business_result_administrator_data) == 1) {
                binding.radioRaptaionCH2.setChecked(true);
            }
        }

        if (itsNotNull(formdata.business_result_ustomer_heard)) {
            if (getValueOfboleaan(formdata.business_result_ustomer_heard) == 1) {
                binding.radioReputationCH.setChecked(true);
            } else if (getValueOfboleaan(formdata.business_result_ustomer_heard) == 2) {
                binding.radioReputationCH2.setChecked(true);
            } else if (getValueOfboleaan(formdata.business_result_ustomer_heard) == 3) {
                binding.radioReputationCH3.setChecked(true);
            }
        }

        if (itsNotNull(formdata.business_result_origin_reputation)) {
            if (getValueOfboleaan(formdata.business_result_origin_reputation) == 1) {
                binding.radioEnterpriseCH.setChecked(true);
            } else if (getValueOfboleaan(formdata.business_result_origin_reputation) == 2) {
                binding.radioEnterpriseCH2.setChecked(true);
            } else if (getValueOfboleaan(formdata.business_result_origin_reputation) == 3) {
                binding.radioEnterpriseCH3.setChecked(true);
            }
        }

        if (itsNotNull(formdata.business_result_sources)) {
            if (getValueOfboleaan(formdata.business_result_sources) == 1) {
                binding.radioSourseCH.setChecked(true);
            } else if (getValueOfboleaan(formdata.business_result_sources) == 2) {
                binding.radioSourseCH2.setChecked(true);
            } else if (getValueOfboleaan(formdata.business_result_sources) == 3) {
                binding.radioSourseCH3.setChecked(true);
            } else if (getValueOfboleaan(formdata.business_result_sources) == 4) {
                binding.radioSourseCH4.setChecked(true);
            }
        }


        //المرفقات
    }

    private void SetactivityManager() {
        binding.fullNameForOwnerM.setText(getValue(formdata.activity_manager_name));
        binding.NakeNameOwnerM.setText(getValue(formdata.activity_manager_nickname));
        binding.NationalIDM.setText(getValue(formdata.activity_manager_national_ID));
        //عمل أخر
        if (itsNotNull(formdata.activity_manager_another_job)) {
            if (getValueOfboleaan(formdata.activity_manager_another_job) == 0) {
                binding.radioJobAntoherMCH.setChecked(true);
            } else if (getValueOfboleaan(formdata.activity_manager_another_job) == 1) {
                binding.radioJobAntoherMCH2.setChecked(true);
            }
        }
        //نوع العمل
        if (itsNotNull(formdata.activity_manager_employment_type)) {
            if (getValueOfboleaan(formdata.activity_manager_employment_type) == 1) {
                binding.radioJobTypeMCH.setChecked(true);
            } else if (getValueOfboleaan(formdata.activity_manager_employment_type) == 2) {
                binding.radioJobTypeMCH2.setChecked(true);
            }
        }

        binding.SalaryAverageM.setText(getValue(formdata.activity_manager_average_income));

    }

    private void setOwnerData() {
        binding.fullNameForOwner.setText(getValue(formdata.activity_owner_name));
        binding.NakeNameOwner.setText(getValue(formdata.activity_owner_nickname));
        binding.NationalID.setText(getValue(formdata.activity_owner_national_ID));
        //عمل أخر
        if (itsNotNull(formdata.activity_owner_another_job)) {
            if (getValueOfboleaan(formdata.activity_owner_another_job) == 0) {
                binding.radioJobAntoherCH.setChecked(true);
            } else if (getValueOfboleaan(formdata.activity_owner_another_job) == 1) {
                binding.radioJobAntoherCH2.setChecked(true);
            }
        }
        //نوع العمل
        if (itsNotNull(formdata.activity_owner_employment_type)) {
            if (getValueOfboleaan(formdata.activity_owner_employment_type) == 1) {
                binding.radioJobTypeCH.setChecked(true);
            } else if (getValueOfboleaan(formdata.activity_owner_employment_type) == 2) {
                binding.radioJobTypeCH2.setChecked(true);
            }
        }

        binding.SalaryAverage.setText(getValue(formdata.activity_owner_average_income));
        binding.FamilyMammber.setText(getValue(formdata.activity_owner_family_number));
    }

    private void SetRacietAttach() {
        //نوع إيصال المرافق
        if (itsNotNull(formdata.attached_type)) {
            if (getValueOfboleaan(formdata.attached_type) == 1) {
                binding.radioUtilityReceiptCH.setChecked(true);

            } else if (getValueOfboleaan(formdata.attached_type) == 2) {

                binding.radioUtilityReceiptCH2.setChecked(true);


            } else if (getValueOfboleaan(formdata.attached_type) == 3) {

                binding.radioUtilityReceiptCH3.setChecked(true);
            }
        }
        binding.counterNumber.setText(getValue(formdata.attached_counter_number));
        binding.PlateNumber.setText(getValue(formdata.attached_plate_number));
        binding.AverageMonthly.setText(getValue(formdata.attached_average_monthly_consumption));
        //اسم المستفيد بالإيصال
        if (itsNotNull(formdata.attached_average_beneficiary_name)) {
            if (getValueOfboleaan(formdata.attached_average_beneficiary_name) == 1) {
                binding.radioNameOfRecipientCH.setChecked(true);

            } else if (getValueOfboleaan(formdata.attached_average_beneficiary_name) == 2) {

                binding.radioNameOfRecipientCH2.setChecked(true);


            } else if (getValueOfboleaan(formdata.attached_average_beneficiary_name) == 3) {

                binding.radioNameOfRecipientCH3.setChecked(true);
            }
        }
    }




    private void SetActvityPlace() {
        //نوع الحيازة
        if (itsNotNull(formdata.headquarters_possession_type)) {
            if (getValueOfboleaan(formdata.headquarters_possession_type) == 1) {
                binding.radioButton.setChecked(true);
            } else if (getValueOfboleaan(formdata.headquarters_possession_type) == 2) {
                binding.radioButton2.setChecked(true);
            } else if (getValueOfboleaan(formdata.headquarters_possession_type) == 3) {
                binding.radioButton3.setChecked(true);
            }
            else if (getValueOfboleaan(formdata.headquarters_possession_type) == 4) {
                binding.radioButton4.setChecked(true);
            }
            else if (getValueOfboleaan(formdata.headquarters_possession_type) == 5) {
                binding.radioButton5.setChecked(true);
            }
        }
        //في حالة الايجار
        binding.OwnerName.setText(getValue(formdata.headquarters_owner_name));
        binding.RelationshipClient.setText(getValue(formdata.headquarters_client_relationship));
        binding.DataeOfending.setText(getValue(formdata.headquarters_to));
        //عنوان مقر النشاط
        binding.buildingnumber.setText(getValue(formdata.headquarters_building_number));
        binding.StreatName.setText(getValue(formdata.headquarters_street_name));
        binding.Nieporehod.setText(getValue(formdata.headquarters_neighborhood));
        binding.Ciety.setText(getValue(formdata.headquarters_city));
        binding.Twon.setText(getValue(formdata.headquarters_governorate));
        binding.SpeiclSine.setText(getValue(formdata.headquarters_special_marque));
        binding.HeadquartersArea.setText(getValue(formdata.headquarters_area));
    }

    private void SetCommerialData() {
        binding.CommercialRegistrationNo.setText(getValue(formdata.commercial_registration_No));
        binding.taxCardNumber.setText(getValue(formdata.commercial_tax_card_number));
        binding.TaxesErrand.setText(getValue(formdata.commercial_Taxes_errand));
        binding.ReleaseDateComarial.setText(getValue(formdata.commercial_form_date));
        binding.ExpiryDate.setText(getValue(formdata.commercial_to_date));
    }

    private void SetActvityData() {
        binding.ComarialName.setText(getValue(formdata.business_name));
        binding.ComaanName.setText(getValue(formdata.business_common_name));
        binding.ComarialDate.setText(getValue(formdata.business_start_date));
        binding.ComarialType.setText(getValue(formdata.business_type));
        binding.NumberOfBransh.setText(getValue(formdata.business_branches_number));
        binding.NumberOfEmployers.setText(getValue(formdata.business_workers_number));

        //يوجد شركاء بالنشاط
        if (itsNotNull(formdata.business_partner)) {
            if (getValueOfboleaan(formdata.business_partner) == 0) {
                binding.radioPartnerCH.setChecked(true);
            } else if (getValueOfboleaan(formdata.business_partner) == 1) {
                binding.radioPartnerCH2.setChecked(true);
            }
        }

        binding.NumberOfPartner.setText(getValue(formdata.business_partner_number));
        binding.PartnerPersange.setText(getValue(formdata.business_customer_share));


        //يتقاضي مقاب للإدارة
        if (itsNotNull(formdata.business_gets_paid)) {
            if (getValueOfboleaan(formdata.business_gets_paid) == 0) {
                binding.radioPartnerMangmentCH.setChecked(true);
            } else if (getValueOfboleaan(formdata.business_gets_paid) == 1) {
                binding.radioPartnerMangmentCH2.setChecked(true);
            }
        }
        //الغرض من التمويل
        if (itsNotNull(formdata.business_purpose_funding)) {
            if (getValueOfboleaan(formdata.business_purpose_funding) == 1) {
                binding.radioInvestmenttCH.setChecked(true);
            } else if (getValueOfboleaan(formdata.business_purpose_funding) == 2) {
                binding.radioInvestmenttCH2.setChecked(true);
            } else if (getValueOfboleaan(formdata.business_purpose_funding) == 3) {
                binding.radioInvestmentCH3.setChecked(true);
            }
        }
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



    private void SetVisbilty() {
        binding.radioPartnerCH2.setOnCheckedChangeListener((compoundButton, b) -> {
            if (binding.radioPartnerCH2.isChecked()) {
                binding.NumberOfPartnervispley.setVisibility(View.VISIBLE);
                binding.PartnerPersangeVispily.setVisibility(View.VISIBLE);
                binding.Choiess.setVisibility(View.VISIBLE);
            }
        });
        binding.radioPartnerCH.setOnCheckedChangeListener((compoundButton, b) -> {
            if (binding.radioPartnerCH.isChecked()) {
                binding.NumberOfPartnervispley.setVisibility(View.GONE);
                binding.PartnerPersangeVispily.setVisibility(View.GONE);
                binding.Choiess.setVisibility(View.GONE);
            }
        });
    }

    private void SetRadiobutttions() {
        // All this Code to make shoure whant Radio Button is selected The other is Off
        binding.radioButton.setOnCheckedChangeListener((compoundButton, b) -> {

            if (binding.radioButton.isChecked()) {
                binding.RentColaet.setVisibility(View.GONE);
                if (binding.radioButton2.isChecked() || binding.radioButton3.isChecked() || binding.radioButton4.isChecked()
                        || binding.radioButton5.isChecked()) {

                    binding.radioButton2.setChecked(false);
                    binding.radioButton3.setChecked(false);
                    binding.radioButton4.setChecked(false);
                    binding.radioButton5.setChecked(false);
                }

            }

        });
        binding.radioButton2.setOnCheckedChangeListener((compoundButton, b) -> {
            if (binding.radioButton2.isChecked()) {
                binding.RentColaet.setVisibility(View.GONE);
                if (binding.radioButton.isChecked() || binding.radioButton3.isChecked() || binding.radioButton4.isChecked()
                        || binding.radioButton5.isChecked()) {
                    binding.radioButton.setChecked(false);
                    binding.radioButton3.setChecked(false);
                    binding.radioButton4.setChecked(false);
                    binding.radioButton5.setChecked(false);
                }

            }

        });
        binding.radioButton3.setOnCheckedChangeListener((compoundButton, b) -> {
            if (binding.radioButton3.isChecked()) {
                binding.RentColaet.setVisibility(View.VISIBLE);
                if (binding.radioButton.isChecked() || binding.radioButton2.isChecked() || binding.radioButton4.isChecked()
                        || binding.radioButton5.isChecked()) {
                    binding.radioButton.setChecked(false);
                    binding.radioButton2.setChecked(false);
                    binding.radioButton4.setChecked(false);
                    binding.radioButton5.setChecked(false);
                }

            }
        });
        binding.radioButton4.setOnCheckedChangeListener((compoundButton, b) -> {
            if (binding.radioButton4.isChecked()) {
                binding.RentColaet.setVisibility(View.VISIBLE);
                if (binding.radioButton.isChecked() || binding.radioButton2.isChecked() || binding.radioButton3.isChecked()
                        || binding.radioButton5.isChecked()) {
                    binding.radioButton.setChecked(false);
                    binding.radioButton2.setChecked(false);
                    binding.radioButton3.setChecked(false);
                    binding.radioButton5.setChecked(false);
                }

            }
        });
        binding.radioButton5.setOnCheckedChangeListener((compoundButton, b) -> {
            if (binding.radioButton5.isChecked()) {
                binding.RentColaet.setVisibility(View.VISIBLE);
                if (binding.radioButton.isChecked() || binding.radioButton2.isChecked() || binding.radioButton4.isChecked()
                        || binding.radioButton3.isChecked()) {
                    binding.radioButton.setChecked(false);
                    binding.radioButton2.setChecked(false);
                    binding.radioButton4.setChecked(false);
                    binding.radioButton3.setChecked(false);
                }

            }

        });

    }




}