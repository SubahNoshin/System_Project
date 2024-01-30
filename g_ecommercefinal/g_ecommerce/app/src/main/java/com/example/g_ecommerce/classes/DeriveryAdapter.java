package com.example.g_ecommerce.classes;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.g_ecommerce.R;
import com.example.g_ecommerce.activities.AddDeliveryManActivity;
import com.example.g_ecommerce.activities.DeliveryBoyAddActivity;

import java.util.ArrayList;

public class DeriveryAdapter extends RecyclerView.Adapter<DeriveryAdapter.ItemViewHolder>{
    Context context;
    ArrayList<Derivery>deriveries;
    public DeriveryAdapter(Context context,ArrayList<Derivery>deriveries)
    {
        this.context =context;
        this.deriveries=deriveries;
    }


    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.each_deliveryboy_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull DeriveryAdapter.ItemViewHolder holder, int position) {
        Derivery derivery = deriveries.get(position);
        holder.dev_name.setText("Name: "+derivery.getName());
        holder.dev_dis.setText("Distance: "+derivery.getDistance()+"km");
        holder.dev_loc.setText("loaction: "+derivery.getLocation());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position =holder.getAdapterPosition();
                Intent intent =new Intent(context, DeliveryBoyAddActivity.class);
                intent.putExtra("fruit",deriveries.get(position));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return deriveries.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView dev_name,dev_loc,dev_dis;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            dev_name = itemView.findViewById(R.id.dev_nameb);
            dev_loc=itemView.findViewById(R.id.dev_locb);
            dev_dis =itemView.findViewById(R.id.dev_disb);
        }
    }
}