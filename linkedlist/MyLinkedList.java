package linkedlist;

public class MyLinkedList {

    private Node head;
    private Node tail;
    private int size;

    private class Node {
        private int data;
        private Node next;

        public Node(int data) {
            this.data = data;
        }

        public Node(int data, Node next) {
            this.data = data;
            this.next = next;
        }
    }

    public MyLinkedList() {
        this.size = 0;
    }

    public int size() {
        return size;
    }

    public void insert(int val) {
        Node node = new Node(val);

        if (head == null) {
            head = node;
            tail = node;
        } else {
            tail.next = node;
            tail = node;
        }
        size += 1;
    }

    public void insertFirst(int val) {
        Node node = new Node(val);
        node.next = head;
        head = node;
        if (tail == null) {
            tail = head;
        }
        size++;
    }

    public void insertLast(int val) {
        Node node = new Node(val);
        if (tail == null) {
            head = node;
            tail = node;
        } else {
            tail.next = node;
            tail = node;
        }
        size++;
    }

    public void insertAt(int index, int val) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }

        if (index == 0) {
            insertFirst(val);
            return;
        }

        if (index == size) {
            insertLast(val);
            return;
        }

        Node temp = head;
        for (int i = 1; i < index; i++) {
            temp = temp.next;
        }

        temp.next = new Node(val, temp.next);
        size++;
    }

    public void deleteFirst() {
        if (head == null) {
            System.out.println("The list is empty");
            return;
        }

        head = head.next;
        size--;

        if (head == null) {
            tail = null;
        }
    }

    public void deleteLastNode() {
        if (head == null || head.next == null) {
            deleteFirst();
            return;
        }

        Node node = head;
        for (int i = 0; i < size - 2; i++) {
            node = node.next;
        }

        node.next = null;
        tail = node;
        size--;
    }

    public void deleteLast() {
        if (tail == null) {
            System.out.println("The list is empty");
            return;
        }

        if (size <= 1) {
            deleteFirst();
            return;
        }

        deleteLastNode(); // size handled inside
    }

    public void deleteAt(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }

        if (index == 0) {
            deleteFirst();
            return;
        }

        if (index == size - 1) {
            deleteLastNode();
            return;
        }

        Node temp = head;
        for (int i = 1; i < index; i++) {
            temp = temp.next;
        }

        temp.next = temp.next.next;
        size--;
    }

    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    public void showList() {
        Node node = head;
        while (node != null) {
            System.out.print(node.data + " ");
            node = node.next;
        }
        System.out.println();
    }

    // Optional helper to get value at index
    public int get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }

        Node temp = head;
        for (int i = 0; i < index; i++) {
            temp = temp.next;
        }

        return temp.data;
    }
}
