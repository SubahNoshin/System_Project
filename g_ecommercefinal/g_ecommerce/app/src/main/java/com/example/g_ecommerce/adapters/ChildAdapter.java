package com.example.g_ecommerce.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.g_ecommerce.R;
import com.example.g_ecommerce.models.MyCartModel;

import java.util.List;

public class ChildAdapter extends RecyclerView.Adapter<ChildAdapter.ChildViewHolder> {

    private List<MyCartModel> children;

    public ChildAdapter(List<MyCartModel> children) {
        this.children = children;
    }

    @Override
    public ChildViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.purchase_item_layout, parent, false);
        return new ChildViewHolder(v);
    }

    @Override
    public int getItemCount() {
        return children.size();
    }

    @Override
    public void onBindViewHolder(ChildViewHolder holder, int position) {
        MyCartModel child = children.get(position);
        holder.name.setText(child.getProductName());
        holder.price.setText(child.getProductPrice());
        holder.date.setText(child.getCurrentDate());
        holder.time.setText(child.getCurrentTime());
        holder.quantity.setText(child.getTotalQuantity());
        holder.totalPrice.setText(String.valueOf(child.getTotalPrice()));
    }

    public static class ChildViewHolder extends RecyclerView.ViewHolder {

        TextView name, price, date, time, quantity, totalPrice;

        public ChildViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.productName);
            price = itemView.findViewById(R.id.productPrice);
            date = itemView.findViewById(R.id.currentDate);
            time = itemView.findViewById(R.id.currentTime);
            quantity = itemView.findViewById(R.id.totalQuantity);
            totalPrice = itemView.findViewById(R.id.totalPrice);
        }
    }
}
