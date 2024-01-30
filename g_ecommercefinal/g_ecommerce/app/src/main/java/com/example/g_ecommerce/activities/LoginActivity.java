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

import com.example.g_ecommerce.MainActivity;
import com.example.g_ecommerce.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    Button signIn;
    EditText email,password;
    TextView signUp;
    ProgressBar progressbar;

    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        auth=FirebaseAuth.getInstance();
        signUp=findViewById(R.id.sign_up);
        email=findViewById(R.id.email_login);
        password=findViewById(R.id.password_login);
        signIn=findViewById(R.id.login_btn);
        progressbar=findViewById(R.id.progressbar);
        progressbar.setVisibility(View.GONE);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
            }
        });
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    loginUser();
                progressbar.setVisibility(View.VISIBLE);
            }
        });

    }

    private void loginUser() {
        String userEmail = email.getText().toString();
        String userPassword = password.getText().toString();


        if (TextUtils.isEmpty(userEmail)) {
            Toast.makeText(this, "Email is empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(userPassword)) {
            Toast.makeText(this, "Password is empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if (userEmail.equals("admin@gmail.com") && userPassword.equals("123456")) {
            Intent intent = new Intent(LoginActivity.this,AdminHomeActivity.class);
            startActivity(intent);

        }
        else if (userEmail.equals("delivery@gmail.com")&&userPassword.equals("654321"))
        {
            Intent intent = new Intent (LoginActivity.this,DeliveryBoyActivity.class);
            startActivity(intent);

        }




        else {
            if (userPassword.length() < 6) {
                Toast.makeText(this, "Password must be atleast 6 letters", Toast.LENGTH_SHORT).show();
                return;
            }
            //Login User
            auth.signInWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        progressbar.setVisibility(View.GONE);
                        Toast.makeText(LoginActivity.this, "Logged in successfully..", Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    } else {
                        progressbar.setVisibility(View.GONE);
                        Toast.makeText(LoginActivity.this, "Try Again", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}