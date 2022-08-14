package com.example.taehaed.Screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.taehaed.Adapters.NotesAdapter;
import com.example.taehaed.Constans;
import com.example.taehaed.Pojo.NoteToShow.NotesRoot;
import com.example.taehaed.databinding.ActivityNotesPageBinding;

public class NotesPage extends AppCompatActivity {

    private ActivityNotesPageBinding binding;
    private NotesRoot notesRoot;
    private NotesAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityNotesPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        notesRoot =(NotesRoot)getIntent().getSerializableExtra(Constans.NoteKey);
        if(notesRoot.getNotes()!=null)
        {
            adapter=new NotesAdapter(notesRoot.getNotes());
            binding.listOfData.setAdapter(adapter);
            binding.listOfData.setLayoutManager(new LinearLayoutManager(binding.getRoot().getContext()));
            if(notesRoot.getNotes().size()==0)
            {
                binding.NoNotes.setVisibility(View.VISIBLE);
            }
            //
        }
        else
        {
          binding.NoNotes.setVisibility(View.VISIBLE);
        }


binding.goback.setOnClickListener(view ->{
    finish();
});
    }
}