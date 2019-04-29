package com.techtopus.greendrive;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class rating extends AppCompatActivity {
    RatingBar ratingBar;
    float rated=0;
    EditText et;
    TextView txt,t;
    String s;
    ImageView img;
    FirebaseAuth mAuth;
    Button b;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);
        ratingBar=findViewById(R.id.ratingBar);
        et=findViewById(R.id.editText4);
    txt=findViewById(R.id.textView89);
t=findViewById(R.id.textView101);
img=findViewById(R.id.imageView62);
mAuth=FirebaseAuth.getInstance();
b=findViewById(R.id.button34);
DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        Query query2 = reference.child("feedback").orderByChild("uid").equalTo(mAuth.getUid());
        query2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                    ratingBar.setVisibility(View.GONE);
                    txt.setVisibility(View.GONE);
                    et.setVisibility(View.GONE);
                    b.setVisibility(View.GONE);
                    t.setVisibility(View.VISIBLE);
                    img.setVisibility(View.VISIBLE);

                }
                }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void submit(View view)
    {
        rated=ratingBar.getRating();
        s=et.getText().toString();

        DatabaseReference databasereference2= FirebaseDatabase.getInstance().getReference("feedback");
        rate r=new rate(s,mAuth.getUid(),rated);
        databasereference2.child(mAuth.getCurrentUser().getDisplayName()).setValue(r);

        ratingBar.setVisibility(View.GONE);
        txt.setVisibility(View.GONE);
        et.setVisibility(View.GONE);
        b.setVisibility(View.GONE);
        t.setVisibility(View.VISIBLE);
        img.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, Login.class));

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this,DriveORRide.class));
    }
}
