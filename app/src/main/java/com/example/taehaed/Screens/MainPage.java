package com.example.taehaed.Screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.taehaed.Adapters.jobsAdpater;
import com.example.taehaed.Constans;
import com.example.taehaed.Model.TaehaedVModel;
import com.example.taehaed.Pojo.LogIn.LoginRoot;
import com.example.taehaed.R;
import com.example.taehaed.Screens.Fragment.ProfileFrgament;

import com.example.taehaed.databinding.ActivityMainPageBinding;
import com.example.taehaed.Pojo.home.HomeRoot;
import com.google.android.material.navigation.NavigationView;


import java.io.Serializable;

public class MainPage extends AppCompatActivity implements Serializable , NavigationView.OnNavigationItemSelectedListener {
    private ActivityMainPageBinding binding;
    private TaehaedVModel taehaedVModel;
    private HomeRoot routea;
    //this Insated of ProggesBar

    private AlertDialog alertDialog;
    private LoginRoot loginRoot;
    private ProfileFrgament profileFrgament;
    private SharedPreferences sharedPreferences;
    private jobsAdpater adpater;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        binding = ActivityMainPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.CLientHadder);
         sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);



        taehaedVModel = new ViewModelProvider(this).get(TaehaedVModel.class);


        taehaedVModel.getIndexes(status -> {
            if(status)
            {
                adpater = new jobsAdpater(taehaedVModel.indexRootMutableLiveData.getValue());
                binding.listOfData.setAdapter(adpater);
                binding.listOfData.setLayoutManager(new LinearLayoutManager(binding.getRoot().getContext()));
                binding.ProgeesPar.setVisibility(View.GONE);
                binding.listOfData.setVisibility(View.VISIBLE);
            }
            else{
                Toast.makeText(this, "عفوا يبدو ان هناك خطأ في تحميل الوظائف ", Toast.LENGTH_SHORT).show();
                binding.ProgeesPar.setVisibility(View.INVISIBLE);
            }
        });


        binding.Freg.setOnClickListener(view -> {
            binding.MainPage.openDrawer(GravityCompat.END);
        });
        alertDialog =Constans.setAlertMeaage("جار تحميل الطلبات",this);



        loginRoot=(LoginRoot)getIntent().getSerializableExtra(Constans.LoginKeyMain);
        if(  loginRoot==null)
        {
            loginRoot=(LoginRoot)getIntent().getSerializableExtra(Constans.LoginKeyLoginPage);
        }
        binding.Fg.setNavigationItemSelectedListener(this);

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id==R.id.pofile)
        {
              profileFrgament = ProfileFrgament.newInstance(loginRoot);
                FragmentManager fragmentManager =getSupportFragmentManager();
                profileFrgament.show(fragmentManager,"");
                profileFrgament.setCancelable(false);


        }
        if(id==R.id.Logout)
        {
            alertDialog =Constans.setAlertMeaage("جار تسجيل الخروج",MainPage.this);
            alertDialog.show();
            taehaedVModel.DeletUserToken(status -> {
                if (status)
                {
                    alertDialog.dismiss();
                    sharedPreferences.edit().clear().apply();
                    Intent intent = new Intent(MainPage.this, LoginPage.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    alertDialog.dismiss();
                    Toast.makeText(this, "عفوا يبدو ان هناك خطأ قد حصل ", Toast.LENGTH_SHORT).show();
                }


            });


        }
        return false;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        recreate();
    }
}