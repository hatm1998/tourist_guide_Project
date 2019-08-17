package com.example.touristguide.Event.Event_Activity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.touristguide.Event.Display_Event.Display_Event_page;
import com.example.touristguide.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Adapter_Events  extends RecyclerView.Adapter<Adapter_Events.ViewHolder> {


    public  static class ViewHolder extends RecyclerView.ViewHolder{
        private CardView cardView;
        private ImageView Img_Event;
        private ProgressBar progressBar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView=itemView.findViewById(R.id.card_Event_Page);
            Img_Event=itemView.findViewById(R.id.Img_Event_Event_page);
            progressBar=itemView.findViewById(R.id.progress_Img_Event_page);
        }


    }

    private Context context;
    private ArrayList<set_Events> list;
    public Adapter_Events(Context context , ArrayList<set_Events> list){
        this.context=context;
        this.list=list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.style_events,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final set_Events pos=list.get(position);
        // On Click Card .
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent =new Intent(context, Display_Event_page.class);
                intent.putExtra("Img",pos.getImg());
                intent.putExtra("name",pos.getName_ev());
                intent.putExtra("date",pos.getDate());
                context.startActivity(intent);
            }
        });


        //  set Pic (User)
        Picasso.get().load(pos.getImg()).into(holder.Img_Event,new Callback() {
            @Override
            public void onSuccess() {
                holder.progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError(Exception e) {

            }

        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
