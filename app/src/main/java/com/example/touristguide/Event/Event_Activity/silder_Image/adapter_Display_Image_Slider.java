package com.example.touristguide.Event.Event_Activity.silder_Image;

        import android.content.Context;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
        import android.widget.ProgressBar;

        import androidx.viewpager.widget.PagerAdapter;

import com.example.touristguide.R;
import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class adapter_Display_Image_Slider extends PagerAdapter {


    private ArrayList<set_Image> imageModelArrayList;
    private LayoutInflater inflater;
    private Context context;
    public adapter_Display_Image_Slider(Context context, ArrayList<set_Image> imageModelArrayList) {
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
        View imageLayout = inflater.inflate(R.layout.sliding_images_event_page_layout, view, false);

        assert imageLayout != null;
        final PhotoView Img = (PhotoView) imageLayout.findViewById(R.id.Img_Slide);
        final ProgressBar progress_city=imageLayout.findViewById(R.id.progress_sliding_images_event_page);

        Picasso.get().load(imageModelArrayList.get(position).getImage_drawable()).into(Img,new Callback() {
            @Override
            public void onSuccess() {
                progress_city.setVisibility(View.GONE);
            }

            @Override
            public void onError(Exception e) {

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