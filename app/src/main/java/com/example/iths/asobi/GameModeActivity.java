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
import android.widget.Toast;

public class GameModeActivity extends AppCompatActivity {

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

        db=DBHelper.getDbHelperInstance(this);

        listview = (ListView) findViewById(R.id.category_list);

        Cursor categories = db.getOneTable("allCategories");
        String [] from = {"category"};
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
            String category = cur.getString(cur.getColumnIndex("category"));

            int length=db.getLengthOfQuestions(category) ;

            if(length<5){
                Toast.makeText(GameModeActivity.this, "You need at least 5 questions in this category!", Toast.LENGTH_SHORT).show();
            } else{

            Intent intent = new Intent(GameModeActivity.this, GameActivity.class);

            intent.putExtra(GameActivity.CATEGORY,category);
            startActivity(intent);

            //else ... stay here and show message?
            }

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
