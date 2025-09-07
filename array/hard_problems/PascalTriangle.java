package array.hard_problems;

/*
118. Pascal's Triangle

Given an integer numRows, return the first numRows of Pascal's triangle.

In Pascal's triangle, each number is the sum of the two numbers directly above it as shown:

Example 1:

Input: numRows = 5
Output: [[1],[1,1],[1,2,1],[1,3,3,1],[1,4,6,4,1]]
Example 2:

Input: numRows = 1
Output: [[1]]
 */

import java.util.ArrayList;
import java.util.List;

public class PascalTriangle {

    public static void main(String[] args) {

        int numRows = 5;
        int rowNum = 3;
        int colNum = 2;
        System.out.println(pascalTriangleBruteForceApproach(numRows));
        System.out.println(pascalTriangleOptimalApproach(numRows));
    }

    private static List<List<Integer>> pascalTriangleBruteForceApproach(int numRows) {

        List<List<Integer>> triangle = new ArrayList<>(numRows);

        for (int i = 1; i <= numRows; i++) {
            List<Integer> row = new ArrayList<>(i);
            for (int j = 0; j < i; j++) {
                if (j == 0 || j == i - 1) {
                    row.add(1);
                } else {
                    row.add(triangle.get(i - 2).get(j - 1) + triangle.get(i - 2).get(j));
                }
            }
            triangle.add(row);
        }

        return triangle;
    }

    /**
     * Generates Pascal's Triangle using optimal approach with combinations formula.
     *
     * Algorithm:
     * 1. Create result list to store all rows
     * 2. For each row number from 1 to numRows:
     *    - Generate complete row using combinations formula
     *    - Add row to result list
     *
     * Benefits:
     * - Direct calculation without storing previous rows
     * - Uses O(1) auxiliary space for calculations
     * - Clean and mathematical approach
     *
     * Time Complexity: O(n²) where n is numRows
     * Space Complexity: 
     * - O(n²) for storing the final result (required by problem)
     * - O(1) auxiliary space for calculations (not counting output space)
     *
     * @param numRows number of rows to generate
     * @return List containing all rows of Pascal's Triangle
     */
    private static List<List<Integer>> pascalTriangleOptimalApproach(int numRows) {
        List<List<Integer>> triangle = new ArrayList<>(numRows);
        for (int i = 1; i <= numRows; i++) {
            triangle.add(rowOfTriangle(i));
        }
        return triangle;
    }

    /**
     * Calculates value at specific position in Pascal's Triangle using combinations formula.
     * Formula: C(n,r) = n!/(r!(n-r)!)
     *
     * Algorithm:
     * 1. Start with result = 1
     * 2. Calculate nCr using optimized multiplication and division:
     *    - For i from 0 to colNum-1:
     *      * Multiply by (rowNum-i)
     *      * Divide by (i+1)
     *
     * Example for rowNum=4, colNum=2:
     * result = 1
     * i=0: result = 1 * 4/1 = 4
     * i=1: result = 4 * 3/2 = 6
     *
     * Time Complexity: O(colNum) - number of iterations
     * Space Complexity: O(1) - uses only constant extra space
     *
     * @param rowNum row number (0-based)
     * @param colNum column number (0-based)
     * @return value at position (rowNum, colNum)
     */
    private static int valueOfRow(int rowNum, int colNum) {
        long result = 1;
        for (int i = 0; i < colNum; i++) {
            result *= (rowNum - i);
            result /= (i + 1);
        }
        return (int) result;
    }

    /**
     * Generates a complete row of Pascal's Triangle using combination values.
     *
     * Algorithm:
     * 1. Create new list to store row values
     * 2. For each position i in row (0 to rowNum-1):
     *    - Calculate value using valueOfRow(rowNum-1, i)
     *    - Add value to row list
     *
     * Example for rowNum=3:
     * i=0: valueOfRow(2,0) = 1
     * i=1: valueOfRow(2,1) = 2
     * i=2: valueOfRow(2,2) = 1
     * Returns: [1,2,1]
     *
     * Time Complexity: O(rowNum²) - calls valueOfRow() rowNum times
     * Space Complexity: O(rowNum) - stores rowNum elements in list
     *
     * @param rowNum the row number to generate (1-based)
     * @return List containing all values in the specified row
     */
    private static List<Integer> rowOfTriangle(int rowNum) {
        List<Integer> row = new ArrayList<>(rowNum);
        for (int i = 0; i < rowNum; i++) {
            row.add(valueOfRow(rowNum - 1, i));
        }
        return row;
    }
}


