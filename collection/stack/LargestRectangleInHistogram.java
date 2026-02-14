package collection.stack;

import java.util.Stack;

/*
Leetcode: 84. Largest Rectangle in Histogram

Given an array of integers heights representing the histogram's bar height where the width of each bar is 1,
return the area of the largest rectangle in the histogram.

Input: heights = [2,1,5,6,2,3]
Output: 10
Explanation: The above is a histogram where width of each bar is 1.
The largest rectangle is shown in the red area, which has an area = 10 units.

Input: heights = [2,4]
Output: 4

Constraints:
            1 <= heights.length <= 10^5
            0 <= heights[i] <= 10^4
 */
public class LargestRectangleInHistogram {

    public static void main(String[] args) {

        int[] height = {2, 1, 5, 6, 2, 3};
        System.out.println(findLargestRectangleArea(height));
    }

    /*
    Thought Process:

    Initially, I tried to solve this problem using a linear traversal approach.
    At each iteration, I maintained a variable called minimumHeight that tracked
    the smallest height encountered so far. My idea was:

    1. Take the current bar height.
    2. Compare it with the next bar.
    3. Take the minimum between them.
    4. Multiply that minimum height by 2 (assuming width = 2).
    5. Keep updating the maximum area found.

    At the end, I also tried multiplying the total number of bars by the
    minimum height found during traversal.

    This approach worked for a few simple cases, but it failed when:
    - There were continuous taller bars in the middle.
    - The largest rectangle was formed by a group of contiguous bars,
      not just adjacent pairs.
    - The minimum height was not the global minimum of the entire array.

    At that point, I realized something important:
    For each bar, I must treat that bar as the height of a potential rectangle,
    and then find how far it can expand to the left and right before hitting
    a smaller bar.

    In other words:
    The rectangle for index i should extend until:
    - A smaller bar appears on the left.
    - A smaller bar appears on the right.

    So the real problem became:
    How do I efficiently find the Previous Smaller Element (PSE)
    and the Next Smaller Element (NSE) for every index?

    After researching and learning from Striver's explanation,
    I understood that this is a classic monotonic stack problem.

    The correct approach:

    1. Precompute Previous Smaller Element (PSE) for each index.
    2. Precompute Next Smaller Element (NSE) for each index.
    3. For every index i:
       width = (nextSmaller[i] - previousSmaller[i] - 1)
       area  = arr[i] * width
    4. Track the maximum area.

    Important edge handling:
    - If no previous smaller element exists, use -1.
    - If no next smaller element exists, use n (array length).

    This gives time complexity:
    O(5N) if we compute PSE and NSE separately.

    However, we can further optimize:
    Instead of storing full arrays,
    we can compute Previous Smaller on the fly
    and use a single stack traversal with a sentinel.

    That reduces:
    Time Complexity → O(2N)
    Space Complexity → O(N)

    Key learning:
    The largest rectangle for any bar depends on how far
    it can stretch between two smaller boundaries,
    not just on local adjacent comparisons.
    */

    /**
     * findLargestRectangleArea
     * <p>
     * What it does:
     * Computes the largest possible rectangular area that can be formed
     * within a histogram where each bar has width 1.
     * <p>
     * Core idea:
     * For every bar in the histogram, treat it as the height of a rectangle.
     * Then determine how far this height can extend to the left and right
     * before encountering a shorter bar.
     * <p>
     * Instead of checking each bar separately in O(n^2),
     * we use a monotonic increasing stack to calculate
     * the maximum area efficiently in O(n).
     * <p>
     * Why a stack works:
     * The stack stores indices of bars in increasing height order.
     * When we encounter a bar shorter than the stack top,
     * we know the rectangle formed using the popped bar's height
     * must end at the current index.
     * <p>
     * That moment gives us both boundaries:
     * - Right boundary = current index i
     * - Left boundary = new top of stack after popping
     * <p>
     * Explanation step by step:
     * <p>
     * - Initialize an empty stack that stores indices.
     * - maxArea keeps track of the largest area found so far.
     * - Iterate from i = 0 to n (inclusive).
     * <p>
     * Important trick:
     * When i == n, treat the height as 0.
     * This forces all remaining bars in the stack
     * to be processed at the end.
     * <p>
     * For each index i:
     * - currentHeight = 0 if i == n
     * otherwise heights[i]
     * <p>
     * While:
     * - The stack is not empty
     * - currentHeight is smaller than the height at stack top
     * <p>
     * This means:
     * We have found the "right wall" for the rectangle
     * whose height is heights[stack.peek()]
     * <p>
     * Process the rectangle:
     * - Pop the top index from the stack.
     * - Let h = height of popped bar.
     * <p>
     * Now determine width:
     * - If the stack is empty:
     * width = i
     * meaning this height extends from index 0 to i - 1
     * <p>
     * - If stack is not empty:
     * width = i - stack.peek() - 1
     * meaning the rectangle extends between:
     * (stack.peek() + 1) and (i - 1)
     * <p>
     * Compute area:
     * area = h * width
     * <p>
     * Update maxArea.
     * <p>
     * After processing, push the current index i into the stack.
     * <p>
     * Example walkthrough:
     * heights = [2,1,5,6,2,3]
     * <p>
     * At index 4 (height 2):
     * - 2 < 6 → pop 6
     * area = 6 * 1 = 6
     * - 2 < 5 → pop 5
     * area = 5 * 2 = 10
     * - push index 4
     * <p>
     * Maximum area becomes 10.
     * <p>
     * Why the virtual 0 at the end is important:
     * Without it, some increasing bars at the end
     * would never be processed.
     * The virtual 0 guarantees all bars get evaluated.
     * <p>
     * Important details:
     * - We store indices, not heights.
     * - Using indices allows us to compute width correctly.
     * - Each index is pushed and popped at most once.
     * <p>
     * Complexity:
     * Time: O(n)
     * Each element is processed at most twice (push and pop).
     * <p>
     * Space: O(n)
     * Stack may store up to n indices.
     * <p>
     * Interview takeaway:
     * This is one of the most important monotonic stack problems.
     * It teaches:
     * - Finding span using previous and next smaller elements
     * - Using indices for width calculation
     * - Using a virtual sentinel to flush the stack
     * <p>
     * Once mastered, this pattern directly applies to:
     * - Max rectangle in matrix
     * - Sum of subarray minimums
     * - Range contribution problems
     */
    private static int findLargestRectangleArea(int[] heights) {
        Stack<Integer> stack = new Stack<>();
        int maxArea = 0;
        int n = heights.length;

        for (int i = 0; i <= n; i++) {
            // Use 0 as a "virtual" bar at the end to force the stack to empty
            int currentHeight = (i == n) ? 0 : heights[i];

            // If the current bar is shorter than the stack top, we found a "Right Wall"
            while (!stack.isEmpty() && currentHeight < heights[stack.peek()]) {
                int h = heights[stack.pop()]; // This is the height of our rectangle

                // The "Right Wall" is 'i'
                // The "Left Wall" is the new top of the stack
                int width = stack.isEmpty() ? i : i - stack.peek() - 1;

                maxArea = Math.max(maxArea, h * width);
            }
            stack.push(i);
        }
        return maxArea;
    }
}
