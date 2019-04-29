package com.techtopus.greendrive;

import android.accessibilityservice.FingerprintGestureController;
import android.app.AlarmManager;
import android.app.DownloadManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.os.CountDownTimer;
import android.os.Handler;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ebanx.swipebtn.OnStateChangeListener;
import com.ebanx.swipebtn.SwipeButton;
import com.google.android.gms.common.providers.PooledExecutorsProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class trippublish extends AppCompatActivity {
FirebaseAuth mAuth;
DatabaseReference reference;
TextView t38,t1,t2,t3,t4,t5;
    String y,m,dextra,h,o;
    private Handler handler = new Handler();
    private Runnable runnable;
    ProgressBar pg;
    SwipeButton swipeButtonNoState;
    String start,dest,tid;
    Button b1,b2,b3,b4;
    ImageView img;
    View v;
    Double slatitude,slongitude,dlatitude,dlongitude,
            s1latitude,s1longitude,s2latitude,s2longitude,s3latitude,s3longitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trippublish);
        mAuth=FirebaseAuth.getInstance();
        b1=findViewById(R.id.button18);
        img=findViewById(R.id.imageView58);

        b3=findViewById(R.id.button12);


        Toolbar toolbar=findViewById(R.id.toolbar);
t38=findViewById(R.id.textView38);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
         t5 = findViewById(R.id.textView43);
       pg=findViewById(R.id.progressBar3);

         swipeButtonNoState = findViewById(R.id.swipe_btn1);
       timer();
        swipeButtonNoState.setOnStateChangeListener(new OnStateChangeListener() {
            @Override
            public void onStateChange(boolean active) {
                complete(v);


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
    }

    public void complete(View v)
{
    reference = FirebaseDatabase.getInstance().getReference();
    Query query = reference.child("trip").orderByChild("custid").equalTo(mAuth.getCurrentUser().getUid());
    query.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
            trip t=dataSnapshot1.getValue(trip.class);
            if(t.getActive().equals("yes")){
                reference.child("trip").child(t.getId()).child("active").setValue("completed");
            }
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            }


});
    DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference();
    Query query2 = reference2.child("Request").orderByChild("tripId").equalTo(tid);
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

    t5.setText("Trip is completed!!");
    b1.setVisibility(View.GONE);

    b3.setVisibility(View.GONE);

    img.setVisibility(View.VISIBLE);
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

    public  void timer() {
        Calendar cal = Calendar.getInstance();
        final Date date = cal.getTime();
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        String formattedDate = dateFormat.format(date);
        if (mAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, Login.class));

        }

        reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("trip").orderByChild("custid").equalTo(mAuth.getCurrentUser().getUid());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    try {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            trip t = dataSnapshot1.getValue(trip.class);

                            if (t.getActive().equals("yes")) {
                                y = t.getY();
                                m = t.getM();
                                tid=t.getId();
                                dextra = t.getD();
                                h = t.getH();
                                o = t.getO();
                                if(Integer.valueOf(t.getH())<12) {
                                    t38.setText(t.getH() + " : " + t.getO()+" AM");
                                }
                                else
                                {
                                    int r = Integer.valueOf(t.getH()) - 12;
                                    t38.setText(r+" : "+t.getO()+" PM");
                                }


                                Calendar calendar = Calendar.getInstance();

                                calendar.set(Integer.valueOf(y), Integer.valueOf(m) - 1, Integer.valueOf(dextra),
                                        Integer.valueOf(h), Integer.valueOf(o), 0);


                                Date d = calendar.getTime();

                                long k = printDifference(d);
                                alarmfn(k);
                                CountDownTimer timer1 = new CountDownTimer(k, 1000) {
                                    public void onTick(long millisUntilFinished) {
                                    }

                                    public void onFinish() {
                                        t38.setText("Timed out!");
                                        t5.setText("Your trip is inactive");
                                        b1.setVisibility(View.GONE);
                                        b3.setVisibility(View.GONE);
                                        swipeButtonNoState.setVisibility(View.GONE);
                                        img.setVisibility(View.VISIBLE);
                                    }
                                };
                                timer1.start();
                             }
                        }
                    } catch (Exception e) {
                        Toast.makeText(trippublish.this, "ERROR : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
    public void home(View v){
        startActivity(new Intent(this,DriveORRide.class));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("trip").orderByChild("custid").equalTo(mAuth.getCurrentUser().getUid());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    try {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            trip t = dataSnapshot1.getValue(trip.class);

                            if (t.getActive().equals("yes")) {
                                Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                                homeIntent.addCategory( Intent.CATEGORY_HOME );
                                homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(homeIntent);
                            }
                        }
                    }catch(Exception e){}
                }
                            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void alarmfn(long diff) {

        Intent intent=new Intent(this,MyBroadcastReciever.class);
        PendingIntent pendingIntent=PendingIntent.getBroadcast(
                this.getApplicationContext(),0,intent,0
        );
        AlarmManager alarmManager=(AlarmManager)getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()+diff,pendingIntent);
    }



    public void pending(View v)
    {
        Intent i=new Intent(this,PendingRequest.class);
        i.putExtra("tid",tid);
        startActivity(i);
    }

    public void cancel(View view)
    {
        reference= FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("trip").orderByChild("custid").equalTo(mAuth.getCurrentUser().getUid());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    trip t = dataSnapshot1.getValue(trip.class);
                    if(t.getActive().equals("yes")) {
                        reference.child("trip").child(t.getId()).child("active").setValue("cancelled");
                        t38.setText("CANCELLED!");
                        t5.setText("Your trip is cancelled");
                        b1.setVisibility(View.GONE);
                        b3.setVisibility(View.GONE);
                        swipeButtonNoState.setVisibility(View.GONE);
                        img.setVisibility(View.VISIBLE);

                    }}        }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        reference= FirebaseDatabase.getInstance().getReference();
        Query query2 = reference.child("Request").orderByChild("tripId").equalTo(tid);
        query2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    RequestClass r = dataSnapshot1.getValue(RequestClass.class);
                    if(r.getStatus().equals("nil")||r.getStatus().equals("yes"))
                    reference.child("Request").child(r.getRequestid()).child("status").setValue("cancelled");
                    }


            }

                                                 @Override
                                                 public void onCancelled(@NonNull DatabaseError databaseError)  {

                                                 }
                                             });





    }

    public long printDifference(Date startDate) {
        //milliseconds
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        dateFormat.format(startDate);
        Date endDate=Calendar.getInstance().getTime();
        dateFormat.format(endDate);
        long different = startDate.getTime() - endDate.getTime();

        long diffcopy = different;
        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;



        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;
        long elapsedSeconds = different / 1000;

        return diffcopy;
    }

}
