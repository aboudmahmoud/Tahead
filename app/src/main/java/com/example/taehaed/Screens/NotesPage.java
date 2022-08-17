package com.example.taehaed.Screens;

import static com.example.taehaed.Constans.setAlertMeaage;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.taehaed.Adapters.NotesAdapter;
import com.example.taehaed.Constans;
import com.example.taehaed.Model.ListenersForRespone.CancelServes;
import com.example.taehaed.Model.ListenersForRespone.OberverTheError;
import com.example.taehaed.Model.TaehaedVModel;
import com.example.taehaed.Pojo.NoteBodey;
import com.example.taehaed.Pojo.NoteToShow.NotesRoot;
import com.example.taehaed.Screens.Fragment.AgentInfoFragment;
import com.example.taehaed.Screens.Fragment.StepsNotesAdd;
import com.example.taehaed.databinding.ActivityNotesPageBinding;

public class NotesPage extends AppCompatActivity implements StepsNotesAdd.OnClickkSend {

    private ActivityNotesPageBinding binding;
    // private NotesRoot notesRoot;
    private NotesAdapter adapter;
    private int SeriverId;
    private NoteBodey noteBodey;
    private AlertDialog alertDialog;
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

    /*    binding.textInputNote.setStartIconOnClickListener(view -> {
            if(binding.NoteAdd.getText().toString().isEmpty())
            {
                binding.textInputNote.setError("اكتب الملاحظة اولا ");
            }else{
                binding.textInputNote.setError(null);
                alertDialog =setAlertMeaage("جاري اضافة الملاحظة",this);
                alertDialog.show();
                noteBodey = new NoteBodey();
                noteBodey.setRequest_service_id(SeriverId);
                noteBodey.setNote(binding.NoteAdd.getText().toString());
                taehaedVModel.SendNote(noteBodey, status -> {
                    if(status)
                    {
                        alertDialog.dismiss();
                        binding.NoteAdd.setText("");
                        ObsevrNotes();
                        binding.listOfData.scrollToPosition(adapter.getItemCount()-1);
                    }
                    else{
                        alertDialog.dismiss();
                        Toast.makeText(this, "يبدو ان هناك خطأ ما", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });*/


    }

    private void ObsevrNotes() {
        taehaedVModel.ObesverNotes(SeriverId, (status, Message) -> {
            if (status) {
                Toast.makeText(this, "عفوا يبدو ان هناك خطأ في ما  " + Message, Toast.LENGTH_SHORT).show();
            }
        });

        taehaedVModel.notesRootMutableLiveData.observe(this, notesRoot -> {
            adapter.setNotes(notesRoot.getNotes());
        });
    }


    @Override
    public void onSendClicked() {
        ObsevrNotes();
        binding.listOfData.scrollToPosition(adapter.getItemCount()-1);
    }
}