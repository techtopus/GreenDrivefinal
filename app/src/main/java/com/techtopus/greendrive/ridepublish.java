package com.techtopus.greendrive;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ebanx.swipebtn.OnStateChangeListener;
import com.ebanx.swipebtn.SwipeButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class ridepublish extends AppCompatActivity {
    FirebaseAuth mAuth;
    String y, m, dextra, h, o, start, dest;
    TextView t3;
    Button b1,b3,b4;
    SwipeButton swipeButton;
    View v;
ImageView img;
    Double slatitude, slongitude, dlatitude, dlongitude;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ridepublish);
        mAuth = FirebaseAuth.getInstance();
        t3 = findViewById(R.id.textView43);
        b1=findViewById(R.id.button17);
       img=findViewById(R.id.imageView59);
        Toolbar toolbar=findViewById(R.id.toolbar10);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
       b4=findViewById(R.id.button16);
       swipeButton=findViewById(R.id.swipe_btn1);
        timer();

        swipeButton.setOnStateChangeListener(new OnStateChangeListener() {
            @Override
            public void onStateChange(boolean active) {
                complete(v);
             //   Toast.makeText(ridepublish.this, "Active!"+active, Toast.LENGTH_SHORT).show();

            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, Login.class));

        }
        reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("Request").orderByChild("riderid").equalTo(mAuth.getCurrentUser().getUid());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    RequestClass rc = dataSnapshot1.getValue(RequestClass.class);
                    if(rc.getStatus().equals("yes")){
                        final Intent i=new Intent(ridepublish.this,driverdetails.class);
                        i.putExtra("id",rc.getTripId());
                        reference = FirebaseDatabase.getInstance().getReference();
                        Query query = reference.child("trip").orderByChild("id").equalTo(rc.getTripId());
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                    trip t = dataSnapshot1.getValue(trip.class);
                                i.putExtra("custid",t.getCustid());

                                    startActivity(i);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void complete(View v){
    reference = FirebaseDatabase.getInstance().getReference();
    Query query = reference.child("ride").orderByChild("custid").equalTo(mAuth.getUid());
    query.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                rideclass t = dataSnapshot1.getValue(rideclass.class);

                if (t.getActive().equals("yes"))
                {
                    reference.child("ride").child(t.getId()).child("active").setValue("completed");

                }
        }}
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    });
    DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference();
    Query query2 = reference2.child("Request").orderByChild("riderid").equalTo(mAuth.getUid());
    query2.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                RequestClass r=dataSnapshot1.getValue(RequestClass.class);
                if(r.getStatus().equals("yes")||r.getStatus().equals("completed")){
                    reference.child("Request").child(r.getRequestid()).child("status").setValue("completed");

                }
                else{
                    reference.child("Request").child(r.getRequestid()).child("status").setValue("cancelled");
                }
            }

        }


        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    });

    t3.setText("Ride is complete");

    b1.setVisibility(View.GONE);


    b4.setVisibility(View.GONE);
    img.setVisibility(View.VISIBLE);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("ride").orderByChild("custid").equalTo(mAuth.getUid());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    try {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            rideclass t = dataSnapshot1.getValue(rideclass.class);

                            if (t.getActive().equals("yes")) {
                                Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                                homeIntent.addCategory( Intent.CATEGORY_HOME );
                                homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(homeIntent);

                            }
                        }
                    }catch(Exception e){}}
                            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void timer() {
        if (mAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, Login.class));

        }

        reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("ride").orderByChild("custid").equalTo(mAuth.getUid());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    try {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            rideclass t = dataSnapshot1.getValue(rideclass.class);

                            if (t.getActive().equals("yes")) {
                                t3.setText("Your Ride is active!!");
                                start = t.getStart();
                                dest = t.getDest();
                                slatitude = t.getSlatitude();
                                slongitude = t.getSlongitude();
                                dlatitude = t.getDlatitude();
                                dlongitude = t.getDlongitude();
                              //  Toast.makeText(ridepublish.this, slatitude.toString(), Toast.LENGTH_SHORT).show();
                                /*Toast.makeText(ridepublish.this, y + "\n" + m + "\n" + dextra, Toast.LENGTH_SHORT).show();


                                Calendar calendar = Calendar.getInstance();

                                calendar.set(Integer.valueOf(y), Integer.valueOf(m) - 1, Integer.valueOf(dextra),
                                        Integer.valueOf(h), Integer.valueOf(o), 0);


                                Date d = calendar.getTime();
                                Toast.makeText(ridepublish.this, d.toString(), Toast.LENGTH_SHORT).show();
*/
                              //  printDifference(d);


                            } }
                    } catch (Exception e) {
                        Toast.makeText(ridepublish.this, "ERROR : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void search(View view) {
        Intent i = new Intent(this, driverslist.class);
        i.putExtra("start", start);
        i.putExtra("dest", dest);
        i.putExtra("slatitude", slatitude);
        i.putExtra("slongitude", slongitude);

        i.putExtra("dlatitude", dlatitude);
        i.putExtra("dlongitude", dlongitude);

        startActivity(i);
    }
/*


    private void startTimer(int noOfMinutes) {
        CountDownTimer countDownTimer = new CountDownTimer(noOfMinutes*60, 1000) {
            public void onTick(long millisUntilFinished) {
                long millis = millisUntilFinished;
                //Convert milliseconds into hour,minute and seconds
                String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis), TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)), TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
                t.setText(hms);//set text
            }
            public void onFinish() {
                t.setText("TIME'S UP!!"); //On finish change timer text
            }
        }.start();

    }*/

    public void home(View v){
        startActivity(new Intent(this,DriveORRide.class));
    }

    public void cancel(View view) {
        //  pg.setVisibility(View.VISIBLE);
        reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("ride").orderByChild("custid").equalTo(mAuth.getCurrentUser().getUid());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    rideclass t = dataSnapshot1.getValue(rideclass.class);
                    if(t.getActive().equals("yes"))
                    reference.child("ride").child(t.getId()).child("active").setValue("cancelled");
                    t3.setText("Ride is CANCELED");

                    b1.setVisibility(View.GONE);

swipeButton.setVisibility(View.GONE);
                    b4.setVisibility(View.GONE);
                    img.setVisibility(View.VISIBLE);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference();
        Query query2 = reference2.child("Request").orderByChild("riderid").equalTo(mAuth.getUid());
        query2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    RequestClass r=dataSnapshot1.getValue(RequestClass.class);
                    if(r.getStatus().equals("yes")||r.getStatus().equals("nil")){
                        reference.child("Request").child(r.getRequestid()).child("status").setValue("cancelled");

                    }
                    else{
                        reference.child("Request").child(r.getRequestid()).child("status").setValue("cancelled");
                    }
                    t3.setText("Ride is CANCELLED");

                    b1.setVisibility(View.GONE);

                    swipeButton.setVisibility(View.GONE);

                    b4.setVisibility(View.GONE);
                    img.setVisibility(View.VISIBLE);

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