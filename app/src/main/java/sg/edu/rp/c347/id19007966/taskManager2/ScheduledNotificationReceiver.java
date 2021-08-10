package sg.edu.rp.c347.id19007966.taskManager2;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;
import androidx.core.app.RemoteInput;

public class ScheduledNotificationReceiver extends BroadcastReceiver {

    int reqCode = 12345;

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.

        String data = intent.getStringExtra("data");
        int id = intent.getIntExtra("id", -1);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("default", "Default Channel", NotificationManager.IMPORTANCE_HIGH);

            channel.setDescription("This is for default notification");
            notificationManager.createNotificationChannel(channel);
        }

        Intent i = new Intent(context, MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(context, reqCode,
                i, PendingIntent.FLAG_CANCEL_CURRENT);



        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "default");
        builder.setContentTitle("Task");
        builder.setContentText(data);
        builder.setSmallIcon(android.R.drawable.ic_dialog_info);
        builder.setContentIntent(pIntent);
        builder.setAutoCancel(true);
        long[] v = {500,1000};
        builder.setVibrate(v);
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        builder.setSound(uri);
        builder.setLights(Color.BLUE, 2000, 1000);
        builder.setPriority(Notification.PRIORITY_HIGH);
        Bitmap image = BitmapFactory.decodeResource(context.getResources(), R.drawable.sentosa);
        Bitmap sideIco = BitmapFactory.decodeResource(context.getResources(), android.R.drawable.ic_dialog_map);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.notification_image);
        views.setImageViewBitmap(R.id.imageView, image);
        views.setTextViewText(R.id.textViewTitle, "Task Manager Reminder");
        views.setTextViewText(R.id.textViewSubtitle, data);
        views.setImageViewBitmap(R.id.imageViewSideIcon, sideIco);
        builder.setCustomContentView(views);
        builder.setCustomBigContentView(views);
        builder.setCustomHeadsUpContentView(views);

        NotificationCompat.Action action = new
                NotificationCompat.Action.Builder(
                R.mipmap.ic_launcher,
                "Launch Task Manager",
                pIntent).build();

        Intent intentreply = new Intent(context, WatchService.class);
        intentreply.putExtra("task_id", id);
        PendingIntent pendingIntentReply = PendingIntent.getService
                (context, 0, intentreply,
                        PendingIntent.FLAG_UPDATE_CURRENT);

        RemoteInput ri = new RemoteInput.Builder("status")
                .setLabel("Status report")
                .setChoices(new String [] {"Completed"})
                .build();

        NotificationCompat.Action action2 = new
                NotificationCompat.Action.Builder(
                R.mipmap.ic_launcher,
                "Reply",
                pendingIntentReply)
                .addRemoteInput(ri)
                .build();

        NotificationCompat.WearableExtender extender = new
                NotificationCompat.WearableExtender();

        extender.addAction(action);
        extender.addAction(action2);

        // Attach the action for Wear notification created above
        builder.extend(extender);

        Notification n = builder.build();
        notificationManager.notify(reqCode, n);
    }

}