package com.codepath.simpletodo.adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.codepath.simpletodo.R;
import com.codepath.simpletodo.models.Priority;
import com.codepath.simpletodo.models.TodoItem;

import java.text.SimpleDateFormat;
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
        TextView dueDate = (TextView) convertView.findViewById(R.id.itemDueDate);
        TextView priority = (TextView) convertView.findViewById(R.id.itemPriority);

        title.setText(item.getTitle());

        if (item.getPriority() != null) {
            priority.setText(item.getPriority().name());
            switch(item.getPriority()) {
                case HIGH:
                    priority.setTextColor(Color.RED);
                    break;
                case MEDIUM:
                    priority.setTextColor(Color.CYAN);
                    break;
                case LOW:
                default:
                    priority.setTextColor(Color.GREEN);
                    break;
            }
        }

        if (item.getDueDate() != null) {
            dueDate.setText(new SimpleDateFormat("MM/dd/yyyy").format(item.getDueDate()));
            dueDate.setTextSize(10);
        }

        // Return the completed view to render on screen
        return convertView;
    }
}
