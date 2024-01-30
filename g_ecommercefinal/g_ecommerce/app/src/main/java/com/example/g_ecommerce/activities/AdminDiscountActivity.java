package com.example.g_ecommerce.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.g_ecommerce.R;
import com.example.g_ecommerce.classes.EachFruit;
import com.example.g_ecommerce.classes.EachFruit1;
import com.example.g_ecommerce.classes.Fruit1Adapter;
import com.example.g_ecommerce.classes.FruitAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class AdminDiscountActivity extends AppCompatActivity {
    private FloatingActionButton fabAdd1;
    private RecyclerView recyclerView1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_discount);
        fabAdd1 = findViewById(R.id.floatingActionButton1);
        recyclerView1 = findViewById(R.id.recyclerView1);
        fabAdd1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminDiscountActivity.this,AddDiscountActivity.class));


            }
        });
        showAllFruits();
    }
    private void showAllFruits(){
        CollectionReference fruitsCollection= FirebaseFirestore.getInstance().collection("PopularProducts");
        fruitsCollection.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    ArrayList<EachFruit1> fruits = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        EachFruit1 frt = document.toObject(EachFruit1.class);
                        if (frt != null) {
                            fruits.add(frt);
                        }

                    }
                    Fruit1Adapter adapter = new Fruit1Adapter(AdminDiscountActivity.this, fruits);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(AdminDiscountActivity.this, LinearLayoutManager.HORIZONTAL, false);
                    recyclerView1.setLayoutManager(layoutManager);
                    recyclerView1.setAdapter(adapter);

                } else {
                    // Handle failure
                    Exception e = task.getException();
                    if (e != null) {
                        // Log or display the error message
                        Log.e("FirestoreError", "Error fetching products", e);
                        Toast.makeText(AdminDiscountActivity.this, "Error fetching products", Toast.LENGTH_SHORT).show();
                    }
                }
            }

        });

    }}



