package main;

import manager.HTTPTaskManager;
import manager.KVServer;
import manager.Managers;
import manager.TaskManager;
import tasks.Enum;
import tasks.Task;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.TreeSet;
import tasks.Epic;
import tasks.Subtask;

public class Main {
    public static void main(String[] args) throws IOException{
        KVServer server =  new KVServer();
        server.start();
        TaskManager manager = Managers.getDefault();

        manager.objectTask(new Task("!!!!!", "&&&&&&", LocalDateTime.of
                (2010, 1, 1, 3, 0), 0));
        manager.getTask(1);
        manager.objectEpic(new Epic("Поменять работу", "Чтобы больше была зарплата", null, 0));
        manager.getEpic(2);
        manager.objectSubTask(new Subtask("Искать работу", "Всеми способами", LocalDateTime.of
                (2010, 1, 1, 4, 0), 80), 2);
        manager.objectSubTask(new Subtask("Искать работу", "Всеми способами", LocalDateTime.of
                (1999, 1, 1, 2, 0), 80), 2);
        manager.updateSubTask(4,new Subtask("Искать работу", "Всеми способами", LocalDateTime.of
                (1999, 1, 1, 2, 0), 80), Enum.Status.DONE);

        HTTPTaskManager newManager =  HTTPTaskManager.loadFromServer("http://localhost:8078/");
        System.out.println(newManager.writeTask());
        System.out.println(newManager.writeEpic());
        System.out.println(newManager.writeSubTask());
        System.out.println(newManager.getHistory());
        System.out.println(newManager.getPrioritizedTasks());
    }
    }
