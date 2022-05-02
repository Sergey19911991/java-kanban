package Task;

public class Task {
    private String nameTask;
    private String descriptionTask;
    private int id;
    private Status statusTask;

    public enum Status {
        NEW, IN_PROGRESS, DONE
    }

    public Status getStatusTask() {
        return statusTask;
    }

    public void setStatusTask(Status statusTask) {
        this.statusTask = statusTask;
    }

    public Task(String nameTask, String descriptionTask) {
        this.nameTask = nameTask;
        this.descriptionTask = descriptionTask;
    }

    public String getNameTask() {
        return this.nameTask;
    }

    public String getDescriptionTask() {
        return this.descriptionTask;
    }


    public void setId(int id) {
        this.id = id;
    }

}
