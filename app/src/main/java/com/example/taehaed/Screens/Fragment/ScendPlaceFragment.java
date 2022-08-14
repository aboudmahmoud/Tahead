package com.example.taehaed.Screens.Fragment;

import static com.example.taehaed.Constans.CheckInputfield;
import static com.example.taehaed.Constans.DESCRIBABLE_KEY;
import static com.example.taehaed.Constans.ErrorMessageValdition;
import static com.example.taehaed.Constans.getValue;
import static com.example.taehaed.Constans.getValueOfboleaan;
import static com.example.taehaed.Constans.itsNotNull;
import static com.example.taehaed.Constans.setAlertMeaage;

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
import com.example.taehaed.Model.ListenersForRespone.StatusApi;
import com.example.taehaed.Model.TaehaedVModel;
import com.example.taehaed.Pojo.FormReuest.FormData;
import com.example.taehaed.Pojo.NoteBodey;
import com.example.taehaed.Pojo.NoteToShow.Note;
import com.example.taehaed.Pojo.Request.RequestService;
import com.example.taehaed.R;
import com.example.taehaed.databinding.FragmentPlaceBinding;

public class ScendPlaceFragment extends Fragment {
    private FormData formdata;
    private FragmentPlaceBinding binding;
    private AlertDialog alertDialog;
    private TaehaedVModel taehaedVModel;
    private int servier_id;

    private boolean homeType;

    public ScendPlaceFragment() {

    }


    public static ScendPlaceFragment getInstance(int id) {
        ScendPlaceFragment fragment = new ScendPlaceFragment();
        Bundle args = new Bundle();
        args.putInt(Constans.IdkeyFrgment, id);
        fragment.setArguments(args);
        return fragment;
    }

    public static ScendPlaceFragment getInstance(int id, FormData formData) {
        ScendPlaceFragment fragment = new ScendPlaceFragment();
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
        binding = FragmentPlaceBinding.inflate(getLayoutInflater());
        taehaedVModel = new ViewModelProvider(this).get(TaehaedVModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        SetDate();
        //Here We set the Data if From data is send it
        SetDataUi();

        binding.Sumbit.setOnClickListener(view1 -> {
            formdata = new FormData();
            if (getFromUiData()) return;

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
        setrRadoisGroubs();
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


    private void SetDataUi() {
        if (formdata != null) {
            binding.Sumbit.setVisibility(View.GONE);
            binding.DeletSumbit.setVisibility(View.VISIBLE);
            //بيانات العميل
            setAgentData();
            //بيانات محل الإقامة
            SetReadinesInfo();
            //بيانات ايصال المرافق
            SetRacietAttach();
            //بيانات الزوج او الزوجة
            SetWHData();
            // الاستعلامات
            SetAskingInfoData();


        }
    }


    private void SetAskingInfoData() {
        if (itsNotNull(formdata.result_personal_data)) {
            if (getValueOfboleaan(formdata.result_personal_data) == 0) {
                binding.radioDataCoustmerCH.setChecked(true);

            } else if (getValueOfboleaan(formdata.result_personal_data) == 1) {

                binding.radioDataCoustmerCH2.setChecked(true);
            }
        }
        if (itsNotNull(formdata.result_residence_data)) {
            if (getValueOfboleaan(formdata.result_residence_data) == 0) {
                binding.radioStayingDataCH.setChecked(true);

            } else if (getValueOfboleaan(formdata.result_residence_data) == 1) {

                binding.radioStayingDataCH2.setChecked(true);
            }
        }
        if (itsNotNull(formdata.result_client_with_same_address)) {
            if (getValueOfboleaan(formdata.result_client_with_same_address) == 0) {
                binding.radioAddersCH.setChecked(true);

            } else if (getValueOfboleaan(formdata.result_client_with_same_address) == 1) {

                binding.radioAddersCH2.setChecked(true);
            }
        }
        if (itsNotNull(formdata.result_attachment_data)) {
            if (getValueOfboleaan(formdata.result_attachment_data) == 0) {
                binding.radioBillCH.setChecked(true);

            } else if (getValueOfboleaan(formdata.result_attachment_data) == 1) {

                binding.radioBillCH2.setChecked(true);
            }
        }
        if (itsNotNull(formdata.result_husband_data)) {
            if (getValueOfboleaan(formdata.result_husband_data) == 0) {
                binding.radioWifeCH.setChecked(true);

            } else if (getValueOfboleaan(formdata.result_husband_data) == 1) {

                binding.radioWifeCH2.setChecked(true);
            }
        }

        if (itsNotNull(formdata.result_family_data)) {
            if (getValueOfboleaan(formdata.result_family_data) == 0) {
                binding.radioFamilyCH.setChecked(true);

            } else if (getValueOfboleaan(formdata.result_family_data) == 1) {

                binding.radioFamilyCH2.setChecked(true);
            }
        }

        if (itsNotNull(formdata.result_client_reputation)) {
            if (getValueOfboleaan(formdata.result_client_reputation) == 1) {
                binding.radioRaptaionCH.setChecked(true);

            } else if (getValueOfboleaan(formdata.result_client_reputation) == 2) {

                binding.radioRaptaionCH2.setChecked(true);


            } else if (getValueOfboleaan(formdata.result_client_reputation) == 3) {

                binding.radioRaptaionCH3.setChecked(true);
            }
        }

        if (itsNotNull(formdata.result_sources)) {
            if (getValueOfboleaan(formdata.result_sources) == 1) {
                binding.radioSourseCH.setChecked(true);

            } else if (getValueOfboleaan(formdata.result_sources) == 2) {

                binding.radioSourseCH2.setChecked(true);


            } else if (getValueOfboleaan(formdata.result_sources) == 3) {

                binding.radioSourseCH3.setChecked(true);
            } else if (getValueOfboleaan(formdata.result_sources) == 4) {

                binding.radioSourseCH4.setChecked(true);
            }

        }


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

    private void SetRacietAttach() {
        binding.CounterNumber.setText(getValue(formdata.attached_counter_number));
        binding.BoardNumber.setText(getValue(formdata.attached_plate_number));
        binding.Monehtlcost.setText(getValue(formdata.attached_average_monthly_consumption));

        //نوع ايصال المرفقب
        if (itsNotNull(formdata.attached_type)) {
            if (getValueOfboleaan(formdata.attached_type) == 1) {
                binding.radio1.setChecked(true);

            } else if (getValueOfboleaan(formdata.attached_type) == 2) {

                binding.radio2.setChecked(true);


            } else if (getValueOfboleaan(formdata.attached_type) == 3) {

                binding.radio3.setChecked(true);
            }
        }
    }

    private void SetReadinesInfo() {
        binding.RentLogic.setText(getValue(formdata.residence_rent_duration));
        binding.RentCost.setText(getValue(formdata.residence_rent_value));
        binding.DataeOfending.setText(getValue(formdata.residence_rent_to));
        binding.Phonehome.setText(getValue(formdata.residence_building_number));
        binding.StreatName.setText(getValue(formdata.residence_street_name));
        binding.Nieporehod.setText(getValue(formdata.residence_neighborhood));
        binding.Ciety.setText(getValue(formdata.residence_city));
        binding.governorate.setText(getValue(formdata.residence_governorate));
        binding.SpeiclSine.setText(getValue(formdata.residence_special_marque));
        binding.TimeOfStaing.setText(getValue(formdata.residence_stay_duration));
        // نوع السكن
        if (itsNotNull(formdata.residence_accommodation_type)) {
            if (getValueOfboleaan(formdata.residence_accommodation_type) == 1) {
                binding.radioButton.setChecked(true);

            } else if (getValueOfboleaan(formdata.residence_accommodation_type) == 2) {

                binding.radioButton2.setChecked(true);

            } else if (getValueOfboleaan(formdata.residence_accommodation_type) == 3) {

                binding.radioButton3.setChecked(true);

            } else if (getValueOfboleaan(formdata.residence_accommodation_type) == 4) {

                binding.radioButton4.setChecked(true);

            } else if (getValueOfboleaan(formdata.residence_accommodation_type) == 5) {

                binding.radioButton5.setChecked(true);
            }
        }
    }

    private void setAgentData() {
        binding.FullName.setText(getValue(formdata.client_name));
        binding.NakeName.setText((getValue(formdata.client_nickname)));
        binding.NainolIdNumber.setText(formdata.client_national_id);
        binding.PerathData.setText(formdata.client_birth_date);
        binding.PhonNumber.setText(formdata.client_mobile_number_1);
        binding.PhonNumber2.setText(formdata.client_mobile_number_2);
        binding.PhonNumber3.setText(formdata.client_phone);
    }


    private boolean getFromUiData() {

        formdata.request_service_id = servier_id;
        //بيانات العميل

        setErrorNullForTextView();
        
        if (getAgentDataValdion()) {
            return true;
        }
        //بيانات محل الإقامة
        if (getResanetDataValditio()) {
            return true;
        }
        //بيانات ايصال المرافق
        if (getReacietAttachValdition()) {
            return true;
        }
        //بيانات الزوج او الزوجة
        if (getWHDataValditon()) {
            return true;
        }

        //الاستعلام
        if (getAskingData()) {
            return true;
        }

        return false;
    }

    private void setErrorNullForTextView() {
        binding.HomeType.setError(null);
        binding.AttachmentRecietType.setError(null);
        binding.AgenetDataIsTrue.setError(null);
        binding.AgentHomeTrue.setError(null);
        binding.AgentStaingTrue.setError(null);
        binding.AttacemtenerTrue.setError(null);
        binding.WHTRue.setError(null);
        binding.FamilyTrue.setError(null);
        binding.Repation.setError(null);
        binding.SoruseTrue.setError(null);
    }

    private boolean getAskingData() {
        if (binding.radioDataCoustmer.getCheckedRadioButtonId() == binding.radioDataCoustmerCH.getId()) {
            formdata.result_personal_data = 0;
        } else if (binding.radioDataCoustmer.getCheckedRadioButtonId() == binding.radioDataCoustmerCH2.getId()) {
            formdata.result_personal_data = 1;
        } else {
            binding.AgenetDataIsTrue.setError(ErrorMessageValdition);
            Toast.makeText(getContext(), ErrorMessageValdition, Toast.LENGTH_SHORT).show();
            return true;
        }
        if (binding.radioStayingData.getCheckedRadioButtonId() == binding.radioStayingDataCH.getId()) {
            formdata.result_residence_data = 0;
        } else if (binding.radioStayingData.getCheckedRadioButtonId() == binding.radioStayingDataCH2.getId()) {
            formdata.result_residence_data = 1;
        } else {
            binding.AgentHomeTrue.setError(ErrorMessageValdition);
            Toast.makeText(getContext(), ErrorMessageValdition, Toast.LENGTH_SHORT).show();
            return true;
        }
        if (binding.radioAdders.getCheckedRadioButtonId() == binding.radioAddersCH.getId()) {
            formdata.result_client_with_same_address = 0;
        } else if (binding.radioAdders.getCheckedRadioButtonId() == binding.radioAddersCH2.getId()) {
            formdata.result_client_with_same_address = 1;
        } else {
            binding.AgentStaingTrue.setError(ErrorMessageValdition);
            Toast.makeText(getContext(), ErrorMessageValdition, Toast.LENGTH_SHORT).show();
            return true;
        }
        if (binding.radioBill.getCheckedRadioButtonId() == binding.radioBillCH.getId()) {
            formdata.result_attachment_data = 0;
        } else if (binding.radioBill.getCheckedRadioButtonId() == binding.radioBillCH2.getId()) {
            formdata.result_attachment_data = 1;
        } else {
            binding.AttacemtenerTrue.setError(ErrorMessageValdition);
            Toast.makeText(getContext(), ErrorMessageValdition, Toast.LENGTH_SHORT).show();
            return true;
        }

        if (binding.radioWife.getCheckedRadioButtonId() == binding.radioWifeCH.getId()) {
            formdata.result_husband_data = 0;
        } else if (binding.radioWife.getCheckedRadioButtonId() == binding.radioWifeCH2.getId()) {
            formdata.result_husband_data = 1;
        } else {
            binding.WHTRue.setError(ErrorMessageValdition);
            Toast.makeText(getContext(), ErrorMessageValdition, Toast.LENGTH_SHORT).show();
            return true;
        }
        if (binding.radioFamily.getCheckedRadioButtonId() == binding.radioFamilyCH.getId()) {
            formdata.result_family_data = 0;
        } else if (binding.radioFamily.getCheckedRadioButtonId() == binding.radioFamilyCH2.getId()) {
            formdata.result_family_data = 1;
        } else {
            binding.FamilyTrue.setError(ErrorMessageValdition);
            Toast.makeText(getContext(), ErrorMessageValdition, Toast.LENGTH_SHORT).show();
            return true;
        }

        if (binding.radioRaptaion.getCheckedRadioButtonId() == binding.radioRaptaionCH.getId()) {
            formdata.result_client_reputation = 1;
        } else if (binding.radioRaptaion.getCheckedRadioButtonId() == binding.radioRaptaionCH2.getId()) {
            formdata.result_client_reputation = 2;
        } else if (binding.radioRaptaion.getCheckedRadioButtonId() == binding.radioRaptaionCH3.getId()) {
            formdata.result_client_reputation = 3;
        } else {
            binding.Repation.setError(ErrorMessageValdition);
            Toast.makeText(getContext(), ErrorMessageValdition, Toast.LENGTH_SHORT).show();
            return true;
        }


        if (binding.radioSourse.getCheckedRadioButtonId() == binding.radioSourseCH.getId()) {
            formdata.result_sources = 1;
        } else if (binding.radioSourse.getCheckedRadioButtonId() == binding.radioSourseCH2.getId()) {
            formdata.result_sources = 2;
        } else if (binding.radioSourse.getCheckedRadioButtonId() == binding.radioSourseCH3.getId()) {
            formdata.result_sources = 3;
        } else if (binding.radioSourse.getCheckedRadioButtonId() == binding.radioSourseCH4.getId()) {
            formdata.result_sources = 4;
        } else {
            binding.SoruseTrue.setError(ErrorMessageValdition);
            Toast.makeText(getContext(), ErrorMessageValdition, Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    private boolean getReacietAttachValdition() {
        //نوع ايصال المرافق
        if (binding.radio1.isChecked()) {
            formdata.attached_type = 1;
        } else if (binding.radio2.isChecked()) {
            formdata.attached_type = 2;
        } else if (binding.radio3.isChecked()) {
            formdata.attached_type = 3;
        } else {
            binding.AttachmentRecietType.setError(ErrorMessageValdition);
            Toast.makeText(getContext(), ErrorMessageValdition, Toast.LENGTH_SHORT).show();
            return true;
        }
        if (CheckInputfield(binding.CounterNumber,getContext())) return true;
        if (CheckInputfield(binding.BoardNumber,getContext())) return true;
        if (CheckInputfield(binding.Monehtlcost,getContext())) return true;

        getReacietAttach();
        return false;
    }

    private void getReacietAttach() {
        formdata.attached_counter_number = getValue(binding.CounterNumber.getText());
        formdata.attached_plate_number = getValue(binding.BoardNumber.getText());
        formdata.attached_average_monthly_consumption = getValue(binding.Monehtlcost.getText());

    }

    private boolean getWHDataValditon() {
        if (CheckInputfield(binding.WHFullName,getContext())) return true;
        if (CheckInputfield(binding.WHNainolIdNumber,getContext())) return true;
        if (CheckInputfield(binding.WHPerathData,getContext())) return true;
        if (CheckInputfield(binding.WHPhonNumber,getContext())) return true;
        if (CheckInputfield(binding.WHFamilyMammaber,getContext())) return true;
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

    private boolean getResanetDataValditio() {

        //بيانات محل الإقامة
        if (binding.radioButton.isChecked()) {
            homeType = false;
            formdata.residence_accommodation_type = 1;
        } else if (binding.radioButton2.isChecked()) {
            homeType = false;
            formdata.residence_accommodation_type = 2;
        } else if (binding.radioButton3.isChecked()) {
            homeType = true;
            formdata.residence_accommodation_type = 3;
        } else if (binding.radioButton4.isChecked()) {
            homeType = true;
            formdata.residence_accommodation_type = 4;
        } else if (binding.radioButton5.isChecked()) {
            homeType = true;
            formdata.residence_accommodation_type = 5;
        } else {
            binding.HomeType.setError(ErrorMessageValdition);
            Toast.makeText(getContext(), ErrorMessageValdition, Toast.LENGTH_SHORT).show();
            return true;
        }
        if (homeType) {


            if (CheckInputfield(binding.RentLogic,getContext())) return true;
            if (CheckInputfield(binding.RentCost,getContext())) return true;
            if (CheckInputfield(binding.DataeOfending,getContext())) return true;

        }
        if (CheckInputfield(binding.Phonehome,getContext())) return true;
        if (CheckInputfield(binding.StreatName,getContext())) return true;
        if (CheckInputfield(binding.Nieporehod,getContext())) return true;
        if (CheckInputfield(binding.Ciety,getContext())) return true;
        if (CheckInputfield(binding.governorate,getContext())) return true;
        if (CheckInputfield(binding.SpeiclSine,getContext())) return true;
        if (CheckInputfield(binding.TimeOfStaing,getContext())) return true;

        getResanetData();
        return false;
    }



    private void getResanetData() {
        formdata.residence_rent_duration = getValue(binding.RentLogic.getText());
        formdata.residence_rent_value = getValue(binding.RentCost.getText());
        formdata.residence_rent_to = getValue(binding.DataeOfending.getText());
        formdata.residence_building_number = getValue(binding.Phonehome.getText());
        formdata.residence_street_name = getValue(binding.StreatName.getText());
        formdata.residence_neighborhood = getValue(binding.Nieporehod.getText());
        formdata.residence_city = getValue(binding.Ciety.getText());
        formdata.residence_governorate = getValue(binding.governorate.getText());
        formdata.residence_special_marque = getValue(binding.SpeiclSine.getText());
        formdata.residence_stay_duration = getValue(binding.TimeOfStaing.getText());


    }

    private boolean getAgentDataValdion() {
        // بيانات العميل
        if (CheckInputfield(binding.FullName,getContext())) return true;
        if (CheckInputfield(binding.NainolIdNumber,getContext())) return true;
        if (CheckInputfield(binding.PerathData,getContext())) return true;
        if (CheckInputfield(binding.PhonNumber,getContext())) return true;
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


    private void setrRadoisGroubs() {

        // All this Code to make shoure whant Radio Button is selected The other is Off
        binding.radioButton.setOnCheckedChangeListener((compoundButton, b) -> {

            if (binding.radioButton.isChecked()) {
                binding.RentColaet.setVisibility(View.GONE);
                if (formdata != null) {
                    formdata.residence_accommodation_type = 1;
                }
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
                binding.RentColaet.setVisibility(View.GONE);
                if (formdata != null) {
                    formdata.residence_accommodation_type = 2;
                }
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
                binding.RentColaet.setVisibility(View.VISIBLE);
                if (formdata != null) {
                    formdata.residence_accommodation_type = 3;
                }
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
                binding.RentColaet.setVisibility(View.VISIBLE);
                if (formdata != null) {
                    formdata.residence_accommodation_type = 4;
                }
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
                binding.RentColaet.setVisibility(View.VISIBLE);
                if (formdata != null) {
                    formdata.residence_accommodation_type = 5;
                }
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

        // All this Code to make shoure whant Radio Button is selected The other is Off
        binding.radio1.setOnCheckedChangeListener((compoundButton, b) -> {

            if (binding.radio1.isChecked()) {
                if (formdata != null) {
                    formdata.attached_type = 1;
                }
                if (binding.radio2.isChecked() || binding.radio3.isChecked()) {
                    binding.radio2.setChecked(false);
                    binding.radio3.setChecked(false);
                }
                //    binding.CheckBox1.setChecked(true);
            }

        });
        binding.radio2.setOnCheckedChangeListener((compoundButton, b) -> {
            if (binding.radio2.isChecked()) {
                if (formdata != null) {
                    formdata.attached_type = 2;
                }
                if (binding.radio1.isChecked() || binding.radio3.isChecked()) {
                    binding.radio1.setChecked(false);
                    binding.radio3.setChecked(false);
                }
                // binding.CheckBox2.setChecked(true);
            }

        });
        binding.radio3.setOnCheckedChangeListener((compoundButton, b) -> {
            if (binding.radio3.isChecked()) {
                if (formdata != null) {
                    formdata.attached_type = 3;
                }
                if (binding.radio1.isChecked() || binding.radio2.isChecked()) {
                    binding.radio1.setChecked(false);
                    binding.radio2.setChecked(false);
                }
                //binding.CheckBox3.setChecked(true);
            }
        });
    }

    private void SetDate() {
        Constans.setDateForInputText(binding.PerathData, getContext());
        Constans.setDateForInputText(binding.WHPerathData, getContext());
        Constans.setDateForInputText(binding.DataeOfending, getContext());
    }


}
