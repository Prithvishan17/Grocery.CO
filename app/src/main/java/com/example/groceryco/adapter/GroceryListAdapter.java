package com.example.groceryco.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.groceryco.R;
import com.example.groceryco.model.GroceryModel;

import java.util.List;

public class GroceryListAdapter extends RecyclerView.Adapter<GroceryListAdapter.myViewHolder> {

    private List<GroceryModel> groceryModelList;
    private GroceryListClickListener clickListener;

    public GroceryListAdapter(List<GroceryModel> groceryModelList, GroceryListClickListener clickListener){
        this.groceryModelList = groceryModelList;
        this.clickListener = clickListener;
    }

    public void updateData(List<GroceryModel> groceryModelList){
        this.groceryModelList = groceryModelList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public GroceryListAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroceryListAdapter.myViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.groceryName.setText(groceryModelList.get(position).getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onItemClick(groceryModelList.get(position));
            }
        });

        Glide.with(holder.thumbImage).load(groceryModelList.get(position).getImage()).into(holder.thumbImage);
    }

    @Override
    public int getItemCount() {
        return groceryModelList.size();
    }

    static class myViewHolder extends RecyclerView.ViewHolder{

        TextView groceryName;
        ImageView thumbImage;

        public myViewHolder(View view){
            super(view);

            groceryName = view.findViewById(R.id.groceryName);
            thumbImage = view.findViewById(R.id.thumbImage);
        }

    }

    public interface GroceryListClickListener {
        public void onItemClick(GroceryModel groceryModel);

    }
}
