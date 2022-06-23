package tasks;

import java.util.Objects;

public class Task {
    private String nameTask;
    private String descriptionTask;
    private int id;
    private Enum.Status statusTask;

    public Enum.Status getStatusTask() {
        return statusTask;
    }

    public void setStatusTask(Enum.Status statusTask) {
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

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Task task = (Task) obj;
        return Objects.equals(nameTask, task.nameTask) &&
                Objects.equals(descriptionTask, task.descriptionTask) &&
                (id == task.id) &&
                Objects.equals(statusTask, task.statusTask);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nameTask, descriptionTask, id, statusTask);
    }

    @Override
    public String toString() {
        return "tasks.Task{" +
                "nameTask='" + nameTask + '\'' +
                ", descriptionTask='" + descriptionTask + '\'' +
                ", id='" + id + '\'' +
                ", statusTask='" + statusTask + '\'' +
                '}';

    }
}