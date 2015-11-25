package com.example.iths.asobi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

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

    private static final String ID_KEY = "_id";
    private static final String QUESTION_KEY = "question";
    private static final String ALTERNATIVE1_KEY = "alternative1";
    private static final String ALTERNATIVE2_KEY = "alternative2";
    private static final String ALTERNATIVE3_KEY = "alternative3";
    private static final String ALTERNATIVE4_KEY = "alternative4";
    private static final String CORRECT_ANSWER_KEY = "correctAnswer";
    private static final String TAG= "debug";



    public DBHelper(Context context){

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
        cvs.put(NAME_KEY,"Guest");

        db.insert(PLAYER_TABLE, null, cvs);

        /*
        // Till example
        addQuestionsToDataBase(SPORT_TABLE,"Which sports is the most popular sport in Sweden?","Tennis","Soccer","Ice hockey","Bandy","2");
        */
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    // ska vi skicka data i en instans eller skicka med putExtra...? vi funderar lite till

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

    public void addHighScores(String name, String score){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues cvs = new ContentValues();
        cvs.put(NAME_KEY,name);
        cvs.put(SCORE_KEY, score);

        long id = db.insert(HIGH_SCORE_TABLE, null, cvs);

        Log.d(TAG,"High scores test. id is "+ id);

        db.close();
    }

    public Cursor getPlayers(){
        return getReadableDatabase().query(PLAYER_TABLE,null,null,null,null,null,null);
    }



}
