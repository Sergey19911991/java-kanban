package tasks;

import java.util.List;
import java.util.ArrayList;
import java.util.Objects;
import java.time.LocalDateTime;

public class Epic extends Task {
    private List<Integer> idSubTask = new ArrayList<>();
    private LocalDateTime endTime;

    public Epic(String nameTask, String descriptionTask, LocalDateTime startTime, long duration) {
        super(nameTask, descriptionTask, startTime, duration);
    }

    public void setIdSubTask(List<Integer> idSubTask) {
        this.idSubTask = idSubTask;
    }

    public List<Integer> getIdSubTask() {
        return idSubTask;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public LocalDateTime getEndTime() {
        return this.endTime;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Epic epic = (Epic) obj;
        return Objects.equals(idSubTask, epic.idSubTask) && super.equals(obj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), idSubTask);
    }

    @Override
    public String toString() {
        return super.toString() + "tasks.Epic{" +
                "idSubTask=" + idSubTask + '}';

    }
}