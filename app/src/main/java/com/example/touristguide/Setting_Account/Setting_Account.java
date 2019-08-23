package com.example.touristguide.Setting_Account;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.ToggleButton;

import com.example.touristguide.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.nex3z.flowlayout.FlowLayout;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Setting_Account extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseFirestore firebaseFirestore;
    private TextInputEditText txt_name, txt_email,txt_phone;
    private CircleImageView Profile_Image;
    private ArrayList<String> Selected_Chip;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting__account);
        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        Selected_Chip = new ArrayList<>();
        Profile_Image = findViewById(R.id.img_setting);
        txt_name = findViewById(R.id.txt_Username_Account_Setting);
        txt_email = findViewById(R.id.txt_Email_account_setting);
        txt_phone = findViewById(R.id.txt_phone_account_page);
        firebaseFirestore.collection("User").document(mAuth.getCurrentUser().getUid())
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                Picasso.get().load(task.getResult().get("Image_User").toString()).into(Profile_Image);
                txt_name.setText(task.getResult().get("Username").toString());
                txt_phone.setText(task.getResult().get("Phone").toString());
                txt_email.setText(mAuth.getCurrentUser().getEmail());
                Selected_Chip = (ArrayList<String>) task.getResult().get("Categories");
                fillChape();
            }
        });

    }

    private void fillChape() {

        String chip_Name[] = {"Medical tourism"
                , "Culture"
                , "Entertainment"
                , "Shopping"
                , "Desert"
                , "Beach"
                , "Beaches"
                , "Nature"
                , "Arts"};
        // add Chips View .
        final FlowLayout layout = (FlowLayout) (findViewById(R.id.flayout));
        final float scale = getResources().getDisplayMetrics().density;

        for (int i = 0; i < chip_Name.length; i++) {
            final ToggleButton chip = new ToggleButton(this);
            chip.setTextOff(chip_Name[i]);
            chip.setTextOn(chip_Name[i]);
            if (Selected_Chip.contains(chip_Name[i]))
                chip.setChecked(true);
            else
                chip.setChecked(false);
            chip.setInputType(InputType.TYPE_TEXT_VARIATION_NORMAL);
            chip.setGravity(Gravity.START | Gravity.CENTER);
            Drawable style_chips = (Drawable) getResources().getDrawable(R.drawable.custom_chips_places);
            chip.setBackground(style_chips);
            Typeface typeface = ResourcesCompat.getFont(getBaseContext(), R.font.mvboli);
            chip.setTypeface(typeface);
            chip.setPadding((int) (5 * scale + 0.5f), 0, (int) (30 * scale + 0.5f), 0);
            chip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (chip.isChecked())
                        Selected_Chip.add(chip.getText().toString());
                    else
                        Selected_Chip.remove(chip.getText().toString());
                }
            });

            layout.addView(chip, new FlowLayout.LayoutParams(FlowLayout.LayoutParams.WRAP_CONTENT, (int) (33 * scale + 0.5f)));


        }

    }


}
