package com.example.touristguide.CityActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.touristguide.CityActivity.sightseeing.Adapter_sightseeing;
import com.example.touristguide.R;
import com.example.touristguide.ShareItem.Add_new_post;
import com.example.touristguide.CityActivity.sightseeing.Places;
import com.example.touristguide.Utilis.Post;
import com.example.touristguide.Utilis.post_recycle_adapter;
import com.example.touristguide.weather.Common.Common;
import com.example.touristguide.weather.Helper.Helper;
import com.example.touristguide.weather.Model.OpenWeatherMap;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import de.hdodenhof.circleimageview.CircleImageView;

public class City_Main extends AppCompatActivity {
    private FirebaseFirestore firebaseFirestore;

    private ImageView header_Image;

    private ProgressBar progressBar;

    private post_recycle_adapter post_recycle_view;

    // Component Weather Information
    private CircleImageView img_ic_status;
    private TextView txtLastUpdate,txtDescription,txtHumidity,txtCelsius;
    private Button btnrefresh;


    // Component Get Weather .
    private  OpenWeatherMap openWeatherMap ;

    private RecyclerView  Post_list_view;
    private List<Post> postList;
    private FloatingActionButton fab;

    // Component sightseeing .
    private ViewPager pager_sightseeing;
    private ArrayList<Places> List_sightseeing;
    private Adapter_sightseeing adapter_sightseeing;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.header_main_city);

        img_ic_status=findViewById(R.id.img_ic_status_display_City);
        txtLastUpdate=findViewById(R.id.txtLastUpdate_display_City);
        txtDescription=findViewById(R.id.txtDescription_display_City);
        txtHumidity=findViewById(R.id.txtHumidity_display_City);
        txtCelsius=findViewById(R.id.txtCelsius_display_City);
        btnrefresh=findViewById(R.id.btn_refresh_location_display_City);

        openWeatherMap = new OpenWeatherMap();

       final  double lat=31.704870,lng=35.801819;

        //  GetWeather
        new GetWeather().execute(Common.apiRequest(String.valueOf(lat),String.valueOf(lng)));



        // Refresh Weather .
        btnrefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation anim = AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.click_btn_refresh);
                btnrefresh.setAnimation(anim);
                new GetWeather().execute(Common.apiRequest(String.valueOf(lat),String.valueOf(lng)));
            }
        });

        // get sightseeing from FireBase .

        List_sightseeing=new ArrayList<>();
        pager_sightseeing=findViewById(R.id.Vpage_in_main_city);
        pager_sightseeing.setClipToPadding(false);
        pager_sightseeing.setPadding(70, 0, 70, 0);
        pager_sightseeing.setPageMargin(20);
        adapter_sightseeing=new Adapter_sightseeing(this,List_sightseeing);
        pager_sightseeing.setAdapter(adapter_sightseeing);
        pager_sightseeing.setCurrentItem(1);

        final Intent intent = getIntent();
        final String CityID = intent.getStringExtra("Ads_id");
        progressBar = findViewById(R.id.header_city_progress);
        postList = new ArrayList<>();
        fab = findViewById(R.id.fab_add_Post);
        firebaseFirestore = FirebaseFirestore.getInstance();

        Post_list_view = findViewById(R.id.RC_City_post);

        final Query firstQuery = firebaseFirestore.collection("Cities").document(CityID).collection("places");

        firstQuery.addSnapshotListener( new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                for (DocumentChange documentChange : queryDocumentSnapshots.getDocumentChanges()) {

                    if (documentChange.getType() == DocumentChange.Type.ADDED) {
                        Places places = documentChange.getDocument().toObject(Places.class);
                        List_sightseeing.add(places);
                        adapter_sightseeing.notifyDataSetChanged();
                        pager_sightseeing.setCurrentItem(1);
                    }
                }
            }
        });



        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(City_Main.this , Add_new_post.class);
                startActivity(intent);

            }
        });


        // ---------------------- ** ------------------------ //

        post_recycle_view = new post_recycle_adapter(postList);
        Post_list_view.setAdapter(post_recycle_view);
        Post_list_view.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));


         Query SecoundQuery = firebaseFirestore.collection("post").orderBy("Date", Query.Direction.DESCENDING);

        SecoundQuery.addSnapshotListener( new EventListener<QuerySnapshot>() {

            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                if (!documentSnapshots.isEmpty()) {
                    for (DocumentChange documentChange : documentSnapshots.getDocumentChanges()) {

                        if (documentChange.getType() == DocumentChange.Type.ADDED) {

                            Post post = documentChange.getDocument().toObject(Post.class);
                            postList.add(post);
                            post_recycle_view.notifyDataSetChanged();
                        }
                    }
                }
            }
        });

      /*  for (int x = 0; x < 5; x++) {
            Post post = new Post();
            postList.add(post);
            place_recycle_view.notifyDataSetChanged();
        }
*/

        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.col_city);

        collapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);

        final Toolbar toolbar = findViewById(R.id.toolbarheader);
        toolbar.setTitle(CityID);

        setSupportActionBar(toolbar);

        progressBar.setVisibility(View.VISIBLE);

        header_Image = findViewById(R.id.header_img_city);
        firebaseFirestore = FirebaseFirestore.getInstance();

        firebaseFirestore.collection("Cities").document(CityID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                Picasso.get().load(task.getResult().get("Image").toString()).into(header_Image, new Callback() {
                    @Override
                    public void onSuccess() {
                        progressBar.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });


            }
        });


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
