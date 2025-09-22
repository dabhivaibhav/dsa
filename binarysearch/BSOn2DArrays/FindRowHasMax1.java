package binarysearch.BSOn2DArrays;

/*
Find row with maximum 1's
Given a non-empty grid mat consisting of only 0s and 1s, where all the rows are sorted in ascending order, find the
index of the row with the maximum number of ones.
If two rows have the same number of ones, consider the one with a smaller index. If no 1 exists in the matrix, return -1.

Examples:
Input : mat = [ [1, 1, 1], [0, 0, 1], [0, 0, 0] ]
Output: 0
Explanation: The row with the maximum number of ones is 0 (0 - indexed).

Input: mat = [ [0, 0], [0, 0] ]
Output: -1
Explanation: The matrix does not contain any 1. So, -1 is the answer.

Constraints:
          n == mat.length
          m == mat[i].length
          1 <= n, m <= 100
          mat[i][j] is either 0 or 1.
 */
public class FindRowHasMax1 {
    public static void main(String[] args) {
        int[][] mat = {
                {0, 0, 0, 1},
                {0, 1, 1, 1},
                {1, 1, 1, 1}
        };
        int[][] mat1 = {
                {1, 1, 1},
                {0, 0, 1},
                {0, 0, 0}
        };
        int[][] mat2 = {
                {0, 0},
                {0, 0}
        };
        System.out.println(rowWithMax1sBruteForce(mat));
        System.out.println(rowWithMax1sBruteForce(mat1));
        System.out.println(rowWithMax1sBruteForce(mat2));
        System.out.println(rowWithMax1sOptimal(mat));
        System.out.println(rowWithMax1sOptimal(mat1));
        System.out.println(rowWithMax1sOptimal(mat2));
    }


    /**
     * What it does:
     * Scans the entire matrix row by row and returns the index of the row
     * containing the maximum number of 1’s. If two rows have the same
     * number of 1’s, the row with the smaller index is chosen. If the
     * matrix has no 1’s, returns -1.
     *
     * <p>
     * Why it works:
     * - The brute-force approach counts the number of 1’s in each row independently.
     * - By keeping track of the row with the current highest count, we can
     * determine the maximum efficiently without needing extra data structures.
     * - Since rows are sorted (all 0’s followed by all 1’s), the count per row
     * can be obtained by direct scanning.
     *
     * <p>
     * How it works:
     * - Initialize `bestRow = -1` and `bestCount = 0`.
     * - For each row:
     * - Count the number of 1’s in that row.
     * - If this count is greater than the current bestCount:
     * → update bestCount and bestRow to this row’s index.
     * - If the count is equal, the earlier row is kept automatically.
     * - Return bestRow at the end.
     *
     * <p>
     * Example 1:
     * mat = [
     * [0, 0, 0, 1],
     * [0, 1, 1, 1],
     * [1, 1, 1, 1]
     * ]
     * - Row 0 → 1 one
     * - Row 1 → 3 ones
     * - Row 2 → 4 ones (maximum)
     * - Answer = 2
     * <p>
     * Example 2:
     * mat = [
     * [1, 1, 1],
     * [0, 0, 1],
     * [0, 0, 0]
     * ]
     * - Row 0 → 3 ones
     * - Row 1 → 1 one
     * - Row 2 → 0 ones
     * - Maximum is row 0 → Answer = 0
     * <p>
     * Example 3:
     * mat = [
     * [0, 0],
     * [0, 0]
     * ]
     * - Both rows contain 0 ones
     * - Answer = -1 (no 1’s present)
     *
     * <p>
     * Time Complexity:
     * - O(n * m): Each cell of the matrix is inspected once.
     * <p>
     * Space Complexity:
     * - O(1): Uses only counters for row index and counts.
     *
     * <p>
     * Output:
     * Returns the index of the row with the maximum number of 1’s,
     * or -1 if the matrix contains no 1’s.
     */
    private static int rowWithMax1sBruteForce(int[][] mat) {
        if (mat == null || mat.length == 0) return -1;

        int bestRow = -1;
        int bestCount = 0;

        for (int i = 0; i < mat.length; i++) {
            int count = 0;
            for (int j = 0; j < mat[i].length; j++) {
                if (mat[i][j] == 1) count++;
            }
            if (count > bestCount) {
                bestCount = count;
                bestRow = i;
            }
        }
        return bestRow;
    }


    /**
     * What it does:
     * Finds the index of the row with the maximum number of 1’s in a binary matrix
     * where each row is sorted in non-decreasing order (all 0’s before 1’s).
     * If multiple rows have the same number of 1’s, the row with the smaller index
     * is returned. If no 1’s are present in the matrix, returns -1.
     *
     * <p>
     * Why it works:
     * - Since each row is sorted (0’s followed by 1’s), the number of 1’s in a row
     * can be determined by finding the index of the first 1.
     * - Once we know the first 1’s position, the count of 1’s is simply
     * (total columns - index of first 1).
     * - We can find the first 1 in a row efficiently using binary search
     * instead of scanning the whole row.
     * - Repeating this for all rows and tracking the row with the maximum count
     * ensures we find the correct answer.
     *
     * <p>
     * How it works:
     * - Initialize bestRow = -1 and maxOnes = 0.
     * - For each row:
     * 1. Run binary search (`findFirstOccurenceOf1`) to find the index of the first 1.
     * 2. Compute ones = (columns - firstOneIdx).
     * 3. If ones > maxOnes, update bestRow = current row index.
     * - After scanning all rows, return bestRow (or -1 if no 1’s were found).
     *
     * <p>
     * Example 1:
     * mat = [
     * [0, 0, 0, 1],
     * [0, 1, 1, 1],
     * [1, 1, 1, 1]
     * ]
     * - Row 0: first 1 at index 3 → 1 one
     * - Row 1: first 1 at index 1 → 3 ones
     * - Row 2: first 1 at index 0 → 4 ones
     * → Answer = 2
     * <p>
     * Example 2:
     * mat = [
     * [1, 1, 1],
     * [0, 0, 1],
     * [0, 0, 0]
     * ]
     * - Row 0: first 1 at index 0 → 3 ones
     * - Row 1: first 1 at index 2 → 1 one
     * - Row 2: no 1 → 0 ones
     * → Answer = 0
     * <p>
     * Example 3:
     * mat = [
     * [0, 0],
     * [0, 0]
     * ]
     * - Both rows contain 0 ones → Answer = -1
     *
     * <p>
     * Time Complexity:
     * - O(n * log m), where n = number of rows, m = number of columns.
     * Each row uses binary search to find the first 1 in O(log m).
     * <p>
     * Space Complexity:
     * - O(1): Only a few variables are used.
     *
     * <p>
     * Output:
     * Returns the index of the row with the maximum number of 1’s,
     * or -1 if the matrix has no 1’s.
     */

    public static int rowWithMax1sOptimal(int[][] mat) {
        int bestRow = -1;
        int maxOnes = 0; // keep 0 so we return -1 if all rows have 0 ones

        for (int i = 0; i < mat.length; i++) {
            int cols = mat[i].length;
            int firstOneIdx = findFirstOccurenceOf1(mat[i], 1);
            int ones = (firstOneIdx == cols) ? 0 : (cols - firstOneIdx);

            if (ones > maxOnes) {
                maxOnes = ones;
                bestRow = i;
            }
        }
        return bestRow;
    }

    /**
     * Helper: findFirstOccurenceOf1
     * - Performs a binary search on a sorted 0/1 array to find the index of the first 1.
     * - If the array contains no 1, returns arr.length.
     *
     * <p>
     * Example:
     * arr = [0, 0, 1, 1, 1] → returns 2
     * arr = [0, 0, 0, 0]   → returns 4 (arr.length, meaning no 1 found)
     */
    public static int findFirstOccurenceOf1(int[] arr, int target) {
        int low = 0;
        int high = arr.length - 1;
        int ans = arr.length;
        while (low <= high) {
            int mid = low + (high - low) / 2;

            if (arr[mid] >= target) {
                ans = mid;
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return ans;
    }
}
