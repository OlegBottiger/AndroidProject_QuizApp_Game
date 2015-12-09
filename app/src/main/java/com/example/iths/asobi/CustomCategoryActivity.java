package com.example.iths.asobi;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class CustomCategoryActivity extends AppCompatActivity {

    private DBHelper db;
    private ListView listview;
    private SimpleCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_category);


        TextView question = (TextView) findViewById(R.id.debug_question);
        TextView ans1 = (TextView) findViewById(R.id.debug_ans1);
        TextView ans2 = (TextView) findViewById(R.id.debug_ans2);
        TextView ans3 = (TextView) findViewById(R.id.debug_ans3);
        TextView ans4 = (TextView) findViewById(R.id.debug_ans4);
//        TextView correctect = (TextView) findViewById(R.id.debug_correct_answer);


 /*       question.setText();
        ans1.setText();
        ans2.setText();
        ans3.setText();
        ans4.setText();
        correct.setText();
*/


        listview = (ListView) findViewById(R.id.list_of_categories);
        db=DBHelper.getDbHelperInstance(this);

        Cursor categories = db.getAllTable("allCategories");
        String [] from = {"category"};
        int [] to = {R.id.category_name};
        adapter = new SimpleCursorAdapter(this, R.layout.deletable_category_list_item,categories ,from, to, 0);

        listview.setAdapter(adapter);
        listview.setOnItemClickListener(listListener);
        listview.setOnItemLongClickListener(listListenerLong);

    }

    private AdapterView.OnItemClickListener listListener = new AdapterView.OnItemClickListener(){

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id){

            Cursor cur = (Cursor) parent.getItemAtPosition(position);
            cur.moveToPosition(position);
            String s = cur.getString(cur.getColumnIndex("category"));

            Intent intent = new Intent(CustomCategoryActivity.this, CustomQuestionActivity.class);

            intent.putExtra(CustomQuestionActivity.CATEGORY,s);
            startActivity(intent);

        }
    };

    private AdapterView.OnItemLongClickListener listListenerLong = new AdapterView.OnItemLongClickListener(){

        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id){


            Log.d("list test", "do you want to delete this?");

            Cursor cur = (Cursor) parent.getItemAtPosition(position);
            cur.moveToPosition(position);
            String s = cur.getString(cur.getColumnIndex("category"));

            //Intent intent = new Intent(CustomCategoryActivity.this, GameActivity.class);

            //intent.putExtra(GameActivity.CATEGORY, s);
            //startActivity(intent);
            db.deleteCategory(s);
            Cursor cursor = db.getAllTable("allCategories");
            adapter.changeCursor(cursor);


            return true;
        }

    };

    public void goToQuestions(View view) {
        Intent i = new Intent(this, CustomQuestionActivity.class);
        i.putExtra(CustomQuestionActivity.CATEGORY,"ALL");
        startActivity(i);
    }

}
