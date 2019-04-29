package com.techtopus.greendrive;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.StrictMode;
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

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class pendingAdapter extends RecyclerView.Adapter<pendingAdapter.ViewHolder> {
    String rider,phone,start,email;
    Double lat,longt;
    private List<listrequest> listitems;
    private Context context;

    public pendingAdapter(List<listrequest> listitems, Context context) {
        this.listitems = listitems;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.customlayout3,viewGroup,false);
        return new pendingAdapter.ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        listrequest listitem=listitems.get(i);
        viewHolder.t1.setText(listitem.getTripId());
        viewHolder.t2.setText(listitem.getRiderid());
        viewHolder.t3.setText(listitem.getRequestid());
        email=listitem.getEmail();
        //Toast.makeText(context, email, Toast.LENGTH_SHORT).show();
        Glide.with(context).load(listitem.getImglink()).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                return false;
            }
        }).into(viewHolder.img);
        viewHolder.b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.b1.setVisibility(View.GONE);
                viewHolder.b2.setVisibility(View.GONE);
              /*  viewHolder.i1.setVisibility(View.VISIBLE);
                viewHolder.i2.setVisibility(View.VISIBLE);
                viewHolder.i3.setVisibility(View.VISIBLE);*/
              viewHolder.t4.setVisibility(View.VISIBLE);

                Toast.makeText(context,viewHolder.t1.getText(), Toast.LENGTH_SHORT).show();
                accept(viewHolder,viewHolder.t3.getText().toString());
            }
        });
        viewHolder.b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "rejected", Toast.LENGTH_SHORT).show();
                reject(viewHolder.t3.getText().toString(),i);
            }
        });

                viewHolder.i1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                        Query query = reference.child("user").orderByChild("userid").equalTo(rider);
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                                    user r = dataSnapshot1.getValue(user.class);
                                    phone=r.getPhno();
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
        viewHolder.i2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                Query query = reference.child("user").orderByChild("userid").equalTo(rider);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            user r = dataSnapshot1.getValue(user.class);
                            phone = r.getPhno();
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
        viewHolder.i3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                Query query = reference.child("ride").orderByChild("custid").equalTo(rider);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                                                         @Override
                                                         public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                             for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                                                 Toast.makeText(context, "Fond rider location", Toast.LENGTH_SHORT).show();
                                                                 rideclass r = dataSnapshot1.getValue(rideclass.class);
                                                             lat=r.getSlatitude();
                                                             longt=r.getSlongitude();
                                                             start=r.getStart();
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

    }
    public void reject(String id,int i)
    {sendNotification2();
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("Request").orderByChild("requestid").equalTo(id);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    RequestClass r = dataSnapshot1.getValue(RequestClass.class);
                reference.child("Request").child(r.getRequestid()).child("status").setValue("cancelled");

                    Toast.makeText(context, "rejected", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
       listitems.remove(listitems.get(i));
       notifyDataSetChanged();

    }

    private void sendNotification2() {

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                int SDK_INT= Build.VERSION.SDK_INT;
                if(SDK_INT>8)
                {
                    StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);



                    try {
                        String jsonResponse;


                        URL url = new URL("https://onesignal.com/api/v1/notifications");
                        HttpURLConnection con = (HttpURLConnection) url.openConnection();
                        con.setUseCaches(false);
                        con.setDoOutput(true);
                        con.setDoInput(true);

                        con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                        con.setRequestProperty("Authorization", "Basic MWNiYWE4OWYtNTAyNC00ZjY3LTgwZWItMDQyYjRmMWRiNGQ1");
                        con.setRequestMethod("POST");
                        //     Toast.makeText(driverdetails.this, e.getMail(), Toast.LENGTH_SHORT).show();
                        String strJsonBody = "{"
                                + "\"app_id\": \"35626e01-c7b4-46c1-9209-f67f25466b67\","

                                + "\"filters\": [{\"field\": \"tag\", \"key\": \"User_ID\", \"relation\": \"=\", \"value\": \"" +email  + "\"}],"

                                + "\"data\": {\"foo\": \"bar\"},"
                                + "\"contents\": {\"en\": \"Oops Sorry your request has been rejected! Please try with another drivers!!!!\"}"
                                + "}";


                        System.out.println("strJsonBody:\n" + strJsonBody);

                        byte[] sendBytes = strJsonBody.getBytes("UTF-8");
                        con.setFixedLengthStreamingMode(sendBytes.length);

                        OutputStream outputStream = con.getOutputStream();
                        outputStream.write(sendBytes);

                        int httpResponse = con.getResponseCode();
                        System.out.println("httpResponse: " + httpResponse);

                        if (httpResponse >= HttpURLConnection.HTTP_OK
                                && httpResponse < HttpURLConnection.HTTP_BAD_REQUEST) {
                            Scanner scanner = new Scanner(con.getInputStream(), "UTF-8");
                            jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                            scanner.close();
                        } else {
                            Scanner scanner = new Scanner(con.getErrorStream(), "UTF-8");
                            jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                            scanner.close();
                        }
                        System.out.println("jsonResponse:\n" + jsonResponse);

                    } catch (Exception e) {
                        //Toast.makeText(driverdetails.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }


                }
            }
        });

    }

    public void accept(final ViewHolder viewHolder,final String id)
    {

        sendNotification();
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("Request").orderByChild("requestid").equalTo(id);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    RequestClass r = dataSnapshot1.getValue(RequestClass.class);

                    reference.child("Request").child(r.getRequestid()).child("status").setValue("yes");

                    Toast.makeText(context, "accepted", Toast.LENGTH_SHORT).show();

                    final DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference();
                    Query query2 = reference2.child("trip").orderByChild("id").equalTo(r.getTripId());
                    query2.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                trip t = dataSnapshot1.getValue(trip.class);
                                String tr= String.valueOf(Integer.parseInt(t.getSeat()) - 1);
                                reference.child("trip").child(t.getId()).child("seat").setValue(tr);
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    @Override
    public int getItemCount() {
        return listitems.size();
    }

    public void sendNotification() {

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                int SDK_INT= Build.VERSION.SDK_INT;
                if(SDK_INT>8)
                {
                    StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);



                    try {
                        String jsonResponse;


                        URL url = new URL("https://onesignal.com/api/v1/notifications");
                        HttpURLConnection con = (HttpURLConnection) url.openConnection();
                        con.setUseCaches(false);
                        con.setDoOutput(true);
                        con.setDoInput(true);

                        con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                        con.setRequestProperty("Authorization", "Basic MWNiYWE4OWYtNTAyNC00ZjY3LTgwZWItMDQyYjRmMWRiNGQ1");
                        con.setRequestMethod("POST");
                        //     Toast.makeText(driverdetails.this, e.getMail(), Toast.LENGTH_SHORT).show();
                        String strJsonBody = "{"
                                + "\"app_id\": \"35626e01-c7b4-46c1-9209-f67f25466b67\","

                                + "\"filters\": [{\"field\": \"tag\", \"key\": \"User_ID\", \"relation\": \"=\", \"value\": \"" +email  + "\"}],"

                                + "\"data\": {\"foo\": \"bar\"},"
                                + "\"contents\": {\"en\": \"YOUR REQUEST HAS BEEN ACCEPTED!!\"}"
                                + "}";


                        System.out.println("strJsonBody:\n" + strJsonBody);

                        byte[] sendBytes = strJsonBody.getBytes("UTF-8");
                        con.setFixedLengthStreamingMode(sendBytes.length);

                        OutputStream outputStream = con.getOutputStream();
                        outputStream.write(sendBytes);

                        int httpResponse = con.getResponseCode();
                        System.out.println("httpResponse: " + httpResponse);

                        if (httpResponse >= HttpURLConnection.HTTP_OK
                                && httpResponse < HttpURLConnection.HTTP_BAD_REQUEST) {
                            Scanner scanner = new Scanner(con.getInputStream(), "UTF-8");
                            jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                            scanner.close();
                        } else {
                            Scanner scanner = new Scanner(con.getErrorStream(), "UTF-8");
                            jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                            scanner.close();
                        }
                        System.out.println("jsonResponse:\n" + jsonResponse);

                    } catch (Exception e) {
                        //Toast.makeText(driverdetails.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }


                }
            }
        });
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView t1,t2,t3,t4;
        ImageView img;
        Button b1,b2;
        ImageView i1,i2,i3;
        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            t4=itemView.findViewById(R.id.textView110);
            t1=itemView.findViewById(R.id.textView51);
            t2=itemView.findViewById(R.id.textView57);
            t3=itemView.findViewById(R.id.textView72);
            img=itemView.findViewById(R.id.img2);
            b1=itemView.findViewById(R.id.accept);
            b2=itemView.findViewById(R.id.reject);
            i1=itemView.findViewById(R.id.imageView38);
            i2=itemView.findViewById(R.id.imageView39);
            i3=itemView.findViewById(R.id.imageView40);

        }



    }
}
