package com.example.taehaed.Screens.Fragment;

import android.graphics.Point;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.bumptech.glide.Glide;
import com.example.taehaed.Constans;
import com.example.taehaed.Pojo.LogIn.LoginRoot;
import com.example.taehaed.R;
import com.example.taehaed.databinding.FragmentProfileFrgamentBinding;


public class ProfileFrgament extends DialogFragment {
private FragmentProfileFrgamentBinding binding;
private LoginRoot loginRoot;

    public ProfileFrgament() {

    }


    public static ProfileFrgament newInstance(LoginRoot loginRoot) {
        ProfileFrgament fragment = new ProfileFrgament();
        Bundle args = new Bundle();
        args.putSerializable(Constans.ProfilekeyFragmen,loginRoot);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfileFrgamentBinding.inflate(inflater);
        loginRoot= (LoginRoot) getArguments().getSerializable(Constans.ProfilekeyFragmen);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.NameProfile.setText(loginRoot.getEmployee().getUsername());
        binding.ProgfilJob.setText(loginRoot.getEmployee().getType().getString());
        Glide.with(getContext()).load(loginRoot.getEmployee().getImage()).into(binding.ImageCard);
        binding.TextCansel.setOnClickListener(view1 -> {
            getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
        });

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
