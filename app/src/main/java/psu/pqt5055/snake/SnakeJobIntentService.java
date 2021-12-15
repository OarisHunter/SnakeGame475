package psu.pqt5055.snake;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.JobIntentService;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Random;

public class SnakeJobIntentService extends JobIntentService {

    private final String CHANNEL_ID = "channel_leave";
    private final int NOTIFICATION_ID = 0;

    public static void startJob(Context context) {
        Intent intent = new Intent(context, SnakeJobIntentService.class);
        enqueueWork(context, SnakeJobIntentService.class, 0, intent);
    }

    @Override
    protected void onHandleWork(Intent intent) {
        createSnakeNotificationChannel();
        String[] array = getResources().getStringArray(R.array.notifications);
        String message = array[new Random().nextInt(array.length)];
        createLeaveNotification(message);
    }

    private void createSnakeNotificationChannel() {
        if (Build.VERSION.SDK_INT < 26) return;

        CharSequence name = getString(R.string.channel_name);
        String description = getString(R.string.channel_description);
        int importance = NotificationManager.IMPORTANCE_LOW;
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
        channel.setDescription(description);

        // Register channel with system
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }

    private void createLeaveNotification(String text) {
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.nav_game_icon)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        notificationManager.notify(NOTIFICATION_ID, notification);
    }
}
