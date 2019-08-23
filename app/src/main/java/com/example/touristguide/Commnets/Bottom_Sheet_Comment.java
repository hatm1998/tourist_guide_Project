package com.example.touristguide.Commnets;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.touristguide.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.Date;

public class Bottom_Sheet_Comment extends BottomSheetDialogFragment {
   private Context context;
    public Bottom_Sheet_Comment(Context context){
        this.context=context;
    }
   @Nullable
   @Override
   public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = (View) inflater.inflate(R.layout.comments_page,container,false);
       Button btn_menu=view.findViewById(R.id.btn_menu_comment);
       RecyclerView RCV_commment=view.findViewById(R.id.RCV_comment);
       ArrayList<set_Comment> list =new ArrayList<>();
       for(int i=0;i<20;i++)
           list.add(new set_Comment("","",new Date()));
       RCV_commment.setLayoutManager(new GridLayoutManager( this.context,1));
       RCV_commment.setAdapter(new Adapter_Comments( this.context,list));
       btn_menu.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
              dismiss();
           }
       });
       return  view;
   }


   }


