package manager;

import static org.junit.jupiter.api.Assertions.*;


import Interface.TaskManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tasks.Enum;
import tasks.Subtask;
import tasks.Task;
import tasks.Epic;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

abstract class TaskManagerTest<T extends TaskManager> {
    public T taskManager;

    public abstract T createTaskManager() throws IOException;

    @BeforeEach
    public void updateTaskManager() throws IOException {
        taskManager = (T) createTaskManager();
    }

    @Test
    public void addTask() {
        Task task = new Task("Task", "Task", LocalDateTime.of
                (2012, 1, 1, 3, 0), 80);
        taskManager.objectTask(task);
        assertNotNull(taskManager.getTask(1), "Задача не найдена.");
        assertEquals(task, taskManager.getTask(1), "Задачи не совпадают");
    }

    @Test
    public void addNewEpic() {
        Epic epic = new Epic("Task", "Task", LocalDateTime.of
                (2012, 1, 1, 3, 0), 80);
        taskManager.objectEpic(epic);
        assertNotNull(taskManager.getEpic(1), "Задача не найдена.");
        assertEquals(epic, taskManager.getEpic(1), "Задачи не совпадают");
    }


    @Test
    public void addSubTask() {
        Epic epic = new Epic("Task", "Task", LocalDateTime.of
                (2012, 1, 1, 3, 0), 80);
        taskManager.objectEpic(epic);
        Subtask subtask = new Subtask("Task", "Task", LocalDateTime.of
                (2012, 1, 1, 3, 0), 80);
        taskManager.objectSubTask(subtask, 1);
        assertNotNull(taskManager.getSubtask(2), "Задача не найдена.");
        assertEquals(subtask, taskManager.getSubtask(2), "Задачи не совпадают");
    }

    @Test
    public void writeTask() {
        Task task = new Task("Task", "Task", LocalDateTime.of
                (2012, 1, 1, 3, 0), 80);
        taskManager.objectTask(task);
        Task task1 = new Task("Task1", "Task1", LocalDateTime.of
                (1999, 1, 1, 3, 0), 80);
        taskManager.objectTask(task1);
        List<Task> taskList = new ArrayList<>();
        taskList.add(task);
        taskList.add(task1);
        assertEquals(taskList, taskManager.writeTask(), "Задачи не выводятся");
    }

    @Test
    public void writeEpic() {
        Epic epic = new Epic("Task", "Task", LocalDateTime.of
                (2012, 1, 1, 3, 0), 80);
        taskManager.objectEpic(epic);
        Epic epic1 = new Epic("Task", "Task", LocalDateTime.of
                (1999, 1, 1, 3, 0), 80);
        taskManager.objectEpic(epic1);
        List<Task> taskList = new ArrayList<>();
        taskList.add(epic);
        taskList.add(epic1);
        assertEquals(taskList, taskManager.writeEpic(), "Задачи не выводятся");
    }

    @Test
    public void writeSubTask() {
        Epic epic = new Epic("Task", "Task", LocalDateTime.of
                (2012, 1, 1, 3, 0), 80);
        taskManager.objectEpic(epic);
        Subtask subtask = new Subtask("Task", "Task", LocalDateTime.of
                (2012, 1, 1, 3, 0), 80);
        taskManager.objectSubTask(subtask, 1);
        Subtask subtask1 = new Subtask("Task", "Task", LocalDateTime.of
                (1999, 1, 1, 3, 0), 80);
        taskManager.objectSubTask(subtask1, 1);
        List<Task> taskList = new ArrayList<>();
        taskList.add(subtask);
        taskList.add(subtask1);
        assertEquals(taskList, taskManager.writeSubTask(), "Задачи не выводятся");
    }

    @Test
    public void getTask() {
        Task task = new Task("Task", "Task", LocalDateTime.of
                (2012, 1, 1, 3, 0), 80);
        taskManager.objectTask(task);
        assertEquals(task, taskManager.getTask(1), "Задача не выводятся");
    }

    @Test
    public void getEpic() {
        Epic epic = new Epic("Task", "Task", LocalDateTime.of
                (2012, 1, 1, 3, 0), 80);
        taskManager.objectEpic(epic);
        assertEquals(epic, taskManager.getEpic(1), "Задача не выводятся");
    }

    @Test
    public void getSubTask() {
        Epic epic = new Epic("Task", "Task", LocalDateTime.of
                (2012, 1, 1, 3, 0), 80);
        taskManager.objectEpic(epic);
        Subtask subtask = new Subtask("Task", "Task", LocalDateTime.of
                (2012, 1, 1, 3, 0), 80);
        taskManager.objectSubTask(subtask, 1);
        assertEquals(subtask, taskManager.getSubtask(2), "Задача не выводятся");
    }

    @Test
    public void removeTask() {
        Task task = new Task("Task", "Task", LocalDateTime.of
                (2012, 1, 1, 3, 0), 80);
        taskManager.objectTask(task);
        taskManager.removeTaskIdentifier(1);
        assertEquals(null, taskManager.getTask(1), "Задача не удалена");
    }

    @Test
    public void removeEpic() {
        Epic epic = new Epic("Task", "Task", LocalDateTime.of
                (2012, 1, 1, 3, 0), 80);
        taskManager.objectEpic(epic);
        taskManager.removeEpicIdentifier(1);
        assertEquals(null, taskManager.getEpic(1), "Задача не удалена");
    }

    @Test
    public void removeSubTask() {
        Epic epic = new Epic("Task", "Task", LocalDateTime.of
                (2012, 1, 1, 3, 0), 80);
        taskManager.objectEpic(epic);
        Subtask subtask = new Subtask("Task", "Task", LocalDateTime.of
                (1999, 1, 1, 3, 0), 80);
        taskManager.objectSubTask(subtask, 1);
        taskManager.removeSubTaskEpicIdentifier(2);
        assertEquals(null, taskManager.getSubtask(2), "Задача не удалена");
    }

    @Test
    public void clearTask() {
        Task task = new Task("Task", "Task", LocalDateTime.of
                (2012, 1, 1, 3, 0), 80);
        taskManager.objectTask(task);
        Task task1 = new Task("Task", "Task", LocalDateTime.of
                (1999, 1, 1, 3, 0), 80);
        taskManager.objectTask(task1);
        taskManager.clearTask();
        List<Task> taskList = new ArrayList<>();
        assertEquals(taskList, taskManager.writeTask(), "Задачи не удалены");
    }

    @Test
    public void clearEpic() {
        Epic epic = new Epic("Task", "Task", LocalDateTime.of
                (2012, 1, 1, 3, 0), 80);
        taskManager.objectEpic(epic);
        Epic epic1 = new Epic("Task", "Task", LocalDateTime.of
                (2012, 1, 1, 3, 0), 80);
        taskManager.objectEpic(epic1);
        taskManager.clearEpic();
        List<Epic> taskList = new ArrayList<>();
        assertEquals(taskList, taskManager.writeEpic(), "Задачи не удалены");
    }

    @Test
    public void clearSubTask() {
        Epic epic = new Epic("Task", "Task", LocalDateTime.of
                (2012, 1, 1, 3, 0), 80);
        taskManager.objectEpic(epic);
        Subtask subtask = new Subtask("Task", "Task", LocalDateTime.of
                (2012, 1, 1, 3, 0), 80);
        taskManager.objectSubTask(subtask, 1);
        Subtask subtask1 = new Subtask("Task", "Task", LocalDateTime.of
                (1999, 1, 1, 3, 0), 80);
        taskManager.objectSubTask(subtask1, 1);
        taskManager.clearSubTask();
        List<Subtask> taskList = new ArrayList<>();
        assertEquals(taskList, taskManager.writeSubTask(), "Задачи не удалены");
    }

    @Test
    public void updateTask() {
        Task task = new Task("Task", "Task", LocalDateTime.of
                (2012, 1, 1, 3, 0), 80);
        taskManager.objectTask(task);
        taskManager.updateTask(1, new Task("Task1", "Task1", LocalDateTime.of
                (1999, 1, 1, 3, 0), 80), Enum.Status.DONE);
        Task task1 = new Task("Task1", "Task1", LocalDateTime.of
                (1999, 1, 1, 3, 0), 80);
        task1.setStatusTask(Enum.Status.DONE);
        task1.setId(1);
        assertEquals(task1, taskManager.getTask(1), "Задача не перезаписана");
    }

    @Test
    public void updateEpic() {
        Epic epic = new Epic("Task", "Task", LocalDateTime.of
                (2012, 1, 1, 3, 0), 80);
        taskManager.objectEpic(epic);
        taskManager.updateEpic(1, new Epic("Task1", "Task1", LocalDateTime.of
                (1999, 1, 1, 3, 0), 80));
        Epic epic1 = new Epic("Task1", "Task1", LocalDateTime.of
                (1999, 1, 1, 3, 0), 80);
        epic1.setId(1);
        epic1.setStatusTask(Enum.Status.NEW);
        assertEquals(epic1, taskManager.getEpic(1), "Задача не перезаписана");
    }

    @Test
    public void updateSubTask() {
        Epic epic = new Epic("Task", "Task", LocalDateTime.of
                (2012, 1, 1, 3, 0), 80);
        taskManager.objectEpic(epic);
        Subtask subtask = new Subtask("Task", "Task", LocalDateTime.of
                (2012, 1, 1, 3, 0), 80);
        taskManager.objectSubTask(subtask, 1);
        taskManager.updateSubTask(2, new Subtask("Task1", "Task1", LocalDateTime.of
                (1999, 1, 1, 3, 0), 80), Enum.Status.DONE);
        Subtask subtask1 = new Subtask("Task1", "Task1", LocalDateTime.of
                (1999, 1, 1, 3, 0), 80);
        subtask1.setId(2);
        subtask1.setIdEpic(1);
        subtask1.setStatusTask(Enum.Status.DONE);
        assertEquals(subtask1, taskManager.getSubtask(2), "Задача не перезаписана");
    }


    @Test
    public void writeSubTaskEpicId() {
        Epic epic = new Epic("Task", "Task", LocalDateTime.of
                (2012, 1, 1, 3, 0), 80);
        taskManager.objectEpic(epic);
        Subtask subtask = new Subtask("Task", "Task", LocalDateTime.of
                (2012, 1, 1, 3, 0), 80);
        taskManager.objectSubTask(subtask, 1);
        Subtask subtask1 = new Subtask("Task", "Task", LocalDateTime.of
                (1999, 1, 1, 3, 0), 80);
        taskManager.objectSubTask(subtask1, 1);
        List<Subtask> taskList = new ArrayList<>();
        taskList.add(subtask);
        taskList.add(subtask1);
        assertEquals(taskList, taskManager.writeSubTaskEpicIdentifier(1), "Подзадачи не выведены");
    }

    @Test
    public void existenceEpic() {
        Epic epic = new Epic("Task", "Task", LocalDateTime.of
                (2012, 1, 1, 3, 0), 80);
        taskManager.objectEpic(epic);
        Subtask subtask = new Subtask("Task", "Task", LocalDateTime.of
                (2012, 1, 1, 3, 0), 80);
        taskManager.objectSubTask(subtask, 1);
        assertEquals(1, subtask.getIdEpic(), "Подзадача не имеет эпика");
    }

    @Test
    public void statusEpic() {
        taskManager.objectEpic(new Epic("Epic", "Epic",
                null, 80));
        taskManager.objectSubTask(new Subtask("Subtask", "Subtask", LocalDateTime.of
                (2012, 1, 1, 3, 0), 80), 1);
        taskManager.updateSubTask(2, new Subtask("Subtask", "Subtask", LocalDateTime.of
                (2012, 1, 1, 3, 0), 80), Enum.Status.DONE);
        taskManager.objectSubTask(new Subtask("Subtask", "Subtask", LocalDateTime.of
                (2010, 1, 1, 3, 0), 60), 1);
        taskManager.updateSubTask(3, new Subtask("Subtask", "Subtask", LocalDateTime.of
                (2010, 1, 1, 3, 0), 60), Enum.Status.DONE);
        assertEquals(Enum.Status.DONE, taskManager.getEpic(1).getStatusTask(), "Неверный статус эпика");
    }

    @Test
    public void emptyGetTask() {
        assertEquals(null, taskManager.getTask(1), "Ошибка вывода при пустом списке задач");
    }

    @Test
    public void emptyGetEpic() {
        assertEquals(null, taskManager.getEpic(1), "Ошибка вывода при пустом списке задач");
    }

    @Test
    public void emptyGetSubTask() {
        assertEquals(null, taskManager.getSubtask(1), "Ошибка вывода при пустом списке задач");
    }

    @Test
    public void emptyWriteTask() {
        List<Task> taskList = new ArrayList<>();
        assertEquals(taskList, taskManager.writeTask(), "Ошибка вывода при пустом списке задач");
    }

    @Test
    public void emptyWriteEpic() {
        List<Epic> taskList = new ArrayList<>();
        assertEquals(taskList, taskManager.writeEpic(), "Ошибка вывода при пустом списке задач");
    }

    @Test
    public void emptyWriteSubTask() {
        List<Subtask> taskList = new ArrayList<>();
        assertEquals(taskList, taskManager.writeSubTask(), "Ошибка вывода при пустом списке задач");
    }

    @Test
    public void emptyWriteSubTaskEpicId() {
        taskManager.objectEpic(new Epic("Epic", "Epic",
                null, 80));
        List<Subtask> taskList = new ArrayList<>();
        assertEquals(taskList, taskManager.writeSubTaskEpicIdentifier(1), "Ошибка вывода при пустом списке задач");
    }

    @Test
    public void emptyRemoveTask() {
        taskManager.removeTaskIdentifier(1);
        assertEquals(null, taskManager.getTask(1), "Ошибка удаления несуществующей задачи");
    }

    @Test
    public void emptyUpdateTask() {
        taskManager.updateTask(1, new Task("Task", "Task", LocalDateTime.of
                (2012, 1, 1, 3, 0), 80), Enum.Status.DONE);
        assertEquals(null, taskManager.getTask(1), "Ошибка при замене несуществующей задачи");
    }

    @Test
    public void emptyClearTask() {
        taskManager.clearTask();
        List<Task> taskList = new ArrayList<>();
        assertEquals(taskList, taskManager.writeTask(), "Ошибка при пустом списке задач");
    }

    @Test
    public void emptyClearEpic() {
        taskManager.clearEpic();
        List<Task> taskList = new ArrayList<>();
        assertEquals(taskList, taskManager.writeEpic(), "Ошибка при пустом списке задач");
    }

    @Test
    public void emptyClearSubTask() {
        taskManager.clearSubTask();
        List<Task> taskList = new ArrayList<>();
        assertEquals(taskList, taskManager.writeSubTask(), "Ошибка при пустом списке задач");
    }

    @Test
    public void emptyRemoveEpic() {
        taskManager.removeEpicIdentifier(1);
        assertEquals(null, taskManager.getEpic(1), "Ошибка удаления несуществующей задачи");
    }

    @Test
    public void emptyUpdateEpic() {
        taskManager.updateEpic(1, new Epic("Task", "Task", LocalDateTime.of
                (2012, 1, 1, 3, 0), 80));
        assertEquals(null, taskManager.getEpic(1), "Ошибка при замене несуществующей задачи");
    }

    @Test
    public void emptyUpdateSubTask() {
        taskManager.updateSubTask(1, new Subtask("Task", "Task", LocalDateTime.of
                (2012, 1, 1, 3, 0), 80), Enum.Status.DONE);
        assertEquals(null, taskManager.getSubtask(1), "Ошибка при замене несуществующей задачи");
    }

    @Test
    public void emptyRemoveSubTask() {
        taskManager.removeSubTaskEpicIdentifier(1);
        assertEquals(null, taskManager.getSubtask(1), "Ошибка удаления несуществующей задачи");
    }

}