package com.example.touristguide.Notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.os.Message;
import android.view.textclassifier.ConversationActions;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.touristguide.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyMessageService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);


        shownotification(remoteMessage.getNotification().getTitle() , remoteMessage.getNotification().getBody()  );
    }

    public void shownotification(String Title , String Message)
    {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"MyNotification")
                .setContentTitle(Title)
                .setContentText(Message)
                .setSmallIcon(R.drawable.logo)
                .setAutoCancel(true);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(99999,builder.build());
    }
}
