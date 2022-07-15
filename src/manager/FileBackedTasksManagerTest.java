package manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Task;

import java.io.File;
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
    public void updateTaskManager() {
        taskManager = createTaskManager();
        super.updateTaskManager();
    }

    @Test
    public void toStringTest() {
        Task task = new Task("!!!!!", "&&&&&&", LocalDateTime.of
                (2010, 1, 1, 3, 0), 0);
        taskManager.objectTask(task);
        String stringTask = taskManager.toString(task);

        assertEquals(stringTask, "1, TASK, !!!!!, NEW, &&&&&&, 2010-01-01T03:00, 0, ", "Строки не совпадают");
    }

}