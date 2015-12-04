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

    public static final String ID_KEY = "_id";
    private static final String QUESTION_KEY = "question";
    private static final String ALTERNATIVE1_KEY = "alternative1";
    private static final String ALTERNATIVE2_KEY = "alternative2";
    private static final String ALTERNATIVE3_KEY = "alternative3";
    private static final String ALTERNATIVE4_KEY = "alternative4";
    private static final String CORRECT_ANSWER_KEY = "correctAnswer";
    private static final String TAG= "debug";
    private static final String CATEGORY_KEY = "category";
    private static final String WHOLE_QUESTION_TABLE = "wholeQuestion" ;


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

        //those are for the test. I remove those later
        ContentValues cvs = new ContentValues();
        cvs.put(NAME_KEY,"Joe");
        cvs.put(SCORE_KEY, 58);
        db.insert(HIGH_SCORE_TABLE, null, cvs);

        cvs = new ContentValues();
        cvs.put(NAME_KEY,"Michael");
        cvs.put(SCORE_KEY, 58);
        db.insert(HIGH_SCORE_TABLE, null, cvs);

        cvs = new ContentValues();
        cvs.put(NAME_KEY,"Maria");
        cvs.put(SCORE_KEY, 58);
        db.insert(HIGH_SCORE_TABLE, null, cvs);

        cvs = new ContentValues();
        cvs.put(NAME_KEY,"Johanna");
        cvs.put(SCORE_KEY, 20);
        db.insert(HIGH_SCORE_TABLE, null, cvs);

        cvs = new ContentValues();
        cvs.put(NAME_KEY,"Mark");
        cvs.put(SCORE_KEY, 6);
        db.insert(HIGH_SCORE_TABLE, null, cvs);


        sql = " CREATE TABLE " + PLAYER_TABLE + " ( ";
        sql += ID_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT,";
        sql += NAME_KEY + " VARCHAR(225) NOT NULL";
        sql += " );";
        db.execSQL(sql);

        // Sets name "Guest" to PLAYER_TABLE as a default name
        cvs = new ContentValues();
        cvs.put(NAME_KEY, "Guest");
        db.insert(PLAYER_TABLE, null, cvs);


        sql = " CREATE TABLE " + ALL_CATEGORY_TABLE + " ( ";
        sql += ID_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT,";
        sql += CATEGORY_KEY + " VARCHAR(225) NOT NULL";
        sql += " );";

        db.execSQL(sql);

        insertCategory(db,SPORT_TABLE);
        insertCategory(db,MUSIC_TABLE);
        insertCategory(db,SCIENCE_TABLE);
        insertCategory(db,GEOGRAPHY_TABLE);
        insertCategory(db,MATH_TABLE);
        insertCategory(db,GAME_TABLE);


        sql = " CREATE TABLE " + WHOLE_QUESTION_TABLE + " ( ";
        sql += ID_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT,";
        sql += QUESTION_KEY + " VARCHAR(225) NOT NULL,";
        sql += ALTERNATIVE1_KEY + " VARCHAR(225),";
        sql += ALTERNATIVE2_KEY + " VARCHAR(225),";
        sql += ALTERNATIVE3_KEY + " VARCHAR(225),";
        sql += ALTERNATIVE4_KEY + " VARCHAR(225),";
        sql += CORRECT_ANSWER_KEY + " VARCHAR(225),";
        sql += ALL_CATEGORY_TABLE + " INTEGER";
        sql += " );";

        db.execSQL(sql);

        // Sets information to WHOLE_QUESTION_TABLE ( just for test for now )

        addQuestionsToDataBase(db," Which sports is the most popular sport in Sweden?","Tennis","Soccer","Ice hockey","Bandy","4",1);
        addQuestionsToDataBase(db," Who won The World Highland Games Championships a record six times?","Geoff Capes","someone","someone","someone","1",1);
        addQuestionsToDataBase(db," What jobs did Mike ‘Fluff’ Cowan, Jim ‘Bones’ Mackay and Fanny Sunesson do?","something","Golf caddies","something","something","2",1);
        addQuestionsToDataBase(db," Which former rugby player once called the English RFU committee 'Old Farts'?", "something", "something", "Will Carling", "something", "3",1);
        addQuestionsToDataBase(db," In inches, how big is the diameter of a basketball hoop?", "11", "13", "16", "18", "4",1);

        addQuestionsToDataBase(db," music 1","Rihanna","Maroon 5","Pitbull","Justin Bieber","4",2);
        addQuestionsToDataBase(db," music 2","jazz","rock","classic","hiphop","4",2);
        addQuestionsToDataBase(db," music 3","Adele","Avicii","Drake","Ariana Grande","4",2);
        addQuestionsToDataBase(db," music 4","Lady Gaga","Beyonce","Michael Jackson","Madonna","4",2);
        addQuestionsToDataBase(db," music 5","Katy Perry","Elle King","Shawn Mendes","Ellie Goulding","4",2);

        addQuestionsToDataBase(db," What is Mario & Luigi’s last name? ","Luigi","Mario","Lombardi","Alfredo","2",6);
        addQuestionsToDataBase(db," When was Nintendo as a company founded?","1991","1979","1889","1981","3",6);
        addQuestionsToDataBase(db," Before Nintendo made Video Games they made...","Card Games","Chairs","Electronics","Amusement Parks","1",6);
        addQuestionsToDataBase(db," Who is the creator of Super Mario?", "Satoru Iwata", "Reginald Fils-Aime", "Shigeru Miyamoto", "Gunpei Yokoi", "3",6);
        addQuestionsToDataBase(db," What is the name of the main character in the “The Legend of Zelda”?","Zelda","Link","Roy","Master Chief","2",6);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // one method to add a question to the data base.
    // I have to fix this because db could cause a problem (it may happen recursively)


    public void addQuestionsToDataBase(SQLiteDatabase db,String question,String alt1,String alt2, String alt3,String alt4,String correctAnswer,int category){

        ContentValues cvs = new ContentValues();
        cvs.put(QUESTION_KEY,question);
        cvs.put(ALTERNATIVE1_KEY, alt1);
        cvs.put(ALTERNATIVE2_KEY,alt2);
        cvs.put(ALTERNATIVE3_KEY,alt3);
        cvs.put(ALTERNATIVE4_KEY,alt4);
        cvs.put(CORRECT_ANSWER_KEY, correctAnswer);
        cvs.put(ALL_CATEGORY_TABLE, category);

        long id = db.insert(WHOLE_QUESTION_TABLE, null, cvs);

        Log.d(TAG, "test id is " + id);
        Log.d(TAG, cvs.toString());

    }

    // one method to add a high score to the data base
    public void addHighScore(String name, int score){
        db = getWritableDatabase();

        ContentValues cvs = new ContentValues();
        cvs.put(NAME_KEY,name);
        cvs.put(SCORE_KEY, score);

        long id = db.insert(HIGH_SCORE_TABLE, null, cvs);

        Log.d(TAG, "High scores test. id is " + id);

    }

    public ArrayList<Integer> getHighScore(){
        db= getWritableDatabase();
        ArrayList<Integer> highScores = new ArrayList<Integer>();
        String[] columns={"score"};

        Cursor cursor = db.query(HIGH_SCORE_TABLE, columns, null, null, null, null, "score DESC");

        if(cursor.moveToFirst()){
            int score;
            do{
                score = cursor.getInt(0);
                highScores.add(score);

            }while(cursor.moveToNext());
        }else{
            highScores.add(0);
        }

        db.close();

        //just for a check
        Log.d(TAG, "high scores are " + highScores);
        return highScores;
    }

    public int getRank(ArrayList<Integer> highScore, int myScore){

        int rank = 1;
        int multiple = 0;

        for( int i =0; i < highScore.size(); i++){

            if(highScore.get(i) >= myScore){
                rank++;

                if(highScore.get(i)== myScore){
                    multiple++;
                }
            }
        }
        if (multiple==0){
            return rank ;
        }else return rank-multiple;

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
     * @param categoryID
     * @return list of instances of Question
     */
    public ArrayList<Question> getRandomFiveQuestions(int categoryID ){

        db= getWritableDatabase();
        ArrayList<Question> questions = new ArrayList<Question>();

        String selection = "allCategories=?";
        String[] selectionArgs={Integer.toString(categoryID)};

        Cursor cursor;

        if(categoryID ==0){
            cursor = db.query(WHOLE_QUESTION_TABLE, null, null, null, null, null, "RANDOM() LIMIT 5");
        } else {
            cursor = db.query(WHOLE_QUESTION_TABLE, null, selection, selectionArgs, null, null, "RANDOM() LIMIT 5");
        }

        if(cursor.moveToFirst()){

            String question;
            String alternativeA;
            String alternativeB;
            String alternativeC;
            String alternativeD;
            String correctAnswer;
            String category;

            do{
                question = cursor.getString(1);
                alternativeA = cursor.getString(2);
                alternativeB =cursor.getString(3);
                alternativeC = cursor.getString(4);
                alternativeD = cursor.getString(5);
                correctAnswer = cursor.getString(6);
                category= getCategoryFromCategoryTableByID(cursor.getInt(7));

                questions.add(new Question(category,question,alternativeA,alternativeB,alternativeC,alternativeD,correctAnswer));
                //just for a check
                Log.d(TAG,"Cursor working"+ question+alternativeA+alternativeB+alternativeC+alternativeD+correctAnswer);

            }while(cursor.moveToNext());
        }
        db.close();
        return questions;
    }

    public String getCategoryFromCategoryTableByID(int categoryID){

        db = getReadableDatabase();
        String category = null;
        String selection = "_id=?";
        String[] selectionArgs={Integer.toString(categoryID)};

        Cursor cursor = db.query(ALL_CATEGORY_TABLE, null,selection, selectionArgs, null, null,null);

        if(cursor.moveToFirst()){
            do{
                category = cursor.getString(1);
                Log.d(TAG,"Category's name by this id is "+ category);
            }while(cursor.moveToNext());
        }
        db.close();
        return category;
    }


    public int getIdFromCategoryTableByCategoryName(String category){

        db = getReadableDatabase();
        int categoryId=0;
        String selection = "category=?";
        String[] selectionArgs={category};

        Cursor cursor = db.query(ALL_CATEGORY_TABLE, null,selection, selectionArgs, null, null,null);

        if(cursor.moveToFirst()){
            do{
                categoryId = cursor.getInt(0);
                Log.d(TAG,"This category's ID is "+ categoryId);
            }while(cursor.moveToNext());
        }
        db.close();
        return categoryId;
    }

    public void insertCategory(SQLiteDatabase db, String category){

        ContentValues cvs = new ContentValues();
        cvs.put(CATEGORY_KEY, category);

        long id = db.insert(ALL_CATEGORY_TABLE, null, cvs);

        Log.d(TAG, "All category table test. id is " + id);

    }

}
