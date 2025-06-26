package logicbuildingpattern;

import java.util.Scanner;

/**
 * This code will print pattern
 *         *
 *       * * *
 *     * * * * *
 *   * * * * * * *
 * * * * * * * * * *
 */
public class Pattern8 {
    public static void main(String[] args) {
        System.out.print("Enter the number : ");
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        //Outer for loop for row
        for (int i = 1; i <= n; i++) {

            //this for loop for space to make stars in center
            for (int j = n - i; j >= 0; j--) {
                System.out.print(" ");
            }
            for (int k = 1; k <= ((2 * i) - 1); k++) {
                System.out.print("*");
            }
            for (int j = n + 1 - i; j >= 0; j--) {
                System.out.print(" ");
            }
            System.out.println();
        }
    }
}
