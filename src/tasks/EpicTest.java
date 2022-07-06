package tasks;

import static org.junit.jupiter.api.Assertions.*;
import manager.Managers;
import manager.TaskManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


import java.time.LocalDateTime;


class EpicTest {
@Test
    void statusNew() {
            TaskManager manager = Managers.getDefault();
            manager.objectEpic(new Epic("Epic", "Epic",
            null, 80));
            manager.objectSubTask(new Subtask("Subtask", "Subtask", LocalDateTime.of
            (2012, 1, 1, 3, 0), 80), 1);
            manager.objectSubTask(new Subtask("Subtask", "Subtask", LocalDateTime.of
            (2010, 1, 1, 3, 0), 60), 1);
            Assertions.assertEquals(Enum.Status.NEW, manager.getEpic(1).getStatusTask(), "Неверный статус эпика");
            }

@Test
    void statusDone() {
            TaskManager manager = Managers.getDefault();
            manager.objectEpic(new Epic("Epic", "Epic",
            null, 80));
            manager.objectSubTask(new Subtask("Subtask", "Subtask", LocalDateTime.of
            (2012, 1, 1, 3, 0), 80), 1);
            manager.updateSubTask(2,new Subtask("Subtask", "Subtask", LocalDateTime.of
            (2012, 1, 1, 3, 0), 80), Enum.Status.DONE);
            manager.objectSubTask(new Subtask("Subtask", "Subtask", LocalDateTime.of
            (2010, 1, 1, 3, 0), 60), 1);
            manager.updateSubTask(3,new Subtask("Subtask", "Subtask", LocalDateTime.of
            (2010, 1, 1, 3, 0), 60), Enum.Status.DONE);
            assertEquals(Enum.Status.DONE, manager.getEpic(1).getStatusTask(), "Неверный статус эпика");
            }

@Test
    void statusDoneNew() {
            TaskManager manager = Managers.getDefault();
            manager.objectEpic(new Epic("Epic", "Epic",
            null, 80));
            manager.objectSubTask(new Subtask("Subtask", "Subtask", LocalDateTime.of
            (2012, 1, 1, 3, 0), 80), 1);
            manager.objectSubTask(new Subtask("Subtask", "Subtask", LocalDateTime.of
            (2010, 1, 1, 3, 0), 60), 1);
            manager.updateSubTask(3,new Subtask("Subtask", "Subtask", LocalDateTime.of
            (2010, 1, 1, 3, 0), 60), Enum.Status.DONE);
            assertEquals(Enum.Status.IN_PROGRESS, manager.getEpic(1).getStatusTask(), "Неверный статус эпика");
            }


@Test
    void statusInProgress() {
            TaskManager manager = Managers.getDefault();
            manager.objectEpic(new Epic("Epic", "Epic",
            null, 80));
            manager.objectSubTask(new Subtask("Subtask", "Subtask", LocalDateTime.of
            (2012, 1, 1, 3, 0), 80), 1);
            manager.updateSubTask(2,new Subtask("Subtask", "Subtask", LocalDateTime.of
            (2012, 1, 1, 3, 0), 80), Enum.Status.IN_PROGRESS);
            manager.objectSubTask(new Subtask("Subtask", "Subtask", LocalDateTime.of
            (2010, 1, 1, 3, 0), 60), 1);
            manager.updateSubTask(3,new Subtask("Subtask", "Subtask", LocalDateTime.of
            (2010, 1, 1, 3, 0), 60), Enum.Status.IN_PROGRESS);
            assertEquals(Enum.Status.IN_PROGRESS, manager.getEpic(1).getStatusTask(), "Неверный статус эпика");
            }

@Test
    void statusEpic() {
            TaskManager manager = Managers.getDefault();
            manager.objectEpic(new Epic("Epic", "Epic",
            null, 80));
            assertEquals(Enum.Status.NEW, manager.getEpic(1).getStatusTask(), "Неверный статус эпика");
            }

            }