package com.example.taehaed.Screens.Fragment;

import static com.example.taehaed.Constans.ImageUri;

import android.graphics.Point;
import android.net.Uri;
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

import com.bumptech.glide.Glide;
import com.example.taehaed.databinding.FragmentPopUpImageBinding;


public class PopUpImageFragment extends DialogFragment {
private FragmentPopUpImageBinding binding;
private Uri uri ;
    public PopUpImageFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static PopUpImageFragment newInstance(String uri) {
        PopUpImageFragment fragment = new PopUpImageFragment();
        Bundle args = new Bundle();

        args.putString(ImageUri,uri);
    /*    args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);*/
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            uri = Uri.parse( getArguments().getString(ImageUri));
         /*   mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);*/
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= FragmentPopUpImageBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Glide.with(binding.getRoot().getContext()).load(uri).into(binding.ImageView);
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