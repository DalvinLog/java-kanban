package ru.practicum.tasktracker.manager;

import ru.practicum.tasktracker.model.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class InMemoryHistoryManager implements HistoryManager {

    private final Map<Integer, Node> taskLinkedHashMap;

    private Node head;
    private Node tail;

    public InMemoryHistoryManager() {
        taskLinkedHashMap = new HashMap<>();
    }

    @Override
    public void add(Task task) {
        remove(task.getId());

        final Node oldTail = tail;
        final Node newNode = new Node(tail, task, null);
        tail = newNode;
        if (oldTail == null)
            head = newNode;
        else
            oldTail.next = newNode;

        taskLinkedHashMap.put(task.getId(), newNode);
    }

    @Override
    public void remove(int id) {
        if (!taskLinkedHashMap.containsKey(id)) {
            return;
        }

        final Node node = taskLinkedHashMap.get(id);
        final Node nextNode = node.next;
        final Node prevNode = node.prev;

        if (nextNode == null && prevNode == null) {
            taskLinkedHashMap.remove(id);
            return;
        }

        if (nextNode == null) {
            prevNode.next = null;
            tail = prevNode;
        } else if (prevNode == null) {
            nextNode.prev = null;
            head = nextNode;
        } else {
            nextNode.prev = prevNode;
            prevNode.next = nextNode;
        }

        taskLinkedHashMap.remove(id);
    }

    @Override
    public void clear() {
        taskLinkedHashMap.clear();
    }

    @Override
    public List<Task> getHistory() {
        List<Task> taskList = new ArrayList<>();

        if (head == null) {
            return taskList;
        }
        Node element = head;

        while (element != null) {
            taskList.add(element.data);
            element = element.next;
        }

        return taskList;
    }

    private static class Node {

        Task data;
        Node next;
        Node prev;

        public Node(Node prev, Task data, Node next) {
            this.data = data;
            this.next = next;
            this.prev = prev;
        }
    }
}


