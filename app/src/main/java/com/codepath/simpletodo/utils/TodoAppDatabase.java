package com.codepath.simpletodo.utils;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by christine_nguyen on 2/27/17.
 */
@Database(name = TodoAppDatabase.NAME, version = TodoAppDatabase.VERSION)
public class TodoAppDatabase {
    public static final String NAME = "todo_database";
    public static final int VERSION = 1;
}
