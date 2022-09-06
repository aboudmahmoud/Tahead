package com.example.taehaed.Screens.Fragment;

import static com.example.taehaed.Constans.KayAttachmentSsting;
import static com.example.taehaed.Constans.KeyForNamefield;

import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.taehaed.databinding.FragmentPopAttachmentBinding;

import java.util.ArrayList;


public class PopAttachmentFragment extends DialogFragment {
    private FragmentPopAttachmentBinding binding;
    private ArrayList<String> files;
    private  String FieldName;
    //this to Know where is the pdf or image

    private ArrayList<SlideModel> ImageList;
    public PopAttachmentFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static PopAttachmentFragment newInstance(ArrayList<String> filesdata,String FieldName) {
        PopAttachmentFragment fragment = new PopAttachmentFragment();
        Bundle args = new Bundle();
        args.putStringArrayList(KayAttachmentSsting,filesdata);
        args.putString(KeyForNamefield,FieldName);

        /*args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);*/
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
         /*   mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);*/
            files=getArguments().getStringArrayList(KayAttachmentSsting);
            FieldName=getArguments().getString(KeyForNamefield);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPopAttachmentBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageList = new ArrayList<>();
        for (String fileImage:files) {
            ImageList.add(new SlideModel(fileImage, ScaleTypes.FIT));
        }
binding.TextName.setText(FieldName);

    binding.imageSlider.setImageList(ImageList);
            binding.imageSlider.setVisibility(View.VISIBLE);




    }

    @Override
    public void onResume() {
        Window window = getDialog().getWindow();
        Point size = new Point();
        Display display = window.getWindowManager().getDefaultDisplay();
        display.getSize(size);
        window.setLayout( WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        super.onResume();
    }
}