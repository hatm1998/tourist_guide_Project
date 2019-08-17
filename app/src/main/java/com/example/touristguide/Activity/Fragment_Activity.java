package com.example.touristguide.Activity;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.touristguide.R;
import com.example.touristguide.Utilis.Adapter_Activity;
import com.example.touristguide.weather.Common.Common;
import com.example.touristguide.weather.Helper.Helper;
import com.example.touristguide.weather.Model.OpenWeatherMap;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import de.hdodenhof.circleimageview.CircleImageView;

public class Fragment_Activity extends Fragment   {

    private GridView Rev_activity;
    private ArrayList<setGovernorates> list;
    private Adapter_Activity adapter_activity ;
    private FirebaseFirestore firebaseFirestore;

    // Component Weather Information
    private CircleImageView img_ic_status;
    private TextView txtLastUpdate,txtDescription,txtHumidity,txtCelsius;
    private Button btnrefresh;

    // Component Current Location .
    private LocationManager locationManager;
    private String provider;
    private Location location;
    private double lat, lng;
    private  int MY_PERMISSION = 0;
   private  OpenWeatherMap openWeatherMap ;

    public Fragment_Activity() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_activity, container, false);
        img_ic_status=view.findViewById(R.id.img_ic_status__Activity_page);
        txtLastUpdate=view.findViewById(R.id.txtLastUpdate_Activity_page);
        txtDescription=view.findViewById(R.id.txtDescription_Activity_page);
        txtHumidity=view.findViewById(R.id.txtHumidity_Activity_page);
        txtCelsius=view.findViewById(R.id.txtCelsius_Activity_page);
        btnrefresh=view.findViewById(R.id.btn_refresh_location_Activity_page);
        Rev_activity= (view.findViewById(R.id.Rev_Activity));

        openWeatherMap = new OpenWeatherMap();

      //  get current location
        Get_Current_Location();



        // Refresh location .
        btnrefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  Animation anim = AnimationUtils.loadAnimation(getActivity(),
                        R.anim.click_btn_refresh);
                btnrefresh.setAnimation(anim);
                Get_Current_Location();
            }
        });


        firebaseFirestore = FirebaseFirestore.getInstance();

        list=new ArrayList<>();



        final List <String> img = new ArrayList();
        final List<String> cityname  =  new ArrayList<>();
        final List <String> ID = new ArrayList();
      Query query =  firebaseFirestore.collection("Cities");

      query.addSnapshotListener( new EventListener<QuerySnapshot>() {
          @Override
          public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {


              if (!queryDocumentSnapshots.isEmpty()) {

                  for (DocumentChange documentChange : queryDocumentSnapshots.getDocumentChanges()) {

                      if (documentChange.getType() == DocumentChange.Type.ADDED) {
                          cityname.add(documentChange.getDocument().getId());
                          img.add( documentChange.getDocument().getString("Image"));
                          ID.add(documentChange.getDocument().getId());                      }
                  }
              }
          }
      });


        adapter_activity=new Adapter_Activity(getContext(),img , cityname , ID);

        Rev_activity.setAdapter(adapter_activity);

        return view;
    }

    private void Get_Current_Location(){
//Get Coordinates
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        provider = locationManager.getBestProvider(new Criteria(), false);

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {


            ActivityCompat.requestPermissions(getActivity(), new String[]{
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
        if (location != null)
        {
            lat = location.getLatitude();
            lng = location.getLongitude();

            new GetWeather().execute(Common.apiRequest(String.valueOf(lat),String.valueOf(lng)));
        }
        else
            Log.e("TAG","No Location");
    }
    private class GetWeather extends AsyncTask<String,Void,String> {



        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           // progress.setTitle("Please wait...");
          //  progress.show();

        }


        @Override
        protected String doInBackground(String... params) {
            String stream = null;
            String urlString = params[0];

            Helper http = new Helper();
            stream = http.getHTTPData(urlString);
            return stream;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s.contains("Error: Not found city")){
              //  progress.dismiss();
                return;
            }
            Gson gson = new Gson();
            Type mType = new TypeToken<OpenWeatherMap>(){}.getType();
            openWeatherMap = gson.fromJson(s,mType);
           // progress.dismiss();

          //  txtCity.setText(String.format("%s,%s",openWeatherMap.getName(),openWeatherMap.getSys().getCountry()));
            txtLastUpdate.setText(String.format("Last Updated: %s", Common.getDateNow()));
            txtDescription.setText(String.format("%s",openWeatherMap.getWeather().get(0).getDescription()));
            txtHumidity.setText(String.format("%d%%",openWeatherMap.getMain().getHumidity()));
           // txtTime.setText(String.format("%s/%s",Common.unixTimeStampToDateTime(openWeatherMap.getSys().getSunrise()),Common.unixTimeStampToDateTime(openWeatherMap.getSys().getSunset())));
            txtCelsius.setText(String.format("%.2f °C",openWeatherMap.getMain().getTemp()));
            Picasso.get()
                    .load(Common.getImage(openWeatherMap.getWeather().get(0).getIcon()))
                    .into(img_ic_status);

        }

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
 }