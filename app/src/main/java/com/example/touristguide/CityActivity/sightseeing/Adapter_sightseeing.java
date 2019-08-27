package com.example.touristguide.CityActivity.sightseeing;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.touristguide.Event.Event_Activity.silder_Image.adapter_Display_Image_Slider;
import com.example.touristguide.Event.Event_Activity.silder_Image.set_Image;
import com.example.touristguide.Places_Api.Adapter_places_option;
import com.example.touristguide.Places_Api.set_places_option;
import com.example.touristguide.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;

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
        String Photo;
        if(List.get(position).getImage().equals(""))
            Photo="https://firebasestorage.googleapis.com/v0/b/touristguide-81502.appspot.com/" +
                    "o/no%20photo.png?alt=media&token=d8c101e1-6066-4c15-bc8b-a0990c858fdd";
           else
            Photo=List.get(position).getImage();

        Picasso.get().load(List.get(position).getImage()).resizeDimen(R.dimen.image_size,R.dimen.image_size) .into(img_place,new Callback() {
            @Override
            public void onSuccess() {
             //   progress_city.setVisibility(View.GONE);
            }

            @Override
            public void onError(Exception e) {

            }

        });
        img_place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // get All Image -> city
                StringBuilder Str_Url = new StringBuilder("https://maps.googleapis.com/maps/api/place/details/json?");
                Str_Url.append("placeid=" + List.get(position).getID_Place());
                Str_Url.append("&fields=photos");
                        Str_Url.append("&key=" + context.getResources().getString(R.string.Google_Places_Key));
                String Url = Str_Url.toString();
                Get_All_Image get_city = new Get_All_Image(List.get(position).getLocation());
                get_city.execute(Url);
            }
        });

        view.addView(inflate, 0);

        return inflate;
    }
    private class Get_All_Image extends AsyncTask<String, String, String> {
        private String url;
        private InputStream Is;
        private BufferedReader bufferedReader;
        private StringBuilder stringBuilder;
        private String data;

        private  Location location;

        Get_All_Image(Location location){
            this.location=location;
        }

        @Override
        protected String doInBackground(String... strings) {
            url = strings[0];
            try {
                URL MyUrl = new URL(url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) MyUrl.openConnection();
                httpURLConnection.connect();
                Is = httpURLConnection.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(Is));

                String Line = "";
                stringBuilder = new StringBuilder();
                while ((Line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(Line);
                }
                data = stringBuilder.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return data;
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject Parent = new JSONObject(s);
                JSONObject result = Parent.getJSONObject("result");

                ArrayList<set_Image> photos =new ArrayList<>();
                if (result.has("photos")){

                    for( int x=0;x<result.getJSONArray("photos").length();x++)
                    {
                        photos.add(new set_Image("https://maps.googleapis.com/maps/api/place/photo?" +
                                "maxwidth=1000" +
                                "&photoreference="+ result.getJSONArray("photos")
                                .getJSONObject(x).getString("photo_reference")+
                                "&key="+context.getResources().getString(R.string.Google_Places_Key)));
                    }
                }
                final Dialog dialog=new Dialog(context);
                dialog.setContentView(R.layout.style_display_image_as_dialog_city);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                ViewPager pager=dialog.findViewById(R.id.Pager_dialog_display_img_city);
                pager.setAdapter(new adapter_Display_Image_Slider(context ,photos ));
                ImageButton btn_Go_Location=dialog.findViewById(R.id.btn_Go_Location);
                btn_Go_Location.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                       String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?q=loc:%f,%f", location.getLatitude(),location.getLongitude());
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                        context.startActivity(intent);
                    }
                });
                dialog.show();

            } catch (JSONException e) {
                Log.i("allimage",e.getMessage());
                e.printStackTrace();
            }
        }
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
