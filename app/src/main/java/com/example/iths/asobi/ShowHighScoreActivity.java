package com.example.iths.asobi;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;

public class ShowHighScoreActivity extends AppCompatActivity {
    private ListView listview;
    private DBHelper db;
    private SimpleCursorAdapter adapter;
    public static final String CATEGORY= "category";
    private String category;
    private  Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_high_score);

        listview = (ListView) findViewById(R.id.show_high_score);
        db=DBHelper.getDbHelperInstance(this);

        Intent intent = getIntent();
        category = intent.getStringExtra(CATEGORY);

            if (category.equals("ALL")){
                cursor = db.getHighScoreTable();
            }else{
                cursor= db.getCategorisedTable(category);
            }

        String [] from = {db.getNameKey(),db.getScoreKey()};
        int [] to = {R.id.high_score_name,R.id.high_score_point};
        adapter = new SimpleCursorAdapter(this, R.layout.high_scores_list_item,cursor,from, to, 0);
        listview.setAdapter(adapter);
        
        //refresh RANK_TABLE
        db.deleteRank();
        ArrayList<Integer> highscore = db.getHighScore(category);

            if(highscore.get(0)!= 0) {

                for (int i = 0; i < highscore.size(); i++) {
                    db.insertRank(db.getRank(highscore, highscore.get(i)));
                }
                cursor = db.getOneTable(db.getRankTable());
                ListView rank = (ListView) findViewById(R.id.show_rank);

                String[] from2 = {db.getRankKey()};
                int[] to2 = {R.id.high_score_rank_test};
                adapter = new SimpleCursorAdapter(this, R.layout.rank_list_item, cursor, from2, to2, 0);
                rank.setAdapter(adapter);
            }

    }

}
