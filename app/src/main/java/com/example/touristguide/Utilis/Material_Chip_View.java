package com.example.touristguide.Utilis;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.touristguide.R;


public class Material_Chip_View extends AppCompatActivity {

    private ImageView img_top,img_Down;
    private Button btn_next;
    private ScrollView scrollView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material__chip__view);

        img_top=findViewById(R.id.img_top_chippage);
        img_Down=findViewById(R.id.img_down_chippage);
        btn_next=findViewById(R.id.btn_next_chippage);
        scrollView=findViewById(R.id.scroll_chippage);

        open_Animation();


        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                close_Animation();
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
        // add Message
      /* final FlowLayout layout=(FlowLayout)(findViewById(R.id.flayout));
        LinearLayout.LayoutParams styleSend = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        styleSend.gravity= Gravity.RIGHT;

        for(int i=0;i<chip_Name.length;i++) {
            final Chip chip = new Chip(this);
            chip.setSelected(true);
            chip.setSelectable(true);
            chip.changeBackgroundColor(Color.parseColor("#E7A942"));
            chip.changeSelectedBackgroundColor(Color.parseColor("#A61A1B16"));
            Log.i("YELLOW",Color.parseColor("#E7A942")+"");
            chip.setChipText(chip_Name[i]);
//            app:mcv_closeColor="@color/customCloseIconColor"
//            app:mcv_selectedBackgroundColor="@color/customSelectedChipColor"
//            app:mcv_textColor="@color/customTitleColor"

            chip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    chip.setSelectable(true);
                }
            });
            layout.addView(chip);
        }
        for(int i=0;i<chip_Name.length;i++) {
            final Chip chip = new Chip(this);
            chip.setSelected(true);
            chip.setSelectable(true);
            chip.changeBackgroundColor(Color.parseColor("#E7A942"));
            chip.changeSelectedBackgroundColor(Color.parseColor("#A61A1B16"));
            Log.i("YELLOW",Color.parseColor("#E7A942")+"");
            chip.setChipText(chip_Name[i]);
//            app:mcv_closeColor="@color/customCloseIconColor"
//            app:mcv_selectedBackgroundColor="@color/customSelectedChipColor"
//            app:mcv_textColor="@color/customTitleColor"

            chip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    chip.setSelectable(true);
                }
            });
            layout.addView(chip);
        }

*/

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
        btn_next.setAnimation(slide_up);
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
        btn_next.setAnimation(slide_up);
        scrollView.setAnimation(bounce);
    }

}
