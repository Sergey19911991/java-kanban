package manager;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.ArrayList;
import java.util.List;

public interface TaskManager {
    void objectTask(Task task);

    ArrayList<Task> writeTask();

    void clearTask();

    Task getTask(int identifier);

    void removeTaskIdentifier(int identifier);

    void updateTask(int identifier, Task task, Task.Status newStatus);

    void objectEpic(Epic epic);

    ArrayList<Epic> writeEpic();

    void clearEpic();

    void removeEpicIdentifier(int identifier);

    Epic getEpic(int identifier);

    void updateEpic(int identifier, Epic epic);

    void objectSubTask(Subtask subTask, int identifier);

    ArrayList<Subtask> writeSubTask();

    void clearSubTask();

    void updateSubTask(int identifier, Subtask subtask, Task.Status newStatus);

    Subtask getSubtask(int identifier);

    void removeSubTaskEpicIdentifier(int identifier);

    ArrayList<Subtask> writeSubTaskEpicIdentifier(int identifier);

    List<Task> getHistory();
}
