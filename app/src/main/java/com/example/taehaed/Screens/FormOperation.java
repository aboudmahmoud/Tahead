package com.example.taehaed.Screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.example.taehaed.Adapters.FormAdapter;
import com.example.taehaed.Constans;
import com.example.taehaed.Pojo.OperationRequstModifed.OperationServiersRoot;
import com.example.taehaed.databinding.ActivityFormOperationBinding;

import java.io.Serializable;

public class FormOperation extends AppCompatActivity implements Serializable {
private ActivityFormOperationBinding binding;
private OperationServiersRoot operationServiersRoot;

FormAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityFormOperationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        operationServiersRoot = (OperationServiersRoot)getIntent().getSerializableExtra(Constans.KayMoudleOpration);
        adapter= new FormAdapter(operationServiersRoot);
        binding.formFiedls.setAdapter(adapter);
        binding.formFiedls.setLayoutManager(new LinearLayoutManager(binding.getRoot().getContext()));
       // binding.TvQuset.setText(operationServiersRoot.request_sercice.form.);

    }
}