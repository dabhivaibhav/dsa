package array;

import java.util.Scanner;

/**
 * Write a program to find smallest element in array
 */
public class SmallestElement {

    public static void main(String[] args) {
        System.out.print("Enter the size of the array: ");
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        while(n<=1){
            System.out.print("Enter the size of the array at least 2 ");
            n = sc.nextInt();
        }
        int[] arr = new int[n];

        for(int i=0; i<n; i++){
            arr[i] = sc.nextInt();
        }

        int smallest = arr[0];

        for(int i=1; i<n; i++){
            if(arr[i]<smallest){
                smallest = arr[i];
            }
        }
        System.out.println("Smallest element is "+smallest);
    }
}
