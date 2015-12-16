package com.example.iths.asobi;

import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

/**
 * From this activity player can choose with which categories she wants to play
 */
public class GameModeActivity extends AppCompatActivity {

    private ListView listview;
    private DBHelper db;
    private MediaPlayer mp;
    public static String currentPlayer="Guest";
    private Player player = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_mode);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        setContentView(R.layout.activity_game_mode);

        player = Player.getPlayerInstance("Guest");
        currentPlayer = player.getName();

        db=DBHelper.getDbHelperInstance(this);

        listview = (ListView) findViewById(R.id.category_list);

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

            int length=db.getLengthOfQuestions(category) ;

            if(length<5){
                Toast.makeText(GameModeActivity.this, R.string.you_need_at_least_five_questions, Toast.LENGTH_SHORT).show();
            } else{

            Intent intent = new Intent(GameModeActivity.this, GameActivity.class);

            intent.putExtra(GameActivity.CATEGORY,category);
            startActivity(intent);
            }
        }
    };

    /**
     * Gets the actionbar.
     * @param menu the actionbar menu.
     * @return true so you can see the actionbar.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_my, menu);
        return true;
    }

    /**
     * Handles the item clicks here.
     * @param item is the symbol showed up on the actionbar.
     * @return returns true if clicked and takes you to the next activity.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_play:
                playClickSound();
                // Play action
                Intent i = new Intent(GameModeActivity.this, GameModeActivity.class);
                startActivity(i);
                return true;
            case R.id.info:
                playClickSound();
                // Asobi presentation activity
                Intent j = new Intent(GameModeActivity.this, AboutActivity.class);
                startActivity(j);
                return true;
            case R.id.profile:
                playClickSound();
                // Create profile activity
                Intent k = new Intent(GameModeActivity.this, ProfilesActivity.class);
                startActivity(k);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * gets all possible questions that is in the database.
     * @param view shows the questions it has gathered.
     */
    public void getQuestionsFromAll(View view) {
        Intent intent = new Intent(GameModeActivity.this, GameActivity.class);
        intent.putExtra(GameActivity.CATEGORY,"ALL");
        startActivity(intent);
    }

    /**
     * A method used for making the buttons do a sound when clicked.
     */
    public void playClickSound() {
        mp = MediaPlayer.create(this, R.raw.test);
        mp.start();
    }
}
