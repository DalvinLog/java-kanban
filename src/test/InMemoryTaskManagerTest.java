package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import ru.practicum.tasktracker.manager.TaskManager;
import ru.practicum.tasktracker.model.*;
import ru.practicum.tasktracker.util.Managers;

import java.util.List;

class InMemoryTaskManagerTest {
    TaskManager taskManager;

    @BeforeEach
    public void beforeEach() {
        taskManager = Managers.getDefault();
    }

    @Test
    public void theManagersClassCanReturnsInitializedObjects() {
        assertNotNull(taskManager);
        assertNotNull(Managers.getDefaultHistory());
    }

    @Test
    public void theManagersClassCanAddDifferentTypesOfTasksAndFindThemById() {
        Task task = new Task("Выгулять собаку");
        int taskId = taskManager.createTask(task);
        Epic epic = new Epic("Убраться дома");
        int epicId = taskManager.createEpic(epic);
        Subtask subtask = new Subtask("Протереть пыль", epicId);
        int subtaskId = taskManager.createSubtask(subtask);

        assertNotNull(taskManager.getTask(taskId), "taskError");
        assertNotNull(taskManager.getEpic(epicId), "epicError");
        assertNotNull(taskManager.getSubtask(subtaskId), "subtaskError");
    }

    @Test
    public void epicCannotBeAddedToItself(){
        Epic newEpic1 = new Epic("Сходить в магазин");
        taskManager.createEpic(newEpic1);

        Epic newEpic2 = new Epic("Приготовить ужин");
        int Epic2Id = taskManager.createEpic(newEpic2);

        Epic epic1 = new Epic("Сходить в магазин");
        epic1.setId(1);

        epic1.addSubtaskId(Epic2Id);
        taskManager.updateEpic(epic1);

        Epic epic = taskManager.getEpic(epic1.getId());

        assertEquals(0, epic.getSubtasksId().size());
    }

    @Test
    public void subtaskCannotBeAddedToItself(){
        Epic newEpic = new Epic("Сходить в магазин");
        int epicId = taskManager.createEpic(newEpic);

        Subtask newSubtask = new Subtask("купить сыр", epicId);
        int subtaskId = taskManager.createSubtask(newSubtask);

        Subtask subtask = taskManager.getSubtask(subtaskId);

        Subtask newSubtask2 = new Subtask("купить молоко", subtask.getId());

        assertEquals(-1, taskManager.createSubtask(newSubtask2));
    }

    @Test
    public void itIsNotPossibleToSetTheIdForTasksInAdvance(){
        Task newTask = new Task("Выкинуть мусор");
        newTask.setId(5);
        int taskId = taskManager.createTask(newTask);

        Epic newEpic = new Epic("Подготовиться к экзамену");
        newEpic.setId(9);
        int epicId = taskManager.createEpic(newEpic);

        Subtask newSubtask = new Subtask("Повторить материаловедение", epicId);
        newSubtask.setId(167);
        int subtaskId = taskManager.createSubtask(newSubtask);

        Task task = taskManager.getTask(taskId);
        Epic epic = taskManager.getEpic(epicId);
        Subtask subtask = taskManager.getSubtask(subtaskId);

        assertEquals(1, task.getId());
        assertEquals(2, epic.getId());
        assertEquals(3, subtask.getId());
    }

    @Test
    public void theTaskRemainsUnchangedAfterBeingAddedToTheManager(){
        Task newTask = new Task("Съездить на собеседование", "На такси");
        newTask.setStatus(IssueStatuses.IN_PROGRESS);
        int taskId = taskManager.createTask(newTask);

        Task task = taskManager.getTask(taskId);

        assertEquals("Съездить на собеседование", task.getName(), "nameError");
        assertEquals("На такси", task.getDescription(), "descriptionError");
        assertEquals(IssueStatuses.IN_PROGRESS, task.getStatus(), "statusError");
    }

    @Test
    public void theEpicRemainsUnchangedAfterBeingAddedToTheManager(){
        Epic newEpic = new Epic("Подготовиться к экзамену", "До завтра");
        newEpic.setStatus(IssueStatuses.NEW);
        int epicId = taskManager.createEpic(newEpic);

        Epic epic = taskManager.getEpic(epicId);

        assertEquals("Подготовиться к экзамену", epic.getName(), "nameError");
        assertEquals("До завтра", epic.getDescription(), "descriptionError");
        assertEquals(IssueStatuses.NEW, epic.getStatus(), "statusError");
    }

    @Test
    public void theSubtaskRemainsUnchangedAfterBeingAddedToTheManager(){
        Epic newEpic = new Epic("Подготовиться к экзамену");
        int epicId = taskManager.createEpic(newEpic);

        Subtask newSubtask = new Subtask("Повторить материаловедение", "Раздел 3", epicId);
        newSubtask.setStatus(IssueStatuses.DONE);
        int subtaskId = taskManager.createSubtask(newSubtask);

        Subtask subtask = taskManager.getSubtask(subtaskId);

        assertEquals("Повторить материаловедение", subtask.getName(), "nameError");
        assertEquals("Раздел 3", subtask.getDescription(), "descriptionError");
        assertEquals(IssueStatuses.DONE, subtask.getStatus(), "statusError");
    }

    @Test
    public void theHistoryManagerSavesThePreviousVersionOfTheTask(){
        Task newTask1 = new Task("Выкинуть мусор");
        int taskId = taskManager.createTask(newTask1);

        taskManager.getTask(taskId);

        Task newTask2 = new Task("пропылесосить");
        newTask2.setId(taskId);
        taskManager.updateTask(newTask2);

        taskManager.getTask(taskId);

        List<Task> historyList = taskManager.getHistory();

        assertEquals("Выкинуть мусор", historyList.getFirst().getName());
    }
}