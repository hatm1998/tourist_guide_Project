package com.example.touristguide.Commnets;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.touristguide.R;
import com.example.touristguide.video_player.models.Post;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Bottom_Sheet_Comment extends BottomSheetDialogFragment {
   private Context context;
   private String ID;
   private TextView txt_comment;
   private Button btn_send;
   private FirebaseAuth mAuth;
   private FirebaseFirestore firebaseFirestore;

    public Bottom_Sheet_Comment(Context context , String ID){
        this.context=context;
        this.ID = ID;
    }
   @Nullable
   @Override
   public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = (View) inflater.inflate(R.layout.comments_page,container,false);
       Button btn_menu=view.findViewById(R.id.btn_menu_comment);
       txt_comment = view.findViewById(R.id.txt_Wirte_comment);
       btn_send = view.findViewById(R.id.btn_comment_send);

       mAuth = FirebaseAuth.getInstance();
       firebaseFirestore = FirebaseFirestore.getInstance();



       btn_send.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               HashMap<String , Object> comment = new HashMap<>();
               comment.put("comment",txt_comment.getText().toString());
               comment.put("UserID",mAuth.getCurrentUser().getUid().toString());
               comment.put("Date",new Date());

               firebaseFirestore.collection("post").document(ID)
                       .collection("Comment").add(comment).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                   @Override
                   public void onComplete(@NonNull Task<DocumentReference> task) {
                      txt_comment.setText("");
                   }
               });
           }
       });

       final RecyclerView RCV_commment=view.findViewById(R.id.RCV_comment);
       final ArrayList<set_Comment> list =new ArrayList<>();

       final Adapter_Comments adapter_comments = new Adapter_Comments( this.context,list);
       RCV_commment.setLayoutManager(new GridLayoutManager( this.context,1));
       RCV_commment.setAdapter(adapter_comments);


       Query SecoundQuery = firebaseFirestore.collection("post").document(ID).collection("Comment")
               .orderBy("Date", Query.Direction.DESCENDING);

       SecoundQuery.addSnapshotListener( new EventListener<QuerySnapshot>() {

           @Override
           public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
               if (!documentSnapshots.isEmpty()) {
                   for (DocumentChange documentChange : documentSnapshots.getDocumentChanges()) {

                       if (documentChange.getType() == DocumentChange.Type.ADDED) {

                           set_Comment post = documentChange.getDocument().toObject(set_Comment.class);

                           list.add(post);
                           adapter_comments.notifyDataSetChanged();
                       }
                   }
               }
           }
       });
//       for(int i=0;i<20;i++)
//           list.add(new set_Comment("","",new Date()));
//
       btn_menu.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
              dismiss();
           }
       });
       return  view;
   }


   }


