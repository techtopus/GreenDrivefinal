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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class carlist  extends AppCompatActivity {
   // private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<Listitem> listitems;
    DatabaseReference reference;
    FirebaseAuth mAuth;
    TextView empty;
    int o=0;
    Listitem listitem,l2;
    String no,ty,v;
int y;

     RecyclerView recycler;
public void recycleclick(View view)
{
    int itemPosition = recycler.getChildLayoutPosition(view);
    l2= listitems.get(itemPosition);
    Intent i=new Intent(this,tripdetails.class);
    if(l2.getCid().equals("ooo"))
    {startActivity(new Intent(this,Drive.class));}
    i.putExtra("carid",l2.getCid());
    startActivity(i);
}
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carlist);
        mAuth=FirebaseAuth.getInstance();
        listitems=new ArrayList<>();
        Toolbar toolbar=findViewById(R.id.toolbar15);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

         recycler= findViewById(R.id.recyclerview);
         empty=findViewById(R.id.textView91);

        recycler.setLayoutManager(new LinearLayoutManager(this));

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("car").orderByChild("uid").equalTo(Objects.requireNonNull(mAuth.getCurrentUser()).getUid());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
                                                 @Override
                                                 public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                     if (dataSnapshot.exists()) {
                                                         try {
                                                             for (DataSnapshot ca : dataSnapshot.getChildren()) {

                                                                 // do something with the individual "issues"
                                                                 car c2=ca.getValue(car.class);

                                                                // Toast.makeText(carlist.this, c2.getRegno(), Toast.LENGTH_SHORT).show();

                                                                 no=c2.getRegno();
                                                                     ty=  c2.getType();

                                                                     v=  c2.getModel();

                                                                 if (ty.equals("bike")) {
                                                                     y = 1;
                                                                 }else if (ty.equals("lmv")) {
                                                                     y = 2;
                                                                 }else if (ty.equals("suv")) {
                                                                     y = 3;
                                                                 }

                                                                 listitem = new Listitem(no,v, y,c2.getCarid());

                                                                 listitems.add(listitem);


                                                                // Toast.makeText(carlist.this, String.valueOf(++o), Toast.LENGTH_SHORT).show();

                                                             }

                                                         adapter = new myadapter(listitems, getApplicationContext());
                                                         recycler.setAdapter(adapter);
                                                     }catch(Exception e){
                                                         Toast.makeText(carlist.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                                     }
                                                 }
                                                    else{
                                                         empty.setVisibility(View.VISIBLE);                                                }
                                                 }

                                                 @Override
                                                 public void onCancelled(@NonNull DatabaseError databaseError) {

                                                 }
                                             });
    }
    public void nxt(View v )
    {
        startActivity(new Intent(this,Drive.class));
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
