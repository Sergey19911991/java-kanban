package main;

import server.HttpTaskServer;
import server.KVServer;
import tasks.Task;

import java.io.IOException;
import java.time.LocalDateTime;


public class Main {
    public static void main(String[] args) throws IOException {
        KVServer server = new KVServer();
        server.start();
        HttpTaskServer server1 = new HttpTaskServer();
        server1.start();
        server1.manager.objectTask(new Task("Task", "Task", LocalDateTime.of
                (2010, 1, 1, 3, 0), 0));
        server1.stop();
        HttpTaskServer server2 = new HttpTaskServer();
        server2.start();
    }
}
