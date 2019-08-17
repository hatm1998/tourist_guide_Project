package com.example.touristguide.Profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
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
    private ViewPager ViewPager;
    private CircleImageView profileimage;
    private TextView Txt_UserName;
    FirebaseAuth mAuth;
    FirebaseFirestore firebaseFirestore;

    public Profile_Page() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
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

        mSectionsPagerAdapter = new SectionsPagerAdapter(getActivity().getSupportFragmentManager());

        ViewPager = (ViewPager) view.findViewById(R.id.viewpager_inprofile);
        ViewPager.setAdapter(mSectionsPagerAdapter);

        final TabLayout tabLayout = (TabLayout) view.findViewById(R.id.Tab_inprofile);

        ViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(ViewPager));
        return view;
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
                    Fragment_Picture fragment_Picture = new Fragment_Picture();
                    return fragment_Picture;
                case 1:
                    Fragment_Post fragment_post = new Fragment_Post();
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