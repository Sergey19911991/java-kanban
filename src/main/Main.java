package main;

import manager.Managers;
import manager.TaskManager;
import tasks.Subtask;
import tasks.Epic;
import tasks.Task;

public class Main {
    public static void main(String[] args) {
        TaskManager manager = Managers.getDefault();
        manager.objectTask(new Task("Переезд", "Собрать коробки"));
        manager.objectTask(new Task("Накормить собаку", "Утром и вечером"));
        manager.objectEpic(new Epic("Поменять работу", "Чтобы больше была зарплата"));
        manager.objectSubTask(new Subtask("Уволиться", "С прежней работы"), 3);
        manager.objectSubTask(new Subtask("Искать работу", "Всеми способами"), 3);
        manager.objectEpic(new Epic("Пойти в магазин", "Купить еды"));
        manager.objectSubTask(new Subtask("Написать список товаров", "на бумаге"), 6);
        for (Object i : manager.writeTask()) {
            System.out.println(i);
        }
       for (Object i : manager.writeEpic()) {
            System.out.println(i);
        }
        for (Object i : manager.writeSubTask()) {
            System.out.println(i);
        }
        System.out.println();
        manager.getTask(1);
        System.out.println(manager.getHistory());
       manager.getTask(1);
        System.out.println(manager.getHistory());
       manager.getEpic(6);
        System.out.println(manager.getHistory());
        manager.getSubtask(4);
        System.out.println(manager.getHistory());
        manager.getEpic(6);
        System.out.println(manager.getHistory());
        manager.getTask(2);
        System.out.println(manager.getHistory());
        manager.getTask(1);
        System.out.println(manager.getHistory());
        manager.getTask(1);
        System.out.println(manager.getHistory());
    }
}