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
import com.example.taehaed.databinding.FragmentEighthRealetateBinding;


public class EighthRealetateFragment extends Fragment {

    private FragmentEighthRealetateBinding binding;
    private FormData formdata;
    private AlertDialog alertDialog;
    private TaehaedVModel taehaedVModel;
    private int servier_id;

    public EighthRealetateFragment() {

    }


    public static EighthRealetateFragment newInstance(int id) {
        EighthRealetateFragment fragment = new EighthRealetateFragment();
        Bundle args = new Bundle();
        args.putInt(Constans.IdkeyFrgment, id);
        fragment.setArguments(args);
        return fragment;
    }

    public static EighthRealetateFragment newInstance(int id, FormData formData) {
        EighthRealetateFragment fragment = new EighthRealetateFragment();
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
        binding = FragmentEighthRealetateBinding.inflate(inflater);
        taehaedVModel = new ViewModelProvider(this).get(TaehaedVModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SetTheRadioButton();
        binding.goBack.setOnClickListener(view1 -> {
            getActivity().onBackPressed();
        });
        setDate();
        SetDataUI();
        binding.Sumbit.setOnClickListener(view1 -> {
            formdata = new FormData();

            if(getDataUI()) return;
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

    private void setDate() {
        Constans.setDateForInputText(binding.PerathData, getContext());
        Constans.setDateForInputText(binding.WHPerathData, getContext());
        Constans.setDateForInputText(binding.BuldingHetory, getContext());

    }

    private boolean getDataUI() {
        formdata.request_service_id = servier_id;

        SetTextErrorNullForRadio();
        //بيانات العميل
        if (getAgentDataValdion()) return true;
        //بيانات الزوج او الزوجة
        if (getWHDataValdion()) return true;
        // بيانات جهة العمل
        if (getJobInfoValdion()) return true;
        //بيانات الموارد البشرية
        if (getHrInfoValdion()) return true;

        //بيانات العقار
        if (getRealestateValdion()) return true;

        // الاستعلامات
        if (getAskingInfoData()) return true;
        return false;
    }

    private void SetTextErrorNullForRadio() {
        binding.InsuserorNottrue.setError(null);
        binding.CommonetiTrue.setError(null);
        binding.RealStatemntTypeTrue.setError(null);
        binding.DataAgentTrue.setError(null);
        binding.WHAgentDataTrue.setError(null);
        binding.JobNameTrue.setError(null);
        binding.SalaryAreNotTrue.setError(null);
        binding.IsInsuertTrue.setError(null);
        binding.RealstetisTrue.setError(null);
        binding.ReqlStateshapeTrue.setError(null);
        binding.SoureQusTrue.setError(null);
    }

    private boolean getAskingInfoData() {
        if (binding.radioDataCoustmerCH.isChecked()) {
            formdata.property_result_client_data = 0;
        } else if (binding.radioDataCoustmerCH2.isChecked()) {
            formdata.property_result_client_data = 1;
        } else {
            setErrorTextView(binding.DataAgentTrue, getContext());
            return true;
        }

        if (binding.radioWifeCH.isChecked()) {
            formdata.property_result_husband = 0;
        } else if (binding.radioWifeCH2.isChecked()) {
            formdata.property_result_husband = 1;
        } else {
            setErrorTextView(binding.WHAgentDataTrue, getContext());
            return true;
        }

        if (binding.radioJobInfoCH.isChecked()) {
            formdata.property_result_employer_data = 0;
        } else if (binding.radioJobInfoCH2.isChecked()) {
            formdata.property_result_employer_data = 1;
        } else {
            setErrorTextView(binding.JobNameTrue, getContext());
            return true;
        }

        if (binding.radioSalaryCH.isChecked()) {
            formdata.property_result_income = 0;
        } else if (binding.radioSalaryCH2.isChecked()) {
            formdata.property_result_income = 1;
        } else {
            setErrorTextView(binding.SalaryAreNotTrue, getContext());
            return true;
        }

        if (binding.radioSaftyCH.isChecked()) {
            formdata.property_result_insured = 0;
        } else if (binding.radiSaftyCH2.isChecked()) {
            formdata.property_result_insured = 1;
        } else {
            setErrorTextView(binding.IsInsuertTrue, getContext());
            return true;
        }

        if (binding.radioRealStateDataCH.isChecked()) {
            formdata.property_result_property_data = 0;
        } else if (binding.radioRealStateDataCH2.isChecked()) {
            formdata.property_result_property_data = 1;
        } else {
            setErrorTextView(binding.RealstetisTrue, getContext());
            return true;
        }

        if (binding.radioRaptaionCH.isChecked()) {
            formdata.property_result_condition = 1;
        } else if (binding.radioRaptaionCH2.isChecked()) {
            formdata.property_result_condition = 2;
        } else if (binding.radioRaptaionCH3.isChecked()) {
            formdata.property_result_condition = 3;
        } else {
            setErrorTextView(binding.ReqlStateshapeTrue, getContext());
            return true;
        }

        if (binding.radioSourseCH.isChecked()) {
            formdata.property_result_sources = 1;
        } else if (binding.radioSourseCH2.isChecked()) {
            formdata.property_result_sources = 2;
        } else if (binding.radioSourseCH3.isChecked()) {
            formdata.property_result_sources = 3;
        } else if (binding.radioSourseCH4.isChecked()) {
            formdata.property_result_sources = 4;
        } else {
            setErrorTextView(binding.SoureQusTrue, getContext());
            return true;
        }
        return false;
    }

    private boolean getRealestateValdion() {
        //نوع العقار
        if (binding.radioButton.isChecked()) {
            formdata.property_type = 1;
        } else if (binding.radioButton2.isChecked()) {
            formdata.property_type = 2;
        } else if (binding.radioButton3.isChecked()) {
            formdata.property_type = 3;
        } else if (binding.radioButton4.isChecked()) {
            formdata.property_type = 4;
        } else if (binding.radioButton5.isChecked()) {
            formdata.property_type = 5;
        } else {
            setErrorTextView(binding.RealStatemntTypeTrue, getContext());
            return true;
        }
        if (CheckInputfield(binding.BuldingName, getContext())) return true;
        if (CheckInputfield(binding.BuldingHetory, getContext())) return true;
        if (CheckInputfield(binding.BuldingNumber, getContext())) return true;
        if (CheckInputfield(binding.BuldingStNumber, getContext())) return true;
        if (CheckInputfield(binding.NieporehodBulding, getContext())) return true;
        if (CheckInputfield(binding.CietyBulding, getContext())) return true;
        if (CheckInputfield(binding.TwonBulding, getContext())) return true;
        if (CheckInputfield(binding.SpeiclSineBulding, getContext())) return true;
        if (CheckInputfield(binding.SpaceBulding, getContext())) return true;
        if (CheckInputfield(binding.RealStateType, getContext())) return true;
        if (CheckInputfield(binding.FloorsNumber, getContext())) return true;
        if (CheckInputfield(binding.Stores, getContext())) return true;
        if (CheckInputfield(binding.dimensions, getContext())) return true;
        getRealestate();
        return false;
    }

    private void getRealestate() {


        formdata.property_name = getValue(binding.BuldingName.getText());
        formdata.property_build_date = getValue(binding.BuldingHetory.getText());
        formdata.property_number = getValue(binding.BuldingNumber.getText());
        formdata.property_street_name = getValue(binding.BuldingStNumber.getText());
        formdata.property_neighborhood = getValue(binding.NieporehodBulding.getText());
        formdata.property_city = getValue(binding.CietyBulding.getText());
        formdata.property_governorate = getValue(binding.TwonBulding.getText());
        formdata.property_special_marque = getValue(binding.SpeiclSineBulding.getText());
        formdata.property_area = getValue(binding.SpaceBulding.getText());
        formdata.property_status = getValue(binding.RealStateType.getText());
        formdata.property_floor_number = getValue(binding.FloorsNumber.getText());
        formdata.property_apartment_number = getValue(binding.Stores.getText());
        formdata.property_facade_dimension = getValue(binding.dimensions.getText());


    }

    private boolean getHrInfoValdion() {
        if (CheckInputfield(binding.NameOfHr, getContext())) return true;
        if (CheckInputfield(binding.JobTitle, getContext())) return true;
        if (CheckInputfield(binding.Salary, getContext())) return true;
        //مؤمن عليه ام لا
        if (binding.radioSafity.getCheckedRadioButtonId() == binding.radioSafityCH.getId()) {
            formdata.hr_insured = 0;
        } else if (binding.radioSafity.getCheckedRadioButtonId() == binding.radioSafityCH2.getId()) {
            formdata.hr_insured = 1;
        } else {
            setErrorTextView(binding.InsuserorNottrue, getContext());
            return true;
        }
        //مدي إلتزامه
        if (binding.radioCommenit.getCheckedRadioButtonId() == binding.radioCommenitCH.getId()) {
            formdata.hr_work_committed = 1;
        } else if (binding.radioCommenit.getCheckedRadioButtonId() == binding.radioCommenitCH2.getId()) {
            formdata.hr_work_committed = 2;
        } else if (binding.radioCommenit.getCheckedRadioButtonId() == binding.radioCommenitCH3.getId()) {
            formdata.hr_work_committed = 3;
        } else {
            setErrorTextView(binding.CommonetiTrue, getContext());
            return true;
        }
        getHrInfo();
        return false;
    }

    private void getHrInfo() {
        formdata.hr_name = getValue(binding.NameOfHr.getText());
        formdata.hr_job_title = getValue(binding.JobTitle.getText());
        formdata.hr_total_income = getValue(binding.Salary.getText());

    }

    private boolean getJobInfoValdion() {
        if (CheckInputfield(binding.JobCompanyName, getContext())) return true;
        if (CheckInputfield(binding.BuldingNumber, getContext())) return true;
        if (CheckInputfield(binding.Nieporehod, getContext())) return true;
        if (CheckInputfield(binding.Station, getContext())) return true;
        if (CheckInputfield(binding.Twon, getContext())) return true;
        if (CheckInputfield(binding.SpeiclSine, getContext())) return true;
        getJobInfo();
        return false;
    }

    private void getJobInfo() {
        formdata.work_name = getValue(binding.JobCompanyName.getText());
        formdata.work_building_number = getValue(binding.BuldingNumber.getText());
        formdata.work_street_name = getValue(binding.Nieporehod.getText());
        formdata.work_neighborhood = getValue(binding.Ciety.getText());
        formdata.work_city = getValue(binding.Station.getText());
        formdata.work_governorate = getValue(binding.Twon.getText());
        formdata.work_special_marque = getValue(binding.SpeiclSine.getText());
    }

    private boolean getWHDataValdion() {
        if (CheckInputfield(binding.WHFullName, getContext())) return true;
        if (CheckInputfield(binding.WHNakeName, getContext())) return true;
        if (CheckInputfield(binding.WHPerathData, getContext())) return true;
        if (CheckInputfield(binding.WHPhonNumber, getContext())) return true;
        if (CheckInputfield(binding.WHPhonNumber2, getContext())) return true;
        if (CheckInputfield(binding.WHPhonNumber3, getContext())) return true;
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
            //بيانات الزوج او الزوجة
            SetWHData();
            //بيانات جهة العمل
            SetJobInfo();
            //بيانات الموارد البشرية
            SetHrInfo();
            //بيانات العقار
            SetRealestate();

            // الاستعلامات
            SetAskingInfoData();
        }

    }

    private void SetRealestate() {
        //نوع العقار
        if (itsNotNull(formdata.property_type)) {
            if ((getValueOfboleaan(formdata.property_type)) == 1) {
                binding.radioButton.setChecked(true);

            } else if ((getValueOfboleaan(formdata.property_type)) == 2) {

                binding.radioButton2.setChecked(true);
            } else if ((getValueOfboleaan(formdata.property_type)) == 3) {

                binding.radioButton3.setChecked(true);
            } else if ((getValueOfboleaan(formdata.property_type)) == 4) {

                binding.radioButton4.setChecked(true);
            } else if ((getValueOfboleaan(formdata.property_type)) == 5) {

                binding.radioButton5.setChecked(true);
            }

        }

        binding.BuldingName.setText(getValue(formdata.property_name));
        binding.BuldingHetory.setText(getValue(formdata.property_build_date));
        binding.BuldingNumber.setText(getValue(formdata.property_number));
        binding.BuldingStNumber.setText(getValue(formdata.property_street_name));
        binding.NieporehodBulding.setText(getValue(formdata.property_neighborhood));
        binding.CietyBulding.setText(getValue(formdata.property_city));
        binding.TwonBulding.setText(getValue(formdata.property_governorate));
        binding.SpeiclSineBulding.setText(getValue(formdata.property_special_marque));
        binding.SpaceBulding.setText(getValue(formdata.property_area));
        binding.RealStateType.setText(getValue(formdata.property_status));
        binding.FloorsNumber.setText(getValue(formdata.property_floor_number));
        binding.Stores.setText(getValue(formdata.property_apartment_number));
        binding.dimensions.setText(getValue(formdata.property_facade_dimension));


    }

    private void SetAskingInfoData() {
        if (itsNotNull(formdata.property_result_client_data)) {
            if (getValueOfboleaan(formdata.property_result_client_data) == 0) {
                binding.radioDataCoustmerCH.setChecked(true);

            } else if (getValueOfboleaan(formdata.property_result_client_data) == 1) {

                binding.radioDataCoustmerCH2.setChecked(true);
            }
        }

        if (itsNotNull(formdata.property_result_husband)) {
            if (getValueOfboleaan(formdata.property_result_husband) == 0) {
                binding.radioWifeCH.setChecked(true);

            } else if (getValueOfboleaan(formdata.property_result_husband) == 1) {

                binding.radioWifeCH2.setChecked(true);
            }
        }

        if (itsNotNull(formdata.property_result_employer_data)) {
            if (getValueOfboleaan(formdata.property_result_employer_data) == 0) {
                binding.radioJobInfoCH.setChecked(true);

            } else if (getValueOfboleaan(formdata.property_result_employer_data) == 1) {

                binding.radioJobInfoCH2.setChecked(true);
            }
        }
        if (itsNotNull(formdata.property_result_income)) {
            if (getValueOfboleaan(formdata.property_result_income) == 0) {
                binding.radioSalaryCH.setChecked(true);

            } else if (getValueOfboleaan(formdata.property_result_income) == 1) {

                binding.radioSalaryCH2.setChecked(true);
            }
        }
        if (itsNotNull(formdata.property_result_insured)) {
            if (getValueOfboleaan(formdata.property_result_insured) == 0) {
                binding.radioSaftyCH.setChecked(true);

            } else if (getValueOfboleaan(formdata.property_result_insured) == 1) {

                binding.radiSaftyCH2.setChecked(true);
            }
        }
        if (itsNotNull(formdata.property_result_property_data)) {
            if (getValueOfboleaan(formdata.property_result_property_data) == 0) {
                binding.radioRealStateDataCH.setChecked(true);

            } else if (getValueOfboleaan(formdata.property_result_property_data) == 1) {

                binding.radioRealStateDataCH2.setChecked(true);
            }
        }
        if (itsNotNull(formdata.property_result_condition)) {
            if (getValueOfboleaan(formdata.property_result_condition) == 1) {
                binding.radioRaptaionCH.setChecked(true);

            } else if (getValueOfboleaan(formdata.property_result_condition) == 2) {

                binding.radioRaptaionCH2.setChecked(true);


            } else if (getValueOfboleaan(formdata.property_result_condition) == 3) {

                binding.radioRaptaionCH3.setChecked(true);
            }
        }
        if (itsNotNull(formdata.property_result_sources)) {
            if (getValueOfboleaan(formdata.property_result_sources) == 1) {
                binding.radioSourseCH.setChecked(true);

            } else if (getValueOfboleaan(formdata.property_result_sources) == 2) {

                binding.radioSourseCH2.setChecked(true);


            } else if (getValueOfboleaan(formdata.property_result_sources) == 3) {

                binding.radioSourseCH3.setChecked(true);
            } else if (getValueOfboleaan(formdata.property_result_sources) == 4) {

                binding.radioSourseCH4.setChecked(true);
            }

        }


    }

    private void SetHrInfo() {
        binding.NameOfHr.setText(getValue(formdata.hr_name));
        binding.JobTitle.setText(getValue(formdata.hr_job_title));
        binding.Salary.setText(getValue(formdata.hr_total_income));
        //بيانات موارد البشرية
        //الاختيارات
        if (itsNotNull(formdata.hr_insured)) {
            if (getValueOfboleaan(formdata.hr_insured) == 0) {
                binding.radioSafityCH.setChecked(true);

            } else if (getValueOfboleaan(formdata.hr_insured) == 1) {

                binding.radioSafityCH2.setChecked(true);
            }
        }
        if (itsNotNull(formdata.hr_work_committed)) {
            if ((getValueOfboleaan(formdata.hr_work_committed)) == 1) {
                binding.radioCommenitCH.setChecked(true);

            } else if ((getValueOfboleaan(formdata.hr_work_committed)) == 2) {

                binding.radioCommenitCH2.setChecked(true);
            } else if ((getValueOfboleaan(formdata.hr_work_committed)) == 3) {

                binding.radioCommenitCH3.setChecked(true);
            }
        }
    }

    private void SetJobInfo() {
        binding.JobCompanyName.setText(getValue(formdata.work_name));
        binding.BuldingNumber.setText(getValue(formdata.work_building_number));
        binding.Nieporehod.setText(getValue(formdata.work_street_name));
        binding.Ciety.setText(getValue(formdata.work_neighborhood));
        binding.Station.setText(getValue(formdata.work_city));
        binding.Twon.setText(getValue(formdata.work_governorate));
        binding.SpeiclSine.setText(getValue(formdata.work_special_marque));
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
        binding.radioButton.setOnCheckedChangeListener((compoundButton, b) -> {

            if (binding.radioButton.isChecked()) {
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
    }
}