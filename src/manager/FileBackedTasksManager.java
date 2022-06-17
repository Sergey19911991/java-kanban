package manager;

import java.io.IOException;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.io.Writer;
import java.io.FileWriter;
import java.io.File;


public class FileBackedTasksManager extends InMemoryTaskManager {
    File file;

    public FileBackedTasksManager(File file) {
        this.file = file;
    }

    public enum Type {
        TASK, EPIC, SUBTASK
    }

    public void save() {
        try {
            Writer fileWriter = new FileWriter(file);
            fileWriter.write("id,type,name,status,description,epic");
            fileWriter.write(System.lineSeparator());
            for (Task i : super.writeTask()) {
                fileWriter.write(toString(i));
                fileWriter.write(System.lineSeparator());
            }
            for (Epic i : super.writeEpic()) {
                fileWriter.write(toString(i));
                fileWriter.write(System.lineSeparator());
            }
            for (Subtask i : super.writeSubTask()) {
                fileWriter.write(toString(i));
                fileWriter.write(System.lineSeparator());
            }
            fileWriter.write(System.lineSeparator());
            fileWriter.write(toString(historyManager));
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    String toString(HistoryManager manager) {
        StringBuilder historyId = new StringBuilder();
        for (Task i : manager.getHistory()) {
            historyId.append(i.getId());
            historyId.append(", ");
        }
        String historyIdString = historyId.toString();
        return historyIdString;
    }


    @Override
    public void objectTask(Task task) {
        super.objectTask(task);
        save();
    }

    @Override
    public void objectEpic(Epic epic) {
        super.objectEpic(epic);
        save();
    }

    @Override
    public void objectSubTask(Subtask subTask, int identifier) {
        super.objectSubTask(subTask, identifier);
        save();
    }

    @Override
    public Task getTask(int identifier) {
        Task task = super.getTask(identifier);
        save();
        return task;
    }

    @Override
    public Epic getEpic(int identifier) {
        super.getEpic(identifier);
        save();
        return super.getEpic(identifier);
    }

    @Override
    public Subtask getSubtask(int identifier) {
        Subtask subTask = super.getSubtask(identifier);
        save();
        return subTask;
    }


    String toString(Task task) {
        String stringTask = task.getId() + ", ";
        if (taskHashMap.containsValue(task)) {
            stringTask = stringTask + Type.TASK + ", ";
        }
        if (epicHashMap.containsValue(task)) {
            stringTask = stringTask + Type.EPIC + ", ";
        }
        if (subTaskHashMap.containsValue(task)) {
            stringTask = stringTask + Type.SUBTASK + ", ";
        }
        stringTask = stringTask + task.getNameTask() + ", " + task.getStatusTask() + ", " +
                task.getDescriptionTask() + ", ";
        if (subTaskHashMap.containsValue(task)) {
            stringTask = stringTask + subTaskHashMap.get(task.getId()).getIdEpic() + ", ";
        }
        return stringTask;
    }


    public static void main(String[] args) {
        File file = new File("C://Users//User//dev//java-kanban", "fileWriter.txt");
        TaskManager manager1 = Managers.getFileBackedTasksManager(file);
        manager1.loadFromFile(file);

        manager1.objectTask(new Task("Переезд", "Собрать коробки"));
        manager1.objectEpic(new Epic("Поменять работу", "Чтобы больше была зарплата"));
        manager1.objectSubTask(new Subtask("Искать работу", "Всеми способами"), 2);
        manager1.getSubtask(3);
        manager1.getTask(1);
        manager1.getEpic(2);

        TaskManager manager2 = Managers.getFileBackedTasksManager(file);
        manager2.loadFromFile(file);
        for (Object i : manager2.writeTask()) {
            System.out.println(i);
        }
        for (Object i : manager2.writeEpic()) {
            System.out.println(i);
        }
        for (Object i : manager2.writeSubTask()) {
            System.out.println(i);
        }
        System.out.println(manager2.getHistory());
    }

}


