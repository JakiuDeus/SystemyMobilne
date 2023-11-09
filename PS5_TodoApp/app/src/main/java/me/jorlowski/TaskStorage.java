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
            if (i%3 == 0) {
                task.setCategory(Category.STUDIES);
            } else {
                task.setCategory(Category.HOME);
            }
            tasks.add(task);
        }
    }

    public static TaskStorage getInstance() {
        return instance;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }
    public Task getTask(UUID taskId) {
        for (Task task : tasks) {
            if (task.getId().equals(taskId))
                return task;
        }
        return null;
    }
}
