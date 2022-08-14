package com.example.taehaed.Screens.Fragment;

import static com.example.taehaed.Constans.CheckInputfield;
import static com.example.taehaed.Constans.DESCRIBABLE_KEY;
import static com.example.taehaed.Constans.getValue;
import static com.example.taehaed.Constans.getValueOfboleaan;
import static com.example.taehaed.Constans.itsNotNull;
import static com.example.taehaed.Constans.setAlertMeaage;
import static com.example.taehaed.Constans.setErrorTextView;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.taehaed.Constans;
import com.example.taehaed.Model.TaehaedVModel;
import com.example.taehaed.Pojo.FormReuest.FormData;
import com.example.taehaed.Pojo.NoteBodey;
import com.example.taehaed.databinding.FragmentNinthSuppliersBinding;

public class NinthSuppliersFragment extends Fragment {

    private FragmentNinthSuppliersBinding binding;
    private FormData formdata;
    private AlertDialog alertDialog;
    private TaehaedVModel taehaedVModel;
    private int servier_id;

    public NinthSuppliersFragment() {

    }


    public static NinthSuppliersFragment newInstance(int id) {
        NinthSuppliersFragment fragment = new NinthSuppliersFragment();
        Bundle args = new Bundle();
        args.putInt(Constans.IdkeyFrgment, id);
        fragment.setArguments(args);
        return fragment;
    }

    public static NinthSuppliersFragment newInstance(int id, FormData formData) {
        NinthSuppliersFragment fragment = new NinthSuppliersFragment();
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
        binding = FragmentNinthSuppliersBinding.inflate(inflater);
        taehaedVModel = new ViewModelProvider(this).get(TaehaedVModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.goBack.setOnClickListener(view1 -> {
            getActivity().onBackPressed();
        });
        SetTheRadioButton();
        SetDate();
        SetDataUI();
        binding.Sumbit.setOnClickListener(view1 -> {
            formdata = new FormData();


           if( getDataUI()) return ;
            alertDialog = setAlertMeaage("جاري ارسال البيانات", getContext());
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
            alertDialog = setAlertMeaage("جاري حذف الاستعلام", getContext());
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


    private boolean getDataUI() {
        formdata.request_service_id = servier_id;

        setErrorNullforTextView();
        //بيانات العميل
        if(getAgentDataValdion())return true;

        //بيانات النشاط
        if(getActvityDataValdion())return true;

        //بيانات تجارية
        if(getCommerialDataValdion())return true;

        //بيانات مقر النشاط
        if(getActvityPlaceValdion())return true;

        //بيانات مرافق النشاط
        if(getRacietAttachValdion()) return true;

        //بيانات مالك النشاط
        if(getOwnerDataValdion())return true;



        //نتيجة الاستعلام
        if(getAskingData())return true;
        return false;

    }

    private void setErrorNullforTextView() {
        binding.PathnerTrue.setError(null);
        binding.getingPayTrue.setError(null);
        binding.PoruberOFfoundingTrue.setError(null);
        binding.DatatOfPlaceActivitrue.setError(null);
        binding.ReaisetTrue.setError(null);
        binding.OwnerMoneyNameTrue.setError(null);
        binding.AnotherJobTrue.setError(null);
        binding.JobTypeTrue.setError(null);
        binding.PriceTrue.setError(null);
        binding.TimeDilerdTrue.setError(null);
        binding.QulatyTrue.setError(null);
        binding.ToolsTrue.setError(null);
        binding.AddersTrue.setError(null);
        binding.AppiltyTrue.setError(null);
        binding.MangemntTimeTrue.setError(null);
        binding.RepartionTrue.setError(null);
        binding.SalaryStwaionTrue.setError(null);
        binding.PformensPerivoseTrue.setError(null);
        binding.OperationTrue.setError(null);
        binding.BackupAndtrianTrue.setError(null);
        binding.RealtionTrue.setError(null);
        binding.ConnetctionTrue.setError(null);
        binding.AboutTrue.setError(null);
        binding.JobInvieteTrue.setError(null);
        binding.JobPeriveisTrue.setError(null);

    }

    private boolean getAskingData() {

        if (binding.radioLieesnasCH.isChecked()) {
            formdata.supplier_result_delivery_time = 1;
        } else if (binding.radioLieesnasCH2.isChecked()) {
            formdata.supplier_result_delivery_time = 2;
        } else if (binding.radioLieesnasCH3.isChecked()) {
            formdata.supplier_result_delivery_time = 3;
        }else{
            setErrorTextView(binding.PriceTrue,getContext());
            return true;
        }

        if (binding.radioMachineLiesnseCH.isChecked()) {
            formdata.supplier_result_net_price = 1;
        } else if (binding.radioMachineLiesnseCH2.isChecked()) {
            formdata.supplier_result_net_price = 2;
        }else{
            setErrorTextView(binding.TimeDilerdTrue,getContext());
            return true;
        }


        if (binding.radioValueCH.isChecked()) {
            formdata.supplier_result_quality_level = 1;
        } else if (binding.radioValueCH2.isChecked()) {
            formdata.supplier_result_quality_level = 2;
        } else if (binding.radioValueCH3.isChecked()) {
            formdata.supplier_result_quality_level = 3;
        } else if (binding.radioValueCH4.isChecked()) {
            formdata.supplier_result_quality_level = 4;
        }else{
            setErrorTextView(binding.QulatyTrue,getContext());
            return true;
        }

        if (binding.radioOwnerCH.isChecked()) {
            formdata.supplier_result_ability = 1;
        } else if (binding.radioOwnerCH2.isChecked()) {
            formdata.supplier_result_ability = 2;
        }else{
            setErrorTextView(binding.ToolsTrue,getContext());
            return true;
        }

        if (binding.radioRaptaionCH.isChecked()) {
            formdata.supplier_result_geographical_location = 1;
        } else if (binding.radioRaptaionCH2.isChecked()) {
            formdata.supplier_result_geographical_location = 2;
        } else if (binding.radioRaptaionCH3.isChecked()) {
            formdata.supplier_result_geographical_location = 3;
        }else{
            setErrorTextView(binding.AddersTrue,getContext());
            return true;
        }

        if (binding.radioReputationCH.isChecked()) {
            formdata.supplier_result_possibilities = 1;
        } else if (binding.radioReputationCH2.isChecked()) {
            formdata.supplier_result_possibilities = 2;
        }else{
            setErrorTextView(binding.AppiltyTrue,getContext());
            return true;
        }

        if (binding.radioEnterpriseCH.isChecked()) {
            formdata.supplier_result_management = 1;
        } else if (binding.radioEnterpriseCH2.isChecked()) {
            formdata.supplier_result_management = 2;
        } else if (binding.radioEnterpriseCH3.isChecked()) {
            formdata.supplier_result_management = 3;
        } else if (binding.radioEnterpriseCH4.isChecked()) {
            formdata.supplier_result_management = 4;
        }else{
            setErrorTextView(binding.MangemntTimeTrue,getContext());
            return true;
        }

        if (binding.radioSupplierCH.isChecked()) {
            formdata.supplier_result_reputation = 1;
        } else if (binding.radioSupplierCH2.isChecked()) {
            formdata.supplier_result_reputation = 2;
        } else if (binding.radioSupplierCH3.isChecked()) {
            formdata.supplier_result_reputation = 3;
        }else{
            setErrorTextView(binding.RepartionTrue,getContext());
            return true;
        }

        if (binding.radioFinancialSituationCH.isChecked()) {
            formdata.supplier_result_financial_situation = 1;
        } else if (binding.radioFinancialSituationCH2.isChecked()) {
            formdata.supplier_result_financial_situation = 2;
        } else if (binding.radioFinancialSituationCH3.isChecked()) {
            formdata.supplier_result_financial_situation = 3;
        }else{
            setErrorTextView(binding.SalaryStwaionTrue,getContext());
            return true;
        }

        if (binding.radioPreviousPerformanceCH.isChecked()) {
            formdata.supplier_result_previous_performance = 1;
        } else if (binding.radioPreviousPerformanceCH2.isChecked()) {
            formdata.supplier_result_previous_performance = 2;
        } else if (binding.radioPreviousPerformanceCH3.isChecked()) {
            formdata.supplier_result_previous_performance = 3;
        }else{
            setErrorTextView(binding.PformensPerivoseTrue,getContext());
            return true;
        }


        if (binding.customerControlCH.isChecked()) {
            formdata.supplier_result_operations_monitor = 1;
        }
        else if (binding.customerControlCH2.isChecked()) {
            formdata.supplier_result_operations_monitor = 2;
        }else{
            setErrorTextView(binding.OperationTrue,getContext());
            return true;
        }


        if (binding.radioObesverCH.isChecked()) {
            formdata.supplier_result_training = 1;
        } else if (binding.radioObesverCH2.isChecked()) {
            formdata.supplier_result_training = 2;
        }else{
            setErrorTextView(binding.BackupAndtrianTrue,getContext());
            return true;
        }

        if (binding.radioSocialCH.isChecked()) {
            formdata.supplier_result_social_relations = 1;
        } else if (binding.radioSocialCH2.isChecked()) {
            formdata.supplier_result_social_relations = 2;
        } else if (binding.radioSocialCH3.isChecked()) {
            formdata.supplier_result_social_relations = 3;
        }else{
            setErrorTextView(binding.RealtionTrue,getContext());
            return true;
        }

        if (binding.radioCommunicationSystemCH.isChecked()) {
            formdata.supplier_result_communication_system = 1;
        } else if (binding.radioCommunicationSystemCH2.isChecked()) {
            formdata.supplier_result_communication_system = 2;
        } else if (binding.radioCommunicationSystemCH3.isChecked()) {
            formdata.supplier_result_communication_system = 3;
        }else{
            setErrorTextView(binding.ConnetctionTrue,getContext());
            return true;
        }


        if (binding.radioImpressionCH.isChecked()) {
            formdata.supplier_result_impression = 1;
        } else if (binding.radioImpressionCH2.isChecked()) {
            formdata.supplier_result_impression = 2;
        } else if (binding.radioImpressionCH3.isChecked()) {
            formdata.supplier_result_impression = 3;
        }else{
            setErrorTextView(binding.AboutTrue,getContext());
            return true;
        }



        if (binding.radioDesireCH.isChecked()) {
            formdata.supplier_result_do_work = 1;
        } else if (binding.radioDesireCH2.isChecked()) {
            formdata.supplier_result_do_work = 2;
        } else if (binding.radioDesireCH3.isChecked()) {
            formdata.supplier_result_do_work = 3;
        }
        else{
            setErrorTextView(binding.JobInvieteTrue,getContext());
            return true;
        }

        if (binding.radioJobsCH.isChecked()) {
            formdata.supplier_result_business_volume = 1;
        } else if (binding.radioJobsCH2.isChecked()) {
            formdata.supplier_result_business_volume = 2;
        } else if (binding.radioJobsCH3.isChecked()) {
            formdata.supplier_result_business_volume = 3;
        }
        else{
            setErrorTextView(binding.JobPeriveisTrue,getContext());
            return true;
        }

return false;
    }


    private boolean getOwnerDataValdion(){
        if (CheckInputfield(binding.fullNameForOwner, getContext())) return true;
        if (CheckInputfield(binding.NakeNameOwner, getContext())) return true;
        if (CheckInputfield(binding.NationalID, getContext())) return true;

        //عمل أخر
        if (binding.radioJobAntoherCH.isChecked()) {
            formdata.activity_owner_another_job = 0;
        } else if (binding.radioJobAntoherCH2.isChecked()) {
            formdata.activity_owner_another_job = 1;
        }else{
            setErrorTextView(binding.AnotherJobTrue,getContext());
            return true;
        }

        //نوع العمل
        if (binding.radioJobTypeCH.isChecked()) {
            formdata.activity_owner_employment_type = 0;
        } else if (binding.radioJobTypeCH2.isChecked()) {
            formdata.activity_owner_employment_type = 1;
        }
        else{
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

    private boolean getRacietAttachValdion(){
        //نوع إيصال المرافق
        if (binding.radioUtilityReceiptCH.isChecked()) {
            formdata.attached_type = 1;
        } else if (binding.radioUtilityReceiptCH2.isChecked()) {
            formdata.attached_type = 2;
        } else if (binding.radioUtilityReceiptCH3.isChecked()) {
            formdata.attached_type = 3;
        }else{
            setErrorTextView(binding.ReaisetTrue,getContext());
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
            setErrorTextView(binding.OwnerMoneyNameTrue,getContext());
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

    private boolean getActvityPlaceValdion() {
        //نوع الحيازة
        if (binding.radioPossession.isChecked()) {
            formdata.headquarters_possession_type = 1;
        } else if (binding.radioPossession2.isChecked()) {
            formdata.headquarters_possession_type = 2;
        } else if (binding.radioPossession3.isChecked()) {
            formdata.headquarters_possession_type = 3;
        } else if (binding.radioPossession4.isChecked()) {
            formdata.headquarters_possession_type = 4;
        } else if (binding.radioPossession5.isChecked()) {
            formdata.headquarters_possession_type = 5;
        }else{
            setErrorTextView(binding.DatatOfPlaceActivitrue,getContext());
            return true;
        }
        //في حالة الايجار
        if (CheckInputfield(binding.OwnerName, getContext())) return true;
        if (CheckInputfield(binding.RelationshipClient, getContext())) return true;
        if (CheckInputfield(binding.DataeOfending, getContext())) return true;

        //عنوان مقر النشاط
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

    private boolean getCommerialDataValdion(){
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

    private boolean getActvityDataValdion()
    {
        if (CheckInputfield(binding.ComarialName, getContext())) return true;
        if (CheckInputfield(binding.ComaanName, getContext())) return true;
        if (CheckInputfield(binding.ComarialDate, getContext())) return true;
        if (CheckInputfield(binding.ComarialType, getContext())) return true;
        if (CheckInputfield(binding.NumberOfBransh, getContext())) return true;
        if (CheckInputfield(binding.NumberOfEmployers, getContext())) return true;

        //يوجد شركاء بالنشاط
        if (binding.radioPartnerCH.isChecked()) {
            formdata.activity_supplier_partners = 0;
        } else if (binding.radioPartnerCH2.isChecked()) {
            formdata.activity_supplier_partners = 1;
        }
        else{
            setErrorTextView(binding.PathnerTrue,getContext());
            return true;
        }
        //يتقاضي مقابل للإدارة
        if (binding.radioPartnerMangmentCH.isChecked()) {
            formdata.activity_supplier_gets_paid = 0;
        } else if (binding.radioPartnerMangmentCH2.isChecked()) {
            formdata.activity_supplier_gets_paid = 1;
        }
        else{
            setErrorTextView(binding.getingPayTrue,getContext());
            return true;
        }

        //الغرض من التمويل
        if (binding.radioInvestmenttCH.isChecked()) {
            formdata.activity_supplier_purpose_funding = 1;
        } else if (binding.radioInvestmenttCH2.isChecked()) {
            formdata.activity_supplier_purpose_funding = 2;
        } else if (binding.radioInvestmentCH3.isChecked()) {
            formdata.activity_supplier_purpose_funding = 3;
        }
        else{
            setErrorTextView(binding.PoruberOFfoundingTrue,getContext());
            return true;
        }
        if (CheckInputfield(binding.NumberOfPartner, getContext())) return true;
        if (CheckInputfield(binding.PartnerPersange, getContext())) return true;
        getActvityData();
        return false;
    }
    private void getActvityData() {
        formdata.activity_supplier_name = getValue(binding.ComarialName.getText());
        formdata.activity_supplier_nickname = getValue(binding.ComaanName.getText());
        formdata.activity_supplier_form_date = getValue(binding.ComarialDate.getText());
        formdata.activity_supplier_to_type = getValue(binding.ComarialType.getText());
        formdata.activity_supplier_branches_number = getValue(binding.NumberOfBransh.getText());
        formdata.activity_supplier_workers_number = getValue(binding.NumberOfEmployers.getText());

        formdata.activity_supplier_partners_number = getValue(binding.NumberOfPartner.getText());
        formdata.activity_supplier_customer_share = getValue(binding.PartnerPersange.getText());
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

    private void SetDate() {
        Constans.setDateForInputText(binding.PerathData, getContext());
        Constans.setDateForInputText(binding.ComarialDate, getContext());
        Constans.setDateForInputText(binding.ReleaseDateComarial, getContext());
        Constans.setDateForInputText(binding.ExpiryDate, getContext());
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
            //تقيم المورد
            SetAskingData();

        }


    }

    private void SetAskingData() {
        if (itsNotNull(formdata.supplier_result_delivery_time)) {
            if (getValueOfboleaan(formdata.supplier_result_delivery_time) == 1) {
                binding.radioLieesnasCH.setChecked(true);
            } else if (getValueOfboleaan(formdata.supplier_result_delivery_time) == 2) {
                binding.radioLieesnasCH2.setChecked(true);
            } else if (getValueOfboleaan(formdata.supplier_result_delivery_time) == 3) {
                binding.radioLieesnasCH3.setChecked(true);
            }
        }
        if (itsNotNull(formdata.supplier_result_net_price)) {
            if (getValueOfboleaan(formdata.supplier_result_net_price) == 1) {
                binding.radioMachineLiesnseCH.setChecked(true);
            } else if (getValueOfboleaan(formdata.supplier_result_net_price) == 2) {
                binding.radioMachineLiesnseCH2.setChecked(true);
            }

        }
        if (itsNotNull(formdata.supplier_result_quality_level)) {
            if (getValueOfboleaan(formdata.supplier_result_quality_level) == 1) {
                binding.radioValueCH.setChecked(true);
            } else if (getValueOfboleaan(formdata.supplier_result_quality_level) == 2) {
                binding.radioValueCH2.setChecked(true);
            } else if (getValueOfboleaan(formdata.supplier_result_quality_level) == 3) {
                binding.radioValueCH3.setChecked(true);
            } else if (getValueOfboleaan(formdata.supplier_result_quality_level) == 4) {
                binding.radioValueCH4.setChecked(true);
            }

        }
        if (itsNotNull(formdata.supplier_result_ability)) {
            if (getValueOfboleaan(formdata.supplier_result_ability) == 1) {
                binding.radioOwnerCH.setChecked(true);
            } else if (getValueOfboleaan(formdata.supplier_result_ability) == 2) {
                binding.radioOwnerCH2.setChecked(true);
            }


        }
        if (itsNotNull(formdata.supplier_result_geographical_location)) {
            if (getValueOfboleaan(formdata.supplier_result_geographical_location) == 1) {
                binding.radioRaptaionCH.setChecked(true);
            } else if (getValueOfboleaan(formdata.supplier_result_geographical_location) == 2) {
                binding.radioRaptaionCH2.setChecked(true);
            } else if (getValueOfboleaan(formdata.supplier_result_geographical_location) == 3) {
                binding.radioRaptaionCH3.setChecked(true);
            }

        }
        if (itsNotNull(formdata.supplier_result_possibilities)) {
            if (getValueOfboleaan(formdata.supplier_result_possibilities) == 1) {
                binding.radioReputationCH.setChecked(true);
            } else if (getValueOfboleaan(formdata.supplier_result_possibilities) == 2) {
                binding.radioReputationCH2.setChecked(true);
            }


        }
        if (itsNotNull(formdata.supplier_result_management)) {
            if (getValueOfboleaan(formdata.supplier_result_management) == 1) {
                binding.radioEnterpriseCH.setChecked(true);
            } else if (getValueOfboleaan(formdata.supplier_result_management) == 2) {
                binding.radioEnterpriseCH2.setChecked(true);
            } else if (getValueOfboleaan(formdata.supplier_result_management) == 3) {
                binding.radioEnterpriseCH3.setChecked(true);
            } else if (getValueOfboleaan(formdata.supplier_result_management) == 4) {
                binding.radioEnterpriseCH4.setChecked(true);
            }


        }
        if (itsNotNull(formdata.supplier_result_reputation)) {
            if (getValueOfboleaan(formdata.supplier_result_reputation) == 1) {
                binding.radioSupplierCH.setChecked(true);
            } else if (getValueOfboleaan(formdata.supplier_result_reputation) == 2) {
                binding.radioSupplierCH2.setChecked(true);
            } else if (getValueOfboleaan(formdata.supplier_result_reputation) == 3) {
                binding.radioSupplierCH3.setChecked(true);
            }


        }
        //error -
        if (itsNotNull(formdata.supplier_result_financial_situation)) {
            if (getValueOfboleaan(formdata.supplier_result_financial_situation) == 1) {
                binding.radioFinancialSituationCH.setChecked(true);
            } else if (getValueOfboleaan(formdata.supplier_result_financial_situation) == 2) {
                binding.radioFinancialSituationCH2.setChecked(true);
            } else if (getValueOfboleaan(formdata.supplier_result_financial_situation) == 3) {
                binding.radioFinancialSituationCH3.setChecked(true);
            }


        }

        if (itsNotNull(formdata.supplier_result_previous_performance)) {
            if (getValueOfboleaan(formdata.supplier_result_previous_performance) == 1) {
                binding.radioPreviousPerformanceCH.setChecked(true);
            } else if (getValueOfboleaan(formdata.supplier_result_previous_performance) == 2) {
                binding.radioPreviousPerformanceCH2.setChecked(true);
            } else if (getValueOfboleaan(formdata.supplier_result_previous_performance) == 3) {
                binding.radioPreviousPerformanceCH3.setChecked(true);
            }
        }

        if (itsNotNull(formdata.supplier_result_operations_monitor)) {
            if (getValueOfboleaan(formdata.supplier_result_operations_monitor) == 1) {
                binding.customerControlCH.setChecked(true);
            } else if (getValueOfboleaan(formdata.supplier_result_operations_monitor) == 2) {
                binding.customerControlCH2.setChecked(true);
            }
        }

        if (itsNotNull(formdata.supplier_result_training)) {
            if (getValueOfboleaan(formdata.supplier_result_training) == 1) {
                binding.radioObesverCH.setChecked(true);
            } else if (getValueOfboleaan(formdata.supplier_result_training) == 2) {
                binding.radioObesverCH2.setChecked(true);
            }
        }
//error
        if (itsNotNull(formdata.supplier_result_social_relations)) {
            if (getValueOfboleaan(formdata.supplier_result_social_relations) == 1) {
                binding.radioSocialCH.setChecked(true);
            } else if (getValueOfboleaan(formdata.supplier_result_social_relations) == 2) {
                binding.radioSocialCH2.setChecked(true);
            } else if (getValueOfboleaan(formdata.supplier_result_social_relations) == 3) {
                binding.radioSocialCH3.setChecked(true);
            }
        }

        if (itsNotNull(formdata.supplier_result_communication_system)) {
            if (getValueOfboleaan(formdata.supplier_result_communication_system) == 1) {
                binding.radioCommunicationSystemCH.setChecked(true);
            } else if (getValueOfboleaan(formdata.supplier_result_communication_system) == 2) {
                binding.radioCommunicationSystemCH2.setChecked(true);
            } else if (getValueOfboleaan(formdata.supplier_result_communication_system) == 3) {
                binding.radioCommunicationSystemCH3.setChecked(true);
            }
        }

        if (itsNotNull(formdata.supplier_result_impression)) {
            if (getValueOfboleaan(formdata.supplier_result_impression) == 1) {
                binding.radioImpressionCH.setChecked(true);
            } else if (getValueOfboleaan(formdata.supplier_result_impression) == 2) {
                binding.radioImpressionCH2.setChecked(true);
            } else if (getValueOfboleaan(formdata.supplier_result_impression) == 3) {
                binding.radioImpressionCH3.setChecked(true);
            }
        }

        if (itsNotNull(formdata.supplier_result_do_work)) {
            if (getValueOfboleaan(formdata.supplier_result_do_work) == 1) {
                binding.radioDesireCH.setChecked(true);
            } else if (getValueOfboleaan(formdata.supplier_result_do_work) == 2) {
                binding.radioDesireCH2.setChecked(true);
            } else if (getValueOfboleaan(formdata.supplier_result_do_work) == 3) {
                binding.radioDesireCH3.setChecked(true);
            }
        }

        if (itsNotNull(formdata.supplier_result_business_volume)) {
            if (getValueOfboleaan(formdata.supplier_result_business_volume) == 1) {
                binding.radioJobsCH.setChecked(true);
            } else if (getValueOfboleaan(formdata.supplier_result_business_volume) == 2) {
                binding.radioJobsCH2.setChecked(true);
            } else if (getValueOfboleaan(formdata.supplier_result_business_volume) == 3) {
                binding.radioJobsCH3.setChecked(true);
            }
        }
    }

    private void SetActvityData() {
        binding.ComarialName.setText(getValue(formdata.activity_supplier_name));
        binding.ComaanName.setText(getValue(formdata.activity_supplier_nickname));
        binding.ComarialDate.setText(getValue(formdata.activity_supplier_form_date));
        binding.ComarialType.setText(getValue(formdata.activity_supplier_to_type));
        binding.NumberOfBransh.setText(getValue(formdata.activity_supplier_branches_number));
        binding.NumberOfEmployers.setText(getValue(formdata.activity_supplier_workers_number));

        //يوجد شركاء بالنشاط
        if (itsNotNull(formdata.activity_supplier_partners)) {
            if (getValueOfboleaan(formdata.activity_supplier_partners) == 0) {
                binding.radioPartnerCH.setChecked(true);
            } else if (getValueOfboleaan(formdata.activity_supplier_partners) == 1) {
                binding.radioPartnerCH2.setChecked(true);
            }
        }

        binding.NumberOfPartner.setText(getValue(formdata.activity_supplier_partners_number));
        binding.PartnerPersange.setText(getValue(formdata.activity_supplier_customer_share));


        //يتقاضي مقاب للإدارة
        if (itsNotNull(formdata.activity_supplier_gets_paid)) {
            if (getValueOfboleaan(formdata.activity_supplier_gets_paid) == 0) {
                binding.radioPartnerMangmentCH.setChecked(true);
            } else if (getValueOfboleaan(formdata.activity_supplier_gets_paid) == 1) {
                binding.radioPartnerMangmentCH2.setChecked(true);
            }
        }
        //الغرض من التمويل
        if (itsNotNull(formdata.activity_supplier_purpose_funding)) {
            if (getValueOfboleaan(formdata.activity_supplier_purpose_funding) == 1) {
                binding.radioInvestmenttCH.setChecked(true);
            } else if (getValueOfboleaan(formdata.activity_supplier_purpose_funding) == 2) {
                binding.radioInvestmenttCH2.setChecked(true);
            } else if (getValueOfboleaan(formdata.activity_supplier_purpose_funding) == 3) {
                binding.radioInvestmentCH3.setChecked(true);
            }
        }
    }

    private void SetactivityManager() {
        binding.fullNameForOwner.setText(getValue(formdata.activity_manager_name));
        binding.NakeNameOwner.setText(getValue(formdata.activity_manager_nickname));
        binding.NationalID.setText(getValue(formdata.activity_manager_national_ID));
        //عمل أخر
        if (itsNotNull(formdata.activity_manager_another_job)) {
            if (getValueOfboleaan(formdata.activity_manager_another_job) == 0) {
                binding.radioJobAntoherCH.setChecked(true);
            } else if (getValueOfboleaan(formdata.activity_manager_another_job) == 1) {
                binding.radioJobAntoherCH2.setChecked(true);
            }
        }
        //نوع العمل
        if (itsNotNull(formdata.activity_manager_employment_type)) {
            if (getValueOfboleaan(formdata.activity_manager_employment_type) == 1) {
                binding.radioJobTypeCH.setChecked(true);
            } else if (getValueOfboleaan(formdata.activity_manager_employment_type) == 2) {
                binding.radioJobTypeCH2.setChecked(true);
            }
        }

        binding.SalaryAverage.setText(getValue(formdata.activity_manager_average_income));
        binding.FamilyMammber.setText(getValue(formdata.activity_manager_owner_relationship));

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
                binding.radioPossession.setChecked(true);
            } else if (getValueOfboleaan(formdata.headquarters_possession_type) == 2) {
                binding.radioPossession2.setChecked(true);
            } else if (getValueOfboleaan(formdata.headquarters_possession_type) == 3) {
                binding.radioPossession3.setChecked(true);
            } else if (getValueOfboleaan(formdata.headquarters_possession_type) == 4) {
                binding.radioPossession4.setChecked(true);
            } else if (getValueOfboleaan(formdata.headquarters_possession_type) == 5) {
                binding.radioPossession5.setChecked(true);
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
        // All this Code to make shoure whant Radio Button is selected The other is Off
        binding.radioPossession.setOnCheckedChangeListener((compoundButton, b) -> {

            if (binding.radioPossession.isChecked()) {
                if (binding.radioPossession2.isChecked() || binding.radioPossession3.isChecked() || binding.radioPossession4.isChecked()
                        || binding.radioPossession5.isChecked()) {
                    binding.radioPossession2.setChecked(false);
                    binding.radioPossession3.setChecked(false);
                    binding.radioPossession4.setChecked(false);
                    binding.radioPossession5.setChecked(false);
                }
                //    binding.CheckBox1.setChecked(true);
            }

        });
        binding.radioPossession2.setOnCheckedChangeListener((compoundButton, b) -> {
            if (binding.radioPossession2.isChecked()) {
                if (binding.radioPossession.isChecked() || binding.radioPossession3.isChecked() || binding.radioPossession4.isChecked()
                        || binding.radioPossession5.isChecked()) {
                    binding.radioPossession.setChecked(false);
                    binding.radioPossession3.setChecked(false);
                    binding.radioPossession4.setChecked(false);
                    binding.radioPossession5.setChecked(false);
                }
                // binding.CheckBox2.setChecked(true);
            }

        });
        binding.radioPossession3.setOnCheckedChangeListener((compoundButton, b) -> {
            if (binding.radioPossession3.isChecked()) {
                if (binding.radioPossession.isChecked() || binding.radioPossession2.isChecked() || binding.radioPossession4.isChecked()
                        || binding.radioPossession5.isChecked()) {
                    binding.radioPossession.setChecked(false);
                    binding.radioPossession2.setChecked(false);
                    binding.radioPossession4.setChecked(false);
                    binding.radioPossession5.setChecked(false);
                }
                //binding.CheckBox3.setChecked(true);
            }
        });
        binding.radioPossession4.setOnCheckedChangeListener((compoundButton, b) -> {
            if (binding.radioPossession4.isChecked()) {
                if (binding.radioPossession.isChecked() || binding.radioPossession2.isChecked() || binding.radioPossession3.isChecked()
                        || binding.radioPossession5.isChecked()) {
                    binding.radioPossession.setChecked(false);
                    binding.radioPossession2.setChecked(false);
                    binding.radioPossession3.setChecked(false);
                    binding.radioPossession5.setChecked(false);
                }
                //   binding.CheckBox4.setChecked(true);
            }
        });
        binding.radioPossession5.setOnCheckedChangeListener((compoundButton, b) -> {
            if (binding.radioPossession5.isChecked()) {
                if (binding.radioPossession.isChecked() || binding.radioPossession2.isChecked() || binding.radioPossession4.isChecked()
                        || binding.radioPossession3.isChecked()) {
                    binding.radioPossession.setChecked(false);
                    binding.radioPossession2.setChecked(false);
                    binding.radioPossession4.setChecked(false);
                    binding.radioPossession3.setChecked(false);
                }
                //   binding.radioButton5.setChecked(true);
            }

        });
    }

}