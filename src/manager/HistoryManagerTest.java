package manager;


import org.junit.jupiter.api.Test;
import tasks.Task;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class HistoryManagerTest {

    @Test
    public void historyTest() {
        TaskManager taskManager = Managers.getDefault();
        Task task = new Task("!!!!!", "&&&&&&", LocalDateTime.of
                (2010, 1, 1, 3, 0), 0);
        taskManager.objectTask(task);
        Task task1 = taskManager.getTask(1);
        List<Task> taskList = new ArrayList<>();
        taskList.add(task1);
        assertEquals(taskList, taskManager.getHistory(), "История не совпадает");
    }
}