package manager;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.io.File;
import java.util.List;

public interface TaskManager {
    List<Integer> fromStringHistory(String value);

    void fromString(String value);

    FileBackedTasksManager loadFromFile(File file);

    void objectTask(Task task) ;

    List<Task> writeTask();

    void clearTask();

    Task getTask(int identifier);

    void removeTaskIdentifier(int identifier);

    void updateTask(int identifier, Task task, Task.Status newStatus);

    void objectEpic(Epic epic) ;

    List<Epic> writeEpic();

    void clearEpic();

    void removeEpicIdentifier(int identifier);

    Epic getEpic(int identifier) ;

    void updateEpic(int identifier, Epic epic);

    void objectSubTask(Subtask subTask, int identifier);

    List<Subtask> writeSubTask();

    void clearSubTask();

    void updateSubTask(int identifier, Subtask subtask, Task.Status newStatus);

    Subtask getSubtask(int identifier);

    void removeSubTaskEpicIdentifier(int identifier);

    List<Subtask> writeSubTaskEpicIdentifier(int identifier);

    List<Task> getHistory();
}
