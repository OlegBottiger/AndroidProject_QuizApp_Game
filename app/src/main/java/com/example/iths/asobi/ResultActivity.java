package com.example.iths.asobi;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

/**
 * This activity shows the result of the game.
 * Player can go to see the high score, replay again and go to the info.
 */
public class ResultActivity extends AppCompatActivity {

    private DBHelper db;
    private TextView tvRank;
    private MediaPlayer mp;
    public static final String FINAL_SCORE = "finalScore";
    public static final String CORRECT_ANSWERS = "correctAnswers";
    public static final String MINUTES = "minutes";
    public static final String SECONDS = "seconds";
    public static final String CATEGORY = "category" ;
    private String name="";
    private Player player;
    public static String currentPlayer = "Guest";
    private MediaPlayer mpb;


    /**
     * Displays the game results and saves the score in the high score table.
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        if(mp != null){
            if(mp.isPlaying()){
                mp.stop();
            }
         }

        //Set actionbar item
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        setContentView(R.layout.activity_result);

        player = Player.getPlayerInstance("Guest");
        currentPlayer = player.getName();

        db=DBHelper.getDbHelperInstance(this);
        player = Player.getPlayerInstance("Guest");

        Intent intent = getIntent();

        int finalScore = intent.getIntExtra(FINAL_SCORE, 0);
        int correctAnswers = intent.getIntExtra(CORRECT_ANSWERS, 0);
        int minutes = intent.getIntExtra(MINUTES, 0);
        int seconds = intent.getIntExtra(SECONDS, 0);
        String category = intent.getStringExtra(CATEGORY);
        name = player.getName();
        int rank= db.getRank(db.getHighScore(category), finalScore);

        TextView tvRank = (TextView) findViewById(R.id.rank);
        tvRank.setText(String.format(getString(R.string.you_are_rank), rank));
        TextView scoreView = (TextView) findViewById(R.id.score);
        scoreView.setText(String.format(getString(R.string.total_score), finalScore));
        TextView correctView = (TextView) findViewById(R.id.correct_anwsers);
        correctView.setText(String.format(getString(R.string.correct_answers), correctAnswers));
        TextView timeView = (TextView) findViewById(R.id.time);
        timeView.setText(String.format(getString(R.string.minutes_seconds), minutes, seconds));;

        int lastDigit = rank % 10;

        if(finalScore > 0){
            tvRank = (TextView) findViewById(R.id.rank);
            if(lastDigit == 1) {
                tvRank.setText(String.format(getString(R.string.you_placed_xst_place_on_high_score), rank,category));
            }
            else if (lastDigit == 2 && rank != 12) {
                tvRank.setText(String.format(getString(R.string.you_placed_xnd_place_on_high_score), rank,category));
            }
            else if (lastDigit == 3 && rank != 13) {
                tvRank.setText(String.format(getString(R.string.you_placed_xrd_place_on_high_score), rank,category));
            } else {
                tvRank.setText(String.format(getString(R.string.you_placed_xth_place_on_high_score), rank,category));
            }
        db.addHighScore(name, finalScore, db.getIdByCategoryName(category));

        }else{
            tvRank = (TextView) findViewById(R.id.rank);
            tvRank.setText(R.string.you_can_not_be_ranked);
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
                Intent i = new Intent(ResultActivity.this, GameModeActivity.class);
                startActivity(i);
                return true;
            case R.id.info:
                playClickSound();
                // Asobi presentation activity
                Intent j = new Intent(ResultActivity.this, AboutActivity.class);
                startActivity(j);
                return true;
            case R.id.profile:
                playClickSound();
                // Create profile activity
                Intent k = new Intent(ResultActivity.this, ProfilesActivity.class);
                startActivity(k);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Goes to High Score List.
     * @param view
     */

    public void goToHighScore(View view) {
        Intent i = new Intent(ResultActivity.this, HighscoreActivity.class);
        startActivity(i);
    }

    /**
     * Returns to game mode selection.
     * @param view
     */

    public void replay(View view) {
        Intent i = new Intent(ResultActivity.this, GameModeActivity.class);
        startActivity(i);
    }

    /**
     * Returns to main menu.
     * @param view
     */

    public void goToMainMenu(View view) {
        Intent i = new Intent(ResultActivity.this, MainActivity.class);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, GameModeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
    /**
     * A method used for making the actionbar buttons do a sound when clicked.
     */
    public void playClickSound() {
        mpb = MediaPlayer.create(this, R.raw.test);
        mpb.start();
    }
}
