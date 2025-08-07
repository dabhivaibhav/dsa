package basicmathproblem;

import java.util.*;

/*
Problem: Divisors of a number. You are given an integer n. You need to find all the divisors of n. Return all the
divisors of n as an array or list in a sorted order. A number which completely divides another number is called it's
divisor.

Constraints: 1 <= n <= 1000

The first approach is Bruteforce approach by checking each single digit upto the given number is divisible or
not.

In the second approach, to print all divisors of num efficiently, I iterate i from 1 up to square root of num instead of going all the
way to num. For each i that divides num (num % i == 0), I add both divisors of that factor pair: i and num/i. This
captures the small divisor and its corresponding large divisor in the same step. To avoid duplicates for perfect squares,
I only add num/i when it’s different from i. After the loop, I sort the collected divisors and print them.

In the third approach, I chose the square root approach with direct ordered printing because it gives me
most optimized output. Instead of checking every number up to n, I only check divisors up to √n. Whenever I find a small
divisor i, I immediately know its corresponding large divisor n / i. By printing all the small divisors first, then
looping backward to print the matching large divisors, I can output everything in sorted order without storing them in a
list or sorting later.
 */
public class PrintAllDivisors {


    public static void printDivisors(int num) {
        if (num < 1 || num > 1000) {
            System.out.println("Invalid input. Please enter a number between 1 and 1000.");
            return;
        }

        //First Approach
        System.out.println("First Approach (Brute force): ");
        for (int i = 1; i <= num; i++) {
            if (num % i == 0) {
                System.out.print(i + " ");


            }
        }

        //Second Approach
        System.out.println();
        System.out.println("Second Approach: ");
        List<Integer> list = new ArrayList<Integer>();

        for (int i = 1; i * i <= num; i++) {
            if (num % i == 0) {
                list.add(i);
                if ((num / i) != i) {
                    list.add(num / i);
                }
            }
        }
        Collections.sort(list);
        for (int l : list) {
            System.out.print(l + " ");
        }


        //Third Approach
        System.out.println();
        System.out.println("Third Approach: ");
        int i;
        for (i = 1; i * i < num; i++) {
            if (num % i == 0) System.out.print(i + " ");
        }
        if (i * i == num) i--;
        for (; i >= 1; i--) {
            if (num % i == 0) System.out.print(num / i + " ");
        }

    }

    public static void main(String[] args) {

        System.out.print("Enter a number between 1 and 1000: ");
        Scanner sc = new Scanner(System.in);
        int num = sc.nextInt();
        printDivisors(num);
    }
}
