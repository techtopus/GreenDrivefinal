package com.techtopus.greendrive;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Date;

public class addstop extends AppCompatActivity {
    TextView t1,t2;
    String s1,s2,stop1="--",stop2="--",stop3="--",d,t;
    Date dv;
    CardView c1,c2,c3;
    ImageView i1,i2,i3;
    String stemp2;
    FirebaseAuth mAuth;
    String start,dest,time,date,cid;
    String y,m,dextra,h,o;
    Double slatitude= Double.valueOf(0),slongitude= Double.valueOf(0),dlatitude= Double.valueOf(0),dlongitude= Double.valueOf(0);
    Double s1latitude= Double.valueOf(0),s1longitude= Double.valueOf(0),s2latitude= Double.valueOf(0),s2longitude= Double.valueOf(0),s3latitude= Double.valueOf(0),s3longitude= Double.valueOf(0);
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addstop);
        t1=findViewById(R.id.textView30);
        cid=getIntent().getStringExtra("carid");
        mAuth=FirebaseAuth.getInstance();
       // Toast.makeText(this, cid, Toast.LENGTH_SHORT).show();
        Toolbar toolbar=findViewById(R.id.toolbar4);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        c1=findViewById(R.id.idCardView);
        c2=findViewById(R.id.idCardView2);
        c3=findViewById(R.id.idCardView3);
        i1=findViewById(R.id.imageView18);
        i2=findViewById(R.id.imageView19);
        i3=findViewById(R.id.imageView20);
        t2=findViewById(R.id.textView29);
        s1=getIntent().getStringExtra("start");
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
        y=getIntent().getStringExtra("y");
        dextra=getIntent().getStringExtra("d");
        h=getIntent().getStringExtra("h");
        m=getIntent().getStringExtra("m");
        o=getIntent().getStringExtra("min");


        //dv= new Date(getIntent().getStringExtra("datevar"));
        String stemp=s1;
        if(s1.length()>10)
        {
            int u= s1.indexOf(",");
            stemp=stemp.substring(u+1);
            int k=stemp.indexOf(",");
            stemp=stemp.substring(0,k);

            if(stemp.length()>20)
            {
                stemp=stemp.substring(0,20);
                stemp= stemp.concat("..");
            }

        }
        t1.setText(stemp);
        s2=getIntent().getStringExtra("dest");
        stemp2=s2;
        if(s2.length()>10)
        {
            int u= s2.indexOf(",");
            stemp2=stemp2.substring(u+1);
            int k=stemp2.indexOf(",");
            stemp2=stemp2.substring(0,k);

            if(stemp2.length()>20)
            {
                stemp2=stemp2.substring(0,20);
                stemp2=stemp2.concat("..");

            }
        }
        t2.setText(stemp2);

        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        AutocompleteFilter filter = new AutocompleteFilter.Builder()
                .setCountry("IN")
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES)
                .build();
        autocompleteFragment.setFilter(filter);
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                stop1 = place.getName().toString();
                LatLng s1stln = place.getLatLng();
                s1latitude=s1stln.latitude;
                s1longitude=s1stln.longitude;
             //   Toast.makeText(addstop.this, place.getName().toString(), Toast.LENGTH_SHORT).show();
                // t1.setText(place.getName().toString());
            }

            @Override
            public void onError(Status status) {
                // t1.setText(status.toString());
                Toast.makeText(addstop.this, status.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        PlaceAutocompleteFragment autocompleteFragment2 = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment2);

        AutocompleteFilter filter2 = new AutocompleteFilter.Builder()
                .setCountry("IN")
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES)
                .build();
        autocompleteFragment2.setFilter(filter);
        autocompleteFragment2.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                stop2 = place.getName().toString();
                LatLng s1stln = place.getLatLng();
                s2latitude=s1stln.latitude;
                s2longitude=s1stln.longitude;

            //    Toast.makeText(addstop.this, place.getName().toString(), Toast.LENGTH_SHORT).show();
                // t1.setText(place.getName().toString());
            }

            @Override
            public void onError(Status status) {
                // t1.setText(status.toString());
                Toast.makeText(addstop.this, status.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        PlaceAutocompleteFragment autocompleteFragment3 = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment3);

        AutocompleteFilter filter3 = new AutocompleteFilter.Builder()
                .setCountry("IN")
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES)
                .build();
        autocompleteFragment3.setFilter(filter);
        autocompleteFragment3.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                stop3 = place.getName().toString();
                LatLng s1stln = place.getLatLng();
                s3latitude=s1stln.latitude;
                s3longitude=s1stln.longitude;

              //  Toast.makeText(addstop.this, place.getName().toString(), Toast.LENGTH_SHORT).show();
                // t1.setText(place.getName().toString());
            }

            @Override
            public void onError(Status status) {
                // t1.setText(status.toString());
                Toast.makeText(addstop.this, status.toString(), Toast.LENGTH_SHORT).show();
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

    public void nextpg(View view){
Intent i=new Intent(this,confirmdriver.class);
        i.putExtra("start",s1);
        i.putExtra("dest",s2);
        i.putExtra("slatitude",slatitude);
        i.putExtra("slongitude",slongitude);

        i.putExtra("dlatitude",dlatitude);
        i.putExtra("dlongitude",dlongitude);
        i.putExtra("stop1",stop1);
        i.putExtra("carid",cid);
        i.putExtra("stop2",stop2);
        i.putExtra("stop3",stop3);
       // i.putExtra("datevar",dv);
        i.putExtra("y",y);
        i.putExtra("m",m);
        i.putExtra("d",dextra);
        i.putExtra("h",h);
        i.putExtra("min",o);
        i.putExtra("s1latitude",s1latitude);
        i.putExtra("s1longitude",s1longitude);
        i.putExtra("s2latitude",s2latitude);
        i.putExtra("s2longitude",s2longitude);
        i.putExtra("s3latitude",s3latitude);
        i.putExtra("s3longitude",s3longitude);

        startActivity(i);
    }
    public void add(View view)
    {
        switch(view.getId())
        { case R.id.imageView18:
i1.setVisibility(View.GONE);
c1.setVisibility(View.VISIBLE);
            break;
            case R.id.imageView19:
                i2.setVisibility(View.GONE);
                c2.setVisibility(View.VISIBLE);
                break;
            case R.id.imageView20:
                i3.setVisibility(View.GONE);
                c3.setVisibility(View.VISIBLE);
                break;
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
