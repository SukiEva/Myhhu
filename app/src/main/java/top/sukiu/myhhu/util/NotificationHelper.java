package top.sukiu.myhhu.util;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Color;
import android.os.Build;

import androidx.annotation.RequiresApi;

import top.sukiu.myhhu.R;

public class NotificationHelper extends ContextWrapper {
    public static final String PRIMARY_CHANNEL_ID = "default";
    public static final String SECONDARY_CHANNEL_ID = "second";
    private NotificationManager manager;
    private Notification.Builder mBuilder;


    @RequiresApi(api = Build.VERSION_CODES.O)
    public NotificationHelper(Context ctx) {
        super(ctx);

        NotificationChannel chan1 = new NotificationChannel(PRIMARY_CHANNEL_ID,
                getString(R.string.app_name), NotificationManager.IMPORTANCE_DEFAULT);
        chan1.setLightColor(Color.GREEN);
        chan1.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        getManager().createNotificationChannel(chan1);

        NotificationChannel chan2 = new NotificationChannel(SECONDARY_CHANNEL_ID,
                getString(R.string.app_name), NotificationManager.IMPORTANCE_HIGH);
        chan2.setLightColor(Color.BLUE);
        chan2.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
        getManager().createNotificationChannel(chan2);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Notification.Builder getNotificationPublic(String title, String body) {
        return this.mBuilder = new Notification.Builder(getApplicationContext(), SECONDARY_CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(getSmallIcon())
                .setAutoCancel(true);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Notification.Builder getNotificationPrivate(String title, String body) {
        return this.mBuilder = new Notification.Builder(getApplicationContext(), PRIMARY_CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(getSmallIcon())
                .setAutoCancel(true);
    }


    public void notify(int id, Notification.Builder notification) {
        getManager().notify(id, notification.build());
    }

    public void notify(int id) {
        getManager().notify(id, this.mBuilder.build());
    }

    private int getSmallIcon() {
        return android.R.drawable.stat_sys_download;
    }

    private NotificationManager getManager() {
        if (manager == null) {
            manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return manager;
    }
}