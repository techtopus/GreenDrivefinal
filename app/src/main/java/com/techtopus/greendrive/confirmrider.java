package com.techtopus.greendrive;

import android.content.Intent;
import android.drm.DrmStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class confirmrider extends AppCompatActivity {
    TextView t1,t2,t3,t4;
    Button b;
    FirebaseAuth mAuth;
    String start,dest,d,m,h,o,y,s,e;
    Double slatitude,slongitude,dlatitude,dlongitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmrider);
        mAuth=FirebaseAuth.getInstance();
        s=getIntent().getStringExtra("start");
        Toolbar toolbar=findViewById(R.id.toolbar11);
b=findViewById(R.id.button14);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        e=getIntent().getStringExtra("dest");

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


        t1=findViewById(R.id.textView53);
        t2=findViewById(R.id.textView55);
        String stemp=s;
        if(s.length()>10)
        {
            int u= s.indexOf(",");
            stemp=s.substring(u+1);
            int k=stemp.indexOf(",");
            stemp=stemp.substring(0,k);

            if(stemp.length()>20)
            {
                stemp=stemp.substring(0,20);
                stemp= stemp.concat("..");
            }
        }
        t1.setText(stemp);
        String stemp2=e;
        if(e.length()>10)
        {
            int u=e.indexOf(",");
            stemp2=e.substring(u+1);
            int k=stemp2.indexOf(",");
            stemp2=stemp2.substring(0,k);

            if(stemp2.length()>20)
            {
                stemp2=stemp2.substring(0,20);
                stemp2=  stemp2.concat("..");
            }
        }
        t2.setText(stemp2);



    }
    public void confirm(View view)
    {
        b.setEnabled(false);
        String custid=mAuth.getCurrentUser().getUid();
        DatabaseReference databasereference2= FirebaseDatabase.getInstance().getReference("ride");
        String id = databasereference2.push().getKey();


        rideclass td=new rideclass(id,custid,s,e,"yes",slatitude,slongitude,dlatitude,dlongitude);
        databasereference2.child(id).setValue(td);
        Toast.makeText(this, "Your data is successfully uploaded", Toast.LENGTH_SHORT).show();
        finish();
        startActivity(new Intent(this,ridepublish.class));
    }
    public void edit(View view)
    {
        finish();
        startActivity(new Intent(this, Ride.class));

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
