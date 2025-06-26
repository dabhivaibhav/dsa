package logicbuildingpattern;

import java.util.Scanner;

/**
 * This code will print pattern
 * *********
 *  *******
 *   *****
 *    ***
 *     *
 */
public class Pattern9 {
    public static void main(String[] args) {
        System.out.print("Enter the number : ");
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        for (int i = n; i > 0; i--) {
            //this is for space
            for (int j = n - i; j >= 0; j--) {
                System.out.print(" ");
            }
            //this is for star
            for (int j = ((2 * i) - 1); j > 0; j--) {
                System.out.print("*");
            }
            //this is for space
            for (int j = n - i; j >= 0; j--) {
                System.out.print(" ");
            }
            System.out.println("");
        }
    }
}
