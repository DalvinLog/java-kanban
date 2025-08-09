import java.util.ArrayList;

public class Epic extends Task {
    private final ArrayList<Integer> subtasksId;

    Epic (String name) {
        super(name);
        subtasksId = new ArrayList<>();
    }

    Epic (String name, String description) {
        super(name, description);
        subtasksId = new ArrayList<>();
    }

    //добавить подзадачу
    void addSubtaskId(int id) {
        subtasksId.add(id);
    }

    //получить список подзадач
    ArrayList<Integer> getSubtasksId() {
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
