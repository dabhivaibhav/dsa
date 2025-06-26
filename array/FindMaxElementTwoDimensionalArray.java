package array;

/**
 * Write a program to find maximum element in each row in 2D array
 */
public class FindMaxElementTwoDimensionalArray {

    public static int findMax(int[] arr) {
        int max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            max = max > arr[i] ? max : arr[i];
        }
        return max;
    }

    public static void main(String[] args) {

        int[][] twoDimensionalArray = {
                {1, 2, 3, -1, 10, 5, 11},
                {4, 500, 6, 1},
                {95, 89, 90, 91}};

        for (int i = 0; i < twoDimensionalArray.length; i++) {
            System.out.println("Maximum element in " + i + " row is: " + findMax(twoDimensionalArray[i]));
        }
    }
}



