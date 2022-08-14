package com.example.taehaed.Screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.example.taehaed.Adapters.jobsAdpater;
import com.example.taehaed.Constans;
import com.example.taehaed.databinding.ActivityAllJobsBinding;
import com.example.taehaed.Pojo.Index.IndexRoot;

import java.io.Serializable;

public class allJobs extends AppCompatActivity implements Serializable {
private ActivityAllJobsBinding binding;
    private jobsAdpater adpater;
    private IndexRoot indexRoot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityAllJobsBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        //We got IndexRoot fromv Main page button jobs or cirren jobs
        indexRoot=(IndexRoot)getIntent().getSerializableExtra(Constans.KayMoudlIdexRoot);
        adpater=new jobsAdpater(indexRoot);
        binding.listOfData.setAdapter(adpater);
        binding.listOfData.setLayoutManager(new LinearLayoutManager(binding.getRoot().getContext()));
        binding.goback.setOnClickListener(view -> {
            finish();
        });
    }
}