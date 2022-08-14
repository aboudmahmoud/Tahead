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

import com.example.taehaed.databinding.FragmentSixHomeBinding;



public class SixHomeFragment extends Fragment {


    private FragmentSixHomeBinding binding;
    private FormData formdata;
    private AlertDialog alertDialog;
    private TaehaedVModel taehaedVModel;
    private int servier_id;


    public SixHomeFragment() {

    }

    public static SixHomeFragment newInstance(int id) {
        SixHomeFragment fragment = new SixHomeFragment();
        Bundle args = new Bundle();
        args.putInt(Constans.IdkeyFrgment, id);
        fragment.setArguments(args);
        return fragment;
    }

    public static SixHomeFragment newInstance(int id, FormData formData) {
        SixHomeFragment fragment =  new SixHomeFragment();
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
        binding = FragmentSixHomeBinding.inflate(inflater);
        taehaedVModel = new ViewModelProvider(this).get(TaehaedVModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        Constans.setDateForInputText(binding.PerathData,getContext());

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

            //بيانات المبيعات / المشتريات
            SetSelasBuyData();

            //نتيجة الاستعلام
            SetAskingData();

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


}