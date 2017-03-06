package com.codepath.simpletodo.models;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by christine_nguyen on 2/27/17.
 */

public enum Status {
    TO_DO("To do"),
    IN_PROGRESS("In progress"),
    COMPLETED("completed");

    private final String name;

    Status(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static ArrayList<String> getNames() {
        ArrayList l = new ArrayList();

        for (Status s : values()) {
            l.add(s.getName());
        }

        return l;
    }

    public static Status getFromName(String name) {
        Status status = TO_DO;
        for (Status s : values()) {
            if (s.getName() == name) {
                status = s;
                break;
            }
        }
        return status;
    }
}
