package com.example.touristguide.Category;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.example.touristguide.R;

import java.util.List;

public class category_adapter extends BaseAdapter {
    Context context;
    List<Integer> Imgcate;
    String [] namecate;
    LayoutInflater inflater;

    public category_adapter(Context context, List<Integer> Imgcate, String [] namecate) {
        this.context = context;
        this.Imgcate = Imgcate;
        this.namecate = namecate;

        inflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return namecate.length;
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
        final View view = inflater.inflate(R.layout.category_item, null);

        ImageView imageView = view.findViewById(R.id.category_item_img);
        imageView.setImageResource(Imgcate.get(position));

       final TextView textView = view.findViewById(R.id.category_item_text);
        textView.setText(namecate[position]);

        CardView cardView = view.findViewById(R.id.CateCategory);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context , ads_category.class);
                intent.putExtra("Cate" ,namecate[position] );
                context.startActivity(intent);
            }
        });



        return view;

    }


}
