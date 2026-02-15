package collection.stack;

import java.util.Stack;

/*
Leetcode: 85. Maximal Rectangle

Given a rows x cols binary matrix filled with 0's and 1's, find the largest rectangle containing only 1's and return its area.

Example 1:
Input: matrix = [['1','0','1','0','0'],['1','0','1','1','1'],['1','1','1','1','1'],['1','0','0','1','0']]
Output: 6
Explanation: The maximal rectangle is shown in the above picture.

Example 2:
Input: matrix = [['0']]
Output: 0

Example 3:
Input: matrix = [['1']]
Output: 1

Constraints:
            rows == matrix.length
            cols == matrix[i].length
            1 <= rows, cols <= 200
            matrix[i][j] is '0' or '1'.
 */
public class MaximumRectangles {

    public static void main(String[] args) {
        char matrix[][] = {{'1', '0', '1', '0', '0'}, {'1', '0', '1', '1', '1'}, {'1', '1', '1', '1', '1'}, {'1', '0', '0', '1', '0'}};
        System.out.println(findMaxRectangle(matrix));
    }


    /**
     * Method: findMaxRectangle(int[][] matrix)
     * <p>
     * What it does:
     * This method finds the largest rectangle consisting only of 1s
     * in a given binary matrix. It converts each row into a histogram
     * representation and reuses the Largest Rectangle in Histogram logic
     * to compute the maximum possible rectangle at each step.
     * <p>
     * Core Idea:
     * Instead of trying to directly search rectangles in 2D,
     * we convert the matrix row by row into a 1D histogram problem.
     * <p>
     * For each row:
     * - If matrix[row][col] == 1 → increase height[col] by 1
     * - If matrix[row][col] == 0 → reset height[col] to 0
     * <p>
     * This means:
     * heights[col] represents how many consecutive 1s
     * are stacked vertically up to the current row.
     * <p>
     * After building the histogram for that row,
     * we compute the largest rectangle in that histogram.
     * The maximum among all rows is the answer.
     * <p>
     * Why it works:
     * Any maximal rectangle of 1s in a matrix
     * must end at some row.
     * When that row is processed,
     * its histogram representation will capture that rectangle.
     * <p>
     * Example:
     * Matrix:
     * 1 0 1 1
     * 1 1 1 1
     * <p>
     * After processing row 0 → heights = [1,0,1,1]
     * After processing row 1 → heights = [2,1,2,2]
     * <p>
     * Now we calculate largest rectangle in histogram [2,1,2,2]
     * which correctly finds the maximum rectangle.
     * <p>
     * Time Complexity:
     * O(rows * cols)
     * Each row builds histogram in O(cols)
     * Each histogram calculation runs in O(cols)
     * <p>
     * Space Complexity:
     * O(cols)
     * For heights array and stack used in histogram calculation.
     */
    private static int findMaxRectangle(char[][] matrix) {
        if (matrix == null || matrix.length == 0) return 0;
        int cols = matrix[0].length;
        int[] heights = new int[cols];
        int maxArea = 0;
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < cols; col++) {
                if (matrix[row][col] == '1') {
                    heights[col] += 1;
                } else {
                    heights[col] = 0;
                }
            }
            maxArea = Math.max(maxArea, calculateHistogramArea(heights));
        }
        return maxArea;
    }

    /**
     * Method: calculateHistogramArea(int[] heights)
     * <p>
     * What it does:
     * Computes the largest rectangular area in a histogram
     * using a monotonic increasing stack.
     * <p>
     * Core Idea:
     * Each bar in the histogram can act as the height
     * of a rectangle. We determine how far it can extend
     * left and right before hitting a smaller bar.
     * <p>
     * Stack Logic:
     * - The stack stores indices of bars in increasing height order.
     * - When we find a smaller bar, we start popping.
     * - Each popped bar becomes the height of a rectangle.
     * <p>
     * For a popped bar:
     * height = heights[poppedIndex]
     * right boundary = current index i
     * left boundary = stack.peek() after pop
     * width = i - stack.peek() - 1 (or i if stack empty)
     * <p>
     * Sentinel Trick:
     * We iterate until i <= n and treat i == n as height 0.
     * This forces all remaining bars to be processed.
     * <p>
     * Why it works:
     * Every bar is pushed and popped exactly once.
     * That guarantees linear time.
     * <p>
     * Time Complexity:
     * O(n)
     * <p>
     * Space Complexity:
     * O(n)
     * Stack can store up to n indices.
     */
    private static int calculateHistogramArea(int[] heights) {
        Stack<Integer> stack = new Stack<>();
        int maxArea = 0;
        int n = heights.length;

        for (int i = 0; i <= n; i++) {
            int currentHeight = (i == n) ? 0 : heights[i];
            while (!stack.isEmpty() && currentHeight < heights[stack.peek()]) {
                int h = heights[stack.pop()];
                int width = stack.isEmpty() ? i : i - stack.peek() - 1;
                maxArea = Math.max(maxArea, h * width);
            }
            stack.push(i);
        }
        return maxArea;
    }

}
