package recursion;

import java.util.Scanner;

/*
you are given an integer n. You need to print the factorial of n using recursion.
 */
public class PrintFactorial {

    public static int printFactorial(int n) {
        if (n < 1) {
            return 0; // If n is less than 1, return 0 (factorial is not defined for negative numbers or zero)
        }
        if (n == 1) return 1; // Base case: if n is 1, return 1 (factorial of 1 is 1)
        return n * printFactorial(n - 1); // Recursive call: multiply n with the factorial of (n-1)
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a positive integer: ");
        int n = sc.nextInt();
        System.out.println("Factorial from 1 to "+n+ " is: "+printFactorial(n));
    }
}
