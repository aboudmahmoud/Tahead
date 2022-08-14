package com.example.taehaed.Screens;

import static com.example.taehaed.Constans.KayMoudleOpration;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.widget.Switch;

import com.example.taehaed.Constans;
import com.example.taehaed.Model.TaehaedVModel;
import com.example.taehaed.Pojo.FormReuest.FormData;
import com.example.taehaed.Pojo.Request.RequestService;
import com.example.taehaed.Screens.Fragment.EighthRealetateFragment;
import com.example.taehaed.Screens.Fragment.FifithEnquiryComarialFragment;
import com.example.taehaed.Screens.Fragment.FourthMachineFragment;
import com.example.taehaed.Screens.Fragment.NinthSuppliersFragment;
import com.example.taehaed.Screens.Fragment.ScendPlaceFragment;
import com.example.taehaed.Screens.Fragment.SevenSevriesFragment;
import com.example.taehaed.Screens.Fragment.SixHomeFragment;
import com.example.taehaed.Screens.Fragment.ThridJobInfoFragment;
import com.example.taehaed.databinding.ActivityOperartionQusetBinding;

import java.io.Serializable;

public class OperartionQuset extends AppCompatActivity implements Serializable {
    private ActivityOperartionQusetBinding binding;
    private FormData formData;
    private int FormNumber;
    private int requestRequestid;
    private TaehaedVModel taehaedVModel;
    private ScendPlaceFragment scend;
    private ThridJobInfoFragment thrid;
    private FourthMachineFragment fourth;
    private FifithEnquiryComarialFragment fifth;
    private SixHomeFragment six;
    private SevenSevriesFragment seven;
    private EighthRealetateFragment eighth;
    private NinthSuppliersFragment ninth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityOperartionQusetBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        taehaedVModel=  new ViewModelProvider(this).get(TaehaedVModel.class);
        formData= (FormData)getIntent().getSerializableExtra(KayMoudleOpration);
        FormNumber=getIntent().getIntExtra(Constans.FormNumbrActvity,0);
        requestRequestid=getIntent().getIntExtra(Constans.ServisIDFtoA,0);
        FragmentManager fragmentManager =getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//الفورم لو فيها بيانات معني كده انها مكتملة
        //في المستقبل لو تغير الموضوع وبقا اصبح انه يمكن التعديل علي الفورم او حفظ بيانات الفورم .. هيتم التعديل بشكل بسيط علي الكود هنا بم يناسب الاختلاف المطلوب
        if(formData==null)
        {
            // رقم الفورم احنا جابنها في حالة الفورم غير مكتمل من صفحة الفريجمنت
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
                }
            }
        }
        else {
           //لو الفورم بقا مكتمل في الحلة دي هنضطر نجيب الفورم من صفحة الاوبريشين  عشان هنغير في قيمتها لانها هتكون بصفر لما نجبيها من صفحة السيرفس
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
                       scend = ScendPlaceFragment.getInstance(requestRequestid,formData);
                        fragmentTransaction.replace(binding.Frgcontainer.getId(),scend);
                        break;
                    }
                    case 3:{
                        thrid = ThridJobInfoFragment.getInstance(requestRequestid,formData);
                        fragmentTransaction.replace(binding.Frgcontainer.getId(),thrid);
                        break;
                    }
                    case 4:{
                        fourth= FourthMachineFragment.newInstance(requestRequestid,formData);
                        fragmentTransaction.replace(binding.Frgcontainer.getId(),fourth);
                        break;
                    }
                    case 5:{
                        fifth=FifithEnquiryComarialFragment.getInstance(requestRequestid,formData);
                        fragmentTransaction.replace(binding.Frgcontainer.getId(),fifth);
                        break;
                    }
                    case 6:{
                         six  = SixHomeFragment.newInstance(requestRequestid,formData);
                        fragmentTransaction.replace(binding.Frgcontainer.getId(),six);
                        break;
                    }
                    case 7:{
                        seven = SevenSevriesFragment.newInstance(requestRequestid,formData);
                        fragmentTransaction.replace(binding.Frgcontainer.getId(),seven);
                        break;
                    }
                    case 8:{
                       eighth=EighthRealetateFragment.newInstance(requestRequestid,formData);
                        fragmentTransaction.replace(binding.Frgcontainer.getId(),eighth);
                        break;
                    }
                    case 9:{
                        ninth = NinthSuppliersFragment.newInstance(requestRequestid,formData);
                        fragmentTransaction.replace(binding.Frgcontainer.getId(),ninth);
                        break;
                    }
                }
            }
        }





        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}