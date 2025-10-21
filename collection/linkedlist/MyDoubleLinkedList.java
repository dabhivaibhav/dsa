package collection.linkedlist;

public class MyDoubleLinkedList {

    private Node head;
    private Node tail;
    private int size;

    public MyDoubleLinkedList() {
        this.size = 0;
    }

    public void insert(int data) {
        Node node = new Node(data);
        if (head == null) {
            head = node;
        } else {
            tail.next = node;
            node.previous = tail;
        }
        tail = node;
        size++;
    }

    //insert a node at the beginning of LinkedList
    public void insertFirst(int val) {
        Node node = new Node(val);
        node.next = head;
        node.previous = null;
        if (head == null) {
            tail = node;
            head = tail;
        }
        if (head != null) {
            head.previous = node;
        }
        head = node;
        size++;
    }

    //insert a node at the end of LinkedList
    public void insertLast(int val) {
        Node node = new Node(val);
        if (tail == null) {
            head = node;
            tail = node;
        } else {
            tail.next = node;
            node.previous = tail;
        }
        tail = node;
        size++;
    }


    public void deleteHead() {
        if (head == null) {
            System.out.println("Linked list is empty.");
            return;
        }
        if (head.next == null) {
            head = null;
            tail = null;
            return;
        }
        head = head.next;
        head.previous = null;
        size--;
    }

    public void deleteTail() {
        if (tail == null) {
            System.out.println("Linked list is empty.");
            return;
        }
        if (tail.previous == null) {
            head = null;
            tail = null;
            return;
        }

        tail = tail.previous;
        tail.next = null;
        size--;
    }

    public void deleteByIndex(int index) {

        if (index < 1 || index > size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
        if (index == 1) {
            deleteHead();
            return;
        }
        if (index == size) {
            deleteTail();
            return;
        }

        Node temp = head;
        for (int i = 1; i < index; i++) temp = temp.next;

        temp.previous.next = temp.next;
        temp.next.previous = temp.previous;
        size--;
    }

    //displays data of LinkedList
    public void showList() {
        Node node = head;
        while (node != null) {
            System.out.print(node.data + " ");
            node = node.next;
        }
        System.out.println();
    }

    //displays data of LinkedList in reverse order
    public void showListReverse() {
        Node node = tail;
        while (node != null) {
            System.out.print(node.data + " ");
            node = node.previous;
        }
        System.out.println();
    }

    public void reverse() {

        if (head == null || head.next == null) return;

        Node curr = head;

        // Flip next <-> previous for every node
        while (curr != null) {
            Node next = curr.next;
            curr.next = curr.previous;
            curr.previous = next;
            curr = next; // move along the original "next" chain
        }

        Node oldHead = head;
        head = tail;
        tail = oldHead;

    }

    private class Node {

        int data;
        Node next;
        Node previous;

        public Node(int data) {
            this.data = data;
        }

        public Node(int data, Node next, Node previous) {
            this.data = data;
            this.next = next;
            this.previous = previous;
        }

    }
}
