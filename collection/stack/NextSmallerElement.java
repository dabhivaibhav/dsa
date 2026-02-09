package collection.stack;

import java.util.Arrays;
import java.util.Stack;

/*
Problem: Next Smaller Element

Given an array of integers arr, your task is to find the Next Smaller Element (NSE) for every element in the array.
The Next Smaller Element for an element x is defined as the first element to the right of x that is smaller than x.
If there is no smaller element to the right, then the NSE is -1.

Example 1
Input: arr = [4, 8, 5, 2, 25]
Output: [2, 5, 2, -1, -1]
Explanation:
- For 4, the next smaller element is 2.
- For 8, the next smaller element is 5.
- For 5, the next smaller element is 2.
- For 2, there is no smaller element to its right → -1.
- For 25, no smaller element exists → -1.

Example 2
Input: arr = [10, 9, 8, 7]
Output: [9, 8, 7, -1]
Explanation:
Each element’s next right neighbor is smaller.

Constraints:
            1 <= arr.length <= 10^5
            -10^9 <= arr[i] <= 10^9
 */
public class NextSmallerElement {

    public static void main(String[] args) {

        System.out.println("Brute force approach: ");
        System.out.println(Arrays.toString(findNextSmallerElementBruteForce(new int[]{4, 8, 5, 2, 25})));
        System.out.println(Arrays.toString(findNextSmallerElementBruteForce(new int[]{10, 9, 8, 7})));
        System.out.println("\nOptimized approach: ");
        System.out.println(Arrays.toString(findNextSmallerElementOptimized(new int[]{4, 8, 5, 2, 25})));
        System.out.println(Arrays.toString(findNextSmallerElementOptimized(new int[]{10, 9, 8, 7})));


    }

    /**
     * Method name:
     * findNextSmallerElementBruteForce
     * <p>
     * What it does:
     * Finds the Next Smaller Element for each element in the array
     * using a direct brute force approach.
     * For every index i, it searches to the right of nums[i]
     * and returns the first element that is smaller than nums[i].
     * If no such element exists, the result is -1.
     * <p>
     * Explanation of the approach:
     * This method follows the problem definition literally.
     * For each element, it checks all elements to its right
     * until a smaller one is found.
     * <p>
     * Step by step logic:
     * - Create a result array to store answers.
     * - For each index i:
     * - Initialize result[i] as -1.
     * - Scan the array from index i + 1 to the end.
     * - If nums[j] is smaller than nums[i],
     * store it as the next smaller element and stop searching.
     * <p>
     * Example:
     * nums = [4, 8, 5, 2, 25]
     * <p>
     * For 4 → next smaller is 2
     * For 8 → next smaller is 5
     * For 5 → next smaller is 2
     * For 2 → no smaller element → -1
     * For 25 → no smaller element → -1
     * <p>
     * Time complexity:
     * O(n^2), where n is the length of the array.
     * Each element may scan the rest of the array.
     * <p>
     * Space complexity:
     * O(n) for the result array.
     * <p>
     * Interview takeaway:
     * This is the most intuitive solution.
     * It is useful as a baseline approach before optimization.
     */
    private static int[] findNextSmallerElementBruteForce(int[] nums) {

        if (nums.length == 1) return new int[]{-1};
        int[] result = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            result[i] = -1;
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[j] < nums[i]) {
                    result[i] = nums[j];
                    break;
                }
            }
        }
        return result;
    }

    /**
     * Method name:
     * findNextSmallerElementOptimized
     * <p>
     * What it does:
     * Finds the Next Smaller Element for each element in the array
     * using a monotonic stack approach.
     * This solution runs in linear time.
     * <p>
     * Key idea behind the optimization:
     * Use a monotonic increasing stack while scanning the array from right to left.
     * The stack stores potential next smaller elements.
     * <p>
     * Why we traverse from right to left:
     * The next smaller element must be to the right of the current index.
     * By scanning from right to left, the stack always contains elements
     * that are positioned to the right of the current element.
     * <p>
     * Explanation of stack behavior:
     * - The stack maintains elements in increasing order from bottom to top.
     * - Any element greater than or equal to the current value
     * cannot be the next smaller element, so it is removed.
     * <p>
     * Step by step logic:
     * - Start from the last index and move towards the first.
     * - While the stack is not empty and the top element is
     * greater than or equal to the current element, pop it.
     * - After popping, the top of the stack (if present)
     * is the next smaller element.
     * - Store this value in the result array.
     * - Push the current element onto the stack.
     * <p>
     * Example:
     * nums = [4, 8, 5, 2, 25]
     * <p>
     * Traversal from right to left:
     * - 25 → stack empty → result = -1 → push 25
     * - 2  → pop 25 → result = -1 → push 2
     * - 5  → top is 2 → result = 2 → push 5
     * - 8  → pop 5 → top is 2 → result = 2 → push 8
     * - 4  → top is 2 → result = 2 → push 4
     * <p>
     * Time complexity:
     * O(n), where n is the length of the array.
     * Each element is pushed and popped at most once.
     * <p>
     * Space complexity:
     * O(n) due to the stack and result array.
     * <p>
     * Interview takeaway:
     * This optimized solution reduces time complexity from quadratic to linear.
     * Monotonic stacks are a common pattern in range and next element problems.
     * Explaining why elements are popped is the key to understanding this approach.
     */
    private static int[] findNextSmallerElementOptimized(int[] nums) {
        int n = nums.length;
        int[] result = new int[n];
        Stack<Integer> stack = new Stack<>();

        // going for Right-to-Left approach to make it work same as finding next greater element
        for (int i = n - 1; i >= 0; i--) {
            // We want the NEXT SMALLER, so pop anything bigger or equal
            while (!stack.isEmpty() && stack.peek() >= nums[i]) {
                stack.pop();
            }

            // The top of the stack is now the first smaller element to the right
            result[i] = stack.isEmpty() ? -1 : stack.peek();

            // Put ourselves on the stack for the elements to our left
            stack.push(nums[i]);
        }
        return result;
    }
}
