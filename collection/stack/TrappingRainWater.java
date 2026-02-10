package collection.stack;

/*
Leetcode 42: Trapping Rainwater

Given n non-negative integers representing an elevation map where the width of each bar is 1,
compute how much water it can trap after raining.

Example 1:
Input: height = [0,1,0,2,1,0,1,3,2,1,2,1]
Output: 6
Explanation: The above elevation map (black section) is represented by array [0,1,0,2,1,0,1,3,2,1,2,1].
In this case, 6 units of rainwater (blue section) are being trapped.

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
        System.out.println(findTrappedWaterOptimized1(height));
        int[] height1 = {3, 0, 2};
        System.out.println(findTrappedWaterOptimized1(height1));

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

    /*
     * Thought process:
     * I knew that from the brute-force approach, I am trying to find the max height bar on
     * the left and right in each iteration, which was very costly and resulted in quadratic
     * time complexity O(n^2). This means my brute-force would definitely fail with a large
     * set of input.
     *
     * Then I thought of an optimized solution where I can find the max bar height on the
     * left and right in linear time complexity O(n). To achieve this, I researched and
     * found that I can use a Prefix/Suffix approach. This involves preparing pre-computed
     * arrays that store the maximum bar height to the left and to the right for every
     * current index.
     *
     * How it works:
     * 1. THE CORE IDEA:
     * Water at any index 'i' is trapped by the 'limiting factor'—which is the shorter of
     * the two tallest walls surrounding it.
     * Formula: Water[i] = min(Max_Left_So_Far, Max_Right_So_Far) - Current_Height[i]
     *
     * 2. PREFIX MAX (Left to Right):
     * We create an array 'prefixMax' where each entry stores the height of the tallest
     * bar encountered from the start of the array up to the current index. This allows
     * us to know the best "left wall" instantly.
     *
     * 3. SUFFIX MAX (Right to Left):
     * We create an array 'suffixMax' where each entry stores the height of the tallest
     * bar encountered from the current index to the end of the array. This allows us
     * to know the best "right wall" instantly.
     *
     * 4. THE CALCULATION:
     * By pre-computing these, we eliminate the need to scan the entire array repeatedly.
     * For any index 'i', we simply compare the pre-stored left-max and right-max. The
     * smaller of these two values defines the maximum water level, and the trapped water
     * is the difference between that level and the bar's actual height.
     *
     * OPTIMIZATION (Space & Time):
     * To reduce the time complexity from O(3N) to O(2N) and space complexity from O(2N) to O(N),
     * I have decided to use the Suffix array only.
     * Logic: While iterating from left to right, we need to know the tallest bar on both sides.
     * 1. The Right Side: This still requires a pre-computed 'suffixMax' array because we cannot
     * see the "future" tallest bar while moving forward.
     * 2. The Left Side: Instead of a full 'prefixMax' array, we can simply maintain a
     * single variable 'maxLeft'. As we iterate, we update 'maxLeft' to be the maximum
     * of itself and the current bar. This eliminates one full array and one full loop, making the
     * code more efficient while keeping the same logic.
     */

    /**
     * findTrappedWaterOptimized1
     * <p>
     * What it does:
     * Computes the total amount of rainwater trapped between bars using
     * a prefix–suffix maximum optimization.
     * This method improves the brute force approach by avoiding repeated scans.
     * <p>
     * Core idea:
     * At every index i, the water that can be trapped depends on:
     * - The tallest bar to the left of i
     * - The tallest bar to the right of i
     * The water level is decided by the minimum of these two values.
     * <p>
     * Why this optimization is needed:
     * In the brute force approach, for every index we repeatedly scan
     * the left and right sides, which causes a time complexity of n^2.
     * This method removes redundant scans by precomputing information once.
     * <p>
     * How the idea evolves from brute force:
     * - In brute force, leftMax and rightMax are recalculated for every index.
     * - Observation:
     * The tallest bar to the right of an index does not change unpredictably.
     * - So we store the maximum height to the right of every index in advance.
     * - The left maximum can be maintained dynamically while traversing.
     * <p>
     * Explanation of the approach step by step:
     * <p>
     * 1. Handle small inputs:
     * If the array has two or fewer bars, no water can be trapped.
     * The method immediately returns 0.
     * <p>
     * 2. Build the suffix maximum array:
     * - suffixMax[i] stores the tallest bar from index i to the end.
     * - Start from the rightmost bar and move left.
     * - Each position takes the maximum of:
     * the bar at that index or the previously computed suffix maximum.
     * <p>
     * 3. Traverse from left to right:
     * - maxLeft keeps track of the tallest bar seen so far on the left.
     * - At each index:
     * - Update maxLeft using the current height.
     * - Determine the water level as the minimum of maxLeft and suffixMax[i].
     * - The water trapped at index i is:
     * waterLevel minus height[i].
     * - Add this value to totalWater.
     * <p>
     * Example walkthrough:
     * height = [4,2,0,3,2,5]
     * <p>
     * suffixMax = [5,5,5,5,5,5]
     * <p>
     * Iteration:
     * i = 1, height = 2
     * maxLeft = 4
     * waterLevel = min(4,5) = 4
     * trapped water = 4 - 2 = 2
     * <p>
     * Summing trapped water at all indices gives 9.
     * <p>
     * Important details:
     * - The suffixMax array ensures right-side maximum is always available in O(1).
     * - maxLeft grows monotonically as we move from left to right.
     * - Negative water values never occur because water level is always
     * greater than or equal to the current height.
     * <p>
     * Complexity:
     * Time: O(2n)
     * One pass to build suffixMax and one pass to compute trapped water.
     * <p>
     * Space: O(n)
     * Extra space is used for the suffix maximum array.
     * <p>
     * Interview takeaway:
     * This solution is a textbook example of trading space for time.
     * It demonstrates how preprocessing can reduce repeated work.
     * It also serves as a stepping stone toward the two-pointer
     * solution, which further reduces space complexity to O(1).
     */
    private static int findTrappedWaterOptimized1(int[] height) {
        int n = height.length;
        if (n <= 2) return 0;

        int[] suffixMax = new int[n];
        suffixMax[n - 1] = height[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            suffixMax[i] = Math.max(suffixMax[i + 1], height[i]);
        }

        int totalWater = 0;
        int maxLeft = 0;

        for (int i = 0; i < n; i++) {
            maxLeft = Math.max(maxLeft, height[i]);
            int waterLevel = Math.min(maxLeft, suffixMax[i]);
            totalWater += (waterLevel - height[i]);
        }
        return totalWater;
    }
}
