package com.example.project.RecyclerViews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.project.Model.Iron_Info;
import com.example.project.Model.Order;
import com.example.project.R;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    private List<Order> mData;
    private LayoutInflater mInflater;

    // data is passed into the constructor
    public OrderAdapter(Context context, List<Order> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_order, parent,false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Order iron = mData.get(position);
        holder.clientName_tv.setText(iron.getClientName());
        holder.date_tv.setText(iron.getDateTime());

    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView clientName_tv;
        TextView date_tv;


        ViewHolder(View itemView) {
            super(itemView);
            clientName_tv = itemView.findViewById(R.id.item_order_name);
            date_tv = itemView.findViewById(R.id.item_order_date);
            itemView.findViewById(R.id.item_order_btn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(view.getContext(),"Rods button pressed",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }



}