package com.example.touristguide.notification_saved;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.touristguide.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Adapter_notification  extends RecyclerView.Adapter<Adapter_notification.ViewHolder> {


    public  static class ViewHolder extends RecyclerView.ViewHolder{
           private CircleImageView Img_event;
           private TextView txt_subject,txt_Document,txt_Date;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Img_event =itemView.findViewById(R.id.img_event_notification_page);
            txt_subject =itemView.findViewById(R.id.txt_subject_event);
            txt_Document =itemView.findViewById(R.id.txt_document_noti_page);
            txt_Date =itemView.findViewById(R.id.txt_date_noti_page);

        }


    }

     private Context context;
    private ArrayList<setNotification> list;
    public Adapter_notification(Context context , ArrayList<setNotification> list){
        this.context=context;
        this.list=list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.style_notification,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final setNotification pos=list.get(position);
      // Set Image For Event URL.
       // holder.Img_event.setImageResource(R.drawable.ffff);
        // Set subject .
       // holder.txt_subject.setText(pos.geSubject());
        // Set Document .
       // holder.txt_Document.setText(pos.getDocument());
        // Set Date .
        //holder.txt_Date.setText("");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
