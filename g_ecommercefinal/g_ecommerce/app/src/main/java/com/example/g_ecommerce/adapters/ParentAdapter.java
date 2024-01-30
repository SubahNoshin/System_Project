package com.example.g_ecommerce.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.g_ecommerce.R;
import com.example.g_ecommerce.models.BuyerModel;

import java.util.List;

public class ParentAdapter extends RecyclerView.Adapter<ParentAdapter.ParentViewHolder> {

    private List<BuyerModel> parents;
    private final RecyclerView.RecycledViewPool viewPool;

    public ParentAdapter(List<BuyerModel> parents) {
        this.parents = parents;
        this.viewPool = new RecyclerView.RecycledViewPool();
    }

    @Override
    public ParentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.per_buyer_recyclerview_layout, parent, false);
        return new ParentViewHolder(v);
    }

    @Override
    public int getItemCount() {
        return parents.size();
    }

    @Override
    public void onBindViewHolder(ParentViewHolder holder, int position) {
        BuyerModel parent = parents.get(position);
        holder.buyerName.setText(parent.getName());
        holder.buyerPhone.setText(parent.getPhone());
        RecyclerView recyclerView = holder.recyclerView;

        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext(), RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(new ChildAdapter(parent.getPurchases()));
        recyclerView.setRecycledViewPool(viewPool);
    }

    public void updateList(List<BuyerModel> buyerList) {
        this.parents = buyerList;
    }

    public static class ParentViewHolder extends RecyclerView.ViewHolder {

        private RecyclerView recyclerView;
        private TextView buyerName;
        private TextView buyerPhone;

        public ParentViewHolder(View itemView) {
            super(itemView);
            this.recyclerView = itemView.findViewById(R.id.rv_child);
            this.buyerName = itemView.findViewById(R.id.buyerName);
            this.buyerPhone = itemView.findViewById(R.id.buyerPhone);
        }
    }
}

