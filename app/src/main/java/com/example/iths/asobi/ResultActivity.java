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

public class ResultActivity extends AppCompatActivity {

    private DBHelper db;
    private TextView tvRank;
    private MediaPlayer mp;
    public static final String FINAL_SCORE = "finalScore";
    public static final String CORRECT_ANSWERS = "correctAnswers";
    public static final String MINUTES = "minutes";
    public static final String SECONDS = "seconds";
    public static final String CATEGORY = "category" ;
    public static final String PLAYER = "player";

    /**
     * Displays the game results.
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

        db=DBHelper.getDbHelperInstance(this);

        Intent intent = getIntent();

        int finalScore = intent.getIntExtra(FINAL_SCORE, 0);
        int correctAnswers = intent.getIntExtra(CORRECT_ANSWERS, 0);
        int minutes = intent.getIntExtra(MINUTES, 0);
        int seconds = intent.getIntExtra(SECONDS, 0);
        String category = intent.getStringExtra(CATEGORY);
        String name = intent.getStringExtra(PLAYER);
        int rank= db.getRank(db.getHighScore(category), finalScore);


        TextView tvRank = (TextView) findViewById(R.id.rank);
        tvRank.setText("You are "+ rank +"th");
        TextView scoreView = (TextView) findViewById(R.id.score);
        scoreView.setText("Total score: " + finalScore);
        TextView correctView = (TextView) findViewById(R.id.correct_anwsers);
        correctView.setText("Correct answers: " + correctAnswers);
        TextView timeView = (TextView) findViewById(R.id.time);
        timeView.setText("Time: " + minutes + " min & " + seconds + " sec");;

        if(finalScore > 0){
            tvRank = (TextView) findViewById(R.id.rank);
            if(rank == 1 || rank == 21) {
                tvRank.setText("You placed " + rank + "st place on High Score");
            }
            else if (rank == 2 || rank == 22) {
                tvRank.setText("You placed " + rank + "nd place on High Score");
            }
            else if (rank == 3 || rank == 23) {
                tvRank.setText("You placed " + rank + "rd place on High Score");
            } else {
                tvRank.setText("You placed " + rank + "th place on High Score");
            }

        //add high scores to the data base.

        db.addHighScore(name, finalScore, db.getIdByCategoryName(category));

        }else{

            tvRank = (TextView) findViewById(R.id.rank);
            tvRank.setText("You can not be ranked!");
        }

        Log.d("debug","players final score is "+finalScore +" category is "+category+" players name is "+name);
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
                // Play action
                Intent i = new Intent(ResultActivity.this, GameModeActivity.class);
                startActivity(i);
                return true;
            case R.id.info:
                // Asobi presentation activity
                Intent j = new Intent(ResultActivity.this, AboutActivity.class);
                startActivity(j);
                return true;
            case R.id.profile:
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
}
