package com.example.touristguide.Utilis;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.touristguide.Activity.setGovernorates;
import com.example.touristguide.CityActivity.City_Main;
import com.example.touristguide.Navigation_Drawer;
import com.example.touristguide.R;
import com.jgabrielfreitas.core.BlurImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Adapter_Activity extends BaseAdapter {

    Context context;
    List<String> Imgcate;
    List<String> namecate;
    LayoutInflater inflater;
    List<String> AdapterID;

    public Adapter_Activity(Context context, List<String> Imgcate, List<String> namecate, List<String> ApdaterID) {
        this.context = context;
        this.Imgcate = Imgcate;
        this.namecate = namecate;
        this.AdapterID = ApdaterID;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return namecate.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final View view = inflater.inflate(R.layout.card_activity, null);

        ImageView imageView = view.findViewById(R.id.img_cardactivity);

        Picasso.get().load(Imgcate.get(position)).into(imageView);
        final TextView textView = view.findViewById(R.id.txtname_cardactivity);
        textView.setText(namecate.get(position));

        final CardView cardView = view.findViewById(R.id.CV_city);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context.getApplicationContext(), City_Main.class);
                intent.putExtra("Ads_id", AdapterID.get(position));
                context.startActivity(intent);
            }
        });


        return view;

    }


}
