package manager;

import Interface.TaskManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.Subtask;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class FileBackedTasksManagerTest extends TaskManagerTest<FileBackedTasksManager> {

    @Override
    public FileBackedTasksManager createTaskManager() {
        return new FileBackedTasksManager(new File("test.txt")) {
            @Override
            public void fromString(String value) {
            }
        };
    }

    @BeforeEach
    public void updateTaskManager() throws IOException {
        taskManager = new FileBackedTasksManager(new File("test.txt"));
        super.updateTaskManager();
    }

    @AfterEach
    void deleteFile() {
        File file = new File("test.txt");
        file.delete();
    }

    @Test
    public void noTask() {
        File file = new File("test.txt");
        taskManager.save();
        TaskManager managerFile = taskManager.loadFromFile(file);
        assertEquals(0, managerFile.writeTask().size(), "Ошибка при считывании файла");
        assertEquals(0, managerFile.writeEpic().size(), "Ошибка при считывании файла");
        assertEquals(0, managerFile.writeSubTask().size(), "Ошибка при считывании файла");
    }

    @Test
    public void noHistory() {
        File file = new File("test.txt");
        Epic epic = new Epic("Task", "Task", LocalDateTime.of
                (2012, 1, 1, 3, 0), 80);
        taskManager.objectEpic(epic);
        Subtask subtask = new Subtask("Task", "Task", LocalDateTime.of
                (2012, 1, 1, 3, 0), 80);
        taskManager.objectSubTask(subtask, 1);
        Subtask subtask1 = new Subtask("Task", "Task", LocalDateTime.of
                (1999, 1, 1, 3, 0), 80);
        taskManager.objectSubTask(subtask1, 1);
        TaskManager managerFile = taskManager.loadFromFile(file);
        assertEquals(0, managerFile.getHistory().size(), "Ошибка при считывании файла");
    }

    @Test
    public void noSubTask() {
        File file = new File("test.txt");
        Epic epic = new Epic("Task", "Task", LocalDateTime.of
                (2012, 1, 1, 3, 0), 80);
        taskManager.objectEpic(epic);
        TaskManager managerFile = taskManager.loadFromFile(file);
        assertEquals(0, managerFile.writeSubTask().size(), "Ошибка при считывании файла");
        assertEquals(1, managerFile.writeEpic().size(), "Ошибка при считывании файла");
    }

}