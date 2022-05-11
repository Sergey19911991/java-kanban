package manager;

import tasks.Task;

import java.util.List;
import java.util.LinkedList;

public class InMemoryHistoryManager implements HistoryManager {
    private final LinkedList<Task> taskHistory = new LinkedList<>();

    @Override
    public List<Task> getHistory() {
        return taskHistory;
    }

    @Override
    public void add(Task task) {
        if (taskHistory.size() == 10) {
            taskHistory.remove(0);
        }
        taskHistory.add(task);
    }
}
