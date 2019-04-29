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

public class historyAdapter extends RecyclerView.Adapter<historyAdapter.Viewholder3> {
    private List<HistoryList> listitems;
    private Context context;

    public historyAdapter(List<HistoryList> listitems, Context context) {
        this.listitems = listitems;
        this.context = context;
    }

    @NonNull
    @Override
    public Viewholder3 onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.historylayout,viewGroup,false);
        return new historyAdapter.Viewholder3(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder3 viewholder3, int i) {
        final HistoryList listitem=listitems.get(i);
        viewholder3.drive.setText(listitem.getType());
        viewholder3.Fromto.setText(listitem.getFrom());
        viewholder3.to.setText(listitem.getTo());
        viewholder3.status.setText(listitem.getStatus());
        if (listitem.getType().equals("drive")){
            viewholder3.img.setImageResource(R.drawable.icons83);
        }
        else
            viewholder3.img.setImageResource(R.drawable.icons84);

    }

    @Override
    public int getItemCount() {
        return listitems.size();
    }

    public class Viewholder3 extends RecyclerView.ViewHolder {
        TextView drive,Fromto,status,to;
        ImageView img;
        public Viewholder3(@NonNull View itemView) {
            super(itemView);
            drive=itemView.findViewById(R.id.textView97);
            Fromto=itemView.findViewById(R.id.textView98);
            status=itemView.findViewById(R.id.textView99);
            to=itemView.findViewById(R.id.textView100);
            img=itemView.findViewById(R.id.circleImageView6);

        }
    }
}
