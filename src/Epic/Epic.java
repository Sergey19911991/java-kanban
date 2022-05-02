package Epic;
import Task.Task;
import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<Integer> idSubTask = new ArrayList<>();

    public Epic(String nameTask, String descriptionTask) {
        super(nameTask, descriptionTask);
    }

    public void setIdSubTask(ArrayList<Integer> idSubTask) {
        this.idSubTask = idSubTask;
    }

    public ArrayList<Integer> getIdSubTask() {
        return idSubTask;
    }
}


