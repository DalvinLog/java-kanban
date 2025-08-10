package ru.practicum.tasktracker.manager;

import ru.practicum.tasktracker.model.*;
import java.util.HashMap;
import java.util.ArrayList;

public class TaskManager {
    private int id = 0;
    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();
    private final HashMap<Integer, Subtask> subtasks = new HashMap<>();

    public int createTask(Task task) {
        id += 1;
        task.setId(id);
        tasks.put(id, task);
        return id;
    }

    public int createEpic(Epic epic) {
        id += 1;
        epic.setId(id);
        epics.put(id, epic);
        updateEpicStatus(epic);
        return id;
    }

    //подзадача передаётся уже с EpicId к которому она относится
    public int createSubtask(Subtask subtask) {
        int epicId = subtask.getEpicId();
        if (!epics.containsKey(epicId)) return 0;

        id += 1;
        subtask.setId(id);
        subtasks.put(id, subtask);

        Epic epic = epics.get(epicId);
        epic.addSubtaskId(id);
        updateEpicStatus(epic);
        return id;
    }

    //задача передаётся уже с нужным статусом
    public void updateTask(Task task) {
        int taskId = task.getId();
        if (!tasks.containsKey(taskId)) return;

        tasks.put(taskId, task);
    }

    //статус и список подзадач не передаётся так, как они обновляются при изменении (добавлении) подзадач
    public void updateEpic(Epic epic) {
        int epicId = epic.getId();
        if (!epics.containsKey(epicId)) return;

        epics.put(epicId, epic);
        updateEpicStatus(epic);
    }

    public void updateSubtask(Subtask subtask) {
        int subtaskId = subtask.getId();
        if (!subtasks.containsKey(subtaskId)) return;

        subtasks.put(subtaskId, subtask);

        int epicId = subtask.getEpicId();
        Epic epic = epics.get(epicId);
        updateEpicStatus(epic);
    }

    public void deleteTask(int taskId) {
        if(!tasks.containsKey(taskId)) return;

        tasks.remove(taskId);
    }

    public void deleteEpic(int epicId) {
        if (!epics.containsKey(epicId)) return;

        Epic epic = epics.get(epicId);
        ArrayList<Integer> subtasksId = epic.getSubtasksId();
        for (int subtaskId: subtasksId) {
            subtasks.remove(subtaskId);
        }
        epics.remove(epicId);
    }

    public void deleteSubtask(int subtaskId) {
        if (!subtasks.containsKey(subtaskId)) return;

        Subtask subtask = subtasks.get(subtaskId);
        int epicId = subtask.getEpicId();
        Epic epic = epics.get(epicId);
        subtasks.remove(subtaskId);
        epic.deleteSubtaskId(subtaskId);
        updateEpicStatus(epic);
    }

    public void updateEpicStatus(Epic epic) {
        ArrayList<IssueStatuses> statuses = new ArrayList<>();
        ArrayList<Integer> subtasksId = epic.getSubtasksId();

        if (subtasksId == null) {
            epic.setStatus(IssueStatuses.NEW);
            return;
        }

        for (int id: subtasksId) {
            Subtask subtask = subtasks.get(id);
            statuses.add(subtask.getStatus());
        }

        if (statuses.contains(IssueStatuses.NEW) && !statuses.contains(IssueStatuses.DONE)
                && !statuses.contains(IssueStatuses.IN_PROGRESS)) {
            epic.setStatus(IssueStatuses.NEW);
        } else if (statuses.contains(IssueStatuses.DONE) && !statuses.contains(IssueStatuses.NEW)
                && !statuses.contains(IssueStatuses.IN_PROGRESS)) {
            epic.setStatus(IssueStatuses.DONE);
        } else {
            epic.setStatus(IssueStatuses.IN_PROGRESS);
        }
    }

    public void deleteAllTasks() {
        tasks.clear();
    }

    public void deleteAllEpics() {
        subtasks.clear();
        epics.clear();
    }

    public void deleteAllSubtasks() {
        subtasks.clear();
        for (Epic epic: epics.values()) {
            epic.setStatus(IssueStatuses.NEW);
        }
    }

    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    public ArrayList<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    public ArrayList<Subtask> getAllSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    public ArrayList<Subtask> getSubtasksOfEpic(int epicId) {
        ArrayList<Subtask> subtasksList = new ArrayList<>();
        Epic epic = epics.get(epicId);
        ArrayList<Integer> subtasksId = epic.getSubtasksId();

        for (int id: subtasksId) {
            subtasksList.add(subtasks.get(id));
        }

        return subtasksList;
    }

    public Task getTask(int taskId) {
        return tasks.get(taskId);
    }

    public Epic getEpic(int epicId) {
        return epics.get(epicId);
    }

    public Subtask getSubtask(int subtaskId) {
        return subtasks.get(subtaskId);
    }
}
