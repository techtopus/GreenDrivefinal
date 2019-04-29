package com.techtopus.greendrive;

import android.app.Activity;
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

import java.util.Calendar;

public class destination extends AppCompatActivity {
    private static final int PLACE_PICKER_REQUEST = 100;
    String start,dest,time,date,cid,d,m,h,o,y,address;
    CardView cv;
    int k=0,k2=0;
    int op=0;
    FirebaseAuth mAuth;
    Intent i;
    EditText t;
    Double slatitude,slongitude,dlatitude,dlongitude;
 public void nexter(View v)
{
    if(op==0) {
        Toast.makeText(this, "Enter a valid destination point", Toast.LENGTH_SHORT).show();
 return;
    }   if(k==0)
{
    Toast.makeText(this, "Enter the time of the trip", Toast.LENGTH_SHORT).show();
            return;
    }if(k2==0)
{
    Toast.makeText(this, "Enter the date of the trip", Toast.LENGTH_SHORT).show();
    return;
}

    else{

      //  Toast.makeText(this, d.toString(), Toast.LENGTH_SHORT).show();
        i=new Intent(this,addstop.class);
        i.putExtra("start",start);
        i.putExtra("dest",dest);

        i.putExtra("carid",cid);

    i.putExtra("slatitude",slatitude);
    i.putExtra("slongitude",slongitude);

    i.putExtra("dlatitude",dlatitude);
    i.putExtra("dlongitude",dlongitude);
    i.putExtra("y",y);
    i.putExtra("m",m);
    i.putExtra("d",d);
    i.putExtra("h",h);
    i.putExtra("min",o);
        startActivity(i);
    }

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
                Place place = PlacePicker.getPlace(data, this);
                address = String.format("%s", place.getAddress());
               // Toast.makeText(this, address, Toast.LENGTH_SHORT).show();
                dest=address;
                op=1;
                LatLng lng=place.getLatLng();
                dlatitude=lng.latitude;
                dlongitude=lng.longitude;
                cv.setVisibility(View.GONE);
                t.setText(dest);
                t.setVisibility(View.VISIBLE);



            }
        }

    }

    public void picker(View v)
{
    switch( v.getId()){
    case R.id.textView11:
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
               k2=1;
               h= String.valueOf(selectedHour);
               o= String.valueOf(selectedMinute);
               if(selectedHour<10)
                   h="0"+selectedHour;
               if(selectedMinute<10)
                   o="0"+selectedMinute;
               time=h+":"+o;

                e.setText( selectedHour + ":" + selectedMinute);
            }
        }, hour, minute, false);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
        break;
        case R.id.textView13:
            final Calendar c = Calendar.getInstance();
            int mYear = c.get(Calendar.YEAR);
            final int mMonth = c.get(Calendar.MONTH);
            final int mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
k=1;
d= String.valueOf(dayOfMonth);
m= String.valueOf(monthOfYear);
y= String.valueOf(year);
if(dayOfMonth<10){
    d="0"+dayOfMonth;

}m=String.valueOf(Integer.parseInt(m)+1);
if(monthOfYear<9)
{
    m="0"+monthOfYear;
    m=String.valueOf(Integer.parseInt(m)+1);
}
                    date=d+"-"+m+"-"+year;
                            e2.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            e2.setText(d+"-"+m+"-"+year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
            break;
}}


    TextView e,e2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination);
        start=getIntent().getStringExtra("start");
        op=0;
        cv=findViewById(R.id.cardView);
        t=findViewById(R.id.editText5);
        Toolbar toolbar=findViewById(R.id.toolbar3);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
mAuth=FirebaseAuth.getInstance();
        Bundle extras=getIntent().getExtras();
        if (extras != null) {
            slatitude=extras.getDouble("slatitude");
        }
        if (extras != null) {
            slongitude=extras.getDouble("slongitude");
        }

        e=findViewById(R.id.textView11);
        cid=getIntent().getStringExtra("carid");

        e2=findViewById(R.id.textView13);
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
                 dest = place.getName().toString();
                 op=1;
                LatLng dltln = place.getLatLng();
                 dlatitude=dltln.latitude;
                 dlongitude=dltln.longitude;
               // Toast.makeText(destination.this, place.getName().toString(), Toast.LENGTH_SHORT).show();
               // t1.setText(place.getName().toString());
            }

            @Override
            public void onError(Status status) {
               // t1.setText(status.toString());
                Toast.makeText(destination.this, status.toString(), Toast.LENGTH_SHORT).show();
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


    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, Login.class));

        }
        op=0;
    }
}
