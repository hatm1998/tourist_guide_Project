package com.example.touristguide;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.touristguide.Utilis.Material_Chip_View;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

public class signup extends AppCompatActivity {


    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseFirestore;


    private ImageView img_top, img_Down;
    private Button btn_next;
    private ConstraintLayout layout_main_comp;
    private CircleImageView img_profile;
    private LinearLayout Relayout;
    private Context context;
    private Animation slide_down;
    private String UserID = null;
    private StorageReference storageReference;
    private Uri ImageUri = null;
    private TextInputEditText txt_FullName, txt_Email,  txt_Password, txt_Re_Password;
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    //"(?=.*[0-9])" +         //at least 1 digit
                    //"(?=.*[a-z])" +         //at least 1 lower case letter
                    //"(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{4,}" +               //at least 4 characters
                    "$");
    private static final String Mobile_Pattern = "[0-9]{10}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signuptest);
        context = this;
        // For Animation .
        img_top = findViewById(R.id.img_top_chippage);
        img_Down = findViewById(R.id.img_down_chippage);
        btn_next = findViewById(R.id.btn_next_chippage);
        layout_main_comp = findViewById(R.id.layout_main_signup_page);
        img_profile = findViewById(R.id.img_signuppage);
        Relayout = findViewById(R.id.Relayout_signuppage);

        storageReference = FirebaseStorage.getInstance().getReference();
        //Firebase Auth

        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();


        // start Animation .
        open_Animation();

        // For Data
        txt_FullName = findViewById(R.id.txt_Username_signup_page);
        txt_Email = findViewById(R.id.txt_Email_account_setting);
        txt_Password = findViewById(R.id.txt_password_signup_page);
        txt_Re_Password = findViewById(R.id.txt_confirm_pass_signup_page);

        img_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setCropShape(CropImageView.CropShape.OVAL)
                        .start(signup.this);
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Check For Input .
                confirm_Input();
            }
        });

    }

    private void confirm_Input() {
        if (!validateEmail() | !validateUsername()  | !validatePassword() | !validateRe_Password()) {
            return;
        } else {
            // Close Animation .
            close_Animation();

            mAuth.createUserWithEmailAndPassword(txt_Email.getText().toString(), txt_Password.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                UserID = task.getResult().getUser().getUid();
                                Toast.makeText(getApplicationContext(),"User Was Created",Toast.LENGTH_LONG).show();
                                Register();
                            } else
                                Toast.makeText(getApplicationContext(), "Error : " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });


        }
    }

    private void Register() {

        final StorageReference imagepath = storageReference.child("Image_Profile").child(UserID + ".jpg");

        imagepath.putFile(ImageUri)
                .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        imagepath.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                String imageurl = task.getResult().toString(); // All of these Lines to get URL .. *_*

                                HashMap<String , String> userMap = new HashMap<>();
                                userMap.put("Username" , txt_FullName.getText().toString());
                                userMap.put("Image_User" ,imageurl);


                                firebaseFirestore.collection("User") // add user info to firebase
                                        .document(UserID).set(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful())
                                        {
                                            new Timer().schedule(new TimerTask() {
                                                public void run() {
                                                    // Next Step .
                                                    startActivity(new Intent(getApplicationContext(), Material_Chip_View.class));

                                                    // CLOSE Animation .
                                                    Animatoo.animateFade(context);
                                                }
                                            }, 250);
                                        }
                                        else
                                            Toast.makeText(getBaseContext(),"Erorr : " + task.getException().getMessage(),Toast.LENGTH_LONG).show();
                                    }
                                });


                            }
                        });
                    }
                });

    }

    // validation .
    private boolean validateUsername() {
        String Username = txt_FullName.getText().toString().trim();

        if (Username.isEmpty()) {
            txt_FullName.setError("Field can't be empty");
            return false;
        } else if (Username.length() > 15) {
            txt_FullName.setError("Too long");
            return false;
        } else {
            txt_FullName.setError(null);
            return true;
        }
    }

    private boolean validateEmail() {
        String Email = txt_Email.getText().toString().trim();

        if (Email.isEmpty()) {
            txt_Email.setError("Field can't be empty");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            txt_Email.setError("Invalid email");
            return false;
        } else {
            txt_Email.setError(null);
            return true;
        }
    }



    private boolean validatePassword() {
//        String Password = txt_Password.getText().toString().trim();
//
//        if (Password.isEmpty()) {
//            txt_Password.setError("Field can't be empty");
//            return false;
//        } else if (!PASSWORD_PATTERN.matcher(Password).matches()) {
//            txt_Password.setError("Too weak");
//            return false;
//        } else {
//            txt_Password.setError(null);
        return true;
//        }
    }

    private boolean validateRe_Password() {
        String Re_Password = txt_Re_Password.getText().toString().trim();

        if (Re_Password.isEmpty()) {
            txt_Re_Password.setError("Field can't be empty");
            return false;
        } else if (!txt_Password.getText().toString().equals(txt_Re_Password.getText().toString())) {
            txt_Re_Password.setError("doesn't match");
            return false;
        } else {
            txt_Re_Password.setError(null);
            return true;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                ImageUri = result.getUri();
                //Log.d("IMGEURI",String.valueOf(ImageUri));
                img_profile.setImageURI(ImageUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    private void open_Animation() {

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

        layout_main_comp.setAnimation(bounce);
    }

    private void close_Animation() {

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
        layout_main_comp.setAnimation(bounce);
    }

}
