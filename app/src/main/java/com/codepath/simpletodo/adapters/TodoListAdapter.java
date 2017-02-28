package com.codepath.simpletodo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.codepath.simpletodo.R;
import com.codepath.simpletodo.models.TodoItem;

import java.util.List;

/**
 * Created by christine_nguyen on 2/27/17.
 */

public class TodoListAdapter extends ArrayAdapter<TodoItem> {
    public TodoListAdapter(Context context, List<TodoItem> items) {
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get data for item at position
        TodoItem item = getItem(position);
        // check for existing view being reused
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_todo, parent, false);
        }
        // Lookup view for data population
        TextView title = (TextView) convertView.findViewById(R.id.itemTitle);
        // Populate the data into the template view using the data object
        title.setText(item.getTitle());

        // Return the completed view to render on screen
        return convertView;
    }
}
