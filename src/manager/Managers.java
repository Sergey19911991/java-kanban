package manager;

import java.io.File;
import java.util.List;

public class Managers {
    public static TaskManager getDefault() {
        return new InMemoryTaskManager() {
            @Override
            public List<Integer> fromStringHistory(String value) {
                return null;
            }

            @Override
            public void fromString(String value) {

            }

            @Override
            public FileBackedTasksManager loadFromFile(File file) {
                return null;
            }
        };
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

    public static TaskManager getFileBackedTasksManager(File file) {
        return new FileBackedTasksManager(file);
    }

}
