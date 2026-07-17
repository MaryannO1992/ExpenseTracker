package com.maryann.tasktracker;

/**
 * Represents a single task with an id, description, and completion status.
 */
public class Task {

    private final int id;
    private String description;
    private boolean done;

    public Task(int id, String description, boolean done) {
        this.id = id;
        this.description = description;
        this.done = done;
    }

    public Task(int id, String description) {
        this(id, description, false);
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    /**
     * Serializes this task to a single CSV line: id,done,description
     * Commas in the description are escaped so they don't break parsing.
     */
    public String toCsvLine() {
        String escaped = description.replace(",", "\\,");
        return id + "," + done + "," + escaped;
    }

    /**
     * Parses a CSV line produced by {@link #toCsvLine()} back into a Task.
     */
    public static Task fromCsvLine(String line) {
        String[] parts = line.split("(?<!\\\\),", 3);
        int id = Integer.parseInt(parts[0]);
        boolean done = Boolean.parseBoolean(parts[1]);
        String description = parts[2].replace("\\,", ",");
        return new Task(id, description, done);
    }

    @Override
    public String toString() {
        String status = done ? "[x]" : "[ ]";
        return status + " #" + id + " " + description;
    }
}
