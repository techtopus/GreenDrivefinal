package com.techtopus.greendrive;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;


public class ridedest extends AppCompatActivity {
    private static final int PLACE_PICKER_REQUEST = 100;
    String start="l",dest,time,date,cid,d,m,h,o,y;
    TextView e,e2,t1,t2,t3;
    CardView cv;
    int op=0;
    int k=0,k2=0;
    FirebaseAuth mAuth;
    EditText t;
    Double slatitude,slongitude,dlatitude,dlongitude;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ridedest);
        start=getIntent().getStringExtra("start");
        t1=findViewById(R.id.textView49);
        op=0;
        Toolbar toolbar=findViewById(R.id.toolbar8);
t=findViewById(R.id.editText6);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mAuth=FirebaseAuth.getInstance();
        cv=findViewById(R.id.cardView);
        Bundle extras=getIntent().getExtras();
        if (extras != null) {
            slatitude=extras.getDouble("slatitude");
        }
        if (extras != null) {
            slongitude=extras.getDouble("slongitude");
        }
        //slatitude= Double.valueOf(getIntent().getStringExtra("slatitude"));
        //slongitude=Double.valueOf(getIntent().getStringExtra("slongitude"));



        PlaceAutocompleteFragment autocompleteFragment2 = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment2);

        AutocompleteFilter filter2 = new AutocompleteFilter.Builder()
                .setCountry("IN")
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES)
                .build();
        autocompleteFragment2.setFilter(filter2);
        autocompleteFragment2.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
op=1;
                dest = place.getName().toString();
                LatLng dltln = place.getLatLng();
                dlatitude=dltln.latitude;
                dlongitude=dltln.longitude;

            }

            @Override
            public void onError(Status status) {
                Toast.makeText(ridedest.this, status.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void nexter(View view)
    {
        if(op==0)
        {
            t1.setVisibility(View.VISIBLE);
            return;
        }
               Intent i= new Intent(this,confirmrider.class);
        i.putExtra("start",start);
        i.putExtra("dest",dest);
        i.putExtra("slatitude",slatitude);
        i.putExtra("slongitude",slongitude);

        i.putExtra("dlatitude",dlatitude);
        i.putExtra("dlongitude",dlongitude);

        startActivity(i);


    }
    public void selectfrmmap(View v)
    {

        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

        Intent intent;
        try {
            intent = builder.build(this);
            startActivityForResult(intent,PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }

    }
    View view;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PLACE_PICKER_REQUEST)
        {
            if(resultCode==RESULT_OK){
                op=1;
                Place place = PlacePicker.getPlace(data, this);
                String address = String.format("%s", place.getAddress());
               // Toast.makeText(this, address, Toast.LENGTH_SHORT).show();
                dest=address;
                LatLng lng=place.getLatLng();
                dlatitude=lng.latitude;
                dlongitude=lng.longitude;
                cv.setVisibility(View.GONE);
                t.setText(dest);
                t.setVisibility(View.VISIBLE);



            }
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

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, Login.class));

        }
    }
}




