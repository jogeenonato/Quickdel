package com.example.quickdel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DetailsAdapter extends RecyclerView.Adapter<DetailsAdapter.MyViewHolder> {

    Context context1;
    ArrayList<Orders> list1;

    public DetailsAdapter(Context context1, ArrayList<Orders> list1){
        this.context1 = context1;
        this.list1 = list1;
    }


    @NonNull
    @Override
    public DetailsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context1).inflate(R.layout.earningsitems, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailsAdapter.MyViewHolder holder, int position) {
        Orders orders = list1.get(position);
        holder.Name.setText(orders.getOrderNumber());
        holder.LastName.setText(orders.getPickupPoint());
        holder.Email.setText(orders.getDestinationPoint());
        holder.PhoneNumber.setText(String.valueOf(orders.getRunnerEarnings()));


    }

    @Override
    public int getItemCount() {
        return list1.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Name, LastName, Email, PhoneNumber;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            Name = itemView.findViewById(R.id.e_orderno);
            LastName = itemView.findViewById(R.id.e_dest);
            Email = itemView.findViewById(R.id.e_pickup);
            PhoneNumber = itemView.findViewById(R.id.e_earnings);
        }

    }
}
