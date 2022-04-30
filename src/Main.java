public class Main {

    public static void main(String[] args) {
        Manager manager = new Manager();
        manager.objectTask(new Task("Переезд","Собрать коробки"));
        manager.objectTask(new Task("Накормить собаку","Утром и вечером"));
        manager.objectEpic(new Epic("Поменять работу", "Чтобы больше была зарплата"));
        manager.objectSubTask(new Subtask("Уволиться","С прежней работы"),0);
        manager.objectSubTask(new Subtask("Искать работу","Всеми способами"),0);
       manager.objectEpic(new Epic("Пойти в магазин", "Купить еды"));
        manager.objectSubTask(new Subtask("Написать список товаров","на бумаге"),1);
        manager.writeTask();
        manager.writeEpic();
        manager.writeSubTask();
        System.out.println();
        manager.updateTask(0,new Task("Переезд заграницу","Куда-то"),"IN_PROGRESS");
        manager.updateSubTask(0,new Subtask("Сбежать с работы","Срочно"),"IN_PROGRESS");
        manager.updateEpic(0,new Epic("Накормить кошку","Днем"));
        manager.writeTask();
        manager.writeEpic();
        manager.writeSubTask();
        System.out.println();
        manager.removeSubTaskEpicIdentifier(0);
        manager.removeTaskIdentifier(0);
        manager.removeEpicIdentifier(1);
        manager.writeTask();
        manager.writeEpic();
        manager.writeSubTask();

    }
}
