package com.example.g_ecommerce.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.g_ecommerce.MainActivity;
import com.example.g_ecommerce.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;

public class HomeActivity extends AppCompatActivity {

    ProgressBar progressbar;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        auth = FirebaseAuth.getInstance();
        progressbar = findViewById(R.id.progressbar);
        progressbar.setVisibility(View.GONE);
        subscribeToToken();
    }

    private void subscribeToToken(){
        SharedPreferences sp = getSharedPreferences("sp",MODE_PRIVATE);

        if(sp.getBoolean("is_already_subscribed",false)){
            continueTo();
        }
        else{
            FirebaseMessaging.getInstance().subscribeToTopic("all").addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putBoolean("is_already_subscribed",true);
                        editor.apply();
                    }
                    continueTo();
                }
            });
        }
    }
    private void continueTo(){
        if (auth.getCurrentUser() != null) {
            progressbar.setVisibility(View.VISIBLE);
            startActivity(new Intent(HomeActivity.this, MainActivity.class));
            Toast.makeText(HomeActivity.this, "please wait you are already logged in", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    public void login(View view) {
        startActivity(new Intent(HomeActivity.this, LoginActivity.class));
        finish();
    }


    public void registration(View view) {
        startActivity(new Intent(HomeActivity.this, RegistrationActivity.class));

    }
}