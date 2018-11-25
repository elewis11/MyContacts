package com.example.erika.mycontacts;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class GroupLists extends CursorAdapter {

    public GroupLists(Context context, Cursor cursor, int flags){
        super(context, cursor, flags);
    }

    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.li_group_list, viewGroup, false);
    }

    public void bindView(View view, Context context, Cursor cursor) {
        ((TextView) view.findViewById(R.id.groupNameTextView)).
                setText(cursor.getString(cursor.getColumnIndex("groupName")));
    }

}
