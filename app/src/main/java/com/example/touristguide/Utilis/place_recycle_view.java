package com.example.touristguide.Utilis;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.touristguide.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class place_recycle_view extends RecyclerView.Adapter<place_recycle_view.ViewHolder> {

    private List<Places> Place_List;
    private Context context;


    public place_recycle_view(List<Places> Place_list) {
        this.Place_List = Place_list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.place_horizontal, parent, false);


        context = parent.getContext();

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setIsRecyclable(false);

        holder.setName_Place(Place_List.get(position).getName());
        holder.setPlace_Image(Place_List.get(position).getImage());


    }


    @Override
    public int getItemCount() {
        return Place_List.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView Name_Place;
        private ImageView Place_Image;
        private View mView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mView = itemView;
        }

        public void setName_Place(String name_place) {

            Name_Place = mView.findViewById(R.id.txt_hoz_Place_Name);
            Name_Place.setText(name_place);

        }

        public void setPlace_Image(String Image_Url) {
            Place_Image = mView.findViewById(R.id.img_hoz_place);

            Picasso.get().load(Image_Url).resizeDimen(R.dimen.image_size ,R.dimen.image_size).into(Place_Image);


        }
    }
}
