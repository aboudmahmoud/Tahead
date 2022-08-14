package com.example.taehaed.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taehaed.Pojo.OperationRequstModifed.Input;
import com.example.taehaed.R;
import com.example.taehaed.databinding.ItemOfQuestBinding;

import java.util.ArrayList;

public class InputAdapter extends RecyclerView.Adapter<InputAdapter.InputHolder> {

    private ArrayList<Input> inputs;
    public InputAdapter(ArrayList<Input> inputs) {
        this.inputs=inputs;
    }

    @NonNull
    @Override
    public InputHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_of_quest, parent, false);
        return new InputHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InputHolder holder, int position) {
        holder.BindTheIems(inputs.get(position));
    }

    @Override
    public int getItemCount() {
        return inputs.size();
    }

    class InputHolder extends RecyclerView.ViewHolder {
     private ItemOfQuestBinding binding;
        public InputHolder(@NonNull View itemView) {
            super(itemView);
            binding=ItemOfQuestBinding.bind(itemView);
        }
        public void BindTheIems(Input input)
        {
            binding.InputName.setText(input.name);
            if(input.type==5)
            {
                binding.Fomvalue.setVisibility(View.GONE);
                binding.ListOfChoesies.setVisibility(View.VISIBLE);
            }
            else {
                binding.Fomvalue.setText(input.value.toString());
            }
        }
    }
}
