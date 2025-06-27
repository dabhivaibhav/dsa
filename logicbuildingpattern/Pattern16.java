package logicbuildingpattern;

import java.util.Scanner;

/**
 * Write a code to print the pattern
 * A
 * AB
 * ABC
 * ABCD
 * ABCDE
 */
public class Pattern16 {
    public static void main(String[] args) {
        System.out.print("Enter the number of rows: ");
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        for(int i=1;i<=n;i++){
            char letter = 'A';
            for(int j=1;j<=i;j++){
                System.out.print(letter+" ");
                letter++;
            }
            System.out.println();
        }


    }
}
