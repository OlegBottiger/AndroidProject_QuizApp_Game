package com.example.iths.asobi;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Player can choose which category she wants to customize from this activity.
 * Player can create and delete her own category.
 *
 */
public class CustomCategoryActivity extends AppCompatActivity {

    private DBHelper db;
    private ListView listview;
    private EditText categoryInput;
    private SimpleCursorAdapter adapter;
    private String z;
    private String categoryName;
    public static String currentPlayer = "Guest";
    private MediaPlayer mpb;
    private Player player = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_category);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        setContentView(R.layout.activity_custom_category);

        player = Player.getPlayerInstance("Guest");
        currentPlayer = player.getName();

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

    /**
     * Gets the actionbar.
     * @param menu the actionbar menu.
     * @return true so you can see the actionbar.
     */
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
                playClickSound();
                // Play action
                Intent i = new Intent(CustomCategoryActivity.this, GameModeActivity.class);
                startActivity(i);
                return true;
            case R.id.info:
                playClickSound();
                // Asobi presentation activity
                Intent j = new Intent(CustomCategoryActivity.this, AboutActivity.class);
                startActivity(j);
                return true;
            case R.id.profile:
                playClickSound();
                // Create profile activity
                Intent k = new Intent(CustomCategoryActivity.this, ProfilesActivity.class);
                startActivity(k);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
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

    private AlertDialog AskOption() {
        AlertDialog myQuittingDialogBox =new AlertDialog.Builder(this)
                //set message, title, and icon
                .setTitle(R.string.delete)
                .setMessage(R.string.do_you_want_to_delete)
                .setIcon(R.drawable.ic_delete)

                .setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        db.deleteCategory(z);
                        Cursor cursor = db.getOneTable(db.getAllCategoryTable());
                        adapter.changeCursor(cursor);
                        dialog.dismiss();
                    }

                })

                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                })
                .create();
        return myQuittingDialogBox;
    }

    /**
     * Handles the information and creates a category from the input.
     * @param view checks if there already is a category with that name.
     */
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
            Toast.makeText(this, String.format(getString(R.string.category_already_exists), categoryName), Toast.LENGTH_SHORT).show();
            categoryInput.getText().clear();
        } else {

            db.insertCategory(db.getWritableDatabase(), categoryName);
            cursor = db.getOneTable(db.getAllCategoryTable());
            adapter.changeCursor(cursor);
            categoryInput.getText().clear();
            Toast.makeText(this, String.format(getString(R.string.category_added), categoryName), Toast.LENGTH_SHORT).show();
        }
    }
    /**
     * A method used for making the actionbar buttons do a sound when clicked.
     */
    public void playClickSound() {
        mpb = MediaPlayer.create(this, R.raw.test);
        mpb.start();
    }
}
