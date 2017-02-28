package com.codepath.simpletodo.models;

import com.codepath.simpletodo.utils.TodoAppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.Date;

@Table(database = TodoAppDatabase.class)
public class TodoItem extends BaseModel {
    @Column
    @PrimaryKey
    int id;

    @Column
    String title;

    @Column
    String description;

    @Column
    Date dueDate;

    @Column
    Priority priority;

    @Column
    Status status;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return title;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }
    public Date getDueDate() {
        return dueDate;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }
    public Priority getPriority() { return priority; }

    public void setStatus(Status status) {
        this.status = status;
    }
    public Status getStatus() { return status; }
}
