package com.example.touristguide.Places_Api;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.touristguide.R;
import com.hedgehog.ratingbar.RatingBar;

import org.json.JSONArray;
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
import java.util.HashMap;

public class Display_Places_Option extends AppCompatActivity {
    // Component Activity
    private Context context;
    private RecyclerView RCV_PLaces;
    private LinearLayout layout;
    // Component Screen
    private float scale;
    // Component Places
    private HashMap<String, String> Type_of_Places;
    private ToggleButton btn_All_Chip;
    private ArrayList<ToggleButton> ALL_chip = new ArrayList<>();
    private String search;

    private Get_ID_Places get_places = null;
    private final int type_Query_places = 10;
    private final int type_Query_Nearby_places = 15;
    private LayoutAnimationController controller;
    private ArrayList<set_places_option> list;
    private Adapter_places_option adapter;
    private ProgressBar progressBar;
    // Component Current Location .
    private LocationManager locationManager;
    private String provider;
    private Location location;
    private int MY_PERMISSION = 0;
    private  int lastson = 0;
    private int startat = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display__places__option);
        context = this;
        RCV_PLaces = findViewById(R.id.RCV_places_OPtion);
        layout = findViewById(R.id.layout_restaurant);

        progressBar = findViewById(R.id.pr_load_more);

        controller = AnimationUtils.loadLayoutAnimation(context, R.anim.layout);
        Intent intent = getIntent();
        search = intent.getStringExtra("search");

        RCV_PLaces.setLayoutManager(new GridLayoutManager(context, 1));

        if (search.equals("Restaurant")) {
            Type_of_Places = new HashMap<>();
            Type_of_Places.put("Restaurant", "Restaurant");
            Type_of_Places.put("Food", "Food");
            Type_of_Places.put("Night Club", "Night Club");
            Type_of_Places.put("Bar", "Bar");
        }

        if (search.equals("Hotel")) {
            Type_of_Places = new HashMap<>();
            Type_of_Places.put("Lodging", "Lodging");
            Type_of_Places.put("5 Star", "5 Star Hotel");
            Type_of_Places.put("4 Star", "4 Star Hotel");
            Type_of_Places.put("3 Or Less Than", "3 Star Hotel+2 Star Hotel+1 Star Hotel");
        }

        if (search.equals("Hospital")) {
            Type_of_Places = new HashMap<>();
            Type_of_Places.put("Private", "Private hospital");
            Type_of_Places.put("Government", "Government hospital");
        }

        // Add Button Closest To Me ;
        Type_of_Places.put("Closest To Me", search);


        scale = getResources().getDisplayMetrics().density;

        // Create btn All Chip
        btn_All_Chip = new ToggleButton(this);
        Create_Chip(btn_All_Chip, "All", (int) (40 * scale + 0.5f));
        btn_All_Chip.setChecked(true);

        // Create btn All Chip Places .
        for (String Name : Type_of_Places.keySet()) {
            Log.i("hazem", Name);
            Create_Chip(new ToggleButton(this), Name, LinearLayout.LayoutParams.WRAP_CONTENT);
        }

        Toast.makeText(context, search, Toast.LENGTH_SHORT).show();
        // get All Places in Your Country

        Start_Query(type_Query_places, search);  // Start Default Query .

    }

    private void Create_Chip(final ToggleButton chip, String Text, int size) {
        chip.setTextOff(Text);
        chip.setTextOn(Text);
        chip.setChecked(false);
        chip.setInputType(InputType.TYPE_TEXT_VARIATION_NORMAL);
        chip.setGravity(Gravity.CENTER);
        Drawable style_chips = (Drawable) getResources().getDrawable(R.drawable.custom_btn_check_state_resturant);
        chip.setBackground(style_chips);
        chip.setTypeface(Typeface.create("sans-serif-light", Typeface.NORMAL));
        final XmlResourceParser xrp = getResources().getXml((int) R.drawable.custom_color_check_state_resturant);
        try {
            ColorStateList csl = ColorStateList.createFromXml(getResources(), xrp);
            chip.setTextColor(csl);
        } catch (Exception e) {
        }

        chip.setPadding((int) (10 * scale + 0.5f), 0, (int) (10 * scale + 0.5f), 0);
        chip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 startat = 0;
                 lastson = 0;

                if (chip.isChecked()) {
                    String Place = chip.getTextOn().toString();
                    // Reformation ALL Chips
                    reformation_Chips(chip);



                    if (Place.equals("Closest To Me"))
                        Start_Query(type_Query_Nearby_places, search);   // Start Query Nearby places by Current location .
                    else if (Place.equals("All"))
                        Start_Query(type_Query_places, search);  // Start Default Query .
                    else
                        Start_Query(type_Query_places, Type_of_Places.get(Place));   // Start Query Place .


                } else {
                    btn_All_Chip.setChecked(true);
                    //  Get Default Query - > Btn All .
                    Start_Query(type_Query_places, search);
                }
            }
        });
        LinearLayout.LayoutParams Params =
                new LinearLayout.LayoutParams(size, (int) (32 * scale + 0.5f));
        Params.setMargins(0, 0, (int) (5 * scale + 0.5f), 0);
        layout.addView(chip, Params);
    }

    private void reformation_Chips(ToggleButton chip) {
        for (int i = 0; i < layout.getChildCount(); i++) {
            if (layout.getChildAt(i) == chip)
                continue;
            ((ToggleButton) layout.getChildAt(i)).setChecked(false);
        }
    }

    private void Query_Places(String Places, double lat, double lng) {

        // get All Places in Your Country
        StringBuilder stringBuilder = new StringBuilder("https://maps.googleapis.com/maps/api/place/textsearch/json?");
        stringBuilder.append("query=" + Places + "+in+Amman");
        stringBuilder.append("&key=" + getResources().getString(R.string.Google_Places_Key));
        String Url = stringBuilder.toString();
        if (get_places != null)
            get_places.cancel(true);

        get_places = new Get_ID_Places(new For_Chick() {
            @Override
            public void finished(boolean chick) {
                Log.i("finished","finished : True");
            }
        });
        get_places.execute(Url);


        Toast.makeText(context, Places, Toast.LENGTH_SHORT).show();
        Log.i("Qury", stringBuilder.toString());

    }

    private void Query_Nearby_places(double lat, double lng) {

        // get All Places in Your Country
        StringBuilder stringBuilder = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        stringBuilder.append("location=" + lat + "," + lng);
        stringBuilder.append("&radius=" + 1000);
        stringBuilder.append("&keyword=" + search);
        stringBuilder.append("&key=" + getResources().getString(R.string.Google_Places_Key));
        String Url = stringBuilder.toString();
        if (get_places != null)
            get_places.cancel(true);

        get_places = new Get_ID_Places(new For_Chick() {
            @Override
            public void finished(boolean chick) {

            }
        });
        get_places.execute(Url);

        Log.i("Qury", stringBuilder.toString());
        Log.i("location", "lat : " + lat + "lng : " + lng);
    }

    private void Start_Query(int type, String Place) {
        //Get Coordinates
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        provider = locationManager.getBestProvider(new Criteria(), false);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {


            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.INTERNET,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_NETWORK_STATE,
                    Manifest.permission.SYSTEM_ALERT_WINDOW,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE


            }, MY_PERMISSION);
        }
        locationManager.requestLocationUpdates(provider, 0, 0, new MYLocationListener());
        location = locationManager.getLastKnownLocation(provider);
        if (location != null) {
            double lat = location.getLatitude();
            double lng = location.getLongitude();
            if (type == type_Query_places)
                Query_Places(Place, lat, lng);
            else if (type == type_Query_Nearby_places)
                Query_Nearby_places(lat, lng); // Start Query Nearby places by Current location .
        } else
            Log.e("TAG", "No Location");
    }

    public class MYLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {

        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    }

    private  interface For_Chick{

        void finished(boolean chick);
    }
    private  class Get_ID_Places extends AsyncTask<String, String, String> {

        private String url;
        private InputStream Is;
        private BufferedReader bufferedReader;
        private StringBuilder stringBuilder;
        private String data;
        private double lat, lng;

        //  Component RCV


        Get_ID_Places(For_Chick forChick) {
            this.lat = lat;
            this.lng = lng;

            this.forChick=forChick;

            // Clear Array List .
            list = new ArrayList<>();
            adapter = new Adapter_places_option(context, list);
            RCV_PLaces.setAdapter(adapter);

        }

        private For_Chick forChick;

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
                JSONObject ParentObject = new JSONObject(s);
                final JSONArray result = ParentObject.getJSONArray("results");

                if (result.length() > 5)
                    lastson = 5;
                else lastson = result.length();


                RCV_PLaces.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                    }

                    @Override
                    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        Boolean canread = !recyclerView.canScrollVertically(1);
                        Log.d("LASTONE", lastson + " / " + startat);
                        if (canread && dy > 0) {
                            //RCV_PLaces.smoothScrollToPosition(lastson);
                            loadmore(result, lastson);
                            Toast.makeText(getBaseContext(), "Can Scroll : " + dy, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                int l = 0;
                for (int i = 0; i < lastson; i++) {
                    JSONObject Parent = result.getJSONObject(i);

                    if (Parent.has("place_id")) { // get All Places in Your Country
                        String ID = Parent.getString("place_id");
                        StringBuilder Str_Url = new StringBuilder("https://maps.googleapis.com/maps/api/place/details/json?");
                        Str_Url.append("placeid=" + ID);
                        Str_Url.append("&fields=name," +
                                "formatted_address," +
                                "geometry," +
                                "rating," +
                                "user_ratings_total," +
                                "opening_hours," +
                                "price_level," +
                                "photos");
                        Str_Url.append("&key=" + getResources().getString(R.string.Google_Places_Key));
                        String Url = Str_Url.toString();
                        Get_Information_Places informationPlaces = new Get_Information_Places(list, adapter);
                        RCV_PLaces.setLayoutAnimation(controller);
                        RCV_PLaces.scheduleLayoutAnimation();
                        informationPlaces.execute(Url);
                        Log.i("moh", Str_Url.toString());
                    }
                    l = i+1;

                    //else
                    //{Toast.makeText(context,"Error",Toast.LENGTH_SHORT).show();}

                }


                lastson = l ;


            } catch (JSONException e) {
                Toast.makeText(context, "Q1 : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }



        private void loadmore(JSONArray res, int lastone) {
            progressBar.setVisibility(View.VISIBLE);
            try {
                Toast.makeText(context, "last = " + lastone, Toast.LENGTH_SHORT).show();
                if ((res.length() - lastone > 5))
                    startat = lastone +5;
                else
                    startat = res.length();

                int k = 0;
                for (int i = lastone; i < startat ; i++) {
                    JSONObject Parent = res.getJSONObject(i);

                    if (Parent.has("place_id")) { // get All Places in Your Country
                        String ID = Parent.getString("place_id");
                        StringBuilder Str_Url = new StringBuilder("https://maps.googleapis.com/maps/api/place/details/json?");
                        Str_Url.append("placeid=" + ID);
                        Str_Url.append("&fields=name," +
                                "formatted_address," +
                                "geometry," +
                                "rating," +
                                "user_ratings_total," +
                                "opening_hours," +
                                "price_level," +
                                "photos");
                        Str_Url.append("&key=" + getResources().getString(R.string.Google_Places_Key));
                        String Url = Str_Url.toString();
                        Get_Information_Places informationPlaces = new Get_Information_Places(list, adapter );
                        informationPlaces.execute(Url);
                        Log.i("moh", Str_Url.toString());
                        if (i >= startat)
                            progressBar.setVisibility(View.GONE);
                    }


                    //else
                    //{Toast.makeText(context,"Error",Toast.LENGTH_SHORT).show();}
                }
                lastson = startat;


            } catch (JSONException e) {
                Toast.makeText(context, "Q1 : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }

        }


    }


    private class Get_Information_Places extends AsyncTask<String, String, String> {
        private String url;
        private InputStream Is;
        private BufferedReader bufferedReader;
        private StringBuilder stringBuilder;
        private String data;

        //  Component RCV
        private ArrayList<set_places_option> list;
        private Adapter_places_option adapter;

        Get_Information_Places(ArrayList<set_places_option> list, Adapter_places_option adapter) {

            // Clear Array List .
            this.list = list;
            this.adapter = adapter;
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


                String address = "kasaji";

                if (result.has("formatted_address"))
                    address = result.getString("formatted_address");
                Log.i("abood", address);

                String Name = "No.";
                if (result.has("name"))
                    Name = result.getString("name");

                JSONObject location_object = result.getJSONObject("geometry").getJSONObject("location");
                Location location = new Location("");
                location.setLatitude(location_object.getDouble("lat"));
                location.setLongitude(location_object.getDouble("lng"));
                String IS_open = "unknown . ";
                if (result.has("opening_hours"))
                    IS_open = (result.getJSONObject("opening_hours")
                            .getString("open_now").equals("true")) ? "Opened" : "Closed";
                String photos = "";
                if (result.has("photos"))
                    photos = result.getJSONArray("photos").getJSONObject(0).getString("photo_reference");
                int price_level = 0;
                if (result.has("price_level"))
                    price_level = result.getInt("price_level");
                double rating = 0.0;
                if (result.has("rating"))
                    rating = result.getDouble("rating");
                int user_rating = 0;
                if (result.has("user_ratings_total"))
                    user_rating = result.getInt("user_ratings_total");
                list.add(new set_places_option(Name, address, IS_open, photos, price_level, user_rating, rating, location));
                Log.i("basil", Name);

                adapter.notifyDataSetChanged();
            } catch (JSONException e) {
                Toast.makeText(context, "Q2 : ", Toast.LENGTH_SHORT).show();
                Log.i("suh", e.toString());
                e.printStackTrace();
            }
        }
    }
}
