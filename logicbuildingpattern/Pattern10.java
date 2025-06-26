package logicbuildingpattern;

import java.util.Scanner;

/**
 * This code will print
 * *
 * **
 * ***
 * ****
 * *****
 * ****
 * ***
 * **
 * *
 */
public class Pattern10 {
    public static void main(String[] args) {
        System.out.print("Enter the number : ");
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        for (int i = 1; i <= 2*n-1; i++) {
            int stars = i;
            if(i >n) stars = 2*n -i;
            for (int j = 1; j <= stars; j++) {
                System.out.print("*");
            }
            System.out.println("");
        }
    }
}
