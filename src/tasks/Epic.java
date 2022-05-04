package tasks;

import java.util.ArrayList;
import java.util.Objects;

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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Epic epic = (Epic) obj;
        return Objects.equals(idSubTask, epic.idSubTask)&&super.equals(obj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(),idSubTask);
    }

    @Override
    public String toString() {
        String result = super.toString()+"tasks.Epic{" +
                "idSubTask=" + idSubTask +'}';
        return result;
    }
}