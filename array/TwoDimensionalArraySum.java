package array;

/**
 * Write a program to print sum of each row in 2D array.
 */
public class TwoDimensionalArraySum {

    public static void main(String[] args) {
        int[][] twoDimensionalArray = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}};

        //Logic to print sum of each row
        for (int i = 0; i < twoDimensionalArray.length; i++) {
            int sum = 0;
            for (int j = 0; j < twoDimensionalArray[i].length; j++) {
                sum += twoDimensionalArray[i][j];
            }
            System.out.println("Sum of " + i + " row: " + sum);
        }
        System.out.println("");
        //Logic to print sum of each column
        for (int i = 0; i < twoDimensionalArray.length; i++) {
            int sum = 0;
            for (int j = 0; j < twoDimensionalArray[i].length; j++) {
                sum += twoDimensionalArray[j][i];
            }
            System.out.println("Sum of " + i + " column: " + sum);
        }
    }
}
