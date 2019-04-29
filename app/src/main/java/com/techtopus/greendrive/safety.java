package com.techtopus.greendrive;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class safety extends AppCompatActivity {
    private static final int RQS_PICK_CONTACT = 101;
    private static final int RESULT_PICK_CONTACT = 100;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 000;
    private static final int SMS_PERMISSION_CONSTANT = 101;
    private static final int REQUEST_PERMISSION_SETTING =100 ;
    String tid, loc, mess, phoneNo;
    int picked;
    Double latitude, longitude;
    String number, msg, from, riderid;
    private static final long MINIMUM_DISTANCE_CHANGE_FOR_UPDATES = 1; // in Meters
    private static final long MINIMUM_TIME_BETWEEN_UPDATES = 1000; // in Milliseconds
    protected LocationManager locationManager;
    FirebaseAuth mAuth;
    private static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 0;
    View v;
    FirebaseAuth mAauth;
    private boolean sentToSettings=false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safety);
        tid = getIntent().getStringExtra("tid");
        from = getIntent().getStringExtra("from");
        mAauth = FirebaseAuth.getInstance();
        mAuth=FirebaseAuth.getInstance();
        Toolbar toolbar=findViewById(R.id.toolbar14);
picked=0;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        GPSTracker gpsTracker=new GPSTracker(getApplicationContext());
        longitude=gpsTracker.getLongitude();
        latitude=gpsTracker.getLatitude();
        Geocoder myLocation = new Geocoder(this, Locale.getDefault());
        List<Address> myList = null;
        try {
            myList = myLocation.getFromLocation((latitude),(longitude), 1);
        Address address = null;

            address = (Address) myList.get(0);

        String addressStr = "";
        addressStr += address.getAddressLine(0) + " ";
        // addressStr += address.getAddressLine(1) + ", ";
        //addressStr += address.getAddressLine(2);
        loc=addressStr;


        gpsTracker.stopUsingGPS();
       // Toast.makeText(this, addressStr, Toast.LENGTH_SHORT).show();

        } catch (IOException e) {
            Toast.makeText(this, "Please sync your gps", Toast.LENGTH_SHORT).show();
        }

    }


    public void contact(View v) {

        Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
        startActivityForResult(contactPickerIntent, RESULT_PICK_CONTACT);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // check whether the result is ok
        if (resultCode == RESULT_OK) {
            // Check for the request code, we might be usign multiple startActivityForReslut
            switch (requestCode) {
                case RESULT_PICK_CONTACT:
                    Cursor cursor = null;
                    try {
                        phoneNo = null;
                        String name = null;
                        Uri uri = data.getData();
                        cursor = getContentResolver().query(uri, null, null, null, null);
                        cursor.moveToFirst();
                        int phoneIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                        phoneNo = cursor.getString(phoneIndex);
                        if (phoneNo.contains("+"))
                            phoneNo = phoneNo.substring(3);
                        picked=1;
                       // Toast.makeText(this, phoneNo, Toast.LENGTH_SHORT).show();
                        number = phoneNo;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
        } else {
            Log.e("MainActivity", "Failed to pick contact");
        }

    }

    public void message(View view) {
        if (from.equals("rider")) {
            final DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
            Query query = reference.child("trip").orderByChild("id").equalTo(tid);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        trip t = dataSnapshot1.getValue(trip.class);
                        if (t.getActive().equals("yes")) {
                           mess= "ALERT FROM " + mAauth.getCurrentUser().getDisplayName() +
                                    "\n Current Location is " + loc + "\n Tap here to open in Google Maps :- http://maps.google.com/" +
                                    "?q=" + latitude + "," + longitude +
                                    "\n Driver details Name : " + t.getName() +
                                    "\n Vehicle Details Vehicle Number : " + t.getVno() + "  Model : " + t.getModel();
                            sendSMS(number, mess);
                            break;
                        }
                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        if (from.equals("driver")) {
            mess = "ALERT FROM " + mAauth.getCurrentUser().getDisplayName() +
                    "\n Current Location is " + loc + "\n Tap here to open in Google Maps :- http://maps.google.com/" +
                    "?q=" + latitude + "," + longitude + "\n ";
sendSMS(number, mess);
        //Toast.makeText(this, mess, Toast.LENGTH_SHORT).show();
           /* Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("sms:"));
            intent.putExtra("address", number);
            intent.putExtra("sms_body", mess);
            startActivity(intent);*/
    }}
public void callpolice(View v)
{
    Intent intent = new Intent(Intent.ACTION_DIAL);
    intent.setData(Uri.parse("tel:" + "100"));
    startActivity(intent);
}
    public void sendSMS(String phoneNo, String mess) {
      if(picked==1){
        Intent sendIntent = new Intent(Intent.ACTION_VIEW);
        sendIntent.setData(Uri.parse("sms:"+phoneNo));
        sendIntent.putExtra("sms_body",mess);
                startActivity(sendIntent);

    }
    else
          Toast.makeText(this, "pick a contact first", Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, Login.class));

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

        @SuppressLint("MissingPermission")
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
          //  Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

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
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this,DriveORRide.class));
    }
}

