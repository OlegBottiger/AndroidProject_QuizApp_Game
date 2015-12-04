package com.example.iths.asobi;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by iths on 2015-12-03.
 */

/*public class ProfileListAdapter extends BaseAdapter {
    private DBHelper dbHelper;
    private Context context;

    public ProfileListAdapter(DBHelper dbHelper, Context context) {
        this.dbHelper = dbHelper;
        this.context = context;
    }

    @Override
    public int getCount() {
        return dbHelper.size();
    }

    @Override
    public Object getItem(int pos) {
        return dbHelper.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return dbHelper.get(pos).getId();
        //just return 0 if your list items do not have an Id variable.
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.profile_list_item, null);
        }

        //Handle TextView and display string from your list
        TextView listItemText = (TextView)view.findViewById(R.id.profile_name);
        listItemText.setText(dbHelper.get(position));

        //Handle buttons and add onClickListeners
        Button deleteBtn = (Button)view.findViewById(R.id.delete_button);

        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //do something
                dbHelper.deleteProfile(); //or some other task
                notifyDataSetChanged();
            }
        });

        return view;
    }
}*/