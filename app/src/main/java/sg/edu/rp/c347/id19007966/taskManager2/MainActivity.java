package sg.edu.rp.c347.id19007966.taskManager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnAdd;
    ListView lv;
    ArrayAdapter<String> aa;
    ArrayList<String> al;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = findViewById(R.id.btnAdd);
        lv = findViewById(R.id.lv);

        DBHelper db = new DBHelper(MainActivity.this);
        al = new ArrayList<String>();
        al = db.retrieveAllStrings();
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

    }
    @Override
    protected void onResume() {
        super.onResume();
        DBHelper db = new DBHelper(MainActivity.this);
        al.clear();
        al.addAll(db.retrieveAllStrings());
        db.close();
        aa.notifyDataSetChanged();

    }
}