package com.techtopus.greendrive;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class profile extends AppCompatActivity {
String uid,tok,s2,pass;
EditText t1,t2,t3,t4,t5;
ImageView img;
ProgressBar p;
FirebaseAuth mAuth;
    private static final int CHOOSE_IMAAGE =101 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        uid=getIntent().getStringExtra("uid");
        t1=findViewById(R.id.editText3);
        t2=findViewById(R.id.editText7);
        p=findViewById(R.id.progressBar4);
        t3=findViewById(R.id.editText8);
        Toolbar toolbar=findViewById(R.id.toolbar16);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        t4=findViewById(R.id.editText9);
        t5=findViewById(R.id.editText10);
        img=findViewById(R.id.circleImageView5);
        p.setVisibility(View.VISIBLE);
mAuth=FirebaseAuth.getInstance();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        Query query2 = reference.child("user").orderByChild("userid").equalTo(uid);
        query2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            user u = dataSnapshot1.getValue(user.class);
                            t1.setText(u.getName());
                            t2.setText(u.getEmail());
                            t3.setText(u.getGender());
                            t4.setText(u.getAadharno());
                            t5.setText(u.getPhno());
                            tok=u.getToken();
                            s2=u.getImglink();
pass=u.getEmail();
                        }
                    }
                }catch(Exception e )
                {

                }
                Glide.with(profile.this).load(s2).listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                       p.setVisibility(View.GONE);
                        return false;
                    }
                }).into(img);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    public void save(View v)
    {DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("user").orderByChild("userid").equalTo(mAuth.getCurrentUser().getUid());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    dataSnapshot1.getRef().removeValue();
                    user u = new user(t1.getText().toString(), uid, t2.getText().toString(), t3.getText().toString(), t5.getText().toString(),
                            t4.getText().toString(), tok, s2);
                    DatabaseReference databasereference2= FirebaseDatabase.getInstance().getReference("user");
                    String id = databasereference2.push().getKey();
                    databasereference2.child(id).setValue(u);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Toast.makeText(this, "Your profile updated successfully!!!", Toast.LENGTH_SHORT).show();

    }
    public void delete(View view)
    {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("user").orderByChild("userid").equalTo(uid);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    dataSnapshot1.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        // Get auth credentials from the user for re-authentication. The example below shows
        // email and password credentials but there are multiple possible providers,
        // such as GoogleAuthProvider or FacebookAuthProvider.
        AuthCredential credential = EmailAuthProvider
                .getCredential("user@example.com", "password1234");

        // Prompt the user to re-provide their sign-in credentials
        user.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                           @Override
                                           public void onComplete(@NonNull Task<Void> task) {
                                               user.delete()
                                                       .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                           @Override
                                                           public void onComplete(@NonNull Task<Void> task) {
                                                               if (task.isSuccessful()) {
                                                                   Toast.makeText(profile.this, "Account deleted successfully!", Toast.LENGTH_SHORT).show();
                                                               }
                                                           }
                                                       });
                                           }          });
                        FirebaseAuth.getInstance().signOut();

        startActivity(new Intent(this,Login.class));
    }
    public void selectpic(View v)
    {
        Intent i=new Intent();
        i.setType("image/*");
        i.setAction(i.ACTION_GET_CONTENT);
        startActivityForResult(i.createChooser(i,"Select your profile picture"),CHOOSE_IMAAGE);


    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this,DriveORRide.class));
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflator=getMenuInflater();
        inflator.inflate(R.menu.menu ,menu);




        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.menuLogout:
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(this, Login.class));
                break;
            case R.id.triphist:
                finish();
                startActivity(new Intent(this,triphistory.class));
                break;
            case R.id.Profile:
                Intent i= new Intent(this,profile.class);
                i.putExtra("uid",mAuth.getCurrentUser().getUid());
                startActivity(i);
                break;
            case R.id.safety:
                Intent i4=new Intent(this,safety.class);
                i4.putExtra("from","driver");
                startActivity(i4);
                break;
            case R.id.randr:
                Intent i2=new Intent(this,rating.class);
                startActivity(i2);
                break;
        }
        return true;
    }


}


