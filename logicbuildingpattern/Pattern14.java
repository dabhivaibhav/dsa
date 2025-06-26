package logicbuildingpattern;

import java.util.Scanner;

/**
 * 1
 * 2 3
 * 4 5 6
 * 7 8 9 10
 * 11 12 13 14 15
 */
public class Pattern14 {
    public static void main(String[] args) {
        System.out.println("Enter the number of rows: ");
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        for (int i = 1; i <= n; i++) {
            for (int j = i; j < i * 2; j++) {
                System.out.print(j);
            }
            System.out.println();
        }
    }
}
