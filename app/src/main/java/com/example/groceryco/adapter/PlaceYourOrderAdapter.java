package com.example.groceryco.adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.groceryco.R;
import com.example.groceryco.model.Menu;

import java.util.List;

public class PlaceYourOrderAdapter extends RecyclerView.Adapter<PlaceYourOrderAdapter.MyViewHolder> {

    private List<Menu> menuList;

    public PlaceYourOrderAdapter(List<Menu> menuList) {
        this.menuList = menuList;
    }

    public void updateData(List<Menu> menuList) {
        this.menuList = menuList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PlaceYourOrderAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.place_order_recycler_row, parent, false);
        return  new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceYourOrderAdapter.MyViewHolder holder, int position) {
        holder.groceryMenuName.setText(menuList.get(position).getName());
        holder.groceryMenuPrice.setText("Price: RM "+String.format("%.2f", menuList.get(position).getPrice()*menuList.get(position).getTotalInCart()));
        holder.groceryQuantity.setText("Qty: " + menuList.get(position).getTotalInCart());
        Glide.with(holder.thumbImage)
                .load(menuList.get(position).getUrl())
                .into(holder.thumbImage);

    }

    @Override
    public int getItemCount() {

        return menuList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView groceryMenuName;
        TextView groceryMenuPrice;
        TextView groceryQuantity;
        ImageView thumbImage;

        public MyViewHolder(View view) {
            super(view);
            groceryMenuName = view.findViewById(R.id.drinksMenuName);
            groceryMenuPrice = view.findViewById(R.id.drinksMenuPrice);
            groceryQuantity = view.findViewById(R.id.drinksQuantity);
            thumbImage = view.findViewById(R.id.thumbImage);
        }
    }
}