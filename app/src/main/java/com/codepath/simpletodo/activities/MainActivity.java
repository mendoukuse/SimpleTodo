package com.codepath.simpletodo.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.codepath.simpletodo.R;
import com.codepath.simpletodo.adapters.TodoListAdapter;
import com.codepath.simpletodo.models.Priority;
import com.codepath.simpletodo.models.Status;
import com.codepath.simpletodo.models.TodoItem;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<TodoItem> items;
    TodoListAdapter itemsAdapter;
    ListView lvItems;
    private final int REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadItems();
        setupListViewListener();
    }

    public void onAddItem(View v) {
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        if (itemText.length() > 0) {
            TodoItem item = new TodoItem();
            item.setId(items.size());
            item.setTitle(itemText);
            item.setDescription(itemText);
            item.setPriority(Priority.LOW);
            item.setStatus(Status.NOT_STARTED);
            item.setDueDate(new Date());

            itemsAdapter.add(item);
            etNewItem.setText("");
            item.save();
        }
        etNewItem.clearFocus();
        hideSoftKeyboard(v);
    }

    public void hideSoftKeyboard(View view){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            String itemText = data.getExtras().getString("item");
            String itemDescription = data.getExtras().getString("description");
            int position = data.getExtras().getInt("position");

            TodoItem updatedItem = items.remove(position);
            updatedItem.setTitle(itemText);
            updatedItem.setDescription(itemDescription);

            if (data.getExtras().getString("priority") != null) {
                updatedItem.setPriority(Priority.valueOf(data.getExtras().getString("priority")));
            }
            if (data.getExtras().get("dueDate") != null) {
                updatedItem.setDueDate((Date) data.getExtras().get("dueDate"));
            }

            items.add(position, updatedItem);
            updatedItem.save();
            itemsAdapter.notifyDataSetChanged();
        }
    }

    private void setupListViewListener() {
        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter,
                                           View item, int pos, long id) {
                TodoItem todoItem = items.remove(pos);
                todoItem.delete();
                itemsAdapter.notifyDataSetChanged();
                return true;
            }
        });

        lvItems.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent,
                                    View view, int position, long id) {
                Intent i = new Intent(MainActivity.this, EditItemActivity.class);
                i.putExtra("position", position);
                i.putExtra("item", items.get(position).getTitle());
                i.putExtra("description", items.get(position).getDescription());
                i.putExtra("priority", items.get(position).getPriority().name());
                i.putExtra("dueDate", items.get(position).getDueDate());
                startActivityForResult(i, REQUEST_CODE);
            }
        });
    }

    private void loadItems() {
        items = SQLite.select().from(TodoItem.class).queryList();
        if (items == null) {
            items = new ArrayList<TodoItem>();
        }
        itemsAdapter = new TodoListAdapter(this, items);
        lvItems = (ListView) findViewById(R.id.lvItems);
        lvItems.setAdapter(itemsAdapter);
    }
}
