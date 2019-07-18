package com.example.touristguide;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import devlight.io.library.ntb.NavigationTabBar;

public class homeActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private Button logout; // test button

    private NavigationTabBar navigationTabBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        final String[] colors = getResources().getStringArray(R.array.colorful);
        logout = findViewById(R.id.btn_logout);
        navigationTabBar = findViewById(R.id.ntb);
        mAuth = FirebaseAuth.getInstance();



        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("MyNotification", "MyNotification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);

        }





        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                sendtologin();

            }
        });



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

    }


    @Override
    protected void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() == null) {

            sendtologin();
        }
    }

    private void sendtologin() {

        finish();
        Intent intent = new Intent(getApplicationContext(), Contact_page.class);
        startActivity(intent);
    }


}
