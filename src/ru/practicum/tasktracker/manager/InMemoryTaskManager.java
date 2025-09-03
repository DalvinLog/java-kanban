package ru.practicum.tasktracker.manager;

import ru.practicum.tasktracker.model.*;
import ru.practicum.tasktracker.util.Managers;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {
    private int id = 0;
    private final Map<Integer, Task> tasks;
    private final Map<Integer, Epic> epics;
    private final Map<Integer, Subtask> subtasks;
    private final HistoryManager historyManager;

    public InMemoryTaskManager() {
        historyManager = Managers.getDefaultHistory();
        tasks = new HashMap<>();
        epics = new HashMap<>();
        subtasks = new HashMap<>();
    }

    @Override
    public int createTask(Task task) {
        id += 1;
        task.setId(id);
        tasks.put(id, task);
        return id;
    }

    @Override
    public int createEpic(Epic epic) {
        id += 1;
        epic.setId(id);
        epics.put(id, epic);
        return id;
    }

    @Override
    public int createSubtask(Subtask subtask) {
        int epicId = subtask.getEpicId();
        if (!epics.containsKey(epicId)) return -1;

        id += 1;
        subtask.setId(id);
        subtasks.put(id, subtask);

        Epic epic = epics.get(epicId);
        epic.addSubtaskId(id);
        updateEpicStatus(epic);
        return id;
    }

    @Override
    public boolean updateTask(Task task) {
        int taskId = task.getId();
        if (!tasks.containsKey(taskId)) return false;

        tasks.put(taskId, task);
        return true;
    }

    @Override
    public boolean updateEpic(Epic newEpic) {
        int epicId = newEpic.getId();
        if (!epics.containsKey(epicId)) return false;

        Epic Epic = epics.get(epicId);

        Epic.setName(newEpic.getName());
        Epic.setDescription(newEpic.getDescription());

        return true;
    }

    @Override
    public boolean updateSubtask(Subtask newSubtask) {
        int subtaskId = newSubtask.getId();
        if (!subtasks.containsKey(subtaskId)) return false;

        Subtask subtask = subtasks.get(subtaskId);

        subtask.setName(newSubtask.getName());
        subtask.setDescription(newSubtask.getDescription());
        subtask.setStatus(newSubtask.getStatus());

        int epicId = subtask.getEpicId();
        Epic epic = epics.get(epicId);
        updateEpicStatus(epic);
        return true;
    }

    @Override
    public void deleteTask(int taskId) {
        if(!tasks.containsKey(taskId)) return;

        tasks.remove(taskId);
        historyManager.remove(taskId);
    }

    @Override
    public void deleteEpic(int epicId) {
        if (!epics.containsKey(epicId)) return;

        Epic epic = epics.get(epicId);
        List<Integer> subtasksId = epic.getSubtasksId();
        for (int subtaskId: subtasksId) {
            subtasks.remove(subtaskId);
        }
        epics.remove(epicId);
        historyManager.remove(epicId);
    }

    @Override
    public void deleteSubtask(int subtaskId) {
        if (!subtasks.containsKey(subtaskId)) return;

        Subtask subtask = subtasks.get(subtaskId);
        int epicId = subtask.getEpicId();
        Epic epic = epics.get(epicId);
        subtasks.remove(subtaskId);
        historyManager.remove(subtaskId);
        epic.deleteSubtaskId(subtaskId);
        updateEpicStatus(epic);
    }

    public void updateEpicStatus(Epic epic) {
        List<IssueStatuses> statuses = new ArrayList<>();
        List<Integer> subtasksId = epic.getSubtasksId();

        if (subtasksId.isEmpty()) {
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

    @Override
    public void deleteAllTasks() {
        for (int id: tasks.keySet()) {
            historyManager.remove(id);
        }
        tasks.clear();
    }

    @Override
    public void deleteAllEpics() {
        for (int id: epics.keySet()) {
            historyManager.remove(id);
        }
        for (int id: subtasks.keySet()) {
            historyManager.remove(id);
        }
        subtasks.clear();
        epics.clear();
    }

    @Override
    public void deleteAllSubtasks() {
        for (int id: subtasks.keySet()) {
            historyManager.remove(id);
        }
        subtasks.clear();
        for (Epic epic: epics.values()) {
            epic.deleteAllSubtaskId();
            epic.setStatus(IssueStatuses.NEW);
        }
    }

    @Override
    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public List<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public List<Subtask> getAllSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    @Override
    public List<Subtask> getSubtasksOfEpic(int epicId) {
        List<Subtask> subtasksList = new ArrayList<>();
        Epic epic = epics.get(epicId);
        List<Integer> subtasksId = epic.getSubtasksId();

        for (int id: subtasksId) {
            subtasksList.add(subtasks.get(id));
        }

        return subtasksList;
    }

    @Override
    public Task getTask(int taskId) {
        if (!tasks.containsKey(taskId)) return null;

        Task task = tasks.get(taskId);
        historyManager.add(task.cloneTask());
        return task;
    }

    @Override
    public Epic getEpic(int epicId) {
        if (!epics.containsKey(epicId)) return null;

        Epic epic = epics.get(epicId);
        historyManager.add(epic.cloneTask());
        return epic;
    }

    @Override
    public Subtask getSubtask(int subtaskId) {
        if (!subtasks.containsKey(subtaskId)) return null;

        Subtask subtask = subtasks.get(subtaskId);
        historyManager.add(subtask.cloneTask());
        return subtask;
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }
}
