package logicbuildingpattern;

import java.util.Scanner;

/**
 * *
 * ***
 * *****
 * *******
 * ********
 * print the stars in odd numbers only
 */
public class Pattern13 {

    public static void main(String[] args) {
        System.out.print("Enter the number of rows: ");
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= ((2 * i) - 1); j++) {
                System.out.print("*");
            }
            System.out.println();
        }
    }
}
