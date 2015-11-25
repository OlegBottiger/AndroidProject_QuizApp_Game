package com.example.iths.asobi;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class ProfilesActivity extends AppCompatActivity {

    private ListView listView;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profiles);

        listView = (ListView) findViewById(R.id.profile_list);
        dbHelper = new DBHelper(this);

        Cursor players = dbHelper.getPlayers();
        String [] from = {dbHelper.NAME_KEY};
        int [] to = {R.id.profile_name};
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.profile_list_item,players ,from, to, 0);

        listView.setAdapter(adapter);

    }
}
