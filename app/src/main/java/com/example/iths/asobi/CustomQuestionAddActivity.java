package com.example.iths.asobi;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class CustomQuestionAddActivity extends AppCompatActivity {

    private static final String TAG = "DEBUG";
    public static final String CATEGORY="category";
    private EditText customQuestion;
    private EditText alternativeOne;
    private EditText alternativeTwo;
    private EditText alternativeThree;
    private EditText alternativeFour;
    private EditText rightAnswer;
    private DBHelper db;
    private String selectedCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_question_add);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        setContentView(R.layout.activity_custom_question_add);

        //Add question field
        customQuestion = (EditText) findViewById(R.id.custom_question);

        //Alternatives 1 - 4 fields
        alternativeOne = (EditText) findViewById(R.id.alternative_one);
        alternativeTwo = (EditText) findViewById(R.id.alternative_two);
        alternativeThree = (EditText) findViewById(R.id.alternative_three);
        alternativeFour = (EditText) findViewById(R.id.alternative_four);

        //Right answer field

        rightAnswer = (EditText) findViewById(R.id.right_answer);

        //Adds questions to our database

//        DBHelper.addQuestionsToDataBase();

        db = DBHelper.getDbHelperInstance(this);

        selectedCategory=getIntent().getStringExtra(CATEGORY);
    }

    /**
     * Gets the actionbar.
     * @param menu the actionbar menu.
     * @return true so you can see the actionbar.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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
                Intent i = new Intent(CustomQuestionAddActivity.this, GameModeActivity.class);
                startActivity(i);
                return true;
            case R.id.info:
                // Asobi presentation activity
                Intent j = new Intent(CustomQuestionAddActivity.this, AboutActivity.class);
                startActivity(j);
                return true;
            case R.id.profile:
                // Create profile activity
                Intent k = new Intent(CustomQuestionAddActivity.this, ProfilesActivity.class);
                startActivity(k);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Creates a new question with 4 different answers.
     * @param view collects the input from the different EditText fields.
     */
    public void addCustomQuestion(View view) {

        String question = customQuestion.getText().toString();
        String alt1 = alternativeOne.getText().toString();
        String alt2 = alternativeTwo.getText().toString();
        String alt3 = alternativeThree.getText().toString();
        String alt4 = alternativeFour.getText().toString();
        String correct = rightAnswer.getText().toString();
        String category = selectedCategory;

        int catId = db.getIdByCategoryName(category);

        db.addQuestionsToDataBase(db.getWritableDatabase(), question, alt1, alt2, alt3, alt4, correct, catId);

        Context context = getApplicationContext();
        CharSequence text = ("Question added!");
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

        customQuestion.getText().clear();
        alternativeOne.getText().clear();
        alternativeTwo.getText().clear();
        alternativeThree.getText().clear();
        alternativeFour.getText().clear();
        rightAnswer.getText().clear();
    }
}

