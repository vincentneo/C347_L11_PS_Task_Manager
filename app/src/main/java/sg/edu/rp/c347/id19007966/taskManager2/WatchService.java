package sg.edu.rp.c347.id19007966.taskManager2;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;

import androidx.core.app.RemoteInput;

public class WatchService extends Service {

    public WatchService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        System.out.println(intent);
        DBHelper db = new DBHelper(getApplicationContext());
        Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);
        int id = intent.getIntExtra("task_id", -1);

        if (remoteInput != null){
            CharSequence reply = remoteInput.getCharSequence("status");
            if (reply != null) {
                String status = reply.toString();
                if (status.equalsIgnoreCase("completed")) {
                    delete(db, id);
                }
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void delete(DBHelper db, int id) {
        if (id != -1) {
            db.deleteAt(id);
            NotificationManager manager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
            manager.cancelAll(); // hide notification once done interacting
        }
    }
}