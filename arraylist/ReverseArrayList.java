package arraylist;

import java.util.ArrayList;
import java.util.Scanner;

/* Write a program to reverse an ArrayList in-place without using a second list */
public class ReverseArrayList {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the size of list: ");
        int size = sc.nextInt();
        ArrayList<Integer> list = new ArrayList<Integer>(size);
        System.out.println("Enter the " + size + " elements of list: ");
        for (int i = 0; i < size; i++) {
            list.add(sc.nextInt());
        }

        for (int i = 0; i < size / 2; i++) {
            int frontElement = list.get(i);
            int backElement = list.get(list.size() - i - 1);
            list.set(i, backElement);
            list.set(list.size() - i - 1, frontElement);
        }
        System.out.println("Reversed ArrayList" + list);

        // here we can use addAll method but the efficient way is parameterized constructor to create a new arraylist
        ArrayList<Integer> list1 = new ArrayList<>(list);
        System.out.println("List1" + list1);
        list.clear();
        System.out.println("List: " + list);
    }
}
