package manager;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.HashMap;
import java.util.ArrayList;

public class Manager {
    private HashMap<Integer, Task> taskHashMap = new HashMap<>();
    private HashMap<Integer, Epic> epicHashMap = new HashMap<>();
    private HashMap<Integer, Subtask> subTaskHashMap = new HashMap<>();

    private int numberTask;

    //Создание задачи
    public void objectTask(Task task) {
        task.setStatusTask(Task.Status.NEW);
        taskHashMap.put(numberTask, task);
        task.setId(numberTask);
        generateId();
    }

    //Вывод всех задач
    public ArrayList<Task> writeTask() {
        ArrayList<Task> task = new ArrayList<>();
        for (Integer keySetTask : taskHashMap.keySet()) {
            task.add(taskHashMap.get(keySetTask));
        }
        return task;
    }

    //Удаление всех задач
    public void clearTask() {
        taskHashMap.clear();
    }

    //Вывод задачи по идентификатору
    public Task writeTaskIdentifier(int identifier) {
        return taskHashMap.get(identifier);
    }

    // Удаление задачи по идентификатору
    public void removeTaskIdentifier(int identifier) {
        taskHashMap.remove(identifier);
    }

    // Замена задачи
    public void updateTask(int identifier, Task task, Task.Status newStatus) {
        taskHashMap.remove(identifier);
        taskHashMap.put(identifier, task);
        task.setStatusTask(newStatus);
        task.setId(identifier);
    }

    //создание эпика
    public void objectEpic(Epic epic) {
        epic.setStatusTask(Task.Status.NEW);
        epicHashMap.put(numberTask, epic);
        epic.setId(numberTask);
        generateId();
    }

    //вывод всех эпиков
    public ArrayList<Epic> writeEpic() {
        ArrayList<Epic> epic = new ArrayList<>();
        for (Integer keySetTask : epicHashMap.keySet()) {
            epic.add(epicHashMap.get(keySetTask));
        }
        return epic;
    }

    //удаление всех эпиков
    public void clearEpic() {
        epicHashMap.clear();
        subTaskHashMap.clear();
    }

    //удаление эпика по идентификатору
    public void removeEpicIdentifier(int identifier) {
        for (Integer id : epicHashMap.get(identifier).getIdSubTask()) {
            subTaskHashMap.remove(id);
        }
        epicHashMap.remove(identifier);
    }


    //вывод эпика по идентификатору
    public Epic writeEpicIdentifier(int identifier) {
        return epicHashMap.get(identifier);
    }


    //замена эпика
    public void updateEpic(int identifier, Epic epic) {
        Task.Status status = epicHashMap.get(identifier).getStatusTask();
        ArrayList<Integer> idSubtask1;
        idSubtask1 = epicHashMap.get(identifier).getIdSubTask();
        epicHashMap.remove(identifier);
        epicHashMap.put(identifier, epic);
        epic.setId(identifier);
        epic.setStatusTask(status);
        epic.setIdSubTask(idSubtask1);
    }


    //создание подзадачи
    public void objectSubTask(Subtask subTask, int identifier) {
        subTaskHashMap.put(numberTask, subTask);
        subTask.setStatusTask(Task.Status.NEW);
        subTask.setIdEpic(identifier);
        epicHashMap.get(identifier).getIdSubTask().add(numberTask);
        subTask.setId(numberTask);
        generateId();
    }


    //вывод всех подзадач
    public ArrayList<Subtask> writeSubTask() {
        ArrayList<Subtask> subTask = new ArrayList<>();
        for (Integer keySetTask : subTaskHashMap.keySet()) {
            subTask.add(subTaskHashMap.get(keySetTask));
        }
        return subTask;
    }

    // удаление всех подзадач
    public void clearSubTask() {
        subTaskHashMap.clear();
        for (Integer keySetEpic : epicHashMap.keySet()) {
            epicHashMap.get(keySetEpic).setStatusTask(Task.Status.NEW);
        }
    }


    //замена подзадачи
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
    public Subtask writeSubTaskIdentifier(int identifier) {
        return subTaskHashMap.get(identifier);
    }


    //удаление подзадачи по идентификатору
    public void removeSubTaskEpicIdentifier(int identifier) {
        int numberEpic = subTaskHashMap.get(identifier).getIdEpic();
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
    public ArrayList<Subtask> writeSubTaskEpicIdentifier(int identifier) {
        ArrayList<Subtask> subTask = new ArrayList<>();
        for (Integer keySetTask : epicHashMap.get(identifier).getIdSubTask()) {
            subTask.add(subTaskHashMap.get(keySetTask));
        }
        return subTask;
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

    //создание идентификатора
    private void generateId() {
        numberTask = numberTask + 1;
    }

}