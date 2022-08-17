package com.example.taehaed.Adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taehaed.Pojo.NoteToShow.Note;
import com.example.taehaed.R;
import com.example.taehaed.databinding.ListOfNotesBinding;

import java.util.ArrayList;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteHolder> {
    private ArrayList<Note> notes;
    public NotesAdapter()
    {
        this.notes=new ArrayList<>();
    }
@SuppressLint("NotifyDataSetChanged")
public void refReash(){
    notifyDataSetChanged();
}

    @SuppressLint("NotifyDataSetChanged")
    public void setNotes(ArrayList<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    public NotesAdapter(ArrayList<Note> notes)
    {
     this.notes=notes;
    }
    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_of_notes, parent, false);
          return new NoteHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder note, int position) {
        note.BindTheData(notes.get(position));
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

   public class NoteHolder extends RecyclerView.ViewHolder {
        private ListOfNotesBinding binding;
        public NoteHolder(@NonNull View itemView) {
            super(itemView);
            binding= ListOfNotesBinding.bind(itemView);

        }
      public  void BindTheData(Note note)
        {
            binding.textDate.setText(note.getCreated_at());
            binding.TextNotesData.setText(note.getNote());
        }

    }
}
