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
        String Image2[]={
                "https://firebasestorage.googleapis.com/v0/b/realtimefirebase-aaf23.appspot.com/o/Post%2Ff.jpg?alt=media&token=9162fd8d-407e-4ddb-8b1d-81491a741764",
                "https://firebasestorage.googleapis.com/v0/b/realtimefirebase-aaf23.appspot.com/o/Post%2Ffff.jpg?alt=media&token=86980ac8-8b4c-414d-a461-aed82c8945da",
                "https://firebasestorage.googleapis.com/v0/b/realtimefirebase-aaf23.appspot.com/o/Post%2Fffffff.jpg?alt=media&token=242478d1-cf55-4747-9b57-177b27ab6c4c",
                "https://firebasestorage.googleapis.com/v0/b/realtimefirebase-aaf23.appspot.com/o/Post%2Fff.jpg?alt=media&token=58ee2bf6-0168-43fe-b701-d7adbedc01a5",
                "https://firebasestorage.googleapis.com/v0/b/realtimefirebase-aaf23.appspot.com/o/Post%2Fffff.jpg?alt=media&token=ce95044f-f86c-4da5-a109-6fa671e10928",
                "https://firebasestorage.googleapis.com/v0/b/realtimefirebase-aaf23.appspot.com/o/Post%2Ffffff.jpg?alt=media&token=9096167c-fd88-49b0-822a-d5344af308d1"
        };
        String Pic_User="https://firebasestorage.googleapis.com/v0/b/realtimefirebase-aaf23.appspot.com/o/User%2Fziad.jpg?alt=media&token=fe511e7a-40fa-4cd2-b6bf-d5fc417a25bd";

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