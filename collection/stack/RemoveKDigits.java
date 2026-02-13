package collection.stack;

import java.util.ArrayDeque;
import java.util.Deque;

/*
Leetcode 402: 402. Remove K Digits

Given string num representing a non-negative integer num, and an integer k, return the smallest possible integer after
removing k digits from num.

Example 1:
Input: num = "1432219", k = 3
Output: "1219"
Explanation: Remove the three digits 4, 3, and 2 to form the new number 1219 which is the smallest.

Example 2:
Input: num = "10200", k = 1
Output: "200"
Explanation: Remove the leading 1 and the number is 200. Note that the output must not contain leading zeroes.

Example 3:
Input: num = "10", k = 2
Output: "0"
Explanation: Remove all the digits from the number and it is left with nothing which is 0.

Constraints:
            1 <= k <= num.length <= 10^5
            num consists of only digits.
            num does not have any leading zeros except for the zero itself.
 */
public class RemoveKDigits {

    public static void main(String[] args) {

        String num = "112";
        int k = 1;
        System.out.println(removeKdigitsBruteForce(num, k));

        num = "10200";
        k = 1;
        System.out.println(removeKdigitsBruteForce(num, k));

        num = "10";
        k = 1;
        System.out.println(removeKdigitsBruteForce(num, k));
    }

    /**
     * removeKdigitsBruteForce:
     * <p>
     * What it does:
     * Removes exactly k digits from the given numeric string
     * so that the resulting number is the smallest possible.
     * The result is returned as a string without leading zeros.
     * <p>
     * Core idea:
     * To make a number as small as possible,
     * we should remove digits that create a local decrease.
     * In other words, removing a larger digit that appears
     * before a smaller digit reduces the number the most.
     * <p>
     * Thought process:
     * Instead of checking all possible combinations of removing k digits,
     * which would be extremely expensive,
     * we repeatedly remove one digit at a time using a greedy strategy.
     * <p>
     * At each step, we remove the first digit that is larger than the digit
     * immediately following it.
     * This digit is called a "peak" because it increases the number.
     * <p>
     * Why this greedy approach works:
     * - Digits on the left have higher place value.
     * - Removing a larger digit earlier has a bigger impact
     * than removing a digit later.
     * - If the digits are already in increasing order,
     * the best choice is to remove from the end.
     * <p>
     * Explanation of the approach step by step:
     * <p>
     * 1. Handle edge case:
     * If k is equal to the length of the string,
     * all digits are removed and the result is "0".
     * <p>
     * 2. Use a StringBuilder for efficient deletions.
     * <p>
     * 3. Repeat the removal process k times:
     * - Start from the beginning of the string.
     * - Move forward while the current digit is less than
     * or equal to the next digit.
     * - Stop at the first position where the current digit
     * is greater than the next digit.
     * - Remove the digit at this position.
     * <p>
     * 4. After all removals:
     * - Remove leading zeros if present.
     * - Ensure at least one digit remains.
     * <p>
     * Example walkthrough:
     * num = "1432219", k = 3
     * <p>
     * Step 1: remove '4' -> "132219"
     * Step 2: remove '3' -> "12219"
     * Step 3: remove '2' -> "1219"
     * <p>
     * Final result = "1219"
     * <p>
     * Example with leading zeros:
     * num = "10200", k = 1
     * Remove '1' -> "0200"
     * Remove leading zero -> "200"
     * <p>
     * Important details:
     * - The comparison is done using character values,
     * which works because digits maintain numeric order in ASCII.
     * - Leading zeros must be removed from the final result.
     * - The StringBuilder ensures deletions are efficient.
     * <p>
     * Complexity:
     * Time: O(k * n)
     * Each removal may scan the string once.
     * <p>
     * Space: O(n)
     * StringBuilder stores the mutable string.
     * <p>
     * Interview takeaway:
     * This solution demonstrates greedy thinking.
     * The key insight is identifying that removing the first
     * decreasing digit always produces the smallest possible result.
     * This logic is closely related to the monotonic stack solution,
     * which further optimizes this approach to O(n).
     */
    private static String removeKdigitsBruteForce(String num, int k) {
        // Edge case: If we remove all digits, the answer is "0"
        if (num.length() == k) return "0";
        StringBuilder sb = new StringBuilder(num);
        // Instead of nested loops looking for substrings,
        // we perform 'k' removals of the "best" digit.
        for (int j = 0; j < k; j++) {
            int i = 0;
            // Find the first "peak" (a digit bigger than the next one)
            // Why? Because removing a larger digit from a higher place value
            // reduces the number the most.
            while (i < sb.length() - 1 && sb.charAt(i) <= sb.charAt(i + 1)) {
                i++;
            }
            // Remove that specific digit
            sb.deleteCharAt(i);
        }
        // Remove leading zeros (e.g., "0200" -> "200")
        // We keep deleting the first character if it's '0', but stop if only one digit is left
        while (sb.length() > 1 && sb.charAt(0) == '0') {
            sb.deleteCharAt(0);
        }
        return sb.isEmpty() ? "0" : sb.toString();
    }


    /**
     * removeKdigitsOptimized
     * <p>
     * What it does:
     * Removes exactly k digits from the given numeric string
     * to form the smallest possible number.
     * The result contains no leading zeros and is returned as a string.
     * <p>
     * Core idea:
     * To minimize the number, we should remove digits that break the increasing order.
     * A larger digit placed before a smaller digit increases the value,
     * so removing that larger digit gives the biggest reduction.
     * <p>
     * This method implements that idea efficiently using a monotonic stack.
     * <p>
     * Thought process and evolution:
     * In the naive greedy approach, we repeatedly scan the string k times
     * to find a digit to remove, which leads to O(k * n) time.
     * To optimize this, we simulate all removals in a single pass
     * using a stack that maintains increasing order.
     * <p>
     * The stack always represents the smallest possible number
     * built so far from the digits we have processed.
     * <p>
     * Why a stack works here:
     * - We want to compare the current digit with the most recent digit added.
     * - If the current digit is smaller, the previous larger digit should be removed.
     * - This exactly matches stack behavior (last in, first out).
     * <p>
     * Explanation of the approach step by step:
     * <p>
     * 1. Handle edge case:
     * If k equals the length of the number, all digits are removed,
     * so the answer is simply "0".
     * <p>
     * 2. Use a Deque as a stack:
     * - addLast acts as push
     * - removeLast acts as pop
     * - This makes it easy to build the final number from left to right.
     * <p>
     * 3. Traverse each digit in the input string:
     * - While:
     * - we still have digits left to remove (k > 0)
     * - the stack is not empty
     * - the last digit in the stack is greater than the current digit
     * remove the last digit from the stack and decrement k.
     * <p>
     * This ensures the stack remains monotonic increasing,
     * which produces the smallest possible number.
     * <p>
     * 4. Add the current digit to the stack.
     * <p>
     * 5. After processing all digits:
     * - If k is still greater than 0,
     * remove digits from the end of the stack.
     * - This handles cases where the number is already increasing.
     * <p>
     * 6. Build the result string:
     * - Skip leading zeros while constructing the output.
     * - If the resulting string is empty, return "0".
     * <p>
     * Example walkthrough:
     * num = "1432219", k = 3
     * <p>
     * Stack evolution:
     * 1 -> [1]
     * 4 -> [1,4]
     * 3 -> remove 4, stack [1], add 3 -> [1,3]
     * 2 -> remove 3, stack [1], add 2 -> [1,2]
     * 2 -> add -> [1,2,2]
     * 1 -> remove 2, stack [1,2], add 1 -> [1,2,1]
     * 9 -> add -> [1,2,1,9]
     * <p>
     * Final result = "1219"
     * <p>
     * Important details:
     * - Characters are compared directly because digit characters
     * preserve numeric ordering.
     * - Leading zeros are explicitly skipped during result construction.
     * - Each digit is pushed and popped at most once.
     * <p>
     * Complexity:
     * Time: O(n)
     * Each digit is processed once and removed at most once.
     * <p>
     * Space: O(n)
     * Stack and output storage.
     * <p>
     * Interview takeaway:
     * This is a classic greedy + monotonic stack problem.
     * The key insight is that removing the first larger digit
     * before a smaller one always leads to the smallest possible number.
     * This optimized approach is preferred in interviews
     * due to its linear time complexity.
     */
    private static String removeKdigitsOptimized(String num, int k) {
        if (k == num.length()) return "0";

        // I used a Deque as a stack because it's easier to build the string from the bottom
        Deque<Character> stack = new ArrayDeque<>();

        for (char digit : num.toCharArray()) {
            // While k left AND current digit is smaller than stack top
            while (k > 0 && !stack.isEmpty() && stack.peekLast() > digit) {
                stack.removeLast();
                k--;
            }
            stack.addLast(digit);
        }

        while (k > 0) {
            stack.removeLast();
            k--;
        }

        // Construct string and skip leading zeros
        StringBuilder sb = new StringBuilder();
        boolean leadingZero = true;
        for (char c : stack) {
            if (leadingZero && c == '0') continue;
            leadingZero = false;
            sb.append(c);
        }

        return sb.isEmpty() ? "0" : sb.toString();
    }
}
