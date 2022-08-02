package manager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import exceptions.ManagerSaveException;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.io.IOException;


public class HTTPTaskManager extends FileBackedTasksManager {
    private static GsonBuilder gsonBuilder = new GsonBuilder();
    private static Gson gson = gsonBuilder.create();
    private final String url;
    private KVTaskClient client;
    private static String task;
    private static String epic;
    private static String subTask;
    private static String history;

    public HTTPTaskManager(String url) throws IOException {
        this.url = url;
        client = new KVTaskClient(url);
        if (!(task == null)) {
            String task = client.load("task");
            String[] task1 = task.split(System.lineSeparator());
            gsonBuilder.setPrettyPrinting();
            for (int i = 0; i < task1.length; i++) {
                Task task3 = gsonBuilder.create().fromJson(task1[i], Task.class);
                taskHashMap.put(task3.getId(), task3);
            }
        }
        if (!(epic == null)) {
            String epic = client.load("epic");
            String[] epic1 = epic.split(System.lineSeparator());
            gsonBuilder.setPrettyPrinting();
            for (int i = 0; i < epic1.length; i++) {
                Epic epic3 = gsonBuilder.create().fromJson(epic1[i], Epic.class);
                epicHashMap.put(epic3.getId(), epic3);
            }
        }
        if (!(subTask == null)) {
            String subTask = client.load("subTask");
            String[] subTask1 = subTask.split(System.lineSeparator());
            gsonBuilder.setPrettyPrinting();
            for (int i = 0; i < subTask1.length; i++) {
                Subtask subTask3 = gsonBuilder.create().fromJson(subTask1[i], Subtask.class);
                subTaskHashMap.put(subTask3.getId(), subTask3);
            }
        }
        if (!(history == null)) {
            String history = client.load("history");
            String[] history1 = history.split(", ");
            for (int i = 0; i < history1.length; i++) {
                Integer a = Integer.parseInt(history1[i]);
                if (taskHashMap.containsKey(a)) {
                    getTask(a);
                } else if (epicHashMap.containsKey(a)) {
                    getEpic(a);
                } else if (subTaskHashMap.containsKey(a)) {
                    getSubtask(a);
                }
            }
        }
    }

    @Override
    public void save() {
        try {
            task = null;
            gson = gsonBuilder.create();
            if (!taskHashMap.isEmpty()) {
                task = "";
                for (Task i : super.writeTask()) {
                    task = task + gsonBuilder.serializeNulls().create().toJson(i);
                    task = task + System.lineSeparator();
                }
            }
            if (!(task == null)) {
                client.put("task", task);
            }
            epic = null;
            gson = gsonBuilder.create();
            if (!epicHashMap.isEmpty()) {
                epic = "";
                //gsonBuilder.registerTypeAdapter(Epic.class, new EpicAdapter());
                for (Epic i : super.writeEpic()) {
                    epic = epic + gsonBuilder.create().toJson(i);
                    //System.out.println("ччччччч");
                    epic = epic + System.lineSeparator();
                }
                System.out.println(epic);
            }
            if (!(epic == null)) {
                client.put("epic", epic);
            }
            subTask = null;
            if (!subTaskHashMap.isEmpty()) {
                subTask = "";
                for (Subtask i : super.writeSubTask()) {
                    subTask = subTask + gson.toJson(i);
                    subTask = subTask + System.lineSeparator();
                }
                System.out.println(subTask);
            }
            if (!(subTask == null)) {
                client.put("subTask", subTask);
            }
            history = null;
            if (!historyManager.getHistory().isEmpty()) {
                history = "";
                for (Task j : historyManager.getHistory()) {
                    history = history + gson.toJson(j.getId()) + ", ";
                }
            }
            if (!(history == null)) {
                client.put("history", history);
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Произошла проблема с сохранением на сервер"
                    + e.getMessage());
        }
    }
}
