package com.techtopus.greendrive;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.MessageFormat;
import java.util.Scanner;

public class driverdetails extends AppCompatActivity {
    TabItem t1, t2, t3;
    TabLayout tabLayout;
    TextView t45,success,t112;
    TextView succ;
    ViewPager viewPager;
    TextView t91,t113;
    MyAdapter2 adapter2;
    ImageView img;
    RequestClass rcq;
    FirebaseAuth mAuth;
    DataSnapshot ds;
    String s2,id;

    String fullname,phone, start, stop1, stop2, stop3, dest, time, date, vno, model, type,tid;
    ImageView gender;
    DatabaseReference databasereference2;
    String journey;
    Double lat;
    Double longt;
    String imglink;
    String custid;
    String email;
    rideclass rc;
    user uc;
    TextView live;
    Button b1,b2,b3,b4;
    String s3;
    ImageView i1,i2,i3,shield,user,driver;
    DatabaseReference reference;
    public static final String CHANNEL_ID = "TECHTOPUS";
    private static final String CHANNEL_NAME = "TECHTOPUS";
    private static final String CHANNEL_DESC = "TECHTOPUS";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driverdetaiils);
        mAuth = FirebaseAuth.getInstance();
        succ=findViewById(R.id.textView111);
        shield=findViewById(R.id.imageView52);
        live=findViewById(R.id.textView117);
        success=findViewById(R.id.textView76);
        tabLayout = findViewById(R.id.tabLayout);
        Toolbar toolbar=findViewById(R.id.toolbar13);
t113=findViewById(R.id.textView113);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        viewPager = findViewById(R.id.viewpager);
        b4=findViewById(R.id.button27);
        email=getIntent().getStringExtra("cemail");
        t45 = findViewById(R.id.textView45);
        i1=findViewById(R.id.imageView34);
        i2=findViewById(R.id.imageView36);
        i3=findViewById(R.id.imageView41);
        t2 = findViewById(R.id.trip);
        t3 = findViewById(R.id.car);
        img = findViewById(R.id.circleImageView4);
        b1=findViewById(R.id.button20);
        driver=findViewById(R.id.circleImageView7);
        user=findViewById(R.id.circleImageView8);
        b3=findViewById(R.id.button21);
        b2=findViewById(R.id.button51);
t112=findViewById(R.id.textView112);
        Bundle extras = getIntent().getExtras();

        if (extras != null)
            id = extras.getString("id");


        if (extras != null)
            custid = extras.getString("custid");
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        Query query2 = reference.child("Request").orderByChild("tripId").equalTo(id);
        query2.addListenerForSingleValueEvent(new ValueEventListener() {
                                                  @Override
                                                  public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                      if (dataSnapshot.exists()) {
                                                          for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                                              RequestClass t = dataSnapshot1.getValue(RequestClass.class);
                                                              if (t.getStatus().equals("nil"))
                                                              {  if(t.getRiderid().equals(mAuth.getUid())){
                                                                  b1.setVisibility(View.GONE);
                                                                  b2.setVisibility(View.VISIBLE);
                                                          }
                                                              }
                                                          }
                                                      }

                                                          }

                                                  @Override
                                                  public void onCancelled(@NonNull DatabaseError databaseError) {

                                                  }
                                              });
                DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference();

        Query query3 = reference2.child("trip").orderByChild("id").equalTo(id);
        query3.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            trip t = dataSnapshot1.getValue(trip.class);

                            s2 = t.getImglink();

                            Glide.with(driverdetails.this).load(s2).listener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    img.setImageResource(R.drawable.male);
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                    return false;
                                }
                            }).into(img);
                            tid = t.getId();
                            fullname = t.getName();
                            fullname = fullname.toUpperCase();
                            t45.setText(fullname);
                            start = (t.getStart());

                            stop1 = t.getStop1();
                            stop2 = (t.getStop2());
                            stop3 = (t.getStop3());
                            dest = (t.getDest());
                            time = (MessageFormat.format("{0} : {1}", t.getH(), t.getO()));
                            date = (t.getD() + " / " + t.getM() + " / " + t.getY());
                            vno = t.getVno();
                            model = t.getModel();
                            type = t.getType();
                            String stemp=start;
                            if(start.length()>10)
                            {
                                int u= start.indexOf(",");
                                stemp=start.substring(u+1);
                                int k=stemp.indexOf(",");
                                stemp=stemp.substring(0,k);

                                if(stemp.length()>20)
                                {
                                    stemp=stemp.substring(0,20);
                                    stemp= stemp.concat("..");
                                }
                            }
                            String stemp2=dest;
                            if(dest.length()>10)
                            {
                                int u=dest.indexOf(",");
                                stemp2=dest.substring(u+1);
                                int k=stemp2.indexOf(",");
                                stemp2=stemp2.substring(0,k);

                                if(stemp2.length()>20)
                                {
                                    stemp2=stemp2.substring(0,20);
                                    stemp2=  stemp2.concat("..");
                                }
                            }

                            adapter2 = new MyAdapter2(getSupportFragmentManager(), getApplicationContext(), 2
                                    , stemp, stop1, stop2, stop3, stemp2, time, date, vno, model, type);
                            viewPager.setAdapter(adapter2);


                            //s2 = t.getCustid();

                        }
                    } else {
                      //  Toast.makeText(driverdetails.this, "aarum illa", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(driverdetails.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        //  String s2 = "https://firebasestorage.googleapis.com/v0/b/greendrive-c916f.appspot.com/o/profilepics%2F" + mAuth.getCurrentUser().getUid() + ".jpg?alt=media&token=dcccb28a-25dc-4822-9952-9ec0740ac5b0";


        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }


            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this,ridepublish.class));
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser()==null)
        {
            startActivity(new Intent(this,Login.class));
        }
        View v = null;
        refresh(v);
    }

    Intent i,j;
public void dispfn(View view){
    j=new Intent(this,DisplayActivity.class);
         i=new Intent(this, TrackerActivity.class);
    startActivity(i);
    final DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    Query query = reference.child("trip").orderByChild("id").equalTo(id);
    query.addListenerForSingleValueEvent(new ValueEventListener() {
                                             @Override
                                             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                 for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                                     trip u=dataSnapshot1.getValue(trip.class);
                                                     j.putExtra("driverid", u.getName());

                                                     startActivity(j);

                                                 }

                                                 }

                                             @Override
                                             public void onCancelled(@NonNull DatabaseError databaseError) {

                                             }
                                         });

           }
    public void refresh(View v)
    {
      //   Toast.makeText(this, "refresh", Toast.LENGTH_SHORT).show();
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("Request").orderByChild("riderid").equalTo(mAuth.getUid());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    try {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            RequestClass r = dataSnapshot1.getValue(RequestClass.class);
                            if (r.getTripId().equals(id)){
                                 s2="https://firebasestorage.googleapis.com/v0/b/greendrive-c916f.appspot.com/o/profilepics%2F"+mAuth.getCurrentUser().getUid()+".jpg?alt=media&token=dcccb28a-25dc-4822-9952-9ec0740ac5b0";

                                Glide.with(driverdetails.this).load(s2).listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        img.setImageResource(R.drawable.male);
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        return false;
                                    }
                                }).into(user);


                                final DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                                Query query = reference.child("trip").orderByChild("id").equalTo(r.getTripId());
                                query.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                            trip t = dataSnapshot1.getValue(trip.class);
                                            if(t.getActive().equals("yes")) {
                                                s3 = t.getImglink();
                                                t112.setText("Your trip starts at : " + t.getH() + ":" + t.getO());
                                                success.setText("Your request has been \n   accepted by " + t.getName());
                                                Glide.with(driverdetails.this).load(s3).listener(new RequestListener<Drawable>() {
                                                    @Override
                                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                                        img.setImageResource(R.drawable.male);
                                                        return false;
                                                    }

                                                    @Override
                                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                                        return false;
                                                    }
                                                }).into(driver);

                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });


                                if(r.getStatus().equals("yes"))
                                   {

                                     b1.setVisibility(View.GONE);
                                    b3.setVisibility(View.GONE);
                                    i1.setVisibility(View.VISIBLE);
                                    b4.setVisibility(View.VISIBLE);
                                    i2.setVisibility(View.VISIBLE);
                                    i3.setVisibility(View.VISIBLE);
                                    viewPager.setVisibility(View.GONE);
                                    succ.setVisibility(View.VISIBLE);
                                    tabLayout.setVisibility(View.GONE);
                                    success.setVisibility(View.VISIBLE );
                                    shield.setVisibility(View.VISIBLE);
                                    img.setVisibility(View.GONE);
                                    live.setVisibility(View.VISIBLE);
                                    t45.setVisibility(View.GONE);
                                    tabLayout.setVisibility(View.GONE);
                                user.setVisibility(View.VISIBLE);
                                driver.setVisibility(View.VISIBLE);
                                t112.setVisibility(View.VISIBLE);
                                t113.setVisibility(View.GONE);
                                   }
                            }
                        }
                    }
                    catch(Exception e){
                        Toast.makeText(driverdetails.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                        }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

   /* @Override
    public void onBackPressed() {
        super.onBackPressed();

        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("Request").orderByChild("riderid").equalTo(mAuth.getUid());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    try {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            RequestClass r = dataSnapshot1.getValue(RequestClass.class);
                            if (r.getTripId().equals(id)) {
                                if (r.getStatus().equals("yes")) {

                                    Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                                    homeIntent.addCategory( Intent.CATEGORY_HOME );
                                    homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(homeIntent);


                                }
                            }
                        }
                    }catch (Exception e){
                        Toast.makeText(driverdetails.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                                }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

   */ public void shield(View v)
    {
        Intent i=new Intent(this,safety.class);
        i.putExtra("tid",id);
        i.putExtra("from","rider");
        startActivity(i);

    }

    public void cancel(View view)
    {
        finish();

        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("Request").orderByChild("riderid").equalTo(mAuth.getUid());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
                                                 @Override
                                                 public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                     for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                                         RequestClass r = dataSnapshot1.getValue(RequestClass.class);
                                                     if(r.getStatus().equals("yes"))
                                                     {
                                                         reference.child("Request").child(r.getRequestid()).child("status").setValue("cancelled");

                                                     }
                                                     }
                                                     }

                                                 @Override
                                                 public void onCancelled(@NonNull DatabaseError databaseError) {

                                                 }
                                             });


                Intent i = new Intent(this, ridepublish.class);
        startActivity(i);
    }
    public void phone(View view){
       //  Toast.makeText(this, "call", Toast.LENGTH_SHORT).show();
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("user").orderByChild("userid").equalTo(custid);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    user r = dataSnapshot1.getValue(user.class);
                    phone = r.getPhno();
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + phone));
                    startActivity(intent);
                }
                }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    public void sms(View view){
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("user").orderByChild("userid").equalTo(custid);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    user r = dataSnapshot1.getValue(user.class);
                    phone = r.getPhno();
                    Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                    sendIntent.setData(Uri.parse("sms:"+phone));
                    startActivity(sendIntent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
    public void nav(View view){
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("trip").orderByChild("custid").equalTo(custid);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    trip t=dataSnapshot1.getValue(trip.class);
                    if(t.getActive().equals("yes"))
                    {
                        lat=t.getSlatitude();
                        longt=t.getSlongitude();
                        start=t.getStart();
                        String geoUri = "http://maps.google.com/maps?q=loc:" + lat + "," + longt + " (" + start + ")";
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(geoUri));
                        startActivity(intent);

                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void request(View v) {
        /*if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel channel=new NotificationChannel (CHANNEL_ID,CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription(CHANNEL_DESC);
            NotificationManager manager=getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }*/

         databasereference2= FirebaseDatabase.getInstance().getReference("Request");
        id = databasereference2.push().getKey();
        Toast.makeText(this, "Request successfully placed", Toast.LENGTH_SHORT).show();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        Query query2 = reference.child("ride").orderByChild("custid").equalTo(mAuth.getCurrentUser().getUid());
        query2.addListenerForSingleValueEvent(new ValueEventListener() {
                                                  @Override
                                                  public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                      if (dataSnapshot.exists()) {
                                                          try {
                                                              for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                                                  rc = dataSnapshot1.getValue(rideclass.class);
                                                                  String stemp=rc.getStart();
                                                                  if(rc.getStart().length()>10)
                                                                  {
                                                                      int u=rc.getStart().indexOf(",");
                                                                      stemp=stemp.substring(u+1);
                                                                      int k=stemp.indexOf(",");
                                                                      stemp=stemp.substring(0,k);

                                                                      if(stemp.length()>20)
                                                                      {
                                                                          stemp=stemp.substring(0,20);
                                                                          stemp=  stemp.concat("..");
                                                                      }

                                                                  }
                                                                  String stemp2=rc.getDest();
                                                                  if(stemp2.length()>10)
                                                                  {
                                                                      int u=stemp2.indexOf(",");
                                                                      stemp2=stemp2.substring(u+1);
                                                                      int k=stemp2.indexOf(",");
                                                                      stemp2=stemp2.substring(0,k);

                                                                      if(stemp2.length()>20)
                                                                      {
                                                                          stemp2=stemp2.substring(0,20);
                                                                          stemp2= stemp2.concat("..");
                                                                      }

                                                                  }

                                                                journey="Start:\n  "+stemp+"\n"+"To:\n   "+stemp2;
                                                                  rcq = new RequestClass(id, mAuth.getCurrentUser().getDisplayName().toUpperCase()
                                                                          , journey,"", tid, mAuth.getCurrentUser().getUid(), "nil");

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

        Query query3 = reference2.child("user").orderByChild("userid").equalTo(mAuth.getCurrentUser().getUid());
        query3.addListenerForSingleValueEvent(new ValueEventListener() {
                                                  @Override
                                                  public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                      if (dataSnapshot.exists()) {
                                                          try {
                                                              for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                                                  uc = dataSnapshot1.getValue(user.class);

                                                                rcq.setImglink(uc.getImglink());
                                                                  databasereference2.child(id).setValue(rcq);
                                                                  //imglink=u.getImglink();

                                                              }
                                                          }
                                                          catch(Exception e){}
                                                      }
                                                  }

                                                  @Override
                                                  public void onCancelled(@NonNull DatabaseError databaseError) {

                                                  }
                                              });
             /*   RequestClass u = new RequestClass(id, mAuth.getCurrentUser().getDisplayName(), rc.getStart(),uc.getImglink()
                        , tid, mAuth.getCurrentUser().getUid(), "nil");
        databasereference2.child(id).setValue(u);*/
        b1.setVisibility(View.GONE);
        b2.setVisibility(View.VISIBLE);
        sendNotification();



    }

    public void sendNotification() {

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
int SDK_INT= Build.VERSION.SDK_INT;
if(SDK_INT>8)
{
    StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
    StrictMode.setThreadPolicy(policy);



                try {
                    String jsonResponse;


                    URL url = new URL("https://onesignal.com/api/v1/notifications");
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setUseCaches(false);
                    con.setDoOutput(true);
                    con.setDoInput(true);

                    con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                    con.setRequestProperty("Authorization", "Basic MWNiYWE4OWYtNTAyNC00ZjY3LTgwZWItMDQyYjRmMWRiNGQ1");
                    con.setRequestMethod("POST");
                    //     Toast.makeText(driverdetails.this, e.getMail(), Toast.LENGTH_SHORT).show();
                    String strJsonBody = "{"
                            + "\"app_id\": \"35626e01-c7b4-46c1-9209-f67f25466b67\","

                            + "\"filters\": [{\"field\": \"tag\", \"key\": \"User_ID\", \"relation\": \"=\", \"value\": \"" +email  + "\"}],"

                            + "\"data\": {\"foo\": \"bar\"},"
                            + "\"contents\": {\"en\": \"You have got NEW  RIDER REQUEST\"}"
                            + "}";


                    System.out.println("strJsonBody:\n" + strJsonBody);

                    byte[] sendBytes = strJsonBody.getBytes("UTF-8");
                    con.setFixedLengthStreamingMode(sendBytes.length);

                    OutputStream outputStream = con.getOutputStream();
                    outputStream.write(sendBytes);

                    int httpResponse = con.getResponseCode();
                    System.out.println("httpResponse: " + httpResponse);

                    if (httpResponse >= HttpURLConnection.HTTP_OK
                            && httpResponse < HttpURLConnection.HTTP_BAD_REQUEST) {
                        Scanner scanner = new Scanner(con.getInputStream(), "UTF-8");
                        jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                        scanner.close();
                    } else {
                        Scanner scanner = new Scanner(con.getErrorStream(), "UTF-8");
                        jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                        scanner.close();
                    }
                    System.out.println("jsonResponse:\n" + jsonResponse);

                } catch (Exception e) {
                    //Toast.makeText(driverdetails.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                }


}
            }
        });
    }

    public void change(View v) {
onBackPressed();
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