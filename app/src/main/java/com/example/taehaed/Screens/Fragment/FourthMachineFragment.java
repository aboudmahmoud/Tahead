package com.example.taehaed.Screens.Fragment;

import static com.example.taehaed.Constans.CheckInputfield;
import static com.example.taehaed.Constans.DESCRIBABLE_KEY;
import static com.example.taehaed.Constans.ErrorMessageValdition;
import static com.example.taehaed.Constans.getValue;
import static com.example.taehaed.Constans.getValueOfboleaan;
import static com.example.taehaed.Constans.itsNotNull;
import static com.example.taehaed.Constans.setAlertMeaage;
import static com.example.taehaed.Constans.setErrorInputText;
import static com.example.taehaed.Constans.setErrorTextView;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.taehaed.Constans;
import com.example.taehaed.Model.TaehaedVModel;
import com.example.taehaed.Pojo.FormReuest.FormData;
import com.example.taehaed.Pojo.NoteBodey;
import com.example.taehaed.Pojo.Request.RequestService;
import com.example.taehaed.R;
import com.example.taehaed.databinding.FragmentFourthMachineBinding;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;


public class FourthMachineFragment extends Fragment {
    private int servier_id;
    private FormData formdata;
    private AlertDialog alertDialog;
    private TaehaedVModel taehaedVModel;
    private FragmentFourthMachineBinding binding;


    public FourthMachineFragment() {

    }


    public static FourthMachineFragment newInstance(int id) {
        FourthMachineFragment fragment = new FourthMachineFragment();

        Bundle args = new Bundle();
        args.putInt(Constans.IdkeyFrgment, id);

        fragment.setArguments(args);
        return fragment;
    }

    public static FourthMachineFragment newInstance(int id, FormData formData) {
        FourthMachineFragment fragment = new FourthMachineFragment();

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
        binding = FragmentFourthMachineBinding.inflate(getLayoutInflater());
        taehaedVModel = new ViewModelProvider(requireActivity()).get(TaehaedVModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.goBack.setOnClickListener(view1 -> {
            getActivity().onBackPressed();
        });

        SetDataUI();

        binding.Sumbit.setOnClickListener(view1 -> {
            formdata = new FormData();
          if(  getFromUiData()) return ;
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
        SetTheRadioButton();
    }

    private void SetDataUI() {
        if (itsNotNull(formdata)) {
            binding.Sumbit.setVisibility(View.GONE);
            binding.DeletSumbit.setVisibility(View.VISIBLE);
            //بيانات العميل
            SetAgentData();
            //بيانات رخصة القيادة
            SetLisenesData();
            //بيانات رخصة المركبة
            SetViesicalLisesData();
            // نتيجة الاستعلام
            SetAskingData();
        }
    }

    private void SetAskingData() {

        if (itsNotNull(formdata.vehicle_result_driving_license)) {
            if (getValueOfboleaan(formdata.vehicle_license_situation) == 0) {
                binding.radioLieesnasCH.setChecked(true);

            } else if (getValueOfboleaan(formdata.vehicle_license_situation) == 1) {

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
        if(getAskingDataInfo()) return true;

        return false;
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
            formdata.vehicle_license_situation = 0;
        } else if (binding.radioLieesnasCH2.isChecked()) {
            formdata.vehicle_license_situation = 1;
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


}