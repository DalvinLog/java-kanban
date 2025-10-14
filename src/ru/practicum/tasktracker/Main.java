package ru.practicum.tasktracker;

import ru.practicum.tasktracker.manager.*;
import ru.practicum.tasktracker.model.*;
import ru.practicum.tasktracker.util.Managers;

public class Main {
    static TaskManager taskManager;

    public static void main(String[] args) {

        taskManager = Managers.getDefault();

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

        Subtask subtask3 = new Subtask("Купить мясо", epic1Id);
        subtask3.setStatus(IssueStatuses.NEW);
        int subtask3Id = taskManager.createSubtask(subtask3);

        Epic epic2 = new Epic("Приготовить ужин", "Вкусный!");
        int epic2Id = taskManager.createEpic(epic2);

        taskManager.getTask(task1Id);
        printHistory();
        taskManager.getTask(task2Id);
        printHistory();
        taskManager.getTask(task1Id);
        printHistory();

        taskManager.getEpic(epic1Id);
        printHistory();
        taskManager.getSubtask(subtask1Id);
        printHistory();
        taskManager.getSubtask(subtask2Id);
        printHistory();


        taskManager.deleteTask(task2Id);
        printHistory();

        taskManager.deleteEpic(epic1Id);
        printHistory();
    }

    static void printAllTasks() {

        System.out.println("Задачи:");
        for (Task task : taskManager.getAllTasks()) {
            System.out.println(task);
        }
        System.out.println("Эпики:");
        for (Task epic : taskManager.getAllEpics()) {
            System.out.println(epic);

            System.out.println("Подзадачи:");
            for (Task subTask : taskManager.getSubtasksOfEpic(epic.getId())) {
                System.out.println("--> " + subTask);
            }
        }

        printHistory();
    }

    static void printHistory() {
        System.out.println("История:");
        for (Task task : taskManager.getHistory()) {
            System.out.println(task);
        }

        System.out.println("-".repeat(20));
    }
}