package logicbuildingpattern;

import java.util.Scanner;

/**
 * print stars in even numbers only
 *  **
 *  ****
 *  ******
 *  ********
 *  **********
 *
 * My logic is that if I want to print the 5 rows so it will take two stars in the first row
 * in 2nd its 4 in 3rd its 6 and so on.
 */
public class Pattern12 {
    public static void main(String[] args) {
        System.out.print("Enter the number of rows: ");
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        for (int i = 1; i <= n; i++) {
            for(int j = 1; j <= i*2; j++){
                System.out.print("*");
            }
            System.out.println();
        }
    }
}
