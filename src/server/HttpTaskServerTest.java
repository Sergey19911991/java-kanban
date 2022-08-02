package server;


import com.google.gson.GsonBuilder;
import tasks.Enum;
import tasks.Task;

import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.io.IOException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class HttpTaskServerTest {

    @Test
    public void createTask() throws IOException {
        KVServer server = new KVServer();
        server.start();
        HttpTaskServer server1 = new HttpTaskServer();
        server1.start();
        HttpClient client = HttpClient.newHttpClient();
        URI adress = URI.create("http://localhost:8080/" + "tasks/task/");
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Task task = new Task("Task", "Task", LocalDateTime.of
                (2010, 1, 1, 3, 0, 0, 0), 0);
        task.setStatusTask(Enum.Status.NEW);
        task.setId(1);
        String json = gsonBuilder.create().toJson(task);
        HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request = HttpRequest.newBuilder()
                .POST(body)
                .uri(adress)
                .build();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, handler);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(task, server1.manager.getTask(1), "Ошибка создания задачи");
        server.stop();
        server1.stop();
    }

    @Test
    public void getTask() throws IOException {
        KVServer server = new KVServer();
        server.start();
        HttpTaskServer server1 = new HttpTaskServer();
        server1.start();
        HttpClient client1 = HttpClient.newHttpClient();
        URI adress = URI.create("http://localhost:8080/tasks/task/?id=1");
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Task task = new Task("Task", "Task", LocalDateTime.of
                (2010, 1, 1, 3, 0), 0);
        task.setStatusTask(Enum.Status.NEW);
        task.setId(1);
        server1.manager.objectTask(task);
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(adress)
                .build();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> response = null;
        try {
            response = client1.send(request, handler);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(task, gsonBuilder.create().fromJson(response.body(), Task.class), "Ошибка создания задачи");
        server.stop();
        server1.stop();
    }


}