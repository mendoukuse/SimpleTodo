package com.codepath.simpletodo.activities;

import android.app.DialogFragment;
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
import com.codepath.simpletodo.fragments.DatePickerFragment;
import com.codepath.simpletodo.models.Priority;
import com.codepath.simpletodo.models.Status;

import java.text.SimpleDateFormat;
import java.util.Date;


public class EditItemActivity extends AppCompatActivity implements DatePickerFragment.onDateSetListener {
    int position;
    Priority priority;
    Status status;
    SimpleDateFormat sdf;
    TextView dueDateText;
    Date dueDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        sdf = new SimpleDateFormat("MM/dd/yyyy");

        // Extract item fields from intent
        String item = getIntent().getStringExtra("item");
        position = getIntent().getIntExtra("position", 0);
        String description = getIntent().getStringExtra("description");
        dueDate = (Date) getIntent().getExtras().get("dueDate");

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
        dueDateText = (TextView) findViewById(R.id.etItemDueDate);
        if (dueDate != null) {
            dueDateText.setText(sdf.format(dueDate));
        }

        setUpPrioritySpinner();
        setUpStatusSpinner();
    }

    private void setUpPrioritySpinner() {
        Spinner spinner = (Spinner) findViewById(R.id.etItemPriority);

        // Create an ArrayAdapter using the string array and a default spinner layout
        // Specify the layout to use when the list of choices appears
        ArrayAdapter<Priority> adapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, Priority.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        setPrioritySpinnerOnItemSelected(spinner);

        // Set item priority
        if (getIntent().getExtras().get("priority") != null) {
            priority = (Priority) getIntent().getExtras().get("priority");
            spinner.setSelection(adapter.getPosition(priority));
        }
    }

    private void setUpStatusSpinner() {
        Spinner spinner = (Spinner) findViewById(R.id.etItemStatus);
        ArrayAdapter<String> adapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, Status.getNames());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        setStatusSpinnerOnItemSelected(spinner);

        // Set item priority
        if (getIntent().getExtras().get("status") != null) {
            status = (Status) getIntent().getExtras().get("status");
            spinner.setSelection(adapter.getPosition(status.getName()));
        }
    }

    private void setPrioritySpinnerOnItemSelected(Spinner spinner) {
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

    private void setStatusSpinnerOnItemSelected(Spinner spinner) {
        spinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent,
                                               View view, int position, long id) {
                        status = Status.getFromName((String) parent.getItemAtPosition(position));
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
        data.putExtra("priority", priority);
        data.putExtra("dueDate", dueDate);
        data.putExtra("status", status);
        setResult(RESULT_OK, data);
        finish();
    }

    public void showCalendarDialogFragment(View v) {
        DialogFragment newFragment = DatePickerFragment.newInstance(dueDate);
        newFragment.setCancelable(true);
        newFragment.show(getFragmentManager(), "datePicker");
    }

    @Override
    public void onDateSet(int year, int month, int day) {
        dueDate = new Date(year, month, day);
        dueDateText.setText(sdf.format(dueDate));
    }
}
