package com.example.touristguide.ShareItem;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;

import com.example.touristguide.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;

public class Next_Info_Item extends AppCompatActivity {

    private ImageView post_image;
    private FusedLocationProviderClient client;
    private TextView Clocation, Txt_categories;
    private EditText txt_Decription;
    private String[] Listitem;
    private ArrayList<String> ListSelectedItem;
    private ConstraintLayout linerVideo;
    private boolean[] Checkeditem;
    private Button addpost;
    private Location locationfinal;
    private VideoView mVideo;
    private FirebaseFirestore firebaseFirestore;
    private StorageReference storageReference;
    private ProgressBar UploadProgress;
    private FirebaseAuth mAuth;
    private ArrayList<Integer> mUserItem = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next__info__item);
        post_image = findViewById(R.id.img_next_post);
        txt_Decription = findViewById(R.id.txt_next_info_desc);
        mVideo = findViewById(R.id.Video_next_post);
        UploadProgress = findViewById(R.id.UploadProgress);
        UploadProgress.setVisibility(View.INVISIBLE);
        linerVideo = findViewById(R.id.linerVideo);
        ListSelectedItem = new ArrayList<>();
        final Intent intent = getIntent();
        final Uri img = Uri.parse(intent.getStringExtra("URI"));
        if (intent.getStringExtra("URI").contains(".mp4")) {
            post_image.setVisibility(View.GONE);
            linerVideo.setVisibility(View.VISIBLE);
            mVideo.setVideoURI(img);
            mVideo.requestFocus();
            mVideo.start();


            // ---------------------

            Uri mUri = null;
            try {
                Field mUriField = VideoView.class.getDeclaredField("mUri");
                mUriField.setAccessible(true);
                mUri = (Uri) mUriField.get(mVideo);


                Toast.makeText(getBaseContext(), String.valueOf(mUri), Toast.LENGTH_LONG).show();
            } catch (Exception e) {
            }

        } else {
            linerVideo.setVisibility(View.GONE);
            post_image.setVisibility(View.VISIBLE);
            if (intent.getIntExtra("Scale", 0) == 1) {
                post_image.setScaleType(ImageView.ScaleType.FIT_CENTER);

            } else
                post_image.setScaleType(ImageView.ScaleType.FIT_XY);

            post_image.setImageURI(img);
        }
        Toast.makeText(getBaseContext(), String.valueOf(img), Toast.LENGTH_LONG).show();
        //-----> FireBase Instance <-------//
        firebaseFirestore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        //-----> FireBase Instance <-------//

        Clocation = findViewById(R.id.txt_Next_info_current_loction);
        Txt_categories = findViewById(R.id.txt_categories_values);
        addpost = findViewById(R.id.btn_post_item);
        Listitem = getResources().getStringArray(R.array.Categort);

        Checkeditem = new boolean[Listitem.length];


        client = new FusedLocationProviderClient(this);

        requestPermission();

        Txt_categories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(Next_Info_Item.this);
                builder.setTitle("Please set at least on of these categories ");
                builder.setMultiChoiceItems(Listitem, Checkeditem, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position, boolean itemchecked) {
                        if (itemchecked) {
                            if (!mUserItem.contains(position))
                                mUserItem.add(position);
                            else
                                mUserItem.remove(position);

                        }

                    }
                });
                builder.setCancelable(false);
                builder.setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String item = "";
                        for (int x = 0; x < mUserItem.size(); x++) {
                            item += Listitem[mUserItem.get(x)];
                            ListSelectedItem.add(Listitem[mUserItem.get(x)]);
                            if (x < mUserItem.size() - 1) {
                                item += ",";
                            }
                        }
                        Txt_categories.setText(item);

                    }
                });
                AlertDialog mdialog = builder.create();
                mdialog.show();


            }
        });

        addpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UploadProgress.setVisibility(View.VISIBLE);
                byte[] data;
                final StorageReference imagepath;
                if (!intent.getStringExtra("URI").contains(".mp4")) {
                    post_image.setDrawingCacheEnabled(true);
                    post_image.buildDrawingCache();
                    Bitmap bitmap = ((BitmapDrawable) post_image.getDrawable()).getBitmap();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    data = baos.toByteArray();
                    imagepath = storageReference.child("Media_post").child(UUID.randomUUID().toString() + ".jpg");
                } else {
                    data = convertVideoToBytes(getBaseContext(), img);
                    imagepath = storageReference.child("Image_Post").child(UUID.randomUUID().toString() + ".mp4");
                }
                imagepath.putBytes(data)
                        .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                imagepath.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Uri> task) {
                                        String URL  = task.getResult().toString();
                                        String media_url =task.getResult().toString();
                                        Date DT = new Date();
                                        HashMap<String, Object> postmap = new HashMap<>();
                                        postmap.put("media_url", URL);
                                        postmap.put("Desc", txt_Decription.getText().toString());
                                        postmap.put("Date", DT);
                                        postmap.put("Location", locationfinal);
                                        postmap.put("Categories", ListSelectedItem);
                                        postmap.put("UserID", mAuth.getCurrentUser().getUid());

                                        firebaseFirestore.collection("post").add(postmap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                                Toast.makeText(getBaseContext(), "done , post was uploaded Successfully", Toast.LENGTH_LONG).show();
                                                UploadProgress.setVisibility(View.INVISIBLE);
                                            }
                                        });

                                    }
                                });
                            }
                        });


            }
        });
        if (ActivityCompat.checkSelfPermission(Next_Info_Item.this, ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            return;
        else {
            client.getLastLocation().addOnSuccessListener(Next_Info_Item.this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {

                    try {
                        locationfinal = location;
                        Geocoder geocoder = new Geocoder(Next_Info_Item.this, Locale.getDefault());
                        List<Address> address = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);


                        Clocation.setText(address.get(0).getAdminArea());
                        Log.d("ContryName", address.get(0).getAdminArea());


                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            });
        }


    }


    public static byte[] convertVideoToBytes(Context context, Uri uri) {
        byte[] videoBytes = null;
        try {//  w  w w  . j ava 2s . c  o m
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            FileInputStream fis = new FileInputStream(new File(String.valueOf(uri)));
            byte[] buf = new byte[1024];
            int n;
            while (-1 != (n = fis.read(buf)))
                baos.write(buf, 0, n);

            videoBytes = baos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return videoBytes;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{ACCESS_COARSE_LOCATION}, 1);
    }
}
