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
        boolean addNew = intent.getBooleanExtra("isAdd", false);

        if (remoteInput != null) {
            CharSequence reply = remoteInput.getCharSequence("userResponse");
            String strReply = reply.toString();
            if (reply != null) {
                if (addNew) {
                    CharSequence replyDesc = remoteInput.getCharSequence("userResponse2");
                    String strDesc = replyDesc.toString();
                    if (replyDesc != null) {
                        add(db, strReply, strDesc);
                    }

                }
                else {
                    if (strReply.equalsIgnoreCase("completed")) {
                        delete(db, id);
                    }
                }
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void add(DBHelper db, String name, String description) {
        Task task = db.insertTask(name, description);
        if (task != null) {
            NotificationManager manager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
            manager.cancelAll(); // hide notification once done interacting
        }
    }

    private void delete(DBHelper db, int id) {
        if (id != -1) {
            db.deleteAt(id);
            NotificationManager manager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
            manager.cancelAll(); // hide notification once done interacting
        }
    }
}