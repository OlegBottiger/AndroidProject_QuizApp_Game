package com.example.iths.asobi;

import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;

/**
 * This activity shows high scores.
 * It shows rank, name and scores.
 */
public class ShowHighScoreActivity extends AppCompatActivity {
    private ListView listview;
    private DBHelper db;
    private SimpleCursorAdapter adapter;
    public static final String CATEGORY= "category";
    private String category;
    private  Cursor cursor;
    public static String currentPlayer = "Guest";
    private MediaPlayer mpb;
    private Player player = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_high_score);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        player = Player.getPlayerInstance("Guest");
        currentPlayer = player.getName();

        listview = (ListView) findViewById(R.id.show_high_score);
        db=DBHelper.getDbHelperInstance(this);

        Intent intent = getIntent();
        category = intent.getStringExtra(CATEGORY);

            if (category.equals("ALL")){
                cursor = db.getHighScoreTable();
            }else{
                cursor= db.getCategorisedTable(category);
            }

        String [] from = {db.getNameKey(),db.getScoreKey()};
        int [] to = {R.id.high_score_name,R.id.high_score_point};
        adapter = new SimpleCursorAdapter(this, R.layout.high_scores_list_item,cursor,from, to, 0);

        listview.setAdapter(adapter);

        //refresh RANK_TABLE
        db.deleteRank();
        ArrayList<Integer> highscore = db.getHighScore(category);

            if(highscore.get(0)!= 0) {

                for (int i = 0; i < highscore.size(); i++) {
                    db.insertRank(db.getRank(highscore, highscore.get(i)));
                }
                cursor = db.getOneTable(db.getRankTable());
                ListView rank = (ListView) findViewById(R.id.show_rank);

                String[] from2 = {db.getRankKey()};
                int[] to2 = {R.id.high_score_rank_test};
                adapter = new SimpleCursorAdapter(this, R.layout.rank_list_item, cursor, from2, to2, 0);
                rank.setAdapter(adapter);
            }
    }

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
                Intent i = new Intent(ShowHighScoreActivity.this, GameModeActivity.class);
                startActivity(i);
                return true;
            case R.id.info:
                playClickSound();
                // Asobi presentation activity
                Intent j = new Intent(ShowHighScoreActivity.this, AboutActivity.class);
                startActivity(j);
                return true;
            case R.id.profile:
                playClickSound();
                // Create profile activity
                Intent k = new Intent(ShowHighScoreActivity.this, ProfilesActivity.class);
                startActivity(k);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * A method used for making the actionbar buttons do a sound when clicked.
     */
    public void playClickSound() {
        mpb = MediaPlayer.create(this, R.raw.test);
        mpb.start();
    }

}
