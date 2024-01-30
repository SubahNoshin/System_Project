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

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.example.g_ecommerce.R;
import com.example.g_ecommerce.activities.AddDiscountActivity;
import com.example.g_ecommerce.activities.AddFruitActivity;
import com.example.g_ecommerce.activities.ViewAllActivity;
import com.example.g_ecommerce.adapters.PopularAdapters;
import com.example.g_ecommerce.models.PopularModel;

import java.util.ArrayList;
import java.util.List;

public class Fruit1Adapter extends RecyclerView.Adapter<Fruit1Adapter.ItemViewHolder>{

    Context context;
    ArrayList<EachFruit1> fruits;
    public Fruit1Adapter(Context context,ArrayList<EachFruit1>fruits){
        this.context=context;
        this.fruits=fruits;

    }

    @NonNull
    @Override
    public Fruit1Adapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Fruit1Adapter.ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.each_fruit1_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Fruit1Adapter.ItemViewHolder holder, int position) {
        EachFruit1 fruit = fruits.get(position);
        holder.tvname.setText(fruit.getName());
        holder.tvDescription.setText(fruit.getDescription());
        holder.tvDiscount.setText(fruit.getDiscount());
        holder.tvrating.setText(fruit.getRating());


        Glide.with(context).load(fruits.get(holder.getAdapterPosition()).getImg_url())
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .error(R.drawable.fruits).into(holder.ivImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Intent intent =new Intent(context, AddDiscountActivity.class);
                intent.putExtra("fruit",fruits.get(position));
                context.startActivity(intent);
            }
        });

    }
    @Override
    public int getItemCount() {
        return fruits.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImage;
        private TextView tvname,tvDescription,tvDiscount,tvType,tvrating;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            ivImage =itemView.findViewById(R.id.pop_img1);
            tvname= itemView.findViewById(R.id.pop_name1);
            tvDescription = itemView.findViewById(R.id.pop_des1);
            tvDiscount = itemView.findViewById(R.id.pop_discount1);
            tvrating=itemView.findViewById(R.id.pop_rating1);





        }
    }
}
