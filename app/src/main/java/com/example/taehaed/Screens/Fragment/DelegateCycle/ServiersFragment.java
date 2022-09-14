package com.example.taehaed.Screens.Fragment.DelegateCycle;

import static com.example.taehaed.Constans.setAlertMeaage;

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
   if (  binding.RadioDiel.isChecked())
   {
       binding.TextInputDenyResson.setVisibility(View.VISIBLE);
   }
        });
        binding.rdioAccept.setOnCheckedChangeListener((compoundButton, b) -> {
            if (  binding.rdioAccept.isChecked())
            {
                binding.TextInputDenyResson.setVisibility(View.GONE);
            }
        });

        binding.ButtonCoremit.setOnClickListener(view1 ->{

            if (binding.RadioDiel.isChecked()) {

                if(binding.Noteanser.getText().toString().equals("")) {
                    binding.TextInputDenyResson.setError(getString(R.string.RessonwhyDeny));

                }
                else {
                    binding.TextInputDenyResson.setError(null);
                    alertDialog= setAlertMeaage(getString(R.string.currentDelet),getContext());
                    alertDialog.show();
                    noteBodey = new NoteBodey();
                    noteBodey.setRequest_service_id(id);
                    noteBodey.setReport(binding.Noteanser.getText().toString());
                    taehaedVModel.CancelRequstNote(noteBodey, status -> {
                        if (status) {
                            alertDialog.dismiss();
                            getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
                            getActivity().recreate();
                            //   Toast.makeText(getContext(), "تم + " + id, Toast.LENGTH_LONG).show();

                        } else {
                            alertDialog.dismiss();
                            Toast.makeText(getContext(), R.string.Cirrrentworj, Toast.LENGTH_LONG).show();
                        }
                    });

                }
            }
            else if(binding.rdioAccept.isChecked()) {
                alertDialog= setAlertMeaage(getString(R.string.RequseRescipt),getContext());
                alertDialog.show();
                taehaedVModel.ConverNoteToAccept(id, status -> {
                    if (status)
                    { alertDialog.dismiss();
                        Toast.makeText(getContext(),getString( R.string.AssigmentAccept), Toast.LENGTH_SHORT).show();
                        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
                        getActivity().recreate();
                    }
                    else {
                        Toast.makeText(getContext(), getString(R.string.SometingError), Toast.LENGTH_SHORT).show();
                    }
                });
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