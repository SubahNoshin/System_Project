package com.example.g_ecommerce.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.g_ecommerce.R;
import com.example.g_ecommerce.models.MyCartModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlacedOrderActivity extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseFirestore firestore;
    ImageView bkash;
    Button btncod;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placed_order);
        auth=FirebaseAuth.getInstance();
        firestore=FirebaseFirestore.getInstance();
        bkash = findViewById(R.id.bkash);
        btncod = findViewById(R.id.btncod);

        List<MyCartModel>list= (ArrayList<MyCartModel>) getIntent().getSerializableExtra("itemList");

        if(list!= null && list.size()>0){
            for (MyCartModel model :list){
                final HashMap<String, Object> cartMap = new HashMap<>();

                cartMap.put("productName", model.getProductName());
                cartMap.put("productPrice", model.getProductPrice());
                cartMap.put("currentDate", model.getCurrentDate());
                cartMap.put("currentTime",model.getCurrentTime());
                cartMap.put("totalQuantity", model.getTotalQuantity());
                cartMap.put("totalPrice", model.getTotalPrice());

                firestore.collection("CurrentUser").document(auth.getCurrentUser().getUid()).collection("MyOrder").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {

                        Toast.makeText(PlacedOrderActivity.this, "Your Order is Booked", Toast.LENGTH_SHORT).show();


                    }
                });


            }


        }

        //pay through bkash
        bkash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PlacedOrderActivity.this, BkashActivity.class));
                finish();
            }
        });

        //pay offline
        btncod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PlacedOrderActivity.this, SeeDeliveryManActivity.class));
                finish();
            }
        });





    }
}