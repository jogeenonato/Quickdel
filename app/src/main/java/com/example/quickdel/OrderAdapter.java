package com.example.quickdel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder> {


    Context context;
    ArrayList<Orders> list;
    private OnNoteListener mOnNoteListener;

    public OrderAdapter(ArrayList<Orders> list, OnNoteListener onNoteListener) {
//        this.context = context;
        this.list = list;
        this.mOnNoteListener = onNoteListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.orderitems, parent, false);
        return new MyViewHolder(v, mOnNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Orders orders = list.get(position);
        holder.orderNumber.setText(orders.getOrderNumber());
        holder.DestinationPoint.setText(orders.getDestinationPoint());
        holder.status.setText(orders.getStatus());
        holder.PickupPoint.setText(orders.getPickupPoint());
        holder.Recipient.setText(orders.getRecipient());
        holder.recipientPhone.setText(orders.getRecipientPhone());
        holder.Description.setText(orders.getDescription());
        holder.Vehicle.setText(orders.getVehicle());
        holder.distance.setText(orders.getDistance());
        holder.Size.setText(orders.getSize());
        holder.Weight.setText(orders.getWeight());
        holder.VehiclePrice.setText(String.valueOf(orders.getVehiclePrice()));
        holder.SizePrice.setText(String.valueOf(orders.getSizePrice()));
        holder.WeightPrice.setText(String.valueOf(orders.getWeightPrice()));
        holder.distancePrice.setText(String.valueOf(orders.getDistancePrice()));
        holder.Total.setText(String.valueOf(orders.getTotal()));

//        boolean isVisible = orderData.visibility;
//        holder.linearLayout.setVisibility(isVisible ? View.VISIBLE : View.GONE);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView orderNumber, DestinationPoint, status, PickupPoint, Recipient, recipientPhone, Vehicle, VehiclePrice, Size, SizePrice, Weight, WeightPrice, distance, distancePrice, Total, Description;
        OnNoteListener onNoteListener;
//        LinearLayout linearLayout;

        public MyViewHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
            super(itemView);

            orderNumber = itemView.findViewById(R.id.hist_orderno);
            DestinationPoint = itemView.findViewById(R.id.hist_dest);
            status = itemView.findViewById(R.id.hist_status);
            PickupPoint = itemView.findViewById(R.id.hist_pickup);
            Recipient = itemView.findViewById(R.id.hist_recipient);
            recipientPhone = itemView.findViewById(R.id.hist_rMobile);
            Description = itemView.findViewById(R.id.hist_description);
            Vehicle = itemView.findViewById(R.id.hist_vehicle);
            VehiclePrice = itemView.findViewById(R.id.hist_vehicleprice);
            Size = itemView.findViewById(R.id.hist_size);
            SizePrice = itemView.findViewById(R.id.hist_sizeprice);
            Weight = itemView.findViewById(R.id.hist_weight);
            WeightPrice = itemView.findViewById(R.id.hist_weightprice);
            distance = itemView.findViewById(R.id.hist_distance);
            distancePrice = itemView.findViewById(R.id.hist_distanceprice);
            Total = itemView.findViewById(R.id.hist_totalprice);
            this.onNoteListener = onNoteListener;
            itemView.setOnClickListener(this);
//            linearLayout = itemView.findViewById(R.id.detailsexpandable);


//            orderNumber.setOnClickListener(new View.OnClickListener(){
//                @Override
//                public void onClick (View v) {
//                    OrderData orderData = list.get(getAdapterPosition());
//                    orderData.setVisibility(!orderData.isVisibility());
//                    notifyItemChanged(getAdapterPosition());
//
//                }
//
//
//            });

        }

        @Override
        public void onClick(View view) {
            onNoteListener.onNoteclick(getAdapterPosition());
        }
    }

    public interface OnNoteListener {
        void onNoteclick(int position);
    }
}
