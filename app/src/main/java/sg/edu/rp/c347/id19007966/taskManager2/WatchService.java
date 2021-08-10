package sg.edu.rp.c347.id19007966.taskManager2;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

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

        return super.onStartCommand(intent, flags, startId);
    }
}