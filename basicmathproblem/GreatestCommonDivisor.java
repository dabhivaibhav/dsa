package basicmathproblem;

import java.util.Scanner;

/*
Problem: GCD of Two Numbers. You are given two integers n1 and n2. You need find the Greatest Common Divisor (GCD) of the
two given numbers. Return the GCD of the two numbers. The Greatest Common Divisor (GCD) of two integers is the largest
positive integer that divides both of the integers.
Constraints: 1 <= n1, n2 <= 1000


 */
public class GreatestCommonDivisor {

    public static void findGreatestCommonDivisor(int a, int b) {
        int minimum = Math.min(a, b);
        int value = 0;
        for (int i = minimum; i >= 1; i--) {
            if (a % i == 0 && b % i == 0) {
                value = i;
                break;
            }
        }
        System.out.println("Greatest common divisor: " + value);
    }

    public static void main(String[] args) {
        System.out.print("Enter first number: ");
        Scanner sc = new Scanner(System.in);
        int a = sc.nextInt();
        System.out.print("Enter second number: ");
        int b = sc.nextInt();
        findGreatestCommonDivisor(a, b);
    }
}
