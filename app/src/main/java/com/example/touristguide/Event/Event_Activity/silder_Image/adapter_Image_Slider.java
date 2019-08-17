package com.example.touristguide.Event.Event_Activity.silder_Image;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.touristguide.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class adapter_Image_Slider extends PagerAdapter {


    private ArrayList<set_Image> imageModelArrayList;
    private LayoutInflater inflater;
    private Context context;
    public adapter_Image_Slider(Context context, ArrayList<set_Image> imageModelArrayList) {
        this.context = context;
        this.imageModelArrayList = imageModelArrayList;
        inflater = LayoutInflater.from(context);

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return imageModelArrayList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, final int position) {
        View imageLayout = inflater.inflate(R.layout.slider_image_city, view, false);

        assert imageLayout != null;
        final ImageView Img_City = (ImageView) imageLayout.findViewById(R.id.img_slider_city);
        final ProgressBar progress_city=imageLayout.findViewById(R.id.progress_sliding_img_City_event_page);


    //    imageView.setImageResource(imageModelArrayList.get(position).getImage_drawable());
        Picasso.get().load(imageModelArrayList.get(position).getImage_drawable()).into(Img_City,new Callback() {
            @Override
            public void onSuccess() {
                progress_city.setVisibility(View.GONE);
            }

            @Override
            public void onError(Exception e) {

            }

        });

        Img_City.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // show Image As dialog .
                final Dialog dialog=new Dialog(context);
                dialog.setContentView(R.layout.style_display_image_as_dialog);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                ViewPager pager=dialog.findViewById(R.id.Pager_dialog_display_img);
                pager.setAdapter(new adapter_Display_Image_Slider(context ,imageModelArrayList ));
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

        view.addView(imageLayout, 0);

        return imageLayout;
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