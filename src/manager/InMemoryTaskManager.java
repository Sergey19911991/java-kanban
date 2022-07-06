package manager;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import tasks.Enum;


import java.util.*;

public abstract class InMemoryTaskManager implements TaskManager {
    protected final HashMap<Integer, Task> taskHashMap = new HashMap<>();
    protected final HashMap<Integer, Epic> epicHashMap = new HashMap<>();
    protected final HashMap<Integer, Subtask> subTaskHashMap = new HashMap<>();

    private int numberTask;

    protected final HistoryManager historyManager = Managers.getDefaultHistory();

    Comparator<Task> taskComp = new Comparator<Task>() {
        @Override
        public int compare(Task o1, Task o2) {
            return o1.getStartTime().compareTo(o2.getStartTime());

        }
    };
    protected final TreeSet<Task> treeTask = new TreeSet(taskComp);
    protected final TreeSet<Task> treeSubTask = new TreeSet(taskComp);



    //Создание задачи
    @Override
    public void objectTask(Task task) {
        task.setStatusTask(Enum.Status.NEW);
        task.setId(generateId());
        taskHashMap.put(task.getId(), task);
        validatorTimeTasks(task);
        treeTask.add(task);
    }

    //Вывод всех задач
    @Override
    public List<Task> writeTask() {
        List<Task> task = new ArrayList<>();
        for (Integer keySetTask : taskHashMap.keySet()) {
            task.add(taskHashMap.get(keySetTask));
        }
        return task;
    }

    //Удаление всех задач
    @Override
    public void clearTask() {
        taskHashMap.clear();
        treeTask.clear();
    }

    //Вывод задачи по идентификатору
    @Override
    public Task getTask(int identifier) {
        Task task = taskHashMap.get(identifier);
        if (task != null) {
            historyManager.add(task);
        }
        return task;
    }

    // Удаление задачи по идентификатору
    @Override
    public void removeTaskIdentifier(int identifier) {
        treeTask.remove(taskHashMap.get(identifier));
        historyManager.remove(taskHashMap.get(identifier).getId());
        taskHashMap.remove(identifier);
    }

    // Замена задачи
    @Override
    public void updateTask(int identifier, Task task, Enum.Status newStatus) {
        treeTask.remove(taskHashMap.get(identifier));
        validatorTimeTasks(task);
        treeTask.add(task);
        taskHashMap.remove(identifier);
        taskHashMap.put(identifier, task);
        task.setStatusTask(newStatus);
        task.setId(identifier);
    }

    //создание эпика
    @Override
    public void objectEpic(Epic epic) {
        epic.setStatusTask(Enum.Status.NEW);
        epic.setId(generateId());
        epicHashMap.put(epic.getId(), epic);
    }

    //вывод всех эпиков
    @Override
    public List<Epic> writeEpic() {
        List<Epic> epic = new ArrayList<>();
        for (Integer keySetTask : epicHashMap.keySet()) {
            epic.add(epicHashMap.get(keySetTask));
        }
        return epic;
    }

    //удаление всех эпиков
    @Override
    public void clearEpic() {
        epicHashMap.clear();
        subTaskHashMap.clear();
    }

    //удаление эпика по идентификатору
    @Override
    public void removeEpicIdentifier(int identifier) {
        for (Integer id : epicHashMap.get(identifier).getIdSubTask()) {
            historyManager.remove(id);
            subTaskHashMap.remove(id);
        }
        historyManager.remove(epicHashMap.get(identifier).getId());
        epicHashMap.remove(identifier);
    }

    //вывод эпика по идентификатору
    @Override
    public Epic getEpic(int identifier) {
        Epic epic = epicHashMap.get(identifier);
        if (epic != null) {
            historyManager.add(epic);
        }
        return epic;
    }

    //замена эпика
    @Override
    public void updateEpic(int identifier, Epic epic) {
        Enum.Status status = epicHashMap.get(identifier).getStatusTask();
        List<Integer> idSubtask1;
        idSubtask1 = epicHashMap.get(identifier).getIdSubTask();
        epicHashMap.remove(identifier);
        epicHashMap.put(identifier, epic);
        epic.setId(identifier);
        epic.setStatusTask(status);
        epic.setIdSubTask(idSubtask1);
    }

    //создание подзадачи
    @Override
    public void objectSubTask(Subtask subTask, int identifier) {
        subTask.setId(generateId());
        subTaskHashMap.put(subTask.getId(), subTask);
        subTask.setStatusTask(Enum.Status.NEW);
        subTask.setIdEpic(identifier);
        epicHashMap.get(identifier).getIdSubTask().add(subTask.getId());
        long sumDuration=0;
        for(Integer i:epicHashMap.get(identifier).getIdSubTask()){
            sumDuration = sumDuration + subTaskHashMap.get(i).getDuration();
        }
        epicHashMap.get(identifier).setDuration(sumDuration);
        validatorSubTimeTasks(subTask);
        treeSubTask.add(subTask);
        epicHashMap.get(identifier).setStartTime(treeSubTask.first().getStartTime());
        epicHashMap.get(identifier).setEndTime(treeSubTask.last().getEndTime());
    }

    //вывод всех подзадач
    @Override
    public List<Subtask> writeSubTask() {
        List<Subtask> subTask = new ArrayList<>();
        for (Integer keySetTask : subTaskHashMap.keySet()) {
            subTask.add(subTaskHashMap.get(keySetTask));
        }
        return subTask;
    }

    // удаление всех подзадач
    @Override
    public void clearSubTask() {
        subTaskHashMap.clear();
        for (Integer keySetEpic : epicHashMap.keySet()) {
            epicHashMap.get(keySetEpic).setStatusTask(Enum.Status.NEW);
            epicHashMap.get(keySetEpic).setDuration(0);
            epicHashMap.get(keySetEpic).getIdSubTask().clear();
            epicHashMap.get(keySetEpic).setEndTime(null);
            epicHashMap.get(keySetEpic).setStartTime(null);
        }
        treeSubTask.clear();
    }

    //замена подзадачи
    @Override
    public void updateSubTask(int identifier, Subtask subtask, Enum.Status newStatus) {
        treeSubTask.remove(subTaskHashMap.get(identifier));
        validatorSubTimeTasks(subtask);
        treeSubTask.add(subtask);
        int numberEpic = subTaskHashMap.get(identifier).getIdEpic();
        subTaskHashMap.remove(identifier);
        subTaskHashMap.put(identifier, subtask);
        subtask.setStatusTask(newStatus);
        subtask.setIdEpic(numberEpic);
        statusEpic(numberEpic);
        subtask.setId(identifier);
        epicHashMap.get(numberEpic).setStartTime(treeSubTask.first().getStartTime());
        long sumDuration=0;
        for(Integer i:epicHashMap.get(numberEpic).getIdSubTask()){
            sumDuration = sumDuration + subTaskHashMap.get(i).getDuration();
        }
        epicHashMap.get(numberEpic).setDuration(sumDuration);
        epicHashMap.get(numberEpic).setEndTime(treeSubTask.last().getEndTime());
    }

    // вывод подзадачи по идентификатору
    @Override
    public Subtask getSubtask(int identifier) {
        Subtask subTask = subTaskHashMap.get(identifier);
        if (subTask != null) {
            historyManager.add(subTask);
        }
        return subTask;
    }

    //удаление подзадачи по идентификатору
    @Override
    public void removeSubTaskEpicIdentifier(int identifier) {
        treeSubTask.remove(subTaskHashMap.get(identifier));
        int numberEpic = subTaskHashMap.get(identifier).getIdEpic();
        historyManager.remove(subTaskHashMap.get(identifier).getId());
        epicHashMap.get(numberEpic).setDuration(epicHashMap.get(numberEpic).getDuration()-subTaskHashMap.get(identifier)
                .getDuration());
        epicHashMap.get(numberEpic).setStartTime(treeSubTask.first().getStartTime());
        epicHashMap.get(numberEpic).setEndTime(treeSubTask.last().getEndTime());
        subTaskHashMap.remove(identifier);
        int k = 0;
        for (int i = 0; i < epicHashMap.get(numberEpic).getIdSubTask().size(); i++) {
            if (epicHashMap.get(numberEpic).getIdSubTask().get(i) == identifier) {
                k = i;
            }
        }
        epicHashMap.get(numberEpic).getIdSubTask().remove(k);
        statusEpic(numberEpic);
    }

    //вывод подзадач определенного эпика
    @Override
    public List<Subtask> writeSubTaskEpicIdentifier(int identifier) {
        List<Subtask> subTask = new ArrayList<>();
        for (Integer keySetTask : epicHashMap.get(identifier).getIdSubTask()) {
            subTask.add(subTaskHashMap.get(keySetTask));
        }
        return subTask;
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    //создание идентификатора
    private int generateId() {
        return numberTask = numberTask + 1;
    }

    //замена статуса эпика после замены или удаления подзадачи этого эпика
    private void statusEpic(int idEpicStatus) {
        int numberNew = 0;
        int numberDone = 0;
        for (Integer id : epicHashMap.get(idEpicStatus).getIdSubTask()) {
            if (subTaskHashMap.get(id).getStatusTask().equals(Enum.Status.DONE)) {
                numberDone = numberDone + 1;
            } else if (subTaskHashMap.get(id).getStatusTask().equals(Enum.Status.NEW)) {
                numberNew = numberNew + 1;
            }
        }
        if (numberDone == epicHashMap.get(idEpicStatus).getIdSubTask().size()) {
            epicHashMap.get(idEpicStatus).setStatusTask(Enum.Status.DONE);
        } else if (numberNew == epicHashMap.get(idEpicStatus).getIdSubTask().size()) {
            epicHashMap.get(idEpicStatus).setStatusTask(Enum.Status.NEW);
        } else {
            epicHashMap.get(idEpicStatus).setStatusTask(Enum.Status.IN_PROGRESS);
        }
    }

    public TreeSet getPrioritizedTasks(){
        return treeTask;
    }


    public TreeSet getPrioritizedSubTasks(){
        return treeSubTask;
    }

    private void validatorTimeTasks(Task task) {
        for (Task priorityTask : treeTask) {
            if ((task.getStartTime().isAfter(priorityTask.getStartTime()) &&
                    task.getStartTime().isBefore(priorityTask.getEndTime()))||(task.getEndTime().isAfter(priorityTask.getStartTime()) &&
                    task.getEndTime().isBefore(priorityTask.getEndTime()))) {
                throw new IllegalArgumentException("Даты пересекаются у задач с номерами: " + priorityTask.getId() + " и " + task.getId());
            }
        }
    }

    private void validatorSubTimeTasks(Subtask subtask) {
        for (Task priorityTask : treeSubTask) {
            if ((subtask.getStartTime().isAfter(priorityTask.getStartTime()) &&
                    subtask.getStartTime().isBefore(priorityTask.getEndTime()))||(subtask.getEndTime().isAfter(priorityTask.getStartTime()) &&
                    subtask.getEndTime().isBefore(priorityTask.getEndTime()))) {
                throw new IllegalArgumentException("Даты пересекаются у подзадач с номерами: " + priorityTask.getId() + " и " + subtask.getId());
            }
        }
    }

}

