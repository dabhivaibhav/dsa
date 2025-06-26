package logicbuildingpattern;

import java.util.Scanner;

/**
 * This code prints pattern
 * 1
 * 12
 * 123
 * 1234
 * 12345
 */

public class Pattern3 {
    public static void main(String[] args) {
        System.out.print("Enter the number: ");
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= i; j++) {
                System.out.print(j+" ");
            }
            System.out.println();
        }
    }
}
