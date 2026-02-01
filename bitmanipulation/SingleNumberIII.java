package bitmanipulation;

import java.util.Arrays;

/*
Single Number - III

Given an array nums of length n, every integer in the array appears twice except for two integers. Identify and return
the two integers that appear only once in the array. Return the two numbers in ascending order.
For example, if nums = [1, 2, 1, 3, 5, 2], the correct answer is [3, 5], not [5, 3].

Example 1
Input : nums = [1, 2, 1, 3, 5, 2]
Output : [3, 5]
Explanation : The integers 3 and 5 have appeared only once.

Example 2
Input : nums = [-1, 0]
Output : [-1, 0]
Explanation : The integers -1 and 0 have appeared only once.

Constraints:
            2 <= nums.length <= 10^5
            -3*10^5 <= nums[i] <= 3*10^5
            Every integer in nums appears twice except two integers.
 */
public class SingleNumberIII {

    public static void main(String[] args) {
        int[] nums = {1, 2, 1, 3, 5, 2};
        System.out.println(Arrays.toString(singleNumber(nums)));
        int[] nums1 = {-1, 0};
        System.out.println(Arrays.toString(singleNumber(nums1)));
    }

    /**
     * What it does:
     * Finds the two numbers that appear exactly once in an array where
     * every other number appears exactly twice.
     * Returns the two unique numbers in ascending order.
     * <p>
     * Core idea (why XOR is the right tool):
     * XOR has three key properties:
     * 1) x ^ x = 0        (pairs cancel)
     * 2) x ^ 0 = x        (identity)
     * 3) XOR is associative and commutative (order doesn't matter)
     * <p>
     * So, if every number appears twice except two unique numbers (say a and b),
     * XORing the entire array cancels all pairs and leaves:
     * <p>
     * totalXOR = a ^ b
     * <p>
     * This is crucial: we don't directly get a and b,
     * but we get a XOR b, which tells us "where they differ" in bits.
     * <p>
     * Line-by-line explanation
     * <p>
     * 1) int totalXOR = 0;
     * - Initializes XOR accumulator.
     * - Starting from 0 is safe because (0 ^ x = x).
     * <p>
     * 2) for (int num : nums) totalXOR ^= num;
     * - XOR every element in the array.
     * - Every duplicated number cancels itself: x ^ x = 0.
     * - After this loop:
     * totalXOR = (unique1 ^ unique2)
     * <p>
     * Example: nums = [1,2,1,3,5,2]
     * Pairs cancel:
     * (1^1) → 0, (2^2) → 0
     * Left with:
     * totalXOR = 3 ^ 5
     * <p>
     * 3) int differentNum = (totalXOR & (totalXOR - 1)) ^ totalXOR;
     * <p>
     * This line is the heart of the solution.
     * <p>
     * What we know:
     * - totalXOR = a ^ b
     * - If a != b, then totalXOR has at least one set bit (1).
     * - A set bit in totalXOR means:
     * "At this bit position, a and b are different"
     * (one has 1, the other has 0).
     * <p>
     * Why we need differentNum:
     * We need ONE bit position that we are sure separates a and b into different groups.
     * If we can find any bit where a and b differ, we can partition the array into two
     * buckets such that:
     * - a goes to one bucket
     * - b goes to the other bucket
     * <p>
     * How we extract that distinguishing bit:
     * - (totalXOR & (totalXOR - 1)) removes the lowest set bit of totalXOR.
     * - XORing that with totalXOR isolates exactly that removed bit.
     * <p>
     * So:
     * differentNum becomes a mask containing only ONE set bit:
     * the rightmost set bit of totalXOR.
     * <p>
     * Equivalent mental model:
     * differentNum = totalXOR & (-totalXOR)
     * (both isolate the lowest set bit)
     * <p>
     * Example:
     * Suppose a = 3  (0011)
     * b = 5  (0101)
     * totalXOR = 3 ^ 5 = 0110
     * <p>
     * totalXOR - 1 = 0101
     * totalXOR & (totalXOR - 1) = 0110 & 0101 = 0100
     * <p>
     * differentNum = 0100 ^ 0110 = 0010
     * <p>
     * differentNum = 0010 means:
     * "Use bit #1 (2's place) to separate numbers"
     * <p>
     * 4) int num1 = 0, num2 = 0;
     * - Two accumulators for the two unique results.
     * - Each will eventually hold one unique number after XOR cancellation.
     * <p>
     * 5) for (int num : nums) { ... }
     * - Second pass through the array.
     * - We now use differentNum as a "bucket selector".
     * <p>
     * 6) if ((num & differentNum) == 0) num2 ^= num;
     * else num1 ^= num;
     * <p>
     * This is the bucket / partition technique.
     * <p>
     * What it means:
     * - We check the specific bit indicated by differentNum.
     * - If that bit is 0 → bucket A (num2)
     * - If that bit is 1 → bucket B (num1)
     * <p>
     * Why this guarantees separation of the two unique numbers:
     * - differentNum comes from totalXOR = a ^ b
     * - At that bit position, a and b must be different.
     * So one will land in bucket A, the other in bucket B.
     * <p>
     * Why duplicates still cancel inside buckets:
     * - Any duplicated number x has identical bits,
     * so both occurrences of x go into the same bucket.
     * - Then x ^ x = 0 cancels within that bucket.
     * <p>
     * End result:
     * - num1 contains one unique number
     * - num2 contains the other unique number
     * <p>
     * 7) if (num1 < num2) return new int[]{num1, num2};
     * return new int[]{num2, num1};
     * <p>
     * - Ensures the output is in ascending order (as the problem requires).
     * - (Note: your current code returns {num1, num2} in both branches —
     * the second return should be {num2, num1}.)
     * <p>
     * Example Walkthrough (bucket separation)
     * nums = [1, 2, 1, 3, 5, 2]
     * <p>
     * Step 1: totalXOR = 3 ^ 5 = 0110
     * Step 2: differentNum isolates a differing bit:
     * differentNum = 0010
     * <p>
     * Partition using bit 0010:
     * - Numbers with that bit = 0: 1(0001), 1(0001), 5(0101)
     * - Numbers with that bit = 1: 2(0010), 2(0010), 3(0011)
     * <p>
     * XOR within bucket 0:
     * 1 ^ 1 ^ 5 = 5
     * <p>
     * XOR within bucket 1:
     * 2 ^ 2 ^ 3 = 3
     * <p>
     * Unique numbers = [3, 5]
     * <p>
     * Complexity:
     * Time:  O(n)   (two linear passes)
     * Space: O(1)   (constant extra variables)
     * <p>
     * Interview takeaway:
     * - XOR the whole array → get a ^ b
     * - Extract a distinguishing set bit → differentNum
     * - Partition into two buckets by that bit → duplicates cancel → recover a and b
     */

    private static int[] singleNumber(int[] nums) {

        int totalXOR = 0;
        for (int num : nums) totalXOR ^= num;
        int differentNum = (totalXOR & (totalXOR - 1)) ^ totalXOR;
        int num1 = 0, num2 = 0;
        for (int num : nums) {
            if ((num & differentNum) == 0) num2 ^= num;
            else num1 ^= num;
        }
        if (num1 < num2) return new int[]{num1, num2};
        return new int[]{num1, num2};
    }
}
