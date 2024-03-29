package manager;

import Interface.HistoryManager;
import Interface.TaskManager;

import java.io.IOException;

public class Managers {
    public static TaskManager getDefault() throws IOException {
        return new HTTPTaskManager("http://localhost:8078/");
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }


}

