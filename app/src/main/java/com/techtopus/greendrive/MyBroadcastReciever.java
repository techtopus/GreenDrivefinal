package com.techtopus.greendrive;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.onesignal.OneSignal;

import org.w3c.dom.Text;

import static android.support.v4.content.ContextCompat.startActivity;

public class MyBroadcastReciever extends BroadcastReceiver {
FirebaseAuth mAuth;
    DatabaseReference ref;
    @Override
    public void onReceive(Context context, Intent intent) {
        mAuth=FirebaseAuth.getInstance();
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("trip").orderByChild("custid").equalTo(mAuth.getCurrentUser().getUid());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    trip t = dataSnapshot1.getValue(trip.class);
                        if(t.getActive().equals("yes")) {
    reference.child("trip").child(t.getId()).child("active").setValue("no");

    final DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference();
    Query query2 = reference2.child("Request").orderByChild("tripId").equalTo(t.getId());
    query2.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                RequestClass r = dataSnapshot1.getValue(RequestClass.class);
                if (r.getStatus().equals("yes"))
                    reference.child("Request").child(r.getRequestid()).child("status").setValue("Cancelled");
            }
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    });

}               }}

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
