package array.easy_problems;

import java.util.Scanner;

public class OccurenceOfAnElement {

    /**
     * Method to take the size of the array from the user
     */
    public static int[] arrayCreation() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the size of array");
        int arrSize = sc.nextInt();
        int[] arr = new int[arrSize];
        System.out.println("Enter the elements of array");
        for (int i = 0; i < arrSize; i++) {
            arr[i] = sc.nextInt();
        }
        return arr;
    }

    /**
     * Logic to find the occurence of an element in an array by iterating over all the
     * elements of an array
     * @param arr
     * @param targetElement
     */
    public static void occurenceOfAnElement(int[] arr, int targetElement) {

        int counter = 0;
        for(int j:arr){
            if(j==targetElement){
                counter++;
            }
        }
        System.out.println("Occurence of target element " + targetElement + " is " + counter);
    }

    public static void main(String[] args) {

        int[] arr = arrayCreation();
        System.out.print("Enter the target element: ");
        Scanner sc = new Scanner(System.in);
        int targetElement = sc.nextInt();
        occurenceOfAnElement(arr, targetElement);
    }

}
