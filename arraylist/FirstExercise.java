package arraylist;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Scanner;

/**
 * Write a program which take a list of elements from the user and take unique elements only. Sort and print these
 * elements.
 */
public class FirstExercise {
    public static void main(String[] args) {

        ArrayList<Integer> list = new ArrayList<Integer>();
        Scanner sc = new Scanner(System.in);
        int n;
        System.out.println("Enter the 10 elements of list: ");
        for (int i = 0; i < 10; i++) {
            n = sc.nextInt();
            if (!list.contains(n)) {
                list.add(n);
            }

        }
        Collections.sort(list);
        System.out.println("Unique Sorted List: " + list);
    }
}
