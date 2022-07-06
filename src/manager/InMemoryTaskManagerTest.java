package manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager> {

    @Override
    public InMemoryTaskManager createTaskManager(){
        return new InMemoryTaskManager(){
            @Override
            public void fromString(String value) {
            }
        };
    }
    @BeforeEach
    public void updateTaskManager() {
        taskManager = createTaskManager();
        super.updateTaskManager();
    }

    @Test
    public void addTask(){
        super.addTask();
    }




}
