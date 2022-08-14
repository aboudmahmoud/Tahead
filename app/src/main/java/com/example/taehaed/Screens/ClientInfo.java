package com.example.taehaed.Screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.taehaed.Adapters.OperationAdapter;
import com.example.taehaed.Constans;
import com.example.taehaed.Model.TaehaedVModel;
import com.example.taehaed.Screens.Fragment.AgentInfoFragment;
import com.example.taehaed.databinding.ActivityClientInfoBinding;
import com.example.taehaed.Pojo.Index.Request;

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
        SetAgentInfo();

        binding.ImageCard.setOnClickListener(view -> {
            AgentInfoFragment agentInfoFragment= AgentInfoFragment.newInstance(request);
            FragmentManager fragmentManager = ((FragmentActivity) binding.getRoot().getContext()).getSupportFragmentManager();
            agentInfoFragment.show(fragmentManager, "");

        });

        //هنا بيتم اظهار الخدمات
        taehaedVModel.getRqusetOpertatiom(request.getId(), Status -> {
            if (Status) {
                binding.ProggesPar.setVisibility(View.GONE);
                binding.ListOfOperation.setVisibility(View.VISIBLE);

                adapter = new OperationAdapter(taehaedVModel.requsetRootMutableLiveData.getValue(), taehaedVModel);
                binding.ListOfOperation.setAdapter(adapter);

                binding.ListOfOperation.setLayoutManager(new LinearLayoutManager(binding.getRoot().getContext()));

            }
        });
    }

    private void SetAgentInfo() {
        binding.ClinetName.setText(request.getFullname());
        binding.ClinetNam.setText(request.getFullname());
        binding.Adders.setText(request.getActual_address());
        binding.PhonNumber.setText(request.getMobile_number_1());
        binding.DateTaske.setText(request.getCreated_at());

        Glide.with(binding.getRoot().getContext()).load(request.getIdentity_card_photo_front()).into(binding.ImageCard);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        recreate();

    }
}