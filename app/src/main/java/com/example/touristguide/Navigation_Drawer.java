package com.example.touristguide;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.touristguide.About_Us.About_Us;
import com.example.touristguide.Activity.Fragment_Activity;
import com.example.touristguide.Bookmark.BookMarks;
import com.example.touristguide.Category.Category_Fragment;
import com.example.touristguide.Event.Event_Activity.Event_Page;
import com.example.touristguide.Profile.Profile_Page;
import com.example.touristguide.Setting_Account.Settings;
import com.example.touristguide.Utilis.LoacalHelper;
import com.example.touristguide.notification_saved.notification_page;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import devlight.io.library.ntb.NavigationTabBar;
import io.paperdb.Paper;

public class Navigation_Drawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth mAuth;
    private Button logout; // test button
    private FirebaseFirestore firebaseFirestore;

    private View headerView;
    private FloatingActionButton fab;
    private NavigationTabBar navigationTabBar;
    private int posFragment = 0;
    private int MY_PERMISSION = 0;
    public static DrawerLayout drawer;

    FirebaseJobDispatcher dispatcher;


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LoacalHelper.onAttach(newBase, "en"));
    }

    private void updateView(String Language) {
        Context context = LoacalHelper.setLocal(this, Language);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation__drawer);

        drawer = findViewById(R.id.drawer_layout);


        Paper.init(this);


        String Language = Paper.book().read("language");

        if (Language == null) {
            Paper.book().write("language", "en");
        }

        updateView((String) Paper.book().read("language"));

        dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(getApplicationContext()));

        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();





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


        drawer = findViewById(R.id.drawer_layout);

        final AppBarLayout AppBar = findViewById(R.id.AppBarID);

        NavigationView navigationView = findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);
//

        headerView = navigationView.getHeaderView(0);


        final String[] colors = getResources().getStringArray(R.array.colorful);

        navigationTabBar = findViewById(R.id.ntb);

        // replace fragment Activity .
        replacefragment(new Fragment_Activity());


        ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_home),
                        Color.parseColor(colors[0])
                ).title("Home")

                        .build()


        );

        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_notifications),
                        Color.parseColor(colors[1])
                ).title("notifications")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_category),
                        Color.parseColor(colors[2])
                ).title("Category")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_action_name),
                        Color.parseColor(colors[3])
                ).title("Profile")
                        .build()
        );


        navigationTabBar.setModels(models);
//        navigationTabBar.setViewPager(viewPager, 1); // WHEN YOU USE VIEWPAGER

        navigationTabBar.setTitleMode(NavigationTabBar.TitleMode.ACTIVE);

        navigationTabBar.setBadgePosition(NavigationTabBar.BadgePosition.CENTER);
        //navigationTabBar.setTypeface("fonts/custom_font.ttf");
        navigationTabBar.setIsBadged(true);
        navigationTabBar.setIsTitled(true);
        navigationTabBar.setIsTinted(true);
        navigationTabBar.setIsBadgeUseTypeface(true);
        navigationTabBar.setBadgeTitleColor(Color.WHITE);
        navigationTabBar.setIsSwiped(true);
        navigationTabBar.setModelIndex(0);
        navigationTabBar.setTitleSize(15);
        navigationTabBar.setBehaviorEnabled(true);
        navigationTabBar.setIconSizeFraction(0.5f);


        navigationTabBar.setOnTabBarSelectedIndexListener(new NavigationTabBar.OnTabBarSelectedIndexListener() {
            @Override
            public void onStartTabSelected(NavigationTabBar.Model model, int index) {


                return;

            }


            @Override
            public void onEndTabSelected(NavigationTabBar.Model model, int index) {
                if (index != posFragment)
                    switch (index) {
                        case 0: {
                            Fragment_Activity activity = new Fragment_Activity();
                            replacefragment(activity);
                            posFragment = index;
                            break;
                        }

                        case 1: {
                            notification_page notification_page = new notification_page();
                            replacefragment(notification_page);


                            posFragment = index;
                            break;
                        }
                        case 2: {
                            Category_Fragment category_fragment = new Category_Fragment();
                            replacefragment(category_fragment);
                            posFragment = index;
                            break;
                        }
                        case 3: {
                            Profile_Page profile_page = new Profile_Page();
                            replacefragment(profile_page);
                            AppBar.setLayoutParams(new CoordinatorLayout.LayoutParams(0, 0));
                            posFragment = index;
                            break;
                        }


                    }
                return;
            }
        });

        LoadLocal();
    }

    private void hideAppBar(final AppBarLayout appBar) {


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.navigation__drawer, menu);


        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setLocal(String lang) {

        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;

        getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());

        SharedPreferences.Editor editor = getSharedPreferences("Setting", MODE_PRIVATE).edit();
        editor.putString("My_Lang", lang);
        editor.apply();

    }

    public void LoadLocal() {
        SharedPreferences preferences = getSharedPreferences("Setting", Activity.MODE_PRIVATE);
        String Lang = preferences.getString("My_Lang", "en");
        setLocal(Lang);

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.nav_event) {
            startActivity(new Intent(this, Event_Page.class));
            Animatoo.animateFade(this);
        } else if (id == R.id.nav_bookmark) {

            startActivity(new Intent(this, BookMarks.class));

        } else if (id == R.id.nav_account) {
            Intent intent = new Intent(this, Settings.class);
            startActivity(intent);

        } else if (id == R.id.nav_aboutus) {
            Intent intent = new Intent(this, About_Us.class);

            startActivity(intent);

        } else if (id == R.id.nav_bookmark) {
            Intent intent = new Intent(Navigation_Drawer.this, BookMarks.class);

            startActivity(intent);

        } else if (id == R.id.nav_exit) {

            mAuth.signOut();
            finish();
            sendtologin();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    protected void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() == null) {

            sendtologin();
        } else {
            firebaseFirestore.collection("User").document(mAuth.getCurrentUser().getUid())
                    .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        TextView navUsername = (TextView) headerView.findViewById(R.id.menu_txt_username);
                        navUsername.setText(task.getResult().get("Username").toString());

                        CircleImageView navImageprofile = headerView.findViewById(R.id.menu_img_profile);
                        Picasso.get().load(task.getResult().get("Image_User").toString()).into(navImageprofile);

                    } else
                        Log.d("Error", task.getException().getMessage());
                }
            });

            FirebaseInstanceId.getInstance().getInstanceId()
                    .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                        @Override
                        public void onComplete(@NonNull Task<InstanceIdResult> task) {
                            if (!task.isSuccessful()) {
                                Log.d("ERRORS", "getInstanceId failed", task.getException());
                                return;
                            }


                            // Get new Instance ID token
                            String token = task.getResult().getToken();

                            // Log and toast
                            HashMap<String, String> tokenmap = new HashMap<>();
                            tokenmap.put("Token", token);
                            firebaseFirestore.collection("Token").document(mAuth.getCurrentUser().getUid()).set(tokenmap);

                        }
                    });
        }

    }


    public void replacefragment(Fragment fragment) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();


    }

    private void sendtologin() {

        finish();
        Intent intent = new Intent(this, Contact_page.class);
        startActivity(intent);
    }
}
