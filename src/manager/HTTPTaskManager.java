package manager;


import exceptions.ManagerSaveException;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class HTTPTaskManager extends FileBackedTasksManager {
    private static final String key = "1";
    private final String url;
    private static KVTaskClient client;

    public HTTPTaskManager(String url) throws IOException {
        this.url = url;
        client = new KVTaskClient(url);
    }

    @Override
    public void save() {
        String json = "";
        try {
            for (Task i : super.writeTask()) {
                json = json + toString(i);
                json = json + System.lineSeparator();
            }
            for (Epic i : super.writeEpic()) {
                json = json + toString(i);
                json = json + System.lineSeparator();
            }
            for (Subtask i : super.writeSubTask()) {
                json = json + toString(i);
                json = json + System.lineSeparator();
            }
            json = json + System.lineSeparator();
            json = json + toString(historyManager);
            client.put(key, json);
        } catch (IOException e) {
            throw new ManagerSaveException("Произошла проблема с сохранением в файл FileBackedTasksManager"
                    + e.getMessage());
        }
    }


    public static HTTPTaskManager loadFromServer(String url) throws IOException {
        String historyFile = client.load(key);
        HTTPTaskManager newTaskManager = new HTTPTaskManager(url);
        String[] historyFile1 = historyFile.split(System.lineSeparator());
        for (int i = 0; i < historyFile1.length; i++) {
            if (!historyFile1[i].isEmpty()) {
                newTaskManager.fromString(historyFile1[i]);
            } else {
                for (Integer j : fromStringHistory(historyFile1[i + 1])) {
                    if (newTaskManager.taskHashMap.containsKey(j)) {
                        newTaskManager.getTask(j);
                    } else if (newTaskManager.epicHashMap.containsKey(j)) {
                        newTaskManager.getEpic(j);
                    } else if (newTaskManager.subTaskHashMap.containsKey(j)) {
                        newTaskManager.getSubtask(j);
                    }
                }
                break;
            }
        }
        return newTaskManager;

    }

}
