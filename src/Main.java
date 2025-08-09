public class Main {

    public static void main(String[] args) {
        Task task1 = new Task("Помыть посуду", "Не забыть про любимую кружку");
        Task task2 = new Task("Убраться в комнате");
        int task1Id = TaskManager.createTask(task1);
        int task2Id = TaskManager.createTask(task2);

        Epic epic1 = new Epic("Сходить в магазин");
        int epic1Id = TaskManager.createEpic(epic1);

        Subtask subtask1 = new Subtask("Купить макароны", epic1Id);
        int subtask1Id = TaskManager.createSubtask(subtask1);

        Subtask subtask2 = new Subtask("Купить сыр", "Маздам", epic1Id);
        int subtask2Id = TaskManager.createSubtask(subtask2);

        Epic epic2 = new Epic("Приготовить ужин", "Вкусный!");
        int epic2Id = TaskManager.createEpic(epic2);

        Subtask subtask3 = new Subtask("Приготовить макароны с сыром", epic2Id);
        int subtask3Id = TaskManager.createSubtask(subtask3);

        printAllTasks();

        task1 = TaskManager.getTask(task1Id);
        task2 = TaskManager.getTask(task2Id);
        task1.setStatus(IssueStatuses.DONE);
        task2.setStatus(IssueStatuses.IN_PROGRESS);
        TaskManager.updateTask(task1);
        TaskManager.updateTask(task2);

        subtask1 =TaskManager.getSubtask(subtask1Id);
        subtask2 =TaskManager.getSubtask(subtask2Id);
        subtask3 =TaskManager.getSubtask(subtask3Id);
        subtask1.setStatus(IssueStatuses.DONE);
        subtask2.setStatus(IssueStatuses.IN_PROGRESS);
        subtask3.setStatus(IssueStatuses.DONE);

        TaskManager.updateSubtask(subtask1);
        TaskManager.updateSubtask(subtask2);
        TaskManager.updateSubtask(subtask3);

        printAllTasks();

        TaskManager.deleteTask(task1Id);
        TaskManager.deleteEpic(epic1Id);

        printAllTasks();
    }

    static void printAllTasks() {
        System.out.println("-".repeat(20));
        System.out.println(TaskManager.getAllTasks());
        System.out.println(TaskManager.getAllEpics());
        System.out.println(TaskManager.getAllSubtasks());
    }
}
