package com.example.touristguide.Notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Message;
import android.view.textclassifier.ConversationActions;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.touristguide.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class MyMessageService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);


        shownotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
    }


    public void shownotification(String Title, String Message) {


        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "MyNotification")
                .setContentTitle(Title)
                .setContentText(Message)
                .setSmallIcon(R.drawable.logo)
                .setSound(alarmSound)
                .setAutoCancel(true);
//builder.addAction(Toast.makeText(getApplicationContext(),"Hello",Toast.LENGTH_LONG).show());
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(99999, builder.build());
    }
}
