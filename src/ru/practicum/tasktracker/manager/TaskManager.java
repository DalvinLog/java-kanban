package ru.practicum.tasktracker.manager;

import ru.practicum.tasktracker.model.Epic;
import ru.practicum.tasktracker.model.Subtask;
import ru.practicum.tasktracker.model.Task;

import java.util.List;

public interface TaskManager {
    int createTask(Task task);

    int createEpic(Epic epic);

    int createSubtask(Subtask subtask);

    boolean updateTask(Task task);

    boolean updateEpic(Epic epic);

    boolean updateSubtask(Subtask subtask);

    void deleteTask(int taskId);

    void deleteEpic(int epicId);

    void deleteSubtask(int subtaskId);

    void deleteAllTasks();

    void deleteAllEpics();

    void deleteAllSubtasks();

    List<Task> getAllTasks();

    List<Epic> getAllEpics();

    List<Subtask> getAllSubtasks();

    List<Subtask> getSubtasksOfEpic(int epicId);

    Task getTask(int taskId);

    Epic getEpic(int epicId);

    Subtask getSubtask(int subtaskId);

    List<Task> getHistory();
}
