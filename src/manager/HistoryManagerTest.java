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
        HistoryManager historyManager = Managers.getDefaultHistory();
        Task task = new Task("!!!!!", "&&&&&&", LocalDateTime.of
                (2010, 1, 1, 3, 0), 0);
        task.setId(1);
        historyManager.add(task);
        List<Task> listHistory = new ArrayList<>();
        listHistory.add(task);
        assertEquals(listHistory, historyManager.getHistory(), "История не совпадает");
    }

    @Test
    public void historyTestAdd() {
        HistoryManager historyManager = Managers.getDefaultHistory();
        Task task = new Task("!!!!!", "&&&&&&", LocalDateTime.of
                (2010, 1, 1, 3, 0), 0);
        historyManager.add(task);
        assertEquals(1, historyManager.getHistory().size(), "Задача не добавлена");
    }

    @Test
    public void historyTestRemoveStart() {
        HistoryManager historyManager = Managers.getDefaultHistory();
        Task task = new Task("!!!!!", "&&&&&&", LocalDateTime.of
                (2010, 1, 1, 3, 0), 0);
        task.setId(1);
        historyManager.add(task);
        Task task1 = new Task("!&&&", "&&&&&&", LocalDateTime.of
                (1999, 1, 1, 3, 0), 0);
        task1.setId(2);
        historyManager.add(task1);
        Task task2 = new Task("!&&&", "&&&&&&", LocalDateTime.of
                (1998, 1, 1, 3, 0), 0);
        task2.setId(3);
        historyManager.add(task2);
        historyManager.remove(1);
        assertEquals(2, historyManager.getHistory().size(), "Задача не удалена");
        assertEquals(2, historyManager.getHistory().get(0).getId(), "Ошибка при удалении первой(начало) задачи");
        assertEquals(3, historyManager.getHistory().get(1).getId(), "Ошибка при удалении первой(начало) задачи");
    }

    @Test
    public void emptyHistoryTest() {
        HistoryManager historyManager = Managers.getDefaultHistory();
        List<Task> listHistory = new ArrayList<>();
        assertEquals(listHistory, historyManager.getHistory(), "Список истории не пустой");
    }

    @Test
    public void duplicationHistoryTest() {
        HistoryManager historyManager = Managers.getDefaultHistory();
        Task task = new Task("!!!!!", "&&&&&&", LocalDateTime.of
                (2010, 1, 1, 3, 0), 0);
        historyManager.add(task);
        historyManager.add(task);
        assertEquals(1, historyManager.getHistory().size(), "Задача добавлена два раза");
    }

    @Test
    public void historyTestRemoveMiddle() {
        HistoryManager historyManager = Managers.getDefaultHistory();
        Task task = new Task("!!!!!", "&&&&&&", LocalDateTime.of
                (2010, 1, 1, 3, 0), 0);
        task.setId(1);
        historyManager.add(task);
        Task task1 = new Task("!&&&", "&&&&&&", LocalDateTime.of
                (1999, 1, 1, 3, 0), 0);
        task1.setId(2);
        historyManager.add(task1);
        Task task2 = new Task("!&&&", "&&&&&&", LocalDateTime.of
                (1998, 1, 1, 3, 0), 0);
        task2.setId(3);
        historyManager.add(task2);
        historyManager.remove(2);
        assertEquals(2, historyManager.getHistory().size(), "Задача не удалена");
        assertEquals(1, historyManager.getHistory().get(0).getId(), "Ошибка при удалении второй(середина) задачи");
        assertEquals(3, historyManager.getHistory().get(1).getId(), "Ошибка при удалении второй(середина) задачи");
    }

    @Test
    public void historyTestRemoveEnd() {
        HistoryManager historyManager = Managers.getDefaultHistory();
        Task task = new Task("!!!!!", "&&&&&&", LocalDateTime.of
                (2010, 1, 1, 3, 0), 0);
        task.setId(1);
        historyManager.add(task);
        Task task1 = new Task("!&&&", "&&&&&&", LocalDateTime.of
                (1999, 1, 1, 3, 0), 0);
        task1.setId(2);
        historyManager.add(task1);
        Task task2 = new Task("!&&&", "&&&&&&", LocalDateTime.of
                (1998, 1, 1, 3, 0), 0);
        task2.setId(3);
        historyManager.add(task2);
        historyManager.remove(3);
        assertEquals(2, historyManager.getHistory().size(), "Задача не удалена");
        assertEquals(1, historyManager.getHistory().get(0).getId(), "Ошибка при удалении второй(середина) задачи");
        assertEquals(2, historyManager.getHistory().get(1).getId(), "Ошибка при удалении второй(середина) задачи");
    }

}