package com.example.iths.asobi;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class CustomCategoryActivity extends AppCompatActivity {

    private DBHelper db;
    private ListView listview;
    private EditText categoryInput;
    private SimpleCursorAdapter adapter;
    private String z;
    private String categoryName;

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

        Cursor categories = db.getOneTable(db.getAllCategoryTable());
        String [] from = {db.getCategoryKey()};
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
            String s = cur.getString(cur.getColumnIndex(db.getCategoryKey()));

            Intent intent = new Intent(CustomCategoryActivity.this, CustomQuestionActivity.class);

            intent.putExtra(CustomQuestionActivity.CATEGORY,s);
            startActivity(intent);

        }
    };

    private AdapterView.OnItemLongClickListener listListenerLong = new AdapterView.OnItemLongClickListener(){

        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id){

            Cursor cur = (Cursor) parent.getItemAtPosition(position);
            cur.moveToPosition(position);
            z = cur.getString(cur.getColumnIndex(db.getCategoryKey()));

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
                        db.deleteCategory(z);
                        Cursor cursor = db.getOneTable(db.getAllCategoryTable());
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
    public void addCategory(View view) {

        categoryInput = (EditText) findViewById(R.id.name_category);
        String categoryName = categoryInput.getText().toString();

        Cursor cursor = db.getOneTable(db.getAllCategoryTable());
        int sameTable = 0;

            if(cursor.moveToFirst()){
                String category;
                do{
                    category = cursor.getString(1);
                        if(categoryName.equals(category)){
                            sameTable++;
                        }
                }while(cursor.moveToNext());
            }

        if(sameTable > 0){
            Toast.makeText(this, "There is already "+categoryName+" category!", Toast.LENGTH_SHORT).show();
            categoryInput.getText().clear();
        } else {

            db.insertCategory(db.getWritableDatabase(), categoryName);
            cursor = db.getOneTable(db.getAllCategoryTable());
            adapter.changeCursor(cursor);
            categoryInput.getText().clear();

        }
    }
}
