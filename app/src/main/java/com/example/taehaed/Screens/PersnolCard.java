package com.example.taehaed.Screens;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.taehaed.databinding.ActivityPersnolCardBinding;

public class PersnolCard extends AppCompatActivity {
private ActivityPersnolCardBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityPersnolCardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}