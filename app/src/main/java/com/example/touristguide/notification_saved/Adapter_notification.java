package com.example.touristguide.notification_saved;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.touristguide.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class Adapter_notification  extends RecyclerView.Adapter<Adapter_notification.ViewHolder> {

    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseFirestore;
    private static Context context;

    public  static class ViewHolder extends RecyclerView.ViewHolder{
           private CircleImageView Img_event;
           private TextView txt_subject,txt_Document,txt_Date , txt_location;
           private ProgressBar progressBar;

        public void setTxt_location(String txt_location) {
            this.txt_location.setText(txt_location);
        }

        public void setImg_event(String img_event) {
            Picasso.get().load(img_event).into(Img_event, new Callback() {
                @Override
                public void onSuccess() {
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onError(Exception e) {

                }
            });

        }

        public void setTxt_subject(String txt_subject) {
            this.txt_subject.setText(txt_subject);

        }

        public void setTxt_Document(String txt_Document) {
            this.txt_Document.setText(txt_Document);
            if (txt_Document.contains("Liked"))
            {
                Drawable img = context.getResources().getDrawable( R.drawable.check_state_love);
                this.txt_Document.setCompoundDrawablesRelative(img,null,null,null);
            }
            else
            {
                Drawable img = context.getResources().getDrawable( R.drawable.style_btn_comment);
                this.txt_Document.setCompoundDrawablesRelative(img,null,null,null);
            }
        }

        public void setTxt_Date(String txt_Date) {
            this.txt_Date.setText(txt_Date);
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Img_event =itemView.findViewById(R.id.img_event_notification_page);
            txt_subject =itemView.findViewById(R.id.txt_subject_event);
            txt_Document =itemView.findViewById(R.id.txt_document_noti_page);
            txt_Date =itemView.findViewById(R.id.txt_date_noti_page);
            progressBar = itemView.findViewById(R.id.pr_noti_image);
            txt_location = itemView.findViewById(R.id.txt_notification_location);


        }


    }
    private ArrayList<setNotification> list;
    public Adapter_notification(Context context , ArrayList<setNotification> list){
        this.context=context;
        this.list=list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.style_notification,parent,false);
        firebaseFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        firebaseFirestore.collection("User").document(list.get(position).getUserSender())
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                holder.setTxt_subject(task.getResult().get("Username").toString());
                holder.setImg_event(task.getResult().get("Image_User").toString());
            }
        });
        holder.setTxt_Document(list.get(position).getMsg());

        firebaseFirestore.collection("post").document(list.get(position).getPostInfo())
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful())
                {
                    holder.setTxt_location(task.getResult().get("CityName").toString());
                }

            }
        });

       // Toast.makeText(context,list.get(position).getPostInfo() , Toast.LENGTH_LONG).show();

       // Log.d("Info1",list.get(position).getPostInfo());

        long MilliSecound = list.get(position).getDate().getTime();

        java.util.Date today = new java.util.Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        String dateString = null;
        String datetoday = DateFormat.format("dd", new Date(MilliSecound)).toString();

        Log.d("Datecomment", datetoday + " / " + calendar.get(Calendar.DAY_OF_MONTH));
        if (Integer.parseInt(datetoday) == calendar.get(Calendar.DAY_OF_MONTH)) {
            dateString = DateFormat.format("HH:mm aa", new Date(MilliSecound)).toString();
        } else {
            dateString = DateFormat.format("dd MMM yyyy", new Date(MilliSecound)).toString();
        }

        holder.setTxt_Date(dateString);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
