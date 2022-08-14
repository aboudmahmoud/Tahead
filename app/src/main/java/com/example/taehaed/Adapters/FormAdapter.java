package com.example.taehaed.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taehaed.Pojo.OperationRequstModifed.Form;
import com.example.taehaed.Pojo.OperationRequstModifed.OperationServiersRoot;
import com.example.taehaed.R;
import com.example.taehaed.databinding.FormNameBinding;

public class FormAdapter extends RecyclerView.Adapter<FormAdapter.FormHolder> {

  private OperationServiersRoot operationServiersRoot;
  private InputAdapter inputAdapter;
    public FormAdapter(OperationServiersRoot operationServiersRoot) {
        this.operationServiersRoot=operationServiersRoot;
    }

    @NonNull
    @Override
    public FormHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.form_name, parent, false);
        return new FormHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FormHolder holder, int position) {
        holder.BindItem(operationServiersRoot.request_sercice.form.get(position));
    }

    @Override
    public int getItemCount() {
        return operationServiersRoot.request_sercice.form.size();
    }

    public class FormHolder extends RecyclerView.ViewHolder {
        private FormNameBinding binding;

        public FormHolder(@NonNull View itemView) {
            super(itemView);
            binding = FormNameBinding.bind(itemView);

        }

        public void BindItem(Form form) {
            binding.TvQuset.setText(form.name);
            inputAdapter= new InputAdapter(form.inputs);
            binding.ListOfInputs.setAdapter(inputAdapter);
            binding.ListOfInputs.setLayoutManager(new LinearLayoutManager(binding.getRoot().getContext()));
        }
    }
}
