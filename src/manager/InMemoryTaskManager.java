package manager;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;


public class InMemoryTaskManager implements TaskManager {
    protected final HashMap<Integer, Task> taskHashMap = new HashMap<>();
    protected final HashMap<Integer, Epic> epicHashMap = new HashMap<>();
    protected final HashMap<Integer, Subtask> subTaskHashMap = new HashMap<>();


    private int numberTask;

    protected final HistoryManager historyManager = Managers.getDefaultHistory();

    //Создание задачи
    @Override
    public void objectTask(Task task) {
        task.setStatusTask(Task.Status.NEW);
        task.setId(generateId());
        taskHashMap.put(task.getId(), task);
    }

    //Вывод всех задач
    @Override
    public List<Task> writeTask() {
        List<Task> task = new ArrayList<>();
        for (Integer keySetTask : taskHashMap.keySet()) {
            task.add(taskHashMap.get(keySetTask));
        }
        return task;
    }

    //Удаление всех задач
    @Override
    public void clearTask() {
        taskHashMap.clear();
    }

    //Вывод задачи по идентификатору
    @Override
    public Task getTask(int identifier) {
        Task task = taskHashMap.get(identifier);
        if (task != null) {
            historyManager.add(task);
        }
        return task;
    }

    // Удаление задачи по идентификатору
    @Override
    public void removeTaskIdentifier(int identifier) {
        historyManager.remove(taskHashMap.get(identifier).getId());
        taskHashMap.remove(identifier);
    }

    // Замена задачи
    @Override
    public void updateTask(int identifier, Task task, Task.Status newStatus) {
        taskHashMap.remove(identifier);
        taskHashMap.put(identifier, task);
        task.setStatusTask(newStatus);
        task.setId(identifier);
    }

    //создание эпика
    @Override
    public void objectEpic(Epic epic) {
        epic.setStatusTask(Task.Status.NEW);
        epic.setId(generateId());
        epicHashMap.put(epic.getId(), epic);
    }

    //вывод всех эпиков
    @Override
    public List<Epic> writeEpic() {
        List<Epic> epic = new ArrayList<>();
        for (Integer keySetTask : epicHashMap.keySet()) {
            epic.add(epicHashMap.get(keySetTask));
        }
        return epic;
    }

    //удаление всех эпиков
    @Override
    public void clearEpic() {
        epicHashMap.clear();
        subTaskHashMap.clear();
    }

    //удаление эпика по идентификатору
    @Override
    public void removeEpicIdentifier(int identifier) {
        for (Integer id : epicHashMap.get(identifier).getIdSubTask()) {
            historyManager.remove(id);
            subTaskHashMap.remove(id);
        }
        historyManager.remove(epicHashMap.get(identifier).getId());
        epicHashMap.remove(identifier);
    }

    //вывод эпика по идентификатору
    @Override
    public Epic getEpic(int identifier) {
        Epic epic = epicHashMap.get(identifier);
        if (epic != null) {
            historyManager.add(epic);
        }
        return epic;
    }

    //замена эпика
    @Override
    public void updateEpic(int identifier, Epic epic) {
        Task.Status status = epicHashMap.get(identifier).getStatusTask();
        List<Integer> idSubtask1;
        idSubtask1 = epicHashMap.get(identifier).getIdSubTask();
        epicHashMap.remove(identifier);
        epicHashMap.put(identifier, epic);
        epic.setId(identifier);
        epic.setStatusTask(status);
        epic.setIdSubTask(idSubtask1);
    }

    //создание подзадачи
    @Override
    public void objectSubTask(Subtask subTask, int identifier) {
        subTask.setId(generateId());
        subTaskHashMap.put(subTask.getId(), subTask);
        subTask.setStatusTask(Task.Status.NEW);
        subTask.setIdEpic(identifier);
        epicHashMap.get(identifier).getIdSubTask().add(subTask.getId());
    }

    //вывод всех подзадач
    @Override
    public List<Subtask> writeSubTask() {
        List<Subtask> subTask = new ArrayList<>();
        for (Integer keySetTask : subTaskHashMap.keySet()) {
            subTask.add(subTaskHashMap.get(keySetTask));
        }
        return subTask;
    }

    // удаление всех подзадач
    @Override
    public void clearSubTask() {
        subTaskHashMap.clear();
        for (Integer keySetEpic : epicHashMap.keySet()) {
            epicHashMap.get(keySetEpic).setStatusTask(Task.Status.NEW);
        }
    }

    //замена подзадачи
    @Override
    public void updateSubTask(int identifier, Subtask subtask, Task.Status newStatus) {
        int numberEpic = subTaskHashMap.get(identifier).getIdEpic();
        subTaskHashMap.remove(identifier);
        subTaskHashMap.put(identifier, subtask);
        subtask.setStatusTask(newStatus);
        subtask.setIdEpic(numberEpic);
        statusEpic(numberEpic);
        subtask.setId(identifier);
    }

    // вывод подзадачи по идентификатору
    @Override
    public Subtask getSubtask(int identifier) {
        Subtask subTask = subTaskHashMap.get(identifier);
        if (subTask != null) {
            historyManager.add(subTask);
        }
        return subTask;
    }

    //удаление подзадачи по идентификатору
    @Override
    public void removeSubTaskEpicIdentifier(int identifier) {
        int numberEpic = subTaskHashMap.get(identifier).getIdEpic();
        historyManager.remove(subTaskHashMap.get(identifier).getId());
        subTaskHashMap.remove(identifier);
        int k = 0;
        for (int i = 0; i < epicHashMap.get(numberEpic).getIdSubTask().size(); i++) {
            if (epicHashMap.get(numberEpic).getIdSubTask().get(i) == identifier) {
                k = i;
            }
        }
        epicHashMap.get(numberEpic).getIdSubTask().remove(k);
        statusEpic(numberEpic);
    }

    //вывод подзадач определенного эпика
    @Override
    public List<Subtask> writeSubTaskEpicIdentifier(int identifier) {
        List<Subtask> subTask = new ArrayList<>();
        for (Integer keySetTask : epicHashMap.get(identifier).getIdSubTask()) {
            subTask.add(subTaskHashMap.get(keySetTask));
        }
        return subTask;
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    //создание идентификатора
    private int generateId() {
        return numberTask = numberTask + 1;
    }

    //замена статуса эпика после замены или удаления подзадачи этого эпика
    private void statusEpic(int idEpicStatus) {
        int numberNew = 0;
        int numberDone = 0;
        for (Integer id : epicHashMap.get(idEpicStatus).getIdSubTask()) {
            if (subTaskHashMap.get(id).getStatusTask().equals(Task.Status.DONE)) {
                numberDone = numberDone + 1;
            } else if (subTaskHashMap.get(id).getStatusTask().equals(Task.Status.NEW)) {
                numberNew = numberNew + 1;
            }
        }
        if (numberDone == epicHashMap.get(idEpicStatus).getIdSubTask().size()) {
            epicHashMap.get(idEpicStatus).setStatusTask(Task.Status.DONE);
        } else if (numberNew == epicHashMap.get(idEpicStatus).getIdSubTask().size()) {
            epicHashMap.get(idEpicStatus).setStatusTask(Task.Status.NEW);
        } else {
            epicHashMap.get(idEpicStatus).setStatusTask(Task.Status.IN_PROGRESS);
        }
    }

    public FileBackedTasksManager loadFromFile(File file) {
        try {
            if (file.isFile()) {
                String historyFile = Files.readString(Path.of(file.getAbsolutePath()));
                String[] historyFile1 = historyFile.split(System.lineSeparator());
                for (int i = 1; i < historyFile1.length; i++) {
                    if (!historyFile1[i].isEmpty()) {
                        System.out.println(historyFile1[i]);
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
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new FileBackedTasksManager(file);
    }

    public void fromString(String value) {
        String[] split = value.split(", ");
        if (FileBackedTasksManager.Type.valueOf(split[1]).equals(FileBackedTasksManager.Type.SUBTASK)) {
            objectSubTask(new Subtask(split[2], split[4]), Integer.parseInt(split[5]));
            subTaskHashMap.get(Integer.parseInt(split[0])).setStatusTask(Task.Status.valueOf(split[3]));
        }
        if (FileBackedTasksManager.Type.valueOf(split[1]).equals(FileBackedTasksManager.Type.TASK)) {
            objectTask(new Task(split[2], split[4]));
            taskHashMap.get(Integer.parseInt(split[0])).setStatusTask(Task.Status.valueOf(split[3]));
        }
        if (FileBackedTasksManager.Type.valueOf(split[1]).equals(FileBackedTasksManager.Type.EPIC)) {
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

}
