package collection.stack;

/*
Leetcode 42: Trapping Rain Water

Given n non-negative integers representing an elevation map where the width of each bar is 1,
compute how much water it can trap after raining.

Example 1:
Input: height = [0,1,0,2,1,0,1,3,2,1,2,1]
Output: 6
Explanation: The above elevation map (black section) is represented by array [0,1,0,2,1,0,1,3,2,1,2,1].
In this case, 6 units of rain water (blue section) are being trapped.

Example 2:
Input: height = [4,2,0,3,2,5]
Output: 9

Constraints:
            n == height.length
            1 <= n <= 2 * 10^4
            0 <= height[i] <= 10^5
 */
public class TrappingRainWater {

    public static void main(String[] args) {

        int[] height = {0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1};
        System.out.println(findTrappedWaterBruteForce(height));

    }

    /*
      Initial Thought Process:

      Here what was my thought process: initially that I understood that the array at each index represents maybe water
      or a block and how I can think of that by looking at the numbers, then I though, to fill up the water at any index,
      I have to look at the left and right of the index based on that I will fill the water. So, the block value should
      be of the same height or higher on either side, so it can contain at most 1 count in between them. And I will follow
      the same process until I reach the end of the array. And then I thought, based on the problem description, what question I
      should ask the interviewer, which impressed him that I clearly understood the problem and what limitation or constraint
      I could think of. After that I asked I were I am wrong so AI corrected me that instead of just looking around
      find the tallest bar on left and right side and then find the minimum of them and check the current index element
      if it is same as the minium of left&right bar, then it can't contain any water otherwise subtract the current index element
      from the level you found from getting minimum of bar on left & right. So based on the observation, I could think of these 4 constraints:

      Q1."What happens if the array is 'Step-shaped' (strictly increasing or decreasing)?"
      Why it is important: It shows you realize that even if you have a massive wall on one side,
      if the other side is flat or lower, the water will just spill off the map.

      Q2."Does the width of each bar always equals 1?"
      Why it is important: Usually, the answer is yes, but asking this confirms you know you are calculating Area
      (Width * Height). If the width varies, the math changes.

      Q3."How should we handle 'Plateaus'?
      Why it is important: If you have heights like [2, 0, 0, 2], the water level stays flat across multiple indices.
      It shows you’re thinking about ranges, not just single points.

      Q4."What is the maximum height possible?
      Why it is important: This shows you are checking for Integer Overflow. If the heights are massive and the array
      is long, the total water could exceed 2^31 - 1.
     */

    /**
     * findTrappedWaterBruteForce
     * <p>
     * What it does:
     * Calculates how much rainwater can be trapped between bars of different heights
     * using a brute force approach.
     * Each bar is treated as a possible container where water may accumulate.
     * <p>
     * Core idea:
     * Water can be trapped above a bar only if there is a taller bar on both its left
     * and its right.
     * The amount of water trapped at a position depends on the minimum height of the
     * tallest bars on both sides.
     * <p>
     * Why it works:
     * For any index i, the maximum water level above that bar is limited by:
     * - The tallest bar on the left of i; by doing a linear scan from 0 to i.
     * - The tallest bar on the right of i; by doing a linear scan from i to n - 1.
     * The shorter of these two decides the water level.
     * Any extra height above the current bar is filled with water.
     * <p>
     * Explanation of the approach step by step:
     * - totalWater keeps track of the accumulated trapped water.
     * - n stores the length of the height array.
     * - The outer loop iterates through each index from 0 to n - 2.
     * The last bar is skipped because water cannot be trapped at the edge.
     * <p>
     * For each index i:
     * - leftMax is calculated by scanning from index 0 to i.
     * This represents the tallest bar on the left side.
     * - rightMax is calculated by scanning from index i to the end of the array.
     * This represents the tallest bar on the right side.
     * - The water trapped at index i is:
     * min(leftMax, rightMax) minus height[i]
     * - This value is added to totalWater.
     * <p>
     * Example walkthrough:
     * height = [0,1,0,2,1,0,1,3,2,1,2,1]
     * <p>
     * At index 2 (height = 0):
     * - leftMax = 1
     * - rightMax = 3
     * - water trapped = min(1, 3) - 0 = 1
     * <p>
     * At index 5 (height = 0):
     * - leftMax = 2
     * - rightMax = 3
     * - water trapped = min(2, 3) - 0 = 2
     * <p>
     * Summing trapped water across all indices gives a total of 6.
     * <p>
     * Important details:
     * - Edge bars cannot trap water because they lack a boundary on one side.
     * - Negative water values never occur because min(leftMax, rightMax)
     * is always greater than or equal to height[i].
     * - This approach is intuitive but not optimized.
     * <p>
     * Complexity:
     * Time: O(n^2)
     * For each index, the algorithm scans both left and right sides.
     * <p>
     * Space: O(1)
     * Only constant extra variables are used.
     * <p>
     * Interview takeaway:
     * This brute force solution is excellent for explaining the intuition behind
     * the trapping rain water problem.
     * Once this logic is clear, it naturally leads to optimized solutions such as:
     * prefix max arrays, two pointers, or monotonic stacks.
     */

    private static int findTrappedWaterBruteForce(int[] height) {
        int totalWater = 0;
        int n = height.length;
        for (int i = 0; i < n - 1; i++) {
            //finding tallest bar on left side of i
            int leftMax = 0;
            for (int j = 0; j <= i; j++) {
                leftMax = Math.max(leftMax, height[j]);
            }
            //finding tallest bar on right side of i
            int rightMax = 0;
            for (int j = i; j < n; j++) {
                rightMax = Math.max(rightMax, height[j]);
            }

            totalWater += Math.min(leftMax, rightMax) - height[i];
        }
        return totalWater;
    }
}
