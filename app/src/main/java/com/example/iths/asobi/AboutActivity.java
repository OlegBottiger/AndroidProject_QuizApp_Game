package com.example.iths.asobi;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

/**
 * This activity has information about the Asobi team who created this application.
 */
public class AboutActivity extends AppCompatActivity {

    public static String currentPlayer = "Guest";
    private MediaPlayer mpb;
    private Player player = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        setContentView(R.layout.activity_about);

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
                Intent i = new Intent(AboutActivity.this, GameModeActivity.class);
                startActivity(i);
                return true;
            case R.id.info:
                playClickSound();
                // Asobi presentation activity
                Intent j = new Intent(AboutActivity.this, AboutActivity.class);
                startActivity(j);
                return true;
            case R.id.profile:
                playClickSound();
                // Create profile activity
                Intent k = new Intent(AboutActivity.this, ProfilesActivity.class);
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
