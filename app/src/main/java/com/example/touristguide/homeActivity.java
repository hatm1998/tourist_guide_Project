package com.example.touristguide;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.touristguide.Activity.Fragment_Activity;
import com.example.touristguide.ShareItem.Add_new_post;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import devlight.io.library.ntb.NavigationTabBar;

public class homeActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private Button logout; // test button

    private FloatingActionButton fab;
    private NavigationTabBar navigationTabBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        final String[] colors = getResources().getStringArray(R.array.colorful);
        logout = findViewById(R.id.btn_logout);
        navigationTabBar = findViewById(R.id.ntb);
        mAuth = FirebaseAuth.getInstance();
        fab = findViewById(R.id.fab_add);

        // replace fragment Activity .
        replacefragment(new Fragment_Activity());

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(homeActivity.this , Add_new_post.class);
                startActivity(intent);
            }
        });
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


        navigationTabBar.setOnTabBarSelectedIndexListener(new NavigationTabBar.OnTabBarSelectedIndexListener() {
            @Override
            public void onStartTabSelected(NavigationTabBar.Model model, int index) {


                return;

            }


            @Override
            public void onEndTabSelected(NavigationTabBar.Model model, int index) {

                switch (index) {
                    case 0: {
                        //replacefragment(home_fragment)
                        replacefragment(new Fragment_Activity());
                        break;
                    }

                    case 1: {
                        //replacefragment(notifications_fragment);
                        break;
                    }
                    case 2: {
                       // replacefragment(category_fragment);
                        break;
                    }
                    case 3: {
                       // replacefragment(profile_fragment);
                        break;
                    }


                }
                return;
            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() == null) {

            sendtologin();
        }
    }


    public void  replacefragment(Fragment fragment) {
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
