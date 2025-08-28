package array;

import java.util.HashMap;

/*
Given an integer array of size n containing distinct values in the range from 0 to n (inclusive),
return the only number missing from the array within this range.
Constraints:
            n == nums.length
            1 <= n <= 104
            0 <= nums[i] <= n
            All the numbers of nums are unique.
 */
public class FindMissingElementArray {

    public static void main(String[] args) {

        int[] num1 = {1, 2, 3, 4, 5, 6, 7};
        int n = 7;
        System.out.println((bruteForceApproach(num1, n)));

    }

    private static int bruteForceApproach(int[] num1, int n) {
        int missing = 0;
        while (missing < n) {
            int flag = 0;
            for (int i = 0; i < n; i++) {
                if (num1[i] == missing) {
                    missing++;
                    flag = 1;
                    break;
                }
            }
            if (flag == 0) {
                return missing;
            }
        }
        return missing;
    }

}
