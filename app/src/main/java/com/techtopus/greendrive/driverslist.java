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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;

public class driverslist extends AppCompatActivity {
    DatabaseReference reference;
    FirebaseAuth mAuth;
    String start,dest;
    TextView empty;
    int y;
    Double slatitude,slongitude,dlatitude,dlongitude;
    public RecyclerView.Adapter adapter2;
    public List<Listitem2> listitems2;
    Double s2s,s12s,s22s,s32s,small;
    Double s12d,s22d,s32d,d2d;
    int counter;
     RecyclerView recyclerfinal;
    RecyclerView recycler2;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driverslist);
        recycler2= findViewById(R.id.recyclerview2);
        Toolbar toolbar=findViewById(R.id.toolbar12);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
empty=findViewById(R.id.empty2);
        recycler2.setLayoutManager(new LinearLayoutManager(this));
        start=getIntent().getStringExtra("start");
        dest=getIntent().getStringExtra("dest");
        Bundle extras=getIntent().getExtras();
        if (extras != null) {
            slatitude=extras.getDouble("slatitude");
        }
        if (extras != null) {
            slongitude=extras.getDouble("slongitude");
        }
        if (extras != null) {
            dlatitude=extras.getDouble("dlatitude");
        }
        if (extras != null) {
            dlongitude=extras.getDouble("dlongitude");
        }
        mAuth=FirebaseAuth.getInstance();
        listitems2=new ArrayList<>();
        //Toast.makeText(this, String.valueOf(slatitude), Toast.LENGTH_SHORT).show();
        recycler2=(RecyclerView)findViewById(R.id.recyclerview2);

        recycler2.setLayoutManager(new LinearLayoutManager(this));


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        Query query2 = reference.child("trip").orderByChild("active").equalTo("yes");
        query2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    try {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            trip t = dataSnapshot1.getValue(trip.class);
                           // Toast.makeText(driverslist.this, slongitude.toString(), Toast.LENGTH_SHORT).show();
                            s2s = distance(t.getSlatitude(), t.getSlongitude(), slatitude, slongitude);
                            s12s = distance(t.getS1latitude(), t.getS1longitude(), slatitude, slongitude);
                            s22s = distance(t.getS2latitude(), t.getS2longitude(), slatitude, slongitude);
                            s32s = distance(t.getS3latitude(), t.getS3longitude(), slatitude, slongitude);
                            small = s2s;
                            counter = 1;
                            if (s2s < 5 || s12s < 5 || s22s < 5 || s32s < 5) {
                                if (s12s < small) {
                                    small = s12s;
                                    counter = 2;
                                }
                                if (s22s < small) {
                                    small = s22s;
                                    counter = 3;

                                }
                                if (s32s < small) {
                                    small = s32s;
                                    counter = 4;
                                }

                                s12d=distance(t.getS1latitude(),t.getS1longitude(),dlatitude,dlongitude);
                                s22d=distance(t.getS2latitude(),t.getS2longitude(),dlatitude,dlongitude);
                                s32d=distance(t.getS3latitude(),t.getS3longitude(),dlatitude,dlongitude);
                                d2d=distance(t.getDlatitude(),t.getDlongitude(),dlatitude,dlongitude);

//                                Toast.makeText(driverslist.this,"Distance"+s12d.toString() , Toast.LENGTH_SHORT).show();
                                        if (s12d<5||s22d<5||s32d<5||d2d<5){
                                          /*  if(counter==1)
                                            {
                                                Toast.makeText(driverslist.this,slatitude+"\n"+slongitude, Toast.LENGTH_SHORT).show();
                                              small=distance2(t.getSlatitude(), t.getSlongitude(), slatitude, slongitude,1);
                                            }
                                            if(counter==2)
                                            {
                                                small= distance2(t.getS1latitude(), t.getS1longitude(), slatitude, slongitude,2);
                                            }
                                            if(counter==3)
                                            {
                                                small= distance2(t.getS1latitude(), t.getS1longitude(), slatitude, slongitude,3);
                                            }
                                            if(counter==4)
                                            {
                                                small= distance2(t.getS1latitude(), t.getS1longitude(), slatitude, slongitude,4);
                                            }*/
                                y=Integer.parseInt(t.getSeat());
                                /*if (t.getType().equals("bike"))
                                    y = 1;
                                else if (t.getType().equals("lmv"))
                                    y = 2;
                                else if (t.getType().equals("suv"))
                                    y = 3;*/

                                            String stemp=t.getStart();
                                            if(stemp.length()>10)
                                            {
                                                int u= t.getStart().indexOf(",");
                                                stemp=stemp.substring(u+1);
                                                int k=stemp.indexOf(",");
                                                stemp=stemp.substring(0,k);

                                                if(stemp.length()>20)
                                                {
                                                    stemp=stemp.substring(0,20);
                                                    stemp.concat("..");
                                                }

                                            }
                                            String stemp2=t.getDest();
                                            if(stemp2.length()>10)
                                            {
                                                int u=stemp2.indexOf(",");
                                                stemp2=stemp2.substring(u+1);
                                                int k=stemp2.indexOf(",");
                                                stemp2=stemp2.substring(0,k);

                                                if(stemp2.length()>20)
                                                {
                                                    stemp2=stemp2.substring(0,20);
                                                    stemp2.concat("..");
                                                }
                                            }
                                            String smallstr=String.valueOf(small);
                                           int u =smallstr.indexOf(".");
                                           smallstr=smallstr.substring(0,u+2);
                                           small=Double.parseDouble(smallstr);

                                           Listitem2 listitem = new Listitem2(t.getName(),y,"Start : "+stemp+"\n   To : "+stemp2,
                                        t.getH()+":"+t.getO(),t.getD()+"/"+t.getM()+"/"+t.getY(),t.getImglink(),
                                                   small,t.getId(),t.getCustid());
                                listitems2.add(listitem);
                                empty.setVisibility(View.GONE);
                            }
                            else
                            {
  //                              Toast.makeText(driverslist.this, "No driver found", Toast.LENGTH_SHORT).show();

                            }



                            }
                            else
                            {// Toast.makeText(driverslist.this, "No drivers nearby!!", Toast.LENGTH_SHORT).show();
                            }
                        } }catch(Exception e){
                            Toast.makeText(driverslist.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                        //  Toast.makeText(driverslist.this, "No data found", Toast.LENGTH_SHORT).show();
                        adapter2 = new adapter2(listitems2, getApplicationContext());
                        recycler2.setAdapter(adapter2);
                    }
                    else{
                    empty.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        }
public void edit(View view){
        startActivity(new Intent(this,ridepublish.class));
}

    public void recyc(View view)
    {
       // Toast.makeText(this, "hiii", Toast.LENGTH_SHORT).show();
        int itemPosition = recycler2.getChildLayoutPosition(view);
        Listitem2 l2 = listitems2.get(itemPosition);
        final Intent i=new Intent(this,driverdetails.class);
        if(l2.getId().equals("ooo"))
        {startActivity(new Intent(this,Drive.class));}
        i.putExtra("id",l2.getId());
        i.putExtra("custid",l2.getCustid());

        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("user").orderByChild("userid").equalTo(l2.getCustid());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
                                                 @Override
                                                 public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                     try{ for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                                      user   uc = dataSnapshot1.getValue(user.class);
                                                         i.putExtra("cemail",uc.getEmail());
                                                         //      Toast.makeText(driverdetails.this, send_email, Toast.LENGTH_SHORT).show();
                                                         startActivity(i);

                                                     }}catch(Exception e) {
                                                         //                                           Toast.makeText(driverdetails.this, e.getMessage(), Toast.LENGTH_SHORT).show();}
                                                     }
                                                 }

                                                 @Override
                                                 public void onCancelled(@NonNull DatabaseError databaseError) {

                                                 }
                                             });

    }
    double dist;
    double distance2(double lat1, double lon1, double lat2, double lon2,int counter) {

        AsyncHttpClient client = new AsyncHttpClient();

        client.get("https://maps.googleapis.com/maps/api/directions/json?origin="+lat1+","+lon1+"&destination="
                        +lat2+","+lon2+"&key=%20AIzaSyDsjO_TEx_bn7G47bbdTfaFdFsngeDutEI"
                ,new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        try {
             dist=fromJson(response);
                        } catch (JSONException e) {
                            Toast.makeText(driverslist.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                    }
                });
        return dist;
    } JSONObject route;
    public double fromJson(JSONObject jsonObject) throws JSONException {
    // routesArray contains ALL routes
        JSONArray routesArray = jsonObject.getJSONArray("routes");
// Grab the first route
        if (routesArray.length()>0) {
             route = routesArray.getJSONObject(0);
        }
        else
            Toast.makeText(this, "not available", Toast.LENGTH_SHORT).show();
// Take all legs from the route
        JSONArray legs = route.getJSONArray("legs");
// Grab first leg
        JSONObject leg = legs.getJSONObject(0);

        JSONObject durationObject = leg.getJSONObject("duration");
        String duration = durationObject.getString("text");
        Toast.makeText(this, "Duration : - "+duration, Toast.LENGTH_SHORT).show();


        JSONObject distanceObject = leg.getJSONObject("distance");
        String distance = distanceObject.getString("text");
        Toast.makeText(this, "Duration : - "+distance, Toast.LENGTH_SHORT).show();
        int k=distance.indexOf(".");

        distance=distance.substring(0,k+3);
        double dista= Double.parseDouble(distance);
       // double dista= 333;
        dista=dista*1.609;
    return dista;

    }


        double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }




 /*   public double distance(double lat1, double lng1, double lat2, double lng2) {

        double earthRadius = 6371; // in miles, change to 6371 for kilometer output

        double dLat = Math.toRadians(lat2-lat1);
        double dLng = Math.toRadians(lng2-lng1);

        double sindLat = Math.sin(dLat / 2);
        double sindLng = Math.sin(dLng / 2);

        double a = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
                * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2));

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        double dist = earthRadius * c;

        return dist; // output distance, in MILES
    }
*/

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

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, Login.class));

        }

    }
}