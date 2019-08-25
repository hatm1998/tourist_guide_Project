package com.example.touristguide.Profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.touristguide.Profile.Fragment_Picture.Fragment_Picture;
import com.example.touristguide.Profile.Fragment_Post_inProfile.Fragment_Post;
import com.example.touristguide.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile_Page extends Fragment {


    private SectionsPagerAdapter mSectionsPagerAdapter;
    private  ViewPager ViewPager;
    private CircleImageView profileimage;
    private TextView Txt_UserName;
    FirebaseAuth mAuth;
    FirebaseFirestore firebaseFirestore;
   static Fragment_Post fragment_post = new Fragment_Post();
   static Fragment_Picture fragment_Picture = new Fragment_Picture();
   private int state = 0;
    View view;

    public Profile_Page() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        profileimage = view.findViewById(R.id.Frag_profile_image);
        Txt_UserName = view.findViewById(R.id.frag_txt_username);


        firebaseFirestore.collection("User").document(mAuth.getCurrentUser().getUid())
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                Picasso.get().load(task.getResult().get("Image_User").toString()).into(profileimage);
                Txt_UserName.setText(task.getResult().get("Username").toString());

            }
        });

        Toast.makeText(getContext(),"View Was Created",Toast.LENGTH_LONG).show();
        mSectionsPagerAdapter = new SectionsPagerAdapter(this.getChildFragmentManager());

        ViewPager = (ViewPager) view.findViewById(R.id.viewpager_inprofile);
        ViewPager.setAdapter(mSectionsPagerAdapter);


         TabLayout tabLayout = (TabLayout) view.findViewById(R.id.Tab_inprofile);


        ViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(ViewPager));
        return view;
    }







    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        Toast.makeText(getContext(), "OutState", Toast.LENGTH_LONG).show();
        super.onSaveInstanceState(outState);

    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {


        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).

            switch (position) {
                case 0:
                    Toast.makeText(getContext(),"Postition : "+ position ,Toast.LENGTH_LONG).show();
                    if (state == 1)
                        fragment_post.mRecyclerView.videoPlayer.stop();
                    state = position;
                    return fragment_Picture;
                case 1:

                    state = position;
                    return fragment_post;

                default:
                    return null;
            }

        }

        @Override
        public int getCount() {
            return 2;
        }
    }

}