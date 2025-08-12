package recursion;

import java.util.Scanner;

/*
You are given a string. You need to check if it is a palindrome using recursion.
A palindrome is a string that reads the same backward as forward, such as "madam"
or "racecar".
 */
public class CheckPalindrome {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a string: ");
        String str = sc.nextLine();
        int strLen = str.length();
        int i = 0;
        checkPalindrome(str, i, strLen - i - 1);
    }

    private static void checkPalindrome(String str, int i, int i1) {

        if (i >= i1) {
            System.out.println(str + " is palindrome.");
            return;
        }

        if (str.charAt(i) != str.charAt(i1)) {
            System.out.println(str + " is not palindrome.");
            return;
        }

        checkPalindrome(str, i + 1, i1 - 1);
    }
}
