package logicbuildingpattern;

import java.util.Scanner;

/**
 * This code prints pattern
 * *****
 * ****
 * ***
 * **
 * *
 */
public class Pattern5 {

    public static void main(String[] args) {
        System.out.print("Enter a number: ");
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        for (int i = 0; i <n; i++) {
            for (int j = n-i; j > 0; j--) {
                System.out.print("* ");
            }
            System.out.println();
        }
    }
}
