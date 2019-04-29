package com.techtopus.greendrive;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

public class frontscreen extends AppCompatActivity {
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frontscreen);
        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
    }
    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser()!=null)
        {
            finish();
            startActivity(new Intent(this,welcome.class));

        }
    }

    public void login(View v)
    {
        Intent i = new Intent(this, Login.class);
        startActivity(i);
    }
    public void register(View v)
    {
        Intent i = new Intent(this,register.class);
        startActivity(i);
    }
}
