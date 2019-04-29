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

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class activerequest extends AppCompatActivity {
    private List<AactiveList> listitems;
    RecyclerView recycle;
    TextView empty,makevisible,invisible;
    String tid;
    Intent i;
    AactiveList rc;
    FirebaseAuth mAuth;
    private RecyclerView.Adapter adapter;
public void makefn(View v)
{
    invisible.setVisibility(View.VISIBLE);
    makevisible.setVisibility(View.GONE);

    i=new Intent(this, TrackerActivity.class);
                    startActivity(i);
  }
public void undo(View view)
{
    invisible.setVisibility(View.GONE);
    makevisible.setVisibility(View.VISIBLE);
    stopService(new Intent(this,TrackerService.class));
   // finish();
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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activerequest);
empty=findViewById(R.id.textView104);
makevisible=findViewById(R.id.textView114);
invisible=findViewById(R.id.textView116);
mAuth=FirebaseAuth.getInstance();
        listitems = new ArrayList<>();
        Toolbar toolbar=findViewById(R.id.toolbar5);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        tid = getIntent().getStringExtra("tid");

        recycle = findViewById(R.id.recycleactive);

        recycle.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ActiveAdapter(listitems, getApplicationContext());
        recycle.setAdapter(adapter);

        DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference();
        Query query2= reference2.child("Request").orderByChild("tripId").equalTo(tid);
        query2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    try {
                        for (DataSnapshot ca : dataSnapshot.getChildren()) {

                            // do something with the individual "issues"
                            final RequestClass r = ca.getValue(RequestClass.class);
                            if(r.getStatus().equals("yes"))
                            {
                                DatabaseReference reference4 = FirebaseDatabase.getInstance().getReference();
                                Query query4= reference4.child("user").orderByChild("userid").equalTo(r.getRiderid());
                                query4.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                          @Override
                                                                          public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                          try{
                                                                              for (DataSnapshot ca : dataSnapshot.getChildren()) {
                                                                                  final user u;
                                                                                      u = ca.getValue(user.class);



                                                                                      DatabaseReference reference3 = FirebaseDatabase.getInstance().getReference();
                                                                                      Query query3= reference3.child("ride").orderByChild("custid").equalTo(u.getUserid());
                                                                                      query3.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                                          @Override
                                                                                          public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                                              for (DataSnapshot ca : dataSnapshot.getChildren()) {
                                                                                                  rideclass ride=ca.getValue(rideclass.class);
                                                                                                  if(ride.getActive().equals("yes")){
                                                                                                      String stemp=ride.getStart();

                                                                                                      if(ride.getStart().length()>10)
                                                                                                      {
                                                                                                          int u=ride.getStart().indexOf(",");
                                                                                                             stemp=stemp.substring(u+1);
                                                                                                              int k=stemp.indexOf(",");
                                                                                                              stemp=stemp.substring(0,k);
                                                                                                          if(stemp.length()>20)
                                                                                                          {
                                                                                                              stemp=stemp.substring(0,20);
                                                                                                              stemp= stemp.concat("..");
                                                                                                          }


                                                                                                      }
                                                                            rc = new AactiveList(u.getName(), stemp, (double) 89, u.getImglink(), u.getUserid());

                                                                                                      listitems.add(rc);
                                                                                                      adapter.notifyDataSetChanged();
                                                                                                  }}

                                                                                          }

                                                                                          @Override
                                                                                          public void onCancelled(@NonNull DatabaseError databaseError) {

                                                                                          }
                                                                                      });



                                                                                  }}catch(Exception e)
                                                                          {
                                                                              Toast.makeText(activerequest.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                                                          }

                                                                          }

                                                                          @Override
                                                                          public void onCancelled(@NonNull DatabaseError databaseError) {

                                                                          }
                                                                      });


                            }}
                    } catch (Exception e) {
                        Toast.makeText(activerequest.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    empty.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
    public void drivershield(View v){
        Intent i=new Intent(this,safety.class);
        i.putExtra("tid",tid);
        i.putExtra("from","driver");
        startActivity(i);
    }
    public void back(View v)
    {
        finish();
        Intent i=new Intent(this,PendingRequest.class);
        i.putExtra("tid",tid);
        startActivity(i);
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
