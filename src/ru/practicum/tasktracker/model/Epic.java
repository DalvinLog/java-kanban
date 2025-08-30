package ru.practicum.tasktracker.model;
import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private final List<Integer> subtasksId;

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

    public void deleteAllSubtaskId() {
        subtasksId.clear();
    }

    //получить список подзадач
    public List<Integer> getSubtasksId() {
        return subtasksId;
    }

    @Override
    public Task cloneTask(){
        Epic epic = new Epic(getName(), getDescription());
        epic.setStatus(getStatus());
        epic.setId(getId());
        for (int id: subtasksId) {
            epic.addSubtaskId(id);
        }
        return epic;
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
