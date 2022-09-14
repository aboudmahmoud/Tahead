package com.example.taehaed.Screens.Fragment.DelegateCycle;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.taehaed.Adapters.NotesAdapter;
import com.example.taehaed.Constans;
import com.example.taehaed.Model.TaehaedVModel;
import com.example.taehaed.Pojo.NoteBodey;
import com.example.taehaed.R;
import com.example.taehaed.Screens.NotesPage;
import com.example.taehaed.Screens.OperartionQuset;
import com.example.taehaed.databinding.FragmentNotesAddsBinding;

import java.util.ArrayList;


public class NotesAddsFragment extends DialogFragment {

    private FragmentNotesAddsBinding binding;
    private int SeriverId;
    private int FornNumber;
    private String choes;
    private TaehaedVModel taehaedVModel;
    private AlertDialog alertDialog;
    private NoteBodey noteBodey;
    NotesAdapter adapter = new NotesAdapter();


    public NotesAddsFragment() {

    }


    public static NotesAddsFragment newInstance(int id, int form) {
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
            SeriverId = getArguments().getInt(Constans.IdServerFragment);
            FornNumber = getArguments().getInt(Constans.FormFragmentKey);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        binding = FragmentNotesAddsBinding.inflate(getLayoutInflater());
        taehaedVModel = new ViewModelProvider(this).get(TaehaedVModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        taehaedVModel.ObesverNotes(SeriverId, (status, Message) -> {

        });

        setErrorMessage();
        setNotes();
        binding.ButtonDiel.setOnClickListener(view1 -> {
            binding.ButtonCoremitDiel.setVisibility(View.VISIBLE);
            binding.TextInputDenyResson.setVisibility(View.VISIBLE);

        });
        binding.ButtonCoremitDiel.setOnClickListener(view1 -> {

            noteBodey = new NoteBodey();
            noteBodey.setRequest_service_id(SeriverId);
            alertDialog = Constans.setAlertMeaage(getString(R.string.Orderrequset), getContext());

            //here we check the Deny resson
        CheckThedenyRessonAndSumbit();

                }

        );


        binding.ButtonCoremit.setOnClickListener(view1 -> {
            Intent intent = new Intent(getContext(), NotesPage.class);
            intent.putExtra(Constans.NoteKey, SeriverId);
            getContext().startActivity(intent);
        });
        binding.ButtonGotoForm.setOnClickListener(view1 -> {
            Intent intent = new Intent(getContext(), OperartionQuset.class);
            intent.putExtra(Constans.FormNumbrActvity, FornNumber);
            intent.putExtra(Constans.ServisIDFtoA, SeriverId);
            getActivity().startActivity(intent);
            getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
        });
    }

    private void CheckThedenyRessonAndSumbit() {
        //if he didnt choes anytihng that is valdtion we can't alowe it
        if(choes==null)
        {
            binding.TextInputDenyResson.setError(getString(R.string.dontletemp));
        }
        //if its other Reson We Have ToKnow Why
            else if (choes.equals(getString(R.string.otherResson))){
                // binding.TextInputDenyResson1.setVisibility(View.VISIBLE);
            binding.TextInputDenyResson.setError(null);
                if (binding.Noteanser.getText().toString().equals("")) {
                    binding.TextInputDenyResson1.setError(getString(R.string.itsNotEmpty));
                } else {
                    binding.TextInputDenyResson1.setError(null);

                    alertDialog.show();
                    noteBodey.setReport(binding.Noteanser.getText().toString());
                    setThdata();
                }
            }
            else{
            binding.TextInputDenyResson.setError(null);
                alertDialog.show();
                noteBodey.setReport(choes);
                setThdata();
            }
    }

    private void setErrorMessage() {
        ArrayList<String> Rjects = new ArrayList<>();
        Rjects.add(getString(R.string.agentClosed));
        Rjects.add(getString(R.string.NoActiveForAgent));
        Rjects.add(getString(R.string.MoneyWall));
        Rjects.add(getString(R.string.agelessThaenone));
        Rjects.add(getString(R.string.theActivieNotforeAget));
        Rjects.add(getString(R.string.theDontKnow));
        Rjects.add(getString(R.string.theLesstAge));
        Rjects.add(getString(R.string.activitagent));
        Rjects.add(getString(R.string.moneyGive));
        Rjects.add(getString(R.string.heyhadMoney));
        Rjects.add(getString(R.string.hedidint));
        Rjects.add(getString(R.string.agentHavebad));
        Rjects.add(getString(R.string.hadstucks));
        Rjects.add(getString(R.string.addersNotWorng));
        Rjects.add(getString(R.string.ActivityPolcity));
        Rjects.add(getString(R.string.theyDeanpdOnSesong));
        Rjects.add(getString(R.string.padReapation));
        Rjects.add(getString(R.string.hadStructer));
        Rjects.add(getString(R.string.heDoesnthavaAdders));
        Rjects.add(getString(R.string.AgenthavePath));
        Rjects.add(getString(R.string.theyDidentExsite));
        Rjects.add(getString(R.string.notmating));
        Rjects.add(getString(R.string.activtyNomating));
        Rjects.add(getString(R.string.theybensmothg));
        Rjects.add(getString(R.string.denyFromBank));
        Rjects.add(getString(R.string.stationRject));
        Rjects.add(getString(R.string.agenst_bolesy));
        Rjects.add(getString(R.string.theNotCompe));
        Rjects.add(getString(R.string.overRated));
        Rjects.add(getString(R.string.theRentNothaveNothing));
        Rjects.add(getString(R.string.notMathingInactivty));
        Rjects.add(getString(R.string.systemDeny));
        Rjects.add(getString(R.string.TheyNotMatching));
        Rjects.add(getString(R.string.theyHavlogn));
        Rjects.add(getString(R.string.repate));
        Rjects.add(getString(R.string.perives));
        Rjects.add(getString(R.string.fall));
        Rjects.add(getString(R.string.Rject));
        Rjects.add(getString(R.string.otherResson));
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter(getContext(), R.layout.textview_rject, Rjects);
        binding.autotext.setAdapter(arrayAdapter);

        binding.autotext.setOnItemClickListener((adapterView, view12, i, l) -> {
           choes =adapterView.getItemAtPosition(i).toString();
           // if he choer othe reson make the field why visible else gone
           if(choes.equals(getString(R.string.otherResson)))
           {
               binding.TextInputDenyResson.setError(null);
               binding.TextInputDenyResson1.setVisibility(View.VISIBLE);
           }
           else{
               binding.TextInputDenyResson.setError(null);
               binding.TextInputDenyResson1.setVisibility(View.GONE);
           }

        });
    }

    private void setNotes() {
        taehaedVModel.notesRootMutableLiveData.observe(getActivity(), notesRoot -> {
            Log.d("Aboud", "onViewCreated: " + notesRoot.getNotes().size());


            adapter.setNotes(notesRoot.getNotes());

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

    void setThdata(){
        taehaedVModel.ConverServiesToCanse(noteBodey, status -> {
            if (status) {
                alertDialog.dismiss();
                getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
                getActivity().recreate();
            } else {
                alertDialog.dismiss();
                SetToastMassge(getString(R.string.ErrorMessage), Toast.LENGTH_LONG);
            }
        });
    }
}