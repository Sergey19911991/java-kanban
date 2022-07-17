package manager;

import java.io.*;

import exceptions.ManagerSaveException;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import tasks.Enum;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

public class FileBackedTasksManager extends InMemoryTaskManager {
    private final File file;

    public FileBackedTasksManager(File file) {
        this.file = file;
    }

    public void save() {
        Writer fileWriter = null;
        try {
            fileWriter = new FileWriter(file);
            fileWriter.write("id, type, name, status, description, startTime, duration, epic");
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

        } catch (IOException e) {
            throw new ManagerSaveException("Произошла проблема с сохранением в файл FileBackedTasksManager"
                    + e.getMessage());
        } finally {
            try {
                fileWriter.close();
            } catch (IOException e) {
                throw new ManagerSaveException("Произошла при закрытии потока записи"
                        + e.getMessage());
            }
        }
    }


    static String toString(HistoryManager manager) {
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

    @Override
    public void removeTaskIdentifier(int identifier) {
        super.removeTaskIdentifier(identifier);
        save();
    }

    @Override
    public void updateTask(int identifier, Task task, Enum.Status newStatus) {
        super.updateTask(identifier, task, newStatus);
        save();
    }

    @Override
    public void removeEpicIdentifier(int identifier) {
        super.removeEpicIdentifier(identifier);
        save();
    }

    @Override
    public void updateEpic(int identifier, Epic epic) {
        super.updateEpic(identifier, epic);
        save();
    }

    @Override
    public void updateSubTask(int identifier, Subtask subtask, Enum.Status newStatus) {
        super.updateSubTask(identifier, subtask, newStatus);
        save();
    }

    @Override
    public void removeSubTaskEpicIdentifier(int identifier) {
        super.removeSubTaskEpicIdentifier(identifier);
        save();
    }

    public String toString(Task task) {
        String stringTask = task.getId() + ", ";
        if (taskHashMap.containsKey(task.getId())) {
            stringTask = stringTask + Enum.Type.TASK + ", ";
        }
        if (epicHashMap.containsKey(task.getId())) {
            stringTask = stringTask + Enum.Type.EPIC + ", ";
        }
        if (subTaskHashMap.containsKey(task.getId())) {
            stringTask = stringTask + Enum.Type.SUBTASK + ", ";
        }
        stringTask = stringTask + task.getNameTask() + ", " + task.getStatusTask() + ", " +
                task.getDescriptionTask() + ", " + task.getStartTime() + ", " + task.getDuration() + ", ";
        if (subTaskHashMap.containsKey(task.getId())) {
            stringTask = stringTask + subTaskHashMap.get(task.getId()).getIdEpic() + ", ";
        }
        return stringTask;
    }

    private static FileBackedTasksManager loadFromFile(File file) {
        numberTask = 0;
        String historyFile = null;
        try {
            historyFile = Files.readString(Path.of(file.getAbsolutePath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] historyFile1 = historyFile.split(System.lineSeparator());
        FileBackedTasksManager newTaskManager = new FileBackedTasksManager(file);
        for (int i = 1; i < historyFile1.length; i++) {
            if (!historyFile1[i].isEmpty()) {
                newTaskManager.fromString(historyFile1[i]);
            } else {
                for (Integer j : fromStringHistory(historyFile1[i + 1])) {
                    if (newTaskManager.taskHashMap.containsKey(j)) {
                        newTaskManager.getTask(j);
                    } else if (newTaskManager.epicHashMap.containsKey(j)) {
                        newTaskManager.getEpic(j);
                    } else if (newTaskManager.subTaskHashMap.containsKey(j)) {
                        newTaskManager.getSubtask(j);
                    }
                }
                break;
            }
        }
        return newTaskManager;
    }

    public void fromString(String value) {
        String[] split = value.split(", ");
        if (Enum.Type.valueOf(split[1]).equals(Enum.Type.TASK)) {
            Task task = new Subtask(split[2], split[4], LocalDateTime.parse(split[5]), Long.parseLong(split[6]));
            task.setId(Integer.parseInt(split[0]));
            task.setStatusTask(Enum.Status.valueOf(split[3]));
            taskHashMap.put(Integer.parseInt(split[0]), task);
            treeTask.add(task);
            numberTask++;
        }
        if (Enum.Type.valueOf(split[1]).equals(Enum.Type.SUBTASK)) {
            Subtask subtask = new Subtask(split[2], split[4], LocalDateTime.parse(split[5]), Long.parseLong(split[6]));
            subtask.setId(Integer.parseInt(split[0]));
            subtask.setStatusTask(Enum.Status.valueOf(split[3]));
            subtask.setIdEpic(Integer.parseInt(split[7]));
            subTaskHashMap.put(Integer.parseInt(split[0]), subtask);
            epicHashMap.get(Integer.parseInt(split[7])).getIdSubTask().add(Integer.parseInt(split[0]));
            treeSubTask.add(subtask);
            treeTask.add(subtask);
            numberTask++;
        }
        if (Enum.Type.valueOf(split[1]).equals(Enum.Type.EPIC)) {
            Epic epic = new Epic(split[2], split[4], null, Long.parseLong(split[6]));
            if (!(split[5].equals("null"))) {
                epic = new Epic(split[2], split[4], LocalDateTime.parse(split[5]), Long.parseLong(split[6]));
            }
            epic.setId(Integer.parseInt(split[0]));
            epic.setStatusTask(Enum.Status.valueOf(split[3]));
            epicHashMap.put(Integer.parseInt(split[0]), epic);
            numberTask++;
        }
    }

    private static List<Integer> fromStringHistory(String value) {
        List<Integer> history = new ArrayList<>();
        String[] split = value.split(", ");
        for (int i = 0; i < split.length; i++) {
            history.add(Integer.parseInt(split[i]));
        }
        return history;
    }


    public static void main(String[] args) {
        File file = new File("fileWriter.txt");
        TaskManager manager1 = Managers.getDefault();


        manager1.objectEpic(new Epic("Поменять работу", "Чтобы больше была зарплата", null, 0));

        manager1.objectSubTask(new Subtask("Искать работу", "Всеми способами", LocalDateTime.of
                (2010, 1, 1, 4, 0), 80), 1);
        manager1.objectSubTask(new Subtask("Искать работу", "Всеми способами", LocalDateTime.of
                (1999, 1, 1, 2, 0), 80), 1);
        manager1.objectTask(new Task("!!!!!", "&&&&&&", LocalDateTime.of
                (2010, 1, 1, 3, 0), 0));
        manager1.updateSubTask(2, new Subtask("Искать работу", "Всеми способами", LocalDateTime.of
                (1999, 1, 1, 4, 0), 80), Enum.Status.DONE);
        manager1.updateSubTask(3, new Subtask("Искать работу", "Всеми способами", LocalDateTime.of
                (1998, 1, 1, 3, 0), 80), Enum.Status.DONE);
        manager1.getTask(4);
        manager1.removeTaskIdentifier(2);
        manager1.removeSubTaskEpicIdentifier(3);
        System.out.println(manager1.getHistory());


        FileBackedTasksManager newTaskManager = FileBackedTasksManager.loadFromFile(file);
        for (Task i : newTaskManager.writeTask()) {
            System.out.println(i);
        }
        for (Epic i : newTaskManager.writeEpic()) {
            System.out.println(i);
        }
        for (Subtask i : newTaskManager.writeSubTask()) {
            System.out.println(i);
        }

        System.out.println(newTaskManager.getPrioritizedTasks());

    }

}
