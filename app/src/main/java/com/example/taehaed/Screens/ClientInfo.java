package com.example.taehaed.Screens;

import static com.example.taehaed.Constans.showMap;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.example.taehaed.Adapters.OperationAdapter;
import com.example.taehaed.Constans;
import com.example.taehaed.Model.TaehaedVModel;
import com.example.taehaed.Pojo.Index.Request;
import com.example.taehaed.R;
import com.example.taehaed.Screens.Fragment.AgentInfoFragment;
import com.example.taehaed.databinding.ActivityClientInfoBinding;

import java.io.Serializable;

public class ClientInfo extends AppCompatActivity implements Serializable {
    private ActivityClientInfoBinding binding;
    private Request request;
    private TaehaedVModel taehaedVModel;
    private OperationAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityClientInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        taehaedVModel = new ViewModelProvider(this).get(TaehaedVModel.class);
        request = (Request) getIntent().getSerializableExtra(Constans.KayMoudlIdexRoot2);

        //هنا بيتم ظبط بيانات العميل
        SetAgentInfo();



        binding.More.setOnClickListener(view -> {
            AgentInfoFragment agentInfoFragment= AgentInfoFragment.newInstance(request);
            FragmentManager fragmentManager = ((FragmentActivity) binding.getRoot().getContext()).getSupportFragmentManager();
            agentInfoFragment.show(fragmentManager, "");
        });

        //هنا بيتم اظهار الخدمات
        Showservies();
        binding.Refresh.setOnRefreshListener(() -> {
            Showservies();
            binding.ProggesPar.setVisibility(View.VISIBLE);
            binding.ListOfOperation.setVisibility(View.INVISIBLE);

            binding.Refresh.setRefreshing(false);
        });

        //هنا لو ضغط علي العنوان نص هيروح علي اللوكيشين من مابس
        binding.Adders.setOnClickListener(view -> {
            if(!binding.Adders.getText().toString().isEmpty())
            {


                showMap(Uri.parse("geo:0,0?q="+request.getActual_address()),this);
            }
        });


    }

    private void Showservies() {
        taehaedVModel.getRqusetOpertatiom(request.getId(), (Status ,mes)  -> {
            if (Status) {
                binding.ProggesPar.setVisibility(View.GONE);
                binding.ListOfOperation.setVisibility(View.VISIBLE);
                binding.Servies.setVisibility(View.VISIBLE);
                adapter = new OperationAdapter(taehaedVModel.requsetRootMutableLiveData.getValue(), taehaedVModel);
                binding.ListOfOperation.setAdapter(adapter);

                binding.ListOfOperation.setLayoutManager(new LinearLayoutManager(binding.getRoot().getContext()));

            }else{

                Toast.makeText(this,getString( R.string.Erromesg) +" "+ mes, Toast.LENGTH_SHORT).show();
                binding.ProggesPar.setVisibility(View.VISIBLE);
                binding.ListOfOperation.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void SetAgentInfo() {
        binding.ClinetName.setText(request.getOrganization().getName());
        binding.ClinetNam.setText(request.getFullname());
        binding.Adders.setText(request.getActual_address());

        binding.DateTaske.setText(request.getCreated_at());

       binding.RequstAgentNumber.setText(""+request.getId());
        Glide.with(binding.getRoot().getContext()).load(request.getOrganization().getImage()).into(binding.CardEgypt);

    }

  @Override
    protected void onRestart() {
        super.onRestart();
      Showservies();

    }


}