package com.example.g_ecommerce.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.g_ecommerce.R;
import com.example.g_ecommerce.classes.Derivery;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddDeliveryManActivity extends AppCompatActivity {
    EditText deliname, address, distance, id;
    String dname, daddress, ddistance, did;
    Button btn;
    FirebaseDatabase database;
    DatabaseReference reference;
    long i=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_delivery_man);

        deliname = findViewById(R.id.deli_name);
        address = findViewById(R.id.address);
        distance = findViewById(R.id.distance);
        btn = findViewById(R.id.deli_add);
        // id = findViewById(R.id.id);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dname = deliname.getText().toString();
                daddress = address.getText().toString();
                ddistance = distance.getText().toString();
                //did = distance.getText().toString();
                reference = FirebaseDatabase.getInstance().getReference().child("Delivery");
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists())
                        {i = (snapshot.getChildrenCount());
                            //System.out.println(did);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {   }
                });


                if( !dname.isEmpty() && !daddress.isEmpty() && !ddistance.isEmpty()){
                    // System.out.println(did);


                    database = FirebaseDatabase.getInstance();
                    reference = database.getReference("Delivery");
                    String s = reference.child(String.valueOf(i+1)).getKey();
                    Derivery model =new Derivery(dname, daddress, ddistance,s);
                    reference.child(String.valueOf(i+1)).setValue(model);
                    //  reference.child(String.valueOf(i+1)).child("id").setValue(String.valueOf(i+1));
                    Toast.makeText(AddDeliveryManActivity.this, "Delivery man added..", Toast.LENGTH_SHORT).show();


                }

            }
        });


    }
}