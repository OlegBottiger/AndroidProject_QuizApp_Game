package com.example.iths.asobi;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;

public class GameModeActivity extends AppCompatActivity {

    private ArrayList<String> category;
    private ArrayAdapter<String> adapter;
    private ListView listview;
    private DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_mode);

        //Set actionbar item
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        setContentView(R.layout.activity_game_mode);

        category = new ArrayList<String>();

        category.add("Sports");
        category.add("Music");
        category.add("Science");
        category.add("Geography");
        category.add("Mathematics");
        category.add("Games");

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, category);
        listview = (ListView) findViewById(R.id.category_list);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(listListener);

        /*
        listview = (ListView) findViewById(R.id.category_list);
        db=DBHelper.getDbHelperInstance(this);

        Cursor categories = db.getAllTable("allCategories");
        String [] from = {"category"};
        int [] to = {R.id.testList};
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.test_category_list,categories ,from, to, 0);

        listview.setAdapter(adapter);
        */
    }

    private AdapterView.OnItemClickListener listListener = new AdapterView.OnItemClickListener(){

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id){

            String category = (String)parent.getItemAtPosition(position);
            Intent intent = new Intent(GameModeActivity.this, GameActivity.class);

            intent.putExtra(GameActivity.CATEGORY,category);
            startActivity(intent);

        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.



        switch (item.getItemId()) {
            case R.id.action_play:
                // Play action
                Intent i = new Intent(GameModeActivity.this, GameModeActivity.class);
                startActivity(i);
                return true;
            case R.id.info:
                // Asobi presentation activity
                Intent j = new Intent(GameModeActivity.this, AboutActivity.class);
                startActivity(j);
                return true;
            case R.id.profile:
                // Create profile activity
                Intent k = new Intent(GameModeActivity.this, ProfilesActivity.class);
                startActivity(k);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }


    }

    public void getQuestionsFromAll(View view) {
        Intent intent = new Intent(GameModeActivity.this, GameActivity.class);

        intent.putExtra(GameActivity.CATEGORY,"ALL");
        startActivity(intent);
    }
}
