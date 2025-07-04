package arraylist;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Write a program to sort an arraylist in ascending order.
 *
 * For now I have used 2 for loops to check 1 element with each next elements in arraylist
 * which takes O(n^2) time complexity in all cases and O(1) in space complexity.
 */
public class SortArrayListAscending {

    public static void main(String[] args) {
        System.out.print("Enter the size of the array: ");
        Scanner sc = new Scanner(System.in);
        int size = sc.nextInt();
        ArrayList<Integer> list = new ArrayList<>(size);

        for (int i = 0; i < size; i++) {
            list.add(sc.nextInt());
        }

        for(int i = 0; i < size; i++) {
            for(int j = i+1; j < size; j++) {
                if(list.get(i) > list.get(j)) {
                    int temp = list.get(i);
                    list.set(i, list.get(j));
                    list.set(j, temp);
                }
            }
        }
        System.out.println(list);
    }

}
