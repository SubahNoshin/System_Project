package com.example.g_ecommerce.ui.profile;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.g_ecommerce.R;
import com.example.g_ecommerce.models.UserModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.net.URI;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {

    ImageView profileImg;
    EditText name, number, address;
    Button update;
    ActivityResultLauncher<Intent> someActivityResultLauncher;
    FirebaseStorage storage;
    FirebaseAuth auth;
    FirebaseDatabase database;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
       View root = inflater.inflate(R.layout.fragment_profile, container, false);

       auth = FirebaseAuth.getInstance();
       database = FirebaseDatabase.getInstance();
       storage = FirebaseStorage.getInstance();


      profileImg = root.findViewById(R.id.profile_img);
      name = root.findViewById(R.id.profile_name);

      number = root.findViewById(R.id.profile_number2);
        address = root.findViewById(R.id.profile_address);
      update = root.findViewById(R.id.update);

      database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull DataSnapshot snapshot) {

              UserModel userModel = snapshot.getValue(UserModel.class);
              name.setText(userModel.getName());
              number.setText(userModel.getPhoneno());
              if(snapshot.hasChild("address")){
                  address.setText(userModel.getAddress());
              }
              Glide.with(getContext()).load(userModel.getProfileImg()).into(profileImg);

          }

          @Override
          public void onCancelled(@NonNull DatabaseError error) {

          }
      });
        someActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == Activity.RESULT_OK) {
                // Handle the result here
                if (result.getData() != null) {
                    // You can access the selected image URI using result.getData().getData()
                    Uri profileUri = result.getData().getData();
                    profileImg.setImageURI(profileUri);

                    final StorageReference reference = storage.getReference().child("profile_picture").child(FirebaseAuth.getInstance().getUid());
                    
                    reference.putFile(profileUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(getContext(), "Uploaded..", Toast.LENGTH_SHORT).show();

                            reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("profileImg").setValue(uri.toString());

                                    Toast.makeText(getContext(), "Profile Picture Uploaded", Toast.LENGTH_SHORT).show();

                                }
                            });


                        }
                    });
                }
            }
        });


        profileImg.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Intent intent = new Intent();
              intent.setAction(Intent.ACTION_GET_CONTENT);
              intent.setType("image/*");
              someActivityResultLauncher.launch(intent);
          }
      });

      update.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              updateUserProfile();
          }
      });


       return root;
    }

    private void updateUserProfile() {
        DatabaseReference userRef = database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid());

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserModel userModel = snapshot.getValue(UserModel.class);

                String uname = name.getText().toString();
                String uphone = number.getText().toString();
                String uaddress = address.getText().toString();

                if (userModel!=null){
                    userModel.setName(uname);
                    userModel.setPhoneno(uphone);
                    userModel.setAddress(uaddress);

                    userRef.setValue(userModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(getContext(), "Updated successfully..", Toast.LENGTH_SHORT).show();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


}