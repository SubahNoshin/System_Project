package com.example.g_ecommerce.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;
import com.example.g_ecommerce.classes.Number;

import com.example.g_ecommerce.R;
import com.example.g_ecommerce.classes.Derivery;
import com.example.g_ecommerce.classes.DeriveryAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DeliveryBoyActivity extends AppCompatActivity {

    private RecyclerView recyclerView4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_boy);

        recyclerView4 = findViewById(R.id.recyclerView4);

        showDeliveryManWithIdOne();
    }

    private void showDeliveryManWithIdOne() {
        String storedValue = Number.getData();
        DatabaseReference deliveryManRef = FirebaseDatabase.getInstance().getReference().child("DeriveryMan");

        deliveryManRef.orderByChild("id").equalTo(storedValue).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    ArrayList<Derivery> deliveryMen = new ArrayList<>();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Derivery deliveryMan = snapshot.getValue(Derivery.class);
                        if (deliveryMan != null) {
                            deliveryMen.add(deliveryMan);
                        }
                    }

                    if (!deliveryMen.isEmpty()) {
                        // Data retrieval successful
                        DeriveryAdapter adapter = new DeriveryAdapter(DeliveryBoyActivity.this, deliveryMen);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(DeliveryBoyActivity.this, LinearLayoutManager.VERTICAL, false);
                        recyclerView4.setLayoutManager(layoutManager);
                        recyclerView4.setAdapter(adapter);
                        showToast("Delivery that admin send data retrieved successfully");
                    } else {
                        // No delivery man found with ID '1'
                        showToast("No delivery man found with ID '1'");
                    }
                } else {
                    // No data found
                    showToast("No delivery man found with ID '1'");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                showToast("Error fetching delivery man data: " + databaseError.getMessage());
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(DeliveryBoyActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}
