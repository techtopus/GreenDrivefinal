package com.techtopus.greendrive;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.google.firebase.auth.FirebaseAuth;
import com.onesignal.OneSignal;

import java.util.Timer;
import java.util.TimerTask;

public class welcome extends AppCompatActivity {
    ImageView i;
    FirebaseAuth mAuth;
    ProgressBar progressbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        OneSignal.startInit(this);
        mAuth = FirebaseAuth.getInstance();
        progressbar=(ProgressBar)findViewById(R.id.progressBar);
        OneSignal.sendTag("User_ID",mAuth.getCurrentUser().getEmail());
        i =  findViewById(R.id.imageView);
        TextView t=findViewById(R.id.textView2);
        RequestOptions requestOptions= RequestOptions.placeholderOf(R.drawable.female);

        String s2="https://firebasestorage.googleapis.com/v0/b/greendrive-c916f.appspot.com/o/profilepics%2F"+mAuth.getCurrentUser().getUid()+".jpg?alt=media&token=dcccb28a-25dc-4822-9952-9ec0740ac5b0";
        Glide.with(this).load(s2).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                i.setImageResource(R.drawable.male);
                new Timer().schedule(new TimerTask(){
                    public void run() {
                        finish();
                        startActivity(new Intent(welcome.this, DriveORRide.class));

                    }
                }, 3000);


                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
              progressbar.setVisibility(View.GONE);
                new Timer().schedule(new TimerTask(){
                    public void run() {
                        finish();
                        startActivity(new Intent(welcome.this, DriveORRide.class));

                    }
                }, 3000);

                return false;
            }
        }).into(i);
        t.setText("WELCOME "+mAuth.getCurrentUser().getDisplayName().toUpperCase());





    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser().getDisplayName()=="")
        {Intent i= new Intent(this,personalinfo.class);
            startActivity(i);
        }

    }


}
