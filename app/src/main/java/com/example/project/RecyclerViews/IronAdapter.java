package com.example.project.RecyclerViews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.Model.Iron_Info;
import com.example.project.R;

import java.util.List;

public class IronAdapter extends RecyclerView.Adapter<IronAdapter.ViewHolder> {

    private final List<Iron_Info> mData;
    private final LayoutInflater mInflater;

    // data is passed into the constructor
    public IronAdapter(Context context, List<Iron_Info> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_iron, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Iron_Info iron = mData.get(position);
        holder.quantity_tv.setText(iron.getQuantity());
        holder.length_tv.setText(iron.getLength());
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView quantity_tv;
        TextView length_tv;

        ViewHolder(View itemView) {
            super(itemView);
            quantity_tv = itemView.findViewById(R.id.item_iron_quantity);
            length_tv = itemView.findViewById(R.id.item_iron_length);
        }

    }


}