package com.example.iths.asobi;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by iths on 2015-12-03.
 */

public class ProfileListAdapter extends CursorAdapter {

    private Button deleteBtn;

    public ProfileListAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // Get "inflater" from context
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // Inflate list item XML
        View v = inflater.inflate(R.layout.profile_list_item, null);
        return v;
    }

    public void bindView(View view, Context context, Cursor cursor) {
        TextView nameText = (TextView)view.findViewById(R.id.profile_name);
        TextView scoreText = (TextView)view.findViewById(R.id.score);
        deleteBtn = (Button)view.findViewById(R.id.delete_button);
        String name = cursor.getString(cursor.getColumnIndex("Name"));
        String score = cursor.getString(cursor.getColumnIndex("Score"));
        nameText.setText(name);
        scoreText.setText(score);

        int pos = cursor.getPosition();

        //Deletes profile.
        deleteBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                dbHelper.deleteProfile(id);
                notifyDataSetChanged();
            }
        });
    }

    // Log in to account when pressed.
    public void onListItemClick(ListView parent, View v, int position, long id) {

        Game.player = DBHelper.
    }


}
