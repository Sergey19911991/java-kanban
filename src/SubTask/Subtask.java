package SubTask;
import Task.Task;
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

}



