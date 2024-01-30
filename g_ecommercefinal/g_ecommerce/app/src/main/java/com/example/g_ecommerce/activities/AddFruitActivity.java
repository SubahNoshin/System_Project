package com.example.g_ecommerce.activities;



import static java.lang.Integer.parseInt;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;

import com.example.g_ecommerce.R;
import com.example.g_ecommerce.classes.EachFruit;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class AddFruitActivity extends AppCompatActivity {
    private ImageView iv_image;
    private EditText fruit_name,fruit_price,fruit_rating,fruit_type,fruit_description;
    private  Button fruit_add;
    private ActivityResultLauncher<String>mGetContent;
    private Uri photoUri;
    private EachFruit eachFruit;
    private Button fruit_delete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_fruit);
        iv_image = findViewById(R.id.iv_image);
        fruit_name = findViewById(R.id.fruit_name);
        fruit_price = findViewById(R.id.fruit_price);
        fruit_rating = findViewById(R.id.fruit_rating);
        fruit_type = findViewById(R.id.fruit_type);
        fruit_description = findViewById(R.id.fruit_description);
        fruit_add = findViewById(R.id.fruit_add);
        fruit_delete = findViewById(R.id.fruit_delete);
        startPicker();
        eachFruit = (EachFruit) getIntent().getSerializableExtra("fruit");
        showData();
        iv_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGetContent.launch("image/*");
            }
        });


        fruit_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (photoUri == null && eachFruit == null) {
                    Toast.makeText(AddFruitActivity.this, "Upload an image", Toast.LENGTH_SHORT).show();
                    return;
                }

                String name = String.valueOf(fruit_name.getText());
                String p =String.valueOf(fruit_price.getText());
                int price = parseInt (String.valueOf(fruit_price.getText()));
                String description = String.valueOf(fruit_description.getText());
                String type = String.valueOf(fruit_type.getText());
                String rating = String.valueOf(fruit_rating.getText());

                if (name.isEmpty() ||
                        p.isEmpty() ||
                        description.isEmpty() ||
                        type.isEmpty() ||
                        rating.isEmpty()) {
                    Toast.makeText(AddFruitActivity.this, "Fill All the forms", Toast.LENGTH_SHORT).show();
                    return;
                }

                startUploading(name, price, description, type, rating);
            }
        });



        fruit_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (eachFruit == null) return;

                String id = eachFruit.getFruit_id();
                if (id == null) return;

                // Get the reference to the Firestore collection
                CollectionReference ticketsCollection = FirebaseFirestore.getInstance().collection("AllProducts");





                DocumentReference ticketDocument = ticketsCollection.document(id);

                // Delete the document
                ticketDocument.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(AddFruitActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(AddFruitActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                            Log.e("FirestoreDeleteError", "Error deleting document", task.getException());
                        }
                    }
                });
            }
        });
    }
        private void showData(){
            if(eachFruit == null) return;


            Glide.with(this).load(eachFruit.getImg_url()).error(R.drawable.fruits).into(iv_image);
            fruit_name.setText(eachFruit.getName());
            int pp = eachFruit.getPrice();
            fruit_price.setText(String.valueOf(pp));
            fruit_type.setText(eachFruit.getType());
            fruit_description.setText(eachFruit.getDescription());
            fruit_rating.setText(eachFruit.getRating());

            fruit_delete.setVisibility(View.VISIBLE);
            fruit_add.setText("ADD");

        }

        private void startUploading(String name, int  price, String des, String type,String rating){

            ProgressDialog dialog = new ProgressDialog(this);
            dialog.setMessage("Saving product info...");
            dialog.setTitle("Adding product");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            String fName = System.currentTimeMillis()+"";

            if(photoUri != null) {

                StorageReference ref = FirebaseStorage.getInstance().getReference().child("admin").child(fName);

                ref.putFile(photoUri)
                        .addOnSuccessListener(taskSnapshot ->
                                ref.getDownloadUrl()
                                        .addOnSuccessListener(uri -> {
                                            FirebaseFirestore firestore = FirebaseFirestore.getInstance();

                                       // Create a reference to the "tickets" collection
                                            CollectionReference ticketsCollection = firestore.collection("AllProducts");

                                            // Generate a new document ID if it's a new ticket, or use the existing ID
                                            String id = eachFruit != null ? eachFruit.getFruit_id() : ticketsCollection.document().getId();

                                            if (id == null) {
                                                dialog.dismiss();
                                                Toast.makeText(AddFruitActivity.this, "failed", Toast.LENGTH_SHORT).show();
                                                return;
                                            }


                                            EachFruit fruit = new EachFruit(uri.toString(), name,type, des,price,rating,id);


                                            ticketsCollection.document(id)
                                                    .set(fruit)
                                                    .addOnCompleteListener(task -> {
                                                        dialog.dismiss();
                                                        if (task.isSuccessful()) {
                                                            Toast.makeText(AddFruitActivity.this, "Products Added", Toast.LENGTH_SHORT).show();
                                                            finish();
                                                        } else {
                                                            Toast.makeText(AddFruitActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                                                        }
                                                    });

                                        })
                                        .addOnFailureListener(e -> {
                                            dialog.dismiss();
                                            Toast.makeText(AddFruitActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                                            e.printStackTrace();
                                        })
                        )
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(AddFruitActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        });
            }
            else{
                // Get a reference to the Firestore database
                FirebaseFirestore firestore = FirebaseFirestore.getInstance();


                CollectionReference ticketsCollection = firestore.collection("AllProducts");

                String id = eachFruit.getFruit_id();

                if (id == null) {
                    dialog.dismiss();
                    Toast.makeText(AddFruitActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                    return;
                }

// Create an EachTicket object
                EachFruit fruit = new EachFruit(eachFruit.getImg_url(), name,type, des,price,rating,id);

                 // Set the value of the document in Firestore
                ticketsCollection.document(id)
                        .set(fruit)
                        .addOnCompleteListener(task -> {
                            dialog.dismiss();
                            if (task.isSuccessful()) {
                                Toast.makeText(AddFruitActivity.this, "Products added", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(AddFruitActivity.this, "Failed", Toast.LENGTH_SHORT).show();

                            }
                        });
            }
        }
    private void startPicker(){
        mGetContent = registerForActivityResult(
                new ActivityResultContracts.GetContent(), result -> {
                    if (result != null) {
                        photoUri = result;
                        iv_image.setImageURI(photoUri);
                    }
                }
        );
    }




    }
