package com.example.touristguide.Utilis;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.example.touristguide.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;


public class GridVideoAdpter extends ArrayAdapter<String>{

    private Context mContext;
    private LayoutInflater mInflater;
    private int layoutResource;
    private String mAppend;
    private ArrayList<String> imgURLs;

    public GridVideoAdpter(Context context, int layoutResource, String append, ArrayList<String> imgURLs) {
        super(context, layoutResource, imgURLs);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mContext = context;
        this.layoutResource = layoutResource;
        mAppend = append;
        this.imgURLs = imgURLs;

    }

    private static class ViewHolder{
        ImageView image;
    }
    @NonNull
    @Override
    public View getView(int position,  View convertView, @NonNull ViewGroup parent) {

        /*
        Viewholder build pattern (Similar to recyclerview)
         */


        final ViewHolder holder;
        if(convertView == null){
            convertView = mInflater.inflate(layoutResource, parent, false);
            holder = new ViewHolder();
            holder.image =  convertView.findViewById(R.id.videoshare);

            //Toast.makeText(getContext(),imgURLs.get(position),Toast.LENGTH_SHORT).show();
          //  holder.image.setImageURI(Uri.parse("file:/"+imgURLs.get(position)));

            ImageLoader imageLoader = ImageLoader.getInstance();
            imageLoader.displayImage("file:/" + imgURLs.get(position), holder.image);


            convertView.setTag(holder);

        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }

        String imgURL = getItem(position);




        return convertView;
    }

}