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

    private static final String NAME_KEY ="name" ;
    private static final String SCORE_KEY = "score" ;
    private static final String HIGH_SCORE_TABLE = "highScores" ;
    private static final String PLAYER_TABLE = "players";
    private static final String ALL_CATEGORY_TABLE = "allCategories";
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
    private static final String CATEGORY_KEY = "category";


    private static DBHelper dbHelper = null;
    private SQLiteDatabase db;
    private String sql;

    public static DBHelper getDbHelperInstance(Context context){
        if (dbHelper == null){
            dbHelper = new DBHelper(context);
        }
        return dbHelper;
    }

    private DBHelper(Context context){

        super(context, GAME_DB, null, VERSION);
    }
    public String getName(){
        return this.NAME_KEY;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

            sql = " CREATE TABLE " + HIGH_SCORE_TABLE + " ( ";
            sql += ID_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT,";
            sql += NAME_KEY + " VARCHAR(225) NOT NULL,";
            sql += SCORE_KEY + " INTEGER";
            sql += " );";

            db.execSQL(sql);

            sql = " CREATE TABLE " + PLAYER_TABLE + " ( ";
            sql += ID_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT,";
            sql += NAME_KEY + " VARCHAR(225) NOT NULL";
            sql += " );";

            db.execSQL(sql);

            sql = " CREATE TABLE " + ALL_CATEGORY_TABLE + " ( ";
            sql += ID_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT,";
            sql += CATEGORY_KEY + " VARCHAR(225) NOT NULL";
            sql += " );";

            db.execSQL(sql);


            createNewTable(SPORT_TABLE, db);
            createNewTable(MUSIC_TABLE, db);
            createNewTable(SCIENCE_TABLE, db);
            createNewTable(GEOGRAPHY_TABLE, db);
            createNewTable(MATH_TABLE,db);
            createNewTable(GAME_TABLE,db);


            // Sets name "Guest" to PLAYER_TABLE as a default name
            ContentValues cvs = new ContentValues();
            cvs.put(NAME_KEY, "Guest");

            db.insert(PLAYER_TABLE, null, cvs);

            // Sets information to SPORT_TABLE ( just for test for now )

            addQuestionsToDataBase(db,SPORT_TABLE," Which sports is the most popular sport in Sweden?","Tennis","Soccer","Ice hockey","Bandy","4");
            addQuestionsToDataBase(db,SPORT_TABLE," Who won The World Highland Games Championships a record six times?","Geoff Capes","someone","someone","someone","1");
            addQuestionsToDataBase(db,SPORT_TABLE," What jobs did Mike ‘Fluff’ Cowan, Jim ‘Bones’ Mackay and Fanny Sunesson do?","something","Golf caddies","something","something","2");
            addQuestionsToDataBase(db, SPORT_TABLE, " Which former rugby player once called the English RFU committee 'Old Farts'?", "something", "something", "Will Carling", "something", "3");
            addQuestionsToDataBase(db, SPORT_TABLE, " In inches, how big is the diameter of a basketball hoop?", "11", "13", "16", "18", "4");

            addQuestionsToDataBase(db,MUSIC_TABLE," music Which sports is the most popular sport in Sweden?","Tennis","Soccer","Ice hockey","Bandy","4");
            addQuestionsToDataBase(db,MUSIC_TABLE," music sports is the most popular sport in Sweden?","Tennis","Soccer","Ice hockey","Bandy","4");
            addQuestionsToDataBase(db,MUSIC_TABLE," music is the most popular sport in Sweden?","Tennis","Soccer","Ice hockey","Bandy","4");
            addQuestionsToDataBase(db,MUSIC_TABLE," music the most popular sport in Sweden?","Tennis","Soccer","Ice hockey","Bandy","4");
            addQuestionsToDataBase(db,MUSIC_TABLE," music most popular sport in Sweden?","Tennis","Soccer","Ice hockey","Bandy","4");

            addQuestionsToDataBase(db,GAME_TABLE," What is Mario & Luigi’s last name? ","Luigi","Mario","Lombardi","Alfredo","2");
            addQuestionsToDataBase(db,GAME_TABLE," When was Nintendo as a company founded?","1991","1979","1889","1981","3");
            addQuestionsToDataBase(db,GAME_TABLE," Before Nintendo made Video Games they made...","Card Games","Chairs","Electronics","Amusement Parks","1");
            addQuestionsToDataBase(db,GAME_TABLE," Who is the creator of Super Mario?", "Satoru Iwata", "Reginald Fils-Aime", "Shigeru Miyamoto", "Gunpei Yokoi", "3");
            addQuestionsToDataBase(db, GAME_TABLE," What is the name of the main character in the “The Legend of Zelda”?","Zelda","Link","Roy","Master Chief","2");



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // one method to add a question to the data base.
    // I have to fix this because db could cause a problem (it may happen recursively)


    public void addQuestionsToDataBase(SQLiteDatabase db,String category,String question,String alt1,String alt2, String alt3,String alt4,String correctAnswer){


        ContentValues cvs = new ContentValues();
        cvs.put(QUESTION_KEY,question);
        cvs.put(ALTERNATIVE1_KEY, alt1);
        cvs.put(ALTERNATIVE2_KEY,alt2);
        cvs.put(ALTERNATIVE3_KEY,alt3);
        cvs.put(ALTERNATIVE4_KEY,alt4);
        cvs.put(CORRECT_ANSWER_KEY, correctAnswer);

        long id = db.insert(category, null, cvs);

        Log.d(TAG, "test id is " + id);
        Log.d(TAG, cvs.toString());

        //db.close();
    }

    //alternative 2, tar emot en objekt

    public void addQuestionsToDataBase(Question question){
        db = getWritableDatabase();

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
        db = getWritableDatabase();

        ContentValues cvs = new ContentValues();
        cvs.put(NAME_KEY,name);
        cvs.put(SCORE_KEY, score);

        long id = db.insert(HIGH_SCORE_TABLE, null, cvs);

        Log.d(TAG, "High scores test. id is " + id);

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
        db = getWritableDatabase();

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
        db = getReadableDatabase();
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

        db = getReadableDatabase();
        ArrayList<Question> questions = new ArrayList<Question>();

        Cursor cursor = db.query(category, null, null, null, null, null, "RANDOM() LIMIT 5");

        if(cursor.moveToFirst()){

            String question;
            String alternativeA;
            String alternativeB;
            String alternativeC;
            String alternativeD;
            String correctAnswer;

            do{

                question = cursor.getString(1);
                alternativeA = cursor.getString(2);
                alternativeB =cursor.getString(3);
                alternativeC = cursor.getString(4);
                alternativeD = cursor.getString(5);
                correctAnswer = cursor.getString(6);

                questions.add(new Question(category,question,alternativeA,alternativeB,alternativeC,alternativeD,correctAnswer));
                Log.d(TAG,"Cursor working"+ question+alternativeA+alternativeB+alternativeC+alternativeD+correctAnswer);

            }while(cursor.moveToNext());
        }
        db.close();
        return questions;
    }

    public ArrayList<String> getthreeCategories(){
        db = getReadableDatabase();
        ArrayList<String> categories = new ArrayList<String>();

        Cursor cursor = db.query(ALL_CATEGORY_TABLE, null, null, null, null, null, "RANDOM() LIMIT 3");

        if(cursor.moveToFirst()){
            String category;
            do{
                category = cursor.getString(1);
                categories.add(category);
                Log.d(TAG,"Cursor working"+ category);

            }while(cursor.moveToNext());
        }
        db.close();
        return categories;
    }

    public Question getQuestion( String category ){

        db = getReadableDatabase();
        Question finalquestion = null;

        Cursor cursor = db.query(category, null, null, null, null, null, "RANDOM() LIMIT 1");

        if(cursor.moveToFirst()){

            finalquestion= new Question(category,cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6));
            Log.d(TAG,"Cursor working");

        }
        db.close();
        return finalquestion;
    }

    public void createNewTable(String category, SQLiteDatabase db){

        sql = " CREATE TABLE " + category + " ( ";
        sql += ID_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT,";
        sql += QUESTION_KEY + " VARCHAR(225) NOT NULL,";
        sql += ALTERNATIVE1_KEY + " VARCHAR(225),";
        sql += ALTERNATIVE2_KEY + " VARCHAR(225),";
        sql += ALTERNATIVE3_KEY + " VARCHAR(225),";
        sql += ALTERNATIVE4_KEY + " VARCHAR(225),";
        sql += CORRECT_ANSWER_KEY + " VARCHAR(225)";
        sql += " );";

        db.execSQL(sql);

        ContentValues cvs = new ContentValues();
        cvs.put(CATEGORY_KEY,category);
        db.insert(ALL_CATEGORY_TABLE, null, cvs);

    }



}
