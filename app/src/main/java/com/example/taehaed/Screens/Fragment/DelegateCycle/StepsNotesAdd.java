package com.example.taehaed.Screens.Fragment.DelegateCycle;

import static com.example.taehaed.Constans.NotesKey;
import static com.example.taehaed.Constans.setAlertMeaage;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.taehaed.Model.TaehaedVModel;
import com.example.taehaed.Pojo.NoteBodey;
import com.example.taehaed.R;
import com.example.taehaed.databinding.FragmentStepsNotesAddBinding;


public class StepsNotesAdd extends DialogFragment {

    private FragmentStepsNotesAddBinding stepsNotesAddBinding;
    private AlertDialog alertDialog;
    private OnClickkSend onClickkSend;
    private int SeriverId;
    private NoteBodey noteBodey;
    private TaehaedVModel taehaedVModel;
    public StepsNotesAdd() {
        // Required empty public constructor
    }



    public static StepsNotesAdd newInstance(int id) {
        StepsNotesAdd fragment = new StepsNotesAdd();
        Bundle args = new Bundle();
       // args.putString(ARG_PARAM1, param1);
        args.putInt(NotesKey,id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        super.onAttach(context);
        if(context instanceof StepsNotesAdd.OnClickkSend)
        {
            onClickkSend=(StepsNotesAdd.OnClickkSend)context;
        }
        else {
            throw new RuntimeException(getString(R.string.Message));
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

            SeriverId=getArguments().getInt(NotesKey);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        stepsNotesAddBinding=FragmentStepsNotesAddBinding.inflate(getLayoutInflater());
        taehaedVModel = new ViewModelProvider(requireActivity()).get(TaehaedVModel.class);
       return stepsNotesAddBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        stepsNotesAddBinding.ButtonCoremit.setOnClickListener(view1 -> {
            if(stepsNotesAddBinding.Noteanser.getText().toString().isEmpty())
            {
                stepsNotesAddBinding.Noteanser.setError(getString(R.string.dontLettheField));
            }else{
                stepsNotesAddBinding.Noteanser.setError(null);
                alertDialog =setAlertMeaage(getString(R.string.notesAdde),getContext());
                alertDialog.show();
                noteBodey = new NoteBodey();
                noteBodey.setRequest_service_id(SeriverId);
                noteBodey.setNote(stepsNotesAddBinding.Noteanser.getText().toString());
                taehaedVModel.SendNote(noteBodey, (status,Message) -> {
                    if(status)
                    {
                        alertDialog.dismiss();
                        stepsNotesAddBinding.Noteanser.setText("");

                        onClickkSend.onSendClicked();
                        this.dismiss();
                    }
                    else{
                        alertDialog.dismiss();
                        Toast.makeText(getContext(),getString( R.string.SometingWorng)+"\n"+Message, Toast.LENGTH_SHORT).show();

                    }
                });
                noteBodey.setNote(stepsNotesAddBinding.Noteanser.getText().toString());

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

    public interface  OnClickkSend
    {
        void onSendClicked();
    }
}