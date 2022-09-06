package com.example.taehaed.Screens;

import static com.example.taehaed.Constans.KayMoudleOpration;

import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.taehaed.Constans;
import com.example.taehaed.Pojo.FormReuest.FormData;
import com.example.taehaed.Screens.Fragment.Forms.EighthRealetateFragment;
import com.example.taehaed.Screens.Fragment.Forms.FifithEnquiryComarialFragment;
import com.example.taehaed.Screens.Fragment.Forms.FourthMachineFragment;
import com.example.taehaed.Screens.Fragment.Forms.NinthSuppliersFragment;
import com.example.taehaed.Screens.Fragment.Forms.ScendPlaceFragment;
import com.example.taehaed.Screens.Fragment.Forms.SevenSevriesFragment;
import com.example.taehaed.Screens.Fragment.Forms.SixHomeFragment;
import com.example.taehaed.Screens.Fragment.Forms.TenthMoneyTabelFragment;
import com.example.taehaed.Screens.Fragment.Forms.ThridJobInfoFragment;
import com.example.taehaed.databinding.ActivityOperartionQusetBinding;

import java.io.Serializable;

public class OperartionQuset extends AppCompatActivity implements Serializable  , LocationListener {
    private ActivityOperartionQusetBinding binding;
    private FormData formData;
    private int FormNumber;
    private int requestRequestid;

    private int DoneStatus;
    private ScendPlaceFragment scend;
    private ThridJobInfoFragment thrid;
    private FourthMachineFragment fourth;
    private FifithEnquiryComarialFragment fifth;
    private SixHomeFragment six;
    private SevenSevriesFragment seven;
    private EighthRealetateFragment eighth;
    private NinthSuppliersFragment ninth;
    private TenthMoneyTabelFragment ten;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityOperartionQusetBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        formData= (FormData)getIntent().getSerializableExtra(KayMoudleOpration);
        FormNumber=getIntent().getIntExtra(Constans.FormNumbrActvity,0);
        requestRequestid=getIntent().getIntExtra(Constans.ServisIDFtoA,0);
        DoneStatus=getIntent().getIntExtra(Constans.KeyForDone,0);
        FragmentManager fragmentManager =getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
         //الفورم لو فيها بيانات معني كده انها مكتملة
        //في المستقبل لو تغير الموضوع وبقا اصبح انه يمكن التعديل علي الفورم او حفظ بيانات الفورم .. هيتم التعديل بشكل بسيط علي الكود هنا بم يناسب الاختلاف المطلوب
        if(formData==null)
        {
            //   رقم الفورم احنا جابنها في حالة الفورم غير مكتمل من صفحة الفريجمنت صفحات اللي بتكون في مجلد Delegatacycle
            if(FormNumber!=0)
            {
                switch (FormNumber)
                {
                    case 2:{
                      scend = ScendPlaceFragment.getInstance(requestRequestid);
                        fragmentTransaction.replace(binding.Frgcontainer.getId(),scend);
                        break;
                    }
                    case 3:{
                        thrid = ThridJobInfoFragment.getInstance(requestRequestid);
                        fragmentTransaction.replace(binding.Frgcontainer.getId(),thrid);
                        break;
                    }
                    case 4:{
                         fourth= FourthMachineFragment.newInstance(requestRequestid);
                        fragmentTransaction.replace(binding.Frgcontainer.getId(),fourth);
                        break;
                    }
                    case 5:{
                        fifth= FifithEnquiryComarialFragment.getInstance(requestRequestid);
                        fragmentTransaction.replace(binding.Frgcontainer.getId(),fifth);
                        break;
                    }
                    case 6:{
                        six = SixHomeFragment.newInstance(requestRequestid);
                        fragmentTransaction.replace(binding.Frgcontainer.getId(),six);
                        break;
                    }
                    case 7:{
                         seven= SevenSevriesFragment.newInstance(requestRequestid);
                        fragmentTransaction.replace(binding.Frgcontainer.getId(),seven);
                        break;
                    }
                    case 8:{
                         eighth= EighthRealetateFragment.newInstance(requestRequestid);
                        fragmentTransaction.replace(binding.Frgcontainer.getId(),eighth);
                        break;
                    }
                    case 9:{
                         ninth =  NinthSuppliersFragment.newInstance(requestRequestid);
                        fragmentTransaction.replace(binding.Frgcontainer.getId(),ninth);
                        break;
                    }
                    case 10:{
                        ten =  TenthMoneyTabelFragment.newInstance(requestRequestid);
                        fragmentTransaction.replace(binding.Frgcontainer.getId(),ten);
                        break;
                    }
                }
            }
        }
        else {
           // لو الفورم بقا مكتمل في الحلة دي هنضطر نجيب الفورم من صفحة الاوبريشين  عشان هنغير في قيمتها لانها هتكون بصفر لما نجبيها من صفحات الفجريمنتاس
           //في حالة لو انت واحد جديد وبتسأل ليه اصلا رحلة مندوب طويله كده ؟ الاجابة عشان العميل عاوز كده

            if(FormNumber==0)
            {
                FormNumber = getIntent().getIntExtra(Constans.FormNumberSend,1);

                if(requestRequestid==0)
                {
                    requestRequestid = getIntent().getIntExtra(Constans.RequsetIDSend,1);

                }
                switch (FormNumber)
                {
                    case 2:{
                       scend = ScendPlaceFragment.getInstance(requestRequestid,formData,DoneStatus);
                        fragmentTransaction.replace(binding.Frgcontainer.getId(),scend);
                        break;
                    }
                    case 3:{
                        thrid = ThridJobInfoFragment.getInstance(requestRequestid,formData,DoneStatus);
                        fragmentTransaction.replace(binding.Frgcontainer.getId(),thrid);
                        break;
                    }
                    case 4:{
                        fourth= FourthMachineFragment.newInstance(requestRequestid,formData,DoneStatus);
                        fragmentTransaction.replace(binding.Frgcontainer.getId(),fourth);
                        break;
                    }
                    case 5:{
                        fifth=FifithEnquiryComarialFragment.getInstance(requestRequestid,formData,DoneStatus);
                        fragmentTransaction.replace(binding.Frgcontainer.getId(),fifth);
                        break;
                    }
                    case 6:{
                         six  = SixHomeFragment.newInstance(requestRequestid,formData,DoneStatus);
                        fragmentTransaction.replace(binding.Frgcontainer.getId(),six);
                        break;
                    }
                    case 7:{
                        seven = SevenSevriesFragment.newInstance(requestRequestid,formData,DoneStatus);
                        fragmentTransaction.replace(binding.Frgcontainer.getId(),seven);
                        break;
                    }
                    case 8:{
                       eighth=EighthRealetateFragment.newInstance(requestRequestid,formData,DoneStatus);
                        fragmentTransaction.replace(binding.Frgcontainer.getId(),eighth);
                        break;
                    }
                    case 9:{
                        ninth = NinthSuppliersFragment.newInstance(requestRequestid,formData,DoneStatus);
                        fragmentTransaction.replace(binding.Frgcontainer.getId(),ninth);
                        break;
                    }
                    case 10:{
                        ten =  TenthMoneyTabelFragment.newInstance(requestRequestid,formData,DoneStatus);
                        fragmentTransaction.replace(binding.Frgcontainer.getId(),ten);
                        break;
                    }
                }
            }
        }





        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case 1:
                if(grantResults.length>0&&grantResults[0]== PackageManager.PERMISSION_GRANTED)
                {
                    Toast.makeText(getBaseContext(),"تــــم الحصول علي الصلاحية",Toast.LENGTH_LONG).show();
                }
                return;
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }




    @Override
    public void onLocationChanged(@NonNull Location location) {

    }
}