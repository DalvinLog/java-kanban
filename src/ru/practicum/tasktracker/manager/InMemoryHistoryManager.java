package ru.practicum.tasktracker.manager;

import ru.practicum.tasktracker.model.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private static final int LIMIT = 10;
    List<Task> taskList;

    public InMemoryHistoryManager() {
        taskList = new ArrayList<>();
    }

    @Override
    public void add(Task task){
        taskList.add(task);
        if (taskList.size() > LIMIT) {
            taskList.removeLast();
        }
    }

    @Override
    public List<Task> getHistory(){
        return taskList;
    }
}
