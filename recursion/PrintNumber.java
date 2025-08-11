package recursion;


import java.util.Scanner;

/*
You are given an integer n. You need to print the number linearly from 1 to n times using recursion.
In the second function print number in reverse order from n to 1.
 */
public class PrintNumber {


    public static void printNumber(int i, int n) {
        if (i > n) return; // Base case: if i exceeds n, stop recursion
        System.out.println(i); // Print the current number
        printNumber(i + 1, n); // Recursive call with incremented i
    }

    public static void printNumberReverse(int i, int n) {
        if (i > n) return; // Base case: if i is less than 1, stop recursion
        printNumberReverse(i + 1, n); // Recursive call with decremented i
        System.out.println(i); // Print the current number
    }

    public static void printNumberReverse1(int k, int n) {
        if (k < 1 ) return; // Base case: if k exceeds n, stop recursion
        System.out.println(k); // Print the current number
        printNumberReverse1(k - 1, n); // Recursive call with incremented k
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a positive integer: ");
        int n = sc.nextInt();
        int i = 1;
        printNumber(i, n);
        int k = 1;
        System.out.println();
        System.out.println("Printing in reverse order:");
        printNumberReverse(k, n);
        System.out.println();
        System.out.println("Printing in reverse order 2nd method:");
        printNumberReverse1(n, n);
    }
}
