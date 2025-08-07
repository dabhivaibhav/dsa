package basicmathproblem;


import java.util.Scanner;

/*
You are given an integer n. Return the integer formed by placing the digits of n in reverse order.
Constraints: 1. 0 <= n <= 5000.
             2. n will contain no leading zeroes except when it is 0 itself.

Approach:
1. Extracting the last digit -> digit = n%10,
2. Shifting the existing result left (multiply by 10) and adding the new digit -> reverse = reverse * 10 + digit
3. Removing the last digit from the original number -> n /= 10
 */
public class ReverseDigit {


    public static int reverseDigits(int n) {
        int reverse = 0;
        while (n > 0) {
            int digit = n % 10;
            reverse = reverse * 10 + digit;
            n /= 10;
        }
        return reverse;
    }

    public static void main(String[] args) {
        System.out.print("Enter a number: ");
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        System.out.println("Reversed number: " + reverseDigits(n));
        sc.close();
    }
}
