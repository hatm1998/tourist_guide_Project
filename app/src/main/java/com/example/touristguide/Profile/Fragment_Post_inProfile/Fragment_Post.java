package com.example.touristguide.Profile.Fragment_Post_inProfile;

        import android.location.Location;
        import android.os.Bundle;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;

        import androidx.fragment.app.Fragment;
        import androidx.recyclerview.widget.GridLayoutManager;
        import androidx.recyclerview.widget.RecyclerView;

        import com.example.touristguide.R;

        import java.util.ArrayList;
        import java.util.Date;

public class Fragment_Post extends Fragment {

    private RecyclerView RCV_post;
    private ArrayList<setPost> list;
    private adapter_Post_inprofile adapter;

    public Fragment_Post() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_post_inprofile, container, false);

        RCV_post=view.findViewById(R.id.RCV_post_inprofile);
        list=new ArrayList<>();
        String Image2[]={"https://firebasestorage.googleapis.com/v0/b/firebast1-57abc.appspot.com/o/Images%20Item%2Fimg%2Ffff.jpg?alt=media&token=0282ef1a-0584-49cf-bf9f-f265a64eb572",
        "https://firebasestorage.googleapis.com/v0/b/firebast1-57abc.appspot.com/o/Images%20Item%2Fimg%2Fr.jpg?alt=media&token=0d1d3d69-b82d-4a92-9e1c-0905ca24a919",
        "https://firebasestorage.googleapis.com/v0/b/firebast1-57abc.appspot.com/o/Images%20Item%2Fimg%2Fff.jpg?alt=media&token=ab3baf32-0ef3-47b6-ac03-b2e757998db3",
        "https://firebasestorage.googleapis.com/v0/b/firebast1-57abc.appspot.com/o/Images%20Item%2Fimg%2Fffff.jpg?alt=media&token=a6fca41a-5681-4bab-9f28-cde6cef945c4",
        "https://firebasestorage.googleapis.com/v0/b/firebast1-57abc.appspot.com/o/Images%20Item%2Fimg%2Fffffff.jpg?alt=media&token=1514b652-7940-4d23-9c75-c75207a1e19e",
        "https://firebasestorage.googleapis.com/v0/b/firebast1-57abc.appspot.com/o/Images%20Item%2Fimg%2Ffffff.jpg?alt=media&token=31f1c275-7b5c-4045-953c-6ecdf8e9387e",
        "https://firebasestorage.googleapis.com/v0/b/firebast1-57abc.appspot.com/o/Images%20Item%2Fimg%2Ff.jpg?alt=media&token=3aa1917a-7f20-45b5-a7e1-0abe4b1f9350"
        };
        String Pic_User="https://firebasestorage.googleapis.com/v0/b/firebast1-57abc.appspot.com/o/Images%20Item%2Fziad.jpg?alt=media&token=bcb52aeb-37fb-4027-8d1b-40429e61bb7f";

        for(int i=0;i<Image2.length;i++)
            list.add(new setPost("",Pic_User
                    ,"Ziad Al-Kasaji"
                    ,Image2[i]
                    ,"become an accepted part of many culturesing.[1] This makes texting a quick and easy ❤❤\uD83D\uDE0A "
                    ,new Date()
                    ,new Location("service Provider")));
        adapter=new adapter_Post_inprofile(getContext(),list);
        RCV_post.setLayoutManager(new GridLayoutManager(getContext(),1));
        RCV_post.setAdapter(adapter);

        return view;
    }

}