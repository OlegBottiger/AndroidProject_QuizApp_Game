package com.example.iths.asobi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
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
    private static final String RANK_KEY = "rank" ;
    private static final String HIGH_SCORE_TABLE = "HighScores" ;
    private static final String PLAYER_TABLE = "Players";
    private static final String RANK_TABLE = "Rank";
    private static final String WHOLE_QUESTION_TABLE = "wholeQuestion";
    private static final String ALL_CATEGORY_TABLE = "allCategories";
    private static final String SPORT_CATEGORY = "Sports";
    private static final String MUSIC_CATEGORY ="Music" ;
    private static final String SCIENCE_CATEGORY = "Science";
    private static final String GEOGRAPHY_CATEGORY = "Geography";
    private static final String MATH_CATEGORY ="Mathematics" ;
    private static final String GAME_CATEGORY ="Games";

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

    public static String getNameKey() {
        return NAME_KEY;
    }

    public static String getScoreKey() {
        return SCORE_KEY;
    }

    public static String getRankKey() {
        return RANK_KEY;
    }

    public static String getPlayerTable() {
        return PLAYER_TABLE;
    }

    public static String getAllCategoryTable() {
        return ALL_CATEGORY_TABLE;
    }

    public static String getRankTable() {
        return RANK_TABLE;
    }

    public static String getWholeQuestionTable() {
        return WHOLE_QUESTION_TABLE;
    }

    public static String getCategoryKey() {
        return CATEGORY_KEY;
    }

    public static String getQuestionKey() {
        return QUESTION_KEY;
    }

    public static String getAlternative1Key() {
        return ALTERNATIVE1_KEY;
    }

    public static String getAlternative2Key() {
        return ALTERNATIVE2_KEY;
    }

    public static String getAlternative3Key() {
        return ALTERNATIVE3_KEY;
    }

    public static String getAlternative4Key() {
        return ALTERNATIVE4_KEY;
    }

    public static String getCorrectAnswerKey() {
        return CORRECT_ANSWER_KEY;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        sql = " CREATE TABLE " + ALL_CATEGORY_TABLE + " ( ";
        sql += ID_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT,";
        sql += CATEGORY_KEY + " VARCHAR(225) NOT NULL";
        sql += " );";

        db.execSQL(sql);

        insertCategory(db, SPORT_CATEGORY);
        insertCategory(db, MUSIC_CATEGORY);
        insertCategory(db, SCIENCE_CATEGORY);
        insertCategory(db, GEOGRAPHY_CATEGORY);
        insertCategory(db, MATH_CATEGORY);
        insertCategory(db, GAME_CATEGORY);

        sql = " CREATE TABLE " + HIGH_SCORE_TABLE + " ( ";
        sql += ID_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT,";
        sql += NAME_KEY + " VARCHAR(225) NOT NULL,";
        sql += SCORE_KEY + " INTEGER,";
        sql += ALL_CATEGORY_TABLE + " INTEGER";
        sql += " );";

        db.execSQL(sql);

        sql = " CREATE TABLE " + RANK_TABLE + " ( ";
        sql += ID_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, ";
        sql += RANK_KEY + " INTEGER";
        sql += " );";
        db.execSQL(sql);

        sql = " CREATE TABLE " + PLAYER_TABLE + " ( ";
        sql += ID_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, ";
        sql += NAME_KEY + " VARCHAR(225) NOT NULL ";
        sql += " );";
        db.execSQL(sql);

        // Sets name "Guest" to PLAYER_TABLE as a default name
        ContentValues cvs = new ContentValues();
        cvs.put(NAME_KEY, "Guest");
        db.insert(PLAYER_TABLE, null, cvs);

        sql = " CREATE TABLE " + WHOLE_QUESTION_TABLE + " ( ";
        sql += ID_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, ";
        sql += QUESTION_KEY + " VARCHAR(225) NOT NULL, ";
        sql += ALTERNATIVE1_KEY + " VARCHAR(225), ";
        sql += ALTERNATIVE2_KEY + " VARCHAR(225), ";
        sql += ALTERNATIVE3_KEY + " VARCHAR(225), ";
        sql += ALTERNATIVE4_KEY + " VARCHAR(225), ";
        sql += CORRECT_ANSWER_KEY + " VARCHAR(225) ,";
        sql += ALL_CATEGORY_TABLE + " INTEGER ";
        sql += " );";

        db.execSQL(sql);

        // Sets information to WHOLE_QUESTION_TABLE
        // Sports questions
        addQuestionsToDataBase(db," Which sports is the most popular sport in Sweden?","Tennis","Soccer","Ice hockey","Bandy","4",1);
        addQuestionsToDataBase(db," Who won The World Highland Games Championships a record six times?","Geoff Capes","someone","someone","someone","1",1);
        addQuestionsToDataBase(db," What jobs did Mike ‘Fluff’ Cowan, Jim ‘Bones’ Mackay and Fanny Sunesson do?","something","Golf caddies","something","something","2",1);
        addQuestionsToDataBase(db," Which former rugby player once called the English RFU committee 'Old Farts'?", "something", "something", "Will Carling", "something", "3",1);
        addQuestionsToDataBase(db," In inches, how big is the diameter of a basketball hoop?", "11", "13", "16", "18", "4",1);

        // Music questions
        addQuestionsToDataBase(db," music 1","Rihanna","Maroon 5","Pitbull","Justin Bieber","4",2);
        addQuestionsToDataBase(db," music 2","jazz","rock","classic","hiphop","4",2);
        addQuestionsToDataBase(db," music 3","Adele","Avicii","Drake","Ariana Grande","4",2);
        addQuestionsToDataBase(db," music 4","Lady Gaga","Beyonce","Michael Jackson","Madonna","4",2);
        addQuestionsToDataBase(db," music 5","Katy Perry","Elle King","Shawn Mendes","Ellie Goulding","4",2);

        // Science questions
        addQuestionsToDataBase(db," Who came up with the Theory of Relativity?", "Robert Brown", "John Dalton", "Erwin Schrödinger", "Albert Einstein", "4", 3);
        addQuestionsToDataBase(db," Approximately how old is the universe?", "11,5 billion years", "12,3 billion years", "13,8 billion years", "14,6 billion years", "3", 3);
        addQuestionsToDataBase(db," What type of gas does a star mostly contain of?", "Oxygen and Helium", "Hydrogen and Helium", "Nitrogen and Boron", "Lithium and Neon", "2", 3);
        addQuestionsToDataBase(db," Which year was the Hubble space telescope sent into space?", "1990", "1987", "1992", "1994", "1", 3);
        addQuestionsToDataBase(db," Which letters does Gold have in the periodic table?", "Al", "Au", "Ac", "Ag", "2", 3);
        addQuestionsToDataBase(db," What is Earth's circumference?", "39,895km", "38,371km", "41,550km", "40,075km", "4", 3);
        addQuestionsToDataBase(db," Which planet is closest to the sun in our solar system?", "Uranus", "Mars", "Mercury", "Venus", "3", 3);

        // Geography questions
        addQuestionsToDataBase(db," What is the capital city of Iran?", "Teheran", "Ankara", "Dushanbe", "Riyadh", "1", 4);
        addQuestionsToDataBase(db," Which lake is the biggest in Sweden?", "Mälaren", "Vättern", "Vänern", "Storsjön", "3", 4);
        addQuestionsToDataBase(db," What percentage of Japan consist of mountains?", "80%", "54%", "73%", "63%", "3", 4);

        // Mathematics questions
        addQuestionsToDataBase(db,"What is the value of pi(π)?", "3.14159265359", "3.1321343", "3.412131436", "4.130054", "1", 5);
        addQuestionsToDataBase(db,"What is 2+2?", "22", "Cheese Cakes", "2", "4", "4", 5);
        addQuestionsToDataBase(db,"How do you calculate the circumference of a circle?", "C=2πr", "C=2πd", "C=4πr", "A=wl", "1", 5);

        // Games questions
        addQuestionsToDataBase(db," What is Mario & Luigi’s last name?","Luigi","Mario","Lombardi","Alfredo","2",6);
        addQuestionsToDataBase(db," When was Nintendo as a company founded?","1991","1979","1889","1981","3",6);
        addQuestionsToDataBase(db," Before Nintendo made Video Games they made...","Card Games","Chairs","Electronics","Amusement Parks","1",6);
        addQuestionsToDataBase(db," Who is the creator of Super Mario?", "Satoru Iwata", "Reginald Fils-Aime", "Shigeru Miyamoto", "Gunpei Yokoi", "3",6);
        addQuestionsToDataBase(db," What is the name of the main character in the “The Legend of Zelda”?","Zelda","Link","Roy","Master Chief","2",6);
        addQuestionsToDataBase(db," How many Pokémons were in the first generation of Pokémon? ","150","100","200","151","4",6);
        addQuestionsToDataBase(db," Which platform was Halo: Combat Evolved first in development for? ","MAC OS X","Windows","Linux","Xbox","1",6);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // one method to add a question to the data base.
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
    public void addHighScore(String name, int score, int category){
        db = getWritableDatabase();

        ContentValues cvs = new ContentValues();
        cvs.put(NAME_KEY, name);
        cvs.put(SCORE_KEY, score);
        cvs.put(ALL_CATEGORY_TABLE, category);

        long id = db.insert(HIGH_SCORE_TABLE, null, cvs);

        Log.d(TAG, "High scores test. id is " + id);

    }

    /**
     * retrieves information from HIGH_SCORE_TABLE and returns it as a list of integers.
     * If there is no information in the table yet then it returns a list which includes only one element "0".
     * @return - a list of high scores
     */

    public ArrayList<Integer> getHighScore(String category){
        db= getWritableDatabase();
        ArrayList<Integer> highScores = new ArrayList<Integer>();
        String[] columns={SCORE_KEY,ALL_CATEGORY_TABLE};
        long id = getIdByCategoryName(category);

        String selection = "allCategories=?";
        String[] selectionArgs = {Integer.toString(getIdByCategoryName(category))};

        Cursor cursor;
            if(id==0){
                cursor = db.query(HIGH_SCORE_TABLE, columns, null, null, null, null,"score DESC" );
            } else {
                cursor = db.query(HIGH_SCORE_TABLE, columns, selection, selectionArgs, null, null, "score DESC");
            }

            if(cursor.moveToFirst()){
                int score;
                do{
                    score = cursor.getInt(0);
                    highScores.add(score);

                }while(cursor.moveToNext());
            }else{
                highScores.add(0);
            }

        //just for a check
        Log.d(TAG, "high scores are " + highScores);
        return highScores;
    }

    /**
     * This method takes a number which you want to check which rank it is in.
     * If there are numbers which are the same then they are the same rank.
     *
     * @param highScore - a list of high scores which is retrieved from the data base.
     * @param finalScore - player's final score. It is checked which rank it is in.
     * @return - the rank
     */
    public int getRank(ArrayList<Integer> highScore, int finalScore){

        ArrayList<Integer> newHighScore = new ArrayList<Integer>();

            for( int i = 0; i < highScore.size(); i++){

                if(!newHighScore.contains(highScore.get(i))){
                    newHighScore.add(highScore.get(i));
                }
            }

        int rank = 1;

            for( int i = 0; i < newHighScore.size(); i++){

                if(newHighScore.get(i) > finalScore){
                    rank++;
                }
            }
        return rank;
    }

    /**
     *
     * @param tableName - a name of a table
     * @return category's cursor
     */
    public Cursor getOneTable(String tableName){
        return getReadableDatabase().query(tableName,null,null,null,null,null,null);
    }

    public Cursor getHighScoreTable(){

        return getReadableDatabase().query(HIGH_SCORE_TABLE,null,null,null,null,null,"score DESC");
    }

    /**
     * adds a player's name to the data base
     * @param name - player's name
     */
    public void addProfile(String name){
        db = getWritableDatabase();

        ContentValues cvs = new ContentValues();
        cvs.put(NAME_KEY,name);

        long id = db.insert(PLAYER_TABLE, null, cvs);
        Log.d(TAG, "Player table test, ID is"+ id);
        db.close();

    }

    public Cursor getCursorForOnesCategory(String category){

        db= getWritableDatabase();

        String selection = "allCategories=?";
        String[] selectionArgs={Integer.toString(getIdByCategoryName(category))};

        Cursor cursor = db.query(WHOLE_QUESTION_TABLE, null, selection, selectionArgs, null, null, null);

        return cursor;

    }

    public Cursor getCategorisedTable(String category) {
        db= getWritableDatabase();
        String selection = "allCategories=?";
        String[] selectionArgs={Integer.toString(getIdByCategoryName(category))};
        Cursor cursor = db.query(HIGH_SCORE_TABLE, null, selection, selectionArgs, null, null, "score DESC");
        return cursor;
    }

    public int getLengthOfQuestions(String category){
        db = getReadableDatabase();
        Cursor cursor = getCursorForOnesCategory(category);
        int lengthOfQuestions = 0;

        if(cursor.moveToFirst()){
            do{
                lengthOfQuestions++;

            } while(cursor.moveToNext());
        }
        return lengthOfQuestions;
    }

    /**
     *
     * @param categoryID
     * @return list of instances of Question
     */
    public ArrayList<Question> getRandomFiveQuestions(int categoryID){

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
                category= getCategoryByID(cursor.getInt(7));

                questions.add(new Question(category,question,alternativeA,alternativeB,alternativeC,alternativeD,correctAnswer));
                //just for a check
                Log.d(TAG,"Cursor working"+ question+alternativeA+alternativeB+alternativeC+alternativeD+correctAnswer);

            }while(cursor.moveToNext());
        }
        db.close();
        return questions;
    }

    /**
     * gets category's name by category's ID
     *
     * @param categoryID category's ID
     * @return category's name
     */
    public String getCategoryByID(int categoryID){

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

    /**
     * gets category's ID by category's name
     * @param category category's name
     * @return category's ID
     */
    public int getIdByCategoryName(String category){

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
        Log.d(TAG,"This category's ID is "+ categoryId);
        return categoryId;
    }

    /**
     * adds category's name to the ALL_CATEGORY_TABLE in the data base.
     * @param db
     * @param category category's name
     */
    public void insertCategory(SQLiteDatabase db, String category){

        ContentValues cvs = new ContentValues();
        cvs.put(CATEGORY_KEY, category);

        long id = db.insert(ALL_CATEGORY_TABLE, null, cvs);

        Log.d(TAG, "All category table test. id is " + id);

    }

    public void insertRank(int rank){
        ContentValues cvs = new ContentValues();
        cvs.put(RANK_KEY, rank);
        db.insert(RANK_TABLE, null, cvs);
    }


    public void deleteProfile(String name) {
        db = getWritableDatabase();
        String selection = "name=?";
        String[] selectionArgs = {name};

        db.delete(PLAYER_TABLE, selection, selectionArgs);
        db.close();
    }

    public void deleteCategory(String category){

        db = getWritableDatabase();

        String[] selectionArg = new String[]{Integer.toString(getIdByCategoryName(category))};
        db.delete(WHOLE_QUESTION_TABLE,"allCategories=?",selectionArg);

        String[] selectionArgs = new String[]{category};
        db.delete(ALL_CATEGORY_TABLE, "category=?", selectionArgs);

        db.close();
    }

    public void deleteRank() {
        db = getWritableDatabase();
        db.delete(RANK_TABLE, RANK_KEY, null);
        db.delete(RANK_TABLE, ID_KEY, null);
        Log.d("rank test", "test rank");
    }

}
