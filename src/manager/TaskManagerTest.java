package manager;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tasks.Task;
import tasks.Epic;

import java.time.LocalDateTime;

abstract class TaskManagerTest <T extends TaskManager> {
    public T taskManager;

    public abstract T createTaskManager();

    @BeforeEach
    public void updateTaskManager() {
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
    public void addEpic() {
        Epic epic = new Epic("Task", "Task", LocalDateTime.of
                (2012, 1, 1, 3, 0), 80);
        taskManager.objectEpic(epic);
        assertNotNull(taskManager.getEpic(1), "Задача не найдена.");
        assertEquals(epic, taskManager.getEpic(1), "Задачи не совпадают");
    }

}