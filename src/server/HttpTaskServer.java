package server;

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

import Interface.TaskManager;
import manager.Managers;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

public class HttpTaskServer {
    private static final int PORT = 8080;
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    private HttpServer httpServer = HttpServer.create();
    public TaskManager manager;

    {
        try {
            manager = Managers.getDefault();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public HttpTaskServer() throws IOException {
        httpServer.bind(new InetSocketAddress(PORT), 0);
        httpServer.createContext("/tasks", new Handler());

    }

    public void start() {
        httpServer.start();
    }

    public void stop() {
        httpServer.stop(1);
    }


    public class Handler implements HttpHandler {
        private GsonBuilder gsonBuilder = new GsonBuilder();
        private Gson gson = gsonBuilder.create();


        @Override
        public void handle(HttpExchange httpExchange) throws IOException {

            try {
                String path = httpExchange.getRequestURI().getPath();
                String method = httpExchange.getRequestMethod();
                OutputStream os = httpExchange.getResponseBody();
                InputStream inputStream = httpExchange.getRequestBody();
                switch (method) {
                    case "GET":
                        if (path.endsWith("/task/")) {
                            if (!httpExchange.getRequestURI().getQuery().isEmpty()) {
                                httpExchange.sendResponseHeaders(200, 0);
                                String tasksGson = gsonBuilder.create().toJson(manager.getTask(Integer.parseInt(httpExchange.getRequestURI().getQuery().split("=")[1])));
                                os.write(tasksGson.getBytes(DEFAULT_CHARSET));
                                os.close();
                            } else {
                                httpExchange.sendResponseHeaders(200, 0);
                                String tasksGson = gsonBuilder.create().toJson(manager.writeTask());
                                os.write(tasksGson.getBytes(DEFAULT_CHARSET));
                                os.close();
                            }
                        }
                        if (path.endsWith("/epic/")) {
                            if (!httpExchange.getRequestURI().getQuery().isEmpty()) {
                                httpExchange.sendResponseHeaders(200, 0);
                                String tasksGson = gsonBuilder.create().toJson(manager.getEpic(Integer.parseInt(httpExchange.getRequestURI().getQuery().split("=")[1])));
                                os.write(tasksGson.getBytes(DEFAULT_CHARSET));
                                os.close();
                            }
                            httpExchange.sendResponseHeaders(200, 0);
                            String tasksGson = gsonBuilder.create().toJson(manager.writeEpic());
                            os.write(tasksGson.getBytes(DEFAULT_CHARSET));
                            os.close();
                        }
                        if (path.endsWith("/subTask/")) {
                            if (!httpExchange.getRequestURI().getQuery().isEmpty()) {
                                httpExchange.sendResponseHeaders(200, 0);
                                String tasksGson = gsonBuilder.create().toJson(manager.getSubtask(Integer.parseInt(httpExchange.getRequestURI().getQuery().split("=")[1])));
                                os.write(tasksGson.getBytes(DEFAULT_CHARSET));
                                os.close();
                            }
                            httpExchange.sendResponseHeaders(200, 0);
                            String tasksGson = gsonBuilder.create().toJson(manager.writeSubTask());
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
                            gsonBuilder.setPrettyPrinting();
                            String body = new String(inputStream.readAllBytes(), DEFAULT_CHARSET);
                            System.out.println(body);
                            Task task = gsonBuilder.create().fromJson(body, Task.class);
                            manager.objectTask(task);
                            inputStream.close();
                        }
                        if (path.endsWith("/epic/")) {
                            httpExchange.sendResponseHeaders(201, 0);
                            gsonBuilder.setPrettyPrinting();
                            String body = new String(inputStream.readAllBytes(), DEFAULT_CHARSET);
                            Epic epic = gsonBuilder.create().fromJson(body, Epic.class);
                            manager.objectEpic(epic);
                            inputStream.close();
                        }
                        if (path.endsWith("/subTask/")) {
                            httpExchange.sendResponseHeaders(201, 0);
                            gsonBuilder.setPrettyPrinting();
                            String body = new String(inputStream.readAllBytes(), DEFAULT_CHARSET);
                            Subtask subtask = gsonBuilder.create().fromJson(body, Subtask.class);
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
                        gsonBuilder.setPrettyPrinting();
                        gson = gsonBuilder.create();
                        String body = new String(inputStream.readAllBytes(), DEFAULT_CHARSET);
                        Task task = gson.fromJson(body, Task.class);
                        manager.updateTask(Integer.parseInt(httpExchange.getRequestURI().getQuery().split("=")[1]), task, task.getStatusTask());
                        inputStream.close();
                        break;
                }
            } catch (IOException e) {
                e.getMessage();
            } finally {
                httpExchange.close();
            }
        }
    }
}


