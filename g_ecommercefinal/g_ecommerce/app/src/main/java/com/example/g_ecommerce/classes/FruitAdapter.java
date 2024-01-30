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
import com.example.g_ecommerce.activities.AddFruitActivity;
import com.example.g_ecommerce.R;

import java.util.ArrayList;

public class FruitAdapter extends RecyclerView.Adapter<FruitAdapter.ItemViewHolder>{

     Context context;
     ArrayList<EachFruit>fruits;
     public FruitAdapter(Context context,ArrayList<EachFruit>fruits){
      this.context=context;
      this.fruits=fruits;

     }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.each_fruit_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        EachFruit fruit = fruits.get(position);
        holder.tvname.setText(fruit.getName());
        holder.tvDescription.setText(fruit.getDescription());
        holder.tvType.setText(fruit.getType());
        holder.tvPrice.setText("Price:tk "+fruit.getPrice()+"/p");
        holder.tvrating.setText(fruit.getRating());


        Glide.with(context).load(fruits.get(holder.getAdapterPosition()).getImg_url())
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .error(R.drawable.fruits).into(holder.ivImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Intent intent =new Intent(context,AddFruitActivity.class);
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
        private TextView tvname,tvDescription,tvPrice,tvType,tvrating;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            ivImage =itemView.findViewById(R.id.ivImage);
            tvname= itemView.findViewById(R.id.tvname);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvType=itemView.findViewById(R.id.tvType);
            tvrating=itemView.findViewById(R.id.tvrating);





        }
    }
}
