package com.example.g_ecommerce.activities;

import static java.lang.Integer.parseInt;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.g_ecommerce.R;

import com.example.g_ecommerce.classes.EachFruit1;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class AddDiscountActivity extends AppCompatActivity {
    private ImageView iv_image1;
    private EditText fruit_name1,fruit_discount1,fruit_rating1,fruit_type1,fruit_description1;
    private Button fruit_add1;
    private ActivityResultLauncher<String> mGetContent1;
    private Uri photoUri1;
    private EachFruit1 eachFruit1;
    private Button fruit_delete1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_discount);

        iv_image1 = findViewById(R.id.iv_image1);
        fruit_name1 = findViewById(R.id.fruit_name1);
        fruit_discount1 = findViewById(R.id.fruit_discount1);
        fruit_rating1 = findViewById(R.id.fruit_rating1);
        fruit_type1 = findViewById(R.id.fruit_type1);
        fruit_description1 = findViewById(R.id.fruit_description1);
        fruit_add1 = findViewById(R.id.fruit_add1);
        fruit_delete1 = findViewById(R.id.fruit_delete1);
        startPicker();
        eachFruit1 = (EachFruit1) getIntent().getSerializableExtra("fruit");
        showData();
        iv_image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGetContent1.launch("image/*");
            }
        });


        fruit_add1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (photoUri1 == null && eachFruit1 == null) {
                    Toast.makeText(AddDiscountActivity.this, "Upload an image", Toast.LENGTH_SHORT).show();
                    return;
                }

                String name = String.valueOf(fruit_name1.getText());
                String discount =String.valueOf(fruit_discount1.getText());

                String description = String.valueOf(fruit_description1.getText());
                String type = String.valueOf(fruit_type1.getText());
                String rating = String.valueOf(fruit_rating1.getText());

                if (name.isEmpty() ||
                        discount.isEmpty() ||
                        description.isEmpty() ||
                        type.isEmpty() ||
                        rating.isEmpty()) {
                    Toast.makeText(AddDiscountActivity.this, "Fill All the forms", Toast.LENGTH_SHORT).show();
                    return;
                }

                startUploading(name, discount, description, type, rating);
            }
        });



        fruit_delete1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (eachFruit1 == null) return;

                String id = eachFruit1.getFruit_id();
                if (id == null) return;

                // Get the reference to the Firestore collection
                CollectionReference ticketsCollection = FirebaseFirestore.getInstance().collection("PopularProducts");





                DocumentReference ticketDocument = ticketsCollection.document(id);

                // Delete the document
                ticketDocument.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(AddDiscountActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(AddDiscountActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                            Log.e("FirestoreDeleteError", "Error deleting document", task.getException());
                        }
                    }
                });
            }
        });
    }
    private void showData(){
        if(eachFruit1 == null) return;


        Glide.with(this).load(eachFruit1.getImg_url()).error(R.drawable.fruits).into(iv_image1);
        fruit_name1.setText(eachFruit1.getName());
        fruit_discount1.setText(eachFruit1.getDiscount());
        fruit_type1.setText(eachFruit1.getType());
        fruit_description1.setText(eachFruit1.getDescription());
        fruit_rating1.setText(eachFruit1.getRating());

        fruit_delete1.setVisibility(View.VISIBLE);
        fruit_add1.setText("ADD");

    }

    private void startUploading(String name, String discount, String des, String type,String rating){

        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Saving product info...");
        dialog.setTitle("Adding product");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        String fName = System.currentTimeMillis()+"";

        if(photoUri1 != null) {

            StorageReference ref = FirebaseStorage.getInstance().getReference().child("admin1").child(fName);

            ref.putFile(photoUri1)
                    .addOnSuccessListener(taskSnapshot ->
                            ref.getDownloadUrl()
                                    .addOnSuccessListener(uri -> {
                                        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

                                        // Create a reference to the "tickets" collection
                                        CollectionReference ticketsCollection = firestore.collection("PopularProducts");

                                        // Generate a new document ID if it's a new ticket, or use the existing ID
                                        String id = eachFruit1 != null ? eachFruit1.getFruit_id() : ticketsCollection.document().getId();

                                        if (id == null) {
                                            dialog.dismiss();
                                            Toast.makeText(AddDiscountActivity.this, "failed", Toast.LENGTH_SHORT).show();
                                            return;
                                        }


                                        EachFruit1 fruit = new EachFruit1(uri.toString(), name,type, des,discount,rating,id);


                                        ticketsCollection.document(id)
                                                .set(fruit)
                                                .addOnCompleteListener(task -> {
                                                    dialog.dismiss();
                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(AddDiscountActivity.this, "Products Added", Toast.LENGTH_SHORT).show();
                                                        finish();
                                                    } else {
                                                        Toast.makeText(AddDiscountActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                                                    }
                                                });

                                    })
                                    .addOnFailureListener(e -> {
                                        dialog.dismiss();
                                        Toast.makeText(AddDiscountActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                                        e.printStackTrace();
                                    })
                    )
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddDiscountActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    });
        }
        else{
            // Get a reference to the Firestore database
            FirebaseFirestore firestore = FirebaseFirestore.getInstance();


            CollectionReference ticketsCollection = firestore.collection("PopularProducts");

            String id = eachFruit1.getFruit_id();

            if (id == null) {
                dialog.dismiss();
                Toast.makeText(AddDiscountActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                return;
            }

// Create an EachTicket object
            EachFruit1 fruit = new EachFruit1(eachFruit1.getImg_url(), name,type, des,discount,rating,id);

            // Set the value of the document in Firestore
            ticketsCollection.document(id)
                    .set(fruit)
                    .addOnCompleteListener(task -> {
                        dialog.dismiss();
                        if (task.isSuccessful()) {
                            Toast.makeText(AddDiscountActivity.this, "Products added", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(AddDiscountActivity.this, "Failed", Toast.LENGTH_SHORT).show();

                        }
                    });
        }
    }
    private void startPicker(){
        mGetContent1 = registerForActivityResult(
                new ActivityResultContracts.GetContent(), result -> {
                    if (result != null) {
                        photoUri1 = result;
                        iv_image1.setImageURI(photoUri1);
                    }
                }
        );
    }




}


