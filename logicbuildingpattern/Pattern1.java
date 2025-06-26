package logicbuildingpattern;

import java.util.Scanner;

/**
 * Code to print pattern
 *  ****
 *  ****
 *  ****
 *  ****
 */

public class Pattern1 {

    public static void main(String[] args) {
        System.out.print("Enter the number to print n*n Matrix: ");
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        for(int i =1 ; i<=n;i++){
            for (int j = 1; j<=n;j++){
                System.out.print("* ");
            }
            System.out.println();
        }

        sc.close();
    }
}
