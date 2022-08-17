package com.example.taehaed.Screens.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.taehaed.R;


public class CoustemDilogFragment extends DialogFragment {

    private static final String ARG_TiTle="title";
    private static final String ARG_MESSAGE="message";
    private static final String ARG_ICON="icon";

    private  String Title;
    private  String message;
    private OnPostiveButton onPostiveButton;
    private OnNegativeButton onNegativeButton;


public CoustemDilogFragment()
{

}

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof OnPostiveButton)
        {
            onPostiveButton=(OnPostiveButton)context;
        }
        else {
            throw new RuntimeException("Plase make shor that ur implemnt onPostiveButton");
        }

        if(context instanceof OnNegativeButton)
        {
            onNegativeButton=(OnNegativeButton)context;
        }
        else {
            throw new RuntimeException("Plase make shor that ur implemnt onPostiveButton");
        }

    }

    public  static CoustemDilogFragment getInstance(String Title, String message ){
        CoustemDilogFragment fragment = new CoustemDilogFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_TiTle,Title);
        bundle.putString(ARG_MESSAGE,message);

        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle!=null)
        {
            Title = bundle.getString(ARG_TiTle);
            message = bundle.getString(ARG_MESSAGE);

        }

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(Title);
        builder.setMessage(message);

        builder.setIcon(R.drawable.ic_log_out_svgrepo_com);
        builder.setPositiveButton("نعم", (dialogInterface, i) -> {

            onPostiveButton.onpostiveClicked();
        });
        builder.setNegativeButton("لا", (dialogInterface, i) -> {

            onNegativeButton.OnNegativeButtonClicked();
        });

        return builder.create();

    }

    @Override
    public void onDetach() {
        super.onDetach();

        onNegativeButton=null;
        onPostiveButton=null;
    }
    public interface  OnPostiveButton
    {
        void onpostiveClicked();
    }

    public  interface OnNegativeButton
    {
        void OnNegativeButtonClicked();
    }
}