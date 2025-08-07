package basicmathproblem;


import java.util.Scanner;

import static java.lang.Math.log10;

/*
Problem: Check if the Number is Armstrong. You are given an integer n. You need to check whether it is an armstrong number
or not. Return true if it is an armstrong number, otherwise return false. An armstrong number is a number which is equal
to the sum of the digits of the number, raised to the power of the number of digits.

Constraint: 0 <= n <= 10^9

Approach: To determine whether a given number is an Armstrong number, we first count the total number of digits in the
number. Once we have the digit count, we take each digit of the number, raise it to the power of the total number of
digits, and sum these powered values. If the resulting sum is equal to the original number, then it is an Armstrong
number; otherwise, it is not. In my logic, I use the countDigit function to calculate the number of digits in the given
number. After that, I apply the standard last-digit extraction technique (n % 10) inside a loop to isolate each digit.
For each extracted digit, I use Java’s Math.pow() function to raise it to the power of the digit count and then add the
result to a running sum. Once all digits have been processed, I compare the sum with the original number. If they match,
the number is an Armstrong number; if not, it isn’t.
 */
public class ArmStrong {


    private static int countDigit(int x) {
        return x == 0 ? 1 : (int) (log10(x) + 1);
    }

    public static void calculateArmStrong(int x) {

        int digitsCount = countDigit(x);
        if (x < 0 || x > (1000000000)) {
            System.out.println("Invalid Input");
            return;
        }
        int duplicate = x;
        int sum = 0;
        while (x > 0) {
            int remainder = x % 10;
            sum += (int) Math.pow(remainder, digitsCount);
            x /= 10;
        }
        if (duplicate == sum)
            System.out.println("Given number " + duplicate + " is armstrong.");
        else
            System.out.println("Given number " + duplicate + " is not armstrong.");

    }

    public static void main(String[] args) {
        System.out.println(10 ^ 9);
        System.out.print("Enter a number: ");
        Scanner sc = new Scanner(System.in);
        int num = sc.nextInt();
        calculateArmStrong(num);
    }
}
