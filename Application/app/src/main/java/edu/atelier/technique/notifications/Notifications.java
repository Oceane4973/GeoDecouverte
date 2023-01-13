package edu.atelier.technique.notifications;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import java.util.Objects;

public class Notifications extends android.app.Application {

    public static final String CHANNEL_1_ID = "channel1";
    private static NotificationManager notificationManager;

    public static NotificationManager getNotificationManager() {
        return notificationManager;
    }

    private void createNotificationChannel() {
        // for API 26+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_1_ID, "Vous avez rajouté une photo", NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("La publication à bien été posté");
            notificationManager = getSystemService((NotificationManager.class));
            Objects.requireNonNull(notificationManager).createNotificationChannel(channel);
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
    }
}
