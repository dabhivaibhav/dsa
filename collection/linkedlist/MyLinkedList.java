package collection.linkedlist;

import collection.linkedlist.medium_problems.MiddleOfLinkedList;

public class MyLinkedList {

    private Node head;
    private Node tail;
    private int size;

    public class Node {
        private int data;
        public Node next;

        public Node(int data) {
            this.data = data;
        }

        public Node(int data, Node next) {
            this.data = data;
            this.next = next;
        }

        public String toString(){
            return data+"";
        }
    }

    public MyLinkedList() {
        this.size = 0;
    }

    public int size() {
        return size;
    }

    //insert a node in order
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

    //insert a node at the beginning of the LinkedList
    public void insertFirst(int val) {
        Node node = new Node(val);
        node.next = head;
        head = node;
        if (tail == null) {
            tail = head;
        }
        size++;
    }

    //insert a node at the end of the LinkedList
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

    //Insert a node at a specific index in the LinkedList
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

    //Remove a node from the beginning of the LinkedList
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

    //Helper function to delete the last node in the Linkedlist
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

    //Remove a node from the end of the LinkedList
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

    //Remove a node at the specific index from the LinkedList
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

    //reset the LinkedList
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    //displays the data of the LinkedList
    public void showList() {
        Node node = head;
        while (node != null) {
            System.out.print(node.data + " ");
            node = node.next;
        }
        System.out.println();
    }

    public Node getHead(){
        return head;
    }
    public void findMiddleofLinkedList(MyLinkedList list){
     Node temp = head;
     Node slow = head;
     Node fast = head;
     while(fast!=null && fast.next!=null){
         slow = slow.next;
         fast = fast.next.next;
     }
     temp = slow;
    while(temp!=null){
        System.out.print(temp.data+" ");
        temp = temp.next;
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
