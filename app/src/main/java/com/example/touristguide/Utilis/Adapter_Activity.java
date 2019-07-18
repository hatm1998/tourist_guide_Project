package com.example.touristguide.Utilis;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.touristguide.Activity.setGovernorates;
import com.example.touristguide.R;

import java.util.ArrayList;

public class Adapter_Activity extends RecyclerView.Adapter<Adapter_Activity.ViewHolder> {


    public  static class ViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView image;
        private TextView Name;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            image=itemView.findViewById(R.id.img_cardactivity);
            Name=itemView.findViewById(R.id.txtname_cardactivity);

        }
    }
         private ArrayList<setGovernorates> List;
    private FragmentActivity Activity;
         public Adapter_Activity(FragmentActivity Activity, ArrayList<setGovernorates> List)
         {
             this.Activity=Activity;
           this.List=List;
         }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(Activity).inflate(R.layout.card_activity,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final setGovernorates pos=List.get(position);
        holder.image.setImageResource(pos.getImage());
        holder.Name.setText(pos.getName());
    }

    @Override
    public int getItemCount() {
        return List.size();
    }




}
