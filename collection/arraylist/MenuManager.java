package collection.arraylist;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Create a menu program with the following options:
 * 1. Add Element
 * 2. Remove Element
 * 3. Display Elements
 * 4. Exit
 * This program runs infinitely until the user chooses the fourth option.
 */
public class MenuManager {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        ArrayList<Integer> list = new ArrayList<Integer>();

        printMenu();
        int choice = sc.nextInt();
        while (choice < 1 || choice > 4) {
            System.out.println("Invalid choice please try again");
            choice = sc.nextInt();
        }
        while (choice == 2) {
            System.out.println("Can't remove element from an empty list");
            printMenu();
            choice = sc.nextInt();
        }
        while (choice != 4) {
            switch (choice) {
                case 1:
                    System.out.println("Enter the element to be added");
                    int element = sc.nextInt();
                    list.add(element);
                    break;

                case 2:
                    if(list.isEmpty()){
                        System.out.println("Can't remove element from an empty list");
                        break;
                    }
                    System.out.println("Current elements of collection.arraylist: " + list);
                    System.out.println("Enter the choice to remove an element \n" +
                            "1. Remove by index \n" +
                            "2. Remove by value/element");
                    int remove = sc.nextInt();
                    while (remove < 1 || remove > 2) {
                        System.out.println("Invalid choice, Please try again");
                        remove = sc.nextInt();
                    }
                    if (remove == 1) {
                        System.out.println("Enter the index to be removed");
                        int index = sc.nextInt();
                        while (index < 0 || index >= list.size()) {
                            System.out.println("Invalid index please try again");
                            index = sc.nextInt();
                        }
                        list.remove(index);
                    } else {
                        System.out.println("Enter the element/value to be removed");
                        int removeElement = sc.nextInt();
                        list.remove(Integer.valueOf(removeElement));
                    }
                    break;
                case 3:
                    System.out.println("Current elements of collection.arraylist: " + list);
                    break;
                default:

                    break;
            }
            printMenu();
            choice = sc.nextInt();
        }
        System.out.println("-----------------------------");
        System.out.println("Thank you for using our program!");
    }

    public static void printMenu() {
        System.out.println("-----------------------------");
        System.out.println("Menu: \n" +
                "1. Add Element \n" +
                "2. Remove Element \n" +
                "3. Display Element \n" +
                "4. Exit");
    }
}
