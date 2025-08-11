package recursion;

import java.util.Scanner;

/*
You are given an integer n. You need to print the sum of all numbers from 1 to n using recursion.
 */
public class PrintSum {

    public static int printSum(int n) {
        if (n == 0) return 0; // Base case: if n is 0, return 0
        return n + printSum(n - 1); // Recursive call: add n to the sum of numbers from 1 to n-1
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a positive integer: ");
        int n = sc.nextInt();
        System.out.println("The sum of numbers from 1 to " + n + " is: " + printSum(n));
        sc.close(); // Close the scanner to prevent resource leaks
    }
}
