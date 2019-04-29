package com.techtopus.greendrive;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class PendingRequest extends AppCompatActivity {
    private List<listrequest> listitems;
    RequestClass r;
    private RecyclerView.Adapter adapter;
    RecyclerView recycle;
    String tid;
    TextView txt,empty;
    String start,dest,journey,imglink,req,rid,name;
    ImageView fullpic;
    TextView fulltxt;
    FirebaseAuth mAuth;
user u;
int i=0;
    listrequest rc;
    rideclass rcc;
View v;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pendingrequest);
        listitems = new ArrayList<>();
        txt=findViewById(R.id.textView77);
        recycle = findViewById(R.id.recycle);
        fullpic=findViewById(R.id.imageView35);
        fulltxt=findViewById(R.id.textView78);
        mAuth=FirebaseAuth.getInstance();
    // Toast.makeText(this, "entered ativity", Toast.LENGTH_SHORT).show();
        recycle.setLayoutManager(new LinearLayoutManager(this));
        tid = getIntent().getStringExtra("tid");
        listitems = new ArrayList<>();
        empty=findViewById(R.id.empty);
        recycle = findViewById(R.id.recycle);
        Toolbar toolbar=findViewById(R.id.toolbar2);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        recycle.setLayoutManager(new LinearLayoutManager(this));
        adapter = new pendingAdapter(listitems, getApplicationContext());
        recycle.setAdapter(adapter);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("trip").orderByChild("id").equalTo(tid);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    try {
                        for (DataSnapshot ca : dataSnapshot.getChildren()) {
                            trip t=ca.getValue(trip.class);
                            if(Integer.parseInt(t.getSeat())<1){
                                fullpic.setVisibility(View.VISIBLE);
                                fulltxt.setVisibility(View.VISIBLE);
                                recycle.setVisibility(View.GONE);
                            }
                            else{
                                refresh(v);
                            }
                        }
                    }
                    catch(Exception e){}
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });







    }
    public void back(View v)
    {
        startActivity(new Intent(this,trippublish.class));

    }
public void active(View v)
{
    Intent i=new Intent(this,activerequest.class);
    i.putExtra("tid",tid);
    startActivity(i);

}
    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, Login.class));

        }
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("trip").orderByChild("id").equalTo(tid);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    try {
                        for (DataSnapshot ca : dataSnapshot.getChildren()) {
                            trip t=ca.getValue(trip.class);
                            if(Integer.parseInt(t.getSeat())<1){
                                fullpic.setVisibility(View.VISIBLE);
                                fulltxt.setVisibility(View.VISIBLE);
                                recycle.setVisibility(View.GONE);
                            }
                            else{
                                         }
                        }
                    }
                    catch(Exception e){}
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference();
        Query query2 = reference2.child("trip").orderByChild("id").equalTo(tid);
        query2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    try {
                        for (DataSnapshot ca : dataSnapshot.getChildren()) {
                            trip t=ca.getValue(trip.class);
                            txt.setText(t.getSeat()+" seats left");
                        }
                    }
                    catch(Exception e){}
                }
                        }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void refresh(View v)
    {
       listitems.clear();
        adapter.notifyDataSetChanged();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("trip").orderByChild("id").equalTo(tid);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    try {
                        for (DataSnapshot ca : dataSnapshot.getChildren()) {
                            trip t=ca.getValue(trip.class);
                            if(Integer.parseInt(t.getSeat())<1){
                                fullpic.setVisibility(View.VISIBLE);
                                fulltxt.setVisibility(View.VISIBLE);
                                recycle.setVisibility(View.GONE);
                            }

                        }
                    }
                    catch(Exception e){}
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        tid = getIntent().getStringExtra("tid");

        DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference();
        Query query2= reference2.child("Request").orderByChild("tripId").equalTo(tid);
        query2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    try {
                        for (DataSnapshot ca : dataSnapshot.getChildren()) {

                            // do something with the individual "issues"
                            RequestClass r = ca.getValue(RequestClass.class);
                            if(r.getStatus().equals("nil"))
                            {
                                rc= new listrequest( r.getRequestid(), r.getName(), r.getJourney(), r.getImglink(), "no","gdf");
                                DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference();
                                Query query2= reference2.child("user").orderByChild("userid").equalTo(r.getRiderid());
                                query2.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                          @Override
                                                                          public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                                                              for (DataSnapshot ca : dataSnapshot.getChildren()) {
                                                                                    user u=ca.getValue(user.class);
                                                                                    rc.setEmail(u.getEmail());

                                                                                  listitems.add(rc);
                                                                                  adapter.notifyDataSetChanged();
                                                                              }
                                                                              }

                                                                          @Override
                                                                          public void onCancelled(@NonNull DatabaseError databaseError) {

                                                                          }
                                                                      });
;

                        }}
                        } catch (Exception e) {
                        Toast.makeText(PendingRequest.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    empty.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        DatabaseReference reference3 = FirebaseDatabase.getInstance().getReference();
        Query query3 = reference3.child("trip").orderByChild("id").equalTo(tid);
        query3.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    try {
                        for (DataSnapshot ca : dataSnapshot.getChildren()) {
                            trip t=ca.getValue(trip.class);
                            txt.setText(t.getSeat()+" seats left");
                        }
                    }
                    catch(Exception e){}
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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
