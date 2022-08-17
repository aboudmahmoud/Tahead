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
import com.example.taehaed.R;
import com.example.taehaed.databinding.FragmentSevenSevriesBinding;

import java.util.ArrayList;


public class SevenSevriesFragment extends Fragment {

    private FragmentSevenSevriesBinding binding;
    private FormData formdata;
    private AlertDialog alertDialog;
    private TaehaedVModel taehaedVModel;
    private int servier_id;

    public SevenSevriesFragment() {

    }

    public static SevenSevriesFragment newInstance(int id) {
        SevenSevriesFragment fragment = new SevenSevriesFragment();
        Bundle args = new Bundle();
        args.putInt(Constans.IdkeyFrgment, id);
        fragment.setArguments(args);
        return fragment;
    }

    public static SevenSevriesFragment newInstance(int id, FormData formData) {
        SevenSevriesFragment fragment = new SevenSevriesFragment();
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
        binding = FragmentSevenSevriesBinding.inflate(inflater);
        taehaedVModel = new ViewModelProvider(this).get(TaehaedVModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SetRadios();

        SetDate();
        SetDataUI();
        binding.Sumbit.setOnClickListener(view1 -> {
            formdata = new FormData();


            if(getDataUI())return;
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

        //MakeRadioError null
        setErrorNullOrRadioText();

        //بيانات العميل
        if(getAgentDataValdion()) return true;

        //بيانات النشاط
        if(getActvityDataValdion()) return true;

        //نتيجة الاستعلام
        if(getAskingData());

        return false;
    }

    private void setErrorNullOrRadioText() {
        binding.ActactivtyPlaceTrue.setError(null);
        binding.InserConverTrue.setError(null);
        binding.InCaseOfPlaceTrue.setError(null);
        binding.PoruberOFfoundinTrue.setError(null);
        binding.WeakReeast.setError(null);
        binding.AntoherJobTrue.setError(null);
        binding.JobTypeTrue.setError(null);
        binding.OwnerDataTrue.setError(null);
        binding.PlacmentAvativeTrue.setError(null);
        binding.OwnerdataisTrue.setError(null);
        binding.ToolsUsedTrue.setError(null);
        binding.LaydownTrue.setError(null);
        binding.ReaprationTure.setError(null);
        binding.SoureseTrue.setError(null);
    }

    private boolean getAskingData() {
        if (binding.radioLieesnasCH.isChecked()) {
            formdata.service_activity_result_owner = 0;
        } else if (binding.radioLieesnasCH2.isChecked()) {
            formdata.service_activity_result_owner = 1;
        }else {
            setErrorTextView(binding.OwnerDataTrue, getContext());
            return true;
        }

        if (binding.radioMachineLiesnseCH.isChecked()) {
            formdata.service_activity_result_headquarters = 0;
        } else if (binding.radioMachineLiesnseCH2.isChecked()) {
            formdata.service_activity_result_headquarters = 1;
        }
        else {
            setErrorTextView(binding.PlacmentAvativeTrue, getContext());
            return true;
        }

        if (binding.radioOwnerCH.isChecked()) {
            formdata.service_activity_result_manager = 0;
        } else if (binding.radioOwnerCH2.isChecked()) {
            formdata.service_activity_result_manager = 1;
        }
        else{
            setErrorTextView(binding.OwnerdataisTrue, getContext());
            return true;
        }


        if (binding.radioValueCH.isChecked()) {
            formdata.service_activity_result_tools = 1;
        } else if (binding.radioValueCH2.isChecked()) {
            formdata.service_activity_result_tools = 2;
        } else if (binding.radioValueCH3.isChecked()) {
            formdata.service_activity_result_tools = 3;
        }
        else{
            setErrorTextView(binding.ToolsUsedTrue, getContext());
            return true;
        }

        if (binding.radioRecessionCH.isChecked()) {
            formdata.service_activity_result_lying_down = 1;
        } else if (binding.radioRecessionCH2.isChecked()) {
            formdata.service_activity_result_lying_down = 2;
        } else if (binding.radioRecessionCH3.isChecked()) {
            formdata.service_activity_result_lying_down = 3;
        } else if (binding.radioRecessionCH4.isChecked()) {
            formdata.service_activity_result_lying_down = 4;
        }
        else{
            setErrorTextView(binding.LaydownTrue, getContext());
            return true;
        }

        if (binding.radioReputationCH.isChecked()) {
            formdata.service_activity_result_customer_heard = 1;
        } else if (binding.radioReputationCH2.isChecked()) {
            formdata.service_activity_result_customer_heard = 2;
        } else if (binding.radioReputationCH3.isChecked()) {
            formdata.service_activity_result_customer_heard = 3;
        }
        else{
            setErrorTextView(binding.ReaprationTure, getContext());
            return true;
        }

        if (binding.radioSoueCH.isChecked()) {
            formdata.service_activity_result_sources = 1;
        } else if (binding.radioSouCH2.isChecked()) {
            formdata.service_activity_result_sources = 2;
        } else if (binding.radioSouCH3.isChecked()) {
            formdata.service_activity_result_sources = 3;
        } else if (binding.radioSouCH4.isChecked()) {
            formdata.service_activity_result_sources = 4;
        }else{
            setErrorTextView(binding.SoureseTrue, getContext());
            return true;
        }


        return false;
    }

    private boolean getActvityDataValdion() {
        if (CheckInputfield(binding.ComarialType, getContext())) return true;
        if (CheckInputfield(binding.ComarialDate, getContext())) return true;
        if (CheckInputfield(binding.NumberOfEmployers, getContext())) return true;
        if (CheckInputfield(binding.NumberOfHoursWorker, getContext())) return true;
        boolean Statuse = false;
        //يوجد مقر للنشاط
        if (binding.radioHavePlaceCH.isChecked()) {
            formdata.service_activity_headquarters_1 = 0;
            Statuse = false;
        } else if (binding.radioHavePlaceCH2.isChecked()) {
            formdata.service_activity_headquarters_1 = 1;
            Statuse = true;
        } else {
            setErrorTextView(binding.ActactivtyPlaceTrue, getContext());
            return true;
        }
        //يوجد تغطية تأمينة
        if (binding.radioInsuranceeCH.isChecked()) {
            formdata.service_activity_financing_coverage = 0;
        } else if (binding.radioInsuranceCH2.isChecked()) {
            formdata.service_activity_financing_coverage = 1;
        } else {
            setErrorTextView(binding.InserConverTrue, getContext());
            return true;
        }
        if (Statuse) {
            //في حالة وجود مقر
            if (binding.radioHeadquartersCH.isChecked()) {
                formdata.service_activity_headquarters_2 = 1;
            } else if (binding.radioHeadquartersCH2.isChecked()) {
                formdata.service_activity_headquarters_2 = 2;
            } else if (binding.radioHeadquartersCH3.isChecked()) {
                formdata.service_activity_headquarters_2 = 3;
            } else if (binding.radioHeadquartersCH4.isChecked()) {
                formdata.service_activity_headquarters_2 = 4;
            } else {
                setErrorTextView(binding.InCaseOfPlaceTrue, getContext());
                return true;
            }
        }

        //الغرض من التمويل
        if (binding.radioFinanceCH.isChecked()) {
            formdata.service_activity_purpose_funding = 1;
        } else if (binding.radioFinanceCH2.isChecked()) {
            formdata.service_activity_purpose_funding = 2;
        } else if (binding.radioFinanceCH3.isChecked()) {
            formdata.service_activity_purpose_funding = 3;
        } else {
            setErrorTextView(binding.PoruberOFfoundinTrue, getContext());
            return true;
        }


        //الراحة الأسبوعية
        if (binding.radioAeeklyCH.isChecked()) {
            formdata.service_activity_weekly_rest = 1;
        } else if (binding.radioAeeklyCH2.isChecked()) {
            formdata.service_activity_weekly_rest = 2;
        } else if (binding.radioAeeklyCH3.isChecked()) {
            formdata.service_activity_weekly_rest = 3;
        } else if (binding.radioAeeklyCH4.isChecked()) {
            formdata.service_activity_weekly_rest = 4;
        } else {
            setErrorTextView(binding.WeakReeast, getContext());
            return true;
        }

        //عمل اُخر
        if (binding.radioJobAntoherCH.isChecked()) {
            formdata.service_activity_another_job = 1;
        } else if (binding.radioJobAntoherCH2.isChecked()) {
            formdata.service_activity_another_job = 2;
        } else {
            setErrorTextView(binding.AntoherJobTrue, getContext());
            return true;
        }

        //نوع العمل
        if (binding.radioradioJobTypeCH.isChecked()) {
            formdata.service_activity_work_type = 1;
        } else if (binding.radioradioJobTypeCH2.isChecked()) {
            formdata.service_activity_work_type = 2;
        } else {
            setErrorTextView(binding.JobTypeTrue, getContext());
            return true;
        }
        if (CheckInputfield(binding.MonreySupported, getContext())) return true;
        if (CheckInputfield(binding.NumberOfFamilyMamaber, getContext())) return true;
        getActvityData();
        return false;
    }

    private void getActvityData() {
        formdata.service_activity_type = getValue(binding.ComarialType.getText());
        formdata.service_activity_from = getValue(binding.ComarialDate.getText());
        formdata.service_activity_workers_number = getValue(binding.NumberOfEmployers.getText());
        formdata.service_activity_working_hours = getValue(binding.NumberOfHoursWorker.getText());
        formdata.service_activity_average_income = getValue(binding.MonreySupported.getText());
        formdata.service_activity_family_number = getValue(binding.NumberOfFamilyMamaber.getText());
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


            //نتيجة الاستعلام
            SetAskingData();

        }
    }

    private void SetAskingData() {
        if (itsNotNull(formdata.service_activity_result_owner)) {
            if (getValueOfboleaan(formdata.service_activity_result_owner) == 0) {
                binding.radioLieesnasCH.setChecked(true);
            } else if (getValueOfboleaan(formdata.service_activity_result_owner) == 1) {
                binding.radioLieesnasCH2.setChecked(true);
            }
        }
        if (itsNotNull(formdata.service_activity_result_headquarters)) {
            if (getValueOfboleaan(formdata.service_activity_result_headquarters) == 0) {
                binding.radioMachineLiesnseCH.setChecked(true);
            } else if (getValueOfboleaan(formdata.service_activity_result_headquarters) == 1) {
                binding.radioMachineLiesnseCH2.setChecked(true);
            }
        }
        if (itsNotNull(formdata.service_activity_result_manager)) {
            if (getValueOfboleaan(formdata.service_activity_result_manager) == 0) {
                binding.radioOwnerCH.setChecked(true);
            } else if (getValueOfboleaan(formdata.service_activity_result_manager) == 1) {
                binding.radioOwnerCH2.setChecked(true);
            }
        }
        if (itsNotNull(formdata.service_activity_result_tools)) {
            if (getValueOfboleaan(formdata.service_activity_result_tools) == 1) {
                binding.radioValueCH.setChecked(true);
            } else if (getValueOfboleaan(formdata.service_activity_result_tools) == 2) {
                binding.radioValueCH2.setChecked(true);
            } else if (getValueOfboleaan(formdata.service_activity_result_tools) == 3) {
                binding.radioValueCH3.setChecked(true);
            }
        }
        if (itsNotNull(formdata.service_activity_result_lying_down)) {
            if (getValueOfboleaan(formdata.service_activity_result_lying_down) == 1) {
                binding.radioRecessionCH.setChecked(true);
            } else if (getValueOfboleaan(formdata.service_activity_result_lying_down) == 2) {
                binding.radioRecessionCH2.setChecked(true);
            } else if (getValueOfboleaan(formdata.service_activity_result_lying_down) == 3) {
                binding.radioRecessionCH3.setChecked(true);
            } else if (getValueOfboleaan(formdata.service_activity_result_lying_down) == 4) {
                binding.radioRecessionCH4.setChecked(true);
            }
        }
        if (itsNotNull(formdata.service_activity_result_customer_heard)) {
            if (getValueOfboleaan(formdata.service_activity_result_customer_heard) == 1) {
                binding.radioReputationCH.setChecked(true);
            } else if (getValueOfboleaan(formdata.service_activity_result_customer_heard) == 2) {
                binding.radioReputationCH2.setChecked(true);
            } else if (getValueOfboleaan(formdata.service_activity_result_customer_heard) == 3) {
                binding.radioReputationCH3.setChecked(true);
            }

        }
        if (itsNotNull(formdata.service_activity_result_sources)) {
            if (getValueOfboleaan(formdata.service_activity_result_sources) == 1) {
                binding.radioSoueCH.setChecked(true);
            } else if (getValueOfboleaan(formdata.service_activity_result_sources) == 2) {
                binding.radioSouCH2.setChecked(true);
            } else if (getValueOfboleaan(formdata.service_activity_result_sources) == 3) {
                binding.radioSouCH3.setChecked(true);
            } else if (getValueOfboleaan(formdata.service_activity_result_sources) == 4) {
                binding.radioSouCH4.setChecked(true);
            }

        }
        if (!formdata.service_activity_result_attachments.isEmpty()) {
            for (int i = 0; i < formdata.service_activity_result_attachments.size(); i++) {
                if (formdata.service_activity_result_attachments.get(i).equals("1")) {
                    binding.radioFielsCH.setChecked(true);
                }
                if (formdata.service_activity_result_attachments.get(i).equals("2")) {
                    binding.radioFielsCH2.setChecked(true);
                }
            }
        }
    }

    private void SetActvityData() {
        binding.ComarialType.setText(getValue(formdata.service_activity_type));
        binding.ComarialDate.setText(getValue(formdata.service_activity_from));
        binding.NumberOfEmployers.setText(getValue(formdata.service_activity_workers_number));
        binding.NumberOfHoursWorker.setText(getValue(formdata.service_activity_working_hours));
        //يوجد مقر للنشاط
        if (itsNotNull(formdata.service_activity_headquarters_1)) {
            if (getValueOfboleaan(formdata.service_activity_headquarters_1) == 0) {
                binding.radioHavePlaceCH.setChecked(true);
            } else if (getValueOfboleaan(formdata.service_activity_headquarters_1) == 1) {
                binding.radioHavePlaceCH2.setChecked(true);
            }
        }
        //يوجد تغطية تأمينة
        if (itsNotNull(formdata.service_activity_financing_coverage)) {
            if (getValueOfboleaan(formdata.service_activity_financing_coverage) == 0) {
                binding.radioInsuranceeCH.setChecked(true);
            } else if (getValueOfboleaan(formdata.service_activity_financing_coverage) == 1) {
                binding.radioInsuranceCH2.setChecked(true);
            }
        }

        //في حالة وجود مقر
        if (itsNotNull(formdata.service_activity_headquarters_2)) {
            if (getValueOfboleaan(formdata.service_activity_headquarters_2) == 1) {
                binding.radioHeadquartersCH.setChecked(true);
            } else if (getValueOfboleaan(formdata.service_activity_headquarters_2) == 2) {
                binding.radioHeadquartersCH2.setChecked(true);
            } else if (getValueOfboleaan(formdata.service_activity_headquarters_2) == 3) {
                binding.radioHeadquartersCH3.setChecked(true);
            } else if (getValueOfboleaan(formdata.service_activity_headquarters_2) == 4) {
                binding.radioHeadquartersCH4.setChecked(true);
            }
        }

        //الغرض من التمويل
        if (itsNotNull(formdata.service_activity_purpose_funding)) {
            if (getValueOfboleaan(formdata.service_activity_purpose_funding) == 1) {
                binding.radioFinanceCH.setChecked(true);
            } else if (getValueOfboleaan(formdata.service_activity_purpose_funding) == 2) {
                binding.radioFinanceCH2.setChecked(true);
            } else if (getValueOfboleaan(formdata.service_activity_purpose_funding) == 3) {
                binding.radioFinanceCH3.setChecked(true);
            }

        }

        //الراحة الأسبوعية
        if (itsNotNull(formdata.service_activity_weekly_rest)) {
            if (getValueOfboleaan(formdata.service_activity_weekly_rest) == 1) {
                binding.radioAeeklyCH.setChecked(true);
            } else if (getValueOfboleaan(formdata.service_activity_weekly_rest) == 2) {
                binding.radioAeeklyCH2.setChecked(true);
            } else if (getValueOfboleaan(formdata.service_activity_weekly_rest) == 3) {
                binding.radioAeeklyCH3.setChecked(true);
            } else if (getValueOfboleaan(formdata.service_activity_weekly_rest) == 4) {
                binding.radioAeeklyCH4.setChecked(true);
            }

        }

        //عمل اُخر
        if (itsNotNull(formdata.service_activity_another_job)) {
            if (getValueOfboleaan(formdata.service_activity_another_job) == 1) {
                binding.radioJobAntoherCH.setChecked(true);
            } else if (getValueOfboleaan(formdata.service_activity_another_job) == 2) {
                binding.radioJobAntoherCH2.setChecked(true);
            }

        }

        //نوع العمل
        if (itsNotNull(formdata.service_activity_work_type)) {
            if (getValueOfboleaan(formdata.service_activity_work_type) == 1) {
                binding.radioradioJobTypeCH.setChecked(true);
            } else if (getValueOfboleaan(formdata.service_activity_work_type) == 2) {
                binding.radioradioJobTypeCH2.setChecked(true);
            }

        }


        binding.MonreySupported.setText(getValue(formdata.service_activity_average_income));
        binding.NumberOfFamilyMamaber.setText(getValue(formdata.service_activity_family_number));
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

    private void SetDate() {
        Constans.setDateForInputText(binding.PerathData, getContext());
        Constans.setDateForInputText(binding.ComarialDate, getContext());

    }

    private void SetRadios() {
        binding.radioRecessionCH.setOnCheckedChangeListener((compoundButton, b) -> {

            if (binding.radioRecessionCH.isChecked()) {
                if (binding.radioRecessionCH2.isChecked() || binding.radioRecessionCH3.isChecked() || binding.radioRecessionCH4.isChecked()) {
                    binding.radioRecessionCH2.setChecked(false);
                    binding.radioRecessionCH3.setChecked(false);
                    binding.radioRecessionCH4.setChecked(false);
                }
                //    binding.CheckBox1.setChecked(true);
            }

        });
        binding.radioRecessionCH2.setOnCheckedChangeListener((compoundButton, b) -> {
            if (binding.radioRecessionCH2.isChecked()) {
                if (binding.radioRecessionCH.isChecked() || binding.radioRecessionCH3.isChecked() || binding.radioRecessionCH4.isChecked()) {
                    binding.radioRecessionCH.setChecked(false);
                    binding.radioRecessionCH3.setChecked(false);
                    binding.radioRecessionCH4.setChecked(false);
                }
                // binding.CheckBox2.setChecked(true);
            }

        });
        binding.radioRecessionCH3.setOnCheckedChangeListener((compoundButton, b) -> {
            if (binding.radioRecessionCH3.isChecked()) {
                if (binding.radioRecessionCH.isChecked() || binding.radioRecessionCH2.isChecked() || binding.radioRecessionCH4.isChecked()) {
                    binding.radioRecessionCH.setChecked(false);
                    binding.radioRecessionCH2.setChecked(false);
                    binding.radioRecessionCH4.setChecked(false);
                }
                //binding.CheckBox3.setChecked(true);
            }
        });
        binding.radioRecessionCH4.setOnCheckedChangeListener((compoundButton, b) -> {
            if (binding.radioRecessionCH4.isChecked()) {
                if (binding.radioRecessionCH.isChecked() || binding.radioRecessionCH2.isChecked() || binding.radioRecessionCH3.isChecked()) {
                    binding.radioRecessionCH.setChecked(false);
                    binding.radioRecessionCH2.setChecked(false);
                    binding.radioRecessionCH3.setChecked(false);
                }
                //binding.CheckBox3.setChecked(true);
            }
        });

        binding.radioHavePlaceCH.setOnCheckedChangeListener((compoundButton, b) -> {
            if (binding.radioHavePlaceCH.isChecked()) {
                binding.PlacmentTrue.setVisibility(View.GONE);
            }
        });
        binding.radioHavePlaceCH2.setOnCheckedChangeListener((compoundButton, b) -> {
            if (binding.radioHavePlaceCH2.isChecked()) {
                binding.PlacmentTrue.setVisibility(View.VISIBLE);
            }
        });


    }

}