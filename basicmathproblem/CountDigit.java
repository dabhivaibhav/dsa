package basicmathproblem;


import java.util.Scanner;

import static java.lang.Math.log10;

/*
Problem: Given the number 'n'. find out and return the number of digits present in a number.

Constraints: 1. 0 <= n <= 5000
             2. n will contain no leading zeroes except when it is 0 itself.

Approach1: Use a simple mathematical approach. Repeatedly divide the number by 10 to remove its last digit, counting how
many times this can be done until the number becomes 0. This works because each division by 10 removes one digit. For the
edge case where the number is 0, return 1 directly since 0 has one digit.

Approach 2: For any number, logb(X) = y means b^y = x
where b -> base of the log, x -> the number from user, y -> the exponent we need
For example to count digits in 123. Log10(123) -> 2.0899 and if you add 1 in it. It will become 3.0899 and if you take
integer from it the result will be 3.

Approach 3: By converting integer into string and then using the length function()
 */

public class CountDigit {


    //Approach1
    public static int countDigit(int n) {

        int count = 0;
        if (n == 0)
            return 1;
        while (n > 0) {
            n = n / 10;
            count++;
        }
        return count;
    }

    //Approach 2
    public static void countDigitLog(int n) {
        if (n == 0) {
            System.out.println(1);
            return;
        }
        System.out.println("Digits in number (logarithmic approach): "+(int) (log10(n) + 1));
    }

    public static void countDigitString(int n) {
        String num = String.valueOf(n);
        System.out.println("Digits in number (String approach): "+num.length());
    }

    public static void main(String[] args) {

        System.out.print("Enter the number: ");
        Scanner sc = new Scanner(System.in);
        int num = sc.nextInt();
        System.out.println("Digits in number (extraction approach): "+countDigit(num));
        countDigitLog(num);
        countDigitString(num);
        sc.close();
    }
}
