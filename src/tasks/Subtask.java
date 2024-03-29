package tasks;

import java.util.Objects;
import java.time.LocalDateTime;

public class Subtask extends Task {
    private int idEpic;

    public Subtask(String nameTask, String descriptionTask, LocalDateTime startTime, long duration) {
        super(nameTask, descriptionTask, startTime, duration);
    }

    public void setIdEpic(int idEpic) {
        this.idEpic = idEpic;
    }

    public int getIdEpic() {
        return idEpic;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Subtask subTask = (Subtask) obj;
        return (idEpic == subTask.idEpic) && super.equals(obj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), idEpic);
    }

    @Override
    public String toString() {
        return super.toString() + "tasks.Subtask{" +
                "idEpic='" + idEpic + '\'' + '}';

    }
}