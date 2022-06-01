package manager;


import tasks.Task;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

public class InMemoryHistoryManager implements HistoryManager {
    public static Map<Integer, Node> nodeHistory = new HashMap<>();
    private Node head;
    private Node tail;
    private int size = 0;

    @Override
    public List<Task> getHistory() {
        return getTasks();
    }

    @Override
    public void remove(int id) {
        removeNode(nodeHistory.get(id));
    }

    @Override
    public void add(Task task) {
        if (nodeHistory.containsKey(task.getId())) {
            remove(task.getId());
        }
        nodeHistory.put(task.getId(), linkLast(task));
    }

    public Node linkLast(Task element) {
        final Node oldTail = tail;
        final Node newNode = new Node(oldTail, element, null);
        tail = newNode;
        if (oldTail == null) {
            head = newNode;
        } else {
            oldTail.next = newNode;
        }
        size++;
        return newNode;
    }


    public List<Task> getTasks() {
        List<Task> taskHistory = new ArrayList<>();
        Node a = head;
        int i = 1;
        while (i <= size) {
            taskHistory.add((Task) a.data);
            a = a.next;
            i++;
        }
        return taskHistory;
    }

    public void removeNode(Node task) {
        if (task.prev == null) {
            task.next.prev = null;
            head = task.next;
            --size;
        } else if (task.next == null) {
            task.prev.next = null;
            tail = task.prev;
            --size;
        } else {
            task.prev.next = task.next;
            task.next.prev = task.prev;
            --size;
        }
    }
}

class Node {
    public Object data;
    public Node next;
    public Node prev;

    public Node(Node prev, Object data, Node next) {
        this.data = data;
        this.next = next;
        this.prev = prev;
    }
}







