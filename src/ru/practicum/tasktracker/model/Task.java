package ru.practicum.tasktracker.model;

import java.util.Objects;

public class Task {
    private String name;
    private String description;
    private int id;
    private IssueStatuses status;

    public Task (String name) {
        this.name = name;
        description = "";
    }

    public Task (String name, String description) {
        this.name = name;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public IssueStatuses getStatus() {
        return status;
    }

    public void setStatus(IssueStatuses status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Task cloneTask() {
        Task task = new Task(name, description);
        task.setStatus(status);
        task.setId(id);
        return task;
    }

    @Override
    public String toString() {
        return "Task{name=" + name
                + ", description=" + description
                + ", status=" + status
                + ", id=" + id + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id;
    }
}
