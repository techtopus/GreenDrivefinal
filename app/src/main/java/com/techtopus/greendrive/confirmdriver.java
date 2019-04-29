package com.techtopus.greendrive;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Date;

public class confirmdriver extends AppCompatActivity {
    String s,e,t,d,v,ty,no;
    TextView t1,t2,t3,t4,t5,t6,t8,t9,t10,t11;
    EditText t7;
    String vseat,stemp2;
    Button b;
    String stop1,stop2,stop3,cid="hai";
        Double slatitude,slongitude,dlatitude,dlongitude,
                s1latitude,s1longitude,s2latitude,s2longitude,s3latitude,s3longitude;
    FirebaseAuth mAuth;
    String y,m,dextra,h,o;
    DatabaseReference databasereference,databaseReference2;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmdriver);
        mAuth=FirebaseAuth.getInstance();
        t1=findViewById(R.id.textView21);
        t2=findViewById(R.id.textView22);
        Toolbar toolbar=findViewById(R.id.toolbar9);
b=findViewById(R.id.button10);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        cid=getIntent().getStringExtra("carid");
        t3=findViewById(R.id.textView23);
        databasereference= FirebaseDatabase.getInstance().getReference("car");
        t4=findViewById(R.id.textView24);
        t5=findViewById(R.id.textView25);
        t6=findViewById(R.id.textView26);
        t7=findViewById(R.id.as);
        t8=findViewById(R.id.textView31);
        t9=findViewById(R.id.textView34);
        t11=findViewById(R.id.textView36);
        t10=findViewById(R.id.textView28);
        s=getIntent().getStringExtra("start");
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
        if (extras != null) {
            s1latitude=extras.getDouble("s1latitude");
        }
        if (extras != null) {
            s1longitude=extras.getDouble("s1longitude");
                    }
        if (extras != null) {
            s2latitude=extras.getDouble("s2latitude");
        }
        if (extras != null) {
            s2longitude=extras.getDouble("s2longitude");
        }
        if (extras != null) {
            s3latitude=extras.getDouble("s3latitude");
        }
        if (extras != null) {
            s3longitude=extras.getDouble("s3longitude");
        }
        stop1=getIntent().getStringExtra("stop1");
        stop2=getIntent().getStringExtra("stop2");
        stop3=getIntent().getStringExtra("stop3");
        t8.setText(stop1);
        t9.setText(stop2);
        t10.setText(stop3);

        y=getIntent().getStringExtra("y");
        dextra=getIntent().getStringExtra("d");
        h=getIntent().getStringExtra("h");
        m=getIntent().getStringExtra("m");
        o=getIntent().getStringExtra("min");
       // Toast.makeText(this, dextra, Toast.LENGTH_SHORT).show();
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
        stemp2=e;
        if(e.length()>10)
        {
            int u= e.indexOf(",");
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
        t3.setText(h+":"+o);
        t4.setText(dextra+"/"+m+"/"+y);

    }

    @Override
    protected void onStart() {
        super.onStart();
        cid= getIntent().getStringExtra("carid");
        if (mAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, Login.class));

        }


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("car").orderByChild("carid").equalTo(cid);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                for (DataSnapshot ca : dataSnapshot.getChildren()) {
                    // do something with the individual "issues"
                    car c2=ca.getValue(car.class);
                    no= (String) c2.getRegno();
                    ty= (String) c2.getType();
                    v= (String) c2.getModel();
                    t5.setText(c2.getRegno());
                    t11.setText(c2.getType());
                    t6.setText(c2.getModel());
                }
            }}

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void confirm(View view)
    {
        vseat=t7.getText().toString();
    if(t7.getText().length()==0)
    {
        t7.setError("Enter the no of seats left");
        t7.isFocused();
        return;
    }b.setEnabled(false);
    String custid=mAuth.getCurrentUser().getUid();
        DatabaseReference  databasereference2= FirebaseDatabase.getInstance().getReference("trip");
        String id = databasereference2.push().getKey();

        String s2="https://firebasestorage.googleapis.com/v0/b/greendrive-c916f.appspot.com/o/profilepics%2F"+mAuth.getCurrentUser().getUid()+".jpg?alt=media&token=dcccb28a-25dc-4822-9952-9ec0740ac5b0";
        trip td=new trip(id,custid,s,e,no,v,ty,vseat,stop1,stop2,stop3,"yes",y,m,
                dextra,h,o,cid,slatitude,slongitude,dlatitude,dlongitude,s1latitude,
                s1longitude,s2latitude,s2longitude,s3latitude,s3longitude,mAuth.getCurrentUser().getDisplayName(),s2);
        databasereference2.child(id).setValue(td);
        Toast.makeText(this, "Your data is successfully uploaded", Toast.LENGTH_SHORT).show();
        finish();
        startActivity(new Intent(this,trippublish.class));
    }
public void edit(View view)
{finish();
    startActivity(new Intent(this,carlist.class));

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
