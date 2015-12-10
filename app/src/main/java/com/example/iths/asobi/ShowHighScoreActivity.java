package com.example.iths.asobi;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class ShowHighScoreActivity extends AppCompatActivity {
    private ListView listview;
    private DBHelper db;
    private SimpleCursorAdapter adapter;
    public static final String CATEGORY= "category";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_high_score);

        listview = (ListView) findViewById(R.id.show_high_score);
        db=DBHelper.getDbHelperInstance(this);

        Intent intent = getIntent();
        String category = intent.getStringExtra(CATEGORY);
        Cursor cursor;

        if (category.equals("ALL")){
            cursor = db.getHighScoreTable();
        }else{
            cursor= db.getCategorisedTable(category);
        }

        String [] from = {"name","score", "rank"};
        int [] to = {R.id.high_score_name,R.id.high_score_point,R.id.high_score_rank};
        adapter = new SimpleCursorAdapter(this, R.layout.high_scores_list_item,cursor,from, to, 0);

        listview.setAdapter(adapter);
    }
}
