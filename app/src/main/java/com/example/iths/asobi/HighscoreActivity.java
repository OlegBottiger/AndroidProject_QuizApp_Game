package com.example.iths.asobi;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class HighscoreActivity extends AppCompatActivity {

    private ListView listview;
    private DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);


        //Set actionbar item
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        setContentView(R.layout.activity_highscore);


        db=DBHelper.getDbHelperInstance(this);
        listview = (ListView) findViewById(R.id.high_score_list_view);

        Cursor categories = db.getOneTable(db.getAllCategoryTable());
        String [] from = {db.getCategoryKey()};
        int [] to = {R.id.category};
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.category_list_item,categories ,from, to, 0);

        listview.setAdapter(adapter);
        listview.setOnItemClickListener(listListener);


    }

    private AdapterView.OnItemClickListener listListener = new AdapterView.OnItemClickListener(){

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id){


            Cursor cur = (Cursor) parent.getItemAtPosition(position);
            cur.moveToPosition(position);
            String category = cur.getString(cur.getColumnIndex(db.getCategoryKey()));

            Intent intent = new Intent(HighscoreActivity.this, ShowHighScoreActivity.class);

            intent.putExtra(ShowHighScoreActivity.CATEGORY,category);
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
                Intent i = new Intent(HighscoreActivity.this, GameModeActivity.class);
                startActivity(i);
                return true;
            case R.id.info:
                // Asobi presentation activity
                Intent j = new Intent(HighscoreActivity.this, AboutActivity.class);
                startActivity(j);
                return true;
            case R.id.profile:
                // Create profile activity
                Intent k = new Intent(HighscoreActivity.this, ProfilesActivity.class);
                startActivity(k);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }


    }
    public void showHighScores(View view) {
        Intent intent = new Intent(this, ShowHighScoreActivity.class);
        intent.putExtra(ShowHighScoreActivity.CATEGORY, "ALL");
        startActivity(intent);
    }
}
