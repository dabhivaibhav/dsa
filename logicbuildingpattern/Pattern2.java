package logicbuildingpattern;

import java.util.Scanner;

/**
 * The code print the pattern
 * *
 * **
 * ***
 * ****
 * *****
 */
public class Pattern2 {
    public static void main(String[] args) {
        System.out.print("Enter the number : ");
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= i; j++) {
                System.out.print("* ");
            }
            System.out.println();
        }
    }
}
