package ru.practicum.tasktracker.model;
import java.util.ArrayList;

public class Epic extends Task {
    private final ArrayList<Integer> subtasksId;

    public Epic (String name) {
        super(name);
        subtasksId = new ArrayList<>();
    }

    public Epic (String name, String description) {
        super(name, description);
        subtasksId = new ArrayList<>();
    }

    //добавить подзадачу
    public void addSubtaskId(int id) {
        subtasksId.add(id);
    }

    public void deleteSubtaskId(int subtaskId) {
        subtasksId.remove(subtaskId);
    }

    //получить список подзадач
    public ArrayList<Integer> getSubtasksId() {
        return subtasksId;
    }

    @Override
    public String toString() {
        return "Epic{name=" + getName()
                + ", description=" + getDescription()
                + ", status=" + getStatus()
                + ", id=" + getId()
                + ", subtasks=" + subtasksId + "}";
    }
}
