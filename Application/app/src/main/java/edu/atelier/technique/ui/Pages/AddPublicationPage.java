package edu.atelier.technique.ui.Pages;

import android.Manifest;
import static edu.atelier.technique.notifications.Notifications.CHANNEL_1_ID;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.app.NotificationCompat;

import edu.atelier.technique.HomePage;
import edu.atelier.technique.R;
import edu.atelier.technique.notifications.Notifications;

public class AddPublicationPage extends AppCompatActivity {

    private int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_publication);

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, 100);
            Log.d("Camera Permission","Denied");
        }else{
            Log.d("Camera Permission","Granted");
        }

        getSupportActionBar().hide();

        this.findViewById(R.id.back).setOnClickListener(click -> {
            super.onBackPressed();
        });

        this.findViewById(R.id.container_advertissement).setOnClickListener(click -> {
            startActivity(new Intent(getApplicationContext(), InfoPage.class));
        });

        this.findViewById(R.id.YES).setOnClickListener(click -> {
            //TODO : ajouter la photo à la base de donnée
            sendNotificationOnChannel(getString(R.string.appName), getString(R.string.notif_message), this);
            startActivity(new Intent(getApplicationContext(), HomePage.class));
        });

        this.findViewById(R.id.NO).setOnClickListener(click -> {
            super.onBackPressed();
        });
    }

    public void sendNotificationOnChannel(String title, String message, Context context) {
        NotificationCompat.Builder notification = new NotificationCompat.Builder(context, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.informations_icon)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH);
        Notifications.getNotificationManager().notify(++id, notification.build());
    }
}