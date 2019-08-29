package com.example.touristguide.Places_Api;

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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.touristguide.Event.Event_Activity.silder_Image.set_Image;
import com.example.touristguide.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

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
    private ArrayList<String> Type_of_Places;
    private ToggleButton btn_All_Chip;
    private String search;
    private String CityID;

    private Get_ID_Places get_places = null;
    private final int type_Query_places = 10;
    private final int type_Query_Nearby_places = 15;
    private LayoutAnimationController controller;
    private ProgressBar progressBar;
    // Component Current Location .

    private LocationManager locationManager;
    private String provider;
    public static  Location position_location;
    private int MY_PERMISSION = 0;


    private ArrayList<set_places_option> list;
    private Adapter_places_option adapter;

    private  ArrayList<Get_Information_Places> Tasks =new ArrayList<>();

    // FireBase

    private FirebaseFirestore firebaseFirestore;
    private DocumentReference Ref;
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
        CityID=intent.getStringExtra("City");
        Toast.makeText(context,CityID,Toast.LENGTH_SHORT).show();

        list = new ArrayList<>();
        adapter = new Adapter_places_option(context, list);
        RCV_PLaces.setAdapter(adapter);

        firebaseFirestore=FirebaseFirestore.getInstance();
        Ref=  firebaseFirestore.collection("Cities").document(CityID);

        RCV_PLaces.setLayoutManager(new LinearLayoutManager(context));

        if (search.equals("Restaurant")) {
            Type_of_Places= new ArrayList<>();
            Type_of_Places.add("Restaurant");
            Type_of_Places.add("Food");
         //   Type_of_Places.add("Night Club");
           // Type_of_Places.add("Bar");
        }

        if (search.equals("Hotels")) {
            Type_of_Places = new ArrayList<>();
            Type_of_Places.add("Lodging");
            Type_of_Places.add("5 Star");
            Type_of_Places.add("4 Star");
            Type_of_Places.add("3 Or Less Than");
        }

        if (search.equals("Hospital")) {
            Type_of_Places = new ArrayList<>();
            Type_of_Places.add("Private");
            Type_of_Places.add("Government");
        }

        // Add Button Closest To Me ;
        Type_of_Places.add("Closest To Me");


        scale = getResources().getDisplayMetrics().density;


        // Create btn All Chip
        btn_All_Chip = new ToggleButton(this);
        Create_Chip(btn_All_Chip, "All", (int) (40 * scale + 0.5f));
        btn_All_Chip.setChecked(true);

        // Create btn All Chip Places .
        for (String Name : Type_of_Places) {
            Create_Chip(new ToggleButton(this), Name, LinearLayout.LayoutParams.WRAP_CONTENT);
        }

        Toast.makeText(context, search, Toast.LENGTH_SHORT).show();
        // get All Places in Your Country

         Start_Query(type_Query_places, search);  // Start Default Query .
    }

    private void Create_Chip(final ToggleButton chip , String Text, int size) {
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

                if (chip.isChecked()) {
                    String Place = chip.getTextOn().toString();
                    // Reformation ALL Chips
                    reformation_Chips(chip);


                    if (Place.equals("Closest To Me"))
                        Start_Query(type_Query_Nearby_places,"");   // Start Query Nearby places by Current location .
                    else if (Place.equals("All"))
                        get_ID_places(search);  // Start Default Query .
                    else
                    {
                        if(Place.equals("Night Club"))
                            Place="Night_Club";
                        else if(Place.equals("5 Star"))
                            Place="five_star_hotel";
                        else if(Place.equals("4 Star"))
                            Place="four_star_hotel";
                        else if(Place.equals("3 Or Less Than"))
                            Place="Less_star_hotel";
                        else if(Place.equals("Private"))
                            Place="Private_hospital";
                        else if(Place.equals("Government"))
                            Place="Government_hospital";
                        Start_Query(type_Query_places,Place); } // Start Query Place .


                } else {
                    btn_All_Chip.setChecked(true);
                    //  Get Default Query - > Btn All .
                    Start_Query(type_Query_places,search);   // Start Default Query .
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

    private void Query_Nearby_places() {

        // get All Places in Your Country
        StringBuilder stringBuilder = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        stringBuilder.append("location=" + position_location.getLatitude() + "," + position_location.getLongitude());
        stringBuilder.append("&radius=" + 1000);
        stringBuilder.append("&keyword=" + search);
        stringBuilder.append("&key=" + getResources().getString(R.string.Google_Places_Key));
        String Url = stringBuilder.toString();
        if (get_places != null)
            get_places.cancel(true);

        get_places = new Get_ID_Places();
        get_places.execute(Url);

    }

    private void Start_Query(int Type ,String place) {
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
        position_location = locationManager.getLastKnownLocation(provider);
        if (position_location != null) {

            if (Type == (type_Query_places)) {
                get_ID_places(place);
            } else if (Type == (type_Query_Nearby_places))
                Query_Nearby_places(); // Start Query Nearby places by Current location .
        }else
            Log.e("TAGS", "No Location");
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

    private  class Get_ID_Places extends AsyncTask<String, String, String> {

        private String url;
        private InputStream Is;
        private BufferedReader bufferedReader;
        private StringBuilder stringBuilder;
        private String data;


        Get_ID_Places() {

//            Clear Array List .
            Rest_Rec_view();
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
        protected void onPostExecute(String s)
        {
            try {
                final JSONObject ParentObject = new JSONObject(s);
                final JSONArray result = ParentObject.getJSONArray("results");
                for (int i = 0; i < result.length(); i++) {
                    JSONObject Parent = result.getJSONObject(i);
                    if (Parent.has("place_id")) { // get All Places in Your Country
                        String ID = Parent.getString("place_id");
                        Log.i("ID_Places", ID);

                        StringBuilder Str_Url = new StringBuilder("https://maps.googleapis.com/maps/api/place/details/json?");
                        Str_Url.append("placeid=" + ID);
                        Str_Url.append("&fields=name," +
                                "formatted_address," +
                                "formatted_phone_number,"+
                                "website,"+
                                "geometry," +
                                "rating," +
                                "user_ratings_total," +
                                "opening_hours," +
                                "price_level," +
                                "photos");
                        Str_Url.append("&key=" + getResources().getString(R.string.Google_Places_Key));
                        String Url = Str_Url.toString();
                        Get_Information_Places places = new Get_Information_Places();
                        places.execute(Url);
                        Tasks.add(places);
                    }
                }


            }catch (JSONException e) {
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

                String address = "UnKnown";
                if (result.has("formatted_address"))
                    address = result.getString("formatted_address");
                Log.i("abood", address);

                String Name = "No.";
                if (result.has("name"))
                    Name = result.getString("name");
                String Phone="UnKnown";
                if (result.has("formatted_phone_number"))
                    Phone = result.getString("formatted_phone_number");

                String Website="UnKnown";
                if (result.has("website"))
                    Website = result.getString("website");

                JSONObject location_object = result.getJSONObject("geometry").getJSONObject("location");
                Location location = new Location("");
                location.setLatitude(location_object.getDouble("lat"));
                location.setLongitude(location_object.getDouble("lng"));
                String IS_open = "unknown . ";
                if (result.has("opening_hours"))
                    IS_open = (result.getJSONObject("opening_hours")
                            .getString("open_now").equals("true")) ? "Opened" : "Closed";
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

                int price_level = 0;
                if (result.has("price_level"))
                    price_level = result.getInt("price_level");
                double rating = 0.0;
                if (result.has("rating"))
                    rating = result.getDouble("rating");
                int user_rating = 0;
                if (result.has("user_ratings_total"))
                    user_rating = result.getInt("user_ratings_total");
                list.add(new set_places_option(Name, address, IS_open, photos, Phone, Website, price_level, user_rating, rating, location));
                adapter.notifyDataSetChanged();
            } catch (JSONException e) {
                Toast.makeText(context, "Q2 : ", Toast.LENGTH_SHORT).show();
                Log.i("suh", e.toString());
                e.printStackTrace();
            }
        }
    }

    private   void Rest_Rec_view(){

        Log.i("onRest",Tasks.size()+"    ");
        for(Get_Information_Places x : Tasks)
        {
            x.cancel(true);
        }
     Tasks.clear();
     Log.i("onRest",Tasks.size()+"   ");
    //  Clear Array List .
    list.clear();
    adapter.notifyDataSetChanged();
}

    private void get_ID_places(String Place){

      //  Clear Array List .
        Rest_Rec_view();

        Ref.collection("ID_Places").document(Place).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists())
                {
                    ArrayList<String> ID=( ArrayList<String>) documentSnapshot.get("ID_Places");


                    for( String i : ID) {
                        StringBuilder Str_Url = new StringBuilder("https://maps.googleapis.com/maps/api/place/details/json?");
                        Str_Url.append("placeid=" + i);
                        Str_Url.append("&fields=name," +
                                "formatted_address," +
                                "formatted_phone_number,"+
                                "website,"+
                                "geometry," +
                                "rating," +
                                "user_ratings_total," +
                                "opening_hours," +
                                "price_level," +
                                "photos");
                        Str_Url.append("&key=" + getResources().getString(R.string.Google_Places_Key));
                        String Url = Str_Url.toString();

                        Get_Information_Places places = new Get_Information_Places();
                        places.execute(Url);
                        Tasks.add(places);
                    }
                }
                else
                    Toast.makeText(context," Not Found ! ",Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context,"Error : "+e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

    }

    private  void  Add_ID_places(ArrayList<String> ID ,String Places) {
        HashMap<String ,ArrayList<String>> data=new HashMap<>();
        data.put("ID_Places",ID);

        firebaseFirestore.collection("Cities").document("Ma'an")
                .collection("ID_Places").document(Places).set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(context,"Done FireBase ",Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context,"Error : "+e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}
