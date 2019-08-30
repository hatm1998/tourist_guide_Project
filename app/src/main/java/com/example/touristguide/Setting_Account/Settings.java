package com.example.touristguide.Setting_Account;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.touristguide.Navigation_Drawer;
import com.example.touristguide.R;
import com.example.touristguide.ShareItem.Next_Info_Item;
import com.example.touristguide.Utilis.LoacalHelper;
import com.facebook.share.Share;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;

public class Settings extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseFirestore;
    private TextView txt_userName, txt_Edit_Profile, txt_language;
    LinearLayout txt_notification_settings;
    private CircleImageView prfile_Image;
    private ArrayList<Integer> mUserItem = new ArrayList<>();
    private String shared = "Sharedperf";
    String Lang ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mAuth = FirebaseAuth.getInstance();
        txt_language = findViewById(R.id.txt_Language_Settings);
        firebaseFirestore = FirebaseFirestore.getInstance();
        prfile_Image = findViewById(R.id.pic_User_settings);
        txt_userName = findViewById(R.id.txt_Username_Settings);
        txt_Edit_Profile = findViewById(R.id.txt_Edit_Profile);
        txt_notification_settings = findViewById(R.id.txt_notification_Settings);


        LoadLocal();

        getResources().getStringArray(R.array.selectedNotification);


        final String[] Notification = getResources().getStringArray(R.array.NotificationSettings);
        final boolean[] Checkeditem = new boolean[Notification.length];

        final String[] ListLanguage = {"Arabic", "English"};
        txt_language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Settings.this);
                builder.setTitle("Please set at least on of these categories ");

                builder.setSingleChoiceItems(ListLanguage, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (i == 0 ) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(Settings.this);
                            builder.setTitle(getResources().getString(R.string.warningdialog));
                            builder.setMessage(getResources().getString(R.string.ChangeLang));

                            builder.setPositiveButton(getResources().getString(R.string.Yes), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    setLocal("ar");
                                    finish();
                                    Intent intent = new Intent(Settings.this, Navigation_Drawer.class);
                                    startActivity(intent);

                                }
                            });
                            builder.setNegativeButton(getResources().getString(R.string.No), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // this line just to log file
                                }
                            });
                            builder.create().show();


                        } else if (i == 1) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(Settings.this);
                            builder.setTitle(getResources().getString(R.string.warningdialog));
                            builder.setMessage(getResources().getString(R.string.ChangeLang));

                            builder.setPositiveButton(getResources().getString(R.string.Yes), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    setLocal("en");
                                    finish();
                                    Intent intent = new Intent(Settings.this, Navigation_Drawer.class);
                                    startActivity(intent);

                                }
                            });
                            builder.setNegativeButton(getResources().getString(R.string.No), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // this line just to log file
                                }
                            });
                            builder.create().show();
                        }

                        dialogInterface.dismiss();
                    }

                });
                AlertDialog mdialog = builder.create();
                mdialog.show();

            }
        });
        for (int x = 0; x < Notification.length; x++) {
            SharedPreferences sharedPreferences = getSharedPreferences(shared, MODE_PRIVATE);
            Log.d("NumberNotiy", String.valueOf(x));
            if (sharedPreferences.getBoolean(Notification[x], true)) {
                Checkeditem[x] = true;
            }
            Log.d("NotiyType", String.valueOf(sharedPreferences.getBoolean(Notification[x], true)));
        }

        txt_notification_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Settings.this);
                builder.setTitle("Please set at least on of these categories ");
                builder.setMultiChoiceItems(Notification, Checkeditem, new DialogInterface.OnMultiChoiceClickListener() {
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
                        for (int x = 0; x < mUserItem.size(); x++) {
                            SharedPreferences sharedPreferences = getSharedPreferences(shared, MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            editor.putBoolean(Notification[mUserItem.get(x)], true);
                            Log.d("PutBoolen", Notification[mUserItem.get(x)]);

                        }


                    }
                });
                AlertDialog mdialog = builder.create();
                mdialog.show();

            }
        });
        firebaseFirestore.collection("User").document(mAuth.getCurrentUser().getUid())
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                Picasso.get().load(task.getResult().get("Image_User").toString()).into(prfile_Image);
                txt_userName.setText(task.getResult().get("Username").toString());

            }
        });

        txt_Edit_Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Settings.this, Setting_Account.class);
                startActivity(intent);
            }
        });

    }

    private void setLocal(String lang) {

        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;

        getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());

        SharedPreferences.Editor editor = getSharedPreferences("Setting", MODE_PRIVATE).edit();
        editor.putString("My_Lang", lang);
        editor.apply();

    }

    public void LoadLocal() {
        SharedPreferences preferences = getSharedPreferences("Setting", Activity.MODE_PRIVATE);
         Lang = preferences.getString("My_Lang", "en");
        Drawable img = null;
        if (Lang.equals("ar")) {
             img = getResources().getDrawable(R.drawable.jordan_flags);
        }else {
             img = getResources().getDrawable(R.drawable.uk);
        }
        txt_language.setCompoundDrawablesRelative(img,null,null,null);


        setLocal(Lang);
    }

}
