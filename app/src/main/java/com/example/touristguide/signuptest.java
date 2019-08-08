package com.example.touristguide;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.touristguide.Utilis.Material_Chip_View;

import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class signuptest extends AppCompatActivity {



    private ImageView img_top,img_Down;
    private Button btn_next;
    private ScrollView scrollView;
    private CircleImageView img_profile;
    private LinearLayout Relayout;
    private  Dialog dialog;
    private Context context;
    Animation slide_down;
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signuptest);
         context=this;
         img_top=findViewById(R.id.img_top_chippage);
         img_Down=findViewById(R.id.img_down_chippage);
         btn_next=findViewById(R.id.btn_next_chippage);
         scrollView=findViewById(R.id.scroll_chippage);
         img_profile=findViewById(R.id.img_signuppage);
         Relayout=findViewById(R.id.Relayout_signuppage);

         open_Animation();



         btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                 close_Animation();
               new Timer().schedule(new TimerTask(){
                   public void run() {
                       startActivity(new Intent(getApplicationContext(), Material_Chip_View.class));
                      // Animatoo.animateZoom(context);
                       Animatoo.animateFade(context);

//                       Animatoo.animateWindmill(context);
//                      Animatoo.animateSpin(context);
//                       Animatoo.animateDiagonal(context);
//                       Animatoo.animateSplit(context);
//                       Animatoo.animateShrink(context);
//                      Animatoo.animateCard(context);
//                      Animatoo.animateInAndOut(context);
//                      Animatoo.animateSwipeLeft(context);
//                       Animatoo.animateSwipeRight(context);
//                       Animatoo.animateSlideLeft(context);
//                       Animatoo.animateSlideRight(context);
//                       Animatoo.animateSlideDown(context);
//                       Animatoo.animateSlideUp(context);
                    }
                }, 250);


              // btn_signUp();
            }
        });

    }

    private void btn_signUp(){
        //finish();
        dialog=new Dialog(this,R.style.PauseDialog);
        dialog.setContentView(R.layout.activity_sign_up);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        final TextView txt_login = dialog.findViewById(R.id.txt_Login_signup);
        txt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Login",Toast.LENGTH_SHORT).show();
            }
        });
        ImageView close=dialog.findViewById(R.id.btn_close_signuppage);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
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
        Relayout.clearAnimation();
        img_profile.clearAnimation();

        img_Down.setAnimation(slide_down);
        img_profile.setAnimation(slide_down);

        img_top.setAnimation(slide_up);
        btn_next.setAnimation(slide_up);
        Relayout.setAnimation(slide_up);

        scrollView.setAnimation(bounce);
    }

    private void close_Animation(){

         slide_down = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.close_top_chippage);
        Animation slide_up = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.close_buttom_chippage);
        Animation bounce = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.close_comp_signup);

        img_Down.clearAnimation();
        img_top.clearAnimation();
        Relayout.clearAnimation();
        img_profile.clearAnimation();
        img_Down.setAnimation(slide_down);
        img_profile.setAnimation(slide_down);
        img_top.setAnimation(slide_up);
        btn_next.setAnimation(slide_up);
        Relayout.setAnimation(slide_up);
         scrollView.setAnimation(bounce);
    }

}
