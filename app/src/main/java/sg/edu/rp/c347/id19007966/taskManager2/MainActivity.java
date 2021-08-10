package sg.edu.rp.c347.id19007966.taskManager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnAdd;
    ListView lv;
    ArrayAdapter<String> aa;
    ArrayList<String> al;
    ArrayList<Task> tasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = findViewById(R.id.btnAdd);
        lv = findViewById(R.id.lv);

        DBHelper db = new DBHelper(MainActivity.this);
        al = new ArrayList<String>();
        al = db.retrieveAllStrings();
        tasks = db.retrieveAllTasks();
        db.close();
        aa = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,al);
        lv.setAdapter(aa);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AddActivity.class);
                startActivity(intent);
            }
        });

        lv.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent(MainActivity.this, AddActivity.class);
            intent.putExtra("task", tasks.get(i));
            startActivity(intent);
        });

    }
    @Override
    protected void onResume() {
        super.onResume();
        refreshList();
    }

    private void refreshList() {
        DBHelper db = new DBHelper(MainActivity.this);
        al.clear();
        al.addAll(db.retrieveAllStrings());
        tasks.clear();
        tasks.addAll(db.retrieveAllTasks());
        db.close();
        aa.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_refresh) {
            refreshList();
        }
        return false;
    }
}