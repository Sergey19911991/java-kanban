package manager;

import Interface.TaskManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.HttpTaskServer;
import server.KVServer;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class HTTPTaskManagerTest extends FileBackedTasksManagerTest {


    @Test
    public void serverState() throws IOException {
        KVServer server = new KVServer();
        server.start();
        HttpTaskServer server1 = new HttpTaskServer();
        server1.start();
        server1.manager.objectTask(new Task("Task11111", "Task121212", LocalDateTime.of
                (2010, 1, 1, 3, 0), 0));
        server1.manager.objectTask(new Task("Task11111", "Task121212", LocalDateTime.of
                (1999, 1, 1, 3, 0), 0));
        server1.manager.getTask(1);
        server1.manager.objectEpic(new Epic("Task11111", "Task121212", LocalDateTime.of
                (1999, 1, 1, 3, 0), 0));
        server1.manager.objectSubTask(new Subtask("Task11111", "Task121212", LocalDateTime.of
                (1991, 1, 1, 3, 0), 0), 3);
        server1.manager.objectSubTask(new Subtask("Task11111", "Task121212", LocalDateTime.of
                (1995, 1, 1, 3, 0), 0), 3);
        server1.manager.getSubtask(5);
        server1.stop();
        HttpTaskServer server2 = new HttpTaskServer();
        server2.start();
        assertEquals(2, server2.manager.writeTask().size(), "Ошибка при считывании состояния с сервера");
        assertEquals(1, server2.manager.writeEpic().size(), "Ошибка при считывании состояния с сервера");
        assertEquals(2, server2.manager.writeSubTask().size(), "Ошибка при считывании состояния с сервера");
        assertEquals(2, server2.manager.getHistory().size(), "Ошибка при считывании состояния с сервера");
        server.stop();
        server2.stop();
    }


}