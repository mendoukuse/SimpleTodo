package com.codepath.simpletodo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import static com.codepath.simpletodo.R.id.etNewItem;

public class EditItemActivity extends AppCompatActivity {
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        String item = getIntent().getStringExtra("item");
        position = getIntent().getIntExtra("position", 0);
        EditText editTodoItem = (EditText) findViewById(R.id.etItem);
        editTodoItem.setText(item);
        editTodoItem.requestFocus(View.FOCUS_RIGHT);
    }

    public void onSaveItem(View v) {
        EditText etItem = (EditText) findViewById(R.id.etItem);
        String itemText = etItem.getText().toString();
        Intent data = new Intent();
        data.putExtra("position", position);
        data.putExtra("item", itemText);
        setResult(RESULT_OK, data);
        finish();
    }
}
