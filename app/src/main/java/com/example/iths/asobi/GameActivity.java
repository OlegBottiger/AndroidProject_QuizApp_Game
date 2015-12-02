package com.example.iths.asobi;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {

    public static final String CATEGORY="category";
    private static final String TAG = "GameActivity debug" ;
    private DBHelper dbHelper;
    private TextView tvCategory;
    private TextView tvQuestion;
    private Button buttonA;
    private Button buttonB;
    private Button buttonC;
    private Button buttonD;

    private ArrayList<Question> questions;
    private String correctAnswer;
    private String playersGuess;
    private int round = 0;
    private int numberOfRightAnswer =0;

    private TextView mTextField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        mTextField = (TextView) findViewById(R.id.time);

        new CountDownTimer(15000, 1000) {

            public void onTick(long millisUntilFinished) {
                String str ="seconds remaining: " + millisUntilFinished / 1000;
                mTextField.setText(str);
                Log.d(TAG, "ticking");
            }

            public void onFinish() {
                mTextField.setText("done!");
                Log.d(TAG, "done");
            }
        }.start();


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

        // gets 5 random questions from the data base and sets them to the list of arrays "question"
        questions= dbHelper.getFiveQuestions(getCategory);

        tvQuestion = (TextView)findViewById(R.id.question);
        tvQuestion.setText(questions.get(round).getQuestion());

        buttonA =(Button)findViewById(R.id.buttonA);
        buttonA.setText(questions.get(round).getAlternative1());

        buttonB =(Button)findViewById(R.id.buttonB);
        buttonB.setText(questions.get(round).getAlternative2());

        buttonC =(Button)findViewById(R.id.buttonC);
        buttonC.setText(questions.get(round).getAlternative3());

        buttonD =(Button)findViewById(R.id.buttonD);
        buttonD.setText(questions.get(round).getAlternative4());

        correctAnswer = questions.get(round).getCorrectAnswer();






        Log.d(TAG, questions.get(0).getQuestion() + questions.get(1).getQuestion()+questions.get(2).getQuestion()
                +questions.get(3).getQuestion()+questions.get(4).getQuestion());

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

    public void nextQuestion(View view) {

        switch (view.getId()){
            case R.id.buttonA:
                playersGuess="1";
                break;
            case R.id.buttonB:
                playersGuess="2";
                break;

            case R.id.buttonC:
                playersGuess="3";
                break;

            case R.id.buttonD:
                playersGuess="4";
                break;
        }
        if (correctAnswer.equals(playersGuess)){

            // players score increase

            // how many right answer increase
            numberOfRightAnswer++;
            Log.d(TAG, "number of right answer is "+ numberOfRightAnswer);

        }
        // increase the number of the round
        round++;
        Log.d(TAG, " next round is "+ round);

        // timer records
        // timer reset


        if ( round < questions.size()) {
            tvQuestion.setText(questions.get(round).getQuestion());
            buttonA.setText(questions.get(round).getAlternative1());
            buttonB.setText(questions.get(round).getAlternative2());
            buttonC.setText(questions.get(round).getAlternative3());
            buttonD.setText(questions.get(round).getAlternative4());
            correctAnswer = questions.get(round).getCorrectAnswer();
        }

        if( round == questions.size()){
            Intent intent = new Intent(this,ResultActivity.class);
            intent.putExtra(ResultActivity.FINAL_SCORE,"0");
            startActivity(intent);

            // send information to the result activity
            // how many points player have
            // how many right answer player got
            // how long it took



        }


    }
}
