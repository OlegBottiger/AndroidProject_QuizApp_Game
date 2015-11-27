package com.example.iths.asobi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {

    public static final String CATEGORY="category";
    private static final String TAG = "GameActivity debug" ;
    private DBHelper dbHelper;
    private TextView tvCategory;
    private ArrayList<Question> questions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //Set actionbar item
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        setContentView(R.layout.activity_game);

        dbHelper = DBHelper.getDbHelperInstance(this);

        // gets String category from GameModeActivity and saves it in getCategory.
        Intent intent = getIntent();
        String getCategory = (String)intent.getSerializableExtra(CATEGORY);

        tvCategory = (TextView)findViewById(R.id.category_field);
        tvCategory.setText(getCategory);

        questions= dbHelper.getFiveQuestions(getCategory);

        TextView tvQuestion = (TextView)findViewById(R.id.question);
        tvQuestion.setText(questions.get(0).getQuestion());

        Log.d(TAG, questions.get(0).getQuestion() + questions.get(1).getQuestion());
        //+questions.get(2).getQuestion());
                //+questions.get(3).getQuestion()+questions.get(4).getQuestion());

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
                Intent i = new Intent(GameActivity.this, GameModeActivity.class);
                startActivity(i);
                return true;
            case R.id.info:
                // Asobi presentation activity
                Intent j = new Intent(GameActivity.this, AboutActivity.class);
                startActivity(j);
                return true;
            case R.id.profile:
                // Create profile activity
                Intent k = new Intent(GameActivity.this, ProfilesActivity.class);
                startActivity(k);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }


    }
}
