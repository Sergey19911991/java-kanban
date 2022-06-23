package manager;


import tasks.Task;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

public class InMemoryHistoryManager implements HistoryManager {
    private Map<Integer, Node> nodeHistory = new HashMap<>();
    private Node head;
    private Node tail;
    private int size = 0;

    @Override
    public List<Task> getHistory() {
        return getTasks();
    }

    @Override
    public void remove(int id) {
        if (nodeHistory.containsKey(id)) {
            removeNode(nodeHistory.get(id));
            nodeHistory.remove(id);
        }
    }

    @Override
    public void add(Task task) {
        remove(task.getId());
        nodeHistory.put(task.getId(), linkLast(task));
    }

    private Node linkLast(Task element) {
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


    private List<Task> getTasks() {
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

    private void removeNode(Node task) {
        if (task.prev == null) {
            head = task.next;
        } else {
            task.prev.next = task.next;
        }

        if (task.next == null) {
            tail = task.prev;
        } else {
            task.next.prev = task.prev;
        }
        --size;
    }
}

class Node {
    Object data;
    Node next;
    Node prev;

    Node(Node prev, Object data, Node next) {
        this.data = data;
        this.next = next;
        this.prev = prev;
    }
}


