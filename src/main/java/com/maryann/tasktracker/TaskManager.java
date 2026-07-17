package com.maryann.tasktracker;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Holds the in-memory list of tasks and handles loading/saving them
 * to a CSV file on disk.
 */
public class TaskManager {

    private final Path storageFile;
    private final List<Task> tasks = new ArrayList<>();
    private int nextId = 1;

    public TaskManager(Path storageFile) {
        this.storageFile = storageFile;
        load();
    }

    public Task addTask(String description) {
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Description cannot be empty");
        }
        Task task = new Task(nextId++, description.trim());
        tasks.add(task);
        save();
        return task;
    }

    public List<Task> listTasks() {
        return List.copyOf(tasks);
    }

    public Optional<Task> findById(int id) {
        return tasks.stream().filter(t -> t.getId() == id).findFirst();
    }

    public boolean markDone(int id) {
        Optional<Task> task = findById(id);
        task.ifPresent(t -> t.setDone(true));
        if (task.isPresent()) {
            save();
            return true;
        }
        return false;
    }

    public boolean deleteTask(int id) {
        boolean removed = tasks.removeIf(t -> t.getId() == id);
        if (removed) {
            save();
        }
        return removed;
    }

    private void load() {
        if (!Files.exists(storageFile)) {
            return;
        }
        try {
            List<String> lines = Files.readAllLines(storageFile, StandardCharsets.UTF_8);
            for (String line : lines) {
                if (line.isBlank()) {
                    continue;
                }
                Task task = Task.fromCsvLine(line);
                tasks.add(task);
                nextId = Math.max(nextId, task.getId() + 1);
            }
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to load tasks from " + storageFile, e);
        }
    }

    private void save() {
        try {
            if (storageFile.getParent() != null) {
                Files.createDirectories(storageFile.getParent());
            }
            List<String> lines = tasks.stream().map(Task::toCsvLine).toList();
            Files.write(storageFile, lines, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to save tasks to " + storageFile, e);
        }
    }
}
