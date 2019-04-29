package com.techtopus.greendrive;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class myadapter extends RecyclerView.Adapter<myadapter.ViewHolder> {
    private List<Listitem> listitems;
    private Context context;

    public myadapter(List<Listitem> listitem, Context context) {
        this.listitems = listitem;
        this.context = context;
    }

    @NonNull
    @Override
    public myadapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.customlayout,viewGroup,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull myadapter.ViewHolder viewHolder, int i) {

        Listitem listitem=listitems.get(i);
        viewHolder.t1.setText(listitem.getDrivername());
        viewHolder.t2.setText(listitem.getModel());
        if(listitem.getType()==1)
            viewHolder.img.setImageResource(R.drawable.bike);
       else if(listitem.getType()==2)
            viewHolder.img.setImageResource(R.drawable.icons84);
        else if(listitem.getType()==3)
            viewHolder.img.setImageResource(R.drawable.suv);
        else if(listitem.getType()==4)
            viewHolder.img.setImageResource(R.drawable.empty);

    }

    @Override
    public int getItemCount() {
        return listitems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
TextView t1,t2;
ImageView img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            t1=itemView.findViewById(R.id.textView64);
            t2=itemView.findViewById(R.id.textView65);
            img=itemView.findViewById(R.id.imageView28);

        }
    }
}
