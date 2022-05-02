package Main;

import Epic.Epic;
import Manager.Manager;
import SubTask.Subtask;
import Task.Task;

public class Main {

    public static void main(String[] args) {
        Manager manager = new Manager();
        manager.objectTask(new Task("Переезд", "Собрать коробки"));
        manager.objectTask(new Task("Накормить собаку", "Утром и вечером"));
        manager.objectEpic(new Epic("Поменять работу", "Чтобы больше была зарплата"));
        manager.objectSubTask(new Subtask("Уволиться", "С прежней работы"), 2);
        manager.objectSubTask(new Subtask("Искать работу", "Всеми способами"), 2);
        manager.objectEpic(new Epic("Пойти в магазин", "Купить еды"));
        manager.objectSubTask(new Subtask("Написать список товаров", "на бумаге"), 5);
        manager.writeTask();
        manager.writeEpic();
        manager.writeSubTask();
        System.out.println();
        manager.updateTask(0, new Task("Переезд заграницу", "Куда-то"), Task.Status.IN_PROGRESS);
        manager.updateSubTask(3, new Subtask("Сбежать с работы", "Срочно"), Task.Status.IN_PROGRESS);
        manager.updateEpic(2, new Epic("Накормить кошку", "Днем"));
        manager.writeTask();
        manager.writeEpic();
        manager.writeSubTask();
        System.out.println();
        manager.removeSubTaskEpicIdentifier(3);
        manager.removeTaskIdentifier(0);
        manager.removeEpicIdentifier(2);
        manager.writeTask();
        manager.writeEpic();
        manager.writeSubTask();

    }
}
