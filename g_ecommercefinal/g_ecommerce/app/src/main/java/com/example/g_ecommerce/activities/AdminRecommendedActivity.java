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
import com.example.g_ecommerce.classes.EachFruit1;
import com.example.g_ecommerce.classes.EachFruit2;
import com.example.g_ecommerce.classes.Fruit1Adapter;
import com.example.g_ecommerce.classes.Fruit2Adapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class AdminRecommendedActivity extends AppCompatActivity {
    private FloatingActionButton fabAdd2;
    private RecyclerView recyclerView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_recommended);
        fabAdd2 = findViewById(R.id.floatingActionButton2);
        recyclerView2 = findViewById(R.id.recyclerView2);
        fabAdd2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminRecommendedActivity.this, AddRecommendedActivity.class));


            }
        });
        showAllFruits();
    }
        private void showAllFruits(){
            CollectionReference fruitsCollection= FirebaseFirestore.getInstance().collection("Recommended");
            fruitsCollection.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        ArrayList<EachFruit2> fruits = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            EachFruit2 frt = document.toObject(EachFruit2.class);
                            if (frt != null) {
                                fruits.add(frt);
                            }

                        }
                        Fruit2Adapter adapter = new Fruit2Adapter(AdminRecommendedActivity.this, fruits);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(AdminRecommendedActivity.this, LinearLayoutManager.HORIZONTAL, false);
                        recyclerView2.setLayoutManager(layoutManager);
                        recyclerView2.setAdapter(adapter);

                    } else {
                        // Handle failure
                        Exception e = task.getException();
                        if (e != null) {
                            // Log or display the error message
                            Log.e("FirestoreError", "Error fetching products", e);
                            Toast.makeText(AdminRecommendedActivity.this, "Error fetching products", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

            });

        }}

