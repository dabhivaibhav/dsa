package basicmathproblem;

import java.util.Scanner;

/*
Given an integer N, return true if it is a palindrome else return false.

A palindrome is a number that reads the same backward as forward. For example, 121, 1331, and 4554 are palindromes
because they remain the same when their digits are reversed.

Constraint: Same constraints as ReverseDigit

Approach: Here I have used the logic of reverseDigits() from ReverseDigit class. then simply checked the Original and
reverse number in if condition.
 */

public class CheckPalindromeDigit {


    public static void checkPalindrome(int num) {
        int reverseNum = ReverseDigit.reverseDigits(num);
        if (num == reverseNum) {
            System.out.println(num + " is palindrome.");
        } else {
            System.out.println("Reverse of " + num + " is " + reverseNum + ". So, " + num + " is not palindrome.");
        }
    }

    public static void main(String[] args) {
        System.out.print("Enter a number: ");
        Scanner sc = new Scanner(System.in);
        int num = sc.nextInt();
        checkPalindrome(num);

    }
}
