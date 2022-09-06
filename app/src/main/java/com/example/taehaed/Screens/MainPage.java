package com.example.taehaed.Screens;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.taehaed.Adapters.jobsAdpater;
import com.example.taehaed.Constans;
import com.example.taehaed.Model.TaehaedVModel;
import com.example.taehaed.Pojo.LogIn.LoginRoot;
import com.example.taehaed.R;
import com.example.taehaed.Screens.Fragment.CoustemDilogFragment;
import com.example.taehaed.Screens.Fragment.ProfileFrgament;
import com.example.taehaed.databinding.ActivityMainPageBinding;
import com.google.android.material.navigation.NavigationView;

import java.io.Serializable;

public class MainPage extends AppCompatActivity implements Serializable , NavigationView.OnNavigationItemSelectedListener , CoustemDilogFragment.OnPostiveButton, CoustemDilogFragment.OnNegativeButton {
    private ActivityMainPageBinding binding;
    private TaehaedVModel taehaedVModel;

    private AlertDialog alertDialog;
    //loginRoot is  we Get Modle Data of our use
    private LoginRoot loginRoot;
    private SharedPreferences sharedPreferences;
    private jobsAdpater adpater;
private CoustemDilogFragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        binding = ActivityMainPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.CLientHadder);
         sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);



        taehaedVModel = new ViewModelProvider(this).get(TaehaedVModel.class);


        //In This Method We get Number Of jobs
        SetRecylerViewData();
        //Here in ReeFesh
        binding.swiperefresh.setOnRefreshListener(() -> {
            SetRecylerViewData();
            binding.swiperefresh.setRefreshing(false);
        });

        //ForNavDrwaer
        binding.Freg.setOnClickListener(view -> {
            binding.MainPage.openDrawer(GravityCompat.END);
        });

        alertDialog =Constans.setAlertMeaage(getString(R.string.orderget),this);


        /*Here I am fetching user data.. There are two ways to get data
The first way I can store his data from the first login process
The second way is that he is already logged in*/
        loginRoot=(LoginRoot)getIntent().getSerializableExtra(Constans.LoginKeyMain);
        if(  loginRoot==null)
        {
            loginRoot=(LoginRoot)getIntent().getSerializableExtra(Constans.LoginKeyLoginPage);
        }
        binding.Fg.setNavigationItemSelectedListener(this);

    }

    private void SetRecylerViewData() {
        binding.ProgeesPar.setVisibility(View.VISIBLE);
        binding.TvMessage.setVisibility(View.GONE);
        binding.listOfData.setVisibility(View.INVISIBLE);
        taehaedVModel.getIndexes((status ,message) -> {
            if(status)
            {
                adpater = new jobsAdpater(taehaedVModel.indexRootMutableLiveData.getValue());
                binding.listOfData.setAdapter(adpater);
                binding.listOfData.setLayoutManager(new LinearLayoutManager(binding.getRoot().getContext()));
                binding.ProgeesPar.setVisibility(View.GONE);
                binding.listOfData.setVisibility(View.VISIBLE);
                binding.TvMessage.setVisibility(View.GONE);
            }
            else{
                Toast.makeText(this, getString(R.string.errortype)+" " + message , Toast.LENGTH_SHORT).show();
                binding.ProgeesPar.setVisibility(View.INVISIBLE);
                binding.TvMessage.setVisibility(View.VISIBLE);
                binding.listOfData.setVisibility(View.GONE);

            }
        });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id==R.id.pofile)
        {
            //profilePageWithInfo
            ProfileFrgament profileFrgament = ProfileFrgament.newInstance(loginRoot);
                FragmentManager fragmentManager =getSupportFragmentManager();
                profileFrgament.show(fragmentManager,"");
                profileFrgament.setCancelable(false);


        }
        if(id==R.id.Logout)
        {
            //here is logout but listner
            fragment = CoustemDilogFragment.getInstance(getString(R.string.logOut),getString(R.string.areushor));
            fragment.show(getSupportFragmentManager(),null);

        }
        return false;
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        SetRecylerViewData();
    }

    @Override
    public void onpostiveClicked() {
        alertDialog =Constans.setAlertMeaage(getString(R.string.LogginfOut),MainPage.this);
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
                Toast.makeText(this, getString(R.string.ErrorHappend), Toast.LENGTH_SHORT).show();
            }


        });
    }

    @Override
    public void OnNegativeButtonClicked() {
        fragment.dismiss();
    }
}