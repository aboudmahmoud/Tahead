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
import static com.example.taehaed.Constans.itsNotNull;
import static com.example.taehaed.Constans.setAdpater;
import static com.example.taehaed.Constans.setAlertMeaage;
import static com.example.taehaed.Constans.setDateForInputText;
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
import com.example.taehaed.databinding.FragmentTenthMoneyTabelBinding;

import java.io.File;
import java.util.ArrayList;

public class TenthMoneyTabelFragment extends Fragment implements ImageTakeIt {
    private int servier_id, DoneStatus;
    private FormData formdata;
    private AlertDialog alertDialog;
    TaehaedVModel taehaedVModel;
    private FragmentTenthMoneyTabelBinding binding;

    private ArrayList<ImageFileData> imageFileData;
    private ImageFileApabter Adapter;
    private ArrayList<File> attachments;
    private File aboudfile;
    ActivityResultLauncher<String> activityResultLauncher;

    public TenthMoneyTabelFragment() {

    }

    public static TenthMoneyTabelFragment newInstance(int id) {
        TenthMoneyTabelFragment fragment = new TenthMoneyTabelFragment();
        Bundle args = new Bundle();
        args.putInt(Constans.IdkeyFrgment, id);
        fragment.setArguments(args);
        return fragment;
    }

    public static TenthMoneyTabelFragment newInstance(int id, FormData formData, int DoneStatus) {
        TenthMoneyTabelFragment fragment = new TenthMoneyTabelFragment();
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
        attachments= new ArrayList<>();
        Adapter=new ImageFileApabter();
        imageFileData = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentTenthMoneyTabelBinding.inflate(inflater);
        taehaedVModel = new ViewModelProvider(this).get(TaehaedVModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setDateForInputText(binding.PerathData, getContext());
        setDateForInputText(binding.ComarialDate, getContext());
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

    private void setDone() {
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

    private boolean getFromUiData() {
        formdata.request_service_id = servier_id;
        //بيانات العميل
        if (getAgentInfoValdion()) {
            return true;
        }
        //بيانات النشاط
        if(getActivitDataValdion())
        {
            return true;
        }
        //الدخل الشهري
        if(getTotalIcomeValdion())
        {
            return true;
        }
        // //المصروفات الشهرية المرتبطة بالنشاط
        if(getCostOfActivtValdion()){
            return true;
        }
        //الادخار الشهري
        if(getTotalSavingValdion()){
            return true;
        }
        ////المصروفات الشهرية المرتبطة بالعميل
        if(getTheAhentcostaValdion()){
            return true;
        }

        return false;
    }

    private boolean getTheAhentcostaValdion() {
        if (CheckInputfield(binding.renteaForAgent, getContext())) return true;
        if (CheckInputfield(binding.ElecteicyAgent, getContext())) return true;
        if (CheckInputfield(binding.GasAgent, getContext())) return true;
        if (CheckInputfield(binding.WataerAgent, getContext())) return true;
        if (CheckInputfield(binding.InternatAgent, getContext())) return true;
        if (CheckInputfield(binding.HomeCosting, getContext())) return true;
        if (CheckInputfield(binding.StudyCosting, getContext())) return true;
        if (CheckInputfield(binding.HelathyCostiing, getContext())) return true;
        if (CheckInputfield(binding.MovmentCosting, getContext())) return true;
        if (CheckInputfield(binding.CarCosting, getContext())) return true;
        if (CheckInputfield(binding.Carpayment, getContext())) return true;
        if (CheckInputfield(binding.CarInrusent, getContext())) return true;
        if (CheckInputfield(binding.baymentCard, getContext())) return true;
        if (CheckInputfield(binding.PersnolInvesment, getContext())) return true;
        if (CheckInputfield(binding.TervalInjouy, getContext())) return true;
        if (CheckInputfield(binding.atf, getContext())) return true;
        getTheAhentcosta();
        return false;


    }

    private void getTheAhentcosta() {
        formdata.financial_monthly_work_customer_rent=getValue( binding.renteaForAgent.getText());
        formdata.financial_monthly_work_customer_electricity=getValue( binding.ElecteicyAgent.getText());
        formdata.financial_monthly_work_customer_water=getValue( binding.WataerAgent.getText());
        formdata.financial_monthly_work_customer_internet_communications=getValue( binding.InternatAgent.getText());
        formdata.financial_monthly_work_customer_household_expenses=getValue( binding.HomeCosting.getText());
        formdata.financial_monthly_work_customer_tuition_fees=getValue( binding.StudyCosting.getText());
        formdata.financial_monthly_work_customer_medical_expenses=getValue( binding.HelathyCostiing.getText());
        formdata.financial_monthly_work_customer_transportation_expenses=getValue( binding.MovmentCosting.getText());
        formdata.financial_monthly_work_customer_car_expenses=getValue( binding.CarCosting.getText());
        formdata.financial_monthly_work_customer_car_premium=getValue( binding.Carpayment.getText());
        formdata.financial_monthly_work_customer_car_insurance=getValue( binding.CarInrusent.getText());
        formdata.financial_monthly_work_customer_visa_Purchases=getValue( binding.baymentCard.getText());
        formdata.financial_monthly_work_customer_personal_finance_installment=getValue( binding.PersnolInvesment.getText());
        formdata.financial_monthly_work_customer_travel_leisure=getValue( binding.TervalInjouy.getText());
        formdata.financial_monthly_work_customer_miscellaneous=getValue( binding.atf.getText());


    }

    private boolean getTotalSavingValdion() {
        if (CheckInputfield(binding.wdf, getContext())) return true;
        if (CheckInputfield(binding.Relation, getContext())) return true;
        if (CheckInputfield(binding.otherSaving, getContext())) return true;
        getTotalSaving();
        return false;

    }

    private void getTotalSaving() {
        formdata.financial_monthly_savings_savings_book=getValue( binding.wdf.getText());
        formdata.financial_monthly_savings_associations=getValue( binding.Relation.getText());
        formdata.financial_monthly_savings_other=getValue( binding.otherSaving.getText());

    }

    private boolean getCostOfActivtValdion() {
        if (CheckInputfield(binding.TotalCostOfItems, getContext())) return true;
        if (CheckInputfield(binding.rentea, getContext())) return true;
        if (CheckInputfield(binding.Electeicy, getContext())) return true;
        if (CheckInputfield(binding.Gas, getContext())) return true;
        if (CheckInputfield(binding.Wataer, getContext())) return true;
        if (CheckInputfield(binding.Internat, getContext())) return true;
        if (CheckInputfield(binding.salrys, getContext())) return true;
        if (CheckInputfield(binding.Teaxaes, getContext())) return true;
        if (CheckInputfield(binding.MovmentandShall, getContext())) return true;
        if (CheckInputfield(binding.helahtyInsur, getContext())) return true;
        if (CheckInputfield(binding.TotalPunish, getContext())) return true;

        getCostOfActivt();
        return false;


    }

    private void getCostOfActivt() {

        formdata.financial_monthly_expenses_actively_rent=getValue( binding.rentea.getText());
        formdata.financial_monthly_expenses_actively_electricity=getValue( binding.Electeicy.getText());
        formdata.financial_monthly_expenses_actively_gas=getValue( binding.Gas.getText());
        formdata.financial_monthly_expenses_actively_waters=getValue( binding.Wataer.getText());
        formdata.financial_monthly_expenses_actively_internet_communications=getValue( binding.Internat.getText());
        formdata.financial_monthly_expenses_actively_salaries=getValue( binding.salrys.getText());
        formdata.financial_monthly_expenses_actively_taxes_insurances=getValue( binding.Teaxaes.getText());
        formdata.financial_monthly_expenses_actively_transfer_mashal=getValue( binding.MovmentandShall.getText());
        formdata.financial_monthly_expenses_actively_health_insurance=getValue( binding.helahtyInsur.getText());
        formdata.financial_monthly_expenses_actively_finance_installments=getValue( binding.TotalPunish.getText());


    }

    private boolean getTotalIcomeValdion() {
        if (CheckInputfield(binding.Totalselse, getContext())) return true;
        if (CheckInputfield(binding.totalSoursIncome, getContext())) return true;
        getTotalIcome();
        return false;
    }

    private void getTotalIcome() {
        formdata.financial_monthly_savings_total_sales=getValue( binding.Totalselse.getText());
        formdata.financial_monthly_savings_total_other_sources_income=getValue( binding.totalSoursIncome.getText());
    }

    private boolean getActivitDataValdion() {
        if (CheckInputfield(binding.ComarialName, getContext())) return true;
        if (CheckInputfield(binding.ComarialDate, getContext())) return true;
        if (CheckInputfield(binding.ComarialType, getContext())) return true;
        if (CheckInputfield(binding.ComariaBuldingType, getContext())) return true;
        if (CheckInputfield(binding.addersActivity, getContext())) return true;
        if (CheckInputfield(binding.SpeiaclSiend, getContext())) return true;
        getActivitData();
        return false;
    }

    private void getActivitData() {
        formdata.financial_trade_name = getValue(binding.ComarialName.getText());
        formdata.financial_activity_start_date = getValue(binding.ComarialDate.getText());
        formdata.financial_merit_activity_type = getValue(binding.ComarialType.getText());
        formdata.financial_activity_location_type = getValue(binding.ComariaBuldingType.getText());
        formdata.financial_activity_title = getValue(binding.addersActivity.getText());
        formdata.financial_special_marque = getValue(binding.SpeiaclSiend.getText());

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

    private void SetDataToUI() {
        //بيانات العميل
        AgentInfo();

        //بيانات النشاط
        setActivitData();

       //الدخل الشهرى
        setTotalIcome();

        //المصروفات الشهرية المرتبطة بالنشاط
        setCostOfActivty();

        //الادخار الشهري
        setTotalSaving();

        ////المصروفات الشهرية المرتبطة بالعميل
        setTheAhentcosta();

    }

    private void setTheAhentcosta() {
        binding.renteaForAgent.setText((getValue(formdata.financial_monthly_work_customer_rent)));
        binding.ElecteicyAgent.setText((getValue(formdata.financial_monthly_work_customer_electricity)));
        binding.GasAgent.setText((getValue(formdata.financial_monthly_work_customer_gas)));
        binding.WataerAgent.setText((getValue(formdata.financial_monthly_work_customer_water)));
        binding.InternatAgent.setText((getValue(formdata.financial_monthly_work_customer_internet_communications)));
        binding.HomeCosting.setText((getValue(formdata.financial_monthly_work_customer_household_expenses)));
        binding.StudyCosting.setText((getValue(formdata.financial_monthly_work_customer_tuition_fees)));
        binding.HelathyCostiing.setText((getValue(formdata.financial_monthly_work_customer_medical_expenses)));
        binding.MovmentCosting.setText((getValue(formdata.financial_monthly_work_customer_transportation_expenses)));
        binding.CarCosting.setText((getValue(formdata.financial_monthly_work_customer_car_expenses)));
        binding.Carpayment.setText((getValue(formdata.financial_monthly_work_customer_car_premium)));
        binding.CarInrusent.setText((getValue(formdata.financial_monthly_work_customer_car_insurance)));
        binding.baymentCard.setText((getValue(formdata.financial_monthly_work_customer_visa_Purchases)));
        binding.PersnolInvesment.setText((getValue(formdata.financial_monthly_work_customer_personal_finance_installment)));
        binding.TervalInjouy.setText((getValue(formdata.financial_monthly_work_customer_travel_leisure)));
        binding.atf.setText((getValue(formdata.financial_monthly_work_customer_miscellaneous)));
        // المفرقات
        if (itsNotNull(formdata.attachments)) {
            for (int i = 0; i < formdata.attachments.size(); i++) {
                String name = getValue(formdata.attachments.get(i));

                setAdpater(Uri.parse(name), Uri.parse(name).getLastPathSegment(), imageFileData, Adapter);

            }
            //
        }
    }

    private void setTotalSaving() {
        binding.wdf.setText((getValue(formdata.financial_monthly_savings_savings_book)));
        binding.Relation.setText((getValue(formdata.financial_monthly_savings_associations)));
        binding.otherSaving.setText((getValue(formdata.financial_monthly_savings_other)));
    }

    private void setCostOfActivty() {
        binding.TotalCostOfItems.setText((getValue(formdata.financial_monthly_expenses_actively_total_cost)));
        binding.rentea.setText((getValue(formdata.financial_monthly_expenses_actively_rent)));
        binding.Electeicy.setText((getValue(formdata.financial_monthly_expenses_actively_electricity)));
        binding.Gas.setText((getValue(formdata.financial_monthly_expenses_actively_gas)));
        binding.Wataer.setText((getValue(formdata.financial_monthly_expenses_actively_waters)));
        binding.Internat.setText((getValue(formdata.financial_monthly_expenses_actively_internet_communications)));
        binding.salrys.setText((getValue(formdata.financial_monthly_expenses_actively_salaries)));
        binding.Teaxaes.setText((getValue(formdata.financial_monthly_expenses_actively_taxes_insurances)));
        binding.MovmentandShall.setText((getValue(formdata.financial_monthly_expenses_actively_transfer_mashal)));
        binding.helahtyInsur.setText((getValue(formdata.financial_monthly_expenses_actively_health_insurance)));
        binding.TotalPunish.setText((getValue(formdata.financial_monthly_expenses_actively_finance_installments)));
    }

    private void setTotalIcome() {
        binding.Totalselse.setText(getValue(formdata.financial_monthly_savings_total_sales));
        binding.totalSoursIncome.setText((getValue(formdata.financial_monthly_savings_total_other_sources_income)));
    }

    private void setActivitData() {
        binding.ComarialName.setText(getValue(formdata.financial_trade_name));
        binding.ComarialDate.setText((getValue(formdata.financial_activity_start_date)));
        binding.ComarialType.setText(getValue(formdata.financial_merit_activity_type));
        binding.ComariaBuldingType.setText(getValue(formdata.financial_activity_location_type));
        binding.addersActivity.setText(getValue(formdata.financial_activity_title));
        binding.SpeiaclSiend.setText(getValue(formdata.financial_special_marque));
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

    private void setNewFormData() {
        if (DoneStatus == 0) {
            formdata = new FormData();
        }
    }

    @Override
    public void imageDone(Uri uri) {
        aboudfile = new File(FileUtil.getPath(uri, getContext()));
        attachments.add(aboudfile);
        setAdpater(uri, aboudfile.getName(), imageFileData,Adapter);
    }
}