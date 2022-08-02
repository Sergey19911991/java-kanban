package manager;

import org.junit.jupiter.api.Test;
import server.HttpTaskServer;
import server.KVServer;
import tasks.Epic;
import tasks.Subtask;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class HTTPTaskManagerTest {

    @Test
    public void noTask() throws IOException {
        KVServer server = new KVServer();
        server.start();
        HttpTaskServer server1 = new HttpTaskServer();
        server1.start();
        server1.stop();
        HttpTaskServer server2 = new HttpTaskServer();
        server2.start();
        assertEquals(0, server2.manager.writeTask().size(), "Ошибка при считывании состояния с сервера");
        assertEquals(0, server2.manager.writeEpic().size(), "Ошибка при считывании состояния с сервера");
        assertEquals(0, server2.manager.writeSubTask().size(), "Ошибка при считывании состояния с сервера");
        server.stop();
        server2.stop();
    }


    @Test
    public void noSubTask() throws IOException {
        KVServer server = new KVServer();
        server.start();
        HttpTaskServer server1 = new HttpTaskServer();
        server1.start();
        server1.manager.objectEpic(new Epic("Task", "Task", LocalDateTime.of
                (2012, 1, 1, 3, 0,0,0), 80));
        server1.stop();
        HttpTaskServer server2 = new HttpTaskServer();
        server2.start();
        assertEquals(0, server2.manager.writeSubTask().size(), "Ошибка при считывании файла");
        assertEquals(1, server2.manager.writeEpic().size(), "Ошибка при считывании состояния с сервера");
        server.stop();
        server2.stop();
    }

    @Test
    public void noHistory() throws IOException {
        KVServer server = new KVServer();
        server.start();
        HttpTaskServer server1 = new HttpTaskServer();
        server1.start();
        Epic epic = new Epic("Task", "Task", LocalDateTime.of
                (2012, 1, 1, 3, 0), 80);
        Subtask subtask = new Subtask("Task", "Task", LocalDateTime.of
                (2012, 1, 1, 3, 0), 80);
        Subtask subtask1 = new Subtask("Task", "Task", LocalDateTime.of
                (1999, 1, 1, 3, 0), 80);
        server1.manager.objectEpic(epic);
        server1.manager.objectSubTask(subtask,1);
        server1.manager.objectSubTask(subtask1,1);
        server1.stop();
        HttpTaskServer server2 = new HttpTaskServer();
        server2.start();
        assertEquals(0,server2.manager.getHistory().size(), "Ошибка при считывании состояния с сервера");
        server.stop();
        server2.stop();
    }

}