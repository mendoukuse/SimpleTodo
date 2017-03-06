package com.codepath.simpletodo.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by christine_nguyen on 3/5/17.
 */

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    public DatePickerFragment() {
    }

    public static DatePickerFragment newInstance(Date date) {
        DatePickerFragment frag = new DatePickerFragment();
        if (date != null) {
            Bundle args = new Bundle();
            // some adjustments made for deprecated date functions
            args.putInt("month", date.getMonth());
            args.putInt("day", date.getDate());
            args.putInt("year", date.getYear() + 1900);
            frag.setArguments(args);
        }
        return frag;
    }

    public interface onDateSetListener {
        void onDateSet(int year, int month, int day);
    }



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        if (getArguments() != null) {
            year = getArguments().getInt("year");
            month = getArguments().getInt("month");
            day = getArguments().getInt("day");
        }
        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user
        onDateSetListener listener = (onDateSetListener) getActivity();
        listener.onDateSet(year - 1900, month, day);
    }
}