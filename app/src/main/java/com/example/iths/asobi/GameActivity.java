package com.example.iths.asobi;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
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

/**
 * This is the activity which has the game's logic.
 */
public class GameActivity extends AppCompatActivity {

    public static final String CATEGORY = "category";
    private static final String TAG = "GameActivity debug";
    private static final String TIME_LEFT = "savedTimeLeft";
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
    private int showRound = 1;
    private int playerScore = 0;
    private TextView mTextField;
    private CountDownTimer countDown;
    private CountDownTimer timer;
    private CountDownTimer countDownAgain;
    private int pointsToRecieve = 3;
    private int numberOfCorrectAnswer = 0;
    private int time;
    private int minutes;
    private int seconds;
    private String getCategory;
    private TextView roundView;
    private MediaPlayer mp;
    private View view;
    private String timeLeft;
    private int defaultTime = 15100;
    private int savedTime;
    private MediaPlayer mpb;
    private MediaPlayer mpbw;
    private MediaPlayer mpbr;
    private Player player = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        timer = new CountDownTimer(300000, 1000) {

            public void onTick(long millisUntilFinished) {
                time = time + 1;
            }

            public void onFinish() {
            }
        }.start();

        mp = MediaPlayer.create(this, R.raw.vitas2);
        mp.start();

        player = Player.getPlayerInstance("Guest");

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        setContentView(R.layout.activity_game);

        dbHelper = DBHelper.getDbHelperInstance(this);

        // gets String category from GameModeActivity and saves it in getCategory.
        Intent intent = getIntent();
        getCategory = (String) intent.getSerializableExtra(CATEGORY);

        tvCategory = (TextView) findViewById(R.id.category_field);
        tvCategory.setText(getCategory);

        // gets 5 random questions from the data base and sets them to the list of arrays "question"
        if (getCategory.equals("ALL")) {
            questions = dbHelper.getRandomFiveQuestions(0);
        } else {
            questions = dbHelper.getRandomFiveQuestions(dbHelper.getIdByCategoryName(getCategory));
        }

        tvQuestion = (TextView) findViewById(R.id.question);
        tvQuestion.setText(questions.get(round).getQuestion());

        buttonA = (Button) findViewById(R.id.buttonA);
        buttonA.setText(questions.get(round).getAlternative1());

        buttonB = (Button) findViewById(R.id.buttonB);
        buttonB.setText(questions.get(round).getAlternative2());

        buttonC = (Button) findViewById(R.id.buttonC);
        buttonC.setText(questions.get(round).getAlternative3());

        buttonD = (Button) findViewById(R.id.buttonD);
        buttonD.setText(questions.get(round).getAlternative4());

        correctAnswer = questions.get(round).getCorrectAnswer();

        roundView = (TextView) findViewById(R.id.round);
        roundView.setText("" + showRound);

        //Starts the countDownTimer;
        countDownTimer(defaultTime);

    }

    /**
     * Starts 15 second countdown. Every 5 seconds the points received is reduced by 1 point.
     * When the count reaches 0, the next question is displayed.
     */

    private void countDownTimer(int time) {
        questionTimer(time);

    }

    @Override
    protected void onPause() {
        super.onPause();
        Intent intent = new Intent(this, GameActivity.class);
        savedTime = Integer.parseInt(timeLeft);
        intent.putExtra(TIME_LEFT, savedTime);
        this.countDown.cancel();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        int newTime = intent.getIntExtra(TIME_LEFT, 15100);
        questionTimer(newTime);
    }

    /**
     * This method handles the game input and check if answer is correct.
     * If the answer is correct the button turns green and the player's score increases.
     * If not, the button turns red and the player receives no points.
     * Finally the method calls on the next question.
     *
     * @param view
     */
    public void gameInput(final View view) {

        countDown.cancel();

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

            playClickSoundGlass();
            view.setBackgroundColor(Color.parseColor("#00cc00"));

            numberOfCorrectAnswer++;

            // Player's score increases
            playerScore = playerScore + pointsToRecieve;

            TextView scoreView = (TextView) findViewById(R.id.score);
            scoreView.setText("" + playerScore);

        } else {
            playClickSoundDuck();
            view.setBackgroundColor(Color.parseColor("#ff3300"));
        }

        round++;
        showRound++;

        new CountDownTimer(200, 1000) {

            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {
                view.setBackgroundColor(Color.parseColor("#573b3a3a"));
                nextQuestion();
            }
        }.start();
    }

    /**
     * This method starts a new round and retrieves a new questions.
     * If the round limit has been reached the user is sent to the result screen together with all the results from that game.
     */
    public void nextQuestion() {

        if (round == questions.size()) {
            timer.cancel();
            minutes = time / 60;
            seconds = time % 60;
            Intent intent = new Intent(this, ResultActivity.class);
            intent.putExtra(ResultActivity.FINAL_SCORE, playerScore);
            intent.putExtra(ResultActivity.CORRECT_ANSWERS, numberOfCorrectAnswer);
            intent.putExtra(ResultActivity.MINUTES, minutes);
            intent.putExtra(ResultActivity.SECONDS, seconds);
            intent.putExtra(ResultActivity.CATEGORY, getCategory);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            mp.stop();
            startActivity(intent);
        } else {

            tvQuestion.setText(questions.get(round).getQuestion());
            buttonA.setText(questions.get(round).getAlternative1());
            buttonB.setText(questions.get(round).getAlternative2());
            buttonC.setText(questions.get(round).getAlternative3());
            buttonD.setText(questions.get(round).getAlternative4());
            correctAnswer = questions.get(round).getCorrectAnswer();

            //TextView roundView = (TextView) findViewById(R.id.round);
            roundView.setText("" + showRound);
            pointsToRecieve = 3;

            questionTimer(defaultTime);
        }
    }

    /**
     * Gets the actionbar.
     *
     * @param menu the actionbar menu.
     * @return true so you can see the actionbar.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.gameactivity_toolbar, menu);
        return true;
    }

    /**
     * Handles the item clicks here and also has a sms function.
     *
     * @param item is the symbol showed up on the actionbar.
     * @return returns true if clicked and takes you to the next activity or sms.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_play:
                playClickSound();
                Intent i = new Intent(GameActivity.this, GameModeActivity.class);
                mp.stop();
                startActivity(i);
                return true;
            case R.id.info:
                playClickSound();
                Intent j = new Intent(GameActivity.this, AboutActivity.class);
                mp.stop();
                startActivity(j);
                return true;
            case R.id.profile:
                playClickSound();
                Intent k = new Intent(GameActivity.this, ProfilesActivity.class);
                mp.stop();
                startActivity(k);
                return true;
            case R.id.send_sms:
                playClickSound();
                Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                sendIntent.setData(Uri.parse("sms:"));
                String quest = tvQuestion.getText().toString();
                String a = buttonA.getText().toString();
                String b = buttonB.getText().toString();
                String c = buttonC.getText().toString();
                String d = buttonD.getText().toString();
                sendIntent.putExtra("sms_body", quest + "\n A: " + a + "\n B: " + b + "\n C: " + c + "\n D: " + d);
                mp.stop();
                startActivity(sendIntent);

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Helper method for our countdown timer. Was a part of more extended workaround with onPause and onResume.
     *
     * @param time from wich we start our timer.
     */
    public void questionTimer(int time) {

        if (countDown != null) {
            countDown.cancel();
            countDown = new CountDownTimer(time, 1000) {
                public void onTick(long millisUntilFinished) {
                    mTextField = (TextView) findViewById(R.id.timer);
                    timeLeft = "" + millisUntilFinished / 1000;
                    mTextField.setText(timeLeft);

                    if (millisUntilFinished < 10000) {
                        pointsToRecieve = 2;
                    } else if (millisUntilFinished < 5000) {
                        pointsToRecieve = 1;
                    } else if (millisUntilFinished <= 0) {
                        pointsToRecieve = 0;
                    }
                }

                public void onFinish() {
                    mTextField.setText("0");
                    round++;
                    showRound++;
                    cancel();
                    nextQuestion();
                }

            }.start();
        } else {

            countDown = new CountDownTimer(15100, 1000) {
                public void onTick(long millisUntilFinished) {
                    mTextField = (TextView) findViewById(R.id.timer);
                    timeLeft = "" + millisUntilFinished / 1000;
                    mTextField.setText(timeLeft);

                    if (millisUntilFinished < 10000) {
                        pointsToRecieve = 2;
                    } else if (millisUntilFinished < 5000) {
                        pointsToRecieve = 1;
                    } else if (millisUntilFinished <= 0) {
                        pointsToRecieve = 0;
                    }
                }

                public void onFinish() {
                    mTextField.setText("0");
                    round++;
                    showRound++;
                    cancel();
                    nextQuestion();
                }

            }.start();

        }

    }

    @Override
    public void onBackPressed(){
        countDown.cancel();
        Intent intent = new Intent(GameActivity.this, MainActivity.class);
        startActivity(intent);

    }

    /**
     * A method used for making the actionbar buttons do a sound when clicked.
     */
    public void playClickSound() {
        mpb = MediaPlayer.create(this, R.raw.test);
        mpb.start();
    }

    /**
     * A method used for making the wrong answer buttons do a sound when clicked.
     */
    public void playClickSoundDuck() {
        mpbw = MediaPlayer.create(this, R.raw.duck_quacking);
        mpbw.start();
    }
    /**
     * A method used for making the right answer button do a sound when clicked.
     */
    public void playClickSoundGlass() {
        mpbr = MediaPlayer.create(this, R.raw.glass_breaking);
        mpbr.start();
    }
}
