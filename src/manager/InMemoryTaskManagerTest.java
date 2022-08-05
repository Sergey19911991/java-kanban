package manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager> {

    @Override
    public InMemoryTaskManager createTaskManager() {
        return new InMemoryTaskManager() {
            @Override
            public void fromString(String value) {
            }
        };
    }

    @BeforeEach
    public void updateTaskManager() throws IOException {
        taskManager = createTaskManager();
        super.updateTaskManager();
    }

    @Test
    public void addTask() {
        super.addTask();
    }

    @Test
    public void addEpic() {
        super.addNewEpic();
    }

    @Test
    public void addSubTask() {
        super.addTask();
    }

}
