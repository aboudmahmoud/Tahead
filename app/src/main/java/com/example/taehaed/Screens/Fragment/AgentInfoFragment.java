package com.example.taehaed.Screens.Fragment;

import static com.example.taehaed.Constans.getValue;
import static com.example.taehaed.Constans.showMap;

import static java.lang.String.valueOf;

import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;


import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.taehaed.Constans;
import com.example.taehaed.Pojo.Index.Request;

import com.example.taehaed.databinding.FragmentAgentInfoBinding;

import java.util.ArrayList;


public class AgentInfoFragment extends DialogFragment {
   private FragmentAgentInfoBinding binding;
   private Request request;
   private ArrayList<SlideModel> ImageList;
    public AgentInfoFragment() {
        // Required empty public constructor
    }

    public static AgentInfoFragment newInstance(Request request) {
        AgentInfoFragment fragment = new AgentInfoFragment();
        Bundle args = new Bundle();
        args.putSerializable(Constans.ReuestToFramgent,request);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            request=(Request)getArguments().getSerializable(Constans.ReuestToFramgent);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= FragmentAgentInfoBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageList = new ArrayList<>();

        ImageList.add(new SlideModel(request.getIdentity_card_photo_front(),ScaleTypes.FIT));
        ImageList.add(new SlideModel(request.getIdentity_card_photo_back(),ScaleTypes.FIT));
        binding.imageSlider.setImageList(ImageList);
        binding.ClinetNam.setText(getValue(request.getFullname()));
        binding.NickName.setText(getValue(request.getNick_name()));
        binding.Adders.setText(getValue(request.getActual_address()));
        binding.AddersCard.setText(getValue(request.getCard_address()));
        binding.CardNumber.setText(getValue(request.getNational_ID()));
        binding.PhonNumber.setText(getValue(request.getMobile_number_1()));
        binding.PhonNumber2.setText(getValue(request.getMobile_number_2()));
        binding.PhonNumber3.setText(getValue(request.getPhone()));
        binding.Job.setText(getValue(request.getActual_job()));
        binding.JobCard.setText(getValue(request.getCard_job()));


        binding.Adders.setOnClickListener(view1 -> {
            if(!binding.Adders.getText().toString().isEmpty())
            {


                showMap(Uri.parse("geo:0,0?q="+request.getActual_address()),getContext());
            }
        });

        binding.AddersCard.setOnClickListener(view1 -> {
            if(!binding.AddersCard.getText().toString().isEmpty())
            {


                showMap(Uri.parse("geo:0,0?q="+request.getCard_address()),getContext());
            }
        });
    }

    @Override
    public void onResume() {
        Window window = getDialog().getWindow();
        Point size = new Point();
        Display display = window.getWindowManager().getDefaultDisplay();
        display.getSize(size);
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        super.onResume();
    }

}