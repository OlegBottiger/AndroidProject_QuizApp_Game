package com.example.iths.asobi;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;

import java.util.ArrayList;

/**
 * Created by iths on 2015-12-10.
 */
public class CustomCursorAdapter extends CursorAdapter {

    DBHelper db;
    private final LayoutInflater customInflater;


    public CustomCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        customInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return null;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        db.getWritableDatabase();
        String name = db.getName();
        ArrayList<Integer> score = db.getHighScore("allCategories");

        int currentHigh = 0;

        for(int i = 0; i < score.size(); i++) {
            db.getRank(score, score.get(i));
        }


    }
}
