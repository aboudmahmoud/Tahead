package com.example.taehaed.Screens.Fragment;

import static com.example.taehaed.Constans.DESCRIBABLE_KEY;
import static com.example.taehaed.Constans.ErrorMessageValdition;
import static com.example.taehaed.Constans.getValue;
import static com.example.taehaed.Constans.getValueOfboleaan;
import static com.example.taehaed.Constans.itsNotNull;
import static com.example.taehaed.Constans.setAlertMeaage;
import static com.example.taehaed.Constans.setDateForInputText;
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
import com.example.taehaed.databinding.FragmentThridJobInfoBinding;


public class ThridJobInfoFragment extends Fragment {
    private int servier_id;
    private FormData formdata;
    private AlertDialog alertDialog;
    TaehaedVModel taehaedVModel;
    private FragmentThridJobInfoBinding binding;

    public ThridJobInfoFragment() {


    }

    public static ThridJobInfoFragment getInstance(int id) {
        ThridJobInfoFragment fragment = new ThridJobInfoFragment();
        Bundle args = new Bundle();
        args.putInt(Constans.IdkeyFrgment, id);
        fragment.setArguments(args);
        return fragment;
    }

    public static ThridJobInfoFragment getInstance(int id, FormData formData) {
        ThridJobInfoFragment fragment = new ThridJobInfoFragment();
        Bundle args = new Bundle();
        args.putInt(Constans.IdkeyFrgment, id);
        args.putSerializable(DESCRIBABLE_KEY, formData);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (itsNotNull(getArguments())) {
            servier_id = getArguments().getInt(Constans.IdkeyFrgment);
            formdata = (FormData) getArguments().getSerializable(DESCRIBABLE_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentThridJobInfoBinding.inflate(getLayoutInflater());
        taehaedVModel = new ViewModelProvider(this).get(TaehaedVModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.goBack.setOnClickListener(view1 -> {
            getActivity().onBackPressed();
        });
        setDateForInputText(binding.PerathData, getContext());
        if (itsNotNull(formdata)) {
            binding.Sumbit.setVisibility(View.GONE);
            binding.DeletSumbit.setVisibility(View.VISIBLE);
            SetDataToUI();


        }
        binding.Sumbit.setOnClickListener(view1 -> {
            formdata = new FormData();
            if (getFromUiData()) return;
            Log.d("Aboud", "onViewCreated: id" + formdata.request_service_id);
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

    private void SetDataToUI() {
        //بيانات العميل
        AgentInfo();
        //بيانات جهة العمل
        JobInfo();
        //بيانات الموارد البشرية
        HrInfo();
        //نتيجة الاستعلام
        AskingOperation();
    }

    private boolean getFromUiData() {

        formdata.request_service_id = servier_id;
        setErrorNullForTextView();
        //بيانات العميل
        if(getAgentInfoValdion())
        {
            return true;
        }
        // بيانات جهة العمل
        if(getJobIfoValdtion())
        {
            return true;
        }
        //بيانات الموارد البشرية
        if(getHrInfoValdtion())
        {
            return true;
        }
        //نتيجة الاستعلام
        if(getAskingInfo())
        {
            return true;
        }
        return false;
    }

    private void setErrorNullForTextView() {
        binding.InscureTrue.setError(null);
        binding.CommentTrue.setError(null);
        binding.AgnetTrue.setError(null);
        binding.JobTrue.setError(null);
        binding.HRTrue.setError(null);
        binding.JobPostionTrue.setError(null);
        binding.SalaryTrue.setError(null);
        binding.IScurTrue.setError(null);
        binding.RaptaionTrue.setError(null);
    }

    private void AskingOperation() {
        if (itsNotNull(formdata.work_result_employer_name)) {
            if (getValueOfboleaan(formdata.work_result_employer_name) == 0) {
                binding.radioJobNameCH.setChecked(true);

            } else if (getValueOfboleaan(formdata.work_result_employer_name) == 1) {

                binding.radioJobNameCH2.setChecked(true);
            }
        }
        if (itsNotNull(formdata.work_result_employer_address)) {
            if (getValueOfboleaan(formdata.work_result_employer_address) == 0) {
                binding.radioJobAddersCH.setChecked(true);

            } else if (getValueOfboleaan(formdata.work_result_employer_address) == 1) {

                binding.radioJobAddersCH2.setChecked(true);
            }
        }
        if (itsNotNull(formdata.work_result_hr_meet)) {
            if (getValueOfboleaan(formdata.work_result_hr_meet) == 0) {

                binding.radioHrCH2.setChecked(false);
            } else if (getValueOfboleaan(formdata.work_result_hr_meet) == 1) {

                binding.radioHrCH2.setChecked(true);
            }
        }
        if (itsNotNull(formdata.work_result_job_title)) {
            if (getValueOfboleaan(formdata.work_result_job_title) == 0) {
                binding.radioJobPostionCH.setChecked(true);

            } else if (getValueOfboleaan(formdata.work_result_job_title) == 1) {

                binding.radioJobPostionCH2.setChecked(true);
            }
        }
        if (itsNotNull(formdata.work_result_income)) {
            if (getValueOfboleaan(formdata.work_result_income) == 0) {
                binding.radioSalaryCH.setChecked(true);

            } else if (getValueOfboleaan(formdata.work_result_income) == 1) {

                binding.radioSalaryCH2.setChecked(true);
            }
        }
        if (itsNotNull(formdata.work_result_insured)) {
            if (getValueOfboleaan(formdata.work_result_insured) == 0) {
                binding.radioSaftyCH.setChecked(true);

            } else if (getValueOfboleaan(formdata.work_result_insured) == 1) {

                binding.radiSaftyCH2.setChecked(true);
            }
        }
        if (itsNotNull(formdata.work_result_customer_heard)) {
            if (getValueOfboleaan(formdata.work_result_customer_heard) == 1) {
                binding.radioRaptaionCH.setChecked(true);

            } else if (getValueOfboleaan(formdata.work_result_customer_heard) == 2) {

                binding.radioRaptaionCH2.setChecked(true);

            } else if (getValueOfboleaan(formdata.work_result_customer_heard) == 3) {

                binding.radioRaptaionCH3.setChecked(true);
            }
        }
    }

    private void HrInfo() {
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

    private void JobInfo() {
        binding.JobCompanyName.setText(getValue(formdata.work_name));
        binding.BuldingNumber.setText(getValue(formdata.work_building_number));
        binding.Nieporehod.setText(getValue(formdata.work_street_name));
        binding.Ciety.setText(getValue(formdata.work_neighborhood));
        binding.Station.setText(getValue(formdata.work_city));
        binding.Twon.setText(getValue(formdata.work_governorate));
        binding.SpeiclSine.setText(getValue(formdata.work_special_marque));
    }

    private void AgentInfo() {
        binding.FullName.setText(getValue(formdata.client_name));
        binding.NakeName.setText((getValue(formdata.client_nickname)));
        binding.NainolIdNumber.setText(getValue(formdata.client_national_id));
        binding.PerathData.setText(getValue(formdata.client_birth_date));
        binding.PhonNumber.setText(getValue(formdata.client_mobile_number_1));
        binding.PhonNumber2.setText(getValue(formdata.client_mobile_number_2));
        binding.PhonNumber3.setText(getValue(formdata.client_phone));
    }


    private boolean getAskingInfo() {
        if (binding.radioJobName.getCheckedRadioButtonId() == binding.radioJobNameCH.getId()) {
            formdata.work_result_employer_name = 0;
        } else if (binding.radioJobName.getCheckedRadioButtonId() == binding.radioJobNameCH2.getId()) {
            formdata.work_result_employer_name = 1;
        } else{
            setErrorTextView( binding.AgnetTrue,getContext());
            return true;
        }
        if (binding.radioJobAdders.getCheckedRadioButtonId() == binding.radioJobAddersCH.getId()) {
            formdata.work_result_employer_address = 0;
        } else if (binding.radioJobAdders.getCheckedRadioButtonId() == binding.radioJobAddersCH2.getId()) {
            formdata.work_result_employer_address = 1;
        }
        else{

            setErrorTextView( binding.JobTrue,getContext());
            return true;
        }
        if (binding.radioHr.getCheckedRadioButtonId() == binding.radioHrCH.getId()) {
            formdata.work_result_hr_meet = 0;
        } else if (binding.radioHr.getCheckedRadioButtonId() == binding.radioHrCH2.getId()) {
            formdata.work_result_hr_meet = 1;
        }
        else{

            setErrorTextView( binding.HRTrue,getContext());
            return true;
        }
        if (binding.radioJobPostion.getCheckedRadioButtonId() == binding.radioJobPostionCH.getId()) {
            formdata.work_result_job_title = 0;
        } else if (binding.radioJobPostion.getCheckedRadioButtonId() == binding.radioJobPostionCH2.getId()) {
            formdata.work_result_job_title = 1;
        }
        else{
            setErrorTextView( binding.JobPostionTrue,getContext());
            return true;
        }
        if (binding.radioSalary.getCheckedRadioButtonId() == binding.radioSalaryCH.getId()) {
            formdata.work_result_income = 0;
        } else if (binding.radioSalary.getCheckedRadioButtonId() == binding.radioSalaryCH2.getId()) {
            formdata.work_result_income = 1;
        }
        else{
            setErrorTextView( binding.SalaryTrue,getContext());
            return true;
        }

        if (binding.radioSafty.getCheckedRadioButtonId() == binding.radioSaftyCH.getId()) {
            formdata.work_result_insured = 0;
        } else if (binding.radioSafty.getCheckedRadioButtonId() == binding.radiSaftyCH2.getId()) {
            formdata.work_result_insured = 1;
        }
        else{
            setErrorTextView( binding.IScurTrue,getContext());
            return true;
        }

        if (binding.radioRaptaion.getCheckedRadioButtonId() == binding.radioRaptaionCH.getId()) {
            formdata.work_result_customer_heard = 1;
        } else if (binding.radioRaptaion.getCheckedRadioButtonId() == binding.radioRaptaionCH2.getId()) {
            formdata.work_result_customer_heard = 2;
        } else if (binding.radioRaptaion.getCheckedRadioButtonId() == binding.radioRaptaionCH3.getId()) {
            formdata.work_result_customer_heard = 3;
        }
        else{
            setErrorTextView(binding.RaptaionTrue,getContext());
        }


        return false;
    }

    private boolean getHrInfoValdtion() {
        if (binding.NameOfHr.getText().toString().equals("")) {
            setErrorInputText(binding.NameOfHr,getContext());
            return true;
        }
        if (binding.JobTitle.getText().toString().equals("")) {
            setErrorInputText(binding.JobTitle,getContext());
            return true;
        }
        if (binding.Salary.getText().toString().equals("")) {
            setErrorInputText(binding.Salary,getContext());
            return true;
        }
        //مؤمن عليه ام لا
        if (binding.radioSafity.getCheckedRadioButtonId() == binding.radioSafityCH.getId()) {
            formdata.hr_insured = 0;
        } else if (binding.radioSafity.getCheckedRadioButtonId() == binding.radioSafityCH2.getId()) {
            formdata.hr_insured = 1;
        } else {
            setErrorTextView( binding.InscureTrue,getContext());
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
            setErrorTextView( binding.CommentTrue,getContext());
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

    private boolean getJobIfoValdtion() {
        if (binding.JobCompanyName.getText().toString().equals("")) {
            setErrorInputText(binding.JobCompanyName,getContext());
            return true;
        }
        if (binding.BuldingNumber.getText().toString().equals("")) {
            setErrorInputText(binding.BuldingNumber,getContext());
            return true;
        }
        if (binding.Nieporehod.getText().toString().equals("")) {
            setErrorInputText(binding.Nieporehod,getContext());
            return true;
        }
        if (binding.Ciety.getText().toString().equals("")) {
            setErrorInputText(binding.Ciety,getContext());
            return true;
        }
        if (binding.Station.getText().toString().equals("")) {
            setErrorInputText(binding.Station,getContext());
            return true;
        }
        if (binding.Twon.getText().toString().equals("")) {
            setErrorInputText(binding.Twon,getContext());
            return true;
        }
        if (binding.SpeiclSine.getText().toString().equals("")) {
            setErrorInputText(binding.SpeiclSine,getContext());
            return true;
        }
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

    private boolean getAgentInfoValdion() {
        // بيانات العميل
        if (binding.FullName.getText().toString().equals("")) {
            setErrorInputText(binding.FullName,getContext());
            return true;
        }
        if (binding.NainolIdNumber.getText().toString().equals("")) {
            setErrorInputText(binding.NainolIdNumber,getContext());
            return true;
        }
        if (binding.PerathData.getText().toString().equals("")) {
            setErrorInputText(binding.PerathData,getContext());
            return true;
        }
        if (binding.PhonNumber.getText().toString().equals("")) {
            setErrorInputText(binding.PhonNumber,getContext());
            return true;
        }
        getAgentInfo();
        return false;
    }

    private void getAgentInfo() {
        formdata.client_name = getValue(binding.FullName.getText());
        formdata.client_nickname = getValue(binding.NakeName.getText());
        formdata.client_national_id = getValue(binding.NainolIdNumber.getText());
        formdata.client_birth_date = getValue(binding.PerathData.getText());
        formdata.client_mobile_number_1 = getValue(binding.PhonNumber.getText());
        formdata.client_mobile_number_2 = getValue(binding.PhonNumber2.getText());
        formdata.client_phone = getValue(binding.PhonNumber3.getText());
    }


}