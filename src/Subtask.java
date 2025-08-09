public class Subtask extends Task{
    private final int epicId;

    Subtask (String name, int epicId) {
        super(name);
        this.epicId = epicId;
    }

    Subtask (String name, String description, int epicId) {
        super(name, description);
        this.epicId = epicId;
    }

    int getEpicId() {
        return epicId;
    }

    @Override
    public String toString() {
        return "Subtask{name=" + getName()
                + ", description=" + getDescription()
                + ", status=" + getStatus()
                + ", id=" + getId()
                + ", epicId=" + epicId + "}";
    }
}
