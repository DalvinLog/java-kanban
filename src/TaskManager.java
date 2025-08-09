import java.util.HashMap;
import java.util.ArrayList;

public class TaskManager {
    static final IssueStatuses NEW = IssueStatuses.NEW;
    static final IssueStatuses IN_PROGRESS = IssueStatuses.IN_PROGRESS;
    static final IssueStatuses DONE = IssueStatuses.DONE;
    static private int id = 0;
    static private final HashMap<Integer, Task> tasks = new HashMap<>();
    static private final HashMap<Integer, Epic> epics = new HashMap<>();
    static private final HashMap<Integer, Subtask> subtasks = new HashMap<>();

    static int createTask(Task task) {
        id += 1;
        task.setId(id);
        task.setStatus(NEW);
        tasks.put(id, task);
        return id;
    }

    static int createEpic(Epic epic) {
        id += 1;
        epic.setId(id);
        epic.setStatus(NEW);
        epics.put(id, epic);
        return id;
    }

    //подзадача передаётся уже с EpicId к которому она относится
    static int createSubtask(Subtask subtask) {
        int epicId = subtask.getEpicId();
        if (!epics.containsKey(epicId)) return 0;

        id += 1;
        subtask.setId(id);
        subtask.setStatus(NEW);
        subtasks.put(id, subtask);

        Epic epic = epics.get(epicId);
        epic.addSubtaskId(id);
        updateEpicStatus(epic);
        return id;
    }

    //задача передаётся уже с нужным статусом
    static void updateTask(Task task) {
        int taskId = task.getId();
        if (!tasks.containsKey(taskId)) return;

        tasks.put(taskId, task);
    }

    //статус и список подзадач не передаётся так, как они обновляются при изменении (добавлении) подзадач
    static void updateEpic(Epic epic) {
        int epicId = epic.getId();
        if (!epics.containsKey(epicId)) return;

        epics.put(epicId, epic);
        updateEpicStatus(epic);
    }

    static void updateSubtask(Subtask subtask) {
        int subtaskId = subtask.getId();
        if (!subtasks.containsKey(subtaskId)) return;

        int epicId = subtask.getEpicId();
        Epic epic = epics.get(epicId);
        updateEpicStatus(epic);

        subtasks.put(subtaskId, subtask);
    }

    static void deleteTask(int taskId) {
        if(!tasks.containsKey(taskId)) return;

        tasks.remove(taskId);
    }

    static void deleteEpic(int epicId) {
        if (!epics.containsKey(epicId)) return;

        Epic epic = epics.get(epicId);
        ArrayList<Integer> subtasksId = epic.getSubtasksId();
        for (int subtaskId: subtasksId) {
            subtasks.remove(subtaskId);
        }
        epics.remove(epicId);
    }

    static void deleteSubtask(int subtaskId) {
        if (!subtasks.containsKey(subtaskId)) return;

        Subtask subtask = subtasks.get(subtaskId);
        int epicId = subtask.getEpicId();
        Epic epic = epics.get(epicId);
        subtasks.remove(subtaskId);
        updateEpicStatus(epic);
    }

    static void updateEpicStatus(Epic epic) {
        ArrayList<IssueStatuses> statuses = new ArrayList<>();
        ArrayList<Integer> subtasksId = epic.getSubtasksId();

        if (subtasksId == null) {
            epic.setStatus(NEW);
            return;
        }

        for (int id: subtasksId) {
            Subtask subtask = subtasks.get(id);
            statuses.add(subtask.getStatus());
        }

        if (statuses.contains(NEW) && !statuses.contains(DONE) && !statuses.contains(IN_PROGRESS)) {
            epic.setStatus(NEW);
        } else if (statuses.contains(DONE) && !statuses.contains(NEW) && !statuses.contains(IN_PROGRESS)) {
            epic.setStatus(DONE);
        } else {
            epic.setStatus(IN_PROGRESS);
        }
    }

    static void deleteAllTasks() {
        tasks.clear();
    }

    static void deleteAllEpics() {
        subtasks.clear();
        epics.clear();
    }

    static void deleteAllSubtasks() {
        subtasks.clear();
        for (Epic epic: epics.values()) {
            epic.setStatus(IssueStatuses.NEW);
        }
    }

    static ArrayList<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    static ArrayList<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    static ArrayList<Subtask> getAllSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    static ArrayList<Subtask> getSubtasksOfEpic(int epicId) {
        ArrayList<Subtask> subtasksList = new ArrayList<>();
        Epic epic = epics.get(epicId);
        ArrayList<Integer> subtasksId = epic.getSubtasksId();

        for (int id: subtasksId) {
            subtasksList.add(subtasks.get(id));
        }

        return subtasksList;
    }

    static Task getTask(int taskId) {
        return tasks.get(taskId);
    }

    static Epic getEpic(int epicId) {
        return epics.get(epicId);
    }

    static Subtask getSubtask(int subtaskId) {
        return subtasks.get(subtaskId);
    }
}
