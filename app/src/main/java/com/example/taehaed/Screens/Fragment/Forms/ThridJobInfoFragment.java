package com.example.taehaed.Screens.Fragment.Forms;

import static com.example.taehaed.Constans.CheckInputfield;
import static com.example.taehaed.Constans.DESCRIBABLE_KEY;
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
import static com.example.taehaed.Constans.setDateForInputText;
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
import com.example.taehaed.databinding.FragmentThridJobInfoBinding;

import java.io.File;
import java.util.ArrayList;


public class ThridJobInfoFragment extends Fragment  implements ImageTakeIt {
    private int servier_id, DoneStatus;
    private FormData formdata;
    private AlertDialog alertDialog;
    TaehaedVModel taehaedVModel;

    private FragmentThridJobInfoBinding binding;
    private ArrayList<ImageFileData> imageFileData;
    private ImageFileApabter Adapter;
    private ArrayList<File> attachments;
    private File aboudfile;
    ActivityResultLauncher<String> activityResultLauncher;
    public ThridJobInfoFragment() {


    }

    public static ThridJobInfoFragment getInstance(int id) {
        ThridJobInfoFragment fragment = new ThridJobInfoFragment();
        Bundle args = new Bundle();
        args.putInt(Constans.IdkeyFrgment, id);
        fragment.setArguments(args);
        return fragment;
    }

    public static ThridJobInfoFragment getInstance(int id, FormData formData, int DoneStatus) {
        ThridJobInfoFragment fragment = new ThridJobInfoFragment();
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
        if (itsNotNull(getArguments())) {
            servier_id = getArguments().getInt(Constans.IdkeyFrgment);
            formdata = (FormData) getArguments().getSerializable(DESCRIBABLE_KEY);
            DoneStatus = getArguments().getInt(Constans.DoneStatus);
        }

        attachments= new ArrayList<>();
        Adapter=new ImageFileApabter();
        imageFileData = new ArrayList<>();
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

        setDateForInputText(binding.PerathData, getContext());
        if (itsNotNull(formdata)) {
            binding.Sumbit.setVisibility(View.VISIBLE);
            binding.DisplayPDf.setVisibility(View.VISIBLE);
            SetDataToUI();

            //Constans.enableDisableViewGroup(binding.TopBoss,false);
        } else {
            setNewFormData();
        }
        binding.fobutton3.setOnClickListener(view1 -> {

            selectUploadType();

        });
        binding.DisplayPDf.setOnClickListener(view1 -> {
            donlowdTheFile(formdata.attachments,getActivity(),getContext());

        });
        setRes(binding.RescView7,Adapter,getContext());
        //ظبط الملفات لرفع
        setTheUpload();

        binding.Location.setOnClickListener(view1 -> {
            setLoavtion();
        });
        binding.Sumbit.setOnClickListener(view1 -> {

            if (getFromUiData()) return ;
            //Log.d("Aboud", "onViewCreated: id" + formdata.request_service_id);
            alertDialog = setAlertMeaage(getString(R.string.getthedata), getContext());
            alertDialog.show();
            if (DoneStatus == 1) {
                NoteBodey noteBodey = new NoteBodey();
                noteBodey.setRequest_service_id(servier_id);
                noteBodey.setReport("تم تعديل الاستعلام");
                taehaedVModel.ConvertDoneToAccept(noteBodey, (status, errorM) -> {
                    if (status) {
                        DoneStatus=0;
                        setDone();

                    } else {
                        Toast.makeText(getContext(), getString(R.string.deletProblen) + " \n " + errorM, Toast.LENGTH_SHORT).show();
                        alertDialog.dismiss();
                    }
                });
            } else {
                setDone();
            }


        });


    }

    private void setNewFormData() {
        if (DoneStatus == 0) {
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

    private void setDone() {
       /* taehaedVModel.setDoneservies(formdata, (status, ErrorMesag) -> {
            if (status) {
                alertDialog.dismiss();
                Toast.makeText(getContext(), getString(R.string.done), Toast.LENGTH_SHORT).show();
                getActivity().onBackPressed();
            } else {
                alertDialog.dismiss();
                Toast.makeText(getContext(), getString(R.string.thereWorng), Toast.LENGTH_SHORT).show();
            }
        });*/
        taehaedVModel.setDoneserviesWithFielsFroms(formdata, attachments, (status, Message) -> {
            if(status)
            {
                alertDialog.dismiss();
                Toast.makeText(getContext(), getString(R.string.thereWorng)+"\n "+ Message, Toast.LENGTH_SHORT).show();
            }else{
                alertDialog.dismiss();
                Toast.makeText(getContext(), getString(R.string.done), Toast.LENGTH_SHORT).show();
                getActivity().onBackPressed();
            }

        });
    }

    private void SetDataToUI() {
        //بيانات العميل
        AgentInfo();
        //بيانات جهة العمل
        JobInfo();
        //بيانات الموارد البشرية
        HrInfo();
        //اضف ملاحظة
        binding.Noteanser.setText(getValue(formdata.note));
        //نتيجة الاستعلام
        AskingOperation();
    }

    private boolean getFromUiData() {

        formdata.request_service_id = servier_id;
        setErrorNullForTextView();
        //بيانات العميل
        if (getAgentInfoValdion()) {
            return true;
        }
        // بيانات جهة العمل
        if (getJobIfoValdtion()) {
            return true;
        }
        //بيانات الموارد البشرية
        if (getHrInfoValdtion()) {
            return true;
        }
        //اضف ملاحظة
        formdata.note = getValue(binding.Noteanser.getText());
        //نتيجة الاستعلام
        if (getAskingInfo()) {
            return true;
        }
        return false;
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
                                attachments.add(aboudfile);
                                setAdpater(result.get(i), aboudfile.getName(), imageFileData,Adapter);
                            } catch (Exception ex) {
                                Log.d("Aboud", "on" +
                                        "ViewCreated: " + result.get(i) + " " + ex.getMessage());
                                Toast.makeText(getContext(), ex.getMessage() + "  " + result.get(i).getPath() + " " + getString(R.string.errorMeshae), Toast.LENGTH_SHORT).show();
                                break;
                            }




                        }


                    }
                });
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


        if (itsNotNull(formdata.attachments)) {
            for (int i = 0; i < formdata.attachments.size(); i++) {
                String name = getValue(formdata.attachments.get(i));

                setAdpater(Uri.parse(name), Uri.parse(name).getLastPathSegment(), imageFileData, Adapter);

            }
            //
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
        } else {
            setErrorTextView(binding.AgnetTrue, getContext());
            return true;
        }
        if (binding.radioJobAdders.getCheckedRadioButtonId() == binding.radioJobAddersCH.getId()) {
            formdata.work_result_employer_address = 0;
        } else if (binding.radioJobAdders.getCheckedRadioButtonId() == binding.radioJobAddersCH2.getId()) {
            formdata.work_result_employer_address = 1;
        } else {

            setErrorTextView(binding.JobTrue, getContext());
            return true;
        }
        if (binding.radioHr.getCheckedRadioButtonId() == binding.radioHrCH.getId()) {
            formdata.work_result_hr_meet = 0;
        } else if (binding.radioHr.getCheckedRadioButtonId() == binding.radioHrCH2.getId()) {
            formdata.work_result_hr_meet = 1;
        } else {

            setErrorTextView(binding.HRTrue, getContext());
            return true;
        }
        if (binding.radioJobPostion.getCheckedRadioButtonId() == binding.radioJobPostionCH.getId()) {
            formdata.work_result_job_title = 0;
        } else if (binding.radioJobPostion.getCheckedRadioButtonId() == binding.radioJobPostionCH2.getId()) {
            formdata.work_result_job_title = 1;
        } else {
            setErrorTextView(binding.JobPostionTrue, getContext());
            return true;
        }
        if (binding.radioSalary.getCheckedRadioButtonId() == binding.radioSalaryCH.getId()) {
            formdata.work_result_income = 0;
        } else if (binding.radioSalary.getCheckedRadioButtonId() == binding.radioSalaryCH2.getId()) {
            formdata.work_result_income = 1;
        } else {
            setErrorTextView(binding.SalaryTrue, getContext());
            return true;
        }

        if (binding.radioSafty.getCheckedRadioButtonId() == binding.radioSaftyCH.getId()) {
            formdata.work_result_insured = 0;
        } else if (binding.radioSafty.getCheckedRadioButtonId() == binding.radiSaftyCH2.getId()) {
            formdata.work_result_insured = 1;
        } else {
            setErrorTextView(binding.IScurTrue, getContext());
            return true;
        }

        if (binding.radioRaptaion.getCheckedRadioButtonId() == binding.radioRaptaionCH.getId()) {
            formdata.work_result_customer_heard = 1;
        } else if (binding.radioRaptaion.getCheckedRadioButtonId() == binding.radioRaptaionCH2.getId()) {
            formdata.work_result_customer_heard = 2;
        } else if (binding.radioRaptaion.getCheckedRadioButtonId() == binding.radioRaptaionCH3.getId()) {
            formdata.work_result_customer_heard = 3;
        } else {
            setErrorTextView(binding.RaptaionTrue, getContext());
        }


        return false;
    }

    private boolean getHrInfoValdtion() {
        if (CheckInputfield(binding.NameOfHr, getContext())) return true;
        if (CheckInputfield(binding.JobTitle, getContext())) return true;
        if (CheckInputfield(binding.Salary, getContext())) return true;
        //مؤمن عليه ام لا
        if (binding.radioSafity.getCheckedRadioButtonId() == binding.radioSafityCH.getId()) {
            formdata.hr_insured = 0;
        } else if (binding.radioSafity.getCheckedRadioButtonId() == binding.radioSafityCH2.getId()) {
            formdata.hr_insured = 1;
        } else {
            setErrorTextView(binding.InscureTrue, getContext());
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
            setErrorTextView(binding.CommentTrue, getContext());
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
        if (CheckInputfield(binding.JobCompanyName, getContext())) return true;
        if (CheckInputfield(binding.BuldingNumber, getContext())) return true;
        if (CheckInputfield(binding.Nieporehod, getContext())) return true;
        if (CheckInputfield(binding.Ciety, getContext())) return true;
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

    private boolean getAgentInfoValdion() {
        // بيانات العميل
        if (CheckInputfield(binding.FullName, getContext())) return true;
        if (setPhoneNumberValdtion(binding.NainolIdNumber, getContext())) return true;
        if (CheckInputfield(binding.PerathData, getContext())) return true;
        if (setPhoneNumberValdtion(binding.PhonNumber, getContext())) return true;
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
        attachments.add(aboudfile);
        setAdpater(uri, aboudfile.getName(), imageFileData,Adapter);

    }
}