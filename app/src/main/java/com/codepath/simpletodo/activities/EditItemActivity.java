package com.codepath.simpletodo.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.codepath.simpletodo.R;
import com.codepath.simpletodo.models.Priority;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;


public class EditItemActivity extends AppCompatActivity {
    int position;
    Priority priority;
    SimpleDateFormat sdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        sdf = new SimpleDateFormat("MM/dd/yyyy");

        // Extract item fields from intent
        String item = getIntent().getStringExtra("item");
        position = getIntent().getIntExtra("position", 0);
        String description = getIntent().getStringExtra("description");
        Date date = (Date) getIntent().getExtras().get("dueDate");

        // Set item title content
        EditText editTodoItem = (EditText) findViewById(R.id.etItem);
        editTodoItem.setText(item);
        editTodoItem.requestFocus(View.FOCUS_RIGHT);

        // Set item description
        EditText editTodoDescription = (EditText) findViewById(R.id.etItemDescription);
        if (description != null || description.length() > 0) {
            editTodoDescription.setText(description);
        }
        // Set item due date
        TextView dueDate = (TextView) findViewById(R.id.etItemDueDate);
        if (date != null) {
            dueDate.setText(sdf.format(date));
        }

        Spinner spinner = (Spinner) findViewById(R.id.etItemPriority);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<Priority> adapter = new ArrayAdapter(this,
            android.R.layout.simple_spinner_item, Priority.values());
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        setSpinnerOnItemSelected(spinner);

        // Set item priority
        if (getIntent().getStringExtra("priority") != null) {
            priority = Priority.valueOf(getIntent().getStringExtra("priority"));
            spinner.setSelection(adapter.getPosition(priority));
        }

    }

    private void setSpinnerOnItemSelected(Spinner spinner) {
        spinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent,
                                               View view, int position, long id) {
                        priority = (Priority) parent.getItemAtPosition(position);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                }
        );
    }

    public void onSaveItem(View v) {
        EditText etItem = (EditText) findViewById(R.id.etItem);
        String itemText = etItem.getText().toString();

        EditText etItemDescription = (EditText) findViewById(R.id.etItemDescription);
        String itemDescription = etItemDescription.getText().toString();

        Intent data = new Intent();
        data.putExtra("position", position);
        data.putExtra("item", itemText);
        data.putExtra("description", itemDescription);
        data.putExtra("priority", priority.name());
        setResult(RESULT_OK, data);
        finish();
    }
}
