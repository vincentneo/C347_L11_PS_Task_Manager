package sg.edu.rp.c347.id19007966.taskManager2;

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

        etRemind = (EditText) findViewById(R.id.etRemind);

        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etName.getText().toString();
                String desc = etDescription.getText().toString();
                DBHelper dbh = new DBHelper(AddActivity.this);
                Task insertTask = dbh.insertTask(name, desc);
                dbh.close();
                if (insertTask.getId() != -1) {
                    if (etRemind.getText().toString().trim().isEmpty()) {
                        Toast.makeText(AddActivity.this, "Please input alert time in seconds", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    showNotification(Integer.parseInt(etRemind.getText().toString()), insertTask);
                    Toast.makeText(AddActivity.this, "Added successfully",
                            Toast.LENGTH_SHORT).show();
                    etName.setText("");
                    etDescription.setText("");
                    etRemind.setText("");
                    Intent i = new Intent();
                    setResult(RESULT_OK, i);

                    finish();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void showNotification(int time, Task task){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.SECOND, time);

        Intent intent = new Intent(AddActivity.this, ScheduledNotificationReceiver.class);
        intent.putExtra("data", task.getName());
        intent.putExtra("id", task.getId());
        int requestCode = 12345;
        PendingIntent pIntent = PendingIntent.getBroadcast(AddActivity.this, requestCode, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        AlarmManager am = (AlarmManager) getSystemService(Activity.ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pIntent);

        setResult(RESULT_OK, intent);

        finish();
    }
}