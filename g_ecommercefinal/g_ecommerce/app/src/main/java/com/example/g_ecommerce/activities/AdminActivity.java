package com.example.g_ecommerce.activities;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.g_ecommerce.R;
import com.example.g_ecommerce.classes.EachFruit;
import com.example.g_ecommerce.classes.FruitAdapter;
import com.example.g_ecommerce.models.MyCartModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class AdminActivity extends AppCompatActivity {
    private FloatingActionButton fabAdd;
    private RecyclerView recyclerView;

    private CardView allPurchasesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        fabAdd = findViewById(R.id.floatingActionButton);
        recyclerView = findViewById(R.id.recyclerView);


        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity.this,AddFruitActivity.class));
            }
        });


        showAllFruits();
    }
    private void showAllFruits(){
        CollectionReference fruitsCollection= FirebaseFirestore.getInstance().collection("AllProducts");
        fruitsCollection.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    ArrayList<EachFruit> fruits = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        EachFruit frt = document.toObject(EachFruit.class);
                        if (frt != null) {
                            fruits.add(frt);
                        }

                    }
                    FruitAdapter adapter = new FruitAdapter(AdminActivity.this, fruits);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(AdminActivity.this, LinearLayoutManager.VERTICAL, false);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(adapter);

                } else {
                    // Handle failure
                    Exception e = task.getException();
                    if (e != null) {
                        // Log or display the error message
                        Log.e("FirestoreError", "Error fetching tickets", e);
                        Toast.makeText(AdminActivity.this, "Error fetching tickets", Toast.LENGTH_SHORT).show();
                    }
                }
            }

        });

    }}


