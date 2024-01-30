package com.example.g_ecommerce.activities;

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
import com.example.g_ecommerce.classes.EachFruit2;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class AddRecommendedActivity extends AppCompatActivity {
    private ImageView iv_image2;
    private EditText fruit_name2,fruit_rating2,fruit_description2;
    private Button fruit_add2;
    private ActivityResultLauncher<String> mGetContent2;
    private Uri photoUri2;
    private EachFruit2 eachFruit2;
    private Button fruit_delete2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recommended);
        iv_image2 = findViewById(R.id.iv_image2);
        fruit_name2 = findViewById(R.id.fruit_name2);

        fruit_rating2 = findViewById(R.id.fruit_rating2);

        fruit_description2 = findViewById(R.id.fruit_description2);
        fruit_add2 = findViewById(R.id.fruit_add2);
        fruit_delete2 = findViewById(R.id.fruit_delete2);
        startPicker();
        eachFruit2 = (EachFruit2) getIntent().getSerializableExtra("fruit");
        showData();
        iv_image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGetContent2.launch("image/*");
            }
        });


        fruit_add2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (photoUri2 == null && eachFruit2 == null) {
                    Toast.makeText(AddRecommendedActivity.this, "Upload an image", Toast.LENGTH_SHORT).show();
                    return;
                }

                String name = String.valueOf(fruit_name2.getText());


                String description = String.valueOf(fruit_description2.getText());
                String rating = String.valueOf(fruit_rating2.getText());

                if (name.isEmpty() ||

                        description.isEmpty() ||

                        rating.isEmpty()) {
                    Toast.makeText(AddRecommendedActivity.this, "Fill All the forms", Toast.LENGTH_SHORT).show();
                    return;
                }

                startUploading(name,  description,  rating);
            }
        });



        fruit_delete2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (eachFruit2 == null) return;

                String id = eachFruit2.getFruit_id();
                if (id == null) return;

                // Get the reference to the Firestore collection
                CollectionReference ticketsCollection = FirebaseFirestore.getInstance().collection("Recommended");





                DocumentReference ticketDocument = ticketsCollection.document(id);

                // Delete the document
                ticketDocument.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(AddRecommendedActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(AddRecommendedActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                            Log.e("FirestoreDeleteError", "Error deleting document", task.getException());
                        }
                    }
                });
            }
        });
    }
    private void showData(){
        if(eachFruit2 == null) return;


        Glide.with(this).load(eachFruit2.getImg_url()).error(R.drawable.fruits).into(iv_image2);
        fruit_name2.setText(eachFruit2.getName());


        fruit_description2.setText(eachFruit2.getDescription());
        fruit_rating2.setText(eachFruit2.getRating());

        fruit_delete2.setVisibility(View.VISIBLE);
        fruit_add2.setText("UPDATE");

    }

    private void startUploading(String name,  String des,String rating){

        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Saving product info...");
        dialog.setTitle("Adding product");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        String fName = System.currentTimeMillis()+"";

        if(photoUri2 != null) {

            StorageReference ref = FirebaseStorage.getInstance().getReference().child("admin2").child(fName);

            ref.putFile(photoUri2)
                    .addOnSuccessListener(taskSnapshot ->
                            ref.getDownloadUrl()
                                    .addOnSuccessListener(uri -> {
                                        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

                                        // Create a reference to the "tickets" collection
                                        CollectionReference ticketsCollection = firestore.collection("Recommended");

                                        // Generate a new document ID if it's a new ticket, or use the existing ID
                                        String id = eachFruit2 != null ? eachFruit2.getFruit_id() : ticketsCollection.document().getId();

                                        if (id == null) {
                                            dialog.dismiss();
                                            Toast.makeText(AddRecommendedActivity.this, "failed", Toast.LENGTH_SHORT).show();
                                            return;
                                        }


                                        EachFruit2 fruit = new EachFruit2( name, des,rating,uri.toString(),id);


                                        ticketsCollection.document(id)
                                                .set(fruit)
                                                .addOnCompleteListener(task -> {
                                                    dialog.dismiss();
                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(AddRecommendedActivity.this, "Products Added", Toast.LENGTH_SHORT).show();
                                                        finish();
                                                    } else {
                                                        Toast.makeText(AddRecommendedActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                                                    }
                                                });

                                    })
                                    .addOnFailureListener(e -> {
                                        dialog.dismiss();
                                        Toast.makeText(AddRecommendedActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                                        e.printStackTrace();
                                    })
                    )
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddRecommendedActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    });
        }
        else{
            // Get a reference to the Firestore database
            FirebaseFirestore firestore = FirebaseFirestore.getInstance();


            CollectionReference ticketsCollection = firestore.collection("Recommended");

            String id = eachFruit2.getFruit_id();

            if (id == null) {
                dialog.dismiss();
                Toast.makeText(AddRecommendedActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                return;
            }

// Create an EachTicket object
            EachFruit2 fruit = new EachFruit2( name, des,rating,eachFruit2.getImg_url(),id);

            // Set the value of the document in Firestore
            ticketsCollection.document(id)
                    .set(fruit)
                    .addOnCompleteListener(task -> {
                        dialog.dismiss();
                        if (task.isSuccessful()) {
                            Toast.makeText(AddRecommendedActivity.this, "Products added", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(AddRecommendedActivity.this, "Failed", Toast.LENGTH_SHORT).show();

                        }
                    });
        }
    }
    private void startPicker(){
        mGetContent2 = registerForActivityResult(
                new ActivityResultContracts.GetContent(), result -> {
                    if (result != null) {
                        photoUri2 = result;
                        iv_image2.setImageURI(photoUri2);
                    }
                }
        );
    }




}

