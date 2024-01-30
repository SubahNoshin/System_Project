package com.example.g_ecommerce.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.g_ecommerce.R;
import com.example.g_ecommerce.models.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity {
    Button signUp, getotp;
    EditText name,email,password, phoneno, otp;
    TextView signIn;
    ProgressBar progressbar;

    FirebaseAuth auth;
    FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        FirebaseApp.initializeApp(this);
        auth = FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        signIn=findViewById(R.id.sign_in);
        signUp=findViewById(R.id.reg_btn);
        name=findViewById(R.id.name);
        email=findViewById(R.id.email_reg);
        phoneno = findViewById(R.id.phone_num);
        password=findViewById(R.id.password_reg);
        //getotp = findViewById(R.id.getotp);
        //otp = findViewById(R.id.enterotp);
        progressbar=findViewById(R.id.progressbar);
        progressbar.setVisibility(View.GONE);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                createUser();
                progressbar.setVisibility(View.VISIBLE);
                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
            }
        });
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistrationActivity.this,LoginActivity.class));

            }
        });
    }

    private void createUser() {
        String userName=name.getText().toString();
        String userEmail=email.getText().toString();
        String userPassword=password.getText().toString();
        String userPhone = phoneno.getText().toString();

        if(TextUtils.isEmpty(userName)){
            Toast.makeText(this, "Name is empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(userEmail)){
            Toast.makeText(this, "Email is empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(userPassword)){
            Toast.makeText(this, "Password is empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(userPhone)){
            Toast.makeText(this, "Phone No is empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if((phoneno.getText().toString().trim()).length() != 11){
            Toast.makeText(this, "Phone No should be 11 digits", Toast.LENGTH_SHORT).show();
            return;
        }

        if(userPassword.length()<6){
            Toast.makeText(this, "Password must be atleast 6 letters", Toast.LENGTH_SHORT).show();
            return;
        }

        //create user
        auth.createUserWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    UserModel userModel=new UserModel(userName, userEmail, userPassword, userPhone );
                    String id=task.getResult().getUser().getUid();
                    database.getReference().child("Users").child(id).setValue(userModel);
                    progressbar.setVisibility(View.GONE);
                    Toast.makeText(RegistrationActivity.this, "Registration is successful", Toast.LENGTH_SHORT).show();
                }
                else{
                    progressbar.setVisibility(View.GONE);
                    Toast.makeText(RegistrationActivity.this, "Invalid Information", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }
}