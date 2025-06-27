package logicbuildingpattern;

/**
 * Write a program to print the pattern
 *     A
 *    BCD
 *   EFGHI
 *  JKLMNOP
 * QRSTUVWXY
 */
public class Pattern17 {

    public static void main(String[] args) {
        char letter = 'A';
        int n =5;
        for (int i = 1; i <= n; i++) {

            for(int k = 1; k <= n-i;k++){
                System.out.print(" ");
            }

            for(int j = 1; j <= 2*i-1; j++) {
                System.out.print(letter);
                letter++;
            }

            System.out.println();
        }
    }
}
