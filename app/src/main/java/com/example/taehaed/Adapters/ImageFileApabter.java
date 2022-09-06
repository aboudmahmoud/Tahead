package com.example.taehaed.Adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.taehaed.Pojo.ImageFileData;
import com.example.taehaed.R;
import com.example.taehaed.Screens.Fragment.PopUpImageFragment;
import com.example.taehaed.databinding.ImageFileBinding;

import java.util.ArrayList;

public class ImageFileApabter extends RecyclerView.Adapter<ImageFileApabter.ImageFileHolder> {
    private ArrayList<ImageFileData> imageFileDatalist = new ArrayList<>();


    @NonNull
    @Override
    public ImageFileHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_file, parent, false);
        return new ImageFileHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageFileHolder holder, int position) {
        holder.bindData(imageFileDatalist.get(position));
    }

    @Override
    public int getItemCount() {
        return imageFileDatalist.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setImageFileDatalist(ArrayList<ImageFileData> imageFileDatalist)
    {
        this.imageFileDatalist.clear();
        this.imageFileDatalist.addAll(imageFileDatalist);
        this.imageFileDatalist.add(new ImageFileData());
        this.notifyDataSetChanged();
    }

    public ImageFileData getSwitchedItem(int swipedItem) {
        return imageFileDatalist.get(swipedItem);
    }

    class ImageFileHolder extends  RecyclerView.ViewHolder{
        private ImageFileBinding binding;
        public ImageFileHolder(@NonNull View itemView) {
            super(itemView);
            binding = ImageFileBinding.bind(itemView);
        }
          void  bindData(ImageFileData imageFileData){
            if(imageFileData.getFileName()!=null)
            {
                String name = imageFileData.getFileName();
                String exantion = name.length() > 3 ? name.substring(name.length() - 3) : name;
                if(exantion.equalsIgnoreCase("jpg") || exantion.equalsIgnoreCase("png")){
                    Glide.with(binding.getRoot().getContext()).load(imageFileData.getImageLink()).into(binding.imageView);
                }else{
                    binding.imageView.setImageResource(R.drawable.ic_file);
                }

                binding.imageView.setOnClickListener(view -> {
                    if(exantion.equalsIgnoreCase("jpg") || exantion.equalsIgnoreCase("png"))
                    {  PopUpImageFragment popUpImageFragment = PopUpImageFragment.newInstance(imageFileData.getImageLink().toString());
                        FragmentManager fragmentManager = ((FragmentActivity) binding.getRoot().getContext()).getSupportFragmentManager();
                        popUpImageFragment.show(fragmentManager, "");


                    }


                });

            }


            binding.textView3.setText(imageFileData.getFileName());

        }
    }
}
