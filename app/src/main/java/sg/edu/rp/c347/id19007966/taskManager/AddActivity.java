package sg.edu.rp.c347.id19007966.taskManager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {

    EditText taskname, desc;
    Button add, cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        taskname = findViewById(R.id.editTextTaskname);
        desc = findViewById(R.id.editTextDescr);
        add = findViewById(R.id.buttonAdd);
        cancel = findViewById(R.id.buttonCancel);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper dbh = new DBHelper(AddActivity.this);

                Intent intent = new Intent(AddActivity.this, MainActivity.class);
                String row_affected = String.valueOf(dbh.insertTask(taskname.getText().toString(),  desc.getText().toString()));
                intent.putExtra("Inserted!", row_affected);
                startActivity(intent);
                dbh.close();
                Toast.makeText(getBaseContext(), "Inserted", Toast.LENGTH_SHORT).show();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


    }
}