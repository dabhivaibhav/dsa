package collection.arraylist;

import java.util.ArrayList;
import java.util.Scanner;

public class RemoveDuplicates {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the number of elements in the collection.arraylist: ");
        int size = sc.nextInt();
        ArrayList<Integer> list = new ArrayList<Integer>(size);

        for (int i = 0; i < size; i++) {
            list.add(sc.nextInt());
        }

        for (int i = 0; i < list.size(); i++) {
            for(int j = i+1; j <= list.size(); j++) {
                if(list.get(i) == list.get(j)) {
                    list.remove(i);
                }
            }
        }

        System.out.println("The list is: "+ list);
    }
}
