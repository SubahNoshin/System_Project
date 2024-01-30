package com.example.g_ecommerce.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.g_ecommerce.R;
import com.example.g_ecommerce.adapters.ParentAdapter;
import com.example.g_ecommerce.models.BuyerModel;
import com.example.g_ecommerce.models.MyCartModel;
import com.example.g_ecommerce.models.UserWithIDModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AllPurchasesActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private ParentAdapter parentAdapter;
    private List<BuyerModel> buyerList = new ArrayList<>();
    private List<UserWithIDModel> users = new ArrayList<>();
    FirebaseFirestore database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_purchases);
        progressBar = findViewById(R.id.progressBar);

        initRecycler();
        getUserDetailsFromFirebase();
    }

    private void getUserDetailsFromFirebase() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    try{
                        Log.d("FirebaseCheck", "onDataChange: got!!");
                        for(DataSnapshot dataSnapshot: snapshot.child("Users").getChildren()) {
                            String id = dataSnapshot.getKey();
                            String email = (String) dataSnapshot.child("email").getValue();
                            String name = (String) dataSnapshot.child("name").getValue();
                            String password = (String) dataSnapshot.child("password").getValue();
                            String phone = (String) dataSnapshot.child("phoneno").getValue();
                            UserWithIDModel user = new UserWithIDModel(name, email, password, phone, id);
                            fetchDateFromFireStore(user);
                            users.add(user);
                        }
                    }
                    catch (Exception e) {
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void fetchDateFromFireStore(UserWithIDModel user) {
        database = FirebaseFirestore.getInstance();
        database.collection("CurrentUser").document(user.getId()).collection("MyOrder").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    List<MyCartModel> purchaseList = new ArrayList<>();
                    for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()){
                        String documentId = documentSnapshot.getId();
                        MyCartModel cartModel = documentSnapshot.toObject(MyCartModel.class);
                        purchaseList.add(cartModel);
                    }
                    BuyerModel buyer = new BuyerModel(user.getName(), user.getPhone(), purchaseList);
                    if(!purchaseList.isEmpty()) {
                        buyerList.add(buyer);
                        parentAdapter.updateList(buyerList);
                        parentAdapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);
                    }
                    Log.d("FireStoreCheck", "onComplete: " + user.getId() + " " + buyerList.size());
                }
            }
        });
    }

    private void initRecycler(){
        recyclerView = findViewById(R.id.rv_parent);

        Log.d("BuyerCheck", "initRecycler: " + buyerList.size());
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        parentAdapter = new ParentAdapter(buyerList);
        recyclerView.setAdapter(parentAdapter);
    }
}