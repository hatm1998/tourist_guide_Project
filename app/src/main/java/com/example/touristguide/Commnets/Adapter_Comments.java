package com.example.touristguide.Commnets;

import android.content.Context;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.touristguide.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class Adapter_Comments extends RecyclerView.Adapter<Adapter_Comments.ViewHolder> {


    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView User, Comment, Date;
        CircleImageView Image_user;
        ProgressBar image_progress;
        View view;

        public void setUser(String user) {
            User = view.findViewById(R.id.name_user_comment);
            User.setText(user);
        }

        public void setComment(String comment) {
            Comment = view.findViewById(R.id.txt_comment_style);
            Comment.setText(comment);
        }

        public void setDate(String date) {
            Date = view.findViewById(R.id.txt_comment_date);
            Date.setText(date);
        }

        public void setImage_user(String image_user) {
            Image_user = view.findViewById(R.id.Pic_User_comment);
            image_progress = view.findViewById(R.id.progress_picuser_inpost_comment);
            Picasso.get().load(image_user).into(Image_user, new Callback() {
                @Override
                public void onSuccess() {
                    image_progress.setVisibility(View.GONE);
                }

                @Override
                public void onError(Exception e) {

                }
            });
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
        }


    }

    private Context context;
    private ArrayList<set_Comment> list;

    private FirebaseFirestore firebaseFirestore;
    public Adapter_Comments(Context context, ArrayList<set_Comment> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(this.context).inflate(R.layout.style_comments, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        holder.setComment(list.get(position).getComment());
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

        holder.setDate(dateString);
        firebaseFirestore = FirebaseFirestore.getInstance();


        firebaseFirestore.collection("User").document(list.get(position).getUserID())
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                holder.setImage_user(task.getResult().get("Image_User").toString());
                holder.setUser(task.getResult().get("Username").toString());
            }
        });



    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
