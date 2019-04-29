package com.techtopus.greendrive;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class tripdetails extends AppCompatActivity {
 FirebaseAuth mAuth;
    TextView t1, t2;Intent i;
    String cid;
    int op=0;
    String start="p";
    Double slatitude,slongitude;

    @Override
    protected void onStart() {

        super.onStart();
        if (mAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, Login.class));

        }
    }

    public void next(View view)
{if(op==0)
    {    t2.setVisibility(View.VISIBLE);
return;}
else{
    i = new Intent(this, destination.class);
i.putExtra("start",start);
i.putExtra("carid",cid);
i.putExtra("slatitude",slatitude);
i.putExtra("slongitude",slongitude);
    startActivity(i);
}}
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tripdetails);
        t2=findViewById(R.id.textView47);
        Toolbar toolbar=findViewById(R.id.toolbar6);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
op=0;
        mAuth=FirebaseAuth.getInstance();
       // t1 = findViewById(R.id.start);
       // t2 = findViewById(R.id.dest);
       // t1.setText("kannan nk");
       // t2.setText("akhil kanja");
         cid=getIntent().getStringExtra("carid");



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
                op=1;
                start=place.getName().toString();
                LatLng sltln = place.getLatLng();
                slatitude=sltln.latitude;
                slongitude=sltln.longitude;
               // Toast.makeText(tripdetails.this, place.getName().toString(), Toast.LENGTH_SHORT).show();
               // t1.setText(place.getName().toString());
            }

            @Override
            public void onError(Status status) {
              //  t1.setText(status.toString());
                Toast.makeText(tripdetails.this, status.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflator=getMenuInflater();
        inflator.inflate(R.menu.menu ,menu);




        return true;
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflate=getMenuInflater();
        inflate.inflate(R.menu.menu,menu);
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
            case R.id.randr:
                Intent i2=new Intent(this,rating.class);
                startActivity(i2);
        }
        return true;
    }

    public void showCurrentLocation(View view) {
    GPSTracker gpsTracker=new GPSTracker(getApplicationContext());
slongitude=gpsTracker.getLongitude();
slatitude=gpsTracker.getLatitude();
        Geocoder myLocation = new Geocoder(this, Locale.getDefault());
        List<Address> myList = null;
        try {
            myList = myLocation.getFromLocation((slatitude),(slongitude), 1);
        Address address = null;
        if (myList != null) {
            address = (Address) myList.get(0);
        }
        String addressStr = "";
        addressStr += address.getAddressLine(0) + " ";
        // addressStr += address.getAddressLine(1) + ", ";
        //addressStr += address.getAddressLine(2);
        start=addressStr;

op=1;
        gpsTracker.stopUsingGPS();
       // Toast.makeText(this, addressStr, Toast.LENGTH_SHORT).show();
        //t1.setText(addressStr);
        next(view);
        } catch (IOException e) {
            Toast.makeText(this, "Please sync your gps", Toast.LENGTH_SHORT).show();
        }

    }


    public class GPSTracker extends Service implements LocationListener {

        private final Context mContext;

        // flag for GPS status
        boolean isGPSEnabled = false;

        // flag for network status
        boolean isNetworkEnabled = false;

        // flag for GPS status
        boolean canGetLocation = false;

        Location location; // location
        double latitude; // latitude
        double longitude; // longitude

        // The minimum distance to change Updates in meters
        private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 1000; // 10 meters

        // The minimum time between updates in milliseconds
        private static final long MIN_TIME_BW_UPDATES = 100000 * 60 * 1; // 5 minute

        // Declaring a Location Manager
        protected LocationManager locationManager;

        public GPSTracker(Context context) {
            this.mContext = context;
            getLocation();
        }

        public Location getLocation() {
            try {
                locationManager = (LocationManager) mContext
                        .getSystemService(LOCATION_SERVICE);

                // getting GPS status
                //isGPSEnabled = locationManager
                //.isProviderEnabled(LocationManager.GPS_PROVIDER);

                // getting network status
                isNetworkEnabled = locationManager
                        .isProviderEnabled(LocationManager.NETWORK_PROVIDER);


                if (!isGPSEnabled && !isNetworkEnabled) {
                    // no network provider is enabled
                    showSettingsAlert();

                } else {
                    this.canGetLocation = true;
                    if (isNetworkEnabled) {
                        locationManager.requestLocationUpdates(
                                LocationManager.NETWORK_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        Log.d("Network", "Network");
                        if (locationManager != null) {
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                        }
                    }
                    // if GPS Enabled get lat/long using GPS Services
                    else {
                        if (isGPSEnabled) {

                            if (location == null) {
                                locationManager.requestLocationUpdates(
                                        LocationManager.GPS_PROVIDER,
                                        MIN_TIME_BW_UPDATES,
                                        MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                                Log.d("GPS Enabled", "GPS Enabled");
                                if (locationManager != null) {
                                    location = locationManager
                                            .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                                    if (location != null) {
                                        latitude = location.getLatitude();
                                        longitude = location.getLongitude();
                                    }
                                }
                            }
                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return location;
        }

        /*
         * Stop using GPS listener
         * Calling this function will stop using GPS in your app
         */
        public void stopUsingGPS() {
            if (locationManager != null) {
                locationManager.removeUpdates(GPSTracker.this);
            }
        }

        /*
         * Function to get latitude
         */
        public double getLatitude() {
            if (location != null) {
                latitude = location.getLatitude();
            }

            // return latitude
            return latitude;
        }

        /**
         * Function to get longitude
         */
        public double getLongitude() {
            if (location != null) {
                longitude = location.getLongitude();
            }

            // return longitude
            return longitude;
        }


        /**
         * Function to check GPS/wifi enabled
         *
         * @return boolean
         */
        public boolean canGetLocation() {
            return this.canGetLocation;
        }

        /*
         * Function to show settings alert dialog
         * On pressing Settings button will lauch Settings Options
         */
        public void showSettingsAlert() {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

            // Setting Dialog Title
            alertDialog.setTitle("GPS is settings");

            // Setting Dialog Message
            alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

            // On pressing Settings button
            alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    mContext.startActivity(intent);
                }
            });

            // on pressing cancel button
            alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    System.exit(0);

                }
            });

            // Showing Alert Message
            alertDialog.show();


        }

        public void onLocationChanged(Location location) {
            String message = String.format(
                    "New Location \n Longitude: %1$s \n Latitude: %2$s",
                    location.getLongitude(), location.getLatitude()
            );
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

        }

        public void onStatusChanged(String s, int i, Bundle b) {
            Toast.makeText(this, "Provider status changed", Toast.LENGTH_SHORT).show();
        }

        public void onProviderDisabled(String s) {
            Toast.makeText(this,"Provider disabled by the user. GPS turned off", Toast.LENGTH_SHORT).show();
        }

        public void onProviderEnabled(String s) {
            Toast.makeText(this, "Provider enabled by the user. GPS turned on", Toast.LENGTH_SHORT).show();
        }

        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }
    }


}