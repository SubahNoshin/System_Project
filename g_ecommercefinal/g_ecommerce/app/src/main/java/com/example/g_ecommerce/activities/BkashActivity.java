package com.example.g_ecommerce.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.g_ecommerce.R;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class BkashActivity extends AppCompatActivity {
    EditText enternumber;
    Button getotpbutton;
    FirebaseAuth auth;
    FirebaseDatabase db;
    FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bkash);
        enternumber = findViewById(R.id.input_mobile_number);
        getotpbutton = findViewById(R.id.buttongetotp);
        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();
        db = FirebaseDatabase.getInstance();
        if(enternumber!=null) {
            getotpbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(currentUser!=null) {
                        if (!enternumber.getText().toString().trim().isEmpty()) {
                            if ((enternumber.getText().toString().trim()).length() == 11) {
                                db.getReference("Users").child(auth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                                        String firebaseValue = snapshot.child("phoneno").getValue(String.class);

                                        // Get the user input
                                        String userInput = enternumber.getText().toString(); // Replace with the user's entered value

                                        // Compare the user input with Firebase value
                                        if (firebaseValue != null && firebaseValue.equals(userInput) && snapshot.hasChild("address")) {
                                            Intent intent = new Intent(getApplicationContext(), SeeDeliveryManActivity.class);
                                            intent.putExtra("mobile", enternumber.getText().toString());
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            Toast.makeText(BkashActivity.this, "Incorrect Phone number or Address is empty", Toast.LENGTH_SHORT).show();
                                        }


                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });






                            } else {
                                Toast.makeText(BkashActivity.this, "Please enter correct number", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(BkashActivity.this, "Enter mobile number", Toast.LENGTH_SHORT).show();
                        }
                    }

                }
            });


}}}
