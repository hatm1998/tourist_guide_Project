package com.example.touristguide.CityActivity.sightseeing;

import android.content.Context;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;

import com.example.touristguide.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Adapter_sightseeing extends PagerAdapter {

    private Context context;
    private ArrayList<Places> List;
    private LayoutInflater inflater;
    public Adapter_sightseeing(Context context , ArrayList<Places> List){
        this.context=context;
        this.List=List;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
    @Override
    public int getCount() {
        return List.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, final int position) {
        View inflate = inflater.inflate(R.layout.place_horizontal, view, false);

        final TextView txt_place_name=inflate.findViewById(R.id.txt_hoz_Place_Name);
        final ImageView img_place=inflate.findViewById(R.id.img_hoz_place);

        txt_place_name.setText(List.get(position).getName());
        Picasso.get().load(List.get(position).getImage()).resizeDimen(R.dimen.image_size,R.dimen.image_size) .into(img_place,new Callback() {
            @Override
            public void onSuccess() {
             //   progress_city.setVisibility(View.GONE);
            }

            @Override
            public void onError(Exception e) {

            }

        });


        view.addView(inflate, 0);

        return inflate;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }
}
