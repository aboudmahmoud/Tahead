package com.example.taehaed.Adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.taehaed.Constans;
import com.example.taehaed.R;
import com.example.taehaed.Screens.ClientInfo;
import com.example.taehaed.databinding.ListClientInfoBinding;
import com.example.taehaed.Pojo.Index.IndexRoot;
import com.example.taehaed.Pojo.Index.Request;

public class jobsAdpater extends RecyclerView.Adapter<jobsAdpater.JobsHolder> {

    //ArrayList<FalseData> falseData ;
    IndexRoot indexRoot;
    public jobsAdpater( IndexRoot indexRoot){
        this.indexRoot=indexRoot;
      //  Log.d("Aboud", "jobsAdpater: "+falseData.size()+" wg "+ this.falseData.size());
    }

    @NonNull
    @Override
    public JobsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_client_info, parent, false);

        return new JobsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JobsHolder holder, int position) {

        holder.BindData(indexRoot.getRequests().get(position));
    }

    @Override
    public int getItemCount() {
        return indexRoot.getRequests().size();
    }

    class JobsHolder extends RecyclerView.ViewHolder {

        private final ListClientInfoBinding binding;
        public JobsHolder(@NonNull View itemView) {
            super(itemView);
            binding= ListClientInfoBinding.bind(itemView);
        }

        void BindData(Request indexRoot)
        {
            binding.IDOrgnaization.setText(indexRoot.getOrganization().getId()+"");
            binding.ClinetName.setText(indexRoot.getFullname());
            binding.Adders.setText(indexRoot.getActual_address());
            binding.Pranstger.setText("%"+indexRoot.getPercentage());
            binding.CompanyName.setText(indexRoot.getOrganization().getName());

            binding.progesAction.setProgressCompat(indexRoot.getPercentage(),true);
            binding.DateTaske.setText(indexRoot.getCreated_at());
            Glide.with(binding.getRoot().getContext()).load(indexRoot.getOrganization().getImage()).into(binding.CardEgypt);

            binding.cartInfo.setOnClickListener(view -> {
                Intent intent = new Intent(binding.getRoot().getContext(), ClientInfo.class);
                intent.putExtra(Constans.KayMoudlIdexRoot2,indexRoot);
                binding.getRoot().getContext().startActivity(intent);
            });



        }

    }
}
