package manager;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import tasks.Enum;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

public class HttpTaskServer {
    private static final int PORT = 8080;
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    private static Gson gson = new Gson();
    private static TaskManager manager;

    static {
        try {
            manager = new HTTPTaskManager("http://localhost:8078/");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static GsonBuilder gsonBuilder = new GsonBuilder();

    public static void main(String[] args) throws IOException {
        HttpServer httpServer = HttpServer.create();

        httpServer.bind(new InetSocketAddress(PORT), 0);
        httpServer.createContext("/tasks", new Handler());
        httpServer.start(); // запускаем сервер

        System.out.println("HTTP-сервер запущен на " + PORT + " порту!");
    }

    static class Handler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            String path = httpExchange.getRequestURI().getPath();
            String method = httpExchange.getRequestMethod();
            OutputStream os = httpExchange.getResponseBody();
            InputStream inputStream = httpExchange.getRequestBody();
            switch (method) {
                case "GET":
                    if (path.endsWith("/task/")) {
                        if (!httpExchange.getRequestURI().getQuery().isEmpty()) {
                            httpExchange.sendResponseHeaders(200, 0);
                            String tasksGson = gson.toJson(manager.getTask(Integer.parseInt(httpExchange.getRequestURI().getQuery().split("=")[1])));
                            os.write(tasksGson.getBytes(DEFAULT_CHARSET));
                            os.close();
                        } else {
                            httpExchange.sendResponseHeaders(200, 0);
                            String tasksGson = gson.toJson(manager.writeTask());
                            os.write(tasksGson.getBytes(DEFAULT_CHARSET));
                            os.close();
                        }
                    }
                    if (path.endsWith("/epic/")) {
                        if (!httpExchange.getRequestURI().getQuery().isEmpty()) {
                            httpExchange.sendResponseHeaders(200, 0);
                            String tasksGson = gson.toJson(manager.getEpic(Integer.parseInt(httpExchange.getRequestURI().getQuery().split("=")[1])));
                            os.write(tasksGson.getBytes(DEFAULT_CHARSET));
                            os.close();
                        }
                        httpExchange.sendResponseHeaders(200, 0);
                        String tasksGson = gson.toJson(manager.writeEpic());
                        os.write(tasksGson.getBytes(DEFAULT_CHARSET));
                        os.close();
                    }
                    if (path.endsWith("/subTask/")) {
                        if (!httpExchange.getRequestURI().getQuery().isEmpty()) {
                            httpExchange.sendResponseHeaders(200, 0);
                            String tasksGson = gson.toJson(manager.getSubtask(Integer.parseInt(httpExchange.getRequestURI().getQuery().split("=")[1])));
                            os.write(tasksGson.getBytes(DEFAULT_CHARSET));
                            os.close();
                        }
                        httpExchange.sendResponseHeaders(200, 0);
                        String tasksGson = gson.toJson(manager.writeSubTask());
                        os.write(tasksGson.getBytes(DEFAULT_CHARSET));
                        os.close();
                    }
                    if (path.endsWith("/tasks/history")) {
                        httpExchange.sendResponseHeaders(200, 0);
                        String tasksGson = gson.toJson(manager.getHistory());
                        os.write(tasksGson.getBytes(DEFAULT_CHARSET));
                        os.close();
                    }
                    break;
                case "POST":
                    if (path.endsWith("/task/")) {
                        httpExchange.sendResponseHeaders(201, 0);
                        gsonBuilder.registerTypeAdapter(Task.class, new TaskAdapter());
                        gsonBuilder.setPrettyPrinting();
                        gson = gsonBuilder.create();
                        String body = new String(inputStream.readAllBytes(), DEFAULT_CHARSET);
                        Task task = gson.fromJson(body, Task.class);

                        manager.objectTask(task);

                        inputStream.close();

                    }
                    if (path.endsWith("/epic/")) {
                        httpExchange.sendResponseHeaders(201, 0);
                        gsonBuilder.registerTypeAdapter(Epic.class, new EpicAdapter());
                        gsonBuilder.setPrettyPrinting();
                        gson = gsonBuilder.create();
                        String body = new String(inputStream.readAllBytes(), DEFAULT_CHARSET);
                        Epic epic = gson.fromJson(body, Epic.class);
                        manager.objectEpic(epic);
                        inputStream.close();
                    }
                    if (path.endsWith("/subTask/")) {
                        httpExchange.sendResponseHeaders(201, 0);
                        gsonBuilder.registerTypeAdapter(Subtask.class, new SubTaskAdapter());
                        gsonBuilder.setPrettyPrinting();
                        gson = gsonBuilder.create();
                        String body = new String(inputStream.readAllBytes(), DEFAULT_CHARSET);
                        Subtask subtask = gson.fromJson(body, Subtask.class);
                        manager.objectSubTask(subtask, subtask.getIdEpic());
                        inputStream.close();
                    }
                    break;
                case "DELETE":
                    if (path.endsWith("/task/")) {
                        if (!httpExchange.getRequestURI().getQuery().isEmpty()) {
                            httpExchange.sendResponseHeaders(200, 0);
                            manager.removeTaskIdentifier(Integer.parseInt(httpExchange.getRequestURI().getQuery().split("=")[1]));
                        } else {
                            httpExchange.sendResponseHeaders(200, 0);
                            manager.clearTask();
                        }
                    }
                    if (path.endsWith("/epic/")) {
                        if (!httpExchange.getRequestURI().getQuery().isEmpty()) {
                            httpExchange.sendResponseHeaders(200, 0);
                            manager.removeEpicIdentifier(Integer.parseInt(httpExchange.getRequestURI().getQuery().split("=")[1]));
                        } else {
                            httpExchange.sendResponseHeaders(200, 0);
                            manager.clearEpic();
                        }
                    }
                    if (path.endsWith("/subTask/")) {
                        if (!httpExchange.getRequestURI().getQuery().isEmpty()) {
                            httpExchange.sendResponseHeaders(200, 0);
                            manager.removeSubTaskEpicIdentifier(Integer.parseInt(httpExchange.getRequestURI().getQuery().split("=")[1]));
                        } else {
                            httpExchange.sendResponseHeaders(200, 0);
                            manager.clearSubTask();
                        }
                    }
                    break;
                case "PUT":
                    httpExchange.sendResponseHeaders(201, 0);
                    gsonBuilder.registerTypeAdapter(Task.class, new TaskAdapter());
                    gsonBuilder.setPrettyPrinting();
                    gson = gsonBuilder.create();
                    String body = new String(inputStream.readAllBytes(), DEFAULT_CHARSET);
                    Task task = gson.fromJson(body, Task.class);
                    manager.updateTask(Integer.parseInt(httpExchange.getRequestURI().getQuery().split("=")[1]), task, task.getStatusTask());
                    inputStream.close();
                    break;
            }
            httpExchange.close();
        }
    }
}


class TaskAdapter extends TypeAdapter<Task> {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Override
    public Task read(JsonReader reader) throws IOException {
        reader.beginObject();
        String fieldname = null;
        Task task = new Task("null", "null", null, 0);

        while (reader.hasNext()) {
            JsonToken token = reader.peek();
            if (token.equals(JsonToken.NAME)) {
                fieldname = reader.nextName();
            }
            if ("nameTask".equals(fieldname)) {
                token = reader.peek();
                task.setName(reader.nextString());
            }
            if ("descriptionTask".equals(fieldname)) {
                token = reader.peek();
                task.setDescription(reader.nextString());
            }
            if ("duration".equals(fieldname)) {
                token = reader.peek();
                task.setDuration(reader.nextInt());
            }
            if ("startTime".equals(fieldname)) {
                token = reader.peek();
                task.setStartTime(LocalDateTime.parse(reader.nextString(), formatter));
            }
            if ("statusTask".equals(fieldname)) {
                token = reader.peek();
                task.setStatusTask(Enum.Status.valueOf(reader.nextString()));
            }
        }
        reader.endObject();
        return task;
    }

    @Override
    public void write(JsonWriter writer, Task task) throws IOException {
        writer.beginObject();
        writer.name("nameTask");
        writer.value(task.getNameTask());
        writer.name("descriptionTask");
        writer.value(task.getDescriptionTask());
        writer.name("duration");
        writer.value(task.getDuration());
        writer.name("id");
        writer.value(task.getId());
        writer.name("startTime");
        writer.value(task.getStartTime().format(formatter));
        writer.name("statusTask");
        writer.value(String.valueOf(task.getStatusTask()));
        writer.endObject();
    }
}

class EpicAdapter extends TypeAdapter<Epic> {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Override
    public Epic read(JsonReader reader) throws IOException {
        reader.beginObject();
        String fieldname = null;
        Epic epic = new Epic("null", "null", null, 0);

        while (reader.hasNext()) {
            JsonToken token = reader.peek();
            if (token.equals(JsonToken.NAME)) {
                fieldname = reader.nextName();
            }
            if ("nameTask".equals(fieldname)) {
                token = reader.peek();
                epic.setName(reader.nextString());
            }
            if ("descriptionTask".equals(fieldname)) {
                token = reader.peek();
                epic.setDescription(reader.nextString());
            }
        }
        reader.endObject();
        return epic;
    }

    @Override
    public void write(JsonWriter writer, Epic epic) throws IOException {
        writer.beginObject();
        writer.name("id");
        writer.value(epic.getId());
        writer.name("nameTask");
        writer.value(epic.getNameTask());
        writer.name("descriptionTask");
        writer.value(epic.getDescriptionTask());
        writer.name("duration");
        writer.value(epic.getDuration());
        writer.name("startTime");
        System.out.println("ззззззз");
        if (epic.getStartTime() == null) {
            System.out.println("ззззззз");
            writer.value("null");
        } else {
            writer.value(epic.getStartTime().format(formatter));
        }

        writer.name("idSubTask");
        writer.value(String.valueOf(epic.getIdSubTask()));
        writer.endObject();

    }
}


class SubTaskAdapter extends TypeAdapter<Subtask> {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Override
    public Subtask read(JsonReader reader) throws IOException {
        reader.beginObject();
        String fieldname = null;
        Subtask subTask = new Subtask("null", "null", null, 0);

        while (reader.hasNext()) {
            JsonToken token = reader.peek();
            if (token.equals(JsonToken.NAME)) {
                fieldname = reader.nextName();
            }
            if ("nameTask".equals(fieldname)) {
                token = reader.peek();
                subTask.setName(reader.nextString());
            }
            if ("descriptionTask".equals(fieldname)) {
                token = reader.peek();
                subTask.setDescription(reader.nextString());
            }
            if ("duration".equals(fieldname)) {
                token = reader.peek();
                subTask.setDuration(reader.nextInt());
            }
            if ("startTime".equals(fieldname)) {
                token = reader.peek();
                subTask.setStartTime(LocalDateTime.parse(reader.nextString(), formatter));
            }
            if ("idEpic".equals(fieldname)) {
                token = reader.peek();
                subTask.setIdEpic(reader.nextInt());
            }
        }
        reader.endObject();
        return subTask;
    }

    @Override
    public void write(JsonWriter writer, Subtask subTask) throws IOException {
        writer.beginObject();
        writer.name("nameTask");
        writer.value(subTask.getNameTask());
        writer.name("descriptionTask");
        writer.value(subTask.getDescriptionTask());
        writer.name("duration");
        writer.value(subTask.getDuration());
        writer.name("id");
        writer.value(subTask.getId());
        writer.name("startTime");
        writer.value(subTask.getStartTime().format(formatter));
        writer.name("idEpic");
        writer.value(subTask.getIdEpic());
        writer.endObject();
    }
}
