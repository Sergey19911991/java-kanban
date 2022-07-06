package manager;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import tasks.Enum;

import java.util.List;
import java.util.TreeSet;

public interface TaskManager {
   TreeSet getPrioritizedSubTasks();

   TreeSet getPrioritizedTasks();

   void fromString(String value);

    void objectTask(Task task);

    List<Task> writeTask();

    void clearTask();

    Task getTask(int identifier);

    void removeTaskIdentifier(int identifier);

    void updateTask(int identifier, Task task, Enum.Status newStatus);

    void objectEpic(Epic epic);

    List<Epic> writeEpic();

    void clearEpic();

    void removeEpicIdentifier(int identifier);

    Epic getEpic(int identifier);

    void updateEpic(int identifier, Epic epic);

    void objectSubTask(Subtask subTask, int identifier);

    List<Subtask> writeSubTask();

    void clearSubTask();

    void updateSubTask(int identifier, Subtask subtask, Enum.Status newStatus);

    Subtask getSubtask(int identifier);

    void removeSubTaskEpicIdentifier(int identifier);

    List<Subtask> writeSubTaskEpicIdentifier(int identifier);

    List<Task> getHistory();
}