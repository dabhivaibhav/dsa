package basicmathproblem;

import java.util.Scanner;

/*
Given a signed 32-bit integer x, return x with its digits reversed. If reversing x causes the value to go outside the signed
32-bit integer range [-2^31, 2^31, - 1], then return 0. Assume the environment does not allow you to store 64-bit integers
(signed or unsigned).

Approach: Here I have used the logic of reverseDigits() from ReverseDigit class. But I have the modified the logic to check
As the int data type supports range from -2^31 to 2^31 (-2,147,483,648 to 2,147,483,647). When reversing a number,
especially one close to 10 digits long, multiplying the current reversed value by 10 and adding the next digit could push
the result outside this range, causing an overflow. To prevent this, before performing rev = rev * 10 + digit, I first
check if rev is greater than Integer.MAX_VALUE / 10 or equal to it with the next digit greater than 7 (the last digit of
Integer.MAX_VALUE), which would cause a positive overflow. Similarly, I check if rev is less than Integer.MIN_VALUE / 10
or equal to it with the next digit less than -8 (the last digit of Integer.MIN_VALUE), which would cause a negative
overflow. If any of these conditions are true, the method returns 0 immediately, ensuring that the reversed value always
stays within the valid 32-bit signed integer range.
 */
public class LeetCode7ReverseInteger {

    public static int reverseInteger(int x) {
        int rev = 0;
        while (x != 0) {
            int digit = x % 10;
            x /= 10;
            if (rev > Integer.MAX_VALUE / 10 || (rev == Integer.MAX_VALUE / 10 && digit > 7)) return 0;
            if (rev < Integer.MIN_VALUE / 10 || (rev == Integer.MIN_VALUE / 10 && digit < -8)) return 0;
            rev = rev * 10 + digit;
        }
        return rev;
    }

    public static void main(String[] args) {

        System.out.print("Enter a number: ");
        Scanner sc = new Scanner(System.in);
        int x = sc.nextInt();

        System.out.println("Reversed Number: "+reverseInteger(x));
    }
}
