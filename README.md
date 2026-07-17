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
