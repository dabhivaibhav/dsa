package logicbuildingpattern;

import java.util.Scanner;

/**
 * 1
 * 23
 * 456
 * 7890
 * 12345
 * 678901
 * 2345678
 */
public class Pattern15 {
    public static void main(String[] args) {
        System.out.print("Enter the number of row: ");
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int num = 1;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= i; j++) {
                if (num > 9) {
                    num = 0;
                }
                System.out.print(num + " ");
                num++;
            }
            System.out.println();
        }

    }
}
