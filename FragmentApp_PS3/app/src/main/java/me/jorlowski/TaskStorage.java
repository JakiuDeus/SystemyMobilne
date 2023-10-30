package me.jorlowski;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TaskStorage {

    private final List<Task> tasks;
    private static final TaskStorage instance = new TaskStorage();

    private TaskStorage() {
        tasks = new ArrayList<>();
        for (int i=1; i<=150; i++) {
            Task task = new Task();
            task.setName("Pilne zadanie numer " + i);
            task.setDone(i%3 == 0);
            tasks.add(task);
        }
    }

    public static TaskStorage getInstance() {
        return instance;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public Task getTask(UUID taskId) {
        for (Task task : tasks) {
            if (task.getId().equals(taskId))
                return task;
        }
        return null;
    }
}
