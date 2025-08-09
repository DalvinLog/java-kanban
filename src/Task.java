public class Task {
    private final String name;
    private final String description;
    private int id;
    private IssueStatuses status;

    Task (String name) {
        this.name = name;
        description = "";
    }

    Task (String name, String description) {
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

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Task{name=" + name
                + ", description=" + description
                + ", status=" + status
                + ", id=" + id + "}";
    }
}
