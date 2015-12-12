package com.example.iths.asobi;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class CustomQuestionActivity extends AppCompatActivity {

    private DBHelper db;
    private ListView list;
    public static final String CATEGORY="category";
    private Cursor customQuestiosCursor;
    private String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_question);
        db = DBHelper.getDbHelperInstance(this);

        //Set actionbar item
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        setContentView(R.layout.activity_custom_question);

        Intent intent =getIntent();
        category = intent.getStringExtra(CATEGORY);

        if(category.equals("ALL")) {
            customQuestiosCursor = db.getOneTable(DBHelper.getWholeQuestionTable());
        } else{
            customQuestiosCursor= db.getCursorForOnesCategory(category);
        }

        list = (ListView) findViewById(R.id.listview_custom_question);

        String[] from = new String[] {db.getQuestionKey(), db.getAlternative1Key(), db.getAlternative2Key(), db.getAlternative3Key(), db.getAlternative4Key(), db.getCorrectAnswerKey()};
        int[] to = new int[] {R.id.debug_question, R.id.debug_ans1, R.id.debug_ans2, R.id.debug_ans3, R.id.debug_ans4};
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.question_list, customQuestiosCursor, from, to, 0);

        list.setAdapter(adapter);

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
                Intent i = new Intent(CustomQuestionActivity.this, GameModeActivity.class);
                startActivity(i);
                return true;
            case R.id.info:
                // Asobi presentation activity
                Intent j = new Intent(CustomQuestionActivity.this, AboutActivity.class);
                startActivity(j);
                return true;
            case R.id.profile:
                // Create profile activity
                Intent k = new Intent(CustomQuestionActivity.this, ProfilesActivity.class);
                startActivity(k);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }


    }


    public void goToAddQuestions(View view) {
        Intent intent = new Intent(this, CustomQuestionAddActivity.class);
        intent.putExtra(CustomQuestionAddActivity.CATEGORY,category);
        startActivity(intent);
    }
}
