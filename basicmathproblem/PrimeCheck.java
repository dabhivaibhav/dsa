package basicmathproblem;

/*
Problem: Check for prime number. You are given an integer n. You need to check if the number is prime or not. Return
true if it is a prime number, otherwise return false. A prime number is a number which has no divisors except 1 and
itself.

Constraints: 1 <= n <= 5000

Approach: I have used the
 */

import java.util.Scanner;

public class PrimeCheck {

    public static boolean isPrime(int num) {

        int counter = 0;
        for (int i = 1; i * i <= num; i++) {
            if (num % i == 0) {
                counter++;
                if ((num % i) != i) {
                    counter++;
                }
            }

        }
        return counter == 2;
    }

    public static void main(String[] args) {

        System.out.print("Enter a number: ");
        Scanner sc = new Scanner(System.in);
        int num = sc.nextInt();
        if(isPrime(num))
            System.out.println(num + " is prime number");
        else
            System.out.println(num + " is not prime number");
    }
}
