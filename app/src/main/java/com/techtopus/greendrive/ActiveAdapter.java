package com.techtopus.greendrive;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class ActiveAdapter extends RecyclerView.Adapter<ActiveAdapter.Viewholder2> {

    private List<AactiveList> listitems;
    private Context context;

    public ActiveAdapter(List<AactiveList> listitems, Context context) {
        this.listitems = listitems;
        this.context = context;
    }

    @NonNull
    @Override
    public ActiveAdapter.Viewholder2 onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activecustomlayout,viewGroup,false);
        return new ActiveAdapter.Viewholder2(v);

    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder2 viewholder2, final int i) {
        final AactiveList listitem=listitems.get(i);
        viewholder2.name.setText("Name : "+listitem.getName().toUpperCase());
        viewholder2.pick.setText("Pickup : \n"+listitem.getPickup());
        viewholder2.km.setText(String.valueOf(listitem.getKm()));
        Glide.with(context).load(listitem.getImglink()).listener(new RequestListener<Drawable>(){
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                return false;
            }
        }).into(viewholder2.img);


                viewholder2.phone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                        Query query = reference.child("user").orderByChild("userid").equalTo(listitem.getRid());
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                                    user r = dataSnapshot1.getValue(user.class);
                                    String   phone=r.getPhno();
                                    Intent intent = new Intent(Intent.ACTION_DIAL);
                                    intent.setData(Uri.parse("tel:"+phone));
                                    context.startActivity(intent);

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                });
                viewholder2.sms.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                        Query query = reference.child("user").orderByChild("userid").equalTo(listitem.getRid());
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                    user r = dataSnapshot1.getValue(user.class);
                                   String phone = r.getPhno();
                                    Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                                    sendIntent.setData(Uri.parse("sms:"+phone));
                                    context.startActivity(sendIntent);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }
                });
                viewholder2.nav.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                        Query query = reference.child("ride").orderByChild("custid").equalTo(listitem.getRid());
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                    rideclass r = dataSnapshot1.getValue(rideclass.class);
                                  Double  lat=r.getSlatitude();
                                    Double  longt=r.getSlongitude();
                                    String start=r.getStart();
                                    String geoUri = "http://maps.google.com/maps?q=loc:" + lat + "," + longt + " (" + start + ")";
                                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(geoUri));
                                    context.startActivity(intent);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });



                    }
                });
                viewholder2.b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                        Query query = reference.child("Request").orderByChild("riderid").equalTo(listitem.getRid());
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                    RequestClass r = dataSnapshot1.getValue(RequestClass.class);
                                if(r.getStatus().equals("yes"))
                                {reference.child("Request").child(r.getRequestid()).child("status").setValue("cancelled");
                                    final DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference();
                                    Query query2 = reference2.child("trip").orderByChild("id").equalTo(r.getTripId());
                                    query2.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                                trip r = dataSnapshot1.getValue(trip.class);
                                                reference2.child("trip").child(r.getId()).child("seat").setValue(String.valueOf(Integer.parseInt(r.getSeat())+1));
                                            }
                                            }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });

                                }}
                                }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        listitems.remove(listitems.get(i));
                        notifyDataSetChanged();

                    }
                });


}

    @Override
    public int getItemCount() {
        return listitems.size();
    }

    public class Viewholder2 extends RecyclerView.ViewHolder {
    TextView name,pick,km;
    ImageView img,phone,sms,nav;
    Button b;

        public Viewholder2(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.textView79);
            pick=itemView.findViewById(R.id.textView80);
            km=itemView.findViewById(R.id.textView81);
            phone=itemView.findViewById(R.id.imageView45);
            sms=itemView.findViewById(R.id.imageView46);
            nav=itemView.findViewById(R.id.imageView47);
            img=itemView.findViewById(R.id.pro);
            b=itemView.findViewById(R.id.button30);
        }
    }
}
