package com.example.iths.asobi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    protected static final String FINAL_SCORE="final_score";
    private DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);


        //Set actionbar item
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        setContentView(R.layout.activity_result);

        //test
        db=DBHelper.getDbHelperInstance(this);

        Intent intent = getIntent();
        String finalScore = intent.getStringExtra(FINAL_SCORE);

        // test
        int rank= db.getRank(db.getHighScore("Sports"), 15);

        TextView tvRank = (TextView) findViewById(R.id.rank);
        tvRank.setText("You are "+ rank +"th");

        //add high scores to the data base.
        DBHelper.getDbHelperInstance(this).addHighScore("Joe", 100, DBHelper.getDbHelperInstance(this).getIdFromCategoryTableByCategoryName("Sports"));


    }



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

    public void goToHighScore(View view) {
        Intent i = new Intent(ResultActivity.this, HighscoreActivity.class);
        startActivity(i);
    }

    public void replay(View view) {
        Intent i = new Intent(ResultActivity.this, GameModeActivity.class);
        startActivity(i);
    }

    public void goToMainMenu(View view) {
        Intent i = new Intent(ResultActivity.this, HighscoreActivity.class);
        startActivity(i);
    }

    //test method. I remove this later.
    public void addHighScore(View view) {
        DBHelper.getDbHelperInstance(this).addHighScore("Joe",58,1);
        DBHelper.getDbHelperInstance(this).addHighScore("Michael",58,1);
        DBHelper.getDbHelperInstance(this).addHighScore("Maria",58,1);
        DBHelper.getDbHelperInstance(this).addHighScore("Johanna",20,1);
        DBHelper.getDbHelperInstance(this).addHighScore("Mark", 6,1);
    }
}
