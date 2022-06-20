package manager;

import java.io.*;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;


public class FileBackedTasksManager extends InMemoryTaskManager {
    private File file;

    public FileBackedTasksManager(File file) {
        this.file = file;
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
            throw new ManagerSaveException("Произошла проблема с сохранением в файл FileBackedTasksManager"
                    + e.getMessage());
        }
    }


    private String toString(HistoryManager manager) {
        StringBuilder historyId = new StringBuilder();
        for (Task i : manager.getHistory()) {
            historyId.append(i.getId());
            historyId.append(", ");
        }
        return historyId.toString();
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


    private String toString(Task task) {
        String stringTask = task.getId() + ", ";
        if (taskHashMap.containsValue(task)) {
            stringTask = stringTask + Task.Type.TASK + ", ";
        }
        if (epicHashMap.containsValue(task)) {
            stringTask = stringTask + Task.Type.EPIC + ", ";
        }
        if (subTaskHashMap.containsValue(task)) {
            stringTask = stringTask + Task.Type.SUBTASK + ", ";
        }
        stringTask = stringTask + task.getNameTask() + ", " + task.getStatusTask() + ", " +
                task.getDescriptionTask() + ", ";
        if (subTaskHashMap.containsValue(task)) {
            stringTask = stringTask + subTaskHashMap.get(task.getId()).getIdEpic() + ", ";
        }
        return stringTask;
    }

    public FileBackedTasksManager loadFromFile(File file) {
        if (file.isFile()) {
            String historyFile = null;
            try {
                historyFile = Files.readString(Path.of(file.getAbsolutePath()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            String[] historyFile1 = historyFile.split(System.lineSeparator());
            for (int i = 1; i < historyFile1.length; i++) {
                if (!historyFile1[i].isEmpty()) {
                    fromString(historyFile1[i]);
                } else {
                    for (Integer j : fromStringHistory(historyFile1[i + 1])) {
                        if (taskHashMap.containsKey(j)) {
                            getTask(j);
                        } else if (epicHashMap.containsKey(j)) {
                            getEpic(j);
                        } else if (subTaskHashMap.containsKey(j)) {
                            getSubtask(j);
                        }
                    }
                    break;
                }
            }
        }
        return new FileBackedTasksManager(file);
    }

    public void fromString(String value) {
        String[] split = value.split(", ");
        if (Task.Type.valueOf(split[1]).equals(Task.Type.SUBTASK)) {
            objectSubTask(new Subtask(split[2], split[4]), Integer.parseInt(split[5]));
            subTaskHashMap.get(Integer.parseInt(split[0])).setStatusTask(Task.Status.valueOf(split[3]));
        }
        if (Task.Type.valueOf(split[1]).equals(Task.Type.TASK)) {
            objectTask(new Task(split[2], split[4]));
            taskHashMap.get(Integer.parseInt(split[0])).setStatusTask(Task.Status.valueOf(split[3]));
        }
        if (Task.Type.valueOf(split[1]).equals(Task.Type.EPIC)) {
            objectEpic(new Epic(split[2], split[4]));
            epicHashMap.get(Integer.parseInt(split[0])).setStatusTask(Task.Status.valueOf(split[3]));
        }
    }

    public List<Integer> fromStringHistory(String value) {
        List<Integer> history = new ArrayList<>();
        String[] split = value.split(", ");
        for (int i = 0; i < split.length; i++) {
            history.add(Integer.parseInt(split[i]));
        }
        return history;
    }


    public static void main(String[] args) {
        File file = new File("fileWriter.txt");
        TaskManager manager1 = Managers.getFileBackedTasksManager(file);

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

class ManagerSaveException extends RuntimeException {
    public ManagerSaveException() {
    }

    public ManagerSaveException(final String message) {
        super(message);
    }
}
