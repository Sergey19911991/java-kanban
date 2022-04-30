import java.util.ArrayList;

public class Epic extends Task {
    ArrayList<Integer> IdSubTask = new ArrayList<>();
    public Epic(String nameTask, String descriptionTask) {
       super(nameTask, descriptionTask);
    }
}
