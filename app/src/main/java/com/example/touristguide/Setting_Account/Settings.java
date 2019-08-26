package com.example.touristguide.Setting_Account;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.touristguide.R;
import com.example.touristguide.ShareItem.Next_Info_Item;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class Settings extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseFirestore;
    private TextView txt_userName, txt_Edit_Profile;
    LinearLayout txt_notification_settings;
    private CircleImageView prfile_Image;

    private SharedPreferences mSharedPreferences;
    private  SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        prfile_Image = findViewById(R.id.pic_User_settings);
        txt_userName = findViewById(R.id.txt_Username_Settings);
        txt_Edit_Profile = findViewById(R.id.txt_Edit_Profile);
        txt_notification_settings = findViewById(R.id.txt_notification_Settings);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = mSharedPreferences.edit();



        getResources().getStringArray(R.array.selectedNotification);


        final String [] Notification =getResources().getStringArray(R.array.NotificationSettings);
       final boolean[] Checkeditem = new boolean[Notification.length];




        txt_notification_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Settings.this);
                builder.setTitle("Please set at least on of these categories ");
                builder.setMultiChoiceItems(Notification, Checkeditem, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position, boolean itemchecked) {
                        if (itemchecked) {

                        }

                    }
                });
                builder.setCancelable(false);
                builder.setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


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
}
