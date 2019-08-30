package com.example.touristguide.Setting_Account;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.touristguide.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.nex3z.flowlayout.FlowLayout;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class Setting_Account extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseFirestore firebaseFirestore;
    private TextInputEditText txt_name, txt_email;
    private CircleImageView Profile_Image;
    private ArrayList<String> Selected_Chip;
    private StorageReference storageReference;
    ArrayList<String> Remove = new ArrayList<>();
    private ProgressBar pr_Update_User;
    private Uri ImageUri = null;
    Button btn_update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting__account);
        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        btn_update = findViewById(R.id.btn_setting_save);

        storageReference = FirebaseStorage.getInstance().getReference();
        Selected_Chip = new ArrayList<>();
        Profile_Image = findViewById(R.id.img_setting);
        txt_name = findViewById(R.id.txt_Username_Account_Setting);
        txt_email = findViewById(R.id.txt_Email_account_setting);

        pr_Update_User = findViewById(R.id.progress_update_user);


        final String[] ImageURL = new String[1];
        firebaseFirestore.collection("User").document(mAuth.getCurrentUser().getUid())
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                ImageURL[0] = task.getResult().get("Image_User").toString();
                Picasso.get().load(ImageURL[0]).into(Profile_Image);
                txt_name.setText(task.getResult().get("Username").toString());

                txt_email.setText(mAuth.getCurrentUser().getEmail());
                Selected_Chip = (ArrayList<String>) task.getResult().get("Categories");
                Remove = Selected_Chip;
                fillChape();
            }
        });

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pr_Update_User.setVisibility(View.VISIBLE);

                if (ImageUri != null) {
                    final StorageReference imagepath = storageReference.child("Image_Profile").child(mAuth.getCurrentUser().getUid() + ".jpg");

                    imagepath.putFile(ImageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if (task.isSuccessful()) {
                                imagepath.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Uri> task) {

                                        UpdateUser(task.getResult().toString());
                                        Remove.removeAll(Selected_Chip);
                                        if (Remove.size() > 0) {
                                            for (int i = 0; i < Selected_Chip.size(); i++) {
                                                final int finalI = i;
                                                FirebaseMessaging.getInstance().unsubscribeFromTopic(Selected_Chip.get(i))
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {

                                                                if (!task.isSuccessful()) {
                                                                    String msg = "Error Subscribe";
                                                                    Log.d("Erorrs", msg);
                                                                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                                                                } else
                                                                    Log.d("unsubscribeFromTopic", Selected_Chip.get(finalI));

                                                            }
                                                        });

                                            }
                                        }
                                    }
                                });
                            }

                        }
                    });
                } else
                    UpdateUser(ImageURL[0]);


            }
        });
    }

    private void UpdateUser(String Image) {
        HashMap<String, Object> maps = new HashMap<>();
        maps.put("Categories", Selected_Chip);
        maps.put("Image_User", Image);
        maps.put("Username", txt_name.getText().toString());


        firebaseFirestore.collection("User").document(mAuth.getCurrentUser().getUid())
                .update(maps).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    pr_Update_User.setVisibility(View.GONE);
                    Toast.makeText(getBaseContext(), "User Was Update Info Successfully", Toast.LENGTH_LONG).show();

                } else
                    Toast.makeText(getBaseContext(), "Error : " + task.getException().getMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                ImageUri = result.getUri();
                //Log.d("IMGEURI",String.valueOf(ImageUri));
                Profile_Image.setImageURI(ImageUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
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
