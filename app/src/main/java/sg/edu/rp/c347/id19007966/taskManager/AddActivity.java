package sg.edu.rp.c347.id19007966.taskManager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class AddActivity extends AppCompatActivity {

    EditText etName, etDescription, etRemind;
    Button btnAddTask, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        etName = (EditText) findViewById(R.id.etName);
        etDescription = (EditText) findViewById(R.id.etDescription);
        btnAddTask = (Button) findViewById(R.id.btnAddTask);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        final int reqCode = 12345;

        etRemind = (EditText) findViewById(R.id.etRemind);

        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                String name = etName.getText().toString();
                String description = etDescription.getText().toString();
                String reminder = etRemind.getText().toString();
                int reminderTime = Integer.parseInt(reminder);

                DBHelper db = new DBHelper(AddActivity.this);
                db.insertTask(name, description);
                Task task = new Task(name, description);
                i.putExtra("task", task);
                setResult(RESULT_OK, i);
                // Go back 1st page
                finish();

                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.SECOND, reminderTime);

                Intent intent = new Intent(AddActivity.this,
                        ScheduledNotificationReceiver.class);
                intent.putExtra("name", name);

                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                        AddActivity.this, reqCode,
                        intent, PendingIntent.FLAG_CANCEL_CURRENT);

                AlarmManager am = (AlarmManager)
                        getSystemService(Activity.ALARM_SERVICE);
                am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
                        pendingIntent);


            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}