package com.maryann.tasktracker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskManagerTest {

    @TempDir
    Path tempDir;

    private TaskManager manager;
    private Path storageFile;

    @BeforeEach
    void setUp() {
        storageFile = tempDir.resolve("tasks.csv");
        manager = new TaskManager(storageFile);
    }

    @Test
    void addTaskAssignsIncrementingIds() {
        Task first = manager.addTask("Write README");
        Task second = manager.addTask("Add tests");

        assertEquals(1, first.getId());
        assertEquals(2, second.getId());
        assertEquals(2, manager.listTasks().size());
    }

    @Test
    void addTaskRejectsBlankDescription() {
        assertThrows(IllegalArgumentException.class, () -> manager.addTask("   "));
    }

    @Test
    void markDoneUpdatesStatus() {
        Task task = manager.addTask("Ship v1");
        assertTrue(manager.markDone(task.getId()));
        assertTrue(manager.findById(task.getId()).orElseThrow().isDone());
    }

    @Test
    void markDoneReturnsFalseForUnknownId() {
        assertFalse(manager.markDone(999));
    }

    @Test
    void deleteTaskRemovesIt() {
        Task task = manager.addTask("Temporary task");
        assertTrue(manager.deleteTask(task.getId()));
        assertTrue(manager.listTasks().isEmpty());
    }

    @Test
    void tasksPersistAcrossManagerInstances() {
        manager.addTask("Persisted task");

        TaskManager reloaded = new TaskManager(storageFile);
        List<Task> tasks = reloaded.listTasks();

        assertEquals(1, tasks.size());
        assertEquals("Persisted task", tasks.get(0).getDescription());
    }

    @Test
    void descriptionWithCommaSurvivesRoundTrip() {
        manager.addTask("Buy milk, eggs, and bread");

        TaskManager reloaded = new TaskManager(storageFile);
        assertEquals("Buy milk, eggs, and bread", reloaded.listTasks().get(0).getDescription());
    }
}
