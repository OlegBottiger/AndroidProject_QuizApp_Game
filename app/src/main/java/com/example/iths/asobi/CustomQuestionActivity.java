package com.example.iths.asobi;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class CustomQuestionActivity extends AppCompatActivity {

    private DBHelper db;
    private ListView list;
    public static final String CATEGORY="category";
    private Cursor customQuestiosCursor;
    private String category;
    private String longClickString;
    private SimpleCursorAdapter adapter;
    private String idFromPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_question);
        db = DBHelper.getDbHelperInstance(this);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        setContentView(R.layout.activity_custom_question);

        Intent intent =getIntent();
        category = intent.getStringExtra(CATEGORY);

        if(category.equals("ALL")) {
            customQuestiosCursor = db.getOneTable(DBHelper.getWholeQuestionTable());
        } else{
            customQuestiosCursor= db.getCursorForOnesCategory(category);
        }

        list = (ListView) findViewById(R.id.listview_custom_question);

        String[] from = new String[] {db.getQuestionKey(), db.getAlternative1Key(), db.getAlternative2Key(), db.getAlternative3Key(), db.getAlternative4Key(), db.getCorrectAnswerKey()};
        int[] to = new int[] {R.id.debug_question, R.id.debug_ans1, R.id.debug_ans2, R.id.debug_ans3, R.id.debug_ans4};
        adapter = new SimpleCursorAdapter(this, R.layout.question_list, customQuestiosCursor, from, to, 0);

        list.setAdapter(adapter);
        list.setOnItemLongClickListener(listListenerLong);
    }

    private AdapterView.OnItemLongClickListener listListenerLong = new AdapterView.OnItemLongClickListener(){

        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id){

            Cursor cur = (Cursor) parent.getItemAtPosition(position);
            cur.moveToPosition(position);
            longClickString = cur.getString(cur.getColumnIndex(db.getIdKey()));
            idFromPosition = longClickString;

            AlertDialog diaBox = AskOption();
            diaBox.show();

            return true;
        }

    };

    public void goToQuestions(View view) {
        Intent i = new Intent(this, CustomQuestionActivity.class);
        i.putExtra(CustomQuestionActivity.CATEGORY,"ALL");
        startActivity(i);
    }

    private AlertDialog AskOption() {
        AlertDialog myQuittingDialogBox =new AlertDialog.Builder(this)
                //set message, title, and icon
                .setTitle("Delete")
                .setMessage("Do you want to delete?")
                .setIcon(R.drawable.ic_delete)

                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        db.deleteQuestion(idFromPosition);
                        Cursor cursor = db.getOneTable(db.getWholeQuestionTable());
                        adapter.changeCursor(cursor);
                        dialog.dismiss();
                    }

                })



                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                })
                .create();
        return myQuittingDialogBox;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_my, menu);
        return true;
    }
    /**
     * Handles the item clicks here.
     * @param item is the symbol showed up on the actionbar.
     * @return returns true if clicked and takes you to the next activity.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_play:
                // Play action
                Intent i = new Intent(CustomQuestionActivity.this, GameModeActivity.class);
                startActivity(i);
                return true;
            case R.id.info:
                // Asobi presentation activity
                Intent j = new Intent(CustomQuestionActivity.this, AboutActivity.class);
                startActivity(j);
                return true;
            case R.id.profile:
                // Create profile activity
                Intent k = new Intent(CustomQuestionActivity.this, ProfilesActivity.class);
                startActivity(k);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void goToAddQuestions(View view) {
        Intent intent = new Intent(this, CustomQuestionAddActivity.class);
        intent.putExtra(CustomQuestionAddActivity.CATEGORY,category);
        startActivity(intent);
    }
}

