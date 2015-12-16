package com.example.iths.asobi;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

/**
 * This is a main activity which navigates the player to different activities including
 * play, categories, high scores and information.
 */
public class MainActivity extends AppCompatActivity {

    private TextView mTextField;
    private MediaPlayer mp;
    public static String currentPlayer="Guest";
    private Player player = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        setContentView(R.layout.activity_main);

        player = Player.getPlayerInstance("Guest");
        currentPlayer = player.getName();
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
                Intent i = new Intent(MainActivity.this, GameModeActivity.class);
                startActivity(i);
                return true;
            case R.id.info:
                playClickSound();
                // Asobi presentation activity
                Intent j = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(j);
                return true;
            case R.id.profile:
                playClickSound();
                // Create profile activity
                Intent k = new Intent(MainActivity.this, ProfilesActivity.class);
                startActivity(k);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Upon click this starts a new game.
     * @param view the button that calls the method.
     */
    public void startGame(View view) {
        playClickSound();
        Intent intent = new Intent(MainActivity.this, GameModeActivity.class);
        startActivity(intent);
    }

    /**
     * Upon click this takes you to the highscore activity.
     * @param view the button that calls the method.
     */
    public void getHighScore(View view) {
        playClickSound();
        Intent i = new Intent(MainActivity.this, HighscoreActivity.class);
        startActivity(i);
    }

    /**
     * Takes you to the activity where you create custom categories/questions.
     * @param view the button that calls the method.
     */
    public void custom(View view) {
        playClickSound();
        Intent i = new Intent(MainActivity.this, CustomCategoryActivity.class);
        startActivity(i);
    }

    /**
     * Takes you to the activity that shows information about Asobi.
     * @param view the button that calls the method.
     */
    public void getInfo(View view) {
        playClickSound();
        Intent i = new Intent(MainActivity.this, AboutActivity.class);
        startActivity(i);
    }

    /**
     * A method used for making the buttons do a sound when clicked.
     */
    public void playClickSound() {
        mp = MediaPlayer.create(this, R.raw.test);
        mp.start();
    }
}
