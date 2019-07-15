package com.example.touristguide;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.touristguide.authentication.Login;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import devlight.io.library.ntb.NavigationTabBar;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private Button logout; // test button

    private NavigationTabBar navigationTabBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final String[] colors = getResources().getStringArray(R.array.colorful);
        logout = findViewById(R.id.btn_logout);
        navigationTabBar = findViewById(R.id.ntb);
        mAuth = FirebaseAuth.getInstance();

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
        Intent intent = new Intent(getApplicationContext(), Login.class);
        startActivity(intent);
    }


}
