package com.techtopus.greendrive;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class triphistory extends AppCompatActivity {
    private List<HistoryList> listitems;
    RecyclerView recycle;
    TextView empty;
    String tid,stemp,stemp2;
    HistoryList rc;
    private RecyclerView.Adapter adapter;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_triphistory);
        recycle=findViewById(R.id.recycle3);
mAuth=FirebaseAuth.getInstance();
empty=findViewById(R.id.textView105);
        listitems = new ArrayList<>();

        recycle.setLayoutManager(new LinearLayoutManager(this));
        adapter = new historyAdapter(listitems, getApplicationContext());
        recycle.setAdapter(adapter);

        DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference();
        Query query2= reference2.child("trip").orderByChild("custid").equalTo(mAuth.getUid());
        query2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                for (DataSnapshot ca : dataSnapshot.getChildren()) {
empty.setVisibility(View.GONE);
                    // do something with the individual "issues"
                    trip t = ca.getValue(trip.class);

                    String start = t.getStart();
                    stemp=start;
                    if(start.length()>10)
                    {

                        int u=start.indexOf(",");
                        stemp=start.substring(u+1);
                        int k=stemp.indexOf(",");
                        stemp=stemp.substring(0,k);
                        if(stemp.length()>20)
                        {
                            stemp=stemp.substring(0,20);
                            stemp= stemp.concat("..");
                        }
                    }
                    String dest = t.getDest();
                    stemp2=dest;
                    if(dest .length()>10)
                    {

                        int u=dest.indexOf(",");
                        stemp2=dest.substring(u+1);
                        int k=stemp2.indexOf(",");
                        stemp2=stemp2.substring(0,k);
                        if(stemp2.length()>20)
                        {
                            stemp2=stemp2.substring(0,20);
                            stemp2= stemp2.concat("..");
                        }
                    }
                    String status=t.getActive();
                    if(t.getActive().equals("no"))
                    {
                        status="Timed Out";
                    }
                    else if(t.getActive().equals("yes"))
                    {
                        status="ACTIVE NOW";
                    }
                    HistoryList h= new HistoryList("drive",stemp,stemp2,status);
                    listitems.add(h);
                    adapter.notifyDataSetChanged();

                }}
                else{
                    empty.setVisibility(View.VISIBLE);
                }
                }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query= reference.child("ride").orderByChild("custid").equalTo(mAuth.getUid());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    for (DataSnapshot ca : dataSnapshot.getChildren()) {
                        empty.setVisibility(View.GONE);
                        // do something with the individual "issues"
                        rideclass t = ca.getValue(rideclass.class);

                        String start = t.getStart();
                        stemp=start;
                        if (start.length() > 10) {

                            int u = start.indexOf(",");
                            stemp = start.substring(u + 1);
                            int k = stemp.indexOf(",");
                            stemp = stemp.substring(0, k);
                            if(stemp.length()>20)
                            {
                                stemp=stemp.substring(0,20);
                                stemp= stemp.concat("..");
                            }
                        }
                        String dest = t.getDest();
                        stemp2=dest;
                        if (dest.length() > 10) {

                            int u = dest.indexOf(",");
                            stemp2 = dest.substring(u + 1);
                            int k = stemp2.indexOf(",");
                            stemp2 = stemp2.substring(0, k);
                            if(stemp2.length()>20)
                            {
                                stemp2=stemp2.substring(0,20);
                                stemp2= stemp2.concat("..");
                            }
                        }
                        String status = t.getActive();
                        if (t.getActive().equals("no")) {
                            status = "Timed Out";
                        } else if (t.getActive().equals("yes")) {
                            status = "ACTIVE NOW";
                        }

                        HistoryList h = new HistoryList("ride", stemp, stemp2, status);
                        listitems.add(h);
                        adapter.notifyDataSetChanged();

                    }
                }else
                {

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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
}
