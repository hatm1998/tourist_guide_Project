package com.example.touristguide.Utilis;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.example.touristguide.Navigation_Drawer;
import com.example.touristguide.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.nex3z.flowlayout.FlowLayout;

import java.util.ArrayList;
import java.util.HashMap;


public class Material_Chip_View extends AppCompatActivity {

    private ImageView img_top,img_Down;
    private Button btn_GO;
    private ScrollView scrollView;
    // Selected Item .
    private ArrayList<String> Selected_Chip;
    private Context context;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material__chip__view);
        context=this;
        img_top=findViewById(R.id.img_top_chippage);
        img_Down=findViewById(R.id.img_down_chippage);
        btn_GO=findViewById(R.id.btn_Go_chippage);
        scrollView=findViewById(R.id.scroll_chippage);
        Selected_Chip=new ArrayList<>();
        firebaseFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        open_Animation();


        btn_GO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(Selected_Chip.isEmpty())
                {

                    new AlertDialog.Builder(context)
                            .setTitle("Sorry!")

                            .setMessage("You have not chosen places to visit ? ")
                            .setNegativeButton("Back",null)
                            .setPositiveButton("Skip", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    close_Animation();
                                }
                            })
                            .show();
               }else
               {
                   HashMap<String , Object> categoreis = new HashMap<>();
                   categoreis.put("Categories" , Selected_Chip);
                   firebaseFirestore.collection("User").document(mAuth.getCurrentUser().getUid())
                           .update("Categories",Selected_Chip);
                   Intent intent = new Intent(Material_Chip_View.this, Navigation_Drawer.class);
                   startActivity(intent);
                   Toast.makeText(context,Selected_Chip.toString(),Toast.LENGTH_LONG).show();
               }
            }
        });


   String chip_Name[]={"Medical tourism"
           ,"Culture"
           ,"Entertainment"
           ,"Shopping"
           ,"Desert"
           ,"Beach"
           ,"Beaches"
           ,"Nature"
           ,"Arts"};
        // add Chips View .
      final FlowLayout layout=(FlowLayout)(findViewById(R.id.flayout));
        final float scale = getResources().getDisplayMetrics().density;

  for(int i=0;i<chip_Name.length;i++)
  {
      final ToggleButton chip=new ToggleButton(this);
      chip.setTextOff(chip_Name[i]);
      chip.setTextOn(chip_Name[i]);
      chip.setChecked(false);
      chip.setInputType(InputType.TYPE_TEXT_VARIATION_NORMAL);
      chip.setGravity(Gravity.START | Gravity.CENTER);
      Drawable style_chips=(Drawable)getResources().getDrawable(R.drawable.custom_chips_places);
      chip.setBackground(style_chips);
      Typeface typeface = ResourcesCompat.getFont(context, R.font.mvboli);
      chip.setTypeface(typeface);
      chip.setPadding((int) (5 * scale + 0.5f),0,(int) (30 * scale + 0.5f),0);
      chip.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
             if(chip.isChecked())
                  Selected_Chip.add(chip.getText().toString());
          else
                  Selected_Chip.remove(chip.getText().toString());
          }
      });
      layout.addView(chip,new FlowLayout.LayoutParams(FlowLayout.LayoutParams.WRAP_CONTENT,(int)(33 * scale + 0.5f)));
  }
    }
    private void open_Animation(){

        Animation slide_down = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.open_top_chippage);
        Animation slide_up = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.open_buttom_chippage);
        Animation bounce = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.open_comp_signup);
        img_Down.clearAnimation();
        img_top.clearAnimation();

        img_Down.setAnimation(slide_down);
        img_top.setAnimation(slide_up);
        btn_GO.setAnimation(slide_up);
        scrollView.setAnimation(bounce);
    }
    private void close_Animation(){

        Animation slide_down = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.close_top_chippage);
        Animation slide_up = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.close_buttom_chippage);
        Animation bounce = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.close_comp_signup);

        img_Down.clearAnimation();
        img_top.clearAnimation();

        img_Down.setAnimation(slide_down);
        img_top.setAnimation(slide_up);
        btn_GO.setAnimation(slide_up);
        scrollView.setAnimation(bounce);
    }

}
