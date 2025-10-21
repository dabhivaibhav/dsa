package collection.linkedlist;

public class Main {


    public static void main(String[] args) {
        MyLinkedList list = new MyLinkedList();
        list.insert(10);
        list.insert(20);
        list.insert(30);
        list.insertFirst(50);
        list.insertLast(40);
        list.showList();

        MyDoubleLinkedList doubleLinkedList = new MyDoubleLinkedList();
        doubleLinkedList.insert(10);
        doubleLinkedList.insert(20);
        doubleLinkedList.insert(30);
        doubleLinkedList.insert(40);
        doubleLinkedList.showList();
        doubleLinkedList.reverse();
        doubleLinkedList.showList();
        doubleLinkedList.showListReverse();

        MyCircularLinkedList circularLinkedList = new MyCircularLinkedList();
        MyCircularLinkedList circularLinkedList1 = new MyCircularLinkedList();
        circularLinkedList.insert(10);
        circularLinkedList.insert(20);
        circularLinkedList.insert(30);
        circularLinkedList.insert(40);
        circularLinkedList.showList();
        System.out.println();
        circularLinkedList.deleteByValue(10);
        circularLinkedList.showList();
        System.out.println();
        circularLinkedList1.showList();
    }


}
