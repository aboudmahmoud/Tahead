package com.example.taehaed.Screens.Fragment;

import static com.example.taehaed.Constans.setAlertMeaage;

import android.graphics.Point;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.taehaed.Constans;
import com.example.taehaed.Model.TaehaedVModel;
import com.example.taehaed.Pojo.NoteBodey;
import com.example.taehaed.R;
import com.example.taehaed.databinding.FragmentServiersBinding;


public class ServiersFragment extends DialogFragment {

    private FragmentServiersBinding binding;
    private NoteBodey noteBodey;
    private int id;
    private String SeriverName;
    private TaehaedVModel taehaedVModel;
    private AlertDialog alertDialog;

    public ServiersFragment() {

    }


    public static ServiersFragment newInstance(int id,String SeriverName) {
        ServiersFragment fragment = new ServiersFragment();
        Bundle args = new Bundle();
        args.putInt(Constans.IdServerFragment, id);
        args.putString(Constans.ServNameKey, SeriverName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getInt(Constans.IdServerFragment);
            SeriverName= getArguments().getString(Constans.ServNameKey);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_serviers, container, false);
        binding = FragmentServiersBinding.inflate(getLayoutInflater());
        taehaedVModel = new ViewModelProvider(this).get(TaehaedVModel.class);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.ServieName.setText(SeriverName);

        binding.RadioDiel.setOnCheckedChangeListener((compoundButton, b) -> {
            if (binding.RadioDiel.isChecked()) {
                binding.ButtonCoremitDiel.setVisibility(View.VISIBLE);
                binding.TextInputDenyResson.setVisibility(View.VISIBLE);
                binding.ButtonCoremit.setVisibility(View.GONE);



            }

        });

        binding.rdioAccept.setOnCheckedChangeListener((compoundButton, b) -> {
            if (binding.rdioAccept.isChecked()) {
                binding.ButtonCoremitDiel.setVisibility(View.GONE);
                binding.TextInputDenyResson.setVisibility(View.GONE);
                binding.ButtonCoremit.setVisibility(View.VISIBLE);



            }
        });
        binding.ButtonCoremitDiel.setOnClickListener(view1 -> {
            if(binding.DeletAnsewer.getText().toString().equals(""))
            {
                binding.TextInputDenyResson.setError("ارجو منك كتابة سبب الرفض");
            }else
            {
                binding.TextInputDenyResson.setError(null);
                alertDialog= setAlertMeaage("جاري حذف الطلب",getContext());
                alertDialog.show();
                noteBodey = new NoteBodey();
                noteBodey.setRequest_service_id(id);
                noteBodey.setReport(binding.DeletAnsewer.getText().toString());
                taehaedVModel.CancelRequstNote(noteBodey, status -> {
                    if (status) {
                        alertDialog.dismiss();
                        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
                        getActivity().recreate();
                        //   Toast.makeText(getContext(), "تم + " + id, Toast.LENGTH_LONG).show();

                    } else {
                        alertDialog.dismiss();
                        Toast.makeText(getContext(), "يبدو ان هنأك خطأ ما", Toast.LENGTH_LONG).show();
                    }
                });

            }

        });
        binding.ButtonCoremit.setOnClickListener(view1 ->{
            alertDialog= setAlertMeaage("جاري قبول الطلب",getContext());
            alertDialog.show();
            taehaedVModel.ConverNoteToAccept(id, status -> {
                if (status)
                { alertDialog.dismiss();
                    getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
                    getActivity().recreate();
                }
                else {
                    Toast.makeText(getContext(), "يبدو ان هناك خطأ ما", Toast.LENGTH_SHORT).show();
                }
            });
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