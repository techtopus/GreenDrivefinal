package com.techtopus.greendrive;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class adapter2 extends RecyclerView.Adapter<adapter2.Viewholder2> {

    private List<Listitem2> listitems2;
    private Context context;

    public adapter2(List<Listitem2> listitems2, Context context) {
        this.listitems2 = listitems2;
        this.context = context;
    }

    public List<Listitem2> getListitems2() {
        return listitems2;
    }

    public void setListitems2(List<Listitem2> listitems2) {
        this.listitems2 = listitems2;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public adapter2.Viewholder2 onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.customlayout2,viewGroup,false);
        return new adapter2.Viewholder2(v);
    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(@NonNull final Viewholder2 viewholder2, int i) {

        Listitem2 listitem2=listitems2.get(i);
        viewholder2.t1.setText(listitem2.getDrivername());
        viewholder2.t2.setText(listitem2.getVtype()+" seats left");
        if(listitem2.getVtype()<=2){
            viewholder2.t2.setTextColor(R.color.quantum_googred );
            viewholder2.t2.setText(listitem2.getVtype()+" seats left");
        }
        viewholder2.t3.setText(listitem2.getFromto());
        viewholder2.t4.setText(listitem2.getTime());
        viewholder2.t5.setText(listitem2.getDate());
        //String str=String.format("%02d KM away",listitem2.getKm());
        viewholder2.t6.setText(listitem2.getKm().toString());
        Glide.with(context).load(listitem2.getImglink()).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                viewholder2.img.setImageResource(R.drawable.male);
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                return false;
            }
        }).into(viewholder2.img);
    }


    @Override
    public int getItemCount() {
        return listitems2.size();
    }

    public class Viewholder2 extends RecyclerView.ViewHolder {
        TextView t1,t2,t3,t4,t5,t6;
        ImageView img;
        public Viewholder2(@NonNull View itemView) {
            super(itemView);
            t1=itemView.findViewById(R.id.textView39);
            t2=itemView.findViewById(R.id.textView40);
            t3=itemView.findViewById(R.id.textView41);
            t4=itemView.findViewById(R.id.textView42);
            t5=itemView.findViewById(R.id.textView62);
            t6=itemView.findViewById(R.id.textView56);
            img=itemView.findViewById(R.id.circleImageView3);


        }
    }
}
