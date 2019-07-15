package com.example.touristguide.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.touristguide.MainActivity;
import com.example.touristguide.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class SignUp extends AppCompatActivity {

    private EditText txt_username, txt_password, txt_confirmation, txt_email;
    private Button btn_signup;
    private FirebaseAuth mAuth;
    private CircleImageView profile_Image;
    private Uri ImageUri = null;
    private String UserID = null;


    // FireBase Objects //
    private FirebaseFirestore firebaseFirestore ;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        txt_username = findViewById(R.id.txt_Username);
        txt_password = findViewById(R.id.txt_password);
        txt_confirmation = findViewById(R.id.txt_confirm_pass);
        txt_email = findViewById(R.id.txt_Email);

        btn_signup = findViewById(R.id.btn_SignUp);

        profile_Image = findViewById(R.id.profile_image);


        profile_Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setCropShape(CropImageView.CropShape.OVAL)
                        .start(SignUp.this);
            }
        });
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mAuth.createUserWithEmailAndPassword(txt_email.getText().toString(), txt_password.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                UserID = mAuth.getUid();

                                createuser();
                            }
                        });

            }
        });

    }

    private void createuser() {

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
                                userMap.put("Username" , txt_username.getText().toString());
                                userMap.put("Image_User" ,imageurl);

                                firebaseFirestore.collection("User") // add user info to firebase
                                        .document(UserID).set(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful())
                                        {
                                            Toast.makeText(getBaseContext(),"User was registered",Toast.LENGTH_LONG).show();
                                            finish();
                                            Intent intent = new Intent(SignUp.this , MainActivity.class);
                                            startActivity(intent);
                                        }
                                    }
                                });

                            }
                        });
                    }
                });


    }


    // ----------- Open Copper Activity ------------ //
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                ImageUri = result.getUri();
                profile_Image.setImageURI(ImageUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
}
