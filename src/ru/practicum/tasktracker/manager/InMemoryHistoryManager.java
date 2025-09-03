package ru.practicum.tasktracker.manager;

import ru.practicum.tasktracker.model.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private static final int LIMIT = 10;
    private final List<Task> taskList;

    public InMemoryHistoryManager() {
        taskList = new ArrayList<>();
    }

    @Override
    public void add(Task task){
        if (taskList.size() == LIMIT) {
            taskList.removeFirst();
        }
        taskList.add(task);
    }

    @Override
    public void remove(int id) {
        taskList.remove(id);
    }

    @Override
    public void clear() {
        taskList.clear();
    }

    @Override
    public List<Task> getHistory(){
        return taskList;
    }
}
