package com.example.iths.asobi;

import android.content.Intent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        //Set actionbar item
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        setContentView(R.layout.activity_result);

        db=DBHelper.getDbHelperInstance(this);

        Intent intent = getIntent();

        int finalScore = intent.getIntExtra("FINAL_SCORE", 0);
        int correctAnswers = intent.getIntExtra("CORRECT_ANSWERS", 0);
        int minutes = intent.getIntExtra("MINUTES", 0);
        int seconds = intent.getIntExtra("SECONDS", 0);
        String category = intent.getStringExtra("CATEGORY");
        String name = intent.getStringExtra("PLAYER");
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
        db.addHighScore(name, finalScore, db.getIdFromCategoryTableByCategoryName(category), rank);
        }else{

            tvRank = (TextView) findViewById(R.id.rank);
            tvRank.setText("You can not be ranked!");
        }

        Log.d("debug","players final score is "+finalScore +"category is "+category);
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
        Intent i = new Intent(ResultActivity.this, MainActivity.class);
        startActivity(i);
    }

    //test method. I remove this later.
    public void addHighScore(View view) {


        DBHelper.getDbHelperInstance(this).insertCategory(db.getWritableDatabase(),"newTable!");
        /*
        DBHelper.getDbHelperInstance(this).addHighScore("Joe",58,1);
        DBHelper.getDbHelperInstance(this).addHighScore("Michael",58,1);
        DBHelper.getDbHelperInstance(this).addHighScore("Maria",58,1);
        DBHelper.getDbHelperInstance(this).addHighScore("Johanna",20,1);
        DBHelper.getDbHelperInstance(this).addHighScore("Mark", 6,1);
        */

    }
}
