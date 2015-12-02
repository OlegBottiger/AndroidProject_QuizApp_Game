package com.example.iths.asobi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by iths on 2015-11-25.
 */
public class DBHelper extends SQLiteOpenHelper {


    public static final String GAME_DB = "Game_Databas";
    private static final int VERSION = 1;


    public static final String NAME_KEY = "name" ;
    private static final String SCORE_KEY = "score" ;
    private static final String HIGH_SCORE_TABLE = "highScores" ;
    private static final String PLAYER_TABLE = "players";
    private static final String SPORT_TABLE = "Sports";
    private static final String MUSIC_TABLE ="Music" ;
    private static final String SCIENCE_TABLE = "Science";
    private static final String GEOGRAPHY_TABLE = "Geography";
    private static final String MATH_TABLE ="Mathematics" ;
    private static final String GAME_TABLE ="Games" ;

    public static final String ID_KEY = "_id";
    private static final String QUESTION_KEY = "question";
    private static final String ALTERNATIVE1_KEY = "alternative1";
    private static final String ALTERNATIVE2_KEY = "alternative2";
    private static final String ALTERNATIVE3_KEY = "alternative3";
    private static final String ALTERNATIVE4_KEY = "alternative4";
    private static final String CORRECT_ANSWER_KEY = "correctAnswer";
    private static final String TAG= "debug";

    private static DBHelper dbHelper = null;

    public static DBHelper getDbHelperInstance(Context context){
        if (dbHelper == null){
            dbHelper = new DBHelper(context);
        }
        return dbHelper;
    }

    private DBHelper(Context context){

        super(context, GAME_DB, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql=" CREATE TABLE "+ HIGH_SCORE_TABLE+" ( ";
        sql += ID_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT,";
        sql += NAME_KEY + " VARCHAR(225) NOT NULL,";
        sql += SCORE_KEY + " INTEGER";
        sql +=" );";

        db.execSQL(sql);

        sql=" CREATE TABLE "+ PLAYER_TABLE+" ( ";
        sql += ID_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT,";
        sql += NAME_KEY + " VARCHAR(225) NOT NULL";
        sql +=" );";

        db.execSQL(sql);

        sql=" CREATE TABLE "+ SPORT_TABLE+" ( ";
        sql += ID_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT,";
        sql += QUESTION_KEY + " VARCHAR(225) NOT NULL,";
        sql += ALTERNATIVE1_KEY + " VARCHAR(225),";
        sql += ALTERNATIVE2_KEY + " VARCHAR(225),";
        sql += ALTERNATIVE3_KEY + " VARCHAR(225),";
        sql += ALTERNATIVE4_KEY + " VARCHAR(225),";
        sql += CORRECT_ANSWER_KEY + " VARCHAR(225)";
        sql +=" );";

        db.execSQL(sql);

        sql=" CREATE TABLE "+ MUSIC_TABLE+" ( ";
        sql += ID_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT,";
        sql += QUESTION_KEY + " VARCHAR(225) NOT NULL,";
        sql += ALTERNATIVE1_KEY + " VARCHAR(225),";
        sql += ALTERNATIVE2_KEY + " VARCHAR(225),";
        sql += ALTERNATIVE3_KEY + " VARCHAR(225),";
        sql += ALTERNATIVE4_KEY + " VARCHAR(225),";
        sql += CORRECT_ANSWER_KEY + " VARCHAR(225)";
        sql +=" );";

        db.execSQL(sql);

        sql=" CREATE TABLE "+ SCIENCE_TABLE+" ( ";
        sql += ID_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT,";
        sql += QUESTION_KEY + " VARCHAR(225) NOT NULL,";
        sql += ALTERNATIVE1_KEY + " VARCHAR(225),";
        sql += ALTERNATIVE2_KEY + " VARCHAR(225),";
        sql += ALTERNATIVE3_KEY + " VARCHAR(225),";
        sql += ALTERNATIVE4_KEY + " VARCHAR(225),";
        sql += CORRECT_ANSWER_KEY + " VARCHAR(225)";
        sql +=" );";

        db.execSQL(sql);

        sql=" CREATE TABLE "+ GEOGRAPHY_TABLE+" ( ";
        sql += ID_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT,";
        sql += QUESTION_KEY + " VARCHAR(225) NOT NULL,";
        sql += ALTERNATIVE1_KEY + " VARCHAR(225),";
        sql += ALTERNATIVE2_KEY + " VARCHAR(225),";
        sql += ALTERNATIVE3_KEY + " VARCHAR(225),";
        sql += ALTERNATIVE4_KEY + " VARCHAR(225),";
        sql += CORRECT_ANSWER_KEY + " VARCHAR(225)";
        sql +=" );";

        db.execSQL(sql);

        sql=" CREATE TABLE "+ MATH_TABLE+" ( ";
        sql += ID_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT,";
        sql += QUESTION_KEY + " VARCHAR(225) NOT NULL,";
        sql += ALTERNATIVE1_KEY + " VARCHAR(225),";
        sql += ALTERNATIVE2_KEY + " VARCHAR(225),";
        sql += ALTERNATIVE3_KEY + " VARCHAR(225),";
        sql += ALTERNATIVE4_KEY + " VARCHAR(225),";
        sql += CORRECT_ANSWER_KEY + " VARCHAR(225)";
        sql +=" );";

        db.execSQL(sql);

        sql=" CREATE TABLE "+ GAME_TABLE+" ( ";
        sql += ID_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT,";
        sql += QUESTION_KEY + " VARCHAR(225) NOT NULL,";
        sql += ALTERNATIVE1_KEY + " VARCHAR(225),";
        sql += ALTERNATIVE2_KEY + " VARCHAR(225),";
        sql += ALTERNATIVE3_KEY + " VARCHAR(225),";
        sql += ALTERNATIVE4_KEY + " VARCHAR(225),";
        sql += CORRECT_ANSWER_KEY + " VARCHAR(225)";
        sql +=" );";

        db.execSQL(sql);

        // Sets name "Guest" to PLAYER_TABLE as a default name
        ContentValues cvs = new ContentValues();
        cvs.put(NAME_KEY, "Guest");

        db.insert(PLAYER_TABLE, null, cvs);

        // Sets information to SPORT_TABLE ( just for test for now )
        cvs = new ContentValues();
        cvs.put(QUESTION_KEY,"Which sports is the most popular sport in Sweden?");
        cvs.put(ALTERNATIVE1_KEY, "Tennis");
        cvs.put(ALTERNATIVE2_KEY,"Soccer");
        cvs.put(ALTERNATIVE3_KEY,"Ice hockey");
        cvs.put(ALTERNATIVE4_KEY, "Bandy");
        cvs.put(CORRECT_ANSWER_KEY, "4");

        db.insert(SPORT_TABLE, null, cvs);

        cvs = new ContentValues();
        cvs.put(QUESTION_KEY,"What is Mario & Luigi’s last name?");
        cvs.put(ALTERNATIVE1_KEY, "Luigi");
        cvs.put(ALTERNATIVE2_KEY,"Mario");
        cvs.put(ALTERNATIVE3_KEY,"Lombardi");
        cvs.put(ALTERNATIVE4_KEY, "Alfredo");
        cvs.put(CORRECT_ANSWER_KEY, "2");

        db.insert(SPORT_TABLE, null, cvs);

        cvs = new ContentValues();
        cvs.put(QUESTION_KEY,"When was Nintendo as a company founded?");
        cvs.put(ALTERNATIVE1_KEY, "1991");
        cvs.put(ALTERNATIVE2_KEY,"1979");
        cvs.put(ALTERNATIVE3_KEY,"1889");
        cvs.put(ALTERNATIVE4_KEY,"1981");
        cvs.put(CORRECT_ANSWER_KEY, "3");
        db.insert(SPORT_TABLE, null, cvs);

        cvs = new ContentValues();
        cvs.put(QUESTION_KEY,"Before Nintendo made Video Games they made...");
        cvs.put(ALTERNATIVE1_KEY, "Card Games");
        cvs.put(ALTERNATIVE2_KEY,"Chairs");
        cvs.put(ALTERNATIVE3_KEY,"Electronics");
        cvs.put(ALTERNATIVE4_KEY,"Amusement Parks");
        cvs.put(CORRECT_ANSWER_KEY, "1");
        db.insert(SPORT_TABLE, null, cvs);

        cvs = new ContentValues();
        cvs.put(QUESTION_KEY,"Who is the creator of Super Mario?");
        cvs.put(ALTERNATIVE1_KEY, "Satoru Iwata");
        cvs.put(ALTERNATIVE2_KEY,"Reginald Fils-Aime");
        cvs.put(ALTERNATIVE3_KEY,"Shigeru Miyamoto");
        cvs.put(ALTERNATIVE4_KEY,"Gunpei Yokoi");
        cvs.put(CORRECT_ANSWER_KEY, "3");
        db.insert(SPORT_TABLE, null, cvs);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // one method to add a question to the data base.
    // I have to fix this because db could cause a problem (it may happen recursively)
    public void addQuestionsToDataBase(String category,String question,String alt1,String alt2, String alt3,String alt4,String correctAnswer){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues cvs = new ContentValues();
        cvs.put(QUESTION_KEY,question);
        cvs.put(ALTERNATIVE1_KEY, alt1);
        cvs.put(ALTERNATIVE2_KEY,alt2);
        cvs.put(ALTERNATIVE3_KEY,alt3);
        cvs.put(ALTERNATIVE4_KEY,alt4);
        cvs.put(CORRECT_ANSWER_KEY,correctAnswer);

        long id = db.insert(category, null, cvs);

        Log.d(TAG, "test id is " + id);
        Log.d(TAG, cvs.toString());

        db.close();
    }

    //alternative 2, tar emot en objekt

    public void addQuestionsToDataBase(Question question){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues cvs = new ContentValues();
        cvs.put(QUESTION_KEY,question.getCategory());
        cvs.put(ALTERNATIVE1_KEY, question.getAlternative1());
        cvs.put(ALTERNATIVE2_KEY,question.getAlternative2());
        cvs.put(ALTERNATIVE3_KEY,question.getAlternative3());
        cvs.put(ALTERNATIVE4_KEY,question.getAlternative4());
        cvs.put(CORRECT_ANSWER_KEY, question.getCorrectAnswer());

        long id = db.insert(question.getCategory(), null, cvs);

        Log.d(TAG, "test id is " + id);
        Log.d(TAG, cvs.toString());

        db.close();
    }

    // one method to add a high score to the data base
    public void addHighScores(String name, String score){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues cvs = new ContentValues();
        cvs.put(NAME_KEY,name);
        cvs.put(SCORE_KEY, score);

        long id = db.insert(HIGH_SCORE_TABLE, null, cvs);

        Log.d(TAG,"High scores test. id is "+ id);

        db.close();
    }

    /**
     *
     * @return - cursor which points to the player's table
     */
    public Cursor getPlayers(){

         return getReadableDatabase().query(PLAYER_TABLE,null,null,null,null,null,null);

    }

    /**
     * adds a player's name to the data base
     * @param name - player's name
     */
    public void addProfile(String name){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues cvs = new ContentValues();
        cvs.put(NAME_KEY,name);

        long id = db.insert(PLAYER_TABLE,null,cvs);
        Log.d(TAG, "Player table test, ID is"+ id);
        db.close();

    }

    /**
     *
     * @param category - a name of a table
     * @return category's cursor
     */
    public Cursor getAllTable(String category){
        return getReadableDatabase().query(category,null,null,null,null,null,null);
    }

    public int getLengthOfQuestions(String category){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = getAllTable(category);
        int lengthOfQuestions = 0;

            if(cursor.moveToFirst()){
                do{
                    lengthOfQuestions++;

                } while(cursor.moveToNext());
            }

        db.close();
        return lengthOfQuestions;
    }

    /**
     *
     * @param category - name of a table which you want to take information from
     * @return list of instances of Question
     */
    public ArrayList<Question> getFiveQuestions( String category ){

        SQLiteDatabase db = getReadableDatabase();

        ArrayList<Question> questions = new ArrayList<Question>();

        Cursor cursor = db.query(category, null, null, null, null, null, "RANDOM() LIMIT 5");

        if(cursor.moveToFirst()){
            int questionIndex= cursor.getColumnIndex(QUESTION_KEY);
            int altAIndex = cursor.getColumnIndex(ALTERNATIVE1_KEY);
            int altBIndex = cursor.getColumnIndex(ALTERNATIVE2_KEY);
            int altCIndex = cursor.getColumnIndex(ALTERNATIVE3_KEY);
            int altDIndex = cursor.getColumnIndex(ALTERNATIVE4_KEY);
            int correctAnswerIndex = cursor.getColumnIndex(CORRECT_ANSWER_KEY);

            String question;
            String alternativeA;
            String alternativeB;
            String alternativeC;
            String alternativeD;
            String correctAnswer;


            do{
                question = cursor.getString(questionIndex);
                alternativeA = cursor.getString(altAIndex);
                alternativeB =cursor.getString(altBIndex);
                alternativeC = cursor.getString(altCIndex);
                alternativeD = cursor.getString(altDIndex);
                correctAnswer = cursor.getString(correctAnswerIndex);

                questions.add(new Question(category,question,alternativeA,alternativeB,alternativeC,alternativeD,correctAnswer));
                Log.d(TAG,"Cursor working"+ question+alternativeA+alternativeB+alternativeC+alternativeD+correctAnswer);

            }while(cursor.moveToNext());
        }

        db.close();
        return questions;
    }



}
