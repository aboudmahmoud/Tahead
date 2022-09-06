package com.example.taehaed.Screens;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.taehaed.Adapters.NotesAdapter;
import com.example.taehaed.Constans;
import com.example.taehaed.Model.TaehaedVModel;
import com.example.taehaed.Screens.Fragment.DelegateCycle.StepsNotesAdd;
import com.example.taehaed.databinding.ActivityNotesPageBinding;

public class NotesPage extends AppCompatActivity implements StepsNotesAdd.OnClickkSend {

    private ActivityNotesPageBinding binding;
    // private NotesRoot notesRoot;
    private NotesAdapter adapter;
    private int SeriverId;

    private StepsNotesAdd notesAddFragemtn;
    private TaehaedVModel taehaedVModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNotesPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        taehaedVModel = new ViewModelProvider(this).get(TaehaedVModel.class);
        SeriverId = getIntent().getIntExtra(Constans.NoteKey, 0);
        adapter = new NotesAdapter();

        binding.listOfData.setAdapter(adapter);
        binding.listOfData.setLayoutManager(new LinearLayoutManager(this));


        if (SeriverId != 0) {

            ObsevrNotes();


        }
        binding.swiperefresh.setOnRefreshListener(() -> {
            ObsevrNotes();


            binding.swiperefresh.setRefreshing(false);
        });
        binding.floatingButton.setOnClickListener(view -> {
            notesAddFragemtn = StepsNotesAdd.newInstance(SeriverId);
            FragmentManager fragmentManager = ((FragmentActivity) binding.getRoot().getContext()).getSupportFragmentManager();
            notesAddFragemtn.show(fragmentManager, "");
        });



    }

    private void ObsevrNotes() {
        taehaedVModel.ObesverNotes(SeriverId, (status, Message) -> {
            if (status) {
                Toast.makeText(this, "عفوا يبدو ان هناك خطأ في ما  " + Message, Toast.LENGTH_SHORT).show();
                binding.ProgeesPar.setVisibility(View.GONE);
                binding.NoNotes.setVisibility(View.VISIBLE);
                binding.listOfData.setVisibility(View.INVISIBLE);
            }else{
                taehaedVModel.notesRootMutableLiveData.observe(this,
                        notesRoot -> adapter.setNotes(notesRoot.getNotes()));
                binding.ProgeesPar.setVisibility(View.GONE);
                if (adapter.getItemCount()==0)
                {
                    binding.NoNotes.setVisibility(View.VISIBLE);
                    binding.listOfData.setVisibility(View.INVISIBLE);
                }else{
                    binding.NoNotes.setVisibility(View.GONE);
                    binding.listOfData.setVisibility(View.VISIBLE);
                }



            }
        });


    }


    @Override
    public void onSendClicked() {
        binding.ProgeesPar.setVisibility(View.VISIBLE);
        ObsevrNotes();
        binding.listOfData.scrollToPosition(adapter.getItemCount()-1);
    }
}