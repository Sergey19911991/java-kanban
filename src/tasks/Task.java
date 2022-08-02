package tasks;

import java.util.Objects;
import java.time.LocalDateTime;

public class Task {
    private String nameTask;
    private String descriptionTask;
    private int id;
    private Enum.Status statusTask;
    private long duration;
    private LocalDateTime startTime;


    public Enum.Status getStatusTask() {
        return statusTask;
    }

    public void setStatusTask(Enum.Status statusTask) {
        this.statusTask = statusTask;
    }

    public Task(String nameTask, String descriptionTask, LocalDateTime startTime, long duration) {
        this.nameTask = nameTask;
        this.descriptionTask = descriptionTask;
        this.duration = duration;
        this.startTime = startTime;
    }

    public void setName(String nameTask){
        this.nameTask= nameTask;
    }

    public void setDescription(String descriptionTask){
        this.descriptionTask= descriptionTask;
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

    public long getDuration() {
        return this.duration;
    }

    public LocalDateTime getStartTime() {
        return this.startTime;
    }

    public LocalDateTime getEndTime() {
        return this.startTime.plusMinutes(this.duration);
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Task task = (Task) obj;
        return Objects.equals(nameTask, task.nameTask) &&
                Objects.equals(descriptionTask, task.descriptionTask) &&
                (id == task.id) &&
                Objects.equals(statusTask, task.statusTask) &&
                Objects.equals(startTime, task.startTime) &&
                (duration == task.duration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nameTask, descriptionTask, id, statusTask, startTime, duration);
    }

    @Override
    public String toString() {
        return "tasks.Task{" +
                "nameTask='" + nameTask + '\'' +
                ", descriptionTask='" + descriptionTask + '\'' +
                ", id='" + id + '\'' +
                ", statusTask='" + statusTask + '\'' +
                ", startTime='" + startTime + '\'' +
                ", duration='" + duration + '\'' +
                '}';

    }

}

