package manager;

import tasks.Task;

import java.util.List;
import java.util.LinkedList;

public class InMemoryHistoryManager implements HistoryManager {
    private final LinkedList<Task> taskHistory = new LinkedList<>();
    static final int maxLength = 10;

    @Override
    public List<Task> getHistory() {
        return taskHistory;
    }

    @Override
    public void add(Task task) {
        if (taskHistory.size() == maxLength) {
            taskHistory.removeFirst();
        }
        taskHistory.add(task);
    }
}
