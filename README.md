# Task Tracker

A simple command-line task tracker written in Java. Add, list, complete, and delete
tasks; everything is saved to a local `tasks.csv` file so your list persists between runs.

## Requirements

- Java 17+
- Maven 3.6+

## Build

```
mvn compile
```

## Run

```
mvn compile exec:java -Dexec.mainClass="com.maryann.tasktracker.Main"
```

Or build a jar and run it directly:

```
mvn package
java -jar target/task-tracker.jar
```

## Test

```
mvn test
```

## Project structure

```
task-tracker/
  pom.xml
  src/main/java/com/maryann/tasktracker/
    Task.java          # single task: id, description, done flag
    TaskManager.java    # in-memory list + CSV load/save
    Main.java            # CLI menu loop
  src/test/java/com/maryann/tasktracker/
    TaskManagerTest.java
```

## Ideas for your next commits

Once this is running, here are small, self-contained features to add — each makes
a good separate commit/PR:

- Due dates: add a `LocalDate` field to `Task`, sort the list by date.
- Priorities: add a `Priority` enum (LOW/MEDIUM/HIGH), filter/sort by it.
- Categories/tags: let tasks belong to a category, add a `list <category>` command.
- Edit command: update an existing task's description in place.
- Search: filter tasks by keyword in the description.
- Switch storage format from CSV to JSON (a good excuse to add a small JSON library).
- Colorized CLI output (green for done, red for overdue) using ANSI escape codes.
- Undo: keep a small history so the last delete/complete can be reverted.

Each of these is scoped to a single evening and gives you a clean commit history
to show on GitHub.
