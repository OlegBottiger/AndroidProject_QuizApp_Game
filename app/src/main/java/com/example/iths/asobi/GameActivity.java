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
    private int playerScore = 0;
    private TextView mTextField;
    private CountDownTimer timer;
    private int pointsToRecieve = 3;

    private int numberOfCorrectAnswer = 0;
    private int time;
    private int minutes;
    private int seconds;
    private Player player;

    private String getCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        countDownTimer();

        //Set actionbar item
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        setContentView(R.layout.activity_game);

        dbHelper = DBHelper.getDbHelperInstance(this);

        // gets String category from GameModeActivity and saves it in getCategory.
        Intent intent = getIntent();
        getCategory = (String)intent.getSerializableExtra(CATEGORY);

        tvCategory = (TextView)findViewById(R.id.category_field);
        tvCategory.setText(getCategory);

        // gets 5 random questions from the data base and sets them to the list of arrays "question"
        if(getCategory.equals("ALL")){
            questions = dbHelper.getRandomFiveQuestions(0);
        } else{
            questions= dbHelper.getRandomFiveQuestions(dbHelper.getIdFromCategoryTableByCategoryName(getCategory));
        }

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

    private void countDownTimer() {
        timer = new CountDownTimer(16000, 1000) {

            public void onTick(long millisUntilFinished) {
                mTextField = (TextView) findViewById(R.id.timer);
                mTextField.setText("" + millisUntilFinished / 1000);
                time = time + 1;
                if (millisUntilFinished < 10000) {
                    pointsToRecieve = 2;
                }
                else if (millisUntilFinished < 5000) {
                    pointsToRecieve = 1;
                }
                else if (millisUntilFinished <= 0) {
                    pointsToRecieve = 0;
                }
            }

            public void onFinish() {
                mTextField.setText("0");
            }
        }.start();
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
        goToNextQuestion(view);
        }

    public void goToNextQuestion(View view) {

        timer.cancel();
        countDownTimer();

        switch (view.getId()) {
            case R.id.buttonA:
                playersGuess = "1";
                break;
            case R.id.buttonB:
                playersGuess = "2";
                break;

            case R.id.buttonC:
                playersGuess = "3";
                break;

            case R.id.buttonD:
                playersGuess = "4";
                break;
        }
        if (correctAnswer.equals(playersGuess)) {

            numberOfCorrectAnswer ++;

            // Player's score increases
            playerScore = playerScore + pointsToRecieve;

            TextView scoreView = (TextView) findViewById(R.id.score);
            scoreView.setText("" + playerScore);

        }
        // increase the number of the round
        round++;

        pointsToRecieve = 3;


        if (round < questions.size()) {
            tvQuestion.setText(questions.get(round).getQuestion());
            buttonA.setText(questions.get(round).getAlternative1());
            buttonB.setText(questions.get(round).getAlternative2());
            buttonC.setText(questions.get(round).getAlternative3());
            buttonD.setText(questions.get(round).getAlternative4());
            correctAnswer = questions.get(round).getCorrectAnswer();
        }

        if (round == questions.size()) {
            int minutes = time/60;
            int seconds = time % 60;
            Intent intent = new Intent(this, ResultActivity.class);
            intent.putExtra("FINAL_SCORE", playerScore);
            intent.putExtra("CORRECT_ANSWERS", numberOfCorrectAnswer);
            intent.putExtra("MINUTES", minutes);
            intent.putExtra("SECONDS", seconds);
            intent.putExtra("CATEGORY" ,getCategory);
            startActivity(intent);

            // send information to the result activity
            // how many points player have
            // how many right answer player got
            // how long it took


        }
    }
}
