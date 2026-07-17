package com.maryann.tasktracker;

import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

/**
 * Simple command-line interface for the task tracker.
 */
public class Main {

    public static void main(String[] args) {
        Path storageFile = Path.of("tasks.csv");
        TaskManager manager = new TaskManager(storageFile);
        Scanner scanner = new Scanner(System.in);

        printMenu();
        boolean running = true;
        while (running) {
            System.out.print("\n> ");
            String input = scanner.nextLine().trim();

            switch (input) {
                case "1" -> handleAdd(scanner, manager);
                case "2" -> handleList(manager);
                case "3" -> handleComplete(scanner, manager);
                case "4" -> handleDelete(scanner, manager);
                case "5", "exit", "quit" -> running = false;
                default -> System.out.println("Unrecognized option. Try 1-5.");
            }
        }

        System.out.println("Goodbye!");
        scanner.close();
    }

    private static void printMenu() {
        System.out.println("=== Task Tracker ===");
        System.out.println("1. Add task");
        System.out.println("2. List tasks");
        System.out.println("3. Mark task done");
        System.out.println("4. Delete task");
        System.out.println("5. Exit");
    }

    private static void handleAdd(Scanner scanner, TaskManager manager) {
        System.out.print("Description: ");
        String description = scanner.nextLine();
        try {
            Task task = manager.addTask(description);
            System.out.println("Added " + task);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void handleList(TaskManager manager) {
        List<Task> tasks = manager.listTasks();
        if (tasks.isEmpty()) {
            System.out.println("No tasks yet.");
            return;
        }
        tasks.forEach(System.out::println);
    }

    private static void handleComplete(Scanner scanner, TaskManager manager) {
        int id = readId(scanner);
        if (id == -1) return;
        boolean success = manager.markDone(id);
        System.out.println(success ? "Marked #" + id + " done." : "No task with id " + id);
    }

    private static void handleDelete(Scanner scanner, TaskManager manager) {
        int id = readId(scanner);
        if (id == -1) return;
        boolean success = manager.deleteTask(id);
        System.out.println(success ? "Deleted #" + id : "No task with id " + id);
    }

    private static int readId(Scanner scanner) {
        System.out.print("Task id: ");
        String raw = scanner.nextLine().trim();
        try {
            return Integer.parseInt(raw);
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
            return -1;
        }
    }
}
