package com.example.g_ecommerce.activities;

import static java.lang.Integer.parseInt;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.g_ecommerce.R;
import com.example.g_ecommerce.classes.Derivery;
import com.example.g_ecommerce.classes.EachFruit;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class DeliveryBoyAddActivity extends AppCompatActivity {


    private EditText deli_name2,deli_loc2,deli_distance2;
    private Button deli_update2;
    private ActivityResultLauncher<String> mGetContent4;

    private Derivery derivery;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivary_boy_add);
        deli_name2 = findViewById(R.id.deli_name2);
        deli_loc2 = findViewById(R.id.deli_loc2);
        deli_distance2=findViewById(R.id.deli_distance2);
        deli_update2= findViewById(R.id.deli_update2);
        derivery = (Derivery) getIntent().getSerializableExtra("fruit");
        showData();
        deli_update2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String name = String.valueOf(deli_name2.getText());

                String location = String.valueOf(deli_loc2.getText());
                String distance = String.valueOf(deli_distance2.getText());

                if (name.isEmpty() ||

                        distance.isEmpty() ||
                        location.isEmpty())
                         {
                    Toast.makeText(DeliveryBoyAddActivity.this, "Fill All the forms", Toast.LENGTH_SHORT).show();
                    return;
                }


            }
        });

        deli_update2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieving values from UI elements
                String name = String.valueOf(deli_name2.getText());
                String location = String.valueOf(deli_loc2.getText());
                String distance = String.valueOf(deli_distance2.getText());

                // Validating input
                if (name.isEmpty() || distance.isEmpty() || location.isEmpty()) {
                    Toast.makeText(DeliveryBoyAddActivity.this, "Fill All the forms", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Assuming you have a Firebase reference for delivery boys
                DatabaseReference deliveryBoyRef = FirebaseDatabase.getInstance().getReference().child("DeriveryMan");

                // Update the specific record using the delivery boy's key/id
                if (derivery != null) {
                    String deliveryBoyKey = derivery.getId(); // Replace with the actual method to get the key/id
                    if (deliveryBoyKey != null) {
                        // Updating the values in Firebase
                        deliveryBoyRef.child(deliveryBoyKey).child("name").setValue(name);
                        deliveryBoyRef.child(deliveryBoyKey).child("location").setValue(location);
                        deliveryBoyRef.child(deliveryBoyKey).child("distance").setValue(distance);

                        // Optionally, you can also update the derivery object
                        derivery.setName(name);
                        derivery.setLocation(location);
                        derivery.setDistance(distance);

                        // Notify the user that the update is successful
                        Toast.makeText(DeliveryBoyAddActivity.this, "Delivery Boy Updated", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });






    }
    private void showData(){

        if(derivery == null) return;
        deli_name2.setText(derivery.getName());

        deli_loc2.setText(derivery.getLocation());
        deli_distance2.setText(derivery.getDistance());
    }



}