package edu.atelier.technique.notifications;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import java.util.Objects;
import edu.atelier.technique.R;

/**
 * Cette class permet d'isoler / stocker les fonctions relatives aux notifications
 */
public class Notifications extends android.app.Application {


    public static final String CHANNEL_1_ID = "channel1";
    private static NotificationManager notificationManager;


    /**
     * récupère le [ NotificationManager ]
     * @return notificationManager
     */
    public static NotificationManager getNotificationManager() {
        return notificationManager;
    }

    /**
     * envoie une notification sur le cannal
     */
    private void createNotificationChannel() {
        //Suppression des restriction API car l' application est dédiée à API +28
        NotificationChannel channel = new NotificationChannel(CHANNEL_1_ID, getString(R.string.AddPublicationProblemTextTitle), NotificationManager.IMPORTANCE_HIGH);
        channel.setDescription(getString(R.string.AddPublicationProblemText));
        notificationManager = getSystemService((NotificationManager.class));
        Objects.requireNonNull(notificationManager).createNotificationChannel(channel);
    }

    /**
     * Constructeur     */
    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
    }
}
