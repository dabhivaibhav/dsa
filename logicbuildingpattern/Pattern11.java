package logicbuildingpattern;

import java.util.Scanner;

/**
 *     *
 *    **
 *   ***
 *  ****
 * *****
 *
 * so in this code there is a pattern that in first line when there is 1 start there is 4 space
 * when 2 stars 3 spaces and so on so i represents the row so when i = 1 means it is the first row
 * which contains 1 star and the remaining will be space so if we have entered 5 in the beginning
 * so in the first line there will be 1 star and 4 spaces. so to print space I have used j for loop
 * which starts from 1 and goes upto n-i which and for * used K loop which is similar to the row number
 * if it is first row print 1, 2nd print two stars and so on.
 */
public class Pattern11 {

    public static void main(String[] args) {
        System.out.print("Enter the number : ");
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        for (int i = 1; i <= n; i++) {
            //print space
            for (int j = 1; j <= n - i; j++) {
                System.out.print(" ");
            }
            //print *
            for (int k = 1; k <= i; k++) {
                System.out.print("*");
            }
            System.out.println();
        }
    }
}
