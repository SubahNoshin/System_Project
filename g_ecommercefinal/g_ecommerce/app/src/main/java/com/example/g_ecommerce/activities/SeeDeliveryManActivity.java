package com.example.g_ecommerce.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.g_ecommerce.R;
import com.example.g_ecommerce.classes.Number;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SeeDeliveryManActivity extends AppCompatActivity {
    Button button5;
    TextView deli_name, deli_loc, deli_distance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_delivery_man);

        button5 = findViewById(R.id.button5);
        deli_name = findViewById(R.id.deli_name);
        deli_loc = findViewById(R.id.deli_loc);
        deli_distance = findViewById(R.id.deli_distance);

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String storedValue = Number.getData();
                DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("DeriveryMan").child(storedValue);

                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            String name = dataSnapshot.child("name").getValue(String.class);
                            String location = dataSnapshot.child("location").getValue(String.class);
                            String distance = dataSnapshot.child("distance").getValue(String.class);
                            deli_name.setText(name);
                            deli_loc.setText(location);
                            deli_distance.setText(distance);


                            showToast("Delivery Man Recieved information");
                        } else {
                            showToast("Node with key 1 does not exist");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }


                });
            }


            private void showToast(String message) {
                Toast.makeText(SeeDeliveryManActivity.this, message, Toast.LENGTH_SHORT).show();
            }

        });}}
