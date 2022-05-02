package Manager;


import Epic.Epic;
import SubTask.Subtask;
import Task.Task;

import java.util.HashMap;
import java.util.ArrayList;

public class Manager {
    HashMap<Integer, Task> taskHashMap = new HashMap<>();
    HashMap<Integer, Epic> epicHashMap = new HashMap<>();
    HashMap<Integer, Subtask> subTaskHashMap = new HashMap<>();
    int numberTask;

    //Создание задачи
    public void objectTask(Task task) {
        task.setStatusTask(Task.Status.NEW);
        taskHashMap.put(numberTask, task);
        task.setId(numberTask);
        numberTask = numberTask + 1;
    }

    //Вывод всех задач
    public void writeTask() {
        for (Integer keySetTask : taskHashMap.keySet()) {
            System.out.print("ID " + keySetTask + "; ");
            System.out.print("Название Задачи: " + taskHashMap.get(keySetTask).getNameTask() + "; ");
            System.out.print("Описание: " + taskHashMap.get(keySetTask).getDescriptionTask() + "; ");
            System.out.println("Статус: " + taskHashMap.get(keySetTask).getStatusTask() + ". ");
        }

    }

    //Удаление всех задач
    public void clearTask() {
        taskHashMap.clear();
    }

    //Вывод задачи по идентификатору
    public void writeTaskIdentifier(int identifier) {
        System.out.print("ID " + identifier + "; ");
        System.out.print("Название Задачи: " + taskHashMap.get(identifier).getNameTask() + "; ");
        System.out.print("Описание: " + taskHashMap.get(identifier).getDescriptionTask() + "; ");
        System.out.println("Статус: " + taskHashMap.get(identifier).getStatusTask() + ". ");
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

    }


    //создание эпика
    public void objectEpic(Epic epic) {
        epic.setStatusTask(Task.Status.NEW);
        epicHashMap.put(numberTask, epic);
        epic.setId(numberTask);
        numberTask = numberTask + 1;
    }

    //вывод всех эпиков
    public void writeEpic() {
        for (Integer keySetEpic : epicHashMap.keySet()) {
            System.out.print("ID " + keySetEpic + "; ");
            System.out.print("Название Эпика: " + epicHashMap.get(keySetEpic).getNameTask() + "; ");
            System.out.print("Описание: " + epicHashMap.get(keySetEpic).getDescriptionTask() + "; ");
            System.out.println("Статус: " + epicHashMap.get(keySetEpic).getStatusTask() + ". ");
        }

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
    public void writeEpicIdentifier(int identifier) {
        System.out.print("ID " + identifier + "; ");
        System.out.print("Название Эпика: " + epicHashMap.get(identifier).getNameTask() + "; ");
        System.out.print("Описание: " + epicHashMap.get(identifier).getDescriptionTask() + "; ");
        System.out.println("Статус: " + epicHashMap.get(identifier).getStatusTask() + ". ");
    }


    //замена эпика
    public void updateEpic(int identifier, Epic epic) {
        Task.Status status = epicHashMap.get(identifier).getStatusTask();
        ArrayList<Integer> idSubtask1;
        idSubtask1 = epicHashMap.get(identifier).getIdSubTask();
        epicHashMap.remove(identifier);
        epicHashMap.put(identifier, epic);
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
        numberTask = numberTask + 1;

    }


    //вывод всех подзадач
    public void writeSubTask() {
        for (Integer keySetTask : subTaskHashMap.keySet()) {
            System.out.print("ID " + keySetTask + "; ");
            System.out.print("Название Подзадачи: " + subTaskHashMap.get(keySetTask).getNameTask() + "; ");
            System.out.print("Описание: " + subTaskHashMap.get(keySetTask).getDescriptionTask() + "; ");
            System.out.println("Статус: " + subTaskHashMap.get(keySetTask).getStatusTask() + ". ");
        }

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

    }

    // вывод подзадачи по идентификатору
    public void writeSubTaskIdentifier(int identifier) {
        System.out.print("ID " + identifier + "; ");
        System.out.print("Название Подзадачи: " + subTaskHashMap.get(identifier).getNameTask() + "; ");
        System.out.print("Описание: " + subTaskHashMap.get(identifier).getDescriptionTask() + "; ");
        System.out.println("Статус: " + subTaskHashMap.get(identifier).getStatusTask() + ". ");
    }


    //удаление подзадачи по идентификатору
    public void removeSubTaskEpicIdentifier(int identifier) {
        int numberEpic = subTaskHashMap.get(identifier).getIdEpic();
        subTaskHashMap.remove(identifier);
        for (Integer i : epicHashMap.get(numberEpic).getIdSubTask()) {
            if (i == identifier) {
                epicHashMap.get(numberEpic).getIdSubTask().remove(i);
            }
        }
        statusEpic(numberEpic);
    }


    //вывод подзадач определенного эпика
    public void writeSubTaskEpicIdentifier(int identifier) {
        for (Integer id : epicHashMap.get(identifier).getIdSubTask()) {
            System.out.print("ID " + id + "; ");
            System.out.print("Название Подзадачи: " + subTaskHashMap.get(id).getNameTask() + "; ");
            System.out.print("Описание: " + subTaskHashMap.get(id).getDescriptionTask() + "; ");
            System.out.println("Статус: " + subTaskHashMap.get(id).getStatusTask() + ". ");
        }
    }

    //замена статуса эпика после замены или удаления подзадачи этого эпика
    private void statusEpic(int idEpicStatus) {
        int numberNew = 0;
        int numberDone = 0;
        for (Integer id : epicHashMap.get(idEpicStatus).getIdSubTask()) {
            if (subTaskHashMap.get(id).getStatusTask().equals("DONE")) {
                numberDone = numberDone + 1;
            } else if (subTaskHashMap.get(id).getStatusTask().equals("NEW")) {
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
}