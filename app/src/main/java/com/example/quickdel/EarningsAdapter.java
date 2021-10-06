package com.example.quickdel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EarningsAdapter extends RecyclerView.Adapter<EarningsAdapter.MyViewHolder> {

    Context context1;
    ArrayList<Orders> list1;

    public EarningsAdapter(Context context1, ArrayList<Orders> list1){
        this.context1 = context1;
        this.list1 = list1;
    }


    @NonNull
    @Override
    public EarningsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context1).inflate(R.layout.earningsitems, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull EarningsAdapter.MyViewHolder holder, int position) {
        Orders orders = list1.get(position);
        holder.orderNumber.setText(orders.getOrderNumber());
        holder.PickupPoint.setText(orders.getPickupPoint());
        holder.DestinationPoint.setText(orders.getDestinationPoint());
        holder.runnerEarnings.setText(String.valueOf(orders.getRunnerEarnings()));


    }

    @Override
    public int getItemCount() {
        return list1.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView orderNumber, DestinationPoint, PickupPoint, runnerEarnings;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            orderNumber = itemView.findViewById(R.id.e_orderno);
            DestinationPoint = itemView.findViewById(R.id.e_dest);
            PickupPoint = itemView.findViewById(R.id.e_pickup);
            runnerEarnings = itemView.findViewById(R.id.e_earnings);
        }

    }
}
