package com.example.touristguide.Profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.touristguide.Profile.Fragment_Picture.Fragment_Picture;
import com.example.touristguide.Profile.Fragment_Post_inProfile.Fragment_Post;
import com.example.touristguide.R;
import com.google.android.material.tabs.TabLayout;

public class Profile_Page extends Fragment {


    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager ViewPager;
    public Profile_Page() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

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