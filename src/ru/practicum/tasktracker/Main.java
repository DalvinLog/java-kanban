package ru.practicum.tasktracker;

import ru.practicum.tasktracker.manager.TaskManager;
import ru.practicum.tasktracker.model.*;

public class Main {
    static TaskManager taskManager;

    public static void main(String[] args) {
        taskManager = new TaskManager();
        Task task1 = new Task("Помыть посуду", "Не забыть про любимую кружку");
        task1.setStatus(IssueStatuses.NEW);
        Task task2 = new Task("Убраться в комнате");
        task2.setStatus(IssueStatuses.NEW);
        int task1Id = taskManager.createTask(task1);
        int task2Id = taskManager.createTask(task2);

        Epic epic1 = new Epic("Сходить в магазин");
        int epic1Id = taskManager.createEpic(epic1);

        Subtask subtask1 = new Subtask("Купить макароны", epic1Id);
        subtask1.setStatus(IssueStatuses.NEW);
        int subtask1Id = taskManager.createSubtask(subtask1);

        Subtask subtask2 = new Subtask("Купить сыр", "Маздам", epic1Id);
        subtask2.setStatus(IssueStatuses.NEW);
        int subtask2Id = taskManager.createSubtask(subtask2);

        Epic epic2 = new Epic("Приготовить ужин", "Вкусный!");
        int epic2Id = taskManager.createEpic(epic2);

        Subtask subtask3 = new Subtask("Приготовить макароны с сыром", epic2Id);
        subtask3.setStatus(IssueStatuses.NEW);
        int subtask3Id = taskManager.createSubtask(subtask3);

        printAllTasks();

        task1 = taskManager.getTask(task1Id);
        task2 = taskManager.getTask(task2Id);
        task1.setStatus(IssueStatuses.DONE);
        task2.setStatus(IssueStatuses.IN_PROGRESS);
        taskManager.updateTask(task1);
        taskManager.updateTask(task2);

        subtask1 = taskManager.getSubtask(subtask1Id);
        subtask2 = taskManager.getSubtask(subtask2Id);
        subtask3 = taskManager.getSubtask(subtask3Id);
        subtask1.setStatus(IssueStatuses.DONE);
        subtask2.setStatus(IssueStatuses.IN_PROGRESS);
        subtask3.setStatus(IssueStatuses.DONE);

        taskManager.updateSubtask(subtask1);
        taskManager.updateSubtask(subtask2);
        taskManager.updateSubtask(subtask3);

        printAllTasks();

        taskManager.deleteTask(task1Id);
        taskManager.deleteEpic(epic1Id);

        printAllTasks();
    }

    static void printAllTasks() {
        System.out.println("-".repeat(20));
        System.out.println(taskManager.getAllTasks());
        System.out.println(taskManager.getAllEpics());
        System.out.println(taskManager.getAllSubtasks());
    }
}
