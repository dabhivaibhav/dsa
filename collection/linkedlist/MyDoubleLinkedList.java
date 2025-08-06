package collection.linkedlist;

public class MyDoubleLinkedList {

    private Node head;
    private Node tail;
    private int size;

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

    public MyDoubleLinkedList() {
        this.size = 0;
    }

    //insert a node at the beginning of LinkedList
    public void insertFirst(int val) {
        Node node = new Node(val);
        node.next = head;
        node.previous = null;
        if(head == null){
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
}
