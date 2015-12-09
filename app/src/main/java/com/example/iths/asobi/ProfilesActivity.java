package com.example.iths.asobi;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.database.Cursor;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;


public class ProfilesActivity extends AppCompatActivity {

    private ListView listView;
    private DBHelper dbHelper;
    private TextView textView;
    SimpleCursorAdapter adapter;
    private String z;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profiles);

        //Set actionbar item
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        setContentView(R.layout.activity_profiles);

        //Create profile
        listView = (ListView) findViewById(R.id.profile_list);
        dbHelper = DBHelper.getDbHelperInstance(this);

        Cursor players = dbHelper.getPlayers();

        String [] from = {dbHelper.getName()};
        int [] to = {R.id.profile_name};
        adapter = new SimpleCursorAdapter(this, R.layout.profile_list_item,players ,from, to, 0);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(listListener);
        listView.setOnItemLongClickListener(listListenerLong);

    }

    private AdapterView.OnItemClickListener listListener = new AdapterView.OnItemClickListener(){

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id){

            //Login

        }
    };

    private AdapterView.OnItemLongClickListener listListenerLong = new AdapterView.OnItemLongClickListener(){

        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id){

            Cursor cur = (Cursor) parent.getItemAtPosition(position);
            cur.moveToPosition(position);
            z = cur.getString(cur.getColumnIndex("name"));

            AlertDialog diaBox = AskOption();
            diaBox.show();

            return true;
        }

    };


    private AlertDialog AskOption() {
        AlertDialog myQuittingDialogBox =new AlertDialog.Builder(this)
                //set message, title, and icon
                .setTitle("Delete")
                .setMessage("Do you want to delete?")
                .setIcon(R.drawable.ic_delete)

                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        dbHelper.deleteProfile(z);
                        Cursor cursor = dbHelper.getPlayers();
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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.



        switch (item.getItemId()) {
            case R.id.action_play:
                // Play action
                Intent i = new Intent(ProfilesActivity.this, GameModeActivity.class);
                startActivity(i);
                return true;
            case R.id.info:
                // Asobi presentation activity
                Intent j = new Intent(ProfilesActivity.this, AboutActivity.class);
                startActivity(j);
                return true;
           case R.id.profile:
                // Create profile activity
                Intent k = new Intent(ProfilesActivity.this, ProfilesActivity.class);
                startActivity(k);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void addProfile(View view) {
        textView = (TextView) findViewById(R.id.enter_name);
        String name = textView.getText().toString();
        dbHelper.addProfile(name);
        Cursor cursor = dbHelper.getPlayers();
        adapter.changeCursor(cursor);
    }

}
