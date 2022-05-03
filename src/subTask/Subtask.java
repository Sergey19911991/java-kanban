package subTask;

import task.Task;

import java.util.Objects;

public class Subtask extends Task {
    private int idEpic;

    public Subtask(String nameTask, String descriptionTask) {
        super(nameTask, descriptionTask);
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
        return (idEpic == subTask.idEpic);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idEpic);
    }

    @Override
    public String toString() {
        String result = "Subtask{" +
                "idEpic='" + idEpic + '\'' +
                '}';
        return result;
    }
}