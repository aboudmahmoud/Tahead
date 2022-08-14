package com.example.taehaed.Screens.Fragment;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.taehaed.Constans;
import com.example.taehaed.Model.ListenersForRespone.CancelServes;
import com.example.taehaed.Model.TaehaedVModel;
import com.example.taehaed.Pojo.NoteBodey;
import com.example.taehaed.R;
import com.example.taehaed.Screens.FormOperation;
import com.example.taehaed.Screens.OperartionQuset;
import com.example.taehaed.databinding.FragmentNotesAddsBinding;


public class NotesAddsFragment extends DialogFragment {

 private FragmentNotesAddsBinding binding;
 private int SeriverId;
 private int FornNumber;
    private TaehaedVModel taehaedVModel;
    private AlertDialog alertDialog;
    private NoteBodey noteBodey;
    public NotesAddsFragment() {

    }


    public static NotesAddsFragment newInstance(int id,int form) {
        NotesAddsFragment fragment = new NotesAddsFragment();
        Bundle args = new Bundle();
        args.putInt(Constans.IdServerFragment, id);
        args.putInt(Constans.FormFragmentKey, form);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            SeriverId=getArguments().getInt(Constans.IdServerFragment);
            FornNumber=getArguments().getInt(Constans.FormFragmentKey);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       // return inflater.inflate(R.layout.fragment_notes_adds, container, false);
        binding= FragmentNotesAddsBinding.inflate(getLayoutInflater());
        taehaedVModel = new ViewModelProvider(this).get(TaehaedVModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.goBack.setOnClickListener(view1 -> {
            getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();


        });


        binding.ButtonDiel.setOnClickListener(view1 -> {
            binding.ButtonCoremitDiel.setVisibility(View.VISIBLE);
            binding.TextInputDenyResson.setVisibility(View.VISIBLE);
        });
        binding.ButtonCoremitDiel.setOnClickListener(view1 -> {
            if(binding.DeletAnsewer.getText().toString().equals(""))
            {
                binding.TextInputDenyResson.setError("ارجو منك كتابة سبب الحذف");
            }
            else{
                binding.TextInputDenyResson.setError(null);
                SetDailgoText("جاري حذف الطلب");
                alertDialog.show();
                noteBodey = new NoteBodey();
                noteBodey.setRequest_service_id(SeriverId);
                noteBodey.setReport(binding.DeletAnsewer.getText().toString());
                taehaedVModel.ConverServiesToCanse(noteBodey, status -> {
                    if(status)
                    {
                        alertDialog.dismiss();
                        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
                        getActivity().recreate();
                    }
                    else{
                        alertDialog.dismiss();
                        SetToastMassge("يبدو ان هنأك خطأ ما", Toast.LENGTH_LONG);
                    }
                });
            }

        });


        binding.ButtonCoremit.setOnClickListener(view1 -> {
            binding.ButtonCoremitDiel.setVisibility(View.GONE);
            binding.TextInputDenyResson.setVisibility(View.GONE);
            if(binding.NoteAdd.getText().toString().equals(""))
            {
                binding.textInputNote.setError("اكتب الملاحظة اولا ");
            }else{
                SetDailgoText("جاري اضافة الملاحظة");
                alertDialog.show();
                noteBodey = new NoteBodey();
                noteBodey.setRequest_service_id(SeriverId);

                noteBodey.setNote(binding.NoteAdd.getText().toString());
                taehaedVModel.SendNote(noteBodey,status -> {
                    if(status)
                    {
                        alertDialog.dismiss();
                        binding.NoteAdd.setText("");

                        SetToastMassge("تمت اضافة الملاحظة", Toast.LENGTH_SHORT);
                    }
                    else{
                        alertDialog.dismiss();
                        SetToastMassge("يبدو ان هناك خطأ ما", Toast.LENGTH_SHORT);
                    }

                });
            }

        });

        binding.ButtonGotoForm.setOnClickListener(view1->{
            Intent intent = new Intent(getContext(), OperartionQuset.class);
            intent.putExtra(Constans.FormNumbrActvity,FornNumber);
            intent.putExtra(Constans.ServisIDFtoA,SeriverId);
            getActivity().startActivity(intent);
            getActivity().finish();
        });
    }

    private void SetToastMassge(String s, int lengthShort) {
        Toast.makeText(getContext(), s, lengthShort).show();
    }

    private void SetDailgoText(String s) {
        alertDialog =
                new AlertDialog.Builder(getContext()).setTitle(s)
                        .setMessage("يرجو الانتظار ....").setCancelable(false)
                        .setIcon(R.drawable.ic_log_out_svgrepo_com).create();
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