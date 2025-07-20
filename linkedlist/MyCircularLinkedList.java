package linkedlist;

public class MyCircularLinkedList {

    private Node head;
    private int size;
    private Node tail;

    private class Node {
        int data;
        Node next;

        public Node(int data) {
            this.data = data;
        }

        public Node(int data, Node next) {
            this.data = data;
            this.next = next;
        }
    }

    public MyCircularLinkedList() {
        this.size = 0;
    }

    public void insert(int val) {
        Node node = new Node(val);
        if (head == null) {
            head = node;
            tail = head;
        } else {
            tail.next = node;
            tail = node;
        }
        tail.next = head;
        size++;
    }

    public void deleteByValue(int val) {
        if (head == null) {
            System.out.println("The list is empty");
            return;
        }
        if (head.data == val) {
            if(size ==1){
                head = null;
                tail = null;
                size--;
                return;
            }
            head = head.next;
            tail.next = head;
            size--;

        }
        Node node = head;
        while (node.next != head) {
            if (node.next.data == val) {
                node.next = node.next.next;
                size--;
                return;
            }
            node = node.next;
        }
    }

    public void showList() {
        Node node = head;
        if (head != null) {
            do {
                System.out.print(node.data + " ");
                node = node.next;
            } while (node != head);
        } else {
            System.out.println("The list is empty");
        }

    }
}
