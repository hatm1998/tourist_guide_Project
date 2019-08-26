package com.example.touristguide.Places_Api;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.QuickContactBadge;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.touristguide.Event.Event_Activity.silder_Image.adapter_Display_Image_Slider;
import com.example.touristguide.Event.Event_Activity.silder_Image.set_Image;
import com.example.touristguide.R;
import com.hedgehog.ratingbar.RatingBar;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;

public class Adapter_places_option  extends RecyclerView.Adapter<Adapter_places_option.ViewHolder> {

    public static Context context;
    private ArrayList<set_places_option> list;
    public  static class ViewHolder extends RecyclerView.ViewHolder{
       private TextView name_Resturant,address_Resturant,state_Resturant,user_rating_Resturant,level_Resturant,Rating_Resturant;
        private ImageView Img_Resturant;
        private RatingBar RatingBar_Resturant;
        private  ProgressBar  Bar_Resturant;
        private CardView cardView ;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name_Resturant =  itemView.findViewById(R.id.txt_name_Resturant);
            address_Resturant =  itemView.findViewById(R.id.txtaddress_Resturant);
            state_Resturant =  itemView.findViewById(R.id.txt_state_Resturant);
            user_rating_Resturant =  itemView.findViewById(R.id.user_rating_Resturant);
            level_Resturant =  itemView.findViewById(R.id.level_Resturant);
            Rating_Resturant =  itemView.findViewById(R.id.txtrating_Resturant);
            Img_Resturant =  itemView.findViewById(R.id.img_Resturant);
            Bar_Resturant =  itemView.findViewById(R.id.progressBar_Resturant);
            cardView=itemView.findViewById(R.id.card__Resturant);
            RatingBar_Resturant = (RatingBar) itemView.findViewById(R.id.ratingBar_Resturant);



        }


    }



    public Adapter_places_option(Context context , ArrayList<set_places_option> list){
        this.context=context;
        this.list=list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.card_style_places_option,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final set_places_option pos=list.get(position);
        holder.RatingBar_Resturant.setStarEmptyDrawable( context.getResources().getDrawable(R.mipmap.star_empty));
        holder.RatingBar_Resturant.setStarHalfDrawable(context.getResources().getDrawable(R.mipmap.star_half));
        holder.RatingBar_Resturant.setStarFillDrawable(context.getResources().getDrawable(R.mipmap.star_full));
        holder.RatingBar_Resturant.setStarCount(5);
        holder.RatingBar_Resturant.setStar((float) pos.getRating());
        holder.RatingBar_Resturant.halfStar(true);
        holder.RatingBar_Resturant.setmClickable(true);
        holder.RatingBar_Resturant.setStarImageWidth(120f);
        holder.RatingBar_Resturant.setStarImageHeight(60f);
        // -----------------------------------------------------------

        String Path_Photo=null;
        if(!pos.getPhoto().equals(""))
         Path_Photo="https://maps.googleapis.com/maps/api/place/photo?" +
                "maxwidth=1000" +
                "&photoreference="+pos.getPhoto()+
                "&key="+context.getResources().getString(R.string.Google_Places_Key);
        else
            Path_Photo="https://firebasestorage.googleapis.com/v0/b/touristguide-81502.appspot.com/" +
                    "o/no%20photo.png?alt=media&token=d8c101e1-6066-4c15-bc8b-a0990c858fdd";
        Picasso.get().load(Path_Photo)
                .resizeDimen(R.dimen.image_size,
                R.dimen.image_size) .into(holder.Img_Resturant,new Callback() {
            @Override
            public void onSuccess() {
                holder.Bar_Resturant.setVisibility(View.GONE);
            }

            @Override
            public void onError(Exception e) {

            }

        });

         final  ArrayList<set_Image> images =new ArrayList<>();
          images.add(new set_Image(Path_Photo));
        holder.Img_Resturant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // show Image As dialog .
                final Dialog dialog=new Dialog(context);
                dialog.setContentView(R.layout.style_display_image_as_dialog);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                ViewPager pager=dialog.findViewById(R.id.Pager_dialog_display_img);
                pager.setAdapter(new adapter_Display_Image_Slider(context ,images ));
                pager.setCurrentItem(position);
                ImageView btn_cross=dialog.findViewById(R.id.btn_Cross_dialog_displayimg);
                btn_cross.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?q=loc:%f,%f", pos.getLocation().getLatitude(),pos.getLocation().getLongitude());
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                context.startActivity(intent);
            }
        });
        holder.name_Resturant.setText(pos.getName());


        holder.address_Resturant.setText(pos.getAddress());
        holder.state_Resturant.setText(pos.getState()+"");
        holder.user_rating_Resturant.setText("User Rating : "+pos.getUser_rating());
        holder.level_Resturant.setText("Level : "+pos.getLevel());
        holder.Rating_Resturant.setText(pos.getRating()+"");
    }


    @Override
    public int getItemCount() {
        return list.size();
    }



}
